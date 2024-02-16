/*
 * UCMInExistsForStationException.java Created on Aug 16, 2006
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
public class UCMInExistsForStationException
extends BusinessException {

	/**
	 * 
	 */
	public static final String UCMINMESSAGE_EXISTS_FOR_STATION = 
		"uld.defaults.ucminmessageexistsforstation";
	
	
	
	
	/**
	 * 
	 * @param errorCode
	 */
	public UCMInExistsForStationException(String errorCode){
		super(errorCode);
	}
	/**
	 *
	 * @param errorCode
	 * @param exceptionCause
	 */
	public UCMInExistsForStationException(String errorCode,
			Object[] exceptionCause) {
	    super(errorCode, exceptionCause);
	    
	}
}
