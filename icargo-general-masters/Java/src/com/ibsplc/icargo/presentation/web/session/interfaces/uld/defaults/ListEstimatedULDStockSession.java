/*
 * ListEstimatedULDStockSession.java Created on 
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

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2934
 *
 */
public interface ListEstimatedULDStockSession extends ScreenSession {
	    
    /**
     * 
     * @param EstimatedULDStockVO
     */
    void setEstimatedULDStockVO(EstimatedULDStockVO estimatedULDStockVO);
    /**
     * 
     * @param estimatedULDStockVOs
     */
    void setEstimatedULDStockVOs(Page<EstimatedULDStockVO> estimatedULDStockVOs);
    /**
     * 
     * @param estimatedULDStockVOColl
     */
    void setEstimatedULDStockVOColl(ArrayList<EstimatedULDStockVO> estimatedULDStockVOColl);
    /**
	 * @param estimatedULDStockFilterVO 
	 * The estimatedULDStockFilterVO to set.
	 */
    public void setEstimatedULDStockFilterVO (EstimatedULDStockFilterVO estimatedULDStockFilterVO);

    /**
	 * @return Returns the estimatedULDStockFilterVO.
	 */
    EstimatedULDStockFilterVO getEstimatedULDStockFilterVO();
	
	/**
	 * 
	 * @return EstimatedULDStockVO
	 */		
    EstimatedULDStockVO getEstimatedULDStockVO();
    /**
     * 
     * @return Page<EstimatedULDStockVO>
     */
    Page<EstimatedULDStockVO> getEstimatedULDStockVOs();
    /**
     * 
     * @return ArrayList<EstimatedULDStockVO> 
     */
    ArrayList<EstimatedULDStockVO> getEstimatedULDStockVOColl();
    
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
    
}
