/*
 * InvalidPartnerException.java Created on Aug 3, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1936
 *
 */
public class InvalidPartnerException extends BusinessException {
   /**
    * The ErrorCode for Invalidpartner( Partner other than our Partner)
    */
	public static final String INVALID_PARTNER="mailtracking.defaults.invalidpartner"; 
    /**
     * The ErrorCode for DuplicatePartner
     */
	public static final String  DUPLICATE_PARTNER="mailtracking.defaults.duplicatepartner";	
	
	/**
	 * The defaultConstructor
	 *
	 */
	public InvalidPartnerException(){
		super();
	}
	 /**
	  * 
	  * @param errorCode
	  * @param errorData
	  */
	public InvalidPartnerException( String errorCode, Object[] errorData){
		super(errorCode,errorData);
	}
	
	
	/**
	 * 
	 * @param ex
	 */
	public InvalidPartnerException( BusinessException ex){
		super(ex);
	}
	
	/**
	  * 
	  * @param errorCode
	  */
	public InvalidPartnerException( String errorCode){
		super(errorCode);
	}
	
}
