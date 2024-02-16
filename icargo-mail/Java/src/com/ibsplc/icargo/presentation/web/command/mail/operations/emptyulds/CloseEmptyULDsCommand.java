/*
 * CloseEmptyULDsCommand.java Created on July 1 2016
 * 
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.emptyulds;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.EmptyULDsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class CloseEmptyULDsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS,UnAssignEmptyULDs");

	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS = "success";

	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.emptyulds";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("UnAssignEmptyULDs","execute");

		EmptyULDsForm emptyULDsForm = (EmptyULDsForm)invocationContext.screenModel;
		EmptyULDsSession emptyULDsSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		
		emptyULDsSession.setContainerDetailsVOs(null);
		emptyULDsForm.setStatus("CLOSE");
		invocationContext.target = TARGET_SUCCESS;
	}
}
