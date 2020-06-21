package org.wenruo.ohh.service.trans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wenruo.ohh.dao.model.AdminLoginLog;
import org.wenruo.ohh.dao.model.AdminUser;
import org.wenruo.ohh.service.admin.AnnAdminLogServiceImpl;
import org.wenruo.ohh.service.admin.AnnAdminUserServiceImpl;

/**
 * @author wendebao
 * @Date 2020/3/23
 **/
@Service
@Slf4j
public class AnnotationTransServiceImpl {
    @Autowired
    private AnnAdminLogServiceImpl annAdminLogService;
    @Autowired
    private AnnAdminUserServiceImpl annAdminUserService;


    @Transactional(rollbackFor = RuntimeException.class)
    public void testTransInnerTryCatch(AdminUser adminUser) {
        annAdminUserService.insertAndThrowsRuntimeException(adminUser);
    }

    public void test_notTrans_TransInnerTryCatch(AdminUser adminUser) {
        try {
            annAdminUserService.insert(adminUser);
        } catch (Exception e) {
            log.error("", e);
        }
    }
    @Transactional(rollbackFor = RuntimeException.class)
    public void testNewTrans(AdminUser adminUser, AdminLoginLog adminLoginLog) {
      annAdminUserService.newTrans(adminUser);
      annAdminLogService.insertAndThrowsRuntimeException(adminLoginLog);

    }
}
