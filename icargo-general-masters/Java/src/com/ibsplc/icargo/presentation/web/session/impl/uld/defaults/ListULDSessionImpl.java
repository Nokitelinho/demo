/*
 * ListULDSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1347
 *
 */
public class ListULDSessionImpl extends AbstractScreenSession
		implements ListULDSession {

	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	
	private static final String KEY_LEVELTYPEVALUES = "levelTypeValues";
	
	private static final String KEY_LOCATIONVALUES = "locationvalues";
	
	private static final String LISTPAGE = "listDisplayPage";
	
	private static final String LISTFILTER = "listFilterVO";
	
	private static final String  ISLISTED = "isListed";
	
	private static final String  LISTSTATUS = "listStatus";
	
	private static final String  TOTALRECORDS = "totalRecords";
	
	private static final String MODULE = "uld.defaults";
	
	private static final String DISPLAYOALULDCHECKBOX = "displayOALULDCheckBox";
		
	private static final String SCREENID =
		"uld.defaults.listuld";
	
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
    * @return Returns the ULDListVO.
    */
    public Page<ULDListVO> getListDisplayPage() {
        return getAttribute(LISTPAGE);
    }
    /**
     * @param uldListVOs The uldListVOs to set.
     */
    public void setListDisplayPage(Page<ULDListVO> uldListVOs) {
    	setAttribute(LISTPAGE,uldListVOs);
    }

	/**
	 * @return Returns the listFilterVO.
	 */
	public ULDListFilterVO getListFilterVO() {
		return getAttribute(LISTFILTER);
	}

	/**
	 * @param listFilterVO The listFilterVO to set.
	 */
	public void setListFilterVO(ULDListFilterVO listFilterVO) {
		setAttribute(LISTFILTER,listFilterVO);
	}

	/**
	 * @return Returns the isListed.
	 */
	public boolean getIsListed() {
		return ((Boolean)getAttribute(ISLISTED)).booleanValue();
	}

	/**
	 * @param isListed The isListed to set.
	 */
	public void setIsListed(boolean isListed) {
		setAttribute(ISLISTED,isListed);
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
	public String getDisplayOALULDCheckBox() {
		return getAttribute(DISPLAYOALULDCHECKBOX);
	}
	public void setDisplayOALULDCheckBox(String displayOALULDCheckBox){
		setAttribute(DISPLAYOALULDCHECKBOX, displayOALULDCheckBox);
	}
	

	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String,Collection<OneTimeVO>> getLocationValues() {
		return getAttribute(KEY_LOCATIONVALUES);
	}

	
	/**
	 * @param oneLocationValues
	 */
	public void setLocationValues(HashMap<String,Collection<OneTimeVO>> oneLocationValues) {
		setAttribute(KEY_LOCATIONVALUES,oneLocationValues);
	}
	/**
	 * @return Returns the oneTimeValues.
	 */
	public Collection<OneTimeVO> getLevelTypeValues() {
		return (Collection<OneTimeVO>)getAttribute(KEY_LEVELTYPEVALUES);
	}

	
	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setLevelTypeValues(Collection<OneTimeVO> oneTimeValues) {
		setAttribute(KEY_LEVELTYPEVALUES,(ArrayList<OneTimeVO>)oneTimeValues);
	}
	
	/**
	 * @return Returns the totalRecords.
	 */
	public Integer getTotalRecords() {
		return getAttribute(TOTALRECORDS);
	}
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTALRECORDS,totalRecords);
	}
    
}
