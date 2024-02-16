/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.proxy.SharedAgentProxy.java
 *
 *	Created by	:	Prashant Behera
 *	Created on	:	Jun 29, 2022
 *
 *  Copyright 2022 Copyright  IBS Software  (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright  IBS Software  (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.agent.AgentBI;
import com.ibsplc.icargo.business.shared.agent.AgentBusinessException;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.proxy.SharedAgentProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Prashant Behera	:	Jun 29, 2022	:	Draft
 */
public class SharedAgentProxy extends SubSystemProxy {
	
	/**
	 * 
	 * 	Method		:	SharedAgentProxy.validateAgents
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param agentCodes
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Map<String,AgentVO>
	 */
	public Map<String, AgentVO> validateAgents(String companyCode , Collection<String> agentCodes) throws SystemException, ProxyException{
		try {
			AgentBI agentBI = (AgentBI)getService("SHARED_AGENT");  
			return agentBI.validateAgents(companyCode,agentCodes);
		}catch(AgentBusinessException ex){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,ex);
		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		} catch (RemoteException remoteException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		} 		
	}	
}
