package org.wenruo.ohh.service.trans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.wenruo.ohh.dao.mapper.AdminLoginLogMapper;
import org.wenruo.ohh.dao.mapper.AdminUserMapper;
import org.wenruo.ohh.service.admin.AnnAdminLogServiceImpl;
import org.wenruo.ohh.service.admin.AnnAdminUserServiceImpl;
import org.wenruo.ohh.service.admin.ProTransAdminLogServiceImpl;
import org.wenruo.ohh.service.admin.ProTransAdminServiceImple;

/**
 * @author wendebao
 * @Date 2020/4/29
 **/
@Service
@Slf4j
public class BlendTransServiceImpl {
    @Autowired
    private AnnAdminLogServiceImpl annAdminLogService;
    @Autowired
    private AnnAdminUserServiceImpl annAdminUserService;
    @Autowired
    private ProTransAdminServiceImple proTransAdminServiceImple;
    @Autowired
    private ProTransAdminLogServiceImpl proTransAdminLogService;
}
