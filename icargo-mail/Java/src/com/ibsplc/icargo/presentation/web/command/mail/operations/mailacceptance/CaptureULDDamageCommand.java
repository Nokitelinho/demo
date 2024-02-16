/*
 * CaptureULDDamageCommand.java Created on Jul 1 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
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
public class CaptureULDDamageCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	

	   private static final String TARGET_SUCCESS = "captureulddamage_success";
	    
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("CaptureULDDamageCommand","execute");
	    	 
	    	MailAcceptanceForm mailAcceptanceForm = (MailAcceptanceForm)invocationContext.screenModel;
	    	MailAcceptanceSession mailAcceptanceSession = getScreenSession(MODULE_NAME,SCREEN_ID);
	    	MailAcceptanceVO mailAcceptanceVO= mailAcceptanceSession.getMailAcceptanceVO();
	    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailAcceptanceVO.getContainerDetails();

	    	if (containerDetailsVOs != null && containerDetailsVOs.size() != 0) {
	    		for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
	    			if(!"I".equals(containerDetailsVO.getContainerOperationFlag())){	    		    	
	    				mailAcceptanceForm.setCaptureULDDamageFlag(MailConstantsVO.FLAG_YES);
	    			}else{
	    		    	mailAcceptanceForm.setCaptureULDDamageFlag(MailConstantsVO.FLAG_NO);
	    		    	ErrorVO errorVO=new ErrorVO("mailtracking.defaults.mailacceptance.contnotsaved");
	    		    	//errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
	          	    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	          	    	invocationContext.addError(errorVO);
	        			invocationContext.target = TARGET_SUCCESS;        			
	        			return;		
	    			}
	    		}
	    	}    	
	    	
			invocationContext.target = TARGET_SUCCESS;
  	    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	log.exiting("CaptureULDDamageCommand","execute");
	    	
	    }

}
