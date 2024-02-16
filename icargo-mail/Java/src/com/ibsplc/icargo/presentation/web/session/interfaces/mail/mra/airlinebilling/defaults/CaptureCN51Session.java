/*
 * CaptureCN51Session.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
/**
 * @author Rani
 * Session implementation for CaptureForm1 screen
 * 
 * Revision History     
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1		Feb 21, 2007     Rani				Initial draft
 */
public interface CaptureCN51Session extends ScreenSession {
	/**
	 * @return Returns the oneTimeValues.
	 */
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	  /**
	 * @param airlineCN51FilterVO
	 */
    public void setFilterDetails(AirlineCN51FilterVO airlineCN51FilterVO);
    /**
	 * @return Returns the AirlineCN51FilterVO.
	 */
    public AirlineCN51FilterVO getFilterDetails();
    /**
	 * @return Returns the AirlineCN51SummaryVO.
	 */
    public AirlineCN51SummaryVO getCn51Details();
    /**
	 * @param airlineCN51SummaryVO
	 */
    public void setCn51Details(AirlineCN51SummaryVO airlineCN51SummaryVO);
    /**
     * Methods for getting parentId
     * @return parentId
     */
    public String getParentId();
   /**
    * Methods for setting ParentId
    * @param parentId
    */
    public void setParentId(String parentId);
    /**
     * This method removes the ParentId in session
     */
     public void removeParentId();	
     
     /**
 	 * @return Returns the AirlineCN51DetailsVO.
 	 */
     public AirlineCN51DetailsVO getCurrentCn51Detail();
     /**
 	 * @param airlineCN51DetailsVO
 	 */
     public void setCurrentCn51Detail(AirlineCN51DetailsVO airlineCN51DetailsVO);
     
     /**
  	 * @return Returns the Collection<AirlineCN51DetailsVO>.
  	 */
      public ArrayList<AirlineCN51DetailsVO> getSelectedCn51DetailsVOs();
     
      /**
       * 
       * @param airlineCN51DetailsVOs
       */
      public void setSelectedCn51DetailsVOs(ArrayList<AirlineCN51DetailsVO> airlineCN51DetailsVOs);
      

      public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO);
	    
      public UnitRoundingVO getVolumeRoundingVO();
    
     public void removeVolumeRoundingVO();
    
     public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO);
    
     public UnitRoundingVO getWeightRoundingVO();
    
     public void removeWeightRoundingVO();
      
}
