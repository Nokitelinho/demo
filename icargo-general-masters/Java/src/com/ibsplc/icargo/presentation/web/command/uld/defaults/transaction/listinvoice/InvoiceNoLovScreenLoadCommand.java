/*
 * InvoiceNoLovScreenLoadCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listinvoice;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class InvoiceNoLovScreenLoadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Invoice Ref No Lov");
	
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.listinvoice";
	private static final String SCREENLOAD_SUCCESS = "lovScreenload_success";
	private static final String SCREENLOAD_FAILURE = "lovScreenload_failure";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ListInvoiceSession listInvoiceSession = (ListInvoiceSession)getScreenSession(MODULE,SCREENID);
		ListInvoiceForm listInvoiceForm = (ListInvoiceForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = null;
		Page<String> lovVOs = new Page<String>(
				new ArrayList<String>(), 0, 0, 0, 0, 0, false);
		
		try {
			String invoiceRefNum = listInvoiceForm.getInvoiceRefNum() != null 
										? listInvoiceForm.getInvoiceRefNum().toUpperCase() : "";
			log.log(Log.FINE, "invoiceRefNum------------>>>>>>", invoiceRefNum);
			log.log(Log.FINE, "Display Page------------>>>>>>", listInvoiceForm.getDisplayLovPage());
			lovVOs = new ULDDefaultsDelegate().findInvoiceRefNumberLov(
					logonAttributes.getCompanyCode(),Integer.parseInt
							(listInvoiceForm.getDisplayLovPage()),invoiceRefNum);
			
		}
		catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
		}
		log.log(Log.FINE,
				"lovVOs before insert into session------------>>>>>>", lovVOs);
		if(lovVOs != null && lovVOs.size()> 0) {
			listInvoiceSession.setLovVO(lovVOs);
		}
		else {
			listInvoiceSession.setLovVO(null);
			ErrorVO error = new ErrorVO("uld.defaults.listuld.norecordsfound");
	     	error.setErrorDisplayType(ErrorDisplayType.STATUS);
	     	errors = new ArrayList<ErrorVO>();
	     	errors.add(error);
	     	invocationContext.addAllError(errors);
		}
		
		log.log(Log.FINE,
				"lovVOs before insert into session------------>>>>>>",
				listInvoiceSession.getLovVO());
		invocationContext.target = SCREENLOAD_SUCCESS;
    }
    
   


}
