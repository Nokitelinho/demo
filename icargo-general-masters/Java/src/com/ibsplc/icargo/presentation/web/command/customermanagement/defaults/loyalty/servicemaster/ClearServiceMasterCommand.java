/*
 * ClearServiceMasterCommand.java  Created on May 8, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.servicemaster;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ServiceMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ServiceMasterForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is invoked on the start up of the 
 *  List Customer Group screen
 * 
 * @author A-2052
 */
public class ClearServiceMasterCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ClearServiceMasterCommand");	
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
    	log.entering("ClearServiceMasterCommand","execute");
    	ServiceMasterForm form = 
			(ServiceMasterForm)invocationContext.screenModel;    	 
    	ServiceMasterSession session = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
    	session.setCustomerServicesVO(null);
    	session.setService(null);
    	form.setListFlag("");
    	form.setService("");
    	form.setPoints("");
    	form.setRedeemToKeyContact("");
    	form.setDescription("");
    	log.exiting("ClearServiceMasterCommand","execute");
      invocationContext.target = SCREENLOAD_SUCCESS;
    }
}

