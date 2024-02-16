/*
 * SharedAgentProxy.java Created on May 12, 2008 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.rmi.RemoteException;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.agent.AgentBI;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This class represents the product proxy for shared agent subsystem
 *
 * @author A-2408
 *
 *
 **/



public class SharedAgentProxy extends SubSystemProxy {
   
	
	private Log log =LogFactory.getLogger("ULD_MANAGEMENT");
	  
  /**
 * @param companyCode
 * @param agentCode
 * @return
 * @throws SystemException
 */
@Action("findAgentDetails")
  public AgentVO findAgentDetails(String companyCode , String agentCode)
  throws SystemException{
	  AgentVO agentVO = null;
	try{
	  AgentBI agentBI =(AgentBI)getService("SHARED_AGENT");
	  agentVO = agentBI.findAgentDetails(companyCode,agentCode);
	}catch(ServiceNotAccessibleException e) {
		throw new SystemException(e.getMessage(),e);
	}catch(RemoteException e){
		throw new SystemException(e.getMessage(),e);
	}
	  
	  return agentVO; 
  }
}
