package org.wenruo.ohh.service.ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wendebao
 * @Date 2020/9/3
 * Spring在创建Bean的过程中分为三步
 * <p>
 * 1.实例化，对应方法：AbstractAutowireCapableBeanFactory中的createBeanInstance方法
 * <p>
 * 2.属性注入，对应方法：AbstractAutowireCapableBeanFactory的populateBean方法
 * <p>
 * 3.初始化，对应方法：AbstractAutowireCapableBeanFactory的initializeBean
 **/
@Component

public class B  {
    @Autowired
    private A a;

    public B() {

    }
}
