/*
 * RateLineException.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1556
 *
 */
public class RateLineException extends BusinessException {

	public static final String RATELINE_EXIST = "mailtracking.mra.defaults.ratelinesexist";


	/**
     * @param errorCode
     */
    public RateLineException(String errorCode) {
        super(errorCode);

    }

    /**
     * @param errorCode
     * @param errorData
     */
    public RateLineException(String errorCode, Object[] errorData) {
        super(errorCode, errorData);

    }

    /**
     *
     */
    public RateLineException() {
        super();

    }

    /**
     * @param exception
     */
    public RateLineException(Throwable exception) {
        super(exception);

    }

    /**
     * @param errorCode
     * @param exception
     */
    public RateLineException(String errorCode, Throwable exception) {
        super(errorCode, exception);

    }

    /**
     * @param businessException
     */
    public RateLineException(BusinessException businessException) {
        super(businessException);

    }


}
