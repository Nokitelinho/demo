/*
 * MaintainAccessoriesStockSession.java Created on Jan 22, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Service(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1347
 *
 */
public interface MaintainAccessoriesStockSession extends ScreenSession {
	    
    
    /**
     * 
     * @param AccessoriesStockConfigVO
     */
    void setAccessoriesStockConfigVO(AccessoriesStockConfigVO 
    										accessoriesStockConfigVO);
	
	/**
	 * 
	 * @return AccessoriesStockConfigVO
	 */		
    AccessoriesStockConfigVO getAccessoriesStockConfigVO();
   
    /**
     * @return Returns the accessoriesStockConfigVOMap
     */
    public HashMap<String,AccessoriesStockConfigVO> 
    										getAccessoriesStockConfigVOMap();
    
    /**
     * 
     * @param accessoriesStockConfigVOMap The accessoriesStockConfigVOMap to set
     */
    
    public void setAccessoriesStockConfigVOMap(
		HashMap<String,AccessoriesStockConfigVO> accessoriesStockConfigVOMap );
    
	 
	 /**
	 * @return Returns the oneTimeValues.
	 */
		HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
    
    /**
     * 
     *
     */
    void removeAccessoriesStockConfigVO();
    
}
