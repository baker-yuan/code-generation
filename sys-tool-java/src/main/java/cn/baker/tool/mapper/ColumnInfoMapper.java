package cn.baker.tool.mapper;

import cn.baker.tool.entity.ColumnInfo;
import cn.baker.tool.entity.vo.TableInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author baker.yuan
 */
public interface ColumnInfoMapper extends Mapper<ColumnInfo> {

    /**
     * 查询所有表的定义信息
     *
     * @return
     */
    List<TableInfo> getTables();

    /**
     * 模糊查询表的定义信息
     *
     * @param name 表名
     * @return
     */
    List<TableInfo> getTablesByName(@Param("name") String name);

    /**
     * 根据表名查询表中列的定义信息
     *
     * @param tableName 表名
     * @return
     */
    List<Map<String, String>> queryByTableName(@Param("tableName") String tableName);


    /**
     * 通过表名查询表自动信息
     *
     * @param tableName 表名
     * @return
     */
    List<ColumnInfo> findByTableNameOrderByIdAsc(@Param("tableName") String tableName);

    /**
     * 批量插入列的数据信息
     *
     * @param columnInfoList 列的数据信息集合
     */
    void batchInsert(@Param("columnInfoList") List<ColumnInfo> columnInfoList);
}
