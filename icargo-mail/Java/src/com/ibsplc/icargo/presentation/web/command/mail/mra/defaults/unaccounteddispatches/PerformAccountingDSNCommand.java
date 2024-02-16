/*

 *

 * PerformAccountingDSNCommand.java Created on MAR 13, 2009

 *

 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS Software Services (P) Ltd.

 * Use is subject to license terms.

 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.unaccounteddispatches;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.UnaccountedDispatchesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.UnaccountedDispatchesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class PerformAccountingDSNCommand extends BaseCommand {

	/**
	 * String MODULENAME
	 */
	private static final String MODULE = "mailtracking.mra.defaults";

	/**
	 * CLASSNAME
	 */
	private static final String CLASS_NAME = "PerformAccountingDSNCommand";

	/**
	 * SCREENID
	 */
	private static final String SCREENID = "mailtracking.mra.defaults.unaccounteddispatches";

	/**
	 * TARGET ACTION
	 */
	private static final String ACCOUNTING_SUCCESS = "accounting_success";

	/**
	 * TARGET ACTION
	 */
	private static final String ACCOUNTING_FAILURE = "accounting_failure";
	
	private static final String INFO_ACCOUNTING_SUCCESS = "mra.defaults.unaccounteddispatches.accountingsuccess";
	
	private static final String IMPORTED_WITH_EXCEPTION = "mailtracking.mra.defaults.unaccounteddespatch.importedwithexception";
	/**
	 * REASON CODE
	 */
	private static final String REASON_CODE = "R1";

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		UnaccountedDispatchesForm unaccountedDispatchesForm = (UnaccountedDispatchesForm) invocationContext.screenModel;
		UnaccountedDispatchesSession session = getScreenSession(MODULE,
				SCREENID);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<UnaccountedDispatchesDetailsVO> unaccountedDespatchesPage = null;
		UnaccountedDispatchesVO unaccountedDispatchesVO = session
		.getUnaccountedDispatchesVO();
		if (unaccountedDispatchesVO != null) {
			unaccountedDespatchesPage = unaccountedDispatchesVO
			.getUnaccountedDispatchesDetails();
		}

		Collection<UnaccountedDispatchesDetailsVO> dispatchesDetailsVOs = new ArrayList<UnaccountedDispatchesDetailsVO>();

		if (unaccountedDespatchesPage == null
				|| unaccountedDespatchesPage.size() == 0) {
			return;
		}
		String[] rowIds = unaccountedDispatchesForm.getSelectedRows()[0]
		                                                              .split(",");
		int size = rowIds.length;
		if (rowIds != null && size > 0) {
			for (int i = 0; i < size; i++) {
				log.log(Log.FINE, "ROW IDs1234", rowIds, i);
				if (rowIds[i] != null && rowIds[i].length() > 0) {
					int pos = Integer.parseInt(rowIds[i]);
					log.log(Log.FINE, "pos1234", pos);
					dispatchesDetailsVOs
					.add(unaccountedDespatchesPage.get(pos));

				}
			}
		}
		for (UnaccountedDispatchesDetailsVO dispatchesDetailsVO : dispatchesDetailsVOs) {
			if (!REASON_CODE.equals(dispatchesDetailsVO.getReason())) {
				ErrorVO error = new ErrorVO(
				"mailtracking.mra.defaults.invalidreasoncode");
				errors.add(error);
				break;
			}
			if(dispatchesDetailsVO.getBilBase() == null || 
					(dispatchesDetailsVO.getBilBase() != null && dispatchesDetailsVO.getBilBase().trim().length() == 0)) {
				/*
				 * Billing Basis will be avaliable only when, there is no exception in importing.
				 */
				ErrorVO error = new ErrorVO(IMPORTED_WITH_EXCEPTION);
				errors.add(error);
				break;
			}
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACCOUNTING_FAILURE;
			return;
		}
		String airlineCode = "";
		for (UnaccountedDispatchesDetailsVO dispatchesDetailVO : dispatchesDetailsVOs) {
			if (dispatchesDetailVO.getFlightNumber() != null) {
				AirlineValidationVO airlineValidationVO = null;
				AirlineDelegate airlineDelegate = new AirlineDelegate();
				airlineCode = dispatchesDetailVO.getFlightNumber().substring(0,
						2);
				if (airlineCode != null && airlineCode.trim().length() > 0) {
					log.log(Log.FINE,
							"inside validateAirlineCodeTwo--------------->>>>",
							airlineCode);
					try {
						airlineValidationVO = airlineDelegate
						.validateAlphaCode(dispatchesDetailVO
								.getCompanyCode(), airlineCode
								.toUpperCase());

					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					if (airlineValidationVO != null) {
						dispatchesDetailVO
						.setFlightCarrierId(airlineValidationVO
								.getAirlineIdentifier());
					}
					dispatchesDetailVO.setFlightNo(dispatchesDetailVO
							.getFlightNumber().substring(2));
				}
			}
		}
		log.log(Log.INFO, "passing collection", dispatchesDetailsVOs);
		try {
			new MailTrackingMRADelegate()
			.performAccountingForDSNs(dispatchesDetailsVOs);

		} catch (BusinessDelegateException exception) {
			errors = handleDelegateException(exception);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACCOUNTING_FAILURE;
			return;
		}
		ErrorVO error = new ErrorVO(INFO_ACCOUNTING_SUCCESS);
		invocationContext.addError(error);
		invocationContext.target = ACCOUNTING_SUCCESS;

	}
}
