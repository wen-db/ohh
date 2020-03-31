package org.wenruo.ohh.service.trans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wenruo.ohh.dao.model.AdminLoginLog;
import org.wenruo.ohh.dao.model.AdminUser;
import org.wenruo.ohh.service.admin.AnnotationTransAService;
import org.wenruo.ohh.service.admin.AnnotationTransBService;

/**
 * @author wendebao
 * @Date 2020/3/23
 **/
@Service
@Slf4j
public class AnnotationTransService {
    @Autowired
    private AnnotationTransAService aService;
    @Autowired
    private AnnotationTransBService bService;


    @Transactional(rollbackFor = RuntimeException.class)
    public void testTransInnerTryCatch(AdminUser adminUser) {
        aService.testInsertAndTryCatch(adminUser);
    }

    public void test_notTrans_TransInnerTryCatch(AdminUser adminUser) {
        try {
            aService.testInsertAndThrowsRuntimeException(adminUser);
        } catch (Exception e) {
            log.error("", e);
        }
    }
    @Transactional(rollbackFor = RuntimeException.class)
    public void testTransAfterTryCatchAndInsert(AdminUser adminUser, AdminLoginLog adminLoginLog) {
        try {
            aService.testInsertAndThrowsRuntimeException(adminUser);
        } catch (Exception e) {
            log.error("", e);
        }
        bService.tesInsert(adminLoginLog);
    }
}
