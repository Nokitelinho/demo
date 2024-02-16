/*
 * ReturnNotPossibleException.java Created on Aug 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
/**
 * 
 * @author A-1936
 * The ReturnNotPossibleException
 */
public class ReturnNotPossibleException extends BusinessException {
/**
 * The ErrorCode fOr InvalidMailBags For Return
 */
public static final String INVALID_MAILBAGS_FORRETURN="mailtracking.defaults.invalidmailbagsforreturn";
/**
 * The ErrorCode For InvalidDespatches For Return 
 */
public static final String INVALID_DESPATCHES_FORRETURN="mailtracking.defaults.invaliddespatchesforreturn";	
	 
	/**
	 * The defaultConstructor
	 *
	 */
	public ReturnNotPossibleException(){
		super();
	}
	 /**
	  * 
	  * @param errorCode
	  * @param errorData
	  */
	public ReturnNotPossibleException( String errorCode, Object[] errorData){
		super(errorCode,errorData);
	}
	
	
	/**
	 * 
	 * @param ex
	 */
	public ReturnNotPossibleException( BusinessException ex){
		super(ex);
	}
	
	/**
	  * 
	  * @param errorCode
	  */
	public ReturnNotPossibleException( String errorCode){
		super(errorCode);
	}
	
}
