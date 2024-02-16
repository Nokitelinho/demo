/*
 * ProrationFactorNotFoundException.java Created on Dec 15, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-3251
 *
 */
public class ProrationFactorNotFoundException extends BusinessException {

	public static final String PRORATION_NOTFOUND = "mailtracking.mra.defaults.prorationnotfound";


	/**
     * @param errorCode
     */
    public ProrationFactorNotFoundException(String errorCode) {
        super(errorCode);

    }

    /**
     * @param errorCode
     * @param errorData
     */
    public ProrationFactorNotFoundException(String errorCode, Object[] errorData) {
        super(errorCode, errorData);

    }

    /**
     *
     */
    public ProrationFactorNotFoundException() {
        super();

    }

    /**
     * @param exception
     */
    public ProrationFactorNotFoundException(Throwable exception) {
        super(exception);

    }

    /**
     * @param errorCode
     * @param exception
     */
    public ProrationFactorNotFoundException(String errorCode, Throwable exception) {
        super(errorCode, exception);

    }

    /**
     * @param businessException
     */
    public ProrationFactorNotFoundException(BusinessException businessException) {
        super(businessException);

    }


}
