package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "uld", submodule = "defaults", name = "uLDDefaultsEProxy")
public interface ULDDefaultsEProxy {
	Void validateULDsForOperation(FlightDetailsVO flightDetailsVo);

	ULDVO findULDDetails(String companyCode, String uldNum);

    ULDVO findULDDetails(Object object);

	void updateULDForOperations(FlightDetailsVO flightDetailsVO);
}
