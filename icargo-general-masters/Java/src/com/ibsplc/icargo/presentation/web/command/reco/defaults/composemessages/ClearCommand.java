/*
 * ClearCommand.java Created on May 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.composemessages;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.composemessages.RegulatoryComposeMessageSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.RegulatoryComposeMessageForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/** * Command class for Clear Compose message details
*
* @author A-5867
*
*/
public class ClearCommand extends BaseCommand{

	/** Logger for Regulatory Compose Messages Log. */
	private Log log = LogFactory.getLogger("RECO.DEFAULTS");

	/** The Module Name. */
	private static final String MODULE = "reco.defaults";
	
	/** The Constant SCREENID. */
	private static final String SCREENID = "reco.defaults.maintainregulatorycompliance";
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS = "success";

	/**
	 * execute method.
	 *
	 * @param invocationContext the invocation context
	 * @throws CommandInvocationException the command invocation exception
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
 		log.entering("ClearCommand","execute"); 
 		RegulatoryComposeMessageForm composeMessageForm = (RegulatoryComposeMessageForm) invocationContext.screenModel;
		RegulatoryComposeMessageSession session = (RegulatoryComposeMessageSession) getScreenSession(MODULE, SCREENID);
		composeMessageForm.setRoleGroup(null);
    	composeMessageForm.setMessages(null);
    	composeMessageForm.setStartDate(null);
    	composeMessageForm.setStartDates(null);
    	composeMessageForm.setEndDate(null);
    	composeMessageForm.setEndDates(null);
    	composeMessageForm.setRoleGroups(null);
    	composeMessageForm.setSerialNumbers(null);
    	session.removeRegulatoryMessages();
		session.removeRegulatoryMessageErrorList();
		invocationContext.target = SUCCESS;
		log.exiting("ClearCommand","execute");
	}

}
