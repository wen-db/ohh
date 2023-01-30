package org.wenruo.dynamic.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.wenruo.dynamic.datasource.DynamicRoutingDataSource;
import org.wenruo.dynamic.datasource.exception.DynamicDatasourceException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wendebao
 * @Date 2020/3/31
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX)
public class DynamicDataSourceProperties implements InitializingBean {
    public static final String PREFIX = "dynamic.datasource";
    /**
     * 多数据源
     */
    private Map<String, DataSourceProperties> allDatasource = new LinkedHashMap<>();
    /**
     * 是否检查master数据源
     */
    private boolean checkMasterDatasource = true;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, DataSource> dataSourceMap = createDataSourceMap(allDatasource);
        if (CollectionUtils.isEmpty(dataSourceMap)) {
            throw new DynamicDatasourceException("no datasource init");
        }
        if (checkMasterDatasource && dataSourceMap.get(DynamicDataSourceContextHolder.DEFAULT_DATASOURCE) == null) {
            throw new DynamicDatasourceException("master datasource cannot be null");
        }
        DynamicRoutingDataSource.init(dataSourceMap);

    }

    private Map<String, DataSource> createDataSourceMap(Map<String, DataSourceProperties> dataSourcePropertiesMap) {
        Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size() * 2);
        for (Map.Entry<String, DataSourceProperties> item : dataSourcePropertiesMap.entrySet()) {
            DataSourceProperties dataSourceProperties = item.getValue();
            String pollName = dataSourceProperties.getPollName();
            if (StringUtils.isEmpty(pollName)) {
                pollName = item.getKey();
            }
            dataSourceProperties.setPollName(pollName);
            dataSourceMap.put(pollName, createDataSource(dataSourceProperties));
        }
        return dataSourceMap;
    }

    private DataSource createDataSource(DataSourceProperties dataSourceProperty) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());
        dataSource.setUrl(dataSourceProperty.getUrl());
        dataSource.setDriverClassName(dataSourceProperty.getDriverClassName());
        dataSource.setName(dataSourceProperty.getPollName());
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException("druid create error", e);
        }
        return dataSource;
    }

}
