/*
 * ScreenLoadCommand.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.invoicenquirydetails;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicEnquiryDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicEnquiryDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2270
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

    private static final String SCREENID ="mailtracking.mra.defaults.invoicenquirydetails";

	private static final String SCREENLOADETAILS_SUCCESS = "screenload_success";

	private static final String CLASS_NAME = "ScreenLoadCommand";

	/**
	 * @author a-2270
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException{

		Log log = LogFactory.getLogger("MRA_DEFAULTS");
		log.entering(CLASS_NAME, "execute");
		log.log(Log.FINEST,"Inside ScreenLoadCommand of InvoicEnquiryDetailsScreen");
		InvoicEnquiryDetailsForm  form = (InvoicEnquiryDetailsForm)invocationContext.screenModel;
		InvoicEnquiryDetailsSession session = (InvoicEnquiryDetailsSession)getScreenSession(MODULE_NAME, SCREENID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		log.log(Log.FINE,"companyCode..."+companyCode);
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		session.setLocationTransportationDtlsVO(null);
		session.setPaymentPriceDtlsVO(null);
		invocationContext.target = SCREENLOADETAILS_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}

