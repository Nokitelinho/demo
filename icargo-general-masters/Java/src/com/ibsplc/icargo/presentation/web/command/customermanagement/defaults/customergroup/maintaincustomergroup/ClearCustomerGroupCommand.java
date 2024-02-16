/*
 * ClearCustomerGroupCommand.java Created on May 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.maintaincustomergroup;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.CustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.CustomerGroupForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for clearing the screen
 *
 * @author A-2122
 */
public class ClearCustomerGroupCommand extends BaseCommand {

    private static final String SCREEN_ID =
    				"customermanagement.defaults.customergroup";
	private static final String MODULE_NAME = "customermanagement.defaults";
    private static final String CLEAR_SUCCESS = "clear_success";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	CustomerGroupForm customerGroupForm = 
			(CustomerGroupForm) invocationContext.screenModel;
        CustomerGroupSession customerGroupSession = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
		Log log = LogFactory.getLogger("MAINTAIN CUSTOMER GROUP");
		log.entering("ClearCustomerGroupCommand","execute");
		//customerGroupForm.setGroupCode("");
		customerGroupForm.setCustomerGroupCode("");
		customerGroupSession.setCustomerGroupVO(null);
		new ICargoComponent().setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting("ClearCustomerGroupCommand","execute");
    }
}
