/*
 * ShowHistoryCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;



import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ShowHistoryCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "show_history_success";
   private static final String TARGET_FAILURE = "show_history_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";	
   private static final String HISTORY_SCREEN_ID = "mailtracking.defaults.mailbaghistory";
   private static final String ENQUIRY_FLAG= "yes";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ShowHistoryCommand","execute");
    	  
    	MailBagEnquiryForm mailBagEnquiryForm = 
    		(MailBagEnquiryForm)invocationContext.screenModel;
    	MailBagEnquirySession mailBagEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	MailBagHistorySession mailBagHistorySession = 
			getScreenSession(MODULE_NAME,HISTORY_SCREEN_ID);
    	    	
    	/*modified by A-8149 for ICRD-248207 begins*/    	
    	Collection<MailbagVO> mailbagVOs = mailBagEnquirySession.getMailbagVOs();
    	ArrayList<Long> mailSequenceNumbers=new ArrayList<Long>();

    
    	String[] selectedRows = mailBagEnquiryForm.getSubCheck(); 
    	if(selectedRows == null || selectedRows.length == 0){
    		mailBagHistorySession.setMailBagId("");
    		mailBagHistorySession.setEnquiryFlag(ENQUIRY_FLAG); //Added by A-8164 for ICRD-260365
        	mailBagEnquiryForm.setStatus("ShowHistoryPopup");
        	invocationContext.target = "show_history_popup";
        	return;
    	}
    	int row = 0;
    	for (MailbagVO mailbagvo : mailbagVOs) {
    		for(int i=0;i<selectedRows.length;i++){
    			if (row == Integer.parseInt(selectedRows[i])) {
		/*
    	 * Mail Captured through Capture Consignemnt,But not Accedpted to the system
    	 * Done for ANZ CR AirNZ1039
    	 */
        			/*if(MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(mailbagvo.getLatestStatus())) {
    		ErrorVO errorVO = new ErrorVO(
    		"mailtracking.defaults.err.capturedbutnotaccepted");	
    		invocationContext.addError(errorVO);			
    		invocationContext.target = TARGET_FAILURE;
    		return;
		}
		Even if status is CAP mailbag details can be shown in view history screen
		Commented as part of ICRD-264958
		*/
        			mailSequenceNumbers.add(mailbagvo.getMailSequenceNumber());
    			}
    		}
    		row++;
    	}
   
    	mailBagHistorySession.setMailSequenceNumber(mailSequenceNumbers);
    	mailBagHistorySession.setEnquiryFlag(ENQUIRY_FLAG); //Added by A-8164 for ICRD-260365
    	mailBagEnquiryForm.setStatus("ShowHistoryPopup"); 
    	
    	/*modified by A-8149 for ICRD-248207 ends*/
        if(mailbagVOs!=null && mailbagVOs.size()>1)	
        	{
        	invocationContext.target = TARGET_SUCCESS;
        	}
        else
        	invocationContext.target = "show_history_popup";
    	log.exiting("ShowHistoryCommand","execute");
    	
    }     
}
