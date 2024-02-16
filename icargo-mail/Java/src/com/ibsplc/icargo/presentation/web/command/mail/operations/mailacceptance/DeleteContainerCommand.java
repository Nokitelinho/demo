/*
 * DeleteContainerCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class DeleteContainerCommand extends BaseCommand  {
	
	@Override
	public boolean breakOnInvocationFailure() {		
		return true;
	}
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "delete_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteContainerCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		Collection<ContainerDetailsVO> contDetailsVOs = mailAcceptanceVO.getContainerDetails();
		String[] selected = mailAcceptanceForm.getSelectMail();
		String selectedMails = selected[0];
		String[] primaryKey = selectedMails.split(",");
	   int cnt=0;
	   int count = 1;
       int primaryKeyLen = primaryKey.length;
       Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
       int errorFlag = 0;
       String contName = "";
       if (contDetailsVOs != null && contDetailsVOs.size() != 0) {
       	for (ContainerDetailsVO contDetailsVO : contDetailsVOs) {
       		String primaryKeyFromVO = contDetailsVO.getCompanyCode()
       				+String.valueOf(count);
       		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
       				          equalsIgnoreCase(primaryKey[cnt].trim())) {
       			if(!"I".equals(contDetailsVO.getContainerOperationFlag())){
       				containerDetailsVOs.add(contDetailsVO);	
       				errorFlag = 1;
       				if("".equals(contName)){
       					contName = contDetailsVO.getContainerNumber();
	       			}else{
	       				contName = new StringBuilder(contName).append(",")
   					                  .append(contDetailsVO.getContainerNumber())
   					                  .toString();	
	       			}
       			}
       			cnt++;
       		}else{
       			containerDetailsVOs.add(contDetailsVO);	
       		}
       		count++;
       	  }
       	}
       
       if(errorFlag == 1){
  	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.cannotdeletecontainers",new Object[]{contName}));
       }
       
       mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
       mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
       mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	  invocationContext.target = TARGET;
      log.exiting("DeleteContainerCommand","execute");
    	
   }
    
}
