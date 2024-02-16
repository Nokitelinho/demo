package com.ibsplc.neoicargo.stock.proxy.impl;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.stock.proxy.SharedAreaEProxy;
import java.util.Collection;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SharedAreaProxy {
  private final SharedAreaEProxy sharedAreaEProxy;

  public Map<String, String> findAirportParametersByCode(
      String companyCode, String airportCode, Collection<String> parameterCodes)
      throws BusinessException {
    try {
      log.info(
          "Request body: \ncompanyCode: {},\nairportCode: {},\nparameterCodes: {}",
          companyCode,
          airportCode,
          parameterCodes);
      final var airportParametersByCode =
          sharedAreaEProxy.findAirportParametersByCode(companyCode, airportCode, parameterCodes);
      log.info("Response body: {}", airportParametersByCode);
      return airportParametersByCode;
    } catch (ServiceException serviceException) {
      log.error("Find airport parameter failed", serviceException);
      throw new BusinessException(serviceException);
    }
  }
}
