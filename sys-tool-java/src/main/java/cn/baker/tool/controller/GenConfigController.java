package cn.baker.tool.controller;


import cn.baker.common.response.ResResult;
import cn.baker.common.response.ResUtils;
import cn.baker.tool.entity.GenConfig;
import cn.baker.tool.export.GenConfigApi;
import cn.baker.tool.service.GenConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author baker.yuan
 */
@RestController
public class GenConfigController implements GenConfigApi {

    @Autowired
    private GenConfigService genConfigService;

    @Override
    public ResResult<GenConfig> query(@PathVariable String tableName) {
        return ResUtils.data(genConfigService.find(tableName));
    }

    @Override
    public ResResult<GenConfig> update(@Validated @RequestBody GenConfig genConfig) {
        return ResUtils.data(genConfigService.update(genConfig.getTableName(), genConfig));
    }
}
