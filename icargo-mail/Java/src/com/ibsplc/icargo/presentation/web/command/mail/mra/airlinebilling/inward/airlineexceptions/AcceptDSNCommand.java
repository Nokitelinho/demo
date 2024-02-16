/*
 * AcceptDSNCommand.java Created on April 18, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.airlineexceptions;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.AirlineExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author Deepthi.E.S
 * Command Class for AcceptDSNCommand screen.
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 July 18,2009 Deepthi.E.S Initial draft
 * 
 */
public class AcceptDSNCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AcceptInvoice ScreenloadCommand");

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

	private static final String ACTION_SUCCESS = "acceptpopup_success";

	private static final String ACCEPT_FAILURE = "acceptpopup_failure";

	private static final String EXCEPTION = "E";

	private static final String EXCEPTATUSNOTEXPCANNOTACCEPTED = "mailtracking.mra.airlinebilling.inward.invoiceexceptions.expsatusnotexp";

	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("AcceptDSNCommand", "execute");
		AirlineExceptionsForm form = (AirlineExceptionsForm) invocationContext.screenModel;
		AirlineExceptionsSession airLineExceptionsSession = (AirlineExceptionsSession) getScreenSession(
				MODULE_NAME, SCREENID);
		// String popDespatchNumber="";
		/**
		 * selected row getting here
		 */
		String[] selectedRow = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<AirlineExceptionsVO> airlineExceptionsVOs = null;
		airlineExceptionsVOs=airLineExceptionsSession.getAirlineExceptionsVOs();
		if (form.getSelectedRows() != null && form.getSelectedRows().length > 0) {
			selectedRow = form.getSelectedRows()[0].split(",");
		}
		/**
		 * here checking the EXCEPTION status is EXCEPTION
		 */
		if (airlineExceptionsVOs != null && airlineExceptionsVOs.size() > 0) {
			if (selectedRow != null && selectedRow.length > 0) {
				for (String s : selectedRow) {
					log.log(Log.FINE, "The selected row is -->", s);
				}
				errors = checkExceptionStatus(airlineExceptionsVOs,
						selectedRow, EXCEPTATUSNOTEXPCANNOTACCEPTED);
			}
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACCEPT_FAILURE;
			return;
		} else {
			airLineExceptionsSession.setSelectedRows(selectedRow);
		}
		invocationContext.target = ACTION_SUCCESS;
		log.exiting("ChangeStatusCommand", "execute");

	}

	/**
	 * 
	 * @param airlineExceptionsVOs
	 * @param selectedRow
	 * @param errorMessage
	 * @return
	 */
	public Collection<ErrorVO> checkExceptionStatus(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs,
			String selectedRow[], String errorMessage) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		int count = 0;
		int errorValue = 0;
		log.entering(MODULE_NAME, "checkExceptionStatus");
		if (airlineExceptionsVOs != null && airlineExceptionsVOs.size() > 0) {
			if (selectedRow != null && selectedRow.length > 0) {
				for (AirlineExceptionsVO airlineExceptionsVO : airlineExceptionsVOs) {
					for (String s : selectedRow) {

						log.log(Log.FINE, "Selected row-->", s);
						log.log(Log.FINE, "count-->", count);
						if (s != null && s.trim().length() > 0) {
							if (count == Integer.parseInt(s)) {
								log
										.log(
												Log.FINE,
												"exceptionInInvoiceVO.getExceptionStatus()",
												airlineExceptionsVO
														.getExceptionStatus());
								if (EXCEPTION.equals(airlineExceptionsVO
										.getExceptionStatus())) {
									errorValue++;
								}
							}
						}
					}
					count++;
				}
			}
			log.log(Log.FINE, "Error value", errorValue);
			if (errorValue == 0) {
				errorVO = new ErrorVO(errorMessage);
				errors.add(errorVO);
			}
		}
		log.exiting(MODULE_NAME, "checkExceptionStatus");
		return errors;
	}
}
