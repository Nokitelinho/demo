/*
 * InvalidULDFormatException.java Created on Dec 20, 2005
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
public class InvalidULDFormatException extends BusinessException {

	/**
	 * 
	 */
	public static final String INVALID_ULD_FORMAT="uld.defaults.invaliduldformat";
	/**
	 * 
	 * @param errorCode
	 * @param exceptionCause
	 */
	public InvalidULDFormatException(String errorCode,Object[] exceptionCause){
		super(errorCode,exceptionCause);
	}
}
