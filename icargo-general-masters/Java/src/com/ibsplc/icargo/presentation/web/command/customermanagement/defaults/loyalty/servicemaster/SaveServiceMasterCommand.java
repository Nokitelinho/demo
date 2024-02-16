/*
 * SaveServiceMasterCommand.java  Created on May 8, 2006
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
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is invoked on the start up of the 
 *  List Customer Group screen
 * 
 * @author A-2052
 */
public class SaveServiceMasterCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SaveServiceMasterCommand");	
	private static final String MODULE_NAME = "customermanagement.defaults";
	private static final String SCREEN_ID ="customermanagement.defaults.servicemaster";
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
       
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
    	ServiceMasterForm form = 
			(ServiceMasterForm)invocationContext.screenModel;    	 
    	ServiceMasterSession session = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
    	CustomerServicesVO vo = session.getCustomerServicesVO();
    	Collection<ErrorVO> errors = null;
    	log.log(Log.FINE, "vo before setting operational flag-------->>>", vo);
		if(session.getCustomerServicesVO() == null){
    		CustomerServicesVO customerServicesVO = new CustomerServicesVO();
    		customerServicesVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);  
    		log.log(Log.FINE,"AbstractVO.OPERATION_FLAG_INSERT-------------=======");
    		customerServicesVO = getFormDetails(customerServicesVO,form,logonAttributes);
    		log.log(Log.FINE, "vo before setting to delegate----->>>>",
					customerServicesVO);
			try{
        		customerDelegate.saveCustomerServices(customerServicesVO);
        	}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
            	handleDelegateException( businessDelegateException);
        	}
    	}else{
    		vo.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
    		log.log(Log.FINE,"AbstractVO.OPERATION_FLAG_UPDATE-------------=======");	 
    		vo = getFormDetails(vo,form,logonAttributes);
    		log.log(Log.FINE, "vo before setting to delegate----->>>>", vo);
			try{
        		customerDelegate.saveCustomerServices(vo);
        	}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
            	handleDelegateException(businessDelegateException);
        	}
    	}
    	log.log(Log.FINE,"setting all values to null----->>>>");
    	session.setCustomerServicesVO(null);
    	session.setService(null);
    	form.setListFlag("");
    	form.setService("");
    	form.setPoints("");
    	form.setRedeemToKeyContact("");
    	form.setDescription("");
    	
    	ErrorVO error = new ErrorVO("customermanagement.defaults.servicemaster.savedsuccessfully");
     	error.setErrorDisplayType(ErrorDisplayType.STATUS);
     	errors = new ArrayList<ErrorVO>();
     	errors.add(error);
     	invocationContext.addAllError(errors);
     	
    	log.exiting("SaveServiceMasterCommand","execute");
      invocationContext.target = SCREENLOAD_SUCCESS;
    }
/**
 * 
 * @param vo
 * @param form
 * @param logonAttributes
 * @return
 */
    private CustomerServicesVO getFormDetails(CustomerServicesVO vo,ServiceMasterForm form,LogonAttributes logonAttributes){

    	vo.setCompanyCode(logonAttributes.getCompanyCode());
    	vo.setServiceCode(form.getService().toUpperCase()); 
    	vo.setPoints(Double.parseDouble(form.getPoints()));
    	vo.setLastUpdatedUser(logonAttributes.getUserId());
    	if(form.getDescription()!=null && form.getDescription().trim().length()>0){
    		vo.setServiceDescription(form.getDescription().toUpperCase());
    	}else{
    		vo.setServiceDescription("");
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
    	log.log(Log.FINE, "vo----------->>>", vo);
		return vo;
    }
}

