/*
 * ClearCommand.java Created on Jan 22, 2007
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
 */
public class ClearCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewupurate";

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String BLANK = "";
	/**
	 * Method  implementing clearing of screen
	 * @author a-2270
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListUPURateLineForm listUPURateLineForm =
    		  (ListUPURateLineForm)invocationContext.screenModel;
    	ListUPURateLineSession listUPURateLineSession =
    		            (ListUPURateLineSession)getScreenSession(MODULE_NAME, SCREENID);


    	// clearing vos in session
    	listUPURateLineSession.removeRateLineVOs();
    	listUPURateLineSession.removeRateLineFilterVO();

    	// clearing form fields
    	listUPURateLineForm.setRateCardID(BLANK);
    	listUPURateLineForm.setOrigin(BLANK);
    	listUPURateLineForm.setDestination(BLANK);
    	listUPURateLineForm.setStatus(BLANK);
    	listUPURateLineForm.setFromDate(BLANK);
    	listUPURateLineForm.setToDate(BLANK);
    	listUPURateLineForm.setOrgDstLevel(BLANK);
    	invocationContext.target = CLEAR_SUCCESS;

    }

}

