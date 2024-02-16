/*
 * CaptureCN51SessionImpl.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;

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
public class CaptureCN51SessionImpl extends AbstractScreenSession implements CaptureCN51Session {

private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	private static final String KEY_FILTERVO="AirlineCN51FilterVO";
	private static final String KEY_DETAILS="AirlineCN51SummaryVO";
	private static final String KEY_CN51DETAIL="AirlineCN51DetailsVO";
	private static final String KEY_SELECTED_CN51DETAIL_VOS="selectedCn51DetailsVOs";
	private static final String KEY_PARENTID="parentId";
    private static final  String KEY_VOLUMEROUNDINGVO = "volumeRounding";
	private static final  String KEY_WEIGHTROUNDINGVO = "weightRounding";
	 /**
     * @return
     */
    @Override
    public String getScreenID() {

        return SCREENID;
    }

    /**
     * @return
     */
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }
    /**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES,oneTimeValues);
	}
	  /**
	 * @param airlineCN51FilterVO
	 */
    public void setFilterDetails(AirlineCN51FilterVO airlineCN51FilterVO) {
    	setAttribute(KEY_FILTERVO,
    			(AirlineCN51FilterVO)airlineCN51FilterVO);
    }

    /**
	 * @return Returns the AirlineCN51FilterVO.
	 */
    public AirlineCN51FilterVO getFilterDetails() {
    	return ((AirlineCN51FilterVO) getAttribute(KEY_FILTERVO));
    }
    /**
	 * @return Returns the AirlineCN51SummaryVO.
	 */
    public AirlineCN51SummaryVO getCn51Details(){
    	return ((AirlineCN51SummaryVO) getAttribute(KEY_DETAILS));
    }
    /**
	 * @param airlineCN51SummaryVO
	 */
    public void setCn51Details(AirlineCN51SummaryVO airlineCN51SummaryVO){
    	setAttribute(KEY_DETAILS,
    			(AirlineCN51SummaryVO)airlineCN51SummaryVO);
    }
    /**
	 * Methods for getting parentId
	 * @return parentId
	 */
	public String getParentId(){
		return getAttribute(KEY_PARENTID);
	}
	/**
	 * Methods for setting ParentId
	 * @param parentId
	 */
	public void setParentId(String parentId){
		setAttribute(KEY_PARENTID,parentId);
	}
	/**
    * This method removes the ParentId in session
    */
	public void removeParentId(){
		removeAttribute(KEY_PARENTID);
    }
	 /**
	 * @return Returns the AirlineCN51DetailsVO.
	 */
    public AirlineCN51DetailsVO getCurrentCn51Detail(){
    	return ((AirlineCN51DetailsVO) getAttribute(KEY_CN51DETAIL));
    }
    /**
	 * @param airlineCN51DetailsVO
	 */
    public void setCurrentCn51Detail(AirlineCN51DetailsVO airlineCN51DetailsVO){
    	setAttribute(KEY_CN51DETAIL,
    			(AirlineCN51DetailsVO) airlineCN51DetailsVO);
    }

    /**
	 * @return Returns the ArrayList<AirlineCN51DetailsVO>.
     */
	public ArrayList<AirlineCN51DetailsVO> getSelectedCn51DetailsVOs() {
    	return ((ArrayList<AirlineCN51DetailsVO>) getAttribute(KEY_SELECTED_CN51DETAIL_VOS));
	}

    /**
	 * @param airlineCN51DetailsVOs
	 */
	public void setSelectedCn51DetailsVOs(ArrayList<AirlineCN51DetailsVO> airlineCN51DetailsVOs) {
    	setAttribute(KEY_SELECTED_CN51DETAIL_VOS,(ArrayList<AirlineCN51DetailsVO>)airlineCN51DetailsVOs);
	}
	
	 public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO) {
	    	setAttribute(KEY_VOLUMEROUNDINGVO, unitRoundingVO);
	    }

	    public UnitRoundingVO getVolumeRoundingVO() {
	    	return getAttribute(KEY_VOLUMEROUNDINGVO);
	    }

	    public void removeVolumeRoundingVO() {   
	    	removeAttribute(KEY_VOLUMEROUNDINGVO);
	    }  
	    
	    
	    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO) {
	    	setAttribute(KEY_WEIGHTROUNDINGVO, unitRoundingVO);
	    }

	    public UnitRoundingVO getWeightRoundingVO() {
	    	return getAttribute(KEY_WEIGHTROUNDINGVO);
	    }

	    public void removeWeightRoundingVO() {
	    	removeAttribute(KEY_WEIGHTROUNDINGVO);
	    	
	    } 
	
}