/*
 * NavigateCustomerGroupCommand.java Created on May 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.maintaincustomergroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.CustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.CustomerGroupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected customergroups
 * 
 * @author A-2122
 */
public class NavigateCustomerGroupCommand extends BaseCommand {
	/**
	 * Logger for CustomerGroup
	 */
	private Log log = LogFactory.getLogger("MAINTAIN CUSTOMER GROUP");
		
	private static final String MODULE_NAME = "customermanagement.defaults";
	private static final String SCREEN_ID = 
								"customermanagement.defaults.customergroup";
	private static final String NAVIGATION_SUCCESS = "navigation_success";
    private static final String NAVIGATION_FAILURE = "navigation_failure";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.log(Log.FINE,"Navigate Command---Entry----------->");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		CustomerGroupForm customerGroupForm = 
			(CustomerGroupForm)invocationContext.screenModel; 
    	
    	CustomerGroupSession customerGroupSession = 
    		(CustomerGroupSession ) getScreenSession(MODULE_NAME, SCREEN_ID);
    	
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		HashMap<String,CustomerGroupVO> customerGroupVOs =
			customerGroupSession.getCustomerGroupVOMap();
		log.log(Log.FINE, "Session values--->", customerGroupVOs);
		int displayPage = Integer.parseInt(customerGroupForm.
    													getDisplayPage());
    	int currentPage = Integer.parseInt(customerGroupForm.
    														getCurrentPage());
    	
    	String[] groupCodes = customerGroupForm.getGroupCodesSelected().split(",");
    	
       	String key = groupCodes[currentPage-1];
       	CustomerGroupVO customerGroupVO =
       		customerGroupSession.getCustomerGroupVOMap().
    									get(key);
       	log.log(Log.FINE, "customerGroupVO-->inside navigate", customerGroupVO);
		log.log(Log.FINE, "key---->", key);
		SaveCustomerGroupCommand saveCustomerGroupCommand = new SaveCustomerGroupCommand();
    	errors = saveCustomerGroupCommand.validateForm(customerGroupForm,logonAttributes.getCompanyCode().toUpperCase());
       	customerGroupVO.setGroupCode(customerGroupForm.getCustomerGroupCode().toUpperCase());
       	            							
    	customerGroupVO.setGroupName
    					(customerGroupForm.getGroupName().toUpperCase());
        customerGroupVO.setStationCode
        		    (customerGroupForm.getStation().toUpperCase());
        //customerGroupVO.setLoyaltyGroupCode(customerGroupForm.getGroupCode().toUpperCase());    				
        customerGroupVO.setGroupRemarks
    							(customerGroupForm.getRemarks());
       
    	log.log(Log.FINE, "Length of Array------- inside NavigateCommand..",
				groupCodes.length);
		String keyVal = groupCodes[displayPage-1];
		log.log(Log.FINE, "keyValue---->", keyVal);
		if(customerGroupVOs.
    					get(keyVal)== null){
    		   		
			try {
				customerGroupVO = new CustomerMgmntDefaultsDelegate().
				listCustomerGroupDetails(logonAttributes.getCompanyCode(),
						groupCodes[displayPage-1]);
    			}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    			}
    			
    			customerGroupVOs.put(keyVal,customerGroupVO);
    		log.log(Log.FINE, "keyValue", keyVal);
			log.log(Log.FINE, "customerGroupVOs----inside navigate>",
					customerGroupVOs);
    	}
    	else {
    			customerGroupVO = customerGroupVOs.get(keyVal);
    			log.log(Log.FINE, "customerGroupVO", customerGroupVO);
				log.log(Log.FINE, "keyValue", keyVal);
    			
        	}
    	if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = NAVIGATION_FAILURE;
				return;
		}
    	
    	customerGroupSession.setCustomerGroupVO(customerGroupVO);
    	customerGroupForm.setCurrentPage(Integer.toString(displayPage));
    	invocationContext.target = NAVIGATION_SUCCESS;
    	
		}
					
		
}
