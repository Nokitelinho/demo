package com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "shared", submodule = "airline", name = "sharedAirlineEProxy")
public interface SharedAirlineEProxy {
	AirlineValidationVO validateAlphaCode(String companyCode, String airlineCode);
}
