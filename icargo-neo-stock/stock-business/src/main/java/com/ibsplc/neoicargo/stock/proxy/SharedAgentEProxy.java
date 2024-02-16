package com.ibsplc.neoicargo.stock.proxy;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "shared", submodule = "agent", name = "sharedAgentEProxy")
public interface SharedAgentEProxy {
  AgentVO findAgentDetails(String companyCode, String agentCode);
}
