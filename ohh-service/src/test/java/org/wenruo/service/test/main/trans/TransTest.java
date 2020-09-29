package org.wenruo.service.test.main.trans;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.test.context.junit4.SpringRunner;
import org.wenruo.ohh.dao.model.AdminLoginLog;
import org.wenruo.ohh.dao.model.AdminUser;
import org.wenruo.ohh.service.trans.TransTestServiceImpl;
import org.wenruo.service.test.base.AbstractTest;
import org.wenruo.service.test.base.TestApplication;

import java.util.Date;

/**
 * @author wendebao
 * @Date 2020/3/23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@Slf4j
public class TransTest extends AbstractTest {
    @Autowired
    private TransTestServiceImpl transTestService;

    static {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "target/w");
    }

    @Test
    public void ann() {
        AdminLoginLog adminLoginLog = buildAdminLog();
        AdminUser adminUser = buildAdminUser();
        try {
            transTestService.testAnn(adminUser, adminLoginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkResult(adminUser, false);
        checkResult(adminLoginLog, false);
    }

    @Test
    public void pro() {
        AdminLoginLog adminLoginLog = buildAdminLog();
        AdminUser adminUser = buildAdminUser();
        try {
            transTestService.testPro(adminUser, adminLoginLog);
            transTestService.testPro(adminUser, adminLoginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("事务是否提交："+isCommit(adminLoginLog));
    }

    @Test
    public void blend() {
        AdminLoginLog adminLoginLog = buildAdminLog();
        AdminUser adminUser = buildAdminUser();
        try {
            transTestService.testBlend(adminUser, adminLoginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkResult(adminUser, false);
        checkResult(adminLoginLog, false);
    }

    @Test
    public void PROPAGATION_NESTED_exception_Subtransaction() {
        AdminLoginLog adminLoginLog = buildAdminLog();
        AdminUser adminUser = buildAdminUser();
        try {
            transTestService.PROPAGATION_NESTED_exception_Subtransaction(adminUser, adminLoginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = "嵌套事务，子事务异常。";
        System.out.println(s + "主事务提交：" + isCommit(adminUser) + " 子事务提交：" + isCommit(adminLoginLog));
    }

    @Test
    public void PROPAGATION_NESTED_exception() {
        AdminLoginLog adminLoginLog = buildAdminLog();
        AdminUser adminUser = buildAdminUser();
        try {
            transTestService.PROPAGATION_NESTED(adminUser, adminLoginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = "嵌套事务，主事务异常。";
        System.out.println(s + "主事务提交：" + isCommit(adminUser) + " 子事务提交：" + isCommit(adminLoginLog));
    }

    @Test
    public void simple_exception() {
        AdminLoginLog adminLoginLog = buildAdminLog();
        AdminUser adminUser = buildAdminUser();
        try {
            Class clazz = transTestService.getClass();
            String name = clazz.getName();
            transTestService.exception_Subtransaction(adminUser, adminLoginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = "普通事务，子事务异常并在主事务中捕获。";
        System.out.println(s + "主事务提交：" + isCommit(adminUser) + " 子事务提交：" + isCommit(adminLoginLog));
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
