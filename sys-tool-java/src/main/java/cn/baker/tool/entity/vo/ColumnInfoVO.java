package cn.baker.tool.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列的数据信息
 *
 * @author baker.yuan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfoVO {
    private String columnName;
    private String isNullable;
    private String dataType;
    private String columnComment;
    private String columnKey;
    private String extra;
}
