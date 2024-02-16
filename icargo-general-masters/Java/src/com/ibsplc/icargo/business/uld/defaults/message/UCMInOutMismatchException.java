/*
 * UCMInOutMismatchException.java Created on jul 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-2048
 *
 */
public class UCMInOutMismatchException
extends BusinessException {

	/**
	 * 
	 */
	public static final String DUPLICATE_UCMOUT_EXISTS_FORSTATION = 
		"uld.defaults.duplicateucmoutexistsforstation";
	/**
	 * 
	 */
	public static final String ULDERRORS_IN_UCM =
		"uld.defaults.ulderrorspresentinucm";
	
	
	
	
	/**
	 * 
	 * @param errorCode
	 */
	public UCMInOutMismatchException(String errorCode){
		super(errorCode);
	}
	/**
	 *
	 * @param errorCode
	 * @param exceptionCause
	 */
	public UCMInOutMismatchException(String errorCode,
			Object[] exceptionCause) {
	    super(errorCode, exceptionCause);
	    // To be reviewed Auto-generated constructor stub
	}
}
