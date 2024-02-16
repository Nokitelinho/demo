/*
 * CustomerGroupSession.java Created on May 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Service(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup;


import java.util.HashMap;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2122
 *
 */
public interface  CustomerGroupSession extends ScreenSession {
	    
    
    /**
     * 
     * @param CustomerGroupVO
     */
   public void setCustomerGroupVO(CustomerGroupVO 
    		customerGroupVO);
	
	/**
	 * 
	 * @return CustomerGroupVO
	 */		
   public CustomerGroupVO getCustomerGroupVO();
   
   /**
    * @return Returns the customerGroupVOMap
    */
   public HashMap<String,CustomerGroupVO> 
   										getCustomerGroupVOMap();
   
   /**
    * 
    * @param customerGroupVOMap The customerGroupVOMap to set
    */
   
   public void setCustomerGroupVOMap(
		HashMap<String,CustomerGroupVO> customerGroupVOMap);
   
    
    /**
     * 
     *
     */
   public void removeCustomerGroupVO();
    
}
