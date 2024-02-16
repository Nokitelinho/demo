/*
 * LookupOkConsignmentSearchCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/*
 * @author
 * LookupOkConsignmentSearchCommand
 * extends BaseCommand
 */
public class LookupOkConsignmentSearchCommand extends BaseCommand{
	
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
	   
	   
	   private static final String TARGET = "save_success";
	   
	   
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.entering("LookupOkSearchConsgnCommand","execute");
		 CarditEnquiryForm carditEnquiryForm=(CarditEnquiryForm)invocationContext.screenModel;
		 MailAcceptanceSession mailAcceptanceSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	mailAcceptanceSession.setConsgnValues(carditEnquiryForm.getConsignmentDocument());
	    	mailAcceptanceSession.setPaoValues(carditEnquiryForm.getPao());
		 
		 invocationContext.target = TARGET;
		 log.exiting("searchconsgnLookupOkCommandexit","execute");
	 }

}
