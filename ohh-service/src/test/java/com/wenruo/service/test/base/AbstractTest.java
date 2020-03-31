package com.wenruo.service.test.base;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.wenruo.ohh.dao.mapper.AdminLoginLogMapper;
import org.wenruo.ohh.dao.mapper.AdminUserMapper;
import org.wenruo.ohh.dao.model.AdminLoginLog;
import org.wenruo.ohh.dao.model.AdminUser;

/**
 * @author wendebao
 * @Date 2020/3/23
 **/
public abstract class AbstractTest {
    @Autowired
    protected AdminUserMapper adminUserMapper;
    @Autowired
    protected AdminLoginLogMapper adminLoginLogMapper;

    public void checkResult(AdminUser adminUser, boolean targetCommit) {
        boolean execute = adminUser.getId() != null;
        System.out.println("A 数据是否执行：" + execute);
        boolean commit = execute && adminUserMapper.selectByPrimaryKey(adminUser.getId()) != null;
        System.out.println("A 事务是否提交：" + commit);
        assertEquals(targetCommit, commit);
    }

    public void checkResult(AdminLoginLog adminLoginLog, boolean targetCommit) {
        boolean execute = adminLoginLog.getId() != null;
        System.out.println("B 数据是否执行：" + execute);
        boolean commit =  execute && adminLoginLogMapper.selectByPrimaryKey(adminLoginLog.getId()) != null;
        System.out.println("B 事务是否提交：" + commit);
    }


    private void assertEquals(boolean targetCommit, boolean commit) {
        Assert.assertEquals(buildMsg(targetCommit, commit), targetCommit, commit);
    }

    private String buildMsg(boolean targetCommit, boolean commit) {
        return "事务预期：" + booleanToString(targetCommit) + "，实际：" + booleanToString(commit);
    }

    private String booleanToString(boolean commit) {
        return commit ? "提交" : "回滚";
    }

}
