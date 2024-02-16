package com.ibsplc.neoicargo.stock.inttest.proxy;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_CONFIRMATION_REQUIRED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_ENABLE_STOCK_HISTORY;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_STOCK_INTRODUCTION_PERIOD;

import com.ibsplc.neoicargo.stock.proxy.SharedDefaultsEProxy;
import com.ibsplc.neoicargo.stock.proxy.impl.SharedDefaultsProxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class SharedDefaultsMockEProxy extends SharedDefaultsProxy {

  public SharedDefaultsMockEProxy(SharedDefaultsEProxy sharedDefaultsEProxy) {
    super(sharedDefaultsEProxy);
  }

  @Override
  public Map<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes) {
    final var valueByCode = new HashMap<String, String>();
    valueByCode.put(STOCK_DEFAULTS_ENABLE_STOCK_HISTORY, "Y");
    valueByCode.put(STOCK_DEFAULTS_CONFIRMATION_REQUIRED, "N");
    valueByCode.put(STOCK_DEFAULTS_STOCK_INTRODUCTION_PERIOD, "2");
    valueByCode.put(STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM, "Y");
    return valueByCode;
  }
}
