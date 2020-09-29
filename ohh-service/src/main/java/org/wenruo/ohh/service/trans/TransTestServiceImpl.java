package org.wenruo.ohh.service.trans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.wenruo.ohh.dao.mapper.AdminLoginLogMapper;
import org.wenruo.ohh.dao.mapper.AdminUserMapper;
import org.wenruo.ohh.dao.model.AdminLoginLog;
import org.wenruo.ohh.dao.model.AdminUser;

import java.util.List;

/**
 * @author wendebao
 * @Date 2020/4/29
 **/
@Service
@Slf4j
public class TransTestServiceImpl {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private AdminLoginLogMapper adminLoginLogMapper;
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private AnnotationTransServiceImpl annotationTransService;


    /**
     * 注解事务
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void testAnn(AdminUser adminUser, AdminLoginLog adminLoginLog) {
        adminUserMapper.insert(adminUser);
        adminLoginLogMapper.insert(adminLoginLog);
    }

    /**
     * 编程事务
     */
    public void testPro(AdminUser adminUser, AdminLoginLog adminLoginLog) {
        AdminUser user = new AdminUser();
        user.setMsg("234");
        user.setName1("232323");
        TransactionSynchronization synchronization = new TransactionSynchronization() {
            @Override
            public void suspend() {

            }

            @Override
            public void resume() {

            }

            @Override
            public void flush() {

            }

            @Override
            public void beforeCommit(boolean readOnly) {

            }

            @Override
            public void beforeCompletion() {

            }

            @Override
            public void afterCommit() {
                System.out.println("事务提交了");
            }

            @Override
            public void afterCompletion(int status) {
                if (TransactionSynchronization.STATUS_COMMITTED == status) {
                    System.out.println("事务已提交");
                } else if (TransactionSynchronization.STATUS_ROLLED_BACK == status) {
                    System.out.println("事务已回滚");
                } else if (TransactionSynchronization.STATUS_UNKNOWN == status) {
                    System.out.println("事务状态未知");
                } else {
                    System.out.println("事务状态未知:" + status);
                }
                    adminUserMapper.insert(user);

            }
        };
        transactionTemplate.execute(status -> {
            adminUserMapper.insert(adminUser);
            adminLoginLogMapper.insert(adminLoginLog);
        TransactionSynchronizationManager.registerSynchronization(synchronization);
           // throw new RuntimeException("");
            return null;
        });
        System.out.println("afterCompletion是否提交："+(adminUserMapper.selectByPrimaryKey(user.getId())!=null));
        System.out.println("事务是否提交："+(adminUserMapper.selectByPrimaryKey(adminUser.getId())!=null));

    }

    /**
     * 混合事务
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void testBlend(AdminUser adminUser, AdminLoginLog adminLoginLog) {
        adminUserMapper.insert(adminUser);
        transactionTemplate.execute(status -> {
            adminLoginLogMapper.insert(adminLoginLog);
            return null;
        });
        throw new RuntimeException("");
    }

    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.NESTED)
    public void PROPAGATION_NESTED_exception_Subtransaction(AdminUser adminUser, AdminLoginLog adminLoginLog) {
        adminUserMapper.insert(adminUser);
        try {
            annotationTransService.PROPAGATION_NESTED_Exception(adminLoginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.NESTED)
    public void PROPAGATION_NESTED(AdminUser adminUser, AdminLoginLog adminLoginLog) {
        adminUserMapper.insert(adminUser);
        annotationTransService.PROPAGATION_NESTED(adminLoginLog);
        throw new RuntimeException("PROPAGATION_NESTED");
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void exception_Subtransaction(AdminUser adminUser, AdminLoginLog adminLoginLog) {
        adminUserMapper.insert(adminUser);
        try {
            annotationTransService.simple(adminLoginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
