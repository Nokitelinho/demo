/*
 * ClearCommand.java Created on Aug, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listcca;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 *
 */
public class ClearCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListCCA ScreenloadCommand");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "ListCommand";

	/**
	 * MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listcca";

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
		ListCCAForm form = (ListCCAForm) invocationContext.screenModel;
		ListCCASession session = (ListCCASession) getScreenSession(MODULE_NAME,
				SCREEN_ID);
		form.setCcaNum(BLANK);
		form.setCcaType(BLANK);
		form.setDsn(BLANK);
		form.setDsnDate(BLANK);
		form.setCcaStatus(BLANK);
		form.setIssueParty(BLANK);
		form.setAirlineCode(BLANK);
		form.setGpaCode(BLANK);
		form.setGpaName(BLANK);
		form.setFrmDate(BLANK);
		form.setToDate(BLANK);
		form.setOrigin(BLANK);
		form.setOriginOfficeOfExchange(BLANK);
		form.setDestinationOfficeOfExchange(BLANK);
		form.setMailCategory(BLANK);
		form.setSubClass(BLANK);
		form.setYear(BLANK);
		form.setReceptacleSerialNumber(BLANK);
		form.setRegisteredIndicator(BLANK);
		form.setHighestNumberIndicator(BLANK);
		form.setDestination(BLANK);
		session.setCCADetailsVOs(null);
		session.setCCAFilterVO(null);
		LocalDate dateTo = new LocalDate(getApplicationSession().getLogonVO()
				.getAirportCode(), Location.ARP, true);
		String toDateToString = TimeConvertor.toStringFormat(dateTo
				.toCalendar(), "dd-MMM-yyyy");
		form.setToDate(toDateToString);
		form.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		form.setMcacreationtype(BLANK);

		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}