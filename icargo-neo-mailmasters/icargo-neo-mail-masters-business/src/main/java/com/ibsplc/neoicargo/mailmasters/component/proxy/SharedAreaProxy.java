package com.ibsplc.neoicargo.mailmasters.component.proxy;

import java.util.Collection;
import java.util.Map;
import java.rmi.RemoteException;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.mailmasters.exception.SharedProxyException;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy.SharedAreaEProxy;
import org.springframework.beans.factory.annotation.Autowired;

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
}
