/*
 * ListEstimatedULDStockSessionImpl.java Created on Oct 22, 2012
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

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListEstimatedULDStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2934
 *
 */
public class ListEstimatedULDStockSessionImpl extends AbstractScreenSession
		implements ListEstimatedULDStockSession {

	private static final String MODULE = "uld.defaults";
	private static final String SCREENID = 
						"uld.defaults.stock.listestimateduldstock";
	private static final String KEY_LISTFILTER = 
								"estimatedULDStockFilterVO";
	private static final String KEY_ESTIMATEDULDSTOCKLIST = 
									"estimatedULDStockVOs";
	private static final String KEY_ESTIMATEDULDSTOCKCOLL = 
									"estimatedULDStockVOColl";
	private static final String  LISTSTATUS = "listStatus";	
	private static final String KEY_INDEXMAP = "indexMap";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
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
     *defaults.ListEstimatedULDStockSession#getEstimatedULDStockVO()
     * @return EstimatedULDStockVO
     */
    public EstimatedULDStockVO getEstimatedULDStockVO() {
    	return null;
    }
    /**
	 * @return Returns the estimatedULDStockFilterVO.
	 */
	public EstimatedULDStockFilterVO getEstimatedULDStockFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}

	/**
	 * @return
	 */
    public Page<EstimatedULDStockVO> getEstimatedULDStockVOs() {
		return (Page<EstimatedULDStockVO>)getAttribute(KEY_ESTIMATEDULDSTOCKLIST);
	}
    /**
     * @return ArrayList<EstimatedULDStockVO>
     */
    public ArrayList<EstimatedULDStockVO> 
    										getEstimatedULDStockVOColl() {
		return (ArrayList<EstimatedULDStockVO>)getAttribute(KEY_ESTIMATEDULDSTOCKCOLL);
	}
    /** (non-Javadoc)
     *@param estimatedULDStockVO
     */
    public void setEstimatedULDStockVO(
            EstimatedULDStockVO estimatedULDStockVO) {
    }
    
  /**
   * @param estimatedULDStockFilterVO
   */
	public void setEstimatedULDStockFilterVO(EstimatedULDStockFilterVO
						estimatedULDStockFilterVO) {
		setAttribute(KEY_LISTFILTER,estimatedULDStockFilterVO);
	}
	
	/**
	 * @param estimatedULDStockVOs
	 */
    public void setEstimatedULDStockVOs(Page<EstimatedULDStockVO> 
    				estimatedULDStockVOs) {
				   setAttribute(KEY_ESTIMATEDULDSTOCKLIST,
		           (Page<EstimatedULDStockVO>) estimatedULDStockVOs);
	}
    /**
     * @param estimatedULDStockVOColl
     */
    public void setEstimatedULDStockVOColl(
    		ArrayList<EstimatedULDStockVO> estimatedULDStockVOColl) {
    	setAttribute(KEY_ESTIMATEDULDSTOCKCOLL,
    		(ArrayList<EstimatedULDStockVO>) estimatedULDStockVOColl);
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
   
}
