/*
 * MonitorULDStockSessionImpl.java.java Created on Oct 10, 2005
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
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MonitorULDStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1496
 *
 */
public class MonitorULDStockSessionImpl extends AbstractScreenSession
		implements MonitorULDStockSession {

	private static final String MODULE = "uld.defaults";
	
	private static final String KEY_STOCKLISTVO = "uLDStockListVO";
	
	private static final String KEY_STOCKFILTERVO = "uLDStockConfigFilterVO";
	
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	
	private static final String KEY_LISTSTATUS = "listStatus";
	
	private static final String KEY_INDEXMAP = "indexMap";
	// Added by A-3045 for CR QF1012 starts
	private static final String KEY_LEVELTYPE = "leveltype";
	// Added by A-3045 for CR QF1012 ends
	private static final String KEY_SORT = "sort";
	
	private static final String SCREENID =
		"uld.defaults.monitoruldstock";

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
	 * @return
	 */
	
	 public Page<ULDStockListVO> getULDStockListVO(){
		 return getAttribute(KEY_STOCKLISTVO);
	 }
	 /**
	     * @param uLDStockListVO
	     */
      public void setULDStockListVO(Page<ULDStockListVO> uLDStockListVO){
    	  setAttribute(KEY_STOCKLISTVO,uLDStockListVO);
      }
      /**
  	 * @return
  	 */
     public ULDStockConfigFilterVO getULDStockConfigFilterVO(){
    	  return getAttribute(KEY_STOCKFILTERVO);
	 }
    /**
     * @param uLDStockConfigFilterVO
     */
     public void setULDStockConfigFilterVO(ULDStockConfigFilterVO uLDStockConfigFilterVO){
    	 setAttribute(KEY_STOCKFILTERVO,uLDStockConfigFilterVO);
    	   
     }
	
     /**
 	 * @return
 	 */
	public String getListStatus() {
		return getAttribute(KEY_LISTSTATUS);
	}

	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setListStatus(String listStatus) {
		setAttribute(KEY_LISTSTATUS,listStatus);
	} 
	
	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues
	 *            The oneTimeValues to set.
	 */
	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
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
	
	// Added by A-3045 for CR QF1012 starts	
	public Collection<OneTimeVO> getLevelType() {
		return (Collection<OneTimeVO>)getAttribute(KEY_LEVELTYPE);
	}
	public void setLevelType(Collection<OneTimeVO> levelType) {
		setAttribute(KEY_LEVELTYPE,(ArrayList<OneTimeVO>)levelType);
	}
	// Added by A-3045 for CR QF1012 ends
    /**
 	 * @return
 	 */
	public String getSort() {
		return getAttribute(KEY_SORT);
	}

	public void setSort(String sort) {
		// To be reviewed Auto-generated method stub
		setAttribute(KEY_SORT,sort);
	}
	public Integer getTotalRecordsCount() {
		    return (Integer)getAttribute("totalRecordsCount");
		  }
	public void setTotalRecordsCount(int totalRecordsCount) {
		    setAttribute("totalRecordsCount", Integer.valueOf(totalRecordsCount));
		  }
	public void removeTotalRecordsCount() {
		    removeAttribute("totalRecordsCount");
		  }
}
