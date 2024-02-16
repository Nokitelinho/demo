package com.ibsplc.neoicargo.stock.proxy;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "shared", submodule = "airline", name = "sharedAirlineEProxy")
public interface SharedAirlineEProxy {
  AirlineValidationVO validateNumericCode(String companyCode, String numericCode);
}