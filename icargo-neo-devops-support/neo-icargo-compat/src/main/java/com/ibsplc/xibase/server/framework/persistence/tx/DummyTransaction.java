/*
 * DummyTransaction.java Created on 24/05/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.persistence.tx;

import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jens
 */
public class DummyTransaction implements Transaction{

    static final Logger logger = LoggerFactory.getLogger(DummyTransaction.class);

    @Override
    public void commit() throws OptimisticConcurrencyException, TransactionException {
        logger.warn("NO-OP commit operation.");
    }

    @Override
    public void begin() throws TransactionException {
        logger.warn("NO-OP commit begin.");
    }

    @Override
    public boolean isActive() throws TransactionException {
        logger.warn("NO-OP isActive check.");
        return false;
    }

    @Override
    public void rollback() throws TransactionException {
        logger.warn("NO-OP rollback operation.");
    }
}
