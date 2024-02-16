/*
 * ScreenLoadListCustomerGroupCommand.java  Created on May 8, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.listcustomergroup;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.ListCustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.ListCustomerGroupForm;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is invoked on the start up of the 
 *  List Customer Group screen
 * 
 * @author A-2122
 */
public class ScreenLoadListCustomerGroupCommand extends BaseCommand {

	
	
	private Log log = LogFactory.getLogger("LIST CUSTOMER GROUP");	
	private static final String MODULE_NAME = "customermanagement.defaults";
	private static final String SCREEN_ID = 
								"customermanagement.defaults.listcustomergroup";
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
       

    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenLoadListCustomerGroupCommand","execute");
    	ListCustomerGroupForm listCustomerGroupForm = 
			(ListCustomerGroupForm)invocationContext.screenModel;    	 
    	ListCustomerGroupSession listCustomerGroupSession = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
    	listCustomerGroupSession.setCustomerGroupFilterVO(null);
    	listCustomerGroupSession.setCustomerGroupVO(null);
    	listCustomerGroupForm.setCreateFlag("ScreenLoad");
    	listCustomerGroupForm.setScreenStatusFlag
    					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
      invocationContext.target = SCREENLOAD_SUCCESS;
    }
    
	
	
}

