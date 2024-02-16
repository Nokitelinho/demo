/*
 * ULDDefaultsBusinessException.java Created on Dec 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1496
 * 
 */
public class ULDDefaultsBusinessException extends BusinessException {
	/**
	 * 
	 */
	public static final String NO_SUCH_ULD_EXISTS = 
		"uld.defaults.nosuchuldexists"; 
	
	public static final String ULD_DEFAULS_NO_DATA_FOUND = 
		"uld.defaults.noreportdata"; 
	
	
	/**
	 * 
	 */
	public static final String ULD_DEFAULTS_CANNOT_RETURN="uld.defaults.cannotreturn";
	
	
	
	public ULDDefaultsBusinessException(){
		super();
	}

	/**
	 * 
	 * @param errorCode
	 * @param exceptionCause
	 */
	public ULDDefaultsBusinessException(String errorCode,
			Object[] exceptionCause) {
		super(errorCode, exceptionCause);
		// To be reviewed Auto-generated constructor stub
	}

	/**
	 * 
	 * @param errorCode
	 */
	public ULDDefaultsBusinessException(String errorCode) {
		super(errorCode);
	}

	/**
	 * 
	 * @param exception
	 */
	public ULDDefaultsBusinessException(BusinessException exception) {
		super(exception);
	}
}
