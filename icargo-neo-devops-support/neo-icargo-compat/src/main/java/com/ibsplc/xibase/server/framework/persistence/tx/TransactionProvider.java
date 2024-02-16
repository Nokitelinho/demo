/*
 * @(#) TransactionProvider.java 1.0 Mar 15, 2006 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence.tx;

/**
 * Interface for all TransactionProvider. Concrete implementations
 * of this interface will be used for starting Transactions.
 * <p>
 * The currently configured transaction provider is obtained by
 * using the <i>PersistenceController</i>
 * 
 * @see Transaction
 * @see com.ibsplc.xibase.server.framework.persistence.PersistenceController
 *
 */
/*
 * Revision History
 * Revision     Date                Author          Description
 * 1.0          Apr 5, 2005         Binu K          First draft
 */
public interface TransactionProvider {
	/**
	 * Return the currently existing Transaction or create
	 * a new one if none exists. The Transaction returned is already
	 * started.
	 * 
	 * @return - The current Transaction
	 * @throws TransactionException
	 */
	public Transaction getTransaction() throws TransactionException;

	/**
	 * Return the currently existing Transaction or create
	 * a new one if none exists. The Transaction returned is already
	 * started.
	 * 
	 * @return - The current Transaction
	 * @throws TransactionException
	 */
	public Transaction getTransaction(boolean shouldCreate)
			throws TransactionException;

	/**
	 * Return the currently existing Transaction or create
	 * a new one if none exists, with the specified timeout.The Transaction returned is already
	 * started.
	 * 
	 * @param timeout The transaction timeout
	 * @return - The current Transaction
	 * @throws TransactionException
	 */
	public Transaction getTransaction(long timeout) throws TransactionException;

	/**
	 * Create a new transaction.The currently existing transaction(if any)
	 * is temporarily suspended .The Transaction returned is already
	 * started.
	 * 
	 * @return - a new Transaction
	 * @throws TransactionException
	 */
	public Transaction getNewTransaction() throws TransactionException;

	/**
	 * Create a new transaction.The currently existing transaction(if any)
	 * is temporarily suspended .The Transaction returned is already
	 * started.
	 * 
	 * @return - a new Transaction
	 * @throws TransactionException
	 */
	Transaction getNewTransaction(boolean startJTATx)
			throws TransactionException;

	/**
	 * Create a new transaction with the specified timeout.
	 * The currently existing transaction(if any) is temporarily suspended 
	 * 
	 * @param timeout The transaction timeout
	 * @return - a new Transaction
	 * @throws TransactionException
	 */
	public Transaction getNewTransaction(long timeout)
			throws TransactionException;

}
