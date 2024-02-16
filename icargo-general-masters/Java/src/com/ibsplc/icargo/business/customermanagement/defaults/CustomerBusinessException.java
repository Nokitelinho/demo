/*
 * CustomerBusinessException.java Created on APR 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * Exception class specific for CAP
 * 
 * Revision History     
 * 
 * Version      Date        Author          		Description
 * 
 *  0.1         25/04/2006  Jeen M 		        Initial draft 
 */
public class CustomerBusinessException extends BusinessException {
	/**
	 * Default constructor
	 */
	public CustomerBusinessException() {
		super();
	}

	/**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public CustomerBusinessException(String errorCode, 
			Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	/**
	 * @param errorCode String
	 */
	public CustomerBusinessException(String errorCode) {
		super(errorCode);
	}
	
	/**
	 * @param exception BusinessException
	 */
	public CustomerBusinessException(BusinessException exception) {
		super(exception);
	}	
}
