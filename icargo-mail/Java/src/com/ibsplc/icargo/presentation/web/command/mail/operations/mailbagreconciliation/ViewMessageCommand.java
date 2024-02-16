/*
 * ViewMessageCommand.java Created on Jul 1 2016
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagreconciliation;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailbagReconciliationSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailbagReconciliationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-3270
 *
 */
public class ViewMessageCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET_SUCCESS = "show_message_success";
	   private static final String TARGET_FAILURE = "show_message_failure";	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.MailbagReconciliation";	
	   private static final String MSGMODULE_NAME = "msgbroker.message";
	   private static final String MSGSCREEN_ID = "msgbroker.message.listmessages";
	  
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ViewMessageCommand","execute");
	    	  
	    	MailbagReconciliationForm form = 
	    		(MailbagReconciliationForm)invocationContext.screenModel;
		    MailbagReconciliationSession session = 
	    		getScreenSession(MODULE_NAME, "mailtracking.defaults.MailbagReconciliation");
		    ListMessageSession msgsession = getScreenSession(MSGMODULE_NAME,
					MSGSCREEN_ID);
		    Page<MailReconciliationDetailsVO> mailReconciliationDetailsVOs = session.getMailReconciliationDetailsVOs();
	    	MailReconciliationDetailsVO selectedvo = null;
	    	Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
	    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
	    		new MailTrackingDefaultsDelegate();
	    	 Page<MessageVO>  messageVOs=null;
	    
	    	String[] selectedRows = form.getRowID();    	
	    	int row = 0;
	    	if(mailReconciliationDetailsVOs!=null){
		    	for (MailReconciliationDetailsVO mailReconciliationDetailsVO : mailReconciliationDetailsVOs) {
		    		if (row == Integer.parseInt(selectedRows[0])) {
		    			selectedvo = mailReconciliationDetailsVO;
						break;
					}
		    		row++;
		    	}
	    	}
	    	log.log(Log.FINE, "selectedvo.getMailbagId() --------->>",
					selectedvo.getMailbagId());
			if(selectedvo.getControlReferenceNumber()!=null && selectedvo.getMsgType()!=null){
	    	try {
	    		messageVOs=mailTrackingDefaultsDelegate
						.findMessageDetails(selectedvo);
			}catch (BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
				
			}
	  	   
	  	    if(messageVOs!=null && messageVOs.size()>0){
	  	    form.setStatus("ShowMessagePopup");
	  	    msgsession.setMessageVO(messageVOs);	   
	  	    invocationContext.target = TARGET_SUCCESS;
	  	    }else{
	  	    	Object[] errorArray = {selectedvo.getMailbagId()};
	  	    	ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.MailbagReconciliation.msgnotfound",errorArray);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
	  	    	
	  	    }
	    	}else{
	    		Object[] errorArray = {selectedvo.getMailbagId()};
	  	    	ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.MailbagReconciliation.msgnotfound",errorArray);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
	    	}
	  	   
	    	log.exiting("ViewMessageCommand","execute");
	    }
}
