package cn.baker.tool.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author baker.yuan
 */
@Slf4j
@Configuration
public class DataSourceAutoConfig implements BeanFactoryPostProcessor, EnvironmentAware {

    private String dataSourceConfig;

    private ConfigurableListableBeanFactory beanFactory;

    @Bean("mainDataSource")
    //@ConfigurationProperties("spring.datasource")
    public DataSource mainDataSource() {
        DataSource dataSource = DataSourceBuilder.create()
                .username("root")
                .password("123456")
                .url("jdbc:mysql://121.36.33.154:3306/ren_ren_fast?characterEncoding=utf-8&useUnicode=true&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
        return dataSource;
    }

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(DataSource mainDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("mainDataSource", mainDataSource);

        Map<String, DataSource> beanMap = beanFactory.getBeansOfType(DataSource.class);
        if (beanMap != null && !beanMap.isEmpty()) {
            targetDataSources.putAll(beanMap);
        }

        DynamicDataSource ds = new DynamicDataSource();
        // 设置数据源映射
        ds.setTargetDataSources(targetDataSources);
        // 设置默认数据源，当无法映射到数据源时会使用默认数据源
        ds.setDefaultTargetDataSource(mainDataSource);
        return ds;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.dataSourceConfig = environment.getProperty("codeGen.dataSourceConfig");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        List<Pair<String, DataSource>> dataSourceList = parseConfig();
        for (Pair<String, DataSource> pair : dataSourceList) {
            beanFactory.registerSingleton(pair.getKey(), pair.getValue());
        }
    }

    private List<Pair<String, DataSource>> parseConfig() {
        log.info(">>>>>>>>>> parse dataSource, config:[{}]", dataSourceConfig);
        if (StringUtils.isBlank(dataSourceConfig)) {
            return Lists.newArrayList();
        }
        List<Pair<String, DataSource>> dataSourceList = Lists.newArrayList();
        try {
            JSONArray jsonArray = JSON.parseArray(dataSourceConfig);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
                dataSourceBuilder.url(jsonObject.getString("url"));
                dataSourceBuilder.username(jsonObject.getString("username"));
                dataSourceBuilder.password(jsonObject.getString("password"));
                dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
                dataSourceList.add(Pair.of(jsonObject.getString("dbName"), dataSourceBuilder.build()));
            }
            return dataSourceList;
        } catch (Exception e) {
            log.error("init dataSource fail, config:[{}], err:[{}]", dataSourceConfig, Throwables.getStackTraceAsString(e));
            return Lists.newArrayList();
        }
    }
}
