/*
 * ULDDefaultsProxyException.java Created on Mar 8, 2007 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;


/**
 * @author A-1883	
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		   Mar 8, 2007			  	 A-1883		Created
 */
public class ULDDefaultsProxyException extends BusinessException {

	
	/**
	 * Constructor to hold the error causes also.
	 * 
	 * This cause consist of the desctiption text displayed in the front end.
	 * 
	 * @param errorCode
	 * @param exceptionCause
	 */
	public ULDDefaultsProxyException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);

	}
 /**
  * @param errorCode
  */
	public ULDDefaultsProxyException(String errorCode) {
		super(errorCode);

	}
	/**
	 * This is the default constructor which can be used while storing multiple
	 * messages in from the same messages.
	 * 
	 * e.g. storing Collection<ErrorVO>
	 * 
	 * @param errorCode
	 */
	public ULDDefaultsProxyException() {
		super();
	}
	
	/**
	 * 
	 * @param exception
	 */
	public ULDDefaultsProxyException(BusinessException exception) {
		super(exception);
	}

}
