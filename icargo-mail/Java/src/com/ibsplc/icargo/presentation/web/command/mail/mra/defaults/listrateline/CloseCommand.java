/*
 * CloseCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateline;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateLineForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 *
 */
public class CloseCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewupurate";

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String INVOKE_SCREEN = "listupuratecard";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLOSE_RATECARD_SUCCESS = "closeratecard";

	private static final String CLOSE_FAILURE = "close_failure";

	/**
	 * Method  implementing closing of screen
	 * @author a-2270
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListUPURateLineForm form = (ListUPURateLineForm)invocationContext.screenModel;

		ListUPURateLineSession session = (ListUPURateLineSession)getScreenSession(MODULE, SCREENID);

		String screen = form.getInvokingScreen();

    	// clearing vos in session
		session.removeAllAttributes();

    	if(INVOKE_SCREEN.equals(screen)){

    		log.log(Log.INFO, "selected CLOSE_RATECARD_SUCCESS-->", screen);
			invocationContext.target = CLOSE_RATECARD_SUCCESS;
    	}else{

    		log.log(Log.INFO, "selected CLOSE_SUCCESS-->", screen);
			invocationContext.target = CLOSE_SUCCESS;
    	}


    }

}
