package org.wenruo.ohh.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.wenruo.dynamic.datasource.TargetDataSource;
import org.wenruo.ohh.dao.mapper.AdminUserMapper;
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
public class AnnotationTransAService implements TransTestService<AdminUser> {
    @Autowired
    private AdminUserMapper adminUserMapper;


    private void throwRuntimeException() {
        throw new RuntimeException("test trans annotation A throws new  runtimeException");
    }


    @Override
    public void tesInsert(AdminUser adminUser) {
        adminUserMapper.insert(adminUser);
    }

    @Override
    public void testThrowRuntimeException(AdminUser adminUser) {
        throwRuntimeException();
    }

    @Override
    @TargetDataSource("d2")
    public void testInsertAndThrowsRuntimeException(AdminUser adminUser) {
        adminUserMapper.insert(adminUser);
        throwRuntimeException();
    }
    public void testInsertAndTryCatch(AdminUser adminUser) {
        try {
            adminUserMapper.insert(adminUser);
            throwRuntimeException();
        } catch (Exception e) {
            log.error("", e);
        }

    }
}
