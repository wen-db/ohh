package org.wenruo.ohh.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wenruo.dynamic.datasource.DS;
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
public class AnnAdminUserServiceImpl implements TransTestService<AdminUser> {
    @Autowired
    private AdminUserMapper adminUserMapper;


    @Override
    public void insert(AdminUser adminUser) {
        adminUserMapper.insert(adminUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = RuntimeException.class)
    public void newTrans(AdminUser adminLoginLog) {
        adminUserMapper.insert(adminLoginLog);
    }
    @Override
    public void throwRuntimeException(AdminUser adminUser) {
        throwNewRuntimeException();
    }

    @Override
    public void insertAndThrowsRuntimeException(AdminUser adminUser) {
        adminUserMapper.insert(adminUser);
        throwNewRuntimeException();
    }

    @Override
    public void throwNewRuntimeException() {
        throw new RuntimeException("test trans annotation A throws new  runtimeException");

    }


}
