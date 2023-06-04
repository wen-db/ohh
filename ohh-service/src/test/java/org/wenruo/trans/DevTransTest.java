package org.wenruo.trans;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.wenruo.ohh.dao.mapper.AccountFlowMapper;
import org.wenruo.ohh.dao.mapper.AccountMapper;
import org.wenruo.ohh.dao.model.Account;
import org.wenruo.ohh.service.entity.TradeEntity;
import org.wenruo.ohh.service.enums.TradeTypeEnum;
import org.wenruo.ohh.service.trans.test.DevAccountService;
import org.wenruo.service.test.base.TestApplication;

import java.util.UUID;

/**
 * @author wendb
 * @date 2023/5/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@EnableAspectJAutoProxy(exposeProxy = true)
@Slf4j
public class DevTransTest {
    @Autowired
    private DevAccountService devAccountService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFlowMapper accountFlowMapper;

    public void assertResult(Account before, long exAmount) {
        System.out.println("账户:" + before.getAccountId() + "交易前余额:" + before.getAmount());
        Account after = accountMapper.selectByAccountId(before.getAccountId());
        System.out.println("账户:" + after.getAccountId() + "交易后余额:" + after.getAmount());
        Assert.assertEquals(exAmount, (long) after.getAmount());

    }


    private TradeEntity buildEntity() {
        String orderNo = UUID.randomUUID().toString();
        String accountId = "A01";
        TradeTypeEnum tradeType = TradeTypeEnum.IN;
        long tradeAmount = 1;
        return TradeEntity.builder().orderNo(orderNo).accountId(accountId).tradeType(tradeType).tradeAmount(tradeAmount).build();
    }

    /**
     *
     */
    @Test
    public void t1() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            devAccountService.testThrowError(entity);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        assertResult(before, entity.getTradeAmount() + 1);
    }


    /**
     * 正常回滚
     */
    @Test
    public void rollback() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            devAccountService.testRollback(entity);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount());
    }

    @Test
    public void rollbackMyException() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            devAccountService.rollbackMyException(entity);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount()+1);
    }
    @Test
    public void rollbackOnly() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            devAccountService.rollbackOnly(entity);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount()+1);
    }




    /**
     * 正常提交
     */
    @Test
    public void commit() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            devAccountService.testCommit(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, entity.getTradeAmount() + 1);
    }

    @Test
    public void testNested() {

        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        System.out.println("before:" + before.getAmount());
        try {
            devAccountService.testNested(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Account after = accountMapper.selectByAccountId(entity.getAccountId());
        System.out.println("after:" + after.getAmount());
    }




}
