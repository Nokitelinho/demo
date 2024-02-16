
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.consignment.PrintMailTagScreenLoadCommand.java
 *
 *	Created by	:	A-7871
 *	Created on	:	06-Feb-2018
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class PrintMailTagScreenLoadCommand extends BaseCommand{


	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.consignment";	
	   private static final String TARGET = "success";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("PrintMailTagScreenLoadCommand","execute");
	ConsignmentForm consignmentForm = (ConsignmentForm)invocationContext.screenModel;
		ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		consignmentSession.setSelectedIndexes(consignmentForm.getSelectedIndexes());
		
		 invocationContext.target = TARGET;
	        log.exiting("PrintMailTagScreenLoadCommand","execute");
	}
	

}
