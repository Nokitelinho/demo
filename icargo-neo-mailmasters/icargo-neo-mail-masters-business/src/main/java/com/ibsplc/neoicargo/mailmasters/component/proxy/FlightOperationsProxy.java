package com.ibsplc.neoicargo.mailmasters.component.proxy;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentValidationVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy.FlightOperationsEProxy;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * @author a-1303
 */
@Component
@Slf4j
public class FlightOperationsProxy {
	@Autowired
	private FlightOperationsEProxy flightOperationsEProxy;
	private static final String MODULE = "FlightOperationsProxy";
	public static final String FLT_OPR_INVALIDFLIGHT = "flight.operation.invalidFlightException";
	public static final String FLT_OPR_INVALIDSEGMENTFORFLIGHTEXCEPTION = "flight.operation.invalidSegmentForFlightException";

	/** 
	* @author a-1936 This method is used to validate the Flight
	* @param flightFilterVO
	* @return
	* @throws SystemException
	*/
	public Collection<FlightValidationVO> validateFlightForAirport(FlightFilterVO flightFilterVO) {
		log.debug(MODULE + " : " + "validateFlightForAirport" + " Entering");
		log.debug("" + "INSIDE THE FLIGHT OPEARTIONS PROXY FILTER VO IS " + " " + flightFilterVO);
		if (!isFlight(flightFilterVO.getFlightNumber())) {
			return null;
		}
		try {
			return flightOperationsEProxy.validateFlightForAirport(flightFilterVO);
		} catch (SystemException proxyException) {
			throw new SystemException(proxyException.getMessage(), proxyException);
		}
	}

	/** 
	* Method		:	FlightOperationsProxy.isFlight Added by 	:	A-8061 on 09-Mar-2020 Used for 	:	IASCB-38025 Parameters	:	@param flightNumber Parameters	:	@return  Return type	: 	boolean
	*/
	public boolean isFlight(String flightNumber) {
		boolean isFlight = true;
		if (flightNumber == null || flightNumber.trim().length() == 0 || "0".equals(flightNumber)
				|| "0000".equals(flightNumber) || MailConstantsVO.DESTN_FLT_STR.equals(flightNumber)) {
			isFlight = false;
		}
		return isFlight;
	}
}
