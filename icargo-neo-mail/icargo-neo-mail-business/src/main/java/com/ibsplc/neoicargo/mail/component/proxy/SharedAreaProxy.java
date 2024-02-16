package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.SharedAreaEProxy;
import com.ibsplc.neoicargo.mail.exception.SharedProxyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/** 
 * This is the product proxy for Shared Area
 * @author A-1936
 */
@Component
@Slf4j
public class SharedAreaProxy {
	@Autowired
	private SharedAreaEProxy sharedAreaEProxy;
	private static final String SHARED_AREA = "SHARED_AREA";

	/** 
	* @author a-1936 This method is used to validate the City
	* @param companyCode
	* @param cityCodes
	* @return
	* @throws SystemException
	* @throws SharedProxyException
	*/
	public Map<String, CityVO> validateCityCodes(String companyCode, Collection<String> cityCodes)
			throws SharedProxyException {
		log.debug("SHARED_AREA_PROXY" + " : " + "validateCityCodes" + " Entering");
		Map<String, CityVO> cityMap = null;
		try {
			cityMap = sharedAreaEProxy.validateCityCodes(companyCode, cityCodes);
		} catch (ServiceException e) {
			for (ErrorVO error : e.getErrors()) {
				throw new SharedProxyException(SharedProxyException.INVALID_CITY,
						new Object[] { error.getErrorData() });
			}
		}
		return cityMap;
	}

	/** 
	* @author a-1936 This method is used to validate the Country
	* @param companyCode
	* @param countryCodes
	* @return
	* @throws SystemException
	* @throws SharedProxyException
	*/
	public Map<String, CountryVO> validateCountryCodes(String companyCode, Collection<String> countryCodes)
			throws SharedProxyException {
		log.debug("AreaDelegate" + " : " + "validateCountryCodes" + " Entering");
		Map<String, CountryVO> countryMap = null;
		try {
			countryMap = sharedAreaEProxy.validateCountryCodes(companyCode, countryCodes);
		} catch (ServiceException e) {
			for (ErrorVO error : e.getErrors()) {
				throw new SharedProxyException(SharedProxyException.INVALID_COUNTRY,
						new Object[] { error.getErrorData() });
			}
		}
		return countryMap;
	}

	/** 
	* @author a-1883
	* @param companyCode
	* @param airportCode
	* @param parameterCodes
	* @return
	* @throws SystemException
	*/
	public Map<String, String> findAirportParametersByCode(String companyCode, String airportCode,
			Collection<String> parameterCodes) {
		try {
			log.debug("SharedAreaProxy" + " : " + "findAirportParametersByCode" + " Entering");
			return sharedAreaEProxy.findAirportParametersByCode(companyCode, airportCode, parameterCodes);
		} finally {
		}
	}

	/** 
	* Used for validating  a collection of airportCodes. Returns NULL if the airportCode does not exist
	* @author A-2246
	* @param companyCode
	* @param airportCodes
	* @return Collection
	* @throws SystemException
	*/
	public Map<String, AirportValidationVO> validateAirportCodes(String companyCode, Collection<String> airportCodes) {
		log.debug("SharedAreaProxy" + " : " + "findAirportParametersByCode" + " Entering");
		Map<String, AirportValidationVO> arpMap = null;
		try {
			arpMap = sharedAreaEProxy.validateAirportCodes(companyCode, airportCodes);
		} catch (ServiceException e) {
			log.debug("AreaBusinessException");
		}
		return arpMap;
	}

	/** 
	* Used for validating  a airportCode. Returns NULL if the airportCode does not exist
	* @author A-2246
	* @param companyCode
	* @return Collection
	* @throws SystemException
	*/
	public AirportValidationVO validateAirportCode(String companyCode, String airportCode) {
		log.debug("SharedAreaProxy" + " : " + "validateAirportCode" + " Entering");
		AirportValidationVO airportValidationVO = null;
		try {
			airportValidationVO = sharedAreaEProxy.validateAirportCode(companyCode, airportCode);
		} catch (ServiceException e) {
			log.debug("AreaBusinessException");
		}
		return airportValidationVO;
	}

	/** 
	* @author A-8353
	* @param companyCode
	* @param parameterCodes
	* @return
	* @throws SystemException
	*/
	public Map<String, String> findStationParametersByCode(String companyCode, String stationCode,
			Collection<String> parameterCodes) {
		try {
			log.debug("SharedAreaProxy" + " : " + "findStationParametersByCode" + " Entering");
			return sharedAreaEProxy.findStationParametersByCode(companyCode, stationCode, parameterCodes);
		} finally {
		}
	}

	public String getDefaultCurrencyForStation(String companyCode, String stationCode) throws BusinessException {
		try {
			return sharedAreaEProxy.defaultCurrencyForStation(companyCode, stationCode);
		} catch (ServiceException e) {
			throw new BusinessException(e);
		}
	}
	public AirportVO findAirportDetails(String companyCode, String airportCode) throws SystemException{
		return sharedAreaEProxy.findAirportDetails(companyCode, airportCode);
	}
}
