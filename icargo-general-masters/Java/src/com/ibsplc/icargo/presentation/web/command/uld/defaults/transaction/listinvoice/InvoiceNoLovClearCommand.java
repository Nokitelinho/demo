/* InvoiceNoLovClearCommand.java Created on May 10,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listinvoice;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListInvoiceForm;

/**
 * 
 * @author A-1819
 * 
 */
public class InvoiceNoLovClearCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULE = "uld.defaults";

	private static final String BLANK = "";
	
	private static final String SCREENID =
		"uld.defaults.listinvoice";

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
			ListInvoiceSession listInvoiceSession = (ListInvoiceSession)getScreenSession(MODULE,SCREENID);
		ListInvoiceForm listInvoiceForm = (ListInvoiceForm)invocationContext.screenModel;		
		listInvoiceForm.setInvoiceRefNum(BLANK);
		listInvoiceForm.setDisplayPage("1");
		listInvoiceSession.setLovVO(null);
		listInvoiceSession.setULDChargingInvoiceVO(null);
		invocationContext.target=CLEAR_SUCCESS;
	}
}
