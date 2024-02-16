package com.ibsplc.neoicargo.stock.proxy.impl;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.proxy.OperationsShipmentEProxy;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class OperationShipmentProxyTest {

  @InjectMocks private OperationShipmentProxy proxy;

  @Mock private OperationsShipmentEProxy eProxy;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateRangesWithoutThrows() throws BusinessException {
    // When
    doNothing().when(eProxy).blacklistRange(anyString(), anyInt(), anyList());

    // Then
    Assertions.assertDoesNotThrow(() -> proxy.blacklistRange(new BlacklistStockVO()));
  }
}
