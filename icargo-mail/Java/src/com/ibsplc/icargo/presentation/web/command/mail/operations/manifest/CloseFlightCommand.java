/*
 * CloseFlightCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class CloseFlightCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILTRACKING,CloseFlightCommand");
		
	   /**
	    * TARGET
	    */
	   private static final String CLOSE_SUCCESS = "close_success";
	   private static final String CLOSE_FAILURE = "close_failure";
	   private static final String POPUP_CLOSE_SUCCESS = "popup_close_success";
	   private static final String SELECT_EMPTYULDS = "select_emptyulds";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
	   private static final String SCREEN_ID_ACCEPTANCE = "mailtracking.defaults.mailacceptance";
	   
	   private static final String ULD = "U";
	   private static final String NOT_ACCEPTED = "N";

		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("CloseFlightCommand","execute");
	    	  
	    	MailManifestForm mailManifestForm = (MailManifestForm)invocationContext.screenModel;
	    	MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID);
	    	//MailAcceptanceSession mailAcceptanceSession = getScreenSession(MODULE_NAME,SCREEN_ID_ACCEPTANCE);
	    	
	    	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    	mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	
	    	MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();
	    	
	    	/**
	    	 * To check whether flight is already closed.
	    	 */
	    	boolean isFlightClosed = false;
	    	try {
	    		
			    	isFlightClosed = delegate.isFlightClosedForMailOperations(mailManifestSession.getOperationalFlightVO());
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = CLOSE_SUCCESS;
				return;
			}
			log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
			if (isFlightClosed) {
				Object[] obj = {new StringBuilder(mailManifestVO.getFlightCarrierCode())
						 .append("").append(mailManifestVO.getFlightNumber()).toString(),
						 mailManifestVO.getStrDepDate()};
				invocationContext.addError(new ErrorVO("mailtracking.defaults.err.flightclosed",obj));
				mailManifestForm.setDisableSaveFlag("Y");
				invocationContext.target = CLOSE_SUCCESS;
				return;				
			}  
	    	
	    	
	    	
	    	OperationalFlightVO operationalFlightVO = mailManifestSession.getOperationalFlightVO();
	    	/*Collection<ContainerDetailsVO> emptyContainers = new ArrayList<ContainerDetailsVO>();
	    	try{
	    		emptyContainers = delegate.findUnacceptedULDs(operationalFlightVO);
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    	}
	    	
	    	log.log(Log.FINE, "\n\n emptyContainers----------> \n"+emptyContainers);
	    	log.log(Log.FINE, "\n\n UldsSelectedFlag----------> "+mailManifestForm.getUldsSelectedFlag());
	    		
	    	if(emptyContainers != null && emptyContainers.size()>0){
	    		mailAcceptanceSession.setContainerDetailsVOs(emptyContainers);
	    		mailManifestForm.setUldsSelectedFlag("N");
	    		invocationContext.target = SELECT_EMPTYULDS;
	    		return;
	    	}*/
	    	
	    	log.log(Log.FINE, "\n\n operationalFlightVO==========> \n",
					operationalFlightVO);
			try{
	    		//delegate.closeFlight(operationalFlightVO);
	    		//added by A-3251 Sreejith P.C. for MailAlert message	    		
	    		delegate.closeFlightManifest(operationalFlightVO,mailManifestVO);
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
    			invocationContext.addAllError(errors);
		    	invocationContext.target = CLOSE_FAILURE;
    			return;
	    	}
	    	
	    //	Object[] obj = {mailManifestVO.getFlightCarrierCode(),
	    //			mailManifestVO.getFlightNumber(),mailManifestVO.getStrDepDate()};
		//	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.flightclosed",obj));
	    	mailManifestSession.setMessageStatus("Y");
			mailManifestForm.setUldsSelectedFlag("");
			mailManifestForm.setUldsPopupCloseFlag("");
	    	invocationContext.target = CLOSE_SUCCESS;

	}

}
