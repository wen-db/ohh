/**
 * Copyright (C), 2011-2018, 微贷网.
 */
package org.wenruo.ohh.web;

import org.wenruo.ohh.service.admin.AnnotationTransAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 文得保 2018/1/24 0024.
 */
@RestController
public class IndexController {
    @Autowired
    private AnnotationTransAService annotationTransAService;
    @Autowired
    @RequestMapping("hello")
    public Object hello() {
        return "hello spring boot";
    }
}
