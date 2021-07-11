package cn.baker.tool.export;

import cn.baker.common.response.ResResult;
import cn.baker.tool.entity.GenConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author baker.yuan
 */
@Api(tags = "系统：代码生成器配置管理")
public interface GenConfigApi {
    @ApiOperation("查询")
    @GetMapping(value = "/api/genConfig/{tableName}")
    ResResult<GenConfig> query(@PathVariable String tableName) ;

    @ApiOperation("修改")
    @PutMapping("/api/genConfig")
    ResResult<GenConfig> update(@Validated @RequestBody GenConfig genConfig) ;
}
