package org.wenruo.ohh.service.trans.test;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.wenruo.ohh.dao.mapper.MqMapper;
import org.wenruo.ohh.dao.model.LocalMq;

import java.util.List;

/**
 * @author wendb
 * @date 2023/5/9
 */
@Component
public class MqProduce {
    @Autowired
    public MqMapper mqMapper;


    @Transactional
    public void sendTransMq1(String topic, String key, Object msg) {
        LocalMq localMq = buildLocalMq(topic, key, msg);
        mqMapper.insert(localMq);
    }


    public void job() {
        List<LocalMq> list = mqMapper.selectByStatus(0);
        for (LocalMq mq : list) {
            if (send(mq.getTopic(), mq.getUkey(), mq.getContent())) {
                mqMapper.updateStatus(mq.getUkey(), 1);
            }
        }
    }

    public boolean send(String topic, String key, Object msg) {
        //这里真正往mq服务发送消息
        System.out.println("topic:" + topic + ",key:" + key + ",msg:" + msg + " 发送成功");
        return true;
    }

    public void sendTransMq(String topic, String key, Object msg) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new RuntimeException("必须在事务中使用");
        }
        LocalMq localMq = buildLocalMq(topic, key, msg);
        mqMapper.insert(localMq);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    if (send(topic, key, msg)) {
                        mqMapper.updateStatus(localMq.getUkey(), 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
}
