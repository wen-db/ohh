package org.wenruo.service.test.main.datasource;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.UnexpectedRollbackException;
import org.wenruo.dynamic.datasource.DS;
import org.wenruo.ohh.dao.model.AdminLoginLog;
import org.wenruo.ohh.dao.model.AdminUser;
import org.wenruo.ohh.dao.plugs.page.Page;
import org.wenruo.ohh.service.trans.AnnotationTransServiceImpl;
import org.wenruo.service.test.base.AbstractTest;
import org.wenruo.service.test.base.TestDynamicApplication;

import java.util.Date;
import java.util.List;

/**
 * @author wendebao
 * @Date 2020/3/23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDynamicApplication.class)
@Slf4j
public class MybatisPlugsTest extends AbstractTest {


    @Test
    public void  t(){
        adminUserMapper.insert(buildAdminUser());
    }
    @Test
    public void  query(){
        Page page = new Page(1,10);
        System.out.println(page.getOffset());
        List<AdminUser> list =  adminUserMapper.adminPageList(0,10);
        System.out.println(list.size());
    }

    private AdminLoginLog buildAdminLog() {
        AdminLoginLog log = new AdminLoginLog();
        log.setLoginDate(new Date());
        log.setMsg("dfas");
        return log;
    }

    private AdminUser buildAdminUser() {
        AdminUser adminUser = new AdminUser();
        adminUser.setName1(System.currentTimeMillis() + "");
        adminUser.setMsg("ProgrammingTransTest");
        return adminUser;
    }
}
