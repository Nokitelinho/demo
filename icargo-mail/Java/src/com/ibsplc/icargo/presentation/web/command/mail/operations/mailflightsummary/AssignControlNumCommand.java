/*
 * AssignControlNumCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailflightsummary;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailInFlightSummaryVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailFlightSummarySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailFlightSummaryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AssignControlNumCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailflightsummary";	  
	
   private static final String TARGET_SUCCESS = "assign_success";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AssignControlNumCommand","execute");
    	
    	MailFlightSummaryForm mailFlightSummaryForm = 
    		(MailFlightSummaryForm)invocationContext.screenModel;
    	MailFlightSummarySession mailFlightSummarySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID); 
    	Collection<ErrorVO> errors = null;
    	if (mailFlightSummaryForm.getControlNum() == null || mailFlightSummaryForm.getControlNum().trim().length()==0) {
			ErrorVO error =
				new ErrorVO("mailtracking.defaults.mailsummary.msg.err.ctrldocmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			mailFlightSummaryForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_DETAIL);
			invocationContext.target = TARGET_SUCCESS;
			return;
		}
    	
    	log.log(Log.FINE, "getSelectedMails--------->>", mailFlightSummaryForm.getSelectedMails());
		ArrayList<MailInFlightSummaryVO> mailInFlightSummaryVOs = (ArrayList<MailInFlightSummaryVO>)mailFlightSummarySession.getSummaryVOs();
    	ArrayList<MailInFlightSummaryVO> assignSummaryVOs = new ArrayList<MailInFlightSummaryVO>();
    	String[] selected=mailFlightSummaryForm.getSelectedMails().split(",");
		for(int i=0;i<selected.length;i++){
			assignSummaryVOs.add(mailInFlightSummaryVOs.get(Integer.parseInt(selected[i])));
			
		}
		log.log(Log.FINE, "assignSummaryVOs------>>", assignSummaryVOs);
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate(); 
		try {
			
			mailTrackingDefaultsDelegate.assignControlDocumentNumber(assignSummaryVOs,mailFlightSummaryForm.getControlNum());

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		mailFlightSummaryForm.setPopupStatus("assignsuccess");
    	mailFlightSummaryForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET_SUCCESS;
    	log.exiting("AssignControlNumCommand","execute");
    	
    }}
    
   
   
    
  
	

	
	
       

