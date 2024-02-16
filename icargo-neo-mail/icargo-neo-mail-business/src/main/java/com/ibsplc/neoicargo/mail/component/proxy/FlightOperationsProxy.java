package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.flight.operation.vo.*;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.FlightOperationsEProxy;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

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
	* This method is used to find the flightsegments for a particaularFlight
	* @param companyCode
	* @param airlineId
	* @param flightNumber
	* @param flightSequenceNumber
	* @return
	* @throws SystemException
	*/
	public Collection<FlightSegmentSummaryVO> findFlightSegments(String companyCode, int airlineId, String flightNumber,
			long flightSequenceNumber) {
		if (isFlight(flightNumber)) {
			return flightOperationsEProxy.findFlightSegments(companyCode, airlineId, flightNumber,
					flightSequenceNumber);
		} else {
			return null;
		}
	}

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
		return flightOperationsEProxy.validateFlightForAirport(flightFilterVO);
	}

	public FlightSegmentValidationVO validateFlightSegment(FlightSegmentFilterVO segmentFilterVO) {
		log.debug("FlightOperationsProxy" + " : " + "validateFlightSegment" + " Entering");
		return flightOperationsEProxy.validateFlightSegment(segmentFilterVO);

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
	public Collection<FlightSegmentCapacitySummaryVO> findFlightListings(FlightFilterVO filterVo){
		return flightOperationsEProxy.findFlightListings(filterVo);
	}
	public Collection<FlightSegmentCapacitySummaryVO> fetchFlightCapacityDetails(
			Collection<FlightFilterVO> flightFilterVOs) throws SystemException{
		return flightOperationsEProxy.findSegmentCapacityDetailsForMail(flightFilterVOs);
	}
	public Collection<FlightValidationVO> validateFlight(
			FlightFilterVO flightFilterVO) throws SystemException{
		return flightOperationsEProxy.validateFlight(flightFilterVO);
	}
	public HashMap<String, Collection<FlightValidationVO>> validateFlightsForAirport(
			Collection<FlightFilterVO> flightFilterVOs) throws SystemException{
		return flightOperationsEProxy.validateFlightsForAirport(flightFilterVOs);
	}
	public Collection<FlightValidationVO> getNonReferenceFlights(FlightFilterVO flightFilterVO)
	throws SystemException{
		return flightOperationsEProxy.getNonReferenceFlights(flightFilterVO);
	}
}
