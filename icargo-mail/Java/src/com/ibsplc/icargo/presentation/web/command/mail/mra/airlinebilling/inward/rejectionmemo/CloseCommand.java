/*
 * CloseCommand.java Created on May 23, 2007
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
public class CloseCommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory
			.getLogger("MRA AIRLINEBILLING INWARD REJECTION MEMO");

	private static final String CLASS_NAME = "CloseCommand";

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

	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CAPTURE_INVOICE="captureinvoice";
	private static final String CLOSE_CAPTURE_INVOICE="close_capture_inv";
	private static final String CAPTURE_FORM_ONE="captureformone";
	private static final String CLOSE_FORM_ONE="close_form1_success";  
	private static final String  LISTEXCEPTION_DETAILS="airlineexceptions";
	private static final String CLOSE_EXCEPTIONDETAILS="close_exceptiondetails";
	private static final String  LISTEXCEPTION_DTLS="listexception";


	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		RejectionMemoForm rejectionMemoForm = (RejectionMemoForm) invocationContext.screenModel;
		RejectionMemoSession session = (RejectionMemoSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		session.removeAllAttributes();
		String screen = rejectionMemoForm.getInvokingScreen();	
		if(LISTEXCEPTION_DETAILS.equals(screen)||LISTEXCEPTION_DTLS.equals(screen)){
			invocationContext.target = CLOSE_EXCEPTIONDETAILS;
		}
		else if (CAPTURE_INVOICE.equals(screen)) {
			log.log(Log.INFO, "-->", screen);
			invocationContext.target = CLOSE_CAPTURE_INVOICE;
		}
		else if (CAPTURE_FORM_ONE.equals(screen)) {
			log.log(Log.INFO, "-->", screen);
			invocationContext.target = CLOSE_FORM_ONE;
		}
		
		else if (!CAPTURE_INVOICE.equals(screen)) {
		invocationContext.target = CLOSE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		}
	}
}
