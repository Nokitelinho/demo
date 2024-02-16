/*
 * ScreenLoadCommand.java Created on July 7, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoiceenquiry;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3434
 *
 */
public class ScreenLoadCommand extends BaseCommand{
	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ScreenLoadInvoiceEnquiry";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";

	private static final String SCREEN_SUCCESS = "screen_success";

	/**
	 * Method to implement the screen load operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		GPABillingInvoiceEnquiryForm gpaBillingInvoiceEnquiryForm=
    		(GPABillingInvoiceEnquiryForm)invocationContext.screenModel;
		GPABillingInvoiceEnquirySession session = (GPABillingInvoiceEnquirySession) getScreenSession(
 				MODULE_NAME, SCREEN_ID);
		session.removeAllAttributes();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
 		Collection<String> parameterTypes = new ArrayList<String>();
 		parameterTypes.add("mra.gpabilling.billingstatus");
 		parameterTypes.add("mra.gpabilling.invoicestatus");
 		ApplicationSessionImpl applicationSession = getApplicationSession();
 		LogonAttributes logonAttributes = applicationSession.getLogonVO();
 		String companyCode = logonAttributes.getCompanyCode();
 		
 		try {
 			oneTimeValues =  sharedDefaultsDelegate.findOneTimeValues(
 					companyCode, parameterTypes);
 			log.log(Log.FINE, " One Time Values---->>", oneTimeValues);

 		} catch (BusinessDelegateException e) {
 			handleDelegateException(e);

 		}
 		session.removeCN51SummaryVO();
 		session.setGpaBillingInvoiceEnquiryFilterVO(null);
 		session.setOneTimeMap((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		gpaBillingInvoiceEnquiryForm.setDisableButton("Y");
		gpaBillingInvoiceEnquiryForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREEN_SUCCESS;
		log.entering(CLASS_NAME,"execute");
	}


}
