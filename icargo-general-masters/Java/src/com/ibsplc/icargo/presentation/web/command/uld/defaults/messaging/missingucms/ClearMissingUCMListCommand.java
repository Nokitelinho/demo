/*
 * ClearMissingUCMListCommand.java Created on Jul 30, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.missingucms;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MissingUCMListSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MissingUCMListForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class ClearMissingUCMListCommand extends BaseCommand {
	
	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.missingucmlist";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ScreenLoadMissingUCMList");
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
		log.entering("ScreenLoadMissingUCMList", "execute");
		MissingUCMListSession missingUCMListSession = (MissingUCMListSession)getScreenSession(MODULE_NAME, SCREEN_ID);
	    MissingUCMListForm missingUCMListForm = (MissingUCMListForm)invocationContext.screenModel;
	    missingUCMListForm.setCarrierCode(BLANK);
	    missingUCMListForm.setFlightDate(BLANK);
	    missingUCMListForm.setFlightNumber(BLANK);
	    missingUCMListForm.setOrigin(BLANK);
	    missingUCMListForm.setDestination(BLANK);
	    missingUCMListForm.setFromDate(BLANK);
	    missingUCMListForm.setToDate(BLANK);
	    missingUCMListForm.setUcmIn(null);
	    missingUCMListForm.setUcmOut(null);
	    missingUCMListSession.removeULDFlightMessageReconcileDetailsVOs();
	    missingUCMListSession.removeAllAttributes();
	    invocationContext.target = CLEAR_SUCCESS;
	    
	
	}


}
