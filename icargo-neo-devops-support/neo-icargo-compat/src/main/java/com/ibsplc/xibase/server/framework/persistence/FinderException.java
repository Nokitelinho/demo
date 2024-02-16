/*
 * FinderException.java Created on Aug 2, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.persistence;

import com.ibsplc.xibase.server.framework.exceptions.OperationFailedException;


/**
 * Exception thrown by the {@link EntityManager} to
 * indicate a failure in find of a entity.
 */

/*
 * Revision History
 * Revision 	Date      	   Author			Description
 * 0.1			Aug 2, 2005	   Binu K			First draft
 */

public class FinderException extends OperationFailedException {
	
	/**
	 * The default constructor
	 *
	 */
	public FinderException() {
		super(OP_FAILED);
	}

	/**
	 * Construct a FinderException with the specified message
	 * 
	 * @param msg
	 */
	public FinderException(String msg) {
		super(msg,OP_FAILED);
	}

	/**
	 * Construct the FinderException with the nestedException
	 * @param nestedException
	 */
	public FinderException(Throwable nestedException) {
		super(nestedException);
	}

	
}
