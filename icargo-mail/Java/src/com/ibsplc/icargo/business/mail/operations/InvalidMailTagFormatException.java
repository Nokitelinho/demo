/*
 * InvalidMailTagFormatException.java Created on Aug 3, 2006
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
 *  The InvalidMailTagFormatException used when the MailTag is Invalid
 */
public class InvalidMailTagFormatException  extends BusinessException {

	
	/**
	 * The ErrorCode for InvalidMailFormat
	 */
	public static final String INVALID_MAILFORMAT="mailtracking.defaults.invalidmailformat";
	
	
	/**
	 * The defaultConstructor
	 *
	 */
	public InvalidMailTagFormatException(){
		super();
	}
	/**
	 * 
	 * @param errorCode
	 * @param errorData
	 */
	public InvalidMailTagFormatException( String errorCode, Object[] errorData){
		super(errorCode,errorData);
	}
	
	
	/**
	 * 
	 * @param ex
	 */
	public InvalidMailTagFormatException( BusinessException ex){
		super(ex);
	}
	
	/**
	  * 
	  * @param errorCode
	  */
	public InvalidMailTagFormatException( String errorCode){
		super(errorCode);
	}
	
	
	
}
