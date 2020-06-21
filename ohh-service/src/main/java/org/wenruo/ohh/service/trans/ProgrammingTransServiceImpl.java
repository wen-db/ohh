package org.wenruo.ohh.service.trans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class ProgrammingTransServiceImpl {
    @Autowired
    private ProTransAdminServiceImple proTransAdminServiceImple;
    @Autowired
    private ProTransAdminLogServiceImpl proTransAdminLogService;
}
