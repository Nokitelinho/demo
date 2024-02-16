/*
 * ScreenLoadCommand.java Created on Dec 15, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * 
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51s";

	private static final String SCREEN_SUCCESS = "screen_success";

	private static final String CN51CN66_SCREEN = "cn51cn66";

	private static final String BLANK = "";

	private static final String SYS_PARA_ACC_ENTRY = "cra.accounting.isaccountingenabled";

	/**
	 * Method to implement the screen load operation
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		ListCN51Form listCN51Form = (ListCN51Form) invocationContext.screenModel;
		ListCN51Session listCN51Session = (ListCN51Session) getScreenSession(
				MODULE_NAME, SCREENID);

		listCN51Form.setAirlinecode(getApplicationSession().getLogonVO()
				.getOwnAirlineCode());

		if (!CN51CN66_SCREEN.equals(listCN51Form.getFromScreen())) {

			// clearing vos in session
			listCN51Session.removeAllAttributes();

			// setting default values to be shown on screen load
			listCN51Form.setListcn51frmdat(new LocalDate(NO_STATION, NONE,
					false).addDays(-10).toDisplayFormat());
			listCN51Form
					.setListcn51todat(new LocalDate(NO_STATION, NONE, false)
							.toDisplayFormat());

		} else {

			populateFormFields(listCN51Session.getCN51SummaryFilterVO(),
					listCN51Form);
		}
		// code for acc entry sys para starts
		Collection<String> systemParameterCodes = new ArrayList<String>();

		systemParameterCodes.add(SYS_PARA_ACC_ENTRY);

		Map<String, String> systemParameters = null;

		try {

			systemParameters = new SharedDefaultsDelegate()
					.findSystemParameterByCodes(systemParameterCodes);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			handleDelegateException(e);
		}

		String accountingEnabled = (systemParameters.get(SYS_PARA_ACC_ENTRY));
		log.log(Log.INFO, "IS acc enabled--->", accountingEnabled);
		if ("N".equals(accountingEnabled)) {
			listCN51Form.setAccEntryFlag("N");
		} else {
			listCN51Form.setAccEntryFlag("Y");
		}
		// code for acc entry sys para ends
		invocationContext.target = SCREEN_SUCCESS;
	}

	/**
	 * populates ListCN51Form from CN51SummaryFilterVO
	 * 
	 * @param cn51SummaryFilterVO
	 * @param listCN51Form
	 */
	private void populateFormFields(CN51SummaryFilterVO cn51SummaryFilterVO,
			ListCN51Form listCN51Form) {

		String date = BLANK;
		LocalDate tempDate = null;

		tempDate = cn51SummaryFilterVO.getFromDate();
		date = tempDate == null ? BLANK : tempDate.toDisplayFormat();
		listCN51Form.setListcn51frmdat(date);

		tempDate = cn51SummaryFilterVO.getToDate();
		date = tempDate == null ? BLANK : tempDate.toDisplayFormat();
		listCN51Form.setListcn51todat(date);
	}
}
