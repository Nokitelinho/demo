/*
 * CloseCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewflightsectorrevenue;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewFlightSectorRevenueSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewMailFlightSectorRevenueForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class CloseCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "CloseCommand";

	/**
	 * MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.viewflightsectorrevenue";

	private static final String SCREENID = "mailtracking.mra.defaults.viewflightsectorrevenue";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLOSE_FAILURE = "close_failure";

	private static final String FROM_POSTINGDTLS = "Posting_Details";

	private static final String CLOSE_SCREEN_TO_POSTINGDTLS = "close_PostingDetails";

	/**
	 * Method to implement the close operation
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		ViewMailFlightSectorRevenueForm form = (ViewMailFlightSectorRevenueForm) invocationContext.screenModel;
		
		ViewFlightSectorRevenueSession viewFlightSectorRevenueSession = (ViewFlightSectorRevenueSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		viewFlightSectorRevenueSession.setFlightDetails(null);
		viewFlightSectorRevenueSession.setFlightSectorRevenueDetails(null);
		viewFlightSectorRevenueSession.setSectorDetails(null);
		log.log(Log.INFO, "From Screen==>>", form.getFromScreen());
		if (FROM_POSTINGDTLS.equals(form.getFromScreen())) {
			form.setFromScreen("");
			invocationContext.target = CLOSE_SCREEN_TO_POSTINGDTLS;
		} else {
			invocationContext.target = CLOSE_SUCCESS;
		}

	}

}
