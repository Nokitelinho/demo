/*
 * SharedSCCProxy.java Created on Dec 22, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.master.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.scc.SCCBI;
import com.ibsplc.icargo.business.shared.scc.vo.SCCLovFilterVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.business.shared.scc.SCCBusinessException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3109
 *
 */


public class SharedSCCProxy extends SubSystemProxy {
	private Log log = LogFactory.getLogger("OPERATIONS SHIPMENT");

	
	/**
	 * @param sccLovFilterVO
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public Collection<SCCVO> findSCCsMaster(SCCLovFilterVO sccLovFilterVO)throws ProxyException,SystemException{
		try {
			SCCBI sccBI = (SCCBI)getService("SHARED_SCC");
		return  sccBI.findSCCsMaster(sccLovFilterVO); 
		
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }

	/**
	 * 
	 * 	Method		:	SharedAgentProxy.findSCCDetails
	 *	Added by 	:	A-7548 on 24-Jan-2018
	 * 	Used for 	:
	 *	Parameters	:	@param sCCLovFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws SCCBusinessException
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws ServiceNotAccessibleException 
	 *	Return type	: 	Collection<SCCVO>
	 */
	public Collection<SCCVO> findSCCDetails(SCCLovFilterVO sCCLovFilterVO)
			throws SystemException, SCCBusinessException {
		try {
			SCCBI sCCBI = (SCCBI) getService("SHARED_SCC");
			return sCCBI.findSCCDetails(sCCLovFilterVO);
		} catch (ServiceNotAccessibleException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
		} catch (RemoteException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
		}
	}
}
	

