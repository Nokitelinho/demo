package com.ibsplc.neoicargo.stock.inttest.proxy;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_STOCK_HOLDER_CODE;

import com.ibsplc.neoicargo.stock.proxy.SharedAreaEProxy;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class SharedAreaMockEProxy implements SharedAreaEProxy {

  @Override
  public Map<String, String> findAirportParametersByCode(
      String companyCode, String airportCode, Collection<String> parameterCodes) {
    return Collections.singletonMap(STOCK_DEFAULTS_STOCK_HOLDER_CODE, "N");
  }
}
