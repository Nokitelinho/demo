/*
 * RateCardException.java Created on Jan 19, 2007
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
public class RateCardException extends BusinessException {

	 public static final String RATECARD_EXIST = "mailtracking.mra.defaults.ratecardexist";

	/**
     * @param errorCode
     */
    public RateCardException(String errorCode) {
        super(errorCode);

    }

    /**
     * @param errorCode
     * @param errorData
     */
    public RateCardException(String errorCode, Object[] errorData) {
        super(errorCode, errorData);

    }

    /**
     *
     */
    public RateCardException() {
        super();

    }

    /**
     * @param exception
     */
    public RateCardException(Throwable exception) {
        super(exception);

    }

    /**
     * @param errorCode
     * @param exception
     */
    public RateCardException(String errorCode, Throwable exception) {
        super(errorCode, exception);

    }

    /**
     * @param businessException
     */
    public RateCardException(BusinessException businessException) {
        super(businessException);

    }

}
