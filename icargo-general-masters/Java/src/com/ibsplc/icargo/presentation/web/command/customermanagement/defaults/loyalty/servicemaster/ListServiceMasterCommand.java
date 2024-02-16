/*
 * ListServiceMasterCommand.java  Created on May 8, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.servicemaster;

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
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is invoked on the start up of the 
 *  List Customer Group screen
 * 
 * @author A-2052
 */
public class ListServiceMasterCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListServiceMasterCommand");	
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
    	log.entering("ListServiceMasterCommand","execute");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        String companyCode = logonAttributes.getCompanyCode();
        
    	ServiceMasterForm form = (ServiceMasterForm)invocationContext.screenModel;    	 
    	ServiceMasterSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
    	CustomerMgmntDefaultsDelegate customerDelegate = new CustomerMgmntDefaultsDelegate();
    	CustomerServicesVO vo = new CustomerServicesVO();
    	session.setCustomerServicesVO(null);
    	
    	String service = form.getService().toUpperCase();
    	log.log(Log.FINE, "service--------->>", service);
		session.setService(service);
    	try{
    		vo = customerDelegate.listCustomerServices(companyCode,service);
    	}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
        	handleDelegateException(businessDelegateException);
    	}
    	if(vo!=null){
    		log.log(Log.FINE, "vo= gettting from server----------->>", vo);
			session.setCustomerServicesVO(vo);
    	}
    	log.exiting("ListServiceMasterCommand","execute");
    	form.setListFlag("afterlist");
    	if(session.getCustomerServicesVO()!=null){    		
    		CustomerServicesVO customerServicesVO = session.getCustomerServicesVO();
    		log.log(Log.FINE, "customerServicesVO-------->>>>",
					customerServicesVO);
			form.setService(customerServicesVO.getServiceCode());
    		form.setPoints(String.valueOf(customerServicesVO.getPoints()));
    		if(AbstractVO.FLAG_YES.equals(customerServicesVO.getKeyContactFlag())){
    			form.setRedeemToKeyContact("on");
    		}else{
    			form.setRedeemToKeyContact("");
    		}    		
    		form.setDescription(customerServicesVO.getServiceDescription());
    	}
      invocationContext.target = SCREENLOAD_SUCCESS;
    }
}

