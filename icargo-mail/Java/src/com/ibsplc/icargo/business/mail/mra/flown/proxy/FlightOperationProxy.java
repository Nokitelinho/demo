/*
 * FlightOperationProxy.java Created on Feb 20, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.flown.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * |
 * @author A-2259
 *
 */
@Module("flight")
@SubModule("operation")
public class FlightOperationProxy extends ProductProxy {

    private Log log = LogFactory.getLogger("CRA ACCOUNTING");
    private static final String MODULE = "FlightOperationsProxy";

    /**
	 * @author a-1936 This method is used to validate the Flight
	 * @param flightFilterVO
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public Collection<FlightValidationVO> validateFlight(
			FlightValidationFilterVO flightValidationFilterVO) throws SystemException {
		log.entering(MODULE, "validateFlight");
		log.log(Log.FINE, "INSIDE THE FLIGHT OPEARTIONS PROXY FILTER VO IS ",
				flightValidationFilterVO);
		try {
			return despatchRequest("validateFlight", flightValidationFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}
	public Collection<FlightValidationVO> validateFlightForAirport(
			FlightFilterVO flightFilterVO) throws SystemException {
		log.entering(MODULE, "validateFlightForAirport");
		log.log(Log.FINE, "INSIDE THE FLIGHT OPEARTIONS PROXY FILTER VO IS ",
				flightFilterVO);
		try {
			return despatchRequest("validateFlightForAirport", flightFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}
	/**
	 * @author A-7371
	 * @param companyCode
	 * @param airlineId
	 * @param flightNumber
	 * @param flightSequenceNumber
	 * @return
	 * @throws SystemException
	 */
	public Collection<FlightSegmentSummaryVO> findFlightSegments(
			String companyCode, int airlineId, String flightNumber,
			long flightSequenceNumber) throws SystemException {
		try {
			return despatchRequest("findFlightSegments", companyCode,
					airlineId, flightNumber, flightSequenceNumber);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}

	}
}
