package cn.baker.tool.controller;


import cn.baker.common.exception.BizException;
import cn.baker.common.response.ResResult;
import cn.baker.common.response.ResUtils;
import cn.baker.common.search.PageResult;
import cn.baker.tool.entity.ColumnInfo;
import cn.baker.tool.entity.GenConfig;
import cn.baker.tool.entity.dto.GeneratorDTO;
import cn.baker.tool.entity.vo.TableInfo;
import cn.baker.tool.export.GeneratorApi;
import cn.baker.tool.service.GenConfigService;
import cn.baker.tool.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author baker.yuan
 */

@RestController
public class GeneratorController implements GeneratorApi {

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private GenConfigService genConfigService;

    @Override
    public ResResult<List<TableInfo>> queryTables() {
        return ResUtils.data(generatorService.getTables());
    }

    @Override
    public ResResult<PageResult<TableInfo>> queryTables(@RequestParam String name,
                                                        @RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        return ResUtils.data(generatorService.getTables(name, page, size));
    }

    @Override
    public ResResult<List<ColumnInfo>> queryColumns(@RequestParam String tableName) {
        return ResUtils.data(generatorService.getColumns(tableName));
    }

    @Override
    public ResponseEntity<HttpStatus> save(@RequestBody List<ColumnInfo> columnInfos) {
        generatorService.save(columnInfos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> sync(@RequestBody @Validated GeneratorDTO.SyncDTO dto) {
        for (String table : dto.getTables()) {
            generatorService.sync(generatorService.getColumns(table), generatorService.query(table));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResResult<?> generator(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
        if (!generatorEnabled && type == 0) {
            throw new BizException("此环境不允许生成代码，请选择预览或者下载查看！");
        }
        GenConfig genConfig = genConfigService.find(tableName);
        List<ColumnInfo> columnInfoList = generatorService.getColumns(tableName);
        switch (type) {
            // 生成代码
            case 0:
                generatorService.generator(genConfig, columnInfoList);
                break;
            // 前端预览
            case 1:
                return ResUtils.data(generatorService.preview(genConfig, columnInfoList));
            // 打包为zip
            case 2:
                generatorService.download(genConfig, columnInfoList, request, response);
                break;
            default:
                throw new BizException("没有这个选项");
        }
        return ResUtils.suc();
    }
}
