package com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy;

import java.util.Collection;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "flight", submodule = "operation", name = "flightOperationsEProxy")
public interface FlightOperationsEProxy {
	Collection<FlightValidationVO> validateFlightForAirport(FlightFilterVO flightFilterVO);
}
