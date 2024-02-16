/*
 * CaptureInvoiceSession.java Created on july 30, 2008
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;

/**
 * @author A-3456
 *
 */
public interface CaptureInvoiceSession extends ScreenSession{
	/**
	 * 
	 * @return AirlineCN51FilterVO
	 */
	public AirlineCN51FilterVO getFilterVo();
	
	/**
	 * 
	 * @param AirlineCN51FilterVO
	 */
	public void setFilterVo(AirlineCN51FilterVO airlineCN51FilterVO);
	/**
	  * 
	  * @param AirlineCN51SummaryVO
	  */
	 public void setAirlineCN51SummaryVO(AirlineCN51SummaryVO airlineCN51SummaryVO);
	 /**
	  * 
	  * @return AirlineCN51SummaryVO
	  */
	 public AirlineCN51SummaryVO getAirlineCN51SummaryVO();
	 /**
    * 
    *for removing filter vo
    */
	 public void removeFilterVo();
	 /**
	  * for removing Airlinec51Summary vo
	  *
	  */
	 
	 public void removeAirlineCN51SummaryVO();
	 
	 
		/**
		 *   Method to get the onetime map in the
		 *         session
		 * @return HashMap the onetime map from session
		 */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeMap();

		/**
		 *  Method to set the Onetimes map to session
		 * @param oneTimeMap
		 *            The one time map to be set to session
		 */
		public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap);

		/**
		 *  Method to remove One Time Map from
		 *         session
		 */
		public void removeOneTimeMap();
		 /**
	     * @param WeightRoundingVO WeightRoundingVO
	     */
	    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO);
	    /**
	     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
	     */
	    public UnitRoundingVO getWeightRoundingVO() ;
	    /**
	     * @param key
	     */
	    public void removeWeightRoundingVO(String key) ;
}
