/*
 * DeleteMailCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 * 
 */

public class DeleteMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   private static final String TARGET_SUCCESS = "delete_success";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";	   
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteMailCommand","execute");
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	MailBagEnquiryForm  mailBagEnquiryForm= 
    		(MailBagEnquiryForm)invocationContext.screenModel;
       	MailBagEnquirySession mailBagEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
       	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();   	
    	Collection<MailbagVO> selectedMailbagVOs = new ArrayList<MailbagVO>();
    	Collection<MailbagVO> mailbagVOs = mailBagEnquirySession.getMailbagVOs();
    	ArrayList<MailbagVO> mailbagArray = null;
        MailbagVO mailbag=null;
    	if(mailbagVOs!=null  && mailbagVOs.size()>0){
    		 mailbagArray= new ArrayList<MailbagVO>(mailbagVOs);
    	}
//        log.log(Log.FINE,"\n\n reassignMailForm.getContainer()-->"+mailBagEnquiryForm.getSubCheck());
	     String childStrs=mailBagEnquiryForm.getSelCont();
	   
	   if(childStrs!= null && childStrs.trim().length()>0){
		    String[] childKey = childStrs.split(",");
		     for(int i=0;i<childKey.length;i++)
		      {  
		    	 mailbag=mailbagArray.get(Integer.parseInt(childKey[i]));
    	     	 if(mailbag.getFlightSequenceNumber() > 0){   
	    			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.cannotdeleteflightassignedmailbag");
	    			invocationContext.addError(errorVO);
	    			invocationContext.target = TARGET_SUCCESS;
	    			return;
	    		 }
		    	 mailbag.setCompanyCode(logonAttributes.getCompanyCode());
			     mailbag.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			     mailbag.setScannedUser(logonAttributes.getUserId());
			     mailbag.setCarrierCode(mailBagEnquiryForm.getFlightCarrierCode());
			     mailbag.setScannedPort(logonAttributes.getAirportCode());
			     selectedMailbagVOs.add(mailbag);
			    	}
		    	 }
 
	    	 try {		    
		 log.log(Log.FINE, "THE SELECTED MAIL BAGS FROM ENQUIRY",
				selectedMailbagVOs);
		new MailTrackingDefaultsDelegate().deleteMailsfromInventory(selectedMailbagVOs);		  
      }catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
	  }
      
      mailBagEnquiryForm.setReList(FLAG_YES);
      
      invocationContext.target = TARGET_SUCCESS;
      log.exiting("DeleteMailCommand","execute");    	
    }       
}
