/*
 * CloseCommand.java Created on Aug-5-2008
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
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3447
 * 
 */
public class CloseCommand extends BaseCommand {
	/**
	 * Logger for the class
	 */
	private Log log = LogFactory.getLogger("--CloseCommand--");

	/**
	 * 
	 * Class name
	 */
	private static final String CLASS_NAME = "CloseCommand--";

	/**
	 * Module name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * Screen Id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	/**
	 * close Success Action
	 */
	private static final String CLOSE_SUCCESS = "close_home_success";
	/**
	 * invoking Screen
	 */
	private static final String INVOKE_SCREEN = "captureformone";
	
	/**
	 * close Success Action
	 */
	private static final String CLOSE_FORM1_SUCCESS = "close_form1_success";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		CaptureInvoiceSession captureInvoiceSession = (CaptureInvoiceSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		/**
		 * session is removed here
		 */
		CaptureMailInvoiceForm captureInvoiceForm = (CaptureMailInvoiceForm) invocationContext.screenModel;
		String screen = captureInvoiceForm.getInvokingScreen();
		log.log(Log.INFO, "selected screen-->", screen);
		//captureInvoiceSession.removeAllAttributes();
		captureInvoiceSession.removeAirlineCN51SummaryVO();
		captureInvoiceSession.removeFilterVo();
		if(INVOKE_SCREEN.equals(screen)&& screen!=null){
            log.log(Log.INFO, "selected CLOSE _SUCCESS-->", screen);
			invocationContext.target = CLOSE_FORM1_SUCCESS;
    	}
		else{
		captureInvoiceSession.removeAirlineCN51SummaryVO();
		invocationContext.target = CLOSE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		}
	}

}
