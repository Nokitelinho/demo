/*
 * ClearCommand.java Created on July 17, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.viewformone;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormOneForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3456
 * 
 */
public class ClearCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");
	
	private static final String MODULE = "mra.airlinebilling";
	
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";
		
	private static final String CLEAR_SUCCESS = "clear_success";
	
	private static final String EMPTY_STRING = "";
	
	private static final String CLASS_NAME = "ClearCommand";
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ViewMailFormOneForm viewFormOneForm = (ViewMailFormOneForm)invocationContext.screenModel;
		
		ViewFormOneSession session = (ViewFormOneSession) getScreenSession(
				MODULE, SCREENID);
		
		session.removeAllAttributes();
		viewFormOneForm.setClearancePeriod(EMPTY_STRING);
		viewFormOneForm.setAirlineCodeFilterField(EMPTY_STRING);
		viewFormOneForm.setAirlineCode(EMPTY_STRING);
		viewFormOneForm.setAirlineNumber(EMPTY_STRING);
		viewFormOneForm.setBillingCurrency(EMPTY_STRING);
		viewFormOneForm.setListingCurrency(EMPTY_STRING);
		viewFormOneForm.setExchangeRateInBillingCurrency(EMPTY_STRING);
		viewFormOneForm.setExchangeRateInListingCurrency(EMPTY_STRING);
		viewFormOneForm.setTableClass(EMPTY_STRING);
		session.removeAllAttributes();
		log.exiting(CLASS_NAME,"execute");
		invocationContext.target=CLEAR_SUCCESS;

	}
}
