/*
 * ScreenLoadCommand.java Created on Aug 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.invoicclaimsenquiry;


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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicClaimsEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicClaimsEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

    private static final String SCREENID ="mailtracking.mra.defaults.invoicclaimsenquiry";

	private static final String SCREENLOADETAILS_SUCCESS = "screenload_success";
	
	private static final String CLAIMTYPE_ONETIME = "mailtracking.mra.defaults.claimtype";
	
	private static final String CLAIMSTATUS_ONETIME = "mailtracking.mra.defaults.claimstatus";

	private static final String CLASS_NAME = "ScreenLoadCommand";
	
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
		log.log(Log.FINE,"Inside ScreenLoadCommand of InvoicClaimsEnquiry");
		
		InvoicClaimsEnquiryForm form = (InvoicClaimsEnquiryForm)invocationContext.screenModel;
		InvoicClaimsEnquirySession session = (InvoicClaimsEnquirySession)getScreenSession(MODULE_NAME,SCREENID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		/**
		 * for taking one times
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {

			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());

		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		session.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		log.log(Log.FINE,"companyCode (InvoicClaimsEnquiry Screen)..."+companyCode);
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		session.setMailInvoicClaimsEnquiryVOs(null);
		form.setGpaCode(BLANK);
		form.setFromDate(null);
		form.setToDate(BLANK);
		invocationContext.target = SCREENLOADETAILS_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
    
   
	
	/**
	 * 
	 * @return Collection<String>
	 */
	private Collection<String> getOneTimeParameterTypes() {

		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(CLAIMTYPE_ONETIME);
		parameterTypes.add(CLAIMSTATUS_ONETIME);
		return parameterTypes;
	}
    
    
}

