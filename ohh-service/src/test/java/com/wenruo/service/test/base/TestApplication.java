/**
 * Copyright (C), 2011-2018, 微贷网.
 */
package com.wenruo.service.test.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 文得保 2018/1/24 0024.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.wenruo.ohh"})
@MapperScan("org.wenruo.ohh.dao.mapper")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }


}
