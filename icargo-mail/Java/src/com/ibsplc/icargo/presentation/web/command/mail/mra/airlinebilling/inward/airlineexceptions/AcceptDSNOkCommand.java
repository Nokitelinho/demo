/*
 * AcceptDSNOkCommand.java Created on July 25, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.airlineexceptions;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.AirlineExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Deepthi.E.S
 * OK Command Class for AcceptDSNOkCommand screen.
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 April 18,2009 Deepthi.E.S Initial draft
 * 
 */
public class AcceptDSNOkCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AirlineBilling");

	/**
	 * module name
	 * 
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * screen id
	 * 
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.airlineexceptions";

	private static final String ACTION_SUCCESS = "acceptpopupok_success";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("AcceptDSNOkCommand", "execute");
		AirlineExceptionsForm form = (AirlineExceptionsForm) invocationContext.screenModel;
		AirlineExceptionsSession airLineExceptionsSession = (AirlineExceptionsSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Page<AirlineExceptionsVO> airlineExceptionsVOs = (Page<AirlineExceptionsVO>) airLineExceptionsSession
				.getAirlineExceptionsVOs();

		String[] select = null;
		if (airLineExceptionsSession.getSelectedRows() != null
				&& airLineExceptionsSession.getSelectedRows().length > 0) {
			select = airLineExceptionsSession.getSelectedRows();
		}
		form.setScreenStatus("ok");
		if (select != null && select.length > 0) {
			form.setScreenStatus("ok");
			for (int i = 0; i < select.length; i++) {

				if (form.getPopupRemarks() != null
						&& form.getPopupRemarks().trim().length() > 0) {

					if (select[i] != null && select[i].trim().length() > 0) {
						airlineExceptionsVOs.get(Integer.parseInt(select[i]))
								.setRemark(form.getPopupRemarks());
					}
				}
			}
		}
		airLineExceptionsSession.setAirlineExceptionsVOs(airlineExceptionsVOs);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting("AcceptInvoiceOkCommand", "execute");

	}

}
