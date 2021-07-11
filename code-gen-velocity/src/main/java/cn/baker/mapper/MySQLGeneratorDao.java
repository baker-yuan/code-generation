package cn.baker.mapper;

import cn.baker.entity.ColumnInfo;
import cn.baker.entity.TableInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * MySQL代码生成器
 */
@Mapper
public interface MySQLGeneratorDao {

    /**
     * @param map
     * @return
     */
    List<TableInfo> queryList(Map<String, Object> map);

    /**
     * @param tableName
     * @return
     */
    TableInfo  queryTable(String tableName);

    /**
     * @param tableName 表名
     * @return
     */
    List<ColumnInfo> queryColumns(String tableName);

}
