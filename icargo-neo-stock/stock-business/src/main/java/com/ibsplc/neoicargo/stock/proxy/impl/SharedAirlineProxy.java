package com.ibsplc.neoicargo.stock.proxy.impl;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.stock.proxy.SharedAirlineEProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SharedAirlineProxy {

  private final SharedAirlineEProxy sharedAirlineEProxy;

  public AirlineValidationVO validateNumericCode(String companyCode, String numericCode)
      throws BusinessException {
    try {
      return sharedAirlineEProxy.validateNumericCode(companyCode, numericCode);
    } catch (ServiceException exception) {
      log.error("Failed validation numeric code", exception);
      throw new BusinessException(exception);
    }
  }
}
