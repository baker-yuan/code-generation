package cn.baker.tool.mapper;

import cn.baker.tool.entity.GenConfig;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author baker.yuan
 */
public interface GenConfigMapper extends Mapper<GenConfig> {
    /**
     * 根据表名查询表的配置信息
     *
     * @param tableName 表名
     * @return /
     */
    GenConfig findByTableName(@Param("tableName") String tableName);
}
