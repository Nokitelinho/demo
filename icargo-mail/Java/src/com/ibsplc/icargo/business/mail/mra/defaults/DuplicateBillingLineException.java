/*
 * DuplicateBillingLineException.java Created on Jul 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * 
 * @author a-2518
 * 
 */
public class DuplicateBillingLineException extends BusinessException {

	/**
	 * Duplicate billing line exists
	 */
	public static final String DUPLICATE_BILLING_LINE_EXIST = "mailtracking.mra.defaults.duplicatebillingline";

	/**
	 * @param errorCode
	 */
	public DuplicateBillingLineException(String errorCode) {
		super(errorCode);

	}

	/**
	 * @param errorCode
	 * @param errorData
	 */
	public DuplicateBillingLineException(String errorCode, Object[] errorData) {
		super(errorCode, errorData);

	}

	/**
	 * 
	 */
	public DuplicateBillingLineException() {
		super();

	}

	/**
	 * @param exception
	 */
	public DuplicateBillingLineException(Throwable exception) {
		super(exception);

	}

	/**
	 * @param errorCode
	 * @param exception
	 */
	public DuplicateBillingLineException(String errorCode, Throwable exception) {
		super(errorCode, exception);

	}

	/**
	 * @param businessException
	 */
	public DuplicateBillingLineException(BusinessException businessException) {
		super(businessException);

	}

}
