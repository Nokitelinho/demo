/*
 * AcceptMailCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 * 
 */
public class CloseMailAcceptanceCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("CloseMailAcceptanceCommand","execute");
	    	log.entering("CloseMailAcceptanceCommand","execute");
	    	  
	    	MailAcceptanceForm mailAcceptanceForm = 
	    		(MailAcceptanceForm)invocationContext.screenModel;
	    	MailAcceptanceSession mailAcceptanceSession =
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
	    	Collection<ContainerDetailsVO> newContainerDetailsVOs = mailAcceptanceSession.getContainerDetailsVOs();
			Collection<ContainerDetailsVO> contDetailsVOs = mailAcceptanceVO.getContainerDetails();
			log.log(Log.FINE, "contDetailsVOs ...in mainscreen", contDetailsVOs);
			//Added by A-4810 for icrd-90664
			Collection<ContainerDetailsVO> newVOs = new ArrayList<ContainerDetailsVO>();
			int flag = 0;
			if(contDetailsVOs != null && contDetailsVOs.size() > 0){
			  for(ContainerDetailsVO mainscreenVO:contDetailsVOs){
				  if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
					for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
					  if(mainscreenVO.getContainerNumber().equals(popupVO.getContainerNumber())){
						  newVOs.add(popupVO);
						  flag = 1;
					  }
			        }
				  }
				  if(flag == 1){
					  flag = 0;
				  }else{
					  newVOs.add(mainscreenVO);
				  }
			  }
			}
			log.log(Log.FINE, "newVOs ...in first", newVOs);
			flag = 0;
			if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
			for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
			      if(contDetailsVOs != null && contDetailsVOs.size() > 0){
				  for(ContainerDetailsVO mainscreenVO:contDetailsVOs){
					   if(mainscreenVO.getContainerNumber().equals(popupVO.getContainerNumber())){
							  flag = 1;
					   }
				    }
				  }
				  if(flag == 0){
					  newVOs.add(popupVO);
				  }else{
					  flag = 0;
				  }
			  }
			}
			//
			mailAcceptanceVO.setContainerDetails(newVOs);
			mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
			mailAcceptanceSession.setContainerDetailsVOs(null);
			mailAcceptanceSession.setContainerDetailsVO(null);
	    
	    	mailAcceptanceForm.setPopupCloseFlag("Y");
	    	invocationContext.target = TARGET;
			return;
	    }
}
