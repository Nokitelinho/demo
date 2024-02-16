/*
 * SharedAgentProxy.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.agent.AgentBI;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1885
 *
 */


public class SharedAgentProxy extends SubSystemProxy {
	
	private Log log = LogFactory.getLogger("STOCK CONTROLLER");
	
	/**
	 * @param companyCode
	 * @param agentCode
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public AgentVO findAgentDetails(String companyCode, String agentCode)
			throws SystemException, ProxyException {
		try {
			AgentBI agentBI = (AgentBI)getService("SHARED_AGENT");
		return  agentBI.findAgentDetails(companyCode, agentCode);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}

	/**
	 * @param companyCode
	 * @param agentCodes
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Map<String, AgentVO> validateAgents(String companyCode,
			Collection<String> agentCodes) throws SystemException,
			ProxyException {
		try {
			AgentBI agentBI = (AgentBI)getService("SHARED_AGENT");
		log.log(Log.FINE,"---inside proxy----");
		log.log(Log.FINER, "companyCode : ", companyCode);
		log.log(Log.FINER, "agentCodes : ", agentCodes);
		return  agentBI.validateAgents(companyCode, agentCodes);

		}catch(com.ibsplc.icargo.business.shared.agent.AgentBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}

}
