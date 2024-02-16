/*
 * ScreenLoadCommand.java Created on Dec 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.invoicelov;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.InvoiceLOVSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.InvoiceLOVForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2524
 * 
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");

	private final static String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String BILLING_TYPE = "mailtracking.mra.billingtype";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.invoicelov";

	private static final String MODULE = "mailtracking.mra.airlinebilling";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "ScreenLoadCommand");
		log.entering(CLASS_NAME, "execute");
		InvoiceLOVForm invoiceLovForm = (InvoiceLOVForm) invocationContext.screenModel;
		String invoiceNumber = null;
		String clearancePeriod = null;
		String airlineCode = null;
		String interlineBillingType = null;
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		String displayPage = invoiceLovForm.getDisplayPage();
		interlineBillingType = invoiceLovForm.getBillingType();
		clearancePeriod = invoiceLovForm.getClearancePeriod();
		airlineCode = invoiceLovForm.getAirlineCode();
		invoiceNumber = invoiceLovForm.getCode();
		log.log(Log.FINE, "Invoice number:---> ", invoiceNumber);
		log.log(Log.FINE, "clearancePeriod:---> ", clearancePeriod);
		log.log(Log.FINE, "airlineCode:---> ", airlineCode);
		log.log(Log.FINE, "interlineBillingType:---> ", interlineBillingType);
		InvoiceLOVSession invoiceLovSession = (InvoiceLOVSession) getScreenSession(
				MODULE, SCREENID);
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(BILLING_TYPE);
		try {
			hashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		for (Collection<OneTimeVO> oneTimeVos : hashMap.values()) {
			invoiceLovSession.setBillingType(oneTimeVos);
			log.log(Log.FINE, "<---------- BILLING TYPE ----------->");
			log.log(Log.FINE, oneTimeVos.toString());
		}
		Page<AirlineInvoiceLovVO> invoiceLovVos = null;
		InvoiceLovFilterVO invoiceLovFilterVO = new InvoiceLovFilterVO();
		invoiceLovFilterVO.setPageNumber(Integer.parseInt(displayPage));

		invoiceLovFilterVO.setCompanycode(companyCode);
		if (invoiceNumber != null && invoiceNumber.length() > 0) {
			invoiceLovFilterVO.setInvoicenumber(invoiceNumber.toUpperCase());
		}
		if (clearancePeriod != null && clearancePeriod.length() > 0) {
			invoiceLovFilterVO
					.setClearanceperiod(clearancePeriod.toUpperCase());
		}
		if (airlineCode != null && airlineCode.length() > 0) {
			invoiceLovFilterVO.setAirlineCode(airlineCode.toUpperCase());
		}
		if (interlineBillingType != null && interlineBillingType.length() > 0) {
			invoiceLovFilterVO.setInterlinebillingtype(interlineBillingType
					.toUpperCase());
		}

		try {
			invoiceLovVos = new MailTrackingMRADelegate()
					.displayInvoiceLOV(invoiceLovFilterVO);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		invoiceLovForm.setInvoiceLovVos(invoiceLovVos);
		log.exiting(CLASS_NAME, "execute");
		invocationContext.target = SCREENLOAD_SUCCESS;

	}

}
