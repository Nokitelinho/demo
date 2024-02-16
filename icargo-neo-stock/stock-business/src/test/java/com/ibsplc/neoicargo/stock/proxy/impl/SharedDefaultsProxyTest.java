package com.ibsplc.neoicargo.stock.proxy.impl;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class SharedDefaultsProxyTest {

  @InjectMocks private SharedDefaultsProxy proxy;

  @Mock private ParameterService parameterService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldFindSystemParameterByCodesWithoutThrows() throws BusinessException {
    // When
    doReturn(List.of())
        .when(parameterService)
        .getParameters(anyList(), eq(ParameterType.SYSTEM_PARAMETER));

    // Then
    Assertions.assertDoesNotThrow(() -> proxy.findSystemParameterByCodes(List.of()));
  }

  @Test
  void shouldThrowExceptionWhenFindSystemParameterByCodes() throws BusinessException {
    // When
    doThrow(BusinessException.class)
        .when(parameterService)
        .getParameters(anyList(), eq(ParameterType.SYSTEM_PARAMETER));

    // Then
    Assertions.assertDoesNotThrow(() -> proxy.findSystemParameterByCodes(List.of()));
  }
}
