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
@Transactional(rollbackFor = RuntimeException.class)
public class AnnotationTransBService implements TransTestService<AdminLoginLog> {
    @Autowired
    private AdminLoginLogMapper adminLoginLogMapper;


    private void throwRuntimeException() {
        throw new RuntimeException("test trans annotation B throws new  runtimeException");
    }


    @Override
    public void tesInsert(AdminLoginLog adminLoginLog) {
        adminLoginLogMapper.insert(adminLoginLog);
    }

    @Override
    public void testThrowRuntimeException(AdminLoginLog loginLog) {
        throwRuntimeException();
    }

    @Override
    public void testInsertAndThrowsRuntimeException(AdminLoginLog adminLoginLog) {
        adminLoginLogMapper.insert(adminLoginLog);
        throwRuntimeException();
    }
}
