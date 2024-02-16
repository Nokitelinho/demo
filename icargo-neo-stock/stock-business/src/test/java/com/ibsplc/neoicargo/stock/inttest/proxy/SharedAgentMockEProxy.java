package com.ibsplc.neoicargo.stock.inttest.proxy;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.neoicargo.stock.proxy.SharedAgentEProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class SharedAgentMockEProxy implements SharedAgentEProxy {

  @Override
  public AgentVO findAgentDetails(String companyCode, String agentCode) {
    if ("agent".equals(agentCode) || "A1001".equals(agentCode)) {
      return new AgentVO();
    }
    return null;
  }
}
