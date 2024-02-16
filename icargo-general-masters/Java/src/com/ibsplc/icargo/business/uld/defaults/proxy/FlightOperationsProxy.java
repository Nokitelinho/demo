/*
 * FlightOperationsProxy.java Created on July 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.util.Collection;

import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.flight.operation.vo.MarkFlightMovementVO;


/**
 * @author A-1945
 *
 */
@Module("flight")
@SubModule("operation")
public class FlightOperationsProxy extends ProductProxy {
	private Log log = LogFactory.getLogger("ULD DEFAULTS");
    /**
	 * This method validates the flight to check if the flight exists and returns 
	 * the flight specific details.
	 * @param flightFilterVo
	 * @return Collection<FlightValidationVO>
	 * @throws SystemException
	 * @throws ProxyException
	 *
	 */
    public Collection<FlightValidationVO> fetchFlightDetails(FlightFilterVO flightFilterVo)
 	throws ProxyException ,SystemException{
    	return  despatchRequest("validateFlightForAirport",flightFilterVo);
    }
    public FlightVO findFlightDetails(String companyCode, int flightCarrierIdentifier,
    		String flightNumber, long flightSequenceNumber)
 	throws ProxyException ,SystemException{
    	return  despatchRequest("findFlightDetails",companyCode,flightCarrierIdentifier,flightNumber,flightSequenceNumber);
    }    
    public Collection<MarkFlightMovementVO> listMarkFlightMovement(FlightValidationVO validationVO)
 	throws ProxyException ,SystemException{
    	return  despatchRequest("listMarkFlightMovement",validationVO);
    }
    /**
     * Method to validate flight segments
     * 
     * @param flightFilterVOs
     * @return
     * @throws ProxyException
     * @throws SystemException
     */
    public Collection<FlightFilterVO> validateFlightsForSegments(
    	Collection<FlightFilterVO> flightFilterVOs)
    	throws ProxyException ,SystemException{
    	log.entering("FlightOperationsProxy","validateFlightsForSegments");
    	return despatchRequest("validateFlightsForSegments",flightFilterVOs);
    }
    /**
     *
     * @param flightFilterVO
     * @return
     * @throws ProxyException
     * @throws SystemException
     */
    public Collection<FlightValidationVO> validateFlight(FlightValidationFilterVO flightFilterVO) throws ProxyException,
			SystemException {
		log.entering("FlightDelegate", "valdiateFlight");
		return despatchRequest("validateFlight", flightFilterVO);
    }
}
