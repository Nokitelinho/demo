/*
 * @(#) PersistenceController.java 1.0 Apr 5, 2005 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.xibase.server.framework.persistence.tx.DummyTransactionProvider;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;

/**
 * The controller for obtaining instances of Entity and Transaction
 * Managers.
 * 
 * @author A-1456
 * @see EntityManager
 * @see com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider
 */
/*
 * Revision History
 * Revision         Date                Author          Description
 * 1.0              Apr 5, 2005         Binu K          First draft
 */
public class PersistenceController {

	static final TransactionProvider DUMMY = new DummyTransactionProvider();
	
	/**
	 * Gets an instance of the currently configured <i>EntityManager</i>
	 * 
	 * @return
	 */
	public static EntityManager getEntityManager() {
		return new NEntityManager(ContextUtil.getInstance());
	}

	/**
	 * Gets an instance of the currently configured <i>TransactionProvider</i>
	 * This method is scheduled to be removed
	 * @return
	 */
	public static TransactionProvider __getTransactionProvider() {
		return DUMMY;
	}

}
