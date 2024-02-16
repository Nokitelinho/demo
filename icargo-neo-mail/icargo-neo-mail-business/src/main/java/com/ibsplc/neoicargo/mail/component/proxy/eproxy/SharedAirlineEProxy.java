package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

import java.util.Collection;
import java.util.Map;

@EProductProxy(module = "shared", submodule = "airline", name = "sharedAirlineEProxy")
public interface SharedAirlineEProxy {
	AirlineValidationVO validateAlphaCode(String companyCode, String airlineCode);

	AirlineValidationVO findAirline(String companyCode, int carrierId);
	Map<String, String> findAirlineParametersByCode(
			String companyCode, int airlineIdentifier, Collection<String> parameterCodes);
	AirlineVO findAirlineDetails(String companyCode, int airlineIdentifier);

	Collection<AirlineAirportParameterVO> findAirlineAirportParameters(
			AirlineAirportParameterFilterVO airlineAirportParameterFilterVO);
}
