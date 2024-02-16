package com.ibsplc.icargo.business.master.defaults.proxy;

import java.rmi.RemoteException;
import java.util.List;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.agent.AgentBI;
import com.ibsplc.icargo.business.shared.agent.vo.AgentFilterVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class SharedAgentProxy  extends SubSystemProxy {
	public SharedAgentProxy() {
	}
	public List<AgentVO> findAgentMasters(AgentFilterVO filterVO)
	throws SystemException {
		try {
			AgentBI agentBI = (AgentBI) getService("SHARED_AGENT");
			return agentBI.findAgentMaster(filterVO);
			} catch (ServiceNotAccessibleException e) {
				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
			} catch (RemoteException e) {
				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
			}
	}
}
