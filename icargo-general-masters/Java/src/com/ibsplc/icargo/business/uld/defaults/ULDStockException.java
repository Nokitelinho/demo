/*
 * ULDStockException.java Created on Aug 21 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author a-3353
 *
 */
public class ULDStockException extends BusinessException {
	
	/**
	 * 
	 */
	public static final String ULDTYPE_ALREADY_DEFINED =
		"uld.defaults.uldtype.alreadydefined";
	
	public ULDStockException(){
		super();
	}
	public ULDStockException(String errorCode){
		super(errorCode);
	}
	public ULDStockException(String errorCode, Object[] exceptionCause){
		super(errorCode,exceptionCause);
	}
}
