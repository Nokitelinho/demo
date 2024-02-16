/*
* @(#) Transaction.java 1.0 Mar 3, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.tx;

import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;

/**
 *  The base interface for all transaction implementations.
 *  
 *  @author A-1456
 */
/*
 * Revision History
 * Revision 	Date      		Author			Description
 * 1.0			Mar 3, 2005		Binu K			First draft
*/
public interface Transaction {
	
	/**
	 * Commit the current transaction
	 * @throws OptimisticConcurrencyException
	 * @throws TransactionException TODO
	 */
	void commit()throws OptimisticConcurrencyException, TransactionException;
	
	/**
	 * Start the transaction
	 * @throws TransactionException TODO
	 */
	void begin() throws TransactionException;
	
	/**
	 * Check if transaction is still active.
	 * 
	 * @return
	 * @throws TransactionException TODO
	 */
	boolean isActive() throws TransactionException;
	
	/**
	 * Rollback the transaction
	 * @throws TransactionException TODO
	 */
	void rollback()throws TransactionException;

}
