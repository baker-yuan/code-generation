/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package cn.baker.service;

import cn.baker.entity.ColumnInfo;
import cn.baker.entity.TableInfo;
import cn.baker.mapper.MySQLGeneratorDao;
import cn.baker.utils.GenUtils;
import cn.baker.utils.PageUtils;
import cn.baker.utils.Query;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysGeneratorService {


    @Resource
    private MySQLGeneratorDao generatorDao;

    public PageUtils queryList(Query query) {
        Page<?> page = PageHelper.startPage(query.getPage(), query.getLimit());
        List<TableInfo> list = generatorDao.queryList(query);
        int total = (int) page.getTotal();
        return new PageUtils(list, total, query.getLimit(), query.getPage());
    }


    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            //查询表信息
            TableInfo tableInfo = generatorDao.queryTable(tableName);
            System.out.println("表信息 => " + tableInfo);
            //查询列信息
            List<ColumnInfo> columnInfoList = generatorDao.queryColumns(tableName);
            System.out.println("列信息 => " + columnInfoList);
            //生成代码
            GenUtils.generatorCode(tableInfo, columnInfoList, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
