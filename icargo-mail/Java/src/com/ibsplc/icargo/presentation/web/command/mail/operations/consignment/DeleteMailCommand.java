/*
 * DeleteMailCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class DeleteMailCommand extends BaseCommand {
	
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
    	
    	log.entering("DeleteMailCommand","execute");
  	  
    	ConsignmentForm consignmentForm = 
    		(ConsignmentForm)invocationContext.screenModel;
    	ConsignmentSession consignmentSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	/*Commented by (A-3217) since it is not is used 
    	anywhere in the code except the declaration*/
    	//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	String[] primaryKeyArr = consignmentForm.getSelectMail();
    	int primaryKeyLen = primaryKeyArr.length;
    	ConsignmentDocumentVO consignmentDocumentVO =consignmentSession.getConsignmentDocumentVO();
    	Collection<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
    	
    	int count = 0;
    	int cnt = 0;
    	Page<MailInConsignmentVO> newMailVOs = new Page<MailInConsignmentVO>(
					new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0,false);
    	 if(mailInConsignmentVOs != null && mailInConsignmentVOs.size() != 0) {
         	for (MailInConsignmentVO mailVO : mailInConsignmentVOs) {
         		String primaryKeyFromVO = String.valueOf(count);
         		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
         				          equalsIgnoreCase(primaryKeyArr[cnt].trim())) {
         			if(!"I".equals(mailVO.getOperationFlag())){
         				mailVO.setOperationFlag("D");
         				newMailVOs.add(mailVO);
         			}
         			cnt++;	
         		}else{
         			newMailVOs.add(mailVO);
         		}
         		count++;
         	}
    	 }
       
       log.log(Log.FINE, "delete...MailConsignmentVOs...command", newMailVOs);
	consignmentDocumentVO.setMailInConsignmentVOs(newMailVOs);	
       consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);	 
       consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
       invocationContext.target = TARGET;
       	
       log.exiting("DeleteMailCommand","execute");
    	
    }
       
}
