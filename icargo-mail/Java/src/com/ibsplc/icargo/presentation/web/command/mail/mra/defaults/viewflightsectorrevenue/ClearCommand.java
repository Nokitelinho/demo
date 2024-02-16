/*
 * ClearCommand.java Created on Aug, 2008
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
public class ClearCommand extends BaseCommand {

	private Log log = LogFactory
			.getLogger("ViewFlightSectorRevenue ClearCommand");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "ClearCommand";

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
	private static final String ACTION_SUCCESS = "clear_success";

	/**
	 * String BLANK
	 */
	private static final String BLANK = "";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		log.log(Log.INFO, "inside clear1234");
		ViewFlightSectorRevenueSession session = (ViewFlightSectorRevenueSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ViewMailFlightSectorRevenueForm form = (ViewMailFlightSectorRevenueForm) invocationContext.screenModel;
		form.setFlightNo(BLANK);
		form.setFlightDate(BLANK);
		form.setSector(BLANK);
		form.setFlightStatus(BLANK);
		form.setFlightSectorStatus(BLANK);
		form.setListSegmentsFlag(BLANK);
		form.setDuplicateFlightFlag(BLANK);
		form.setFromScreen(BLANK);
		form.setSectorCtrlFlg("N");
		form.setCarrierCode("NZ");
		double value = 0.0;
		form.setTotGrossWeight(value);
		form.setTotNetRevenue(value);
		form.setTotWeightCharge(value);
		session.setFlightDetails(null);
		session.setSectorDetails(null);
		session.setFlightSectorRevenueDetails(null);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}