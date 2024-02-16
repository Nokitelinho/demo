/*
 * DeleteConsignmentCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class DeleteConsignmentCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.consignment";	
    
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */   
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteConsignmentCommand","execute");
  	  
       ConsignmentForm consignmentForm = (ConsignmentForm)invocationContext.screenModel;
       ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,SCREEN_ID);
       Collection<ErrorVO> errors = null;
       
       ConsignmentDocumentVO consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO(); 
       consignmentDocumentVO.setOperationFlag("D"); 
       Page<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
        if(mailInConsignmentVOs != null && mailInConsignmentVOs.size() != 0) {
        	for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
        		mailInConsignmentVO.setOperationFlag("D"); 
        	}
        }
        consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentVOs);
       
     	log.log(Log.FINE, "Going To Delete ...in command",
				consignmentDocumentVO);
		try {
		    new MailTrackingDefaultsDelegate().deleteConsignmentDocumentDetails(consignmentDocumentVO);
       }catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
 	    }
 	  	if (errors != null && errors.size() > 0) {
 	  		invocationContext.addAllError(errors);
 	  		invocationContext.target = TARGET;
 	  		return;
 	  	}
 	    
 	   ConsignmentDocumentVO conDocumentVO = new ConsignmentDocumentVO();
 	   consignmentSession.setConsignmentDocumentVO(conDocumentVO);
 	   consignmentForm.setConDocNo("");
	   consignmentForm.setPaCode("");
	   consignmentForm.setDirection("");
	   consignmentForm.setDisableListSuccess("");
	   consignmentForm.setDirection("O");
       consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
       invocationContext.target = TARGET;
       	
       log.exiting("DeleteConsignmentCommand","execute");
    	
    }
       
}
