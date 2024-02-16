/*
 * CapacityBookingProxy.java Created on Aug 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.rmi.RemoteException;


import com.ibsplc.icargo.business.capacity.booking.CapacityBookingBusinessException;

import com.ibsplc.icargo.business.capacity.booking.vo.FlightAvailabilityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentForBookingVO;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;

import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5125
 * 
 *
 */
@Module("capacity")
@SubModule("booking")
public class CapacityBookingProxy extends ProductProxy {
	
	private Log log = LogFactory.getLogger("FLIGHT_OPERATION");
	
 
	/**
	 * @author A-5125
	 * @param flightAvailabilityFilterVO
	 * @return Page<FlightSegmentForBookingVO>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws CapacityBookingBusinessException
	 * @throws ProxyException 
	 */
    public Page<FlightSegmentForBookingVO> findFlightsForBooking(
			FlightAvailabilityFilterVO flightAvailabilityFilterVO)
			throws SystemException, RemoteException,
			CapacityBookingBusinessException, ProxyException {
    	  log.entering( "CapacityBookingProxy in Uld","findFlightsForBooking" );
		return despatchRequest("findFlightsForBooking",flightAvailabilityFilterVO);
	}

    




}
