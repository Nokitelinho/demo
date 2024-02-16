/*
 * ListULDSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1347
 *
 */
public interface ListULDSession extends ScreenSession {
	 
	/**
	 * 
	 * @param oneTimeValues
	 */
	 void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	 /**
	  * 
	  * @return Returns the onetime values.
	  */
	 HashMap<String,Collection<OneTimeVO>> getOneTimeValues();
			
    /**
     * 
     * @param Page<ULDListVO>
     */
	void setListDisplayPage(Page<ULDListVO> uldListVOs);
	
	/**
	 * 
	 * @return Page<ULDListVO>
	 */		
    Page<ULDListVO> getListDisplayPage();
    
    /**
	 * @return Returns the listFilterVO.
	 */
	ULDListFilterVO getListFilterVO();

	/**
	 * @param listFilterVO The listFilterVO to set.
	 */
	void setListFilterVO(ULDListFilterVO listFilterVO);
	
	/**
	 * @return Returns the isListed.
	 */
	boolean getIsListed();

	/**
	 * @param isListed The isListed to set.
	 */
	void setIsListed(boolean isListed); 
	
	/**
	 * @return the DisplayOALULDCheckBox.
	 */
	String getDisplayOALULDCheckBox();
	/**
     * 
     * @param DisplayOALULDCheckBox 
     */
	void setDisplayOALULDCheckBox(String displayOALULDCheckBox);
	/**
	 * @return Returns the listStatus.
	 */
	String getListStatus();

	/**
	 * @param listStatus The listStatus to set.
	 */
	void setListStatus(String listStatus);
	
	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String,Collection<OneTimeVO>> getLocationValues();
	

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setLocationValues(HashMap<String,Collection<OneTimeVO>> oneLocationValues);
    
	/**
	 * 
	 * @param oneTimeValues
	 */
	 void setLevelTypeValues(Collection<OneTimeVO> oneTimeValues);
	
	 /**
	  * 
	  * @return Returns the onetime values.
	  */
	 Collection<OneTimeVO> getLevelTypeValues();
	 /**
		 * @return Returns the totalRecords.
		 */
	 
	 public Integer  getTotalRecords();
	 /**
		 * @param totalRecords The totalRecords to set.
		 */
	 public void setTotalRecords(int totalRecords);
    
}
