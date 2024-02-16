/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer.CloseCommand.java
 *
 *	Created by	:	A-7534
 *	Created on	:	23-Feb-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer.CloseCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7534	:	23-Feb-2018	:	Draft
 */
public class CloseCommand extends BaseCommand{
	
	private static final String CLOSE_SUCCESS = "success";
	private static final String RETURN_TO_WORKFLOW = "return_workflow";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7534 on 23-Feb-2018
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MessageInboxSession messageInboxSession = 
				(MessageInboxSession)getScreenSession("workflow.defaults", "workflow.defaults.messageinbox");
		if(messageInboxSession.getMessageDetails() != null){
			 messageInboxSession.setListStatus("fromAnotherScreen");
			 messageInboxSession.setMessageDetails(null);
			invocationContext.target = RETURN_TO_WORKFLOW;
		}else{
			invocationContext.target = CLOSE_SUCCESS;
		}
	}

}
