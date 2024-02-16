/*
 * ListAccessriesStockSessionImpl.java Created on Oct 10, 2005
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
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListAccessoriesStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1347
 *
 */
public class ListAccessriesStockSessionImpl extends AbstractScreenSession
		implements ListAccessoriesStockSession {

	private static final String MODULE = "uld.defaults";
	private static final String SCREENID = 
						"uld.defaults.stock.listaccessoriesstock";
	private static final String KEY_LISTFILTER = 
								"accessoriesStockConfigFilterVO";
	private static final String KEY_ACCESSORYSTOCKLIST = 
									"accessoriesStockConfigVOs";
	private static final String KEY_ACCESSORYSTOCKCOLL = 
									"accessoriesStockConfigVOColl";
	private static final String  LISTSTATUS = "listStatus";	
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	private static final String TOTAL_RECORDS_COUNT = "totalRecordsCount";
	
	// Added by A-5183 for < ICRD-22824 > Starts 		
	public Integer getTotalRecordsCount() {
		
		return getAttribute(TOTAL_RECORDS_COUNT);
	}	
	public void setTotalRecordsCount(int totalRecordsCount) {
		
		setAttribute(TOTAL_RECORDS_COUNT, totalRecordsCount);
				
	}
	public void removeTotalRecordsCount(){
		removeAttribute(TOTAL_RECORDS_COUNT);
	}	
	// Added by A-5183 for < ICRD-22824 > Ends
	
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
     *defaults.ListAccessoriesStockConfigSession#getAccessoriesStockConfigVO()
     * @return AccessoriesStockConfigVO
     */
    public AccessoriesStockConfigVO getAccessoriesStockConfigVO() {
    	return null;
    }
    /**
	 * @return Returns the accessoriesStockConfigFilterVO.
	 */
	public AccessoriesStockConfigFilterVO getAccessoriesStockConfigFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}

	/**
	 * @return
	 */
    public Page<AccessoriesStockConfigVO> getAccessoriesStockConfigVOs() {
		return (Page<AccessoriesStockConfigVO>) 
									getAttribute(KEY_ACCESSORYSTOCKLIST);
	}
    /**
     * @return ArrayList<AccessoriesStockConfigVO>
     */
    public ArrayList<AccessoriesStockConfigVO> 
    										getAccessoriesStockConfigVOColl() {
		return (ArrayList<AccessoriesStockConfigVO>) 
								getAttribute(KEY_ACCESSORYSTOCKCOLL);
	}
    /** (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.
     * ListAccessoriesStockConfigSession#setAccessoriesStockConfigVO
     *(com.ibsplc.icargo.business.uld.defaults.stock.vo.
     *AccessoriesStockConfigVO)
     *@param accessoriesStockConfigVO
     */
    public void setAccessoriesStockConfigVO(
            AccessoriesStockConfigVO accessoriesStockConfigVO) {
    }
    
  /**
   * @param accessoriesStockConfigFilterVO
   */
	public void setAccessoriesStockConfigFilterVO(AccessoriesStockConfigFilterVO
						accessoriesStockConfigFilterVO) {
		setAttribute(KEY_LISTFILTER,accessoriesStockConfigFilterVO);
	}
	
	/**
	 * @param accessoriesStockConfigVOs
	 */
    public void setAccessoriesStockConfigVOs(Page<AccessoriesStockConfigVO> 
    				accessoriesStockConfigVOs) {
				   setAttribute(KEY_ACCESSORYSTOCKLIST,
		           (Page<AccessoriesStockConfigVO>) accessoriesStockConfigVOs);
	}
    /**
     * @param accessoriesStockConfigVOColl
     */
    public void setAccessoriesStockConfigVOColl(
    		ArrayList<AccessoriesStockConfigVO> accessoriesStockConfigVOColl) {
    	setAttribute(KEY_ACCESSORYSTOCKCOLL,
    		(ArrayList<AccessoriesStockConfigVO>) accessoriesStockConfigVOColl);
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
