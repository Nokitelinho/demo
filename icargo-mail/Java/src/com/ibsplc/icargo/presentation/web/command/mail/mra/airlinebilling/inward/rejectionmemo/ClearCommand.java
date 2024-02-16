/*
 * ClearCommand.java Created on May 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.rejectionmemo;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ClearCommand extends BaseCommand {
	
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD REJECTION MEMO");
	private static final String CLASS_NAME = "ClearCommand";
	
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";
	
	
	private static final String CLEAR_SUCCESS = "clear_success";
	
	private static final String BLANK="";
	
	
	
	
	/**
	 *
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		RejectionMemoForm rejectionMemoForm = 
			(RejectionMemoForm)invocationContext.screenModel;
		
		RejectionMemoSession session = 
			(RejectionMemoSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		session.removeRejectionMemoVO();
		rejectionMemoForm.setMemoCode(BLANK);
		rejectionMemoForm.setInvoiceNumber(BLANK);
		rejectionMemoForm.setAirlineCode(BLANK);
		rejectionMemoForm.setYourInvoiceNumber(BLANK);
		rejectionMemoForm.setYourInvoiceDate(BLANK);
		rejectionMemoForm.setYourReferenceNumber(BLANK);
		rejectionMemoForm.setMonthOfClearance(BLANK);
		rejectionMemoForm.setMonthOfTransaction(BLANK);
		
		rejectionMemoForm.setChargeNotConvertedToContractIndicator(BLANK);
		rejectionMemoForm.setChargeNotCoveredByContractIndicator(BLANK);
		rejectionMemoForm.setIncorrectExchangeRateIndicator(BLANK);
		rejectionMemoForm.setDuplicateBillingIndicator(BLANK);
		rejectionMemoForm.setNoApprovalIndicator(BLANK);
		rejectionMemoForm.setNoReceiptIndicator(BLANK);
		rejectionMemoForm.setOtherIndicator(BLANK);
		rejectionMemoForm.setRequestAuthorisationIndicator(BLANK);
		rejectionMemoForm.setOutTimeLimitsForBillingIndicator(BLANK); 
		rejectionMemoForm.setDuplicateBillingInvoiceDate(BLANK);
		rejectionMemoForm.setDuplicateBillingInvoiceNumber(BLANK);
		rejectionMemoForm.setRequestAuthorisationReference(BLANK);
		rejectionMemoForm.setRequestAuthorisationDate(BLANK);
		rejectionMemoForm.setDsn(BLANK);
		rejectionMemoForm.setRemarks(BLANK);
		rejectionMemoForm.setInvokingScreen(BLANK);
		rejectionMemoForm.setScreenFlag("screenload");
		
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	
}
