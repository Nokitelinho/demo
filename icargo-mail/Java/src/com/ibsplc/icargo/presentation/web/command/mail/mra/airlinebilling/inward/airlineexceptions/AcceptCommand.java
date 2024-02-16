/*
 * AcceptCommand.java Created on APR 18, 2009
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
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.AirlineExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command Class for InvoiceExceptions screen.
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Feb 20, 2007 Deepthi.E.S Initial draft
 * 
 */
public class AcceptCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");

	private static final String CLASS_NAME = "AcceptCommand";

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

	/**
	 * ACCEPT_SUCCESS Action
	 */
	private static final String ACCEPT_SUCCESS = "accept_success";

	/**
	 * ACCEPT_FAILURE Action
	 */
	private static final String ACCEPT_FAILURE = "accept_failure";

	private static final String EXCEPTATUSNOTEXPCANNOTACCEPTED = "mailtracking.mra.airlinebilling.inward.invoiceexceptions.expsatusnotexp";

	private static final String EXCEPTION = "E";

	/**
	 * 
	 * Execute method *
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		AirlineExceptionsForm form = (AirlineExceptionsForm) invocationContext.screenModel;
		AirlineExceptionsSession airLineExceptionsSession = (AirlineExceptionsSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Collection<AirlineExceptionsVO> airlineExceptionsVOs = airLineExceptionsSession
				.getAirlineExceptionsVOs();
		Collection<AirlineExceptionsVO> tempairlineExpInvoiceVOs = new ArrayList<AirlineExceptionsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String selectedRow[] = null;
		MailTrackingMRADelegate deligate = new MailTrackingMRADelegate();
		/**
		 * selected row getting here
		 */
		/*
		 * if(form.getSelectedRows()!=null && form.getSelectedRows().length>0){
		 * selectedRow=form.getSelectedRows(); }
		 */
		if (airLineExceptionsSession.getSelectedRows() != null
				&& airLineExceptionsSession.getSelectedRows().length > 0) {
			log.log(Log.FINE,
					"The selected rows not null in session in accept command");
			selectedRow = airLineExceptionsSession.getSelectedRows();
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
		}
		if (airlineExceptionsVOs != null && airlineExceptionsVOs.size() > 0) {
			int count = 0;
			for (AirlineExceptionsVO vo : airlineExceptionsVOs) {

				log.log(Log.FINE, "THe ccount", count);
				if (selectedRow != null && selectedRow.length > 0) {
					for (String s : selectedRow) {
						log.log(Log.FINE, "THe seelcted row", s);
						if (s != null && s.trim().length() > 0) {
							if (count == Integer.parseInt(s)) {
								log.log(Log.FINE, "THe seelcted row", s);
								if (EXCEPTION.equals(vo.getExceptionStatus())) {
									tempairlineExpInvoiceVOs.add(vo);
								}
							}
						}
					}
					count++;
				}
			}
		}
		for (AirlineExceptionsVO vo : tempairlineExpInvoiceVOs) {
			log.log(Log.FINE, "THe finel vo are", vo);
		}

		/**
		 * Deliagte Call done here
		 */
		try {
			deligate.acceptAirlineDsns(tempairlineExpInvoiceVOs);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			handleDelegateException(e);
		}

		form.setScreenStatus("");
		invocationContext.target = ACCEPT_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 * 
	 * @param airlineExceptionsVOs
	 * @param selectedRow
	 * @param errorMessage
	 * @return
	 * @exception
	 */
	public Collection<ErrorVO> checkExceptionStatus(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs,
			String selectedRow[], String errorMessage) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		int count = 0;
		int errorValue = 0;
		log.entering(CLASS_NAME, "checkExceptionStatus");
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
		log.exiting(CLASS_NAME, "checkExceptionStatus");
		return errors;
	}
}
