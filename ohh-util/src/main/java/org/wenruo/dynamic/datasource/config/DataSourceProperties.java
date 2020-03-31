package org.wenruo.dynamic.datasource.config;


import lombok.Data;

/**
 * @author wendebao
 * @Date 2020/3/31
 **/
@Data
public class DataSourceProperties {
    /**
     * 连接池名称
     */
    private String pollName;
    /**
     * JDBC driver
     */
    private String driverClassName;
    /**
     * JDBC url 地址
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;

}
