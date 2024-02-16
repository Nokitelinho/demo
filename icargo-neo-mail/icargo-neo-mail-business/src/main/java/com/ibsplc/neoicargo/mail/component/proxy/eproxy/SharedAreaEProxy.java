package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "shared", submodule = "area", name = "sharedAreaEProxy")
public interface SharedAreaEProxy {
	Map<String, CityVO> validateCityCodes(String companyCode, Collection<String> cityCodes);

	Map<String, CountryVO> validateCountryCodes(String companyCode, Collection<String> countryCodes);

	Map<String, String> findAirportParametersByCode(String companyCode, String airportCode,
			Collection<String> parameterCodes);

	Map<String, AirportValidationVO> validateAirportCodes(String companyCode, Collection<String> airportCodes);

	AirportValidationVO validateAirportCode(String companyCode, String airportCode);

	Map<String, String> findStationParametersByCode(String companyCode, String stationCode,
			Collection<String> parameterCodes);

	String defaultCurrencyForStation(String companyCode, String stationCode);

	AirportVO findAirportDetails(String companyCode, String airportCode);
}
