/*
 * FlownException.java Created on Jun 22, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.flown;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
/**
 * 
 * @author A-2270
 *
 */
public class FlownException extends BusinessException {
	
	/**
	 * 
	 */
	
	public static final String NO_DATA_FOUND_FOR_MAIL_REVENUE_DETAILS = 
		"mailtracking.mra.flown.report.NoDataFoundForMailRevenueDetails";
	
	/**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public FlownException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	/**
	 * @param errorCode
	 */
	public FlownException(String errorCode) {
		super(errorCode);
	}
	
	/**	
	 * 
	 *
	 */
	public 	FlownException(){
		super();
	}

	/**
	 *
	 * @param businessException
	 */
	public FlownException(BusinessException businessException) {
		super(businessException);
	}	
}
