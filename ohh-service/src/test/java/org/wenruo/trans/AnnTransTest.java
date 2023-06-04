package org.wenruo.trans;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.wenruo.ohh.dao.mapper.AccountMapper;
import org.wenruo.ohh.dao.model.Account;
import org.wenruo.ohh.service.entity.TradeEntity;
import org.wenruo.ohh.service.enums.TradeTypeEnum;
import org.wenruo.ohh.service.trans.test.AnnAService;
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
public class AnnTransTest {
    @Autowired
    private AnnAService bService;
    @Autowired
    private AccountMapper accountMapper;

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
     * 非public方法
     */
    @Test
    public void protectedTrade() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.protectedTrade(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount() + 1);
    }

    /**
     * 非spring bean
     */
    @Test
    public void notSpringBean() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            AnnAService service = new AnnAService();
            service.setAccountMapper(accountMapper);
            service.trade(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount() + 1);
    }

    /**
     * 方法内部调用
     */
    @Test
    public void inner() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.inner(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount() + 1);
    }

    @Test
    public void innerTrue() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.innerTrue(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount());
    }


    /**
     * 跨线程，调用
     */
    @Test
    public void newThread() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.newThread(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount() + 1);
    }

    @Test
    public void newThreadTrue() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.newThreadTrue(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount());
    }


    /**
     * 业务代码异常，catch后未抛出异常
     */
    @Test
    public void tryCatch() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.tryCatch(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount());
    }

    /**
     * 嵌套事务
     */
    @Test
    public void tradeNested() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.tradeNested(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount() + 1);
    }

    @Test
    public void transMq1() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.testMq1(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount() + 1);
    }
    @Test
    public void transMq2() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.testMq2(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount() + 1);
    }

    @Test
    public void transMq3() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.testMq3(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount() + 1);
    }

    @Test
    public void rollbackOfException() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.rollbackOfException(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertResult(before, before.getAmount());
    }
    @Test
    public void rollbackOfError() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        try {
            bService.rollbackOfError(entity);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //抛出的是runtimeException或error，事务回滚所以余额不变
        assertResult(before, before.getAmount());
    }

    @Test
    public void successCommit() {
        TradeEntity entity = buildEntity();
        Account before = accountMapper.selectByAccountId(entity.getAccountId());
        long s = System.currentTimeMillis();
        try {
            bService.successCommit(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long e = System.currentTimeMillis();
        System.out.println(e-s);
        assertResult(before, before.getAmount() + 1);

    }
    @Test
    public void iso() throws InterruptedException {
        TradeEntity entity = buildEntity();
        bService.a(entity);
    }


    private void clazz() {
        //-Dcglib.debugLocation=./target/proxy
        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", true);
    }
}
