/*
 * ScreenLoadHandledCarrierSetupCommand.java Created on Dec 5, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.handledcarrier;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.HandledCarrierSetupForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start 
 * up of the HandledCarrierSetup screen
 * 
 * @author A-2883
 */

public class ScreenLoadHandledCarrierSetupCommand extends BaseCommand{
	
	private static final String SCREEN_ID = 
		"uld.defaults.misc.handledcarriersetup";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	   /**
	   * execute method
	   * @param invocationContext
	   * @throws CommandInvocationException
	   */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.log(Log.FINE,"\n -*--*-*-*-*****");
		 
		 ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			HandledCarrierSetupForm handledCarrierSetupForm = 
				(HandledCarrierSetupForm) invocationContext.screenModel;
			
			
			
			invocationContext.target = SCREENLOAD_SUCCESS;
			
	 }

}
