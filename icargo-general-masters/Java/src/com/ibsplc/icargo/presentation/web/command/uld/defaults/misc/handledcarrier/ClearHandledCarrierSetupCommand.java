/*
 * ClearHandledCarrierSetupCommand.java Created on Dec 5, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.handledcarrier;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDHandledCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.HandledCarrierSetupForm;

/**
 * This command class is used to clear the session values
 * @author A-2883
 */

public class ClearHandledCarrierSetupCommand extends BaseCommand{
	
	private static final String SCREEN_ID = 
		"uld.defaults.misc.handledcarriersetup";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	 /**
	   * execute method
	   * @param invocationContext
	   * @throws CommandInvocationException
	   */
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		
		HandledCarrierSetupForm handledCarrierSetupForm = 
			(HandledCarrierSetupForm) invocationContext.screenModel;
	    ULDHandledCarrierSession uldCarrierSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
	    
		uldCarrierSession.setULDHandledCarrierVO(null);
		handledCarrierSetupForm.setAirlineCode(null);
		handledCarrierSetupForm.setStationCode(null);
		handledCarrierSetupForm.setTemplateAirlineCode(null);
		handledCarrierSetupForm.setTemplateAirlineName(null);
		handledCarrierSetupForm.setTemplateOperationFlag(null);
		handledCarrierSetupForm.setTemplateStationCode(null);
		
		invocationContext.target=SCREENLOAD_SUCCESS;
		
		
	}
	

}
