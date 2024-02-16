/**
 * CloseCommand.java Created on May 11, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.pomailsummary;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.POMailSummarySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.POMailSummaryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class CloseCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("CloseCommand");

	private static final String CLASS_NAME = "CloseCommand";
	private static final String BLANK="";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.pomailsummary";
	private static final String ACTION_SUCCESS = "close_success";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, MODULE_NAME);
		POMailSummaryForm form=( POMailSummaryForm)invocationContext.screenModel;
		POMailSummarySession session = (POMailSummarySession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		form.setLocation(BLANK);
		form.setLocationType(BLANK);
		form.setFromDate(BLANK);
		form.setToDate(BLANK);
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target=ACTION_SUCCESS;
		log.exiting(CLASS_NAME, MODULE_NAME);

	}

}
