package org.wenruo.ohh.service.trans;/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 * Template class that simplifies programmatic transaction demarcation and
 * transaction exception handling.
 *
 * <p>The central method is {@link #execute}, supporting transactional code that
 * implements the {@link TransactionCallback} interface. This template handles
 * the transaction lifecycle and possible exceptions such that neither the
 * TransactionCallback implementation nor the calling code needs to explicitly
 * handle transactions.
 *
 * <p>Typical usage: Allows for writing low-level data access objects that use
 * resources such as JDBC DataSources but are not transaction-aware themselves.
 * Instead, they can implicitly participate in transactions handled by higher-level
 * application services utilizing this class, making calls to the low-level
 * services via an inner-class callback object.
 *
 * <p>Can be used within a service implementation via direct instantiation with
 * a transaction manager reference, or get prepared in an application context
 * and passed to services as bean reference. Note: The transaction manager should
 * always be configured as bean in the application context: in the first case given
 * to the service directly, in the second case given to the prepared template.
 *
 * <p>Supports setting the propagation behavior and the isolation level by name,
 * for convenient configuration in context definitions.
 *
 * @author Juergen Hoeller
 * @since 17.03.2003
 * @see #execute
 * @see #setTransactionManager
 * @see org.springframework.transaction.PlatformTransactionManager
 */
public class MyTransactionTemplate extends DefaultTransactionDefinition
        implements TransactionOperations, InitializingBean {

    private List<Class<? extends Throwable>> rollbackFor;

    public void setRollbackFor(List<Class<? extends Throwable>> rollbackFor) {
        this.rollbackFor = rollbackFor;
    }

    /** Logger available to subclasses. */
    protected final Log logger = LogFactory.getLog(getClass());

    @Nullable
    private PlatformTransactionManager transactionManager;


    /**
     * Construct a new TransactionTemplate for bean usage.
     * <p>Note: The PlatformTransactionManager needs to be set before
     * any {@code execute} calls.
     * @see #setTransactionManager
     */
    public MyTransactionTemplate() {
    }

    /**
     * Construct a new TransactionTemplate using the given transaction manager.
     * @param transactionManager the transaction management strategy to be used
     */
    public MyTransactionTemplate(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Construct a new TransactionTemplate using the given transaction manager,
     * taking its default settings from the given transaction definition.
     * @param transactionManager the transaction management strategy to be used
     * @param transactionDefinition the transaction definition to copy the
     * default settings from. Local properties can still be set to change values.
     */
    public MyTransactionTemplate(PlatformTransactionManager transactionManager, TransactionDefinition transactionDefinition) {
        super(transactionDefinition);
        this.transactionManager = transactionManager;
    }


    /**
     * Set the transaction management strategy to be used.
     */
    public void setTransactionManager(@Nullable PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Return the transaction management strategy to be used.
     */
    @Nullable
    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    @Override
    public void afterPropertiesSet() {
        if (this.transactionManager == null) {
            throw new IllegalArgumentException("Property 'transactionManager' is required");
        }
    }


    @Override
    @Nullable
    public <T> T execute(TransactionCallback<T> action) throws TransactionException {
        Assert.state(this.transactionManager != null, "No PlatformTransactionManager set");

        if (this.transactionManager instanceof CallbackPreferringPlatformTransactionManager) {
            return ((CallbackPreferringPlatformTransactionManager) this.transactionManager).execute(this, action);
        }
        else {
            TransactionStatus status = this.transactionManager.getTransaction(this);
            T result;
            try {
                result = action.doInTransaction(status);
            }
            catch (Throwable ex) {
                // Transactional code threw unexpected exception -> rollback
                if (!rollbackForEx(ex)){
                    this.transactionManager.commit(status);
                    throw ex;
                }else {
                    rollbackOnException(status, ex);
                    throw new UndeclaredThrowableException(ex, "TransactionCallback threw undeclared checked exception");
                }
            }
            this.transactionManager.commit(status);
            return result;
        }
    }

    private boolean rollbackForEx(Throwable ex){
        if (rollbackFor==null){
            rollbackFor = new ArrayList<>();
            rollbackFor.add(RuntimeException.class);
            rollbackFor.add(Error.class);
        }
        for (Class<? extends Throwable> clazz : rollbackFor) {
            if (clazz.isAssignableFrom(ex.getClass())){
                return true;
            }
        }
        return false;
    }
    /**
     * Perform a rollback, handling rollback exceptions properly.
     * @param status object representing the transaction
     * @param ex the thrown application exception or error
     * @throws TransactionException in case of a rollback error
     */
    private void rollbackOnException(TransactionStatus status, Throwable ex) throws TransactionException {
        Assert.state(this.transactionManager != null, "No PlatformTransactionManager set");

        logger.debug("Initiating transaction rollback on application exception", ex);
        try {
            this.transactionManager.rollback(status);
        }
        catch (TransactionSystemException ex2) {
            logger.error("Application exception overridden by rollback exception", ex);
            ex2.initApplicationException(ex);
            throw ex2;
        }
        catch (RuntimeException | Error ex2) {
            logger.error("Application exception overridden by rollback exception", ex);
            throw ex2;
        }
    }


    @Override
    public boolean equals(@Nullable Object other) {
        return (this == other || (super.equals(other) && (!(other instanceof TransactionTemplate) ||
                getTransactionManager() == ((TransactionTemplate) other).getTransactionManager())));
    }

}
