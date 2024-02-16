/*
* @(#) CreateException.java 1.0 Aug 2, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence;

import com.ibsplc.xibase.server.framework.exceptions.OperationFailedException;


/**
 * The exception used to indicate any failure in creation of
 * an entity
 */

/*
 * Revision History
 * Revision 	Date      	  	   Author			Description
 * 0.1			Aug 2, 2005	  	   Binu K			First draft
 */
public class CreateException extends OperationFailedException {

	
	/**
	 * The default constructor
	 *
	 */
	public CreateException() {
		super(OP_FAILED);
	}

	/**
	 * Construct a CreateException with the specified message
	 * 
	 * @param msg
	 */
	public CreateException(String msg) {
		super(msg,OP_FAILED);
	}

	/**
	 * Construct the CreateException with the nestedException
	 * 
	 * @param nestedException
	 */
	public CreateException(Throwable nestedException) {
		super(nestedException);
	}

	
}
