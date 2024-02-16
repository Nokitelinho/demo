package com.ibsplc.neoicargo.stock.proxy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.stock.proxy.SharedAreaEProxy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class SharedAreaProxyTest {

  @InjectMocks private SharedAreaProxy proxy;

  @Mock private SharedAreaEProxy eProxy;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldFindAirportParametersByCodeWithoutThrows() {
    // When
    doReturn(Map.of())
        .when(eProxy)
        .findAirportParametersByCode(anyString(), anyString(), anyCollection());

    // Then
    Assertions.assertDoesNotThrow(() -> proxy.findAirportParametersByCode("IBS", "HQ", List.of()));
  }

  @Test
  void shouldThrowExceptionWhenValidateRanges() {
    // When
    doThrow(ServiceException.class)
        .when(eProxy)
        .findAirportParametersByCode(anyString(), anyString(), anyCollection());

    // Then
    assertThrows(
        BusinessException.class, () -> proxy.findAirportParametersByCode("IBS", "HQ", List.of()));
  }
}
