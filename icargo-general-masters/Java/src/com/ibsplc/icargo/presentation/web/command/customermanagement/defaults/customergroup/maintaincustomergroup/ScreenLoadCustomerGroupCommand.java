/*
 * ScreenLoadCustomerGroupCommand.java  Created on May 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.maintaincustomergroup;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.CustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.CustomerGroupForm;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is invoked on the start up of the 
 *  Customer Group screen
 * 
 * @author A-2122
 */
public class ScreenLoadCustomerGroupCommand extends BaseCommand {

	
	
	private Log log = LogFactory.getLogger("MAINTAIN CUSTOMER GROUP");	
	private static final String MODULE_NAME = "customermanagement.defaults";
	private static final String SCREEN_ID = 
								"customermanagement.defaults.customergroup";
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
       

    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenLoadCustomerGroupCommand","execute");
    	CustomerGroupForm customerGroupForm = 
			(CustomerGroupForm)invocationContext.screenModel;
    	
    	CustomerGroupSession customerGroupSession = 
    		
			getScreenSession(MODULE_NAME, SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	customerGroupSession.setCustomerGroupVO(null);
    	customerGroupForm.setStatusFlag("I");
    	if(("List").equals(customerGroupForm.getCloseFlag())){
    		customerGroupForm.setDetailsFlag("From List");
    	}
    	
    	log.log(Log.FINE, "CloseFlag-->", customerGroupForm.getCloseFlag());
		customerGroupForm.setStation(logonAttributes.getStationCode());
    	customerGroupForm.setScreenStatusFlag
    					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
      invocationContext.target = SCREENLOAD_SUCCESS;
    }
    
	
	
}

