/*
 * DetailsListCustomerGroupCommand.java Created on May 15, 2006
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
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
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
 * selected CustomerGroup
 * 
 * @author A-2122
 */
public class DetailsListCustomerGroupCommand extends BaseCommand {
	/**
	 * Logger for CustomerGroup
	 */
	private Log log = LogFactory.getLogger("MAINTAIN CUSTOMER GROUP");
		
	private static final String MODULE_NAME = "customermanagement.defaults";
	private static final String SCREEN_ID = 
								"customermanagement.defaults.customergroup";
	 private static final String DETAILS_SUCCESS = "details_success";
	  private static final String DETAILS_FAILURE = "details_failure";
    
        
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		CustomerGroupForm customerGroupForm = 
			(CustomerGroupForm)invocationContext.screenModel; 
    	
    	CustomerGroupSession customerGroupSession = 
    		(CustomerGroupSession ) getScreenSession(MODULE_NAME, SCREEN_ID);
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering("DetailsListCustomerGroupCommand","execute");
		
		CustomerGroupVO customerGroupVO = null;
						
		HashMap<String,CustomerGroupVO> customerGroupVOs =
				new HashMap<String,CustomerGroupVO>();
		String[] groupCodes = customerGroupForm.getGroupCodesSelected().split(",");
		String companyCode=logonAttributes.getCompanyCode().toUpperCase();
			
			log.log(Log.FINE, "accessoryCode--->", groupCodes);
			try {
				
				customerGroupVO = new CustomerMgmntDefaultsDelegate().listCustomerGroupDetails(
		    				  companyCode,groupCodes[0]);
	    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
	    		customerGroupSession.setCustomerGroupVO(customerGroupVO);
    					
    		log.log(Log.FINE, "VO in Details Command-------------->",
					customerGroupVO);
			String key = groupCodes[0];
    		customerGroupVOs.put(key,customerGroupVO);
    		customerGroupSession.setCustomerGroupVOMap(customerGroupVOs);
    		log.log(Log.FINE, "vos inside  session in Details Command-->",
					customerGroupVOs);
			customerGroupForm.setDisplayPage("1");
    		customerGroupForm.setCurrentPage("1");
    		customerGroupForm.
    				setLastPageNum(Integer.toString(groupCodes.length));
    		customerGroupForm.
    				setTotalRecords(Integer.toString(groupCodes.length));
			//maintainAccessoriesStockForm.setScreenStatusFlag
			                //(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		customerGroupForm.setStatusFlag("U");
    		customerGroupForm.setDetailsFlag("From List");
			//maintainAccessoriesStockForm.setModeFlag("Y");
			//maintainAccessoriesStockForm.setLovFlag("Y");
			invocationContext.target = DETAILS_SUCCESS;
		
			
			
			
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = DETAILS_FAILURE;
		}
    }
    	   
    }



