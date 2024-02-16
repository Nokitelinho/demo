/*
 * ReassignmentException.java Created on July 03, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1739
 *
 */
public class ReassignmentException extends BusinessException {
    /**
     * The ErrorCode
     */
	public static final String MAILBAG_REASSIGN_FROM_PABUILT = 
		"mailtracking.defaults.err.mailbagreassignfrompabuiltuld";
	/**
	 * The ErrorCode
	 */
	public static final String DESPATCH_REASSIGN_FROM_PABUILT = 
		"mailtracking.defaults.err.despatchreassignfrompabuiltuld";
	 /**
	  * The ErrorCode
	  */
	public static final String MAILBAG_REASSIGN_NOT_AVAILABLE = 
		"mailtracking.defaults.err.mailbagreassignnotavailable";
	/**
	 * @param errorCode
	 */
	public ReassignmentException(String errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param errorData
	 */
	public ReassignmentException(String errorCode, Object[] errorData) {
		super(errorCode, errorData);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public ReassignmentException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param exception
	 */
	public ReassignmentException(Throwable exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param exception
	 */
	public ReassignmentException(String errorCode, Throwable exception) {
		super(errorCode, exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param businessException
	 */
	public ReassignmentException(BusinessException businessException) {
		super(businessException);
		// TODO Auto-generated constructor stub
	}

}
