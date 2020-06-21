package org.wenruo.ohh.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.wenruo.ohh.dao.mapper.AdminLoginLogMapper;
import org.wenruo.ohh.dao.mapper.AdminUserMapper;
import org.wenruo.ohh.dao.model.AdminLoginLog;
import org.wenruo.ohh.dao.model.AdminUser;

/**
 * 声明式事务
 *
 * @author wendebao
 * @Date 2020/3/21
 **/
@Service
@Slf4j
public class AnnAdminLogServiceImpl implements TransTestService<AdminLoginLog> {
    @Autowired
    private AdminLoginLogMapper adminLoginLogMapper;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert(AdminLoginLog adminLoginLog) {
        adminLoginLogMapper.insert(adminLoginLog);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = RuntimeException.class)
    public void newTrans(AdminLoginLog adminLoginLog) {
        adminLoginLogMapper.insert(adminLoginLog);
    }

    @Override
    public void throwRuntimeException(AdminLoginLog loginLog) {
        throwNewRuntimeException();
    }

    @Override
    public void insertAndThrowsRuntimeException(AdminLoginLog adminLoginLog) {
        adminLoginLogMapper.insert(adminLoginLog);
        throwNewRuntimeException();
    }

    @Override
    public void throwNewRuntimeException() {
        throw new RuntimeException("test trans annotation B throws new  runtimeException");
    }
}
