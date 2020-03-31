package org.wenruo.dynamic.datasource.config;

import java.util.function.Supplier;

/**
 * @author wendebao
 * @Date 2020/3/31
 **/
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> THREAD_DATA_LOCAL = ThreadLocal.withInitial(() -> null);
    public static String DEFAULT_DATASOURCE = "master";

    public static String getDataSource() {
        return THREAD_DATA_LOCAL.get();
    }

    public static void setDataSource(String targetDataSource) {
        THREAD_DATA_LOCAL.set(targetDataSource);
    }

    public static void clear() {
        THREAD_DATA_LOCAL.remove();
    }
}
