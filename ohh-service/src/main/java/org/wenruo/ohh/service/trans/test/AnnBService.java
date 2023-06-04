package org.wenruo.ohh.service.trans.test;

import com.alibaba.fastjson.JSONObject;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wenruo.ohh.dao.mapper.AccountFlowMapper;
import org.wenruo.ohh.dao.mapper.AccountMapper;
import org.wenruo.ohh.dao.mapper.MqMapper;
import org.wenruo.ohh.dao.model.Account;
import org.wenruo.ohh.dao.model.LocalMq;
import org.wenruo.ohh.service.entity.TradeEntity;

/**
 * @author wendb
 * @date 2023/5/7
 */
@Service
@Setter
public class AnnBService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFlowMapper accountFlowMapper;
    @Autowired
    private MqProduce mqProduce;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    public MqMapper mqMapper;

    @Transactional
    protected void protectedTrade(TradeEntity entity) {
        updateAmount(entity);
        System.out.println(1 / 0);
    }

    @Transactional
    public void tradeAndException(TradeEntity entity) {
        updateAmount(entity);
        System.out.println(1 / 0);
    }


    @Transactional(propagation = Propagation.NESTED)
    public void tradeNested(TradeEntity entity) {
        updateAmount(entity);
        System.out.println(1 / 0);
    }

    @Transactional
    public void n(TradeEntity entity) throws InterruptedException {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
        Account account = accountMapper.selectByAccountId(entity.getAccountId());
        System.out.println("after amount=" + account.getAmount());
        Thread.sleep(20000);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void b(TradeEntity entity) {
        Account account = accountMapper.selectByAccountId(entity.getAccountId());
        System.out.println("b amount=" + account.getAmount());
    }


    @Transactional
    public void trade(TradeEntity entity) {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
        mqProduce.send("test_topic", entity.getOrderNo(), entity);
    }


    @Transactional
    public void tradeAndMsg1(TradeEntity entity) {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
        LocalMq localMq = buildLocalMq("test_topic", entity.getOrderNo(), entity);
        mqMapper.insert(localMq);
    }



    @Transactional
    public LocalMq tradeAndMsg2(TradeEntity entity) {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
        LocalMq localMq = buildLocalMq("test_topic", entity.getOrderNo(), entity);
        mqMapper.insert(localMq);
        return localMq;
    }

    @Transactional
    public void tradeAndMsg3(TradeEntity entity) {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
        mqProduce.sendTransMq("test_topic", entity.getOrderNo(), entity);
    }


    private LocalMq buildLocalMq(String topic, String key, Object msg) {
        LocalMq localMq = new LocalMq();
        localMq.setUkey(key);
        localMq.setEvent("test");
        localMq.setContent(JSONObject.toJSONString(msg));
        localMq.setTopic(topic);
        localMq.setStatus(0);
        return localMq;
    }

    private void updateAmount(TradeEntity entity) {
        accountMapper.updateAmount(entity.getAccountId(), 1L, 0L);
    }


}
