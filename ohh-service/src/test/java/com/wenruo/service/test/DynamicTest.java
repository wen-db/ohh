package com.wenruo.service.test;

import com.wenruo.service.test.base.AbstractTest;
import com.wenruo.service.test.base.TestDynamicApplication;
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
import org.wenruo.ohh.service.trans.AnnotationTransService;

import java.util.Date;

/**
 * @author wendebao
 * @Date 2020/3/23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDynamicApplication.class)
@Slf4j
public class DynamicTest extends AbstractTest {
    @Autowired
    private AnnotationTransService annotationTransService;

    /**
     * 在事务方法内抛出异常，在事务外吃掉，事务会回滚
     */
    @Test
    @DS("d0")
    public void test_notTrans_TransInnerTryCatch() {
        AdminUser adminUser = buildAdminUser();
        annotationTransService.test_notTrans_TransInnerTryCatch(adminUser);
        checkResult(adminUser, false);

    }

    /**
     * 在事务方法内抛出异常，在事务外吃掉，然后再尝试提交事务，会报rollback-only异常
     * 事务全部回滚
     */
    @Test
    public void testTransAfterTryCatch() {
        AdminUser adminUser = buildAdminUser();
        AdminLoginLog adminLoginLog = buildAdminLog();
        try {
            annotationTransService.testTransAfterTryCatchAndInsert(adminUser, adminLoginLog);
        } catch (Exception e) {
            log.error("", e);
            Assert.assertTrue("预期 rollback-only 异常类", e instanceof UnexpectedRollbackException);
            Assert.assertEquals("预期 rollback-only 信息", "Transaction rolled back because it has been marked as rollback-only", e.getMessage());
        }
        checkResult(adminUser, false);
        checkResult(adminLoginLog, false);
    }

    /**
     * 在事务方法内抛出异常，在事务内吃掉，事务会提交
     */
    @Test
    public void testTransInnerTryCatch() {
        AdminUser adminUser = buildAdminUser();
        annotationTransService.testTransInnerTryCatch(adminUser);
        checkResult(adminUser, true);

    }

    @Test
    public void  t(){
        MybatisProperties mybatisProperties  =new MybatisProperties();
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
