package com.ibsplc.neoicargo.stock.proxy.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.stock.proxy.SharedAirlineEProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class SharedAirlineProxyTest {

  @InjectMocks private SharedAirlineProxy sharedAirlineProxy;

  @Mock private SharedAirlineEProxy sharedAirlineEProxy;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnAirlineValidationVO() throws BusinessException {
    doReturn(new AirlineValidationVO()).when(sharedAirlineEProxy).validateNumericCode("IBS", "123");

    var actual = sharedAirlineProxy.validateNumericCode("IBS", "123");

    assertThat(actual).isNotNull();
    verify(sharedAirlineEProxy).validateNumericCode("IBS", "123");
  }

  @Test
  void shouldThrowException() throws BusinessException {
    doThrow(ServiceException.class).when(sharedAirlineEProxy).validateNumericCode("IBS", "INVALID");

    assertThrows(
        BusinessException.class, () -> sharedAirlineProxy.validateNumericCode("IBS", "INVALID"));

    verify(sharedAirlineEProxy).validateNumericCode("IBS", "INVALID");
  }
}
