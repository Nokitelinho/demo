/*
 * ListCustomerGroupSession.java Created on MAy 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup;


import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2122
 *
 */
public interface ListCustomerGroupSession extends ScreenSession {
	    
    
    
    /**
     * 
     * @param customerGroupVO
     */
    public void setCustomerGroupVO(Page<CustomerGroupVO> 
    												customerGroupVO);
    
    /**
	 * @param customerGroupFilterVO 
	 * The customerGroupFilterVO to set.
	 */
    public void setCustomerGroupFilterVO
    			(CustomerGroupFilterVO customerGroupFilterVO);

    
    
    
    /**
	 * @return Returns the CustomerGroupFilterVO.
	 */
    public CustomerGroupFilterVO getCustomerGroupFilterVO();
	
	
    /**
     * 
     * @return Page<CustomerGroupVO>
     */
    public Page<CustomerGroupVO> getCustomerGroupVO();
   
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
	 * Methods for getting CustomerGroupVOs
	 */
	public Collection<CustomerGroupLoyaltyProgrammeVO> getCustomerGroupLoyaltyProgrammeVOs();
	
	/**
	 * Methods for setting CustomerGroupVOs
	 */
	public void setCustomerGroupLoyaltyProgrammeVOs(Collection<CustomerGroupLoyaltyProgrammeVO> customerGroupVOs);
	
	/**
	 * Methods for removing CustomerGroupVOs
	 */
	public void removeCustomerGroupLoyaltyProgrammeVOs();
	
    
    /* **** added by kiran **** */
    /**
     * Methods for storring absoluteIndexMap
     */
    public HashMap<String,String> getIndexMap();
    
    public void setIndexMap(HashMap<String,String> indexMap);
    
    // Added by A-5183 for < ICRD-22065 > Starts
	 
 	/**
	 * @return Returns the totalRecords.
	 */
 
 	public Integer getTotalRecordsCount();
 
 	/**
	 * @param totalRecords The totalRecords to set.
	 */
 	
 	public void setTotalRecordsCount(int totalRecordsCount);
 	
 	
 	public void removeTotalRecordsCount();
	
 	// Added by A-5183 for < ICRD-22065 > Ends
    
    
}
