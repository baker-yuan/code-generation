package cn.baker.entity;

import lombok.Data;

/**
 * @author baker.yuan
 */
@Data
public class TableInfo {
    private String tableName;
    private String engine;
    private String tableComment;
    private String createTime;
}
