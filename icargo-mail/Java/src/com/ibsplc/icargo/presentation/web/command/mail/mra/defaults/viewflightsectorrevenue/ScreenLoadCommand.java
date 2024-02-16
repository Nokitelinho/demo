/*
 * ScreenLoadCommand.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewflightsectorrevenue;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
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
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "ScreenLoadCommand";

	/**
	 * MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.viewflightsectorrevenue";

	/**
	 * Target action
	 */
	private static final String SCREEN_SUCCESS = "screenload_success";
	
	private static final String BLANK = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ViewMailFlightSectorRevenueForm viewSectorRevenueForm = (ViewMailFlightSectorRevenueForm) invocationContext.screenModel;
		ViewFlightSectorRevenueSession session = (ViewFlightSectorRevenueSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		viewSectorRevenueForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		viewSectorRevenueForm.setCarrierCode("NZ");
		
		double value = 0.0;
		viewSectorRevenueForm.setTotGrossWeight(value);
		viewSectorRevenueForm.setTotNetRevenue(value);
		viewSectorRevenueForm.setTotWeightCharge(value);
		viewSectorRevenueForm.setFromScreen(BLANK);
		session.setFlightDetails(null);
		session.setSectorDetails(null);
		session.setFlightSectorRevenueDetails(null);
		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

}
