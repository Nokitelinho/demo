/*
 * ClearCommand.java Created on Feb 01 , 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.operations.national.pomailstatement;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.POMailStatementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.POMailStatementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4777
 *
 */
public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING");	
	  
	   private static final String TARGET_SUCCESS = "clear_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.national.mailstatement";	
	   
	   public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		   
		   log.entering("ClearCommand","execute");
		   POMailStatementForm poMailStatementForm =(POMailStatementForm)invocationContext.screenModel;
		   POMailStatementSession poMailStatementSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		   ApplicationSessionImpl applicationSession = getApplicationSession();
		   LogonAttributes logonAttributes = applicationSession.getLogonVO();
		   //modified by A-4810 for icrd-15155
		   poMailStatementForm.setCategory("");
		   poMailStatementForm.setFromDate("");
		   poMailStatementForm.setToDate("");
		   poMailStatementForm.setConsignmentNo("");
		   poMailStatementForm.setOrigin("");
		   poMailStatementForm.setDestination("");
		   poMailStatementForm.setFirstList("");
		   
	   poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	   
	   poMailStatementSession.setSelectedPOMailStatementVOs(null);
	   poMailStatementSession.setIndexMap(null);
	   invocationContext.target = TARGET_SUCCESS;
      	
   	   log.exiting("ClearCommand","execute");
	   }
}
