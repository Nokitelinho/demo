/*
 * ScreenLoadCommand.java Created on Sep30, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.residitrestrictonsetup;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ResiditRestrictonSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ResiditRestrictonSetUpForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3108
 *
 */
public class CloseCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("MailTracking,ResiditRestrictonSetUp");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.residitrestrictionsetup";
	
	private static final String SUCCESS = "close_success";
	
	private static final String BLANK = "";
	private static final String YES="Y";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
		
    	log.log(Log.FINE, "\n\n ClearCommand---------->\n\n");

    	ResiditRestrictonSetUpForm residitRestrictonSetUpForm =
							(ResiditRestrictonSetUpForm)invocationContext.screenModel;
    	ResiditRestrictonSetUpSession residitRestrictonSetUpSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	residitRestrictonSetUpForm.setAirportCodeFilter(BLANK);
    	residitRestrictonSetUpForm.setPaCodeFilter(BLANK);
    	residitRestrictonSetUpForm.setCarrierCodeFilter(BLANK);
    	residitRestrictonSetUpForm.setDisableSave(YES);
    	residitRestrictonSetUpSession.setResiditRestrictonVOs(null);
    	
    	residitRestrictonSetUpForm.setScreenStatusFlag
     					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

    	invocationContext.target = SUCCESS;

	}

}
