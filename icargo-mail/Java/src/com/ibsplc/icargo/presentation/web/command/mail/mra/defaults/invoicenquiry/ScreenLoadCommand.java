/*
 * ScreenLoadCommand.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.invoicenquiry;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

    private static final String SCREENID ="mailtracking.mra.defaults.invoicenquiry";

	private static final String SCREENLOADETAILS_SUCCESS = "screenload_success";

	private static final String CLASS_NAME = "ScreenLoadCommand";
	
	private static final String KEY_RATE_TYP_IND="mailtracking.mra.defaults.ratetypeindicator";
	
	private static final String KEY_PAYMENT_TYPE = "mailtracking.mra.defaults.invoicpaymenttype";
	
	private static final String BLANK = "";

	//private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException{

		Log log = LogFactory.getLogger("MRA_DEFAULTS");
		log.entering(CLASS_NAME, "execute");
		log.log(Log.FINEST,"Inside ScreenLoadCommand of InvoicEnquiryScreen");
		InvoicEnquiryForm  form = (InvoicEnquiryForm)invocationContext.screenModel;
		InvoicEnquirySession session = (InvoicEnquirySession)getScreenSession(MODULE_NAME, SCREENID);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		log.log(Log.FINE,"companyCode..."+companyCode);
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		session.setMailInvoicEnquiryDetailsVOs(null);
		session.setMailInvoicEnquirySummaryVO(null);
		session.removeAllAttributes();
		form.setPoaCode(BLANK);
		form.setSchInvoiceDate(null);
		form.setCarrierCode(BLANK);
		form.setCarrierName(BLANK);
		form.setControlValue(BLANK);
		form.setTotalAdjAmt(BLANK);
		form.setPaymentType(BLANK);
		form.setReconcilStatus(BLANK);
		Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_RATE_TYP_IND);
		oneTimeActiveStatusList.add(KEY_PAYMENT_TYPE);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		if(errors!=null && errors.size()>0){
	    	invocationContext.addAllError(errors);
	    	}
		
		invocationContext.target = SCREENLOADETAILS_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}

