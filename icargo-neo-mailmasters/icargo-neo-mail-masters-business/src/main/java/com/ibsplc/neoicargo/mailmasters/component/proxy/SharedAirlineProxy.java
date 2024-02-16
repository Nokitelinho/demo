package com.ibsplc.neoicargo.mailmasters.component.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.mailmasters.exception.SharedProxyException;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy.SharedAirlineEProxy;
import org.springframework.beans.factory.annotation.Autowired;

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
}
