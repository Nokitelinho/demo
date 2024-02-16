/*
 * DummyTransactionManager.java Created on 24/05/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.persistence.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jens
 */
public class DummyTransactionProvider implements TransactionProvider{

    static final Logger logger = LoggerFactory.getLogger(DummyTransactionProvider.class);
    static final Transaction DUMMY = new DummyTransaction();

    @Override
    public Transaction getTransaction() throws TransactionException {
        logger.warn("NO-OP getTransaction");
        return DUMMY;
    }

    @Override
    public Transaction getTransaction(boolean shouldCreate) throws TransactionException {
        logger.warn("NO-OP getTransaction({})", shouldCreate);
        return DUMMY;
    }

    @Override
    public Transaction getTransaction(long timeout) throws TransactionException {
        logger.warn("NO-OP getTransaction({})", timeout);
        return DUMMY;
    }

    @Override
    public Transaction getNewTransaction() throws TransactionException {
        logger.warn("NO-OP getNewTransaction");
        return DUMMY;
    }

    @Override
    public Transaction getNewTransaction(boolean startJTATx) throws TransactionException {
        logger.warn("NO-OP getNewTransaction({})", startJTATx);
        return DUMMY;
    }

    @Override
    public Transaction getNewTransaction(long timeout) throws TransactionException {
        logger.warn("NO-OP getNewTransaction({})", timeout);
        return DUMMY;
    }
}
