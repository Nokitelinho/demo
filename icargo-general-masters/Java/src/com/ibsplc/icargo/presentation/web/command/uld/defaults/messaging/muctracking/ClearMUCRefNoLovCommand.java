/*
 * ClearMUCRefNoLovCommand.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.muctracking;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class ClearMUCRefNoLovCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MUC Ref No Lov");
	
	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULE = "uld.defaults";

	private static final String BLANK = "";
	
	private static final String SCREENID =
		"uld.defaults.messaging.muctracking";

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MUCTrackingForm mucTrackingForm = (MUCTrackingForm) invocationContext.screenModel;
		MUCTrackingSession mucTrackingSession = getScreenSession(MODULE,SCREENID);
/*added by A-4138 for bug 59916------starts*/
			
		mucTrackingForm.setMucReferenceNum(BLANK);
		mucTrackingForm.setMucLovFilterDate(BLANK);
		//mucTrackingForm.setDisplayPage("1");
		log.log(Log.FINE,"Clearing on progress------------>>>>>>");		
		mucTrackingSession.setLovVO(null);
		//mucTrackingSession.setListDisplayColl(null);
/*ends-----*/
		invocationContext.target=CLEAR_SUCCESS;
	}

}
