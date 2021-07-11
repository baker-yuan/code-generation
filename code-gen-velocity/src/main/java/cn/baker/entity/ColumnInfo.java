package cn.baker.entity;

import lombok.Data;

/**
 * @author baker.yuan
 */
@Data
public class ColumnInfo {
    private String columnName;
    private String dataType;
    private String columnComment;
    private String columnKey;
    private String extra;
}
