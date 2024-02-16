/*
 * ScreenLoadCommand.java Created on Jan 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.generateinvoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GenerateInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GenerateGPABillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Jun 20, 2007   Kiran S P 				Initial draft , Added method execute
 */
public class ScreenLoadCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.generateinvoice";

	private static final String KEY_INVOICE_TYPE_ONETIME = "mailtracking.mra.gpabilling.invoiceType";

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
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		Collection<String> oneTimeInvoiceTypeList 		= new ArrayList<String>();
    	GenerateGPABillingInvoiceForm generateInvoiceForm =
    		(GenerateGPABillingInvoiceForm)invocationContext.screenModel;
    	GenerateInvoiceSession session=null;
    	session=(GenerateInvoiceSession) getScreenSession(MODULE_NAME,SCREENID);
    	
    	Map oneTimeHashMap 	= null;
    	oneTimeInvoiceTypeList.add(KEY_INVOICE_TYPE_ONETIME);
    	try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeInvoiceTypeList);
		} catch (BusinessDelegateException e) {
    		e.getMessage();
		}
    	session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
    	
		generateInvoiceForm.setScreenSuccessFlag("SCREENLOAD_SUCCESS");
		invocationContext.target = SCREEN_SUCCESS;
		log.entering(CLASS_NAME,"execute");
	}
}
