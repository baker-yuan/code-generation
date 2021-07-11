package cn.baker.tool.entity;


import cn.baker.tool.utils.GenUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 列的数据信息
 *
 * @author baker.yuan
 */
@Data
@Table(name = "code_column_config")
public class ColumnInfo {

    @Id
    @Column(name = "column_id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 表名
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 数据库字段名称
     */
    @Column(name = "column_name")
    private String columnName;

    /**
     * 数据库字段类型
     */
    @Column(name = "column_type")
    private String columnType;
    /**
     * 字典名称
     */
    @Column(name = "dict_name")
    private String dictName;


    /**
     * 字段额外的参数
     */
    private String extra;

    /**
     * 是否表单显示
     */
    @Column(name = "form_show")
    private Boolean formShow;

    /**
     * 表单类型
     */
    @Column(name = "form_type")
    private String formType;


    /**
     * 数据库字段键类型
     */
    @Column(name = "key_type")
    private String keyType;


    /**
     * 是否在列表显示
     */
    @Column(name = "list_show")
    private Boolean listShow;

    /**
     * 是否必填
     */
    @Column(name = "not_null")
    private Boolean notNull;

    /**
     * 查询 1-模糊 2-精确
     */
    @Column(name = "query_type")
    private String queryType;


    /**
     * 数据库字段描述
     */
    private String remark;


    /**
     * 日期注解
     */
    @Column(name = "date_annotation")
    private String dateAnnotation;

    public ColumnInfo() {

    }

    public ColumnInfo(String tableName, String columnName, Boolean notNull, String columnType, String remark, String keyType, String extra) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.keyType = keyType;
        this.extra = extra;
        this.notNull = notNull;
        if (GenUtil.PK.equalsIgnoreCase(keyType) && GenUtil.EXTRA.equalsIgnoreCase(extra)) {
            this.notNull = false;
        }
        this.remark = remark;
        this.listShow = true;
        this.formShow = true;
    }

}
