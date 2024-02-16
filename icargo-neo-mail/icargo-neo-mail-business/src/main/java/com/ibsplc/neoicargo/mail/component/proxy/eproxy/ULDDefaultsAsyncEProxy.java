package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "uld", submodule = "defaults", name = "uLDDefaultsAsyncEProxy", asyncDispatch=true)

public interface ULDDefaultsAsyncEProxy {
	void updateULDForOperations(FlightDetailsVO flightDetailsVO);
}
