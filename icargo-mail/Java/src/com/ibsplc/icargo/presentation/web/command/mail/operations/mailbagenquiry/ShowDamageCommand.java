/*
 * ShowDamageCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DamagedMailBagSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ShowDamageCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET_SUCCESS = "show_damage_success";
	   private static final String TARGET_FAILURE = "show_damage_failure";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";	
	   private static final String DAMAGE_SCREEN_ID = "mailtracking.defaults.damagedmailbag";
	  
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ShowDamageCommand","execute");
	    	  
	    	MailBagEnquiryForm mailBagEnquiryForm = 
	    		(MailBagEnquiryForm)invocationContext.screenModel;
	    	MailBagEnquirySession mailBagEnquirySession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	DamagedMailBagSession damagedMailBagSession = 
				getScreenSession(MODULE_NAME,DAMAGE_SCREEN_ID);
	    	    	
	    	Collection<MailbagVO> mailbagVOs = mailBagEnquirySession.getMailbagVOs();
	    	MailbagVO selectedvo = null;
	    
	    	String[] selectedRows = mailBagEnquiryForm.getSubCheck();    	
	    	int row = 0;
	    	for (MailbagVO mailbagvo : mailbagVOs) {
	    		if (row == Integer.parseInt(selectedRows[0])) {
					selectedvo = mailbagvo;
					break;
				}
	    		row++;
	    	}
	    	log.log(Log.FINE, "selectedvo --------->>", selectedvo);
			/*
	    	 * Mail Captured through Capture Consignemnt,But not Accedpted to the system
	    	 * Done for ANZ CR AirNZ1039
	    	 */
			if(MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(selectedvo.getLatestStatus())) {
	    		ErrorVO errorVO = new ErrorVO(
	    		"mailtracking.defaults.err.capturedbutnotaccepted");	
	    		invocationContext.addError(errorVO);			
	    		invocationContext.target = TARGET_FAILURE;
	    		return;
			}
	    	
	    	damagedMailBagSession.setMailBagId(selectedvo.getMailbagId());
	    	mailBagEnquiryForm.setStatus("ShowDamagePopup");
	        	
	    	invocationContext.target = TARGET_SUCCESS;
	       	
	    	log.exiting("ShowDamageCommand","execute");
	    	
	    }     

}
