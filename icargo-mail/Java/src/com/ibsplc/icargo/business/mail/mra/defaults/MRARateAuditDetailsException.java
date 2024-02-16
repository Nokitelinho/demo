/*
 * MRARateAuditDetailsException.java Created on Dec 15, 2008
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
public class MRARateAuditDetailsException extends BusinessException {

	public static final String COMPUTETOT_FAILED = "mailtracking.mra.defaults.computetotprocfailed";


	/**
     * @param errorCode
     */
    public MRARateAuditDetailsException(String errorCode) {
        super(errorCode);

    }

    /**
     * @param errorCode
     * @param errorData
     */
    public MRARateAuditDetailsException(String errorCode, Object[] errorData) {
        super(errorCode, errorData);

    }

    /**
     *
     */
    public MRARateAuditDetailsException() {
        super();

    }

    /**
     * @param exception
     */
    public MRARateAuditDetailsException(Throwable exception) {
        super(exception);

    }

    /**
     * @param errorCode
     * @param exception
     */
    public MRARateAuditDetailsException(String errorCode, Throwable exception) {
        super(errorCode, exception);

    }

    /**
     * @param businessException
     */
    public MRARateAuditDetailsException(BusinessException businessException) {
        super(businessException);

    }


}
