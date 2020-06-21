package org.wenruo.ohh.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.wenruo.ohh.dao.mapper.AdminLoginLogMapper;
import org.wenruo.ohh.dao.model.AdminLoginLog;
import org.wenruo.ohh.dao.model.AdminUser;

/**
 * @author wendebao
 * @Date 2020/3/23
 **/
@Service
@Slf4j
public class ProTransAdminLogServiceImpl implements TransTestService<AdminLoginLog> {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private AdminLoginLogMapper adminLoginLogMapper;


    @Override
    public void insert(AdminLoginLog adminLoginLog) {
        adminLoginLogMapper.insert(adminLoginLog);
    }

    @Override
    public void newTrans(AdminLoginLog adminLoginLog) {
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            adminLoginLogMapper.insert(adminLoginLog);
            return null;
        });
    }

    @Override
    public void throwRuntimeException(AdminLoginLog adminLoginLog) {
        transactionTemplate.execute(status -> {
            adminLoginLogMapper.insert(adminLoginLog);
            return null;
        });
        throwNewRuntimeException();

    }

    @Override
    public void insertAndThrowsRuntimeException(AdminLoginLog adminLoginLog) {
        transactionTemplate.execute(status -> {
            try {
                adminLoginLogMapper.insert(adminLoginLog);
                throwNewRuntimeException();
                return null;
            } catch (Exception e) {
                log.error("", e);
            }
            return null;
        });
    }

    public void throwNewRuntimeException() {
        throw new RuntimeException(" new Programming B runtime exception");
    }
}
