/*
 * CloseCommand.java Created on July 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformthree;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormThreeSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 *
 */
public class CloseCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CaptureForm3 CloseCommand");

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureformthree";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "close_success";
	
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
    	CaptureFormThreeSession captureFormThreeSession = (CaptureFormThreeSession)
		getScreenSession(MODULE_NAME, SCREEN_ID);
    	CaptureMailFormThreeForm captureFormThreeForm=(CaptureMailFormThreeForm)invocationContext.screenModel;
    	String screen = captureFormThreeForm.getInvokingScreen();
		log.log(Log.INFO, "selected screen-->", screen);
		captureFormThreeSession.removeAllAttributes();    	
    	if(INVOKE_SCREEN.equals(screen)&& screen!=null){
            log.log(Log.INFO, "selected CLOSE _SUCCESS-->", screen);
			invocationContext.target = CLOSE_FORM1_SUCCESS;
    	}
    	else{
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    	}
    }
}
