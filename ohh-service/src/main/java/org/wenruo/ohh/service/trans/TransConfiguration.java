package org.wenruo.ohh.service.trans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wendb
 * @date 2023/5/12
 */
@Configuration
public class TransConfiguration {
    @Bean(name = "transactionTemplate")
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
    @Bean(name = "transactionTemplateNew")
    public TransactionTemplate transactionTemplateNew(PlatformTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return template;
    }
    @Bean(name = "myTransactionTemplate")
    public MyTransactionTemplate myTransactionTemplate(PlatformTransactionManager transactionManager) {
        return new MyTransactionTemplate(transactionManager);
    }

}
