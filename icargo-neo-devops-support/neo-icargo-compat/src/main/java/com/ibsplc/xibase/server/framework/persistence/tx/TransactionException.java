/*
* @(#) TransactionException.java 1.0 Apr 22, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.tx;

/**
 * The exception that wraps all transaction related error conditions. 
 *
 */
/*
* Revision History
*  Revision 	Date      	      Author			Description
*  0.1			Apr 22, 2005 	  Binu K			First draft
*/
public class TransactionException extends RuntimeException {

	
	public static final String OTHER = "TRA_001";
	/**
	 * 
	 */
	public TransactionException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public TransactionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TransactionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public TransactionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
