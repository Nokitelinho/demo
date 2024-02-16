package com.ibsplc.neoicargo.stock.proxy.impl;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_ENABLE_STOCK_HISTORY;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.proxy.SharedDefaultsEProxy;
import java.util.Collection;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SharedDefaultsProxy {

  private final SharedDefaultsEProxy sharedDefaultsEProxy;

  public Map<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes)
      throws BusinessException {
    try {
      log.info("Request body: {}", systemParameterCodes);
      var parameters = sharedDefaultsEProxy.findSystemParameterByCodes(systemParameterCodes);
      log.info("Income parameters");
      parameters.forEach(
          (key, value) -> {
            log.info("key: {}, value: {}", key, value);
          });
      return parameters;
    } catch (Exception serviceException) {
      log.error("Find airport parameter failed", serviceException);
      return Map.of(STOCK_DEFAULTS_ENABLE_STOCK_HISTORY, "Y");
    }
  }
}
