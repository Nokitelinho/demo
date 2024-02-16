/*
 * CloseCustomerGroupCommand.java Created on May 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.maintaincustomergroup;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.ListCustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.CustomerGroupForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2122
 *
 */
public class CloseCustomerGroupCommand extends BaseCommand {
	/**
	 * Logger for CustomerGroup
	 */
	private Log log = LogFactory.getLogger("MAINTAIN CUSTOMER GROUP");
		
	private static final String MODULELIST_NAME = "customermanagement.defaults";
	private static final String SCREENLIST_ID = 
								"customermanagement.defaults.listcustomergroup";
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String LIST_FORWARD = "list_forward";
	private static final String CREATE_SUCCESS = "create_success";
	 /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("CloseCustomerGroupCommand","execute");
    	CustomerGroupForm customerGroupForm = 
			(CustomerGroupForm)invocationContext.screenModel;    	
    	log.log(Log.FINE, "CloseFlag", customerGroupForm.getCloseFlag());
		ListCustomerGroupSession listCustomerGroupSession = 
			getScreenSession(MODULELIST_NAME,SCREENLIST_ID);
    	
    	if(("From List").equals(customerGroupForm.getDetailsFlag())){
    		listCustomerGroupSession.setListStatus("noListForm");
        	invocationContext.target = LIST_FORWARD; 
        	}
    	else if(("ScreenLoad").equals(customerGroupForm.getCloseFlag())){
    		customerGroupForm.setCloseFlag("");
    		invocationContext.target = CREATE_SUCCESS; 
    	}
		else{        	
    	invocationContext.target=CLOSE_SUCCESS;
    }
    }
}
