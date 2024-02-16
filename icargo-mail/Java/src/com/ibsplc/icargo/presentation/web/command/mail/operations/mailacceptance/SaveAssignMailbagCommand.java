/*
 * SaveAssignMailbagCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-5991
 *
 */
public class SaveAssignMailbagCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "save_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
	   private static final String CONST_FLIGHT = "FLIGHT";   
	   
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		ReassignMailForm reassignMailForm = 
    		(ReassignMailForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		  try {
			  new MailTrackingDefaultsDelegate().saveAcceptanceDetails(mailAcceptanceVO);
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
  	  }
  	  if (errors != null && errors.size() > 0) {
  		invocationContext.addAllError(errors);
  		mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
  		invocationContext.target = TARGET;
  		return;
  	  }
		reassignMailForm.setCloseFlag("Y");
		invocationContext.target = TARGET;
    	log.exiting("SaveassignMailCommand","execute");
	}
	
}
