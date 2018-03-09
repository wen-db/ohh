/**
 * Copyright (C), 2011-2018, 微贷网.
 */
package org.wenruo.ohh.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 文得保 2018/1/24 0024.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.wenruo"})
public class Application implements EmbeddedServletContainerCustomizer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(8888);
    }
}
