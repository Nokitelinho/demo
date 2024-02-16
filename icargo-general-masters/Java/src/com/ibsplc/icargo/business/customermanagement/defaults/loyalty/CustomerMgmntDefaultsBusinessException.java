/*
 * CustomerMgmntDefaultsBusinessException.java
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
/**
 * 
 * @author a-1496
 *
 */
public class CustomerMgmntDefaultsBusinessException extends BusinessException {

	/**
     * Constant for Already parameter Exists exception
     */
	public static final String PARAMETER_ALREADY_EXISTS = 
		"customermanagement.defaults.parameterAlreadyExists";

	public CustomerMgmntDefaultsBusinessException() {
		super();
	}

	/**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public CustomerMgmntDefaultsBusinessException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	/**
	 * @param errorCode
	 */
	public CustomerMgmntDefaultsBusinessException(String errorCode) {
		super(errorCode);
	}

	public CustomerMgmntDefaultsBusinessException(BusinessException exception) {
		super(exception);
	}
}
