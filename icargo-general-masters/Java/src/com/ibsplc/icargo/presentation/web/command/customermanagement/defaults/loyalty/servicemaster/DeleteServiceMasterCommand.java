/*
 * DeleteServiceMasterCommand.java  Created on May 8, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.servicemaster;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.CustomerServicesVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ServiceMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ServiceMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is invoked on the start up of the 
 *  List Customer Group screen
 * 
 * @author A-2052
 */
public class DeleteServiceMasterCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SaveServiceMasterCommand");	
	private static final String MODULE_NAME = "customermanagement.defaults";
	private static final String SCREEN_ID ="customermanagement.defaults.servicemaster";
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String DELETE_FAILURE = "delete_failure";
       
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("SaveServiceMasterCommand","execute");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        CustomerMgmntDefaultsDelegate customerDelegate = new CustomerMgmntDefaultsDelegate();
        Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ServiceMasterForm form = 
			(ServiceMasterForm)invocationContext.screenModel;    	 
    	ServiceMasterSession session = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
    	CustomerServicesVO vo = new CustomerServicesVO();    	
    	vo = getFormDetails(form,logonAttributes);
    	log.log(Log.FINE, "vo before setting to delete----------->>>>>", vo);
		try{
    		customerDelegate.saveCustomerServices(vo);
    	}catch(BusinessDelegateException e){
    		errors = handleDelegateException(e);
    	}
    	if(errors!=null && errors.size() > 0) {   
	   		invocationContext.addAllError(errors);
	   		invocationContext.target = DELETE_FAILURE;
	   		return;
    	}
    	log.log(Log.FINE,"setting all values to null----->>>>");
    	session.setCustomerServicesVO(null);
    	session.setService(null);
    	form.setListFlag("");
    	form.setService("");
    	form.setPoints("");
    	form.setRedeemToKeyContact("");
    	form.setDescription("");
    	log.exiting("SaveServiceMasterCommand","execute");
      invocationContext.target = SCREENLOAD_SUCCESS;
    }
	/**
	 * Function to get the details to be saved
	 * @param form
	 * @param logonAttributes
	 * @return
	 */
    private CustomerServicesVO getFormDetails(ServiceMasterForm form,LogonAttributes logonAttributes){

    	CustomerServicesVO vo = new CustomerServicesVO();
    	vo.setCompanyCode(logonAttributes.getCompanyCode());
    	vo.setServiceCode(form.getService()); 
    	if(form.getPoints()!=null && form.getPoints().trim().length()>0){
    		vo.setPoints(Double.parseDouble(form.getPoints()));
    	}
    	vo.setLastUpdatedUser(logonAttributes.getUserId());
    	if(form.getDescription()!=null && form.getDescription().trim().length()>0){
    		vo.setServiceDescription(form.getDescription().toUpperCase());
    	}
    	if(form.getRedeemToKeyContact()==null){
    		vo.setKeyContactFlag("N");
			log.log(Log.INFO,
					"form.getRedeemToKeyContact() as I since it is null", vo.getKeyContactFlag());
		}else{
			if("on".equals(form.getRedeemToKeyContact())){
				vo.setKeyContactFlag("Y");			
			}else{	
				vo.setKeyContactFlag("N");				
			}
		}
    	vo.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
    	log.log(Log.FINE, "vo----------->>>", vo);
		return vo;
    }
}

