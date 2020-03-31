package org.wenruo.ohh.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.wenruo.ohh.dao.mapper.AdminUserMapper;
import org.wenruo.ohh.dao.model.AdminUser;

/**
 * 编程示事务
 *
 * @author wendebao
 * @Date 2020/3/23
 **/
@Service
@Slf4j
public class ProgrammingTransAService implements TransTestService<AdminUser> {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private AdminUserMapper adminUserMapper;


    @Override
    public void tesInsert(AdminUser adminUser) {
        adminUserMapper.insert(adminUser);
    }

    @Override
    public void testThrowRuntimeException(AdminUser adminUser) {
        transactionTemplate.execute(status -> {
            try {
                adminUserMapper.insert(adminUser);
                return null;
            } catch (Exception e) {
                log.error("", e);
            }
            return null;

        });
        throwNewRuntimeException();
    }

    @Override
    public void testInsertAndThrowsRuntimeException(AdminUser adminUser) {
        transactionTemplate.execute(status -> {
            try {
                adminUserMapper.insert(adminUser);
                throwNewRuntimeException();
                return null;
            } catch (Exception e) {
                log.error("", e);
            }
            return null;

        });
    }


    private void throwNewRuntimeException() {
        throw new RuntimeException(" new Programming runtime exception");
    }
}
