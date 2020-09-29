package org.wenruo.ohh.service.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wendebao
 * @Date 2020/9/3
 **/
@Component
//@DependsOn("b")
//@Lazy
public class A {
  //  @Autowired
    private B b;

    public A(B b) {
    }
}

