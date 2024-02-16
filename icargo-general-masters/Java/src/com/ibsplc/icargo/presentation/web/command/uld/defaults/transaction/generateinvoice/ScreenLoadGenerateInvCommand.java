/*
 * ScreenLoadGenerateInvCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.generateinvoice;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateInvoiceForm;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-1496
 *
 */
public class ScreenLoadGenerateInvCommand  extends BaseCommand {
	
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
   
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	GenerateInvoiceForm generateInvoiceForm = (GenerateInvoiceForm)invocationContext.screenModel;
		LocalDate currentdate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
		generateInvoiceForm.setInvoicedDate(
				TimeConvertor.toStringFormat(
						currentdate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT));
		invocationContext.target = SCREENLOAD_SUCCESS;
    }
  
}
