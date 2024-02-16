/*
* @(#) QueryException.java 1.0 Apr 22, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.query;

/**
 * The exception that wraps any error condition raised by the
 * query module
 *
 */
/*
* Revision History
* Revision      Date                Author          Description
* 1.0           Apr 22, 2005        Binu K          First draft
*/
public class QueryException extends RuntimeException {


    /**
     * @param message
     */
    public QueryException() {
        super();
    }    
    
	/**
	 * @param message
	 */
	public QueryException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public QueryException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public QueryException(Throwable cause) {
		super(cause);
	}

}
