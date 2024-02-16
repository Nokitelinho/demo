/*
 * ULDInOperationException.java Created on Dec 20, 2005
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
public class ULDInOperationException extends BusinessException {
	
	/**
	 * 
	 */
	public static final String ULD_IS_OCCUPIED_AT_WAREHOUSE =
		"uld.defaults.uldisoccupiedatwarehouse";	
	
	public static final String ULD_IS_IN_OPERATION =
		"uld.defaults.uldisinoperation";	
	
	
	
	public ULDInOperationException(){
		super();
	}
	
	public ULDInOperationException(String errorCode){
		super(errorCode);
	}
	
	public ULDInOperationException(String errorCode, Object[] exceptionCause) {
	    super(errorCode, exceptionCause);
	    // To be reviewed Auto-generated constructor stub 
	}
	
}
