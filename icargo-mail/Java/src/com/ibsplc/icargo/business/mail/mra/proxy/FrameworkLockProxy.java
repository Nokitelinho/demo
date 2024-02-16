/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy.java
 *
 *	Created by	:	A-4809
 *	Created on	:	10-Jan-2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.proxy;

import java.util.Collection;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.tx.TxConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	10-Jan-2014	:	Draft
 */
@Module("framework")
@SubModule("lock")
public class FrameworkLockProxy extends ProductProxy{
	private Log log = LogFactory.getLogger("FRAMEWORK LOCK PROXY");
	
/**
 * 	Method		:	FrameworkLockProxy.addLocks
 *	Added by 	:	A-4809 on 10-Jan-2014
 * 	Used for 	:	ICRD-42160
 *	Parameters	:	@param lockVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws ProxyException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Collection<LockVO>
 */
	public Collection<LockVO> addLocks(Collection<LockVO> lockVOs)
	 throws ProxyException,SystemException {
		log.log(Log.FINE,"Calling despatchRequest for addLocks");
		return despatchRequest(TxConstants.MULTIPLE_LOCK_ACTION,lockVOs);
			
}
	/**
	 * 	Method		:	FrameworkLockProxy.releaseLocks
	 *	Added by 	:	A-4809 on 10-Jan-2014
	 * 	Used for 	:	ICRD-42160
	 *	Parameters	:	@param lockVOs
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void releaseLocks(Collection<LockVO> lockVOs)
	throws ProxyException,SystemException{
		log.log(Log.FINE,"Calling despatchRequest for releaseLocks");
		despatchRequest(TxConstants.MULTIPLE_UNLOCK_ACTION,new Object[]{lockVOs});
		
	}

}
