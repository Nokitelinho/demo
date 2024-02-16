/*
 * DuplicateDSNException.java Created on May 27, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-2553
 * This class is used to create an exception to be thrown when checks for the
 * ContainerAssignment are Done.
 */
public class DuplicateDSNException extends BusinessException {
	/**
	 * The ErrorCode dsn already in despatch or mailbags
	 */
	public static final String DSN_IN_MAILBAG_DESPATCH = "mailtracking.defaults.dsninmailbaganddespatch";
	
	public static final String DSN_IN_MAILBAG = "mailtracking.defaults.consignment.duplicatemailbag";
	
	public static final String DSN_IN_DESPATCH = "mailtracking.defaults.consignment.duplicatedespatch";

	
	/** 
	 * The Constructor
	 */
	public DuplicateDSNException() {
		super();
	}

	/**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public DuplicateDSNException(String errorCode,
			Object[] exceptionCause) {
		super(errorCode, exceptionCause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 */
	public DuplicateDSNException(String errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param ex
	 */
	public DuplicateDSNException(BusinessException ex) {
		super(ex);
		// TODO Auto-generated constructor stub
	}
}
