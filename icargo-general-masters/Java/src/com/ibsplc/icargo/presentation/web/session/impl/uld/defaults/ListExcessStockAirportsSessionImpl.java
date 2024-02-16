/*
 * ListExcessStockAirportsSessionImpl.java Created on Oct 22, 2012
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.capacity.booking.vo.FlightAvailabilityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentForBookingVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListExcessStockAirportsSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2934
 *
 */
public class ListExcessStockAirportsSessionImpl extends AbstractScreenSession
		implements ListExcessStockAirportsSession {

	private static final String MODULE = "uld.defaults";
	private static final String SCREENID = 
						"uld.defaults.stock.findairportswithexcessstock";
	private static final String KEY_LISTFILTER = 
								"excessStockAirportFilterVO";
	private static final String KEY_ESTIMATEDULDSTOCKLIST = 
									"excessStockAirportVOs";
	private static final String KEY_FLIGHT_DETAILS_LIST = 
		"flightSegmentForBookingVOs";
	private static final String KEY_ESTIMATEDULDSTOCKCOLL = 
									"excessStockAirportVOColl";
	private static final String  LISTSTATUS = "listStatus";	
	private static final String KEY_INDEXMAP = "indexMap";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	private static final String FLIGHT_FILTER_VO = "flightAvailabilityFilterVo";
	private static final String FLT_KEY_INDEXMAP = "fltIndexMap";
	private static final String KEY_TOTALRECORDS = "totalrecords";
	/**
	 *
	 * /** Method to get ScreenID
	 *
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 *
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}

    /** (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.uld.
     *defaults.ListEstimatedULDStockSession#getExcessStockAirportVO()
     * @return ExcessStockAirportVO
     */
    public ExcessStockAirportVO getExcessStockAirportVO() {
    	return null;
    }
    /**
	 * @return Returns the estimatedULDStockFilterVO.
	 */
	public ExcessStockAirportFilterVO getExcessStockAirportFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}

	/**
	 * @return
	 */
    public Page<ExcessStockAirportVO> getExcessStockAirportVOs() {
		return (Page<ExcessStockAirportVO>)getAttribute(KEY_ESTIMATEDULDSTOCKLIST);
	}
    /**
     * 
     * @return
     */
    public Page<FlightSegmentForBookingVO> getFlightSegmentForBookingVOs() {
		return (Page<FlightSegmentForBookingVO>)getAttribute(KEY_FLIGHT_DETAILS_LIST);
	}
    /**
     * @return ArrayList<ExcessStockAirportVO>
     */
    public ArrayList<ExcessStockAirportVO> 
    										getExcessStockAirportVOColl() {
		return (ArrayList<ExcessStockAirportVO>)getAttribute(KEY_ESTIMATEDULDSTOCKCOLL);
	}
    /** (non-Javadoc)
     *@param excessStockAirportVO
     */
    public void setExcessStockAirportVO(
            ExcessStockAirportVO excessStockAirportVO) {
    }
    
  /**
   * @param excessStockAirportFilterVO
   */
	public void setExcessStockAirportFilterVO(ExcessStockAirportFilterVO
			excessStockAirportFilterVO) {
		setAttribute(KEY_LISTFILTER,excessStockAirportFilterVO);
	}
	
	/**
	 * @param excessStockAirportVOs
	 */
    public void setExcessStockAirportVOs(Page<ExcessStockAirportVO> 
    				excessStockAirportVOs) {
				   setAttribute(KEY_ESTIMATEDULDSTOCKLIST,
		           (Page<ExcessStockAirportVO>) excessStockAirportVOs);
	}
    /**
     * @author A-5125
     * @param flightSegmentForBookingVOs
     */
    public void setFlightSegmentForBookingVOs(Page<FlightSegmentForBookingVO> 
    flightSegmentForBookingVOs) {
    	setAttribute(KEY_FLIGHT_DETAILS_LIST,
    				(Page<FlightSegmentForBookingVO>) flightSegmentForBookingVOs);
}
    /**
     * @param excessStockAirportVOColl
     */
    public void setExcessStockAirportColl(
    		ArrayList<ExcessStockAirportVO> excessStockAirportVOColl) {
    	setAttribute(KEY_ESTIMATEDULDSTOCKCOLL,
    		(ArrayList<ExcessStockAirportVO>) excessStockAirportVOColl);
	}
    
    /**
	 * @return Returns the listStatus.
	 */
	public String getListStatus() {
		return getAttribute(LISTSTATUS);
	}

	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setListStatus(String listStatus) {
		setAttribute(LISTSTATUS,listStatus);
	}    
    
    /**
     * 
     * @return HashMap<String,Collection<OneTimeVO>>
     */
    public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}
	/**
	 * This method returns the indexMap
	 * @return indexMap - HashMap<String, String>
	 */
	public HashMap<String, String> getIndexMap(){
		return (HashMap<String, String>)getAttribute(KEY_INDEXMAP);
	}
	
	/**
	 * This method is used to set indexMap to the session
	 * @param indexMap - HashMap<String, String>
	 */
	public void setIndexMap(HashMap<String, String> indexMap){
		setAttribute(KEY_INDEXMAP,indexMap);
	}

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> 
				oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES,oneTimeValues);
	}
	
    /** (non-Javadoc)
     * @see com.ibsplc.icargo.framework.session.
     * ScreenSession#removeAllAttributes()
     */
//    public void removeAllAttributes() {
//    	
//    }
	/**
	 * Methods for FlightAvailabilityFilterVO
	 * 
	 * @return FlightAvailabilityFilterVO
	 */
	public FlightAvailabilityFilterVO getFlightAvailabilityFilterVO() {
		return (FlightAvailabilityFilterVO) getAttribute(FLIGHT_FILTER_VO);
	}
	public void setFlightAvailabilityFilterVO(
			FlightAvailabilityFilterVO flightAvailabilityFilterVO) {
		setAttribute(FLIGHT_FILTER_VO, flightAvailabilityFilterVO);
	}
	public HashMap<String, String> getFltIndexMap(){
		return (HashMap<String, String>)getAttribute(FLT_KEY_INDEXMAP);
	}
	/**
	 * This method is used to set indexMap to the session
	 * @param indexMap - HashMap<String, String>
	 */
	public void setFltIndexMap(HashMap<String, String> indexMap){
		setAttribute(FLT_KEY_INDEXMAP,indexMap);
	}
	/**
	 *	Added by 			: A-7426 on 24-Apr-2017
	 * 	Used for 	:		icrd-174537
	 *	Parameters	:	@return 
	 */
	public Integer getTotalRecords() {
				return getAttribute(KEY_TOTALRECORDS);
	}
	/**
	 *	Added by 			: A-7426 on 24-Apr-2017
	 * 	Used for 	: icrd-174537
	 *	Parameters	:	@param totalRecords 
	 */
	public void setTotalRecords(int totalRecords) {
			setAttribute(KEY_TOTALRECORDS, totalRecords);			
	}
   
}
