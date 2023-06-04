package org.wenruo.ohh.service.trans.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionTemplate;
import org.wenruo.ohh.dao.mapper.AccountFlowMapper;
import org.wenruo.ohh.dao.mapper.AccountMapper;
import org.wenruo.ohh.dao.model.Account;
import org.wenruo.ohh.dao.model.AccountFlow;
import org.wenruo.ohh.service.entity.TradeEntity;
import org.wenruo.ohh.service.enums.TradeTypeEnum;
import org.wenruo.ohh.service.trans.MyTransactionTemplate;

import java.util.Arrays;

/**
 * @author wendb
 * @date 2023/5/9
 */
@Service

public class DevAccountService {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFlowMapper accountFlowMapper;
    @Autowired
    private MyTransactionTemplate myTransactionTemplate;


    public void testThrowError(TradeEntity entity) {
        transactionTemplate.execute(status -> {
            status.setRollbackOnly();
            updateAmount(entity);

            throw new Error("");
        });
    }

    public void testRollback(TradeEntity entity) {
        transactionTemplate.execute(status -> {
            updateAmount(entity);
            throw new Error("test error");
        });
    }

    public void rollbackMyException(TradeEntity entity) {
        myTransactionTemplate.setRollbackFor(Arrays.asList(MyException.class));
        myTransactionTemplate.execute(status -> {
            updateAmount(entity);
            System.out.println(1 / 0);
            return null;
        });
    }

    public void rollbackOnly(TradeEntity entity) {
        TransactionTemplate template =getTransactionTemplate(Propagation.REQUIRED, Isolation.DEFAULT, 100, true);
        TransactionTemplate nestedTemplate = getTransactionTemplate(Propagation.NESTED, Isolation.READ_UNCOMMITTED, -1, false);
        nestedTemplate.execute(status -> {
            updateAmount(entity);
            nestedTemplate.execute(status1 -> {
                updateAmount(entity);
                status1.setRollbackOnly();
                return null;
            });
            return null;
        });
    }

    public void testCommit(TradeEntity entity) {
        transactionTemplate.execute(status -> {
            updateAmount(entity);
            return null;
        });
    }

    public void testNested(TradeEntity entity) throws InterruptedException {


      /*  new Thread(() -> {
            transactionTemplate.setReadOnly(true);
            transactionTemplate.execute(status -> {

                Account account = accountMapper.selectByAccountId(entity.getAccountId());
                System.out.println("new thread amount=" + account.getAmount());
                return null;
            });
        }).start();*/
        Thread.sleep(3000);
        transactionTemplate.execute(status -> {
            updateAmount(entity);
            Account account = accountMapper.selectByAccountId(entity.getAccountId());
            System.out.println("amount=" + account.getAmount());
            return null;
        });
    }

    private TransactionTemplate getTransactionTemplate(Propagation propagation, Isolation isolation, int timeout, boolean readOnly) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setPropagationBehavior(propagation.value());
        template.setIsolationLevel(isolation.value());
        template.setTimeout(timeout);
        template.setReadOnly(readOnly);
        return template;
    }


    private void tradeError(TradeEntity entity) {
        String orderNo = entity.getOrderNo();
        String accountId = entity.getAccountId();
        TradeTypeEnum tradeType = entity.getTradeType();
        long tradeAmount = entity.getTradeAmount();

        AccountFlow flow = new AccountFlow();
        flow.setOrderNo(orderNo);
        flow.setAccountId(accountId);
        flow.setTradeType(tradeType.getCode());
        flow.setTradeAmount(tradeAmount);
        flow.setAfterAmount(100L);
        flow.setAfterFrozenAmount(120L);
        accountFlowMapper.insert(flow);

        accountMapper.updateAmountSqlError(accountId, 100L, 10L);
    }

    private void updateAmount(TradeEntity entity) {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
    }
}
