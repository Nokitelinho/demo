/*
 * SendResditsScreenloadCommand.java Created on Jul 1 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.AutoForwardDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.CarditEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ux.sendmessage.SendMessageSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class SendResditOkCommand extends BaseCommand {
	
   private static final Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");
	
  private static final String MODULE_NAME = "mail.operations";
 private static final String SCREEN_ID = "mail.operations.ux.carditenquiry";	  
   private static final String TARGET = "screenload_success";
	  public static final String CONST_MESSAGE_INFO_SEND_SUCCESS = "mailtracking.defaults.carditenquiry.msg.info.sendsuccessfully";

	 /**
	 * This method overrides the execute method of BaseComand class
	 * 
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
 	LOGGER.entering("SendResditOkCommand","execute");
   
		SendMessageSession sendMessageSession = (SendMessageSession) getScreenSession("msgbroker.message",
				"msgbroker.message.ux.sendmessage");
    	CarditEnquirySession carditEnquirySession=(CarditEnquirySession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	Collection<MessageDespatchDetailsVO>	messageAddressDetails= sendMessageSession.getDespatchDetails();
    	
    	Collection<AutoForwardDetailsVO> participantDetails= sendMessageSession.getAutoForwardDetails();
    	MessageVO messageVo = sendMessageSession.getMessage();
    	
    	String selectedResditVersion=carditEnquirySession.getSelectedResditVersion();
		List<String> selectedResdits=new ArrayList<>();
		if (carditEnquirySession.getSelectedResdits() != null) {
			selectedResdits = carditEnquirySession.getSelectedResdits();
		}
    	Collection<MailbagVO> selectedMailbags=carditEnquirySession.getSelectedMailbags();
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
    	try {
    		
			mailTrackingDefaultsDelegate.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo,
					selectedResditVersion, selectedResdits, selectedMailbags);
		} catch(BusinessDelegateException exception) {
			invocationContext.addAllError(handleDelegateException(exception));
			
			return;
		}
		ErrorVO error = new ErrorVO(CONST_MESSAGE_INFO_SEND_SUCCESS);
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		invocationContext.addError(error);
		invocationContext.target = TARGET;
    	LOGGER.exiting("SendResditOkCommand","execute");
    
    	
    }
    
    
}
