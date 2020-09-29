package org.wenruo.ohh.service.trans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.wenruo.ohh.dao.mapper.AdminUserMapper;
import org.wenruo.ohh.dao.model.AdminUser;

/**
 * @author wendebao
 * @Date 2020/4/29
 **/
@Service
@Slf4j
public class ProgrammingTransServiceImpl {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private AdminUserMapper adminUserMapper;

    public void testTransInnerTryCatch(AdminUser adminUser) {
        transactionTemplate.execute(status -> {
            adminUserMapper.insert(adminUser);
            return null;
        });
    }
}
