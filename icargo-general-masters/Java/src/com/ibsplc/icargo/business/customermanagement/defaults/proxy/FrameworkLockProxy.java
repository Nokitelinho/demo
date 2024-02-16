/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.proxy.FrameworkLockProxy.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.proxy;

import static com.ibsplc.xibase.server.framework.tx.TxConstants.MULTIPLE_LOCK_ACTION;
import static com.ibsplc.xibase.server.framework.tx.TxConstants.MULTIPLE_UNLOCK_ACTION;

import java.util.Collection;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.proxy.FrameworkLockProxy.java
 *	Version	:	Date				:	Updation
 * ---------------------------------------------------------------
 *		0.1		:	25-Jul-2022	:	Created
 */

@Module("framework")
@SubModule("lock")
public class FrameworkLockProxy extends ProductProxy {

	public Collection<LockVO> addLocks(Collection<LockVO> lockVOs) throws ProxyException, SystemException {
		return despatchRequest(MULTIPLE_LOCK_ACTION, lockVOs);
	}

	public void releaseLocks(Collection<LockVO> lockVOs) throws ProxyException, SystemException {
		despatchRequest(MULTIPLE_UNLOCK_ACTION, lockVOs);
	}

}
