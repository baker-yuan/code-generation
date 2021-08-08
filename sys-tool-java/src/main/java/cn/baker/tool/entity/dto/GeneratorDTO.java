package cn.baker.tool.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
/**
 * @author baker.yuan
 */
@Data
@ApiModel("代码生成管理")
public class GeneratorDTO {

    @ApiModel("同步字段数据")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class SyncDTO {
        @NotEmpty
        @ApiModelProperty("表名")
        List<String> tables;

        @NotBlank
        @ApiModelProperty("库表名")
        private String dbName;
    }
}
