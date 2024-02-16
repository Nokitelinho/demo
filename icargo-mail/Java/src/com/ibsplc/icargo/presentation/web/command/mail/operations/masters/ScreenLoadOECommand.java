/*
 * ScreenLoadOECommand.java Created on June 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OfficeOfExchangeMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeMasterForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ScreenLoadOECommand extends BaseCommand {

	private static final String SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger("ScreenLoadOECommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
							"mailtracking.defaults.masters.officeofexchange";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the screen load---------->\n\n");

    	OfficeOfExchangeMasterForm oeMasterForm =
					(OfficeOfExchangeMasterForm)invocationContext.screenModel;
    	OfficeOfExchangeMasterSession oeSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);

    	oeMasterForm.setOfficeOfExchange("");
    	
    	oeSession.setOfficeOfExchangeVOs(null);
    	
    	oeMasterForm.setScreenStatusFlag
     					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	log.log(Log.FINE, "\n\n out of screen load---------->\n\n");

    	invocationContext.target = SUCCESS;

	}

}
