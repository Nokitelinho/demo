/*
 * ClearCommand.java Created on Nov 22,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturerejectionmemo;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for clearing capture rejection memo screen 
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Nov 22,2007   Ruby Abraham		Initial draft
 */
public class ClearCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	
	private static final String CLASS_NAME = "ClearCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";
	
	private static final String CLEAR_SUCCESS = "clear_success";
	

	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		CaptureRejectionMemoSession  captureRejectionMemoSession = 
			(CaptureRejectionMemoSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		CaptureRejectionMemoForm captureRejectionMemoForm=
			(CaptureRejectionMemoForm)invocationContext.screenModel;
		
		captureRejectionMemoSession.removeMemoFilterVO();
		captureRejectionMemoSession.removeMemoInInvoiceVOs();
		captureRejectionMemoForm.setClearancePeriod("");
		captureRejectionMemoForm.setAirlineCode("");
		captureRejectionMemoForm.setInvoiceNo("");
		captureRejectionMemoForm.setInterlineBillingType("");
		captureRejectionMemoForm.setSelectAll(false);
		captureRejectionMemoForm.setEditFormOneFlag("");
		captureRejectionMemoForm.setStatusFlag("screenload");
		captureRejectionMemoForm.setLinkStatusFlag("disable");
		captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		
		log.log(Log.FINE, "MemoFilterVO", captureRejectionMemoSession.getMemoFilterVO());
		log.log(Log.FINE, "MemoInInvoiceVOs", captureRejectionMemoSession.getMemoInInvoiceVOs());
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
