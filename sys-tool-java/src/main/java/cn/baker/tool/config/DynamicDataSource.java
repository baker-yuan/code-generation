package cn.baker.tool.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author baker.yuan
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dsName = DynamicDataSourceContextHolder.getDataSourceName();
        log.debug(">>>>>>>>>> change dataSource {}", dsName);
        return dsName;
    }

}