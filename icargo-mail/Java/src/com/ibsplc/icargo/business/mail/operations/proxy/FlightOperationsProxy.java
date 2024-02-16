/*
 * FlightOperationsProxy.java Created on May 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentValidationVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVolumeDetailsVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1303
 * 
 */

@Module("flight")
@SubModule("operation")
public class FlightOperationsProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private static final String MODULE = "FlightOperationsProxy";
	
	public static final String FLT_OPR_INVALIDFLIGHT = 
		"flight.operation.invalidFlightException";
	
	public static final String FLT_OPR_INVALIDSEGMENTFORFLIGHTEXCEPTION = 
		"flight.operation.invalidSegmentForFlightException";

	/**
	 * This method is used to find the flightsegments for a particaularFlight
	 * 
	 * @param companyCode
	 * @param airlineId
	 * @param flightNumber
	 * @param flightSequenceNumber
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<FlightSegmentSummaryVO> findFlightSegments(
			String companyCode, int airlineId, String flightNumber,
			long flightSequenceNumber) throws SystemException {
		try {
			if(isFlight(flightNumber)){
			return despatchRequest("findFlightSegments", companyCode,
					airlineId, flightNumber, flightSequenceNumber);
			}else{
				return null;
			}
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}

	}

	/**
	 * @author a-1936 This method is used to validate the Flight
	 * @param flightFilterVO
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public Collection<FlightValidationVO> validateFlightForAirport(
			FlightFilterVO flightFilterVO) throws SystemException {
		log.entering(MODULE, "validateFlightForAirport");
		log.log(Log.FINE, "INSIDE THE FLIGHT OPEARTIONS PROXY FILTER VO IS ",
				flightFilterVO);
		if(!isFlight(flightFilterVO.getFlightNumber())){
			return null;
		}
		try {
			return despatchRequest("validateFlightForAirport", flightFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}
	
	public FlightSegmentValidationVO validateFlightSegment(
			FlightSegmentFilterVO segmentFilterVO) throws SystemException {
		log.entering("FlightOperationsProxy", "validateFlightSegment");
		try {
			return despatchRequest("validateFlightSegment", segmentFilterVO);
		} catch (ProxyException proxyException) {
			for (ErrorVO error : proxyException.getErrors()) {
				if(!error.getErrorCode().equals(FLT_OPR_INVALIDFLIGHT) || 
						!error.getErrorCode().equals(
								FLT_OPR_INVALIDSEGMENTFORFLIGHTEXCEPTION)) {
					throw new SystemException(proxyException.getMessage(),
							proxyException);
				}
			}			
		}
		return null; 
	}
	
	/**
	 * @author a-5526 This method is used to validate the Flight
	 * @param flightFilterVOs
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public HashMap<String, Collection<FlightValidationVO>> validateFlightsForAirport(
			Collection<FlightFilterVO> flightFilterVOs) throws SystemException {
		log.entering(MODULE, "validateFlightForAirport");
		log.log(Log.FINE, "INSIDE THE FLIGHT OPEARTIONS PROXY FILTER VOs IS ",
				flightFilterVOs);     
		try {
			return despatchRequest("validateFlightsForAirport", flightFilterVOs);     
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}
	/**
	 * @author a-8176 This method is used to fetch the flight capacity details with mail accepted data
	 * @param flightFilterVOs
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public Collection<FlightSegmentCapacitySummaryVO> fetchFlightCapacityDetails(Collection<FlightFilterVO> flightFilterVOs) throws SystemException {
		log.entering(MODULE, "fetchFlightCapacityDetails");
				try {
			return despatchRequest("findSegmentCapacityDetailsForMail", flightFilterVOs);     
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}
	/**
	 * 
	 * 	Method		:	FlightOperationsProxy.validateFlight
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param flightFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<FlightValidationVO>
	 */
	public Collection<FlightValidationVO> validateFlight(
    		FlightFilterVO flightFilterVO)
    		throws ProxyException,SystemException {
			if(isFlight(flightFilterVO.getFlightNumber())){
    		return despatchRequest("validateFlightForAirport",flightFilterVO);
			}else{
				return null;
			}
    	}

	/**
	 * 
	 * 	Method		:	FlightOperationsProxy.isFlight
	 *	Added by 	:	A-8061 on 09-Mar-2020
	 * 	Used for 	:	IASCB-38025
	 *	Parameters	:	@param flightNumber
	 *	Parameters	:	@return 
	 *	Return type	: 	boolean
	 */
	public boolean isFlight(String flightNumber){
		boolean isFlight=true;
		if (flightNumber == null || flightNumber.trim().length() == 0 || "0".equals(flightNumber)
				|| "0000".equals(flightNumber) || MailConstantsVO.DESTN_FLT_STR.equals(flightNumber)) {
			isFlight = false;
		}
		return isFlight;
	}
	/**
	 * @author a-8353
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<FlightValidationVO> getNonReferenceFlights(FlightFilterVO flightFilterVO) 
			throws SystemException, ProxyException {		
		return despatchRequest("getNonReferenceFlights",flightFilterVO);
	}
	public Collection<FlightSegmentCapacitySummaryVO> findFlightListings(FlightFilterVO filterVo)
			throws ProxyException, SystemException {
		return despatchRequest("findFlightListings", filterVo);
	}
	public Page<FlightSegmentCapacitySummaryVO> findActiveAllotments(
			FlightSegmentCapacityFilterVO filterVo)
			throws ProxyException, SystemException {
		return despatchRequest("findActiveAllotments", filterVo);
	}
	public Collection<FlightSegmentVolumeDetailsVO> fetchFlightVolumeDetails(Collection<FlightFilterVO> flightFilterVOs)
			throws SystemException {
		log.entering(MODULE, "fetchFlightVolumeDetails");
		try {
			return despatchRequest("findFlightSegmentVolumeDetailsForMail", flightFilterVOs);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(), proxyException);
		}
	}
}
