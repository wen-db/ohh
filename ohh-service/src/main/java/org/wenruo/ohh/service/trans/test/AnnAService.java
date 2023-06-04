package org.wenruo.ohh.service.trans.test;

import lombok.Setter;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.wenruo.ohh.dao.mapper.AccountFlowMapper;
import org.wenruo.ohh.dao.mapper.AccountMapper;
import org.wenruo.ohh.dao.mapper.MqMapper;
import org.wenruo.ohh.dao.model.Account;
import org.wenruo.ohh.dao.model.LocalMq;
import org.wenruo.ohh.service.entity.TradeEntity;

import java.io.IOException;
import java.util.concurrent.FutureTask;

/**
 * @author wendb
 * @date 2023/5/7
 */
@Service
public class AnnAService {
    @Autowired
    private AnnBService accountService;
    @Setter
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFlowMapper accountFlowMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private MqProduce mqProduce;
    @Autowired
    public MqMapper mqMapper;


    public void protectedTrade(TradeEntity entity) {
        accountService.protectedTrade(entity);
    }

    @Transactional
    public void tradeAndException(TradeEntity entity) {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
        System.out.println(1 / 0);
    }
    public void inner(TradeEntity entity) {
        tradeAndException(entity);
    }

    public void innerTrue(TradeEntity entity) {
        //启动注解 @EnableAspectJAutoProxy(exposeProxy = true)
        ((AnnAService) AopContext.currentProxy()).tradeAndException(entity);
    }


    @Transactional
    public void trade(TradeEntity entity) throws IOException {
        updateAmount(entity);
        throw new IOException("");
    }

    private void updateAmount(TradeEntity entity) {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
    }

    @Transactional
    public void newThread(TradeEntity entity) throws Exception {
        FutureTask<Object> futureTask = new FutureTask<>(() -> {
            accountService.trade(entity);
            return null;
        });
        new Thread(futureTask).start();
        futureTask.get();
        System.out.println(1 / 0);
    }

    @Transactional
    public void newThreadTrue(TradeEntity entity) throws Exception {
        Object dataSource = transactionManager.getResourceFactory();
        Object con = TransactionSynchronizationManager.getResource(dataSource);
        FutureTask<Object> futureTask = new FutureTask<>(() -> {
            TransactionSynchronizationManager.bindResource(dataSource, con);
            accountService.trade(entity);
            return null;
        });
        new Thread(futureTask).start();
        futureTask.get();
        System.out.println(1 / 0);
    }

    @Transactional
    public void tryCatch(TradeEntity entity) {
        updateAmount(entity);
        try {
            accountService.tradeAndException(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void tradeNested(TradeEntity entity) {
        updateAmount(entity);
        try {
            accountService.tradeNested(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void testMq1(TradeEntity entity) {
        accountService.tradeAndMsg1(entity);
    }





    public void testMq2(TradeEntity entity) {
        LocalMq localMq = accountService.tradeAndMsg2(entity);
        //事务成功后立马尝试发送一次
        try {
            boolean b = mqProduce.send("test_topic", localMq.getUkey(), localMq.getContent());
            if (b) {
                mqMapper.updateStatus(localMq.getUkey(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testMq3(TradeEntity entity) {
        accountService.tradeAndMsg3(entity);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void a(TradeEntity entity) {
        Account account = accountMapper.selectByAccountId(entity.getAccountId());
        System.out.println("a amount=" + account.getAmount());
        accountService.b(entity);
    }


    @Transactional
    public void rollbackOfException(TradeEntity entity) {
        updateAmount(entity);
        throw new RuntimeException("test runtime exception");
    }

    @Transactional
    public void rollbackOfError(TradeEntity entity) {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
        throw new Error("test error");
    }

    @Transactional()
    public void successCommit(TradeEntity entity) throws InterruptedException {
        // Thread.sleep(5000);
        System.out.println("休眠结束");
        updateAmount(entity);
    }
}
