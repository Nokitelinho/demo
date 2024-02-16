/*
 * ListCustomerGroupSessionImpl.java Created on May 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.customergroup;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.ListCustomerGroupSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2122
 *
 */
public class ListCustomerGroupSessionImpl extends AbstractScreenSession
		implements ListCustomerGroupSession {

	private static final String MODULE = "customermanagement.defaults";
	private static final String SCREENID = 
						"customermanagement.defaults.listcustomergroup";
	private static final String KEY_LISTFILTER = 
								"customerGroupFilterVO";
	private static final String KEY_CUSTOMERGROUPLIST = 
									"customerGroupVO";
		private static final String  LISTSTATUS = "listStatus";	
		private static final String KEY_CUSTOMERGROUPLOYALTYPGMVOS = "customerGroupLoyaltyVOs";
		private static final String TOTAL_RECORDS_COUNT = "totalRecordsCount";
        
    /* attributes added for storring indexmap */
        private static final String KEY_ABSOLUTE_INDEXMAP = "indexMap";
        
	
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
	 * @return Returns the CustomerGroupFilterVO.
	 */
	public CustomerGroupFilterVO getCustomerGroupFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}

	/**
	 * @return Page<CustomerGroupVO>
	 */
    public Page<CustomerGroupVO> getCustomerGroupVO() {
		return (Page<CustomerGroupVO>) 
									getAttribute(KEY_CUSTOMERGROUPLIST);
	}
    
    
    
    /**
	 * @param customerGroupFilterVO The
	 *  customerGroupFilterVO to set.
	 */
	public void setCustomerGroupFilterVO(CustomerGroupFilterVO customerGroupFilterVO) {
		setAttribute(KEY_LISTFILTER,customerGroupFilterVO);
	}
	
	/**
	 * @param customerGroupVO
	 */
    public void setCustomerGroupVO(Page<CustomerGroupVO> 
											customerGroupVO) {
				   setAttribute(KEY_CUSTOMERGROUPLIST,
		           (Page<CustomerGroupVO>) customerGroupVO);
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
 * @return  Collection<CustomerGroupLoyaltyProgrammeVO>
 */
	public Collection<CustomerGroupLoyaltyProgrammeVO> getCustomerGroupLoyaltyProgrammeVOs(){
		return (Collection<CustomerGroupLoyaltyProgrammeVO>) getAttribute(KEY_CUSTOMERGROUPLOYALTYPGMVOS);
	}
/**
 * @param customerGroupVOs
 * 
 */
	public void setCustomerGroupLoyaltyProgrammeVOs(Collection<CustomerGroupLoyaltyProgrammeVO> customerGroupVOs){
		setAttribute(KEY_CUSTOMERGROUPLOYALTYPGMVOS,(ArrayList<CustomerGroupLoyaltyProgrammeVO>) customerGroupVOs);
	}
	/**
	 * Methods for removing CustomerGroupVOs
	 */
	public void removeCustomerGroupLoyaltyProgrammeVOs(){
		removeAttribute(KEY_CUSTOMERGROUPLOYALTYPGMVOS);
	}

   /**
    * 
    */   
    public HashMap<String, String> getIndexMap() {
        // To be reviewed Auto-generated method stub
        return getAttribute(KEY_ABSOLUTE_INDEXMAP);
    }
/**
 * @param indexMap
 */
    public void setIndexMap(HashMap<String, String> indexMap) {
        // To be reviewed Auto-generated method stub
        setAttribute(KEY_ABSOLUTE_INDEXMAP,indexMap);
    }
    /* **** added by kiran ends ****** */
        
    // Added by A-5183 for < ICRD-22065 > Starts 

		
	public Integer getTotalRecordsCount() {
		
		return getAttribute(TOTAL_RECORDS_COUNT);
	}
	
	public void setTotalRecordsCount(int totalRecordsCount) {
		
		setAttribute(TOTAL_RECORDS_COUNT, totalRecordsCount);
				
	}	
	
	public void removeTotalRecordsCount(){
		removeAttribute(TOTAL_RECORDS_COUNT);
	}
	
	// Added by A-5183 for < ICRD-22065 > Ends
    
       
}
