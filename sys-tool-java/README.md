
```sql
CREATE TABLE `code_column_config`
(
    `column_id`       bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_name`      varchar(255) DEFAULT '' COMMENT '表名',
    `column_name`     varchar(255) DEFAULT '' COMMENT '数据库字段名称',
    `column_type`     varchar(255) DEFAULT '' COMMENT '数据库字段类型',
    `dict_name`       varchar(255) DEFAULT '' COMMENT '字典名称',
    `extra`           varchar(255) DEFAULT '' COMMENT '字段额外的参数',
    `form_show`       bit(1)       DEFAULT b'0' COMMENT '是否表单显示',
    `form_type`       varchar(255) DEFAULT '' COMMENT '表单类型',
    `key_type`        varchar(255) DEFAULT '' COMMENT '数据库字段键类型',
    `list_show`       bit(1)       DEFAULT b'0' COMMENT '是否在列表显示',
    `not_null`        bit(1)       DEFAULT b'0' COMMENT '是否必填',
    `query_type`      varchar(255) DEFAULT '0' COMMENT '查询 1-模糊 2-精确',
    `remark`          varchar(255) DEFAULT '' COMMENT '数据库字段描述',
    `date_annotation` varchar(255) DEFAULT '' COMMENT '日期注解',
    PRIMARY KEY (`column_id`) USING BTREE,
    KEY `idx_table_name` (`table_name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
    COMMENT ='代码生成字段信息存储';
```

```sql
CREATE TABLE `code_gen_config`
(
    `config_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_name`  varchar(255) DEFAULT NULL COMMENT '表名',
    `author`      varchar(255) DEFAULT NULL COMMENT '作者',
    `cover`       bit(1)       DEFAULT NULL COMMENT '是否覆盖',
    `module_name` varchar(255) DEFAULT NULL COMMENT '模块名称',
    `pack`        varchar(255) DEFAULT NULL COMMENT '至于哪个包下',
    `path`        varchar(255) DEFAULT NULL COMMENT '前端代码生成的路径',
    `api_path`    varchar(255) DEFAULT NULL COMMENT '前端Api文件路径',
    `prefix`      varchar(255) DEFAULT NULL COMMENT '表前缀',
    `api_alias`   varchar(255) DEFAULT NULL COMMENT '接口名称',
    PRIMARY KEY (`config_id`) USING BTREE,
    KEY `idx_table_name` (`table_name`(100))
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = COMPACT COMMENT ='代码生成器配置';

```