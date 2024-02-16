/*
 * MaintainAccessoriesStockSessionImpl.java Created on Jan22, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults;



import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainAccessoriesStockSession;

/**
 * @author A-1347
 *
 */
public class MaintainAccessoriesStockSessionImpl extends AbstractScreenSession
		implements MaintainAccessoriesStockSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.maintainaccessoriesstock";
	private static final String KEY_ACCESSORYDETAILS="accessoriesStockConfigVO";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	private static final String KEY_ACCESSORYMAP =
										"accessoriesStockConfigVOMap";
	
	
	/**
	 * @return Returns the accessoriesStockConfigVOMap.
	 */
	public HashMap<String,AccessoriesStockConfigVO> 
								getAccessoriesStockConfigVOMap() {
		return getAttribute(KEY_ACCESSORYMAP);
	}
	/**
	 * @param accessoriesStockConfigVOMap The accessoriesStockConfigVOMap to set
	 */
	
	public void setAccessoriesStockConfigVOMap(
		HashMap<String,AccessoriesStockConfigVO> accessoriesStockConfigVOMap ){
		 setAttribute(KEY_ACCESSORYMAP,accessoriesStockConfigVOMap);
	}
	
	/**
	 *
	 * */
	/** Method to get ScreenID
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
     *  (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.
     * MaintainAccessoriesStockConfigSession#getAccessoriesStockConfigVO()
     * @return AccessoriesStockConfigVO
     */
    public AccessoriesStockConfigVO getAccessoriesStockConfigVO() {
        return (AccessoriesStockConfigVO)getAttribute(KEY_ACCESSORYDETAILS);
    }
    /** (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.
     * MaintainAccessoriesStockConfigSession#setAccessoriesStockConfigVO
     *(com.ibsplc.icargo.business.uld.defaults.stock.vo.
     * AccessoriesStockConfigVO)
     *@param accessoriesStockConfigVO
     */
    public void setAccessoriesStockConfigVO(
            AccessoriesStockConfigVO accessoriesStockConfigVO) {
    	setAttribute(KEY_ACCESSORYDETAILS, accessoriesStockConfigVO);
    	
    }
    /**
     * 
     * @return HashMap<String,Collection<OneTimeVO>>
     */
    
    public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>>
									oneTimeValues) {
					setAttribute(KEY_ONETIMEVALUES,oneTimeValues);
	}
		

   /**
    * 
    */ 

    public void removeAccessoriesStockConfigVO() {
    	removeAttribute(KEY_ACCESSORYDETAILS);
    }

}

