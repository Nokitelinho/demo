/*
 * ListAccessoriesStockSession.java Created on Aug 1, 2005
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
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1347
 *
 */
public interface ListAccessoriesStockSession extends ScreenSession {
	    
    
    /**
     * 
     * @param AccessoriesStockConfigVO
     */
    void setAccessoriesStockConfigVO(AccessoriesStockConfigVO 
    									accessoriesStockConfigVO);
    /**
     * 
     * @param accessoriesStockConfigVOs
     */
    void setAccessoriesStockConfigVOs(Page<AccessoriesStockConfigVO> 
    						accessoriesStockConfigVOs);
    /**
     * 
     * @param accessoriesStockConfigVOColl
     */
    void setAccessoriesStockConfigVOColl(ArrayList<AccessoriesStockConfigVO> 
    								accessoriesStockConfigVOColl);
    /**
	 * @param accessoriesStockConfigFilterVO 
	 * The accessoriesStockConfigFilterVO to set.
	 */
    public void setAccessoriesStockConfigFilterVO
    			(AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO);

    
    
    
    /**
	 * @return Returns the accessoriesStockConfigFilterVO.
	 */
    AccessoriesStockConfigFilterVO getAccessoriesStockConfigFilterVO();
	
	/**
	 * 
	 * @return AccessoriesStockConfigVO
	 */		
    AccessoriesStockConfigVO getAccessoriesStockConfigVO();
    /**
     * 
     * @return Page<AccessoriesStockConfigVO>
     */
    Page<AccessoriesStockConfigVO> getAccessoriesStockConfigVOs();
    /**
     * 
     * @return ArrayList<AccessoriesStockConfigVO> 
     */
    ArrayList<AccessoriesStockConfigVO> getAccessoriesStockConfigVOColl();
    
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
	 
	// Added by A-5183 for < ICRD-22824  > Starts	 
	 	/**
		 * @return Returns the totalRecords.
		 */
	 
	 	public Integer getTotalRecordsCount();	 
	 	/**
		 * @param totalRecords The totalRecords to set.
		 */	 	
	 	public void setTotalRecordsCount(int totalRecordsCount); 
	 	public void removeTotalRecordsCount();		
	 	// Added by A-5183 for < ICRD-22824 > Ends
	    
}
