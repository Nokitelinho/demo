/*
 * UCMValidationException.java Created on Dec 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1950
 *
 */
public class UCMValidationException
extends BusinessException {

	/**
		 *
		 */
	public static final String DUPLICATE_UCMOUT_EXISTS = 
		"uld.defaults.duplicateucmoutexists";
	/**
	 * 
	 */
	public static final String UCMOUT_AFTER_UCMIN_IS_INVALID =
		"uld.defaults.ucmoutafterucminisnotvalid";
	/**
	 * 
	 */
	public static final String UCMIN_IF_DUPLICATE_UCMOUT_IS_NOT_POSSIBLE =
		"uld.defaults.ucminisnotpossibleifduplicateoutexists";
	/**
	 * @param String
	 * 
	 */
	public static final String DUPLICATED_UCMIN_EXISTS =
		"uld.defaults.duplicateucminexists";
	
	
	/**
	 * 
	 * @param errorCode
	 */
	public UCMValidationException(String errorCode){
		super(errorCode);
	}
	/**
	 *
	 * @param errorCode
	 * @param exceptionCause
	 */
	public UCMValidationException(String errorCode,
			Object[] exceptionCause) {
	    super(errorCode, exceptionCause);
	    // To be reviewed Auto-generated constructor stub
	}
}
