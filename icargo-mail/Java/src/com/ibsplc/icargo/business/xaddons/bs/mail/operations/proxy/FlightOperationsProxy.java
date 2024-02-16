/*
 * FlightOperationsProxy.java Created on Sep 24, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;

import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.FlightOperationsProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	24-Sep-2017	:	Draft
 */

@Module("flight")
@SubModule("operation")
public class FlightOperationsProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("BSMAIL_OPERATIONS");

	private static final String MODULE = "FlightOperationsProxy";
	

	/**
	 * 
	 * 	Method		:	FlightOperationsProxy.validateFlightForAirport
	 *	Added by 	:	a-7779 on 24-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param flightFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<FlightValidationVO>
	 */
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
	 * @author A-7779
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
			if(flightNumber!=null && flightNumber.trim().length()>0){
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
}
