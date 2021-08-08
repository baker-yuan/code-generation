package cn.baker.tool.service;


import cn.baker.common.search.PageResult;
import cn.baker.tool.entity.ColumnInfo;
import cn.baker.tool.entity.GenConfig;
import cn.baker.tool.entity.vo.TableInfoVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * @author baker.yuan
 */
public interface GeneratorService {

    /**
     * 获取所有table
     *
     * @return /
     */
    List<TableInfoVO> getTables(String dbName);


    /**
     * 查询数据库元数据
     *
     * @param tbName 表名
     * @param page 页码
     * @param size 页面大小
     * @return /
     */
    PageResult<TableInfoVO> getTables(String dbName, String tbName, Integer page, Integer size);

    /**
     * 查询数据库的表字段数据数据
     *
     * @param table /
     * @return /
     */
    List<ColumnInfo> query(String dbName, String table);


    /**
     * 得到数据表的元数据
     *
     * @param name 表名
     * @return /
     */
    List<ColumnInfo> getColumns(String dbName, String name);

    /**
     * 同步表数据
     *
     * @param columnInfos    数据表的元数据
     * @param columnInfoList 数据库的表字段数据数据
     */
    void sync(List<ColumnInfo> columnInfos, List<ColumnInfo> columnInfoList);

    /**
     * 保持数据
     *
     * @param columnInfos /
     */
    void save(List<ColumnInfo> columnInfos);


    /**
     * 代码生成
     *
     * @param genConfig 配置信息
     * @param columns   字段信息
     */
    void generator(GenConfig genConfig, List<ColumnInfo> columns);

    /**
     * 预览
     *
     * @param genConfig 配置信息
     * @param columns   字段信息
     * @return /
     */
    List<Map<String, Object>> preview(GenConfig genConfig, List<ColumnInfo> columns);

    /**
     * 打包下载
     *
     * @param genConfig 配置信息
     * @param columns   字段信息
     * @param request   /
     * @param response  /
     */
    void download(GenConfig genConfig, List<ColumnInfo> columns, HttpServletRequest request, HttpServletResponse response);
}
