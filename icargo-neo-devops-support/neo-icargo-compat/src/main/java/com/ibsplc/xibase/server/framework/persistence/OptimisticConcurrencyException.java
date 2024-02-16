/*
* @(#) OptimisticConcurrencyException.java 1.0 Apr 1, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence;


/**
 * Exception raised by the {@link EntityManager} to
 * indicate any concurrency check failures
 *
 */
/*
* Revision History
* Revision      Date                Author          Description
* 1.0           Apr 1, 2005         Binu K          First draft
*/
public class OptimisticConcurrencyException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4706011113757906239L;

	private String errorCode;
	
	private String entityName;
	/**
	 * 
	 */
	public OptimisticConcurrencyException() {
		super();
	}
	
	
	public OptimisticConcurrencyException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OptimisticConcurrencyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public OptimisticConcurrencyException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public OptimisticConcurrencyException(Throwable cause) {
		super(cause);
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}


	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}


}
