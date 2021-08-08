package cn.baker.tool.service.impl;

import cn.baker.common.exception.BizException;
import cn.baker.common.search.PageResult;
import cn.baker.tool.config.DynamicDataSourceContextHolder;
import cn.baker.tool.entity.ColumnInfo;
import cn.baker.tool.entity.GenConfig;
import cn.baker.tool.entity.vo.TableInfoVO;
import cn.baker.tool.mapper.ColumnInfoMapper;
import cn.baker.tool.service.GeneratorService;
import cn.baker.tool.utils.FileUtil;
import cn.baker.tool.utils.GenUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ZipUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author baker.yuan
 */
@Slf4j
@Service
public class GeneratorServiceImpl implements GeneratorService {


    @Resource
    private ColumnInfoMapper columnInfoMapper;

    @Override
    public List<TableInfoVO> getTables(String dbName) {
        return columnInfoMapper.getTables(dbName);
    }

    @Override
    public PageResult<TableInfoVO> getTables(String dbName, String tbName, Integer page, Integer size) {
        DynamicDataSourceContextHolder.setDataSourceName(dbName);
        Integer count = columnInfoMapper.getTablesByNameCount(dbName, tbName);
        Integer start = (page - 1) * size;
        List<TableInfoVO> res = columnInfoMapper.getTablesByName(dbName, tbName, start, size);
        if (CollectionUtils.isNotEmpty(res)) {
            return new PageResult<>(count, res);
        }
        DynamicDataSourceContextHolder.clear();
        return new PageResult<>(0, Lists.newArrayList());
    }


    @Override
    public List<ColumnInfo> query(String dbName, String tableName) {
        List<ColumnInfo> columnInfoList = Lists.newArrayList();
        DynamicDataSourceContextHolder.setDataSourceName(dbName);
        List<Map<String, String>> columnInfoListMap = columnInfoMapper.queryByTableName(dbName, tableName);
        DynamicDataSourceContextHolder.clear();
        if (CollectionUtils.isEmpty(columnInfoListMap)) {
            return Lists.newArrayList();
        }
        for (Map<String, String> columnInfoMap : columnInfoListMap) {
            columnInfoList.add(new ColumnInfo(
                    tableName,
                    columnInfoMap.get("columnName"),
                    "NO".equals(columnInfoMap.get("isNullable")),
                    columnInfoMap.get("dataType"),
                    columnInfoMap.get("columnComment"),
                    columnInfoMap.get("columnKey"),
                    columnInfoMap.get("extra")));
        }
        return columnInfoList;
    }


    @Override
    public List<ColumnInfo> getColumns(String dbName, String tableName) {
        List<ColumnInfo> columnInfos = columnInfoMapper.findByTableNameOrderByIdAsc(tableName);
        if (CollectionUtils.isNotEmpty(columnInfos)) {
            return columnInfos;
        } else {
            columnInfos = query(dbName, tableName);
            columnInfoMapper.batchInsert(columnInfos);
            return columnInfoMapper.findByTableNameOrderByIdAsc(tableName);
        }
    }


    @Override
    public void sync(List<ColumnInfo> columnInfos, List<ColumnInfo> columnInfoList) {
        // 第一种情况,数据库类字段改变或者新增字段
        for (ColumnInfo columnInfo : columnInfoList) {
            // 根据字段名称查找
            List<ColumnInfo> columns = columnInfos.stream()
                    .filter(c -> c.getColumnName().equals(columnInfo.getColumnName()))
                    .collect(Collectors.toList());
            // 如果能找到，就修改部分可能被字段
            if (CollectionUtil.isNotEmpty(columns)) {
                ColumnInfo column = columns.get(0);
                column.setColumnType(columnInfo.getColumnType());
                column.setExtra(columnInfo.getExtra());
                column.setKeyType(columnInfo.getKeyType());
                if (StringUtils.isBlank(column.getRemark())) {
                    column.setRemark(columnInfo.getRemark());
                }
                columnInfoMapper.updateByPrimaryKeySelective(column);
            } else {
                // 如果找不到，则保存新字段信息
                columnInfoMapper.insertSelective(columnInfo);
            }
        }
        // 第二种情况,数据库字段删除了
        for (ColumnInfo columnInfo : columnInfos) {
            // 根据字段名称查找
            List<ColumnInfo> columns = columnInfoList.stream().filter(c -> c.getColumnName().equals(columnInfo.getColumnName())).collect(Collectors.toList());
            // 如果找不到，就代表字段被删除了，则需要删除该字段
            if (CollectionUtil.isEmpty(columns)) {
                columnInfoMapper.deleteByPrimaryKey(columnInfo.getId());
            }
        }
    }

    @Override
    public void save(List<ColumnInfo> columnInfos) {
//        columnInfoMapper.saveAll(columnInfos);
    }

    @Override
    public void generator(GenConfig genConfig, List<ColumnInfo> columns) {
        if (genConfig.getId() == null) {
            throw new BizException("请先配置生成器");
        }
        try {
            GenUtil.generatorCode(columns, genConfig);
        } catch (IOException e) {
            log.error("文件生成失败,err:[{}]", Throwables.getStackTraceAsString(e));
            throw new BizException("生成失败，请手动处理已生成的文件");
        }
    }

    @Override
    public List<Map<String, Object>> preview(GenConfig genConfig, List<ColumnInfo> columns) {
        if (genConfig.getId() == null) {
            throw new BizException("请先配置生成器");
        }
        return GenUtil.preview(columns, genConfig);
    }

    @Override
    public void download(GenConfig genConfig, List<ColumnInfo> columns, HttpServletRequest request, HttpServletResponse response) {
        if (genConfig == null) {
            throw new BizException("请先配置生成器");
        }
        try {
            File file = new File(GenUtil.download(columns, genConfig));
            String zipPath = file.getPath() + ".zip";
            ZipUtil.zip(file.getPath(), zipPath);
            FileUtil.downloadFile(request, response, new File(zipPath), true);
        } catch (IOException e) {
            throw new BizException("打包失败");
        }
    }
}
