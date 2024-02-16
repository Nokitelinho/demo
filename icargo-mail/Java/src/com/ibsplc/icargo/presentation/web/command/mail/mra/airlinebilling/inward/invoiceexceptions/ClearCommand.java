/*
 * ClearCommand.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.invoiceexceptions;


import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Sreekanth.V.G
 * Command Class  for InvoiceExceptions  screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 20, 2007   Sreekanth.V.G   			Initial draft
 *  
 */
public class ClearCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	private static final String CLASS_NAME = "ClearCommand";
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.invoiceexceptions";
	/**
	 * SCREENLOAD_SUCCESS Action
	 */
	private static final String CLEAR_SUCCESS = "clear_success";
	
	/**
	 * Blank valus
	 */
	private static final String BLANK = "";
	
	/**
	 *
	 * Execute method	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		InvoiceExceptionsForm form =
			(InvoiceExceptionsForm)invocationContext.screenModel;
		InvoiceExceptionsSession  session=	(InvoiceExceptionsSession)getScreenSession(
				MODULE_NAME, SCREENID);
		
		/**
		 * Here celar all the form values
		 */
		clearAllValues(session,form);
		
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	/**
	 * 
	 * @param session
	 * @param form
	 */
	public void clearAllValues(InvoiceExceptionsSession session,
			InvoiceExceptionsForm form){
		/**
		 * session is removed here
		 */
		session.removeExceptionInInvoiceVOs();
		session.removeExceptionInInvoiceFilterVO();
		session.setExceptionInInvoiceFilterVO(null);
		
		form.setAirlineCode(BLANK);
		form.setClearencePeriod(BLANK);
		form.setExceptionStatus(BLANK);
		form.setInvoiceNumber(BLANK);
		form.setRejectionMemoNumber(BLANK);
		form.setContractCurrency(BLANK);
		form.setMemoStatus(BLANK);	
		form.setFromScreenFlag(BLANK);
		form.setDisplayPage("1");
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	}
}