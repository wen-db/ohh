package org.wenruo.dynamic.datasource;

import java.lang.annotation.*;

/**
 * @author wendebao
 * @Date 2020/3/21
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value()  ;
}
