package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import java.util.Collection;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;

@EProductProxy(module = "operations", submodule = "flthandling", name = "operationsFltHandlingEProxy")
public interface OperationsFltHandlingEProxy {
	Void saveOperationalULDsInFlight(Collection<UldInFlightVO> uldInFlightVOs);
	Void updateOperationalFlightStatus(OperationalFlightVO operationalFlightVO);
}
