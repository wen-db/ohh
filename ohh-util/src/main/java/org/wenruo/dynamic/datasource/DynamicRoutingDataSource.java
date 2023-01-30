package org.wenruo.dynamic.datasource;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.wenruo.dynamic.datasource.config.DynamicDataSourceContextHolder;
import org.wenruo.dynamic.datasource.exception.DynamicDatasourceException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author wendebao
 * @Date 2020/3/31
 **/
public class DynamicRoutingDataSource extends AbstractDataSource {
    /**
     * 所有数据库
     */
    private static Map<String, DataSource> dataSourceMap;
    @Override
    public Connection getConnection() throws SQLException {
        return getDatasource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getDatasource().getConnection(username, password);
    }

    private DataSource getDatasource() {
        String datasourceName = DynamicDataSourceContextHolder.getDataSource();
        if (datasourceName == null) {
            //未配置动态数据源注解，使用默认数据源
            datasourceName = DynamicDataSourceContextHolder.DEFAULT_DATASOURCE;
        }
        DataSource dataSource = dataSourceMap.get(datasourceName);
        if (dataSource == null) {
            throw new DynamicDatasourceException("can not routing datasource");
        }
        return dataSource;
    }

    public static void init(Map<String, DataSource> dataSourceMap) {
        DynamicRoutingDataSource.dataSourceMap = dataSourceMap;

    }

}
