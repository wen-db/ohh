
package org.wenruo.ohh.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 文得保 2018/1/24 0024.
 */
@RestController
public class IndexController {
    @Autowired
    @RequestMapping("hello")
    public Object hello() {
        return "hello spring boot";
    }
}
