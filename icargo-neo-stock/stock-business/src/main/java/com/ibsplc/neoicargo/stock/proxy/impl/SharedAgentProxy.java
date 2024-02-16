package com.ibsplc.neoicargo.stock.proxy.impl;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.stock.proxy.SharedAgentEProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SharedAgentProxy {
  private final SharedAgentEProxy sharedAgentEProxy;

  public AgentVO findAgentDetails(String companyCode, String agentCode) {
    try {
      return sharedAgentEProxy.findAgentDetails(companyCode, agentCode);
    } catch (ServiceException exception) {
      log.error("Failed find agent details", exception);
      return null;
    }
  }
}
