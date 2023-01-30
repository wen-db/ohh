package org.wenruo.ohh.service.ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author wendebao
 * @Date 2020/9/3
 **/
@Component
public class BeanInstanceSortPostProcessBeanFactory implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
        BeanDefinition a= factory.getBeanDefinition("a");
        BeanDefinition b= factory.getBeanDefinition("b");
        factory.removeBeanDefinition("a");
        factory.registerBeanDefinition("a",a);
    }
}
