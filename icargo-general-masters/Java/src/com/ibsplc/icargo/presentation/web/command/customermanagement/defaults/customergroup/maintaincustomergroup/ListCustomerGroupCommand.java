/*
 * ListCustomerGroupCommand.java Created on May 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.maintaincustomergroup;
import java.util.Collection;
import java.util.ArrayList;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.CustomerGroupSession;

import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.CustomerGroupForm;

/**
 * This command class is used to list the customer 
 * details of the specified group  
 * @author A-2122
 */
public class ListCustomerGroupCommand extends BaseCommand {
    
	private Log log = LogFactory.getLogger("MAINTAIN CUSTOMER GROUP");
	private static final String MODULE_NAME = "customermanagement.defaults";
	private static final String SCREEN_ID = 
							"customermanagement.defaults.customergroup";
	    private static final String LIST_SUCCESS = "list_success";
    private static final String LIST_FAILURE = "list_failure";
    
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListCustomerGroupCommand", "execute");
    	
    	CustomerGroupForm customerGroupForm = 
			(CustomerGroupForm)invocationContext.screenModel; 
    	
    	CustomerGroupSession customerGroupSession = 
    		(CustomerGroupSession ) getScreenSession(MODULE_NAME, SCREEN_ID);
    	    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
             	
		
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO error = null;
		
    	
    	CustomerGroupVO customerGroupVO = new CustomerGroupVO();
    	
    	
    	CustomerMgmntDefaultsDelegate customerDelegate = new CustomerMgmntDefaultsDelegate();
    	    	 
    	String groupCode=customerGroupForm.getCustomerGroupCode().toUpperCase();
    		    //customerGroupForm.getGroupCode().toUpperCase();
    	String companyCode=logonAttributes.getCompanyCode().toUpperCase();
    	
    	
    	errors=validateForm(customerGroupForm);
    	  	
    	if (errors != null && errors.size() > 0) {	

       	 log.log(Log.INFO, "invocationCtxt.target:", invocationContext.target);
       
   		}else{
	    	try{
	    		customerGroupVO = 
	    		  customerDelegate.listCustomerGroupDetails(
	    				  companyCode,groupCode);
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    	}
	    	if(customerGroupVO == null){
	    			
	    			log.log(Log.FINE, "vo null...customerGroupVO-->",
							customerGroupVO);
					error = new ErrorVO
	    					  ("customermanagement.defaults.customergroup.msg.err.nosuchrecordexists");
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);
			
			}
   		}
		if (errors != null && errors.size() > 0) {	
			CustomerGroupVO customerGroupvo = new CustomerGroupVO();
			customerGroupvo.setGroupCode(groupCode);
			customerGroupSession.setCustomerGroupVO(customerGroupvo);
			invocationContext.addAllError(errors);
          	 invocationContext.target = LIST_FAILURE;
          	 
      	}else{
      		customerGroupSession.setCustomerGroupVO(customerGroupVO);
    		//maintainAccessoriesStockForm.setModeFlag("Y");
    		//maintainAccessoriesStockForm.setLovFlag("Y");
//      		customerGroupForm.setScreenStatusFlag
//						(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
      		customerGroupForm.setStatusFlag("U");
      		invocationContext.target = LIST_SUCCESS; 
	    }    
	}
	  private Collection<ErrorVO> validateForm
	   				(CustomerGroupForm customerGroupForm){
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			log.entering("ListCustomerGroupCommand","validateForm");
			ErrorVO error = null;
		//if(customerGroupForm.getGroupCode()==null ||
			//	customerGroupForm.getGroupCode().trim().length()==0){
			if(customerGroupForm.getCustomerGroupCode() == null ||
					customerGroupForm.getCustomerGroupCode().trim().length()==0){
				error = new ErrorVO
				  ("customermanagement.defaults.customergroup.msg.err.groupcode.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
						
			log.exiting("ListCustomerGroupCommand","validateForm");
			customerGroupForm.setScreenStatusFlag
			(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			return errors;
		}    
	   
	    }


