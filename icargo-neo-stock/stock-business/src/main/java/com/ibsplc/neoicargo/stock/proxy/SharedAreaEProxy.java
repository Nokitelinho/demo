package com.ibsplc.neoicargo.stock.proxy;

import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;
import java.util.Collection;
import java.util.Map;

@EProductProxy(module = "shared", submodule = "area", name = "sharedAreaEProxy")
public interface SharedAreaEProxy {
  Map<String, String> findAirportParametersByCode(
      String companyCode, String airportCode, Collection<String> parameterCodes);
}
