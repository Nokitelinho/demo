/*
 * RemoveException.java Created on Aug 2, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.persistence;

import com.ibsplc.xibase.server.framework.exceptions.OperationFailedException;


/**
 * This expception is thrown by the {@link EntityManager}
 * to indicate a failure in removing an entity
 * 
 * @author Binu K
 */

/*
 * Revision History
 * Revision 	Date      	   Author			Description
 * 0.1			Aug 2, 2005	   Binu K			First draft
 */

public class RemoveException extends OperationFailedException {

	/**
	 * The default constructor
	 */
	public RemoveException() {
		super(OP_FAILED);
	}

	/**
	 * Construct the RemoveException using the message
	 * @param msg
	 */
	public RemoveException(String msg) {
		super(msg,OP_FAILED);
	}

	/**
	 * Construct the RemoveException with the nestedException
	 * 
	 * @param nestedException
	 */
	public RemoveException(Throwable nestedException) {
		super(nestedException);
	}




}
