package com.ibsplc.neoicargo.stock.proxy.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.stock.proxy.SharedAgentEProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class SharedAgentProxyTest {

  @InjectMocks private SharedAgentProxy sharedAgentProxy;

  @Mock private SharedAgentEProxy sharedAgentEProxy;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnAgentVO() {
    // Given
    var companyCode = "AV";
    var agentCode = "ACDC";
    var agentVO = new AgentVO();

    // When
    doReturn(agentVO).when(sharedAgentEProxy).findAgentDetails(companyCode, agentCode);

    // Then
    var agentDetails = sharedAgentProxy.findAgentDetails(companyCode, agentCode);
    verify(sharedAgentEProxy, times(1)).findAgentDetails(companyCode, agentCode);
    assertNotNull(agentDetails);
  }

  @Test
  void shouldReturnNull() {
    // Given
    var companyCode = "AV";
    var agentCode = "ACDC";
    var agentVO = new AgentVO();
    agentVO.setAccountCode("code");

    // When
    doThrow(ServiceException.class)
        .when(sharedAgentEProxy)
        .findAgentDetails(companyCode, agentCode);

    // Then
    var agentDetails = sharedAgentProxy.findAgentDetails(companyCode, agentCode);
    verify(sharedAgentEProxy, times(1)).findAgentDetails(companyCode, agentCode);
    assertNull(agentDetails);
  }
}
