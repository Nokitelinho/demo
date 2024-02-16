/*
 * MonitorULDStockSession.java Created on Aug 1, 2005
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
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1347
 *
 */
public interface MonitorULDStockSession extends ScreenSession {
	    
    

	/**
	 * 
	 * @return
	 */
	 public Page<ULDStockListVO> getULDStockListVO();
    /**
     * 
     * @param uLDStockListVO
     */
     public void setULDStockListVO(Page<ULDStockListVO> uLDStockListVO);
     /**
      * 
      * @return
      */
    public ULDStockConfigFilterVO getULDStockConfigFilterVO();
   /**
    * 
    * @param uLDStockConfigFilterVO
    */
    public void setULDStockConfigFilterVO(ULDStockConfigFilterVO uLDStockConfigFilterVO);
	
	/**
	 * @return Returns the listStatus.
	 */
	public String getListStatus();
	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setListStatus(String listStatus);
	
	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues
	 *            The oneTimeValues to set.
	 */
	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues);
 
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
    
	// Added by A-3045 for CR QF1012 starts
    /**
	 * @return Returns the levelType.
	 */
    public Collection<OneTimeVO> getLevelType();
    /**
	 * @param levelType The levelType to set.
	 */
	public void setLevelType(Collection<OneTimeVO> levelType);
	// Added by A-3045 for CR QF1012 ends
	/**
	 * @param setSort The sort to set.
	 */
	public void setSort(String sort);
	public  Integer getTotalRecordsCount();
	public  void setTotalRecordsCount(int paramInt);
	public  void removeTotalRecordsCount();
}
