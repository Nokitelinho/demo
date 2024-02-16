/*
 * PersistenceException.java Created on Jun 30, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.persistence;

import com.ibsplc.ibase.lang.RootCarrierException;

/**
 * This expception is thrown to indicate a failure in the persistence tier.
 * The root cause is set as one of the constants defined. 
 * @author Binu K
 */
/*
 * Revision History
 * Revision 	Date      	   Author			Description
 * 0.1			Aug 2, 2005	   Binu K			First draft
 */

public class PersistenceException extends RootCarrierException {

	public static final String CREATE = "PER_001";

	public static final String MODIFY = "PER_002";

	public static final String REMOVE = "PER_003";
	
	public static final String FIND = "PER_004";
	
	public static final String MERGE = "PER_005";
	
	public static final String REFRESH = "PER_006";

	public static final String OTHER = "PER_007";
	
	/**
	 * errorCode added for clear action
	 */
	public static final String CLEAR = "PER_008";

	private String errorCode;
	
	/**
	 * Default constructor 
	 */
	public PersistenceException() {
		super();
	}


	/**
	 * @param errorCode
	 * @param arg1
	 */
	public PersistenceException(String errorCode, Throwable arg1) {
		super(errorCode, arg1);
		this.errorCode = errorCode;
	}

	/**
	 * @param arg0
	 */
	public PersistenceException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Constructs an instance of CreateException with the given message
	 * 
	 * @param message
	 *            The detail message
	 */
	public PersistenceException(String message) {
		super(message);
	}

	/**
	 * 
	 * @return The errorcode
	 *
	 */
	public String getErrorCode() {
		return errorCode;
	}
	
	/**
	 * Sets the errorCode
	 * 
	 * @param errorCode 
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
