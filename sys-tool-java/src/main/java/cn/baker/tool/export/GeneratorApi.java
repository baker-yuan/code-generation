package cn.baker.tool.export;

import cn.baker.common.response.ResResult;
import cn.baker.common.search.PageResult;
import cn.baker.tool.entity.ColumnInfo;
import cn.baker.tool.entity.dto.GeneratorDTO;
import cn.baker.tool.entity.vo.TableInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "系统：代码生成管理")
public interface GeneratorApi {

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/api/generator/tables/all")
    ResResult<List<TableInfo>> queryTables();

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/api/generator/tables")
    ResResult<PageResult<TableInfo>> queryTables(@RequestParam String name, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size);

    @ApiOperation("查询字段数据")
    @GetMapping(value = "/api/generator/columns")
    ResResult<List<ColumnInfo>> queryColumns(@RequestParam String tableName);

    @ApiOperation("保存字段数据")
    @PutMapping("/api/generator")
    ResponseEntity<HttpStatus> save(@RequestBody List<ColumnInfo> columnInfos);

    @ApiOperation("同步字段数据")
    @PostMapping(value = "/api/generator/sync")
    ResponseEntity<HttpStatus> sync(@RequestBody @Validated GeneratorDTO.SyncDTO dto);

    @ApiOperation("生成代码")
    @PostMapping(value = "/api/generator/{tableName}/{type}")
    ResResult<?> generator(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response);
}
