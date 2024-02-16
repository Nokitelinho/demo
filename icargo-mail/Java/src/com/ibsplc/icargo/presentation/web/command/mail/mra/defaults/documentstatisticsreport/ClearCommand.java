/*
 * ClearCommand.java Created on Sep, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.documentstatisticsreport;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults.DocumentStatisticsSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailDocumentStatisticsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class ClearCommand extends BaseCommand {

	private Log log = LogFactory
			.getLogger("DocumentStatistics Report ClearCommand");

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
	private static final String SCREEN_ID = "mailtracking.mra.defaults.documentstatisticsreport";

	/**
	 * Target action
	 */
	private static final String ACTION_SUCCESS = "clear_success";

	/**
	 * String
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
		MailDocumentStatisticsForm statisticsForm = (MailDocumentStatisticsForm) invocationContext.screenModel;
		DocumentStatisticsSessionImpl session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		statisticsForm.setCarrierCode(BLANK);
		statisticsForm.setFlightNo(BLANK);
		statisticsForm.setFromDate(BLANK);
		statisticsForm.setToDate(BLANK);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}