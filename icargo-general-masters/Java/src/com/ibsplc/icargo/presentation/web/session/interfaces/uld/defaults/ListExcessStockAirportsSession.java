/*
 * ListExcessStockAirportsSession.java Created on Sep 22, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.capacity.booking.vo.FlightAvailabilityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentForBookingVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2934
 *
 */
public interface ListExcessStockAirportsSession extends ScreenSession {
	    
    /**
     * 
     * @param ExcessStockAirportVO
     */
    void setExcessStockAirportVO(ExcessStockAirportVO excessStockAirportVO);
    /**
     * 
     * @param excessStockAirportVOs
     */
    void setExcessStockAirportVOs(Page<ExcessStockAirportVO> excessStockAirportVOs);
    /**
     * 
     * @param excessStockAirportVOColl
     */
    void setExcessStockAirportColl(ArrayList<ExcessStockAirportVO> excessStockAirportVOColl);
    /**
	 * @param excessStockAirportFilterVO 
	 * The excessStockAirportFilterVO to set.
	 */
    public void setExcessStockAirportFilterVO (ExcessStockAirportFilterVO excessStockAirportFilterVO);

    /**
	 * @return Returns the excessStockAirportFilterVO.
	 */
    ExcessStockAirportFilterVO getExcessStockAirportFilterVO();
	
	/**
	 * 
	 * @return ExcessStockAirportVO
	 */		
    ExcessStockAirportVO getExcessStockAirportVO();
    /**
     * 
     * @return Page<ExcessStockAirportVO>
     */
    Page<ExcessStockAirportVO> getExcessStockAirportVOs();
    /**
     * 
     * @return ArrayList<ExcessStockAirportVO> 
     */
    ArrayList<ExcessStockAirportVO> getExcessStockAirportVOColl();
    
    /**
	 * @return Returns the listStatus.
	 */
	String getListStatus();

	/**
	 * @param listStatus The listStatus to set.
	 */
	void setListStatus(String listStatus);
	/**
	 * 
	 */
    void removeAllAttributes();
    /**
     * 
     * @param oneTimeValues
     */
    void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	/**
	 * 
	 * @return
	 */
	 HashMap<String,Collection<OneTimeVO>> getOneTimeValues();
	/**
	 * The setter method for indexMap
	 * @param indexMap
	 */
	public void setIndexMap(
			HashMap<String, String> indexMap);
    /**
     * The getter method for indexMap
     * @return indexMap
     */
    public HashMap<String, String> getIndexMap();
    /**
     * to display flight booking details
     * @param flightSegmentForBookingVOs
     */
    public void setFlightSegmentForBookingVOs(Page<FlightSegmentForBookingVO> 
    flightSegmentForBookingVOs);
   /**
    * toDisplay Flight Booking Details
    * @author A-5125
    * @return
    */
    public Page<FlightSegmentForBookingVO> getFlightSegmentForBookingVOs();
   /**
    * 
    * @param flightAvailabilityFilterVO
    */
    void setFlightAvailabilityFilterVO(
			FlightAvailabilityFilterVO flightAvailabilityFilterVO);
    /**
     * 
     * @return
     */
    FlightAvailabilityFilterVO getFlightAvailabilityFilterVO();
    
    public void setFltIndexMap(
			HashMap<String, String> indexMap);
    /**
     * The getter method for indexMap
     * @return indexMap
     */
    public HashMap<String, String> getFltIndexMap();
    /**
     * to display flight booking details
     * @param flightSegmentForBookingVOs
     */
    public Integer getTotalRecords();
	public void setTotalRecords(int totalRecords);
}
