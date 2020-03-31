package org.wenruo.dynamic.datasource.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.wenruo.dynamic.datasource.config.DynamicDataSourceContextHolder;
import org.wenruo.dynamic.datasource.DS;

/**
 * @author wendebao
 * @Date 2020/3/31
 **/
@Aspect
@Slf4j
@Component
@Order(1)
public class DynamicAspectJ  {

    @Before("@annotation(org.wenruo.dynamic.datasource.DS))")
    public void switchDataSource(JoinPoint joinPoint) {
        if (joinPoint.getSignature() instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            DS ds = methodSignature.getMethod().getAnnotation(DS.class);
            DynamicDataSourceContextHolder.setDataSource(ds.value());
        }
    }

    @After("@annotation(org.wenruo.dynamic.datasource.DS))")
    public void restoreDataSource() {
        DynamicDataSourceContextHolder.clear();
    }


}
