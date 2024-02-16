package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.SharedAirlineEProxy;
import com.ibsplc.neoicargo.mail.exception.SharedProxyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/** 
 * @author A-1617
 */
@Component
@Slf4j
public class SharedAirlineProxy {
	@Autowired
	private SharedAirlineEProxy sharedAirlineEProxy;
	private static final String SHARED_AIRLINE = "SHARED_AIRLINE";

	/** 
	* This method is used to validate the AlphaCode
	* @author A-1936
	* @param companyCode
	* @param airlineCode
	* @return
	* @throws SystemException
	* @throws SharedProxyException
	*/
	public AirlineValidationVO validateAlphaCode(String companyCode, String airlineCode) throws SharedProxyException {
		log.debug("INSIDE THE AIRLINE" + " : " + "PROXY" + " Entering");
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = sharedAirlineEProxy.validateAlphaCode(companyCode, airlineCode);
		} catch (ServiceException e) {
			for (ErrorVO error : e.getErrors()) {
				throw new SharedProxyException(SharedProxyException.INVALID_AIRLINE,
						new Object[] { error.getErrorData() });
			}
		}
		return airlineValidationVO;
	}

	public AirlineValidationVO findAirline(String companyCode, int carrierId) throws SharedProxyException {
		log.debug("INSIDE THE AIRLINE" + " : " + "PROXY" + " Entering");
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = sharedAirlineEProxy.findAirline(companyCode, carrierId);
		} catch (ServiceException e) {
			for (ErrorVO error : e.getErrors()) {
				throw new SharedProxyException(SharedProxyException.INVALID_AIRLINE,
						new Object[] { error.getErrorData() });
			}
		}
		return airlineValidationVO;
	}

	public Map<String, String> findAirlineParametersByCode(
			String companyCode, int airlineIdentifier, Collection<String> parameterCodes)
		throws SystemException{
		return  sharedAirlineEProxy.findAirlineParametersByCode(companyCode,airlineIdentifier,parameterCodes);
	}
	public AirlineVO findAirlineDetails(String companyCode, int airlineIdentifier)
		throws SystemException{
		return  sharedAirlineEProxy.findAirlineDetails(companyCode,airlineIdentifier);
	}
	public Collection<AirlineAirportParameterVO> findAirlineAirportParameters(
			AirlineAirportParameterFilterVO airlineAirportParameterFilterVO) throws SystemException{
		return  sharedAirlineEProxy.findAirlineAirportParameters(airlineAirportParameterFilterVO);
	}

}
