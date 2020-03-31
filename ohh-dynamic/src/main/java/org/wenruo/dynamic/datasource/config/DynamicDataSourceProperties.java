package org.wenruo.dynamic.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.wenruo.dynamic.datasource.DynamicRoutingDataSource;

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
    private Map<String, DataSourceProperties> allDatasource = new LinkedHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        DynamicRoutingDataSource.init(createDataSourceMap(allDatasource));

    }

    private Map<String, DataSource> createDataSourceMap(Map<String, DataSourceProperties> dataSourcePropertiesMap) {
        Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size() * 2);
        for (Map.Entry<String, DataSourceProperties> item : dataSourcePropertiesMap.entrySet()) {
            DataSourceProperties dataSourceProperty = item.getValue();
            String pollName = dataSourceProperty.getPollName();
            if (pollName == null || "".equals(pollName)) {
                pollName = item.getKey();
            }
            dataSourceProperty.setPollName(pollName);
            dataSourceMap.put(pollName, createDataSource(dataSourceProperty));
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
