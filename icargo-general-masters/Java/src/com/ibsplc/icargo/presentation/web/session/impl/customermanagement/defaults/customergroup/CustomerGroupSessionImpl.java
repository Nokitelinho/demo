/*
 * CustomerGroupSessionImpl.java Created on May 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.customergroup;


import java.util.HashMap;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.CustomerGroupSession;


/**
 * @author A-2122
 *
 */
public class CustomerGroupSessionImpl extends AbstractScreenSession
		implements CustomerGroupSession {

	private static final String MODULE = "customermanagement.defaults";

	private static final String SCREENID =
		"customermanagement.defaults.customergroup";
	private static final String KEY_CUSTOMERGRPDETAILS="customerGroupVO";
	private static final String KEY_CUSTOMERMAP =
						"customerGroupVOMap";
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
	 *  @return Returns the CustomerGroupVO.
	 */
   
    public CustomerGroupVO getCustomerGroupVO() {
    	return (CustomerGroupVO)getAttribute(KEY_CUSTOMERGRPDETAILS);
    }
    
    /**
     *  @param customerGroupVO
     */
    public void setCustomerGroupVO(CustomerGroupVO 
    		customerGroupVO) {
    	setAttribute(KEY_CUSTOMERGRPDETAILS, customerGroupVO);
    	
    }
    
    
    
    /**
	 * @return Returns the customerGroupVOMap.
	 */
    public HashMap<String,CustomerGroupVO> 	getCustomerGroupVOMap() {
		return getAttribute(KEY_CUSTOMERMAP);
	}
	/**
	 * @param customerGroupVOMap The customerGroupVOMap to set
	 */
	
    public void setCustomerGroupVOMap(
    		HashMap<String,CustomerGroupVO> customerGroupVOMap ){
		 setAttribute(KEY_CUSTOMERMAP,customerGroupVOMap);
	}
	
    	

   /**
    * 
    */ 

    public void removeCustomerGroupVO() {
    	removeAttribute(KEY_CUSTOMERGRPDETAILS);
    }

}

