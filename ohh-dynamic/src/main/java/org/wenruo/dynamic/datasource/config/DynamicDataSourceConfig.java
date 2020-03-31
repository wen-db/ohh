package org.wenruo.dynamic.datasource.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wenruo.dynamic.datasource.DynamicRoutingDataSource;

import javax.sql.DataSource;

/**
 * @author wendebao
 * @Date 2020/3/31
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DynamicDataSourceConfig {
    private  DynamicDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DataSource dynamicDataSource(){
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        return dynamicRoutingDataSource;
    }
}
