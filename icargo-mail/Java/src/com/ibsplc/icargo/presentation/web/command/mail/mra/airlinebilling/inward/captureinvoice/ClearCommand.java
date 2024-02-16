/*
 * ClearCommand.java Created on Aug-05-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureinvoice;
/**
 * @author a-3447
 */
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
 /**
  * @author a-3447
  */
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;




/**
 * Command Class  for InvoiceExceptions  screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Aug 05, 2008   Muralee(a-3447)		  Initial draft
 *  
 */
public class ClearCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	/**
	 * 
	 * Class name
	 */
	private static final String CLASS_NAME = "ClearCommand--";

	/**
	 * Module name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * Screen Id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	/**
	 * clear Success Action
	 */
	private static final String CLEAR_SUCCESS = "clear_success";
	
	/**
	 * Blank valus
	 */
	private static final String BLANK = "";
	
	/**
	 *@author a-3447
	 * Execute method	
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");		
		CaptureMailInvoiceForm captureInvoiceForm = (CaptureMailInvoiceForm) invocationContext.screenModel;
		CaptureInvoiceSession captureInvoiceSession=(CaptureInvoiceSession)getScreenSession(
				MODULE_NAME, SCREEN_ID);
		
		
		/**
		 * Here celar all the form values
		 */
		clearAllValues(captureInvoiceSession,captureInvoiceForm);
		
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	/**
	 * 
	 * @param session
	 * @param form
	 */
	public void clearAllValues(CaptureInvoiceSession session,
			CaptureMailInvoiceForm form){
		/**
		 * session is removed here
		 */
		session.removeAirlineCN51SummaryVO();
		session.removeFilterVo();	
		form.setAirlineCode(BLANK);
		form. setAirlineNo(BLANK);
		form.setInvoiceRefNo(BLANK);
		form.setClearancePeriod(BLANK);
		form.setInvoiceDate(BLANK);
		form.setListingCurrency(BLANK)	;
		form.setNetAmount(BLANK);
		form.setExgRate(BLANK);
		form.setAmountInusd(BLANK);
		form.setInvoiceReceiptDate(BLANK);
		form.setTotalWeight(BLANK);
		form.setInvoiceFormOneStatus(BLANK);
		form.setInvoiceStatus(BLANK);
		form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		form.setNoFormOneCaptured("true");
	}
	
}