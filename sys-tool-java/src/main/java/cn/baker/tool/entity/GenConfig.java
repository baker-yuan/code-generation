package cn.baker.tool.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 代码生成配置
 *
 * @author baker.yuan
 */
@Data
@Table(name = "code_gen_config")
public class GenConfig {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "config_id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 表名
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 作者
     */
    private String author;

    /**
     * 是否覆盖
     */
    private Boolean cover;

    /**
     * 模块名
     */
    @Column(name = "module_name")
    private String moduleName;

    /**
     * 包路径
     */
    private String pack;

    /**
     * 前端文件路径
     */
    private String path;

    /**
     * 前端文件路径
     */
    @Column(name = "api_path")
    private String apiPath;

    /**
     * 表前缀
     */
    private String prefix;

    /**
     * 接口名称
     */
    @Column(name = "api_alias")
    private String apiAlias;
}
