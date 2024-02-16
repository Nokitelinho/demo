/*
 * CloseFlightCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class CloseFlightCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILOPERATIONS,CloseFlightCommand");
		
	   /**
	    * TARGET
	    */
	   private static final String CLOSE_SUCCESS = "close_success";
	   private static final String CLOSE_FAILURE = "close_failure";
	   private static final String POPUP_CLOSE_SUCCESS = "popup_close_success";
	   private static final String SELECT_EMPTYULDS = "select_emptyulds";
	   
	   private static final String CLOSE_SUCCESS_MANIFEST = "close_success_manifest";
	   private static final String SELECT_EMPTYULDS_MANIFEST = "select_emptyulds_manifest";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	   private static final String SCREEN_ID_MANIFEST = "mailtracking.defaults.mailmanifest";
	   
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
	    	  
	    	MailAcceptanceForm mailAcceptanceForm = (MailAcceptanceForm)invocationContext.screenModel;
	    	MailAcceptanceSession mailAcceptanceSession = getScreenSession(MODULE_NAME,SCREEN_ID);
	    	MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID_MANIFEST);
	    	
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
	    	
	    	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	
	    	String fromScreen = mailAcceptanceForm.getFromScreen();
	    	
	    	
	    	MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
	    	MailAcceptanceVO newMailAcceptanceVO = new MailAcceptanceVO();
	    	if(mailAcceptanceVO != null){
		    	try{
		    		BeanHelper.copyProperties(newMailAcceptanceVO,mailAcceptanceVO);
		    	}catch(SystemException systemException){
		    		systemException.getMessage();
		    	}
	    	}
	    	
	    	MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();
	    	FlightValidationVO flightValidationVO = mailManifestSession.getFlightValidationVO();
	    	
	    	Collection<ContainerDetailsVO> emptyContainers = new ArrayList<ContainerDetailsVO>();

	    	if(!"MANIFEST".equals(fromScreen)){
	    		
	    		
    		/**
	    	 * To check whether flight is already closed.
	    	 */
	    	boolean isFlightClosed = false;
	    	try {
	    		
			    	isFlightClosed = delegate.isFlightClosedForMailOperations(mailAcceptanceSession.getOperationalFlightVO());
			   
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = CLOSE_SUCCESS;
				log.log(Log.FINE, "\n\n FLAG2 \n", mailAcceptanceForm.getUldsPopupCloseFlag());
				return;
			}
			log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
			if (isFlightClosed) {
				Object[] obj = {new StringBuilder(mailAcceptanceVO.getFlightCarrierCode())
						 .append("").append(mailAcceptanceVO.getFlightNumber()).toString(),
						 mailAcceptanceVO.getStrFlightDate()};
				invocationContext.addError(new ErrorVO("mailtracking.defaults.err.flightclosed",obj));
				mailAcceptanceForm.setDisableSaveFlag("Y");
				invocationContext.target = CLOSE_SUCCESS;
				log.log(Log.FINE, "\n\n FLAG3 \n", mailAcceptanceForm.getUldsPopupCloseFlag());
				return;				
			}  	
	    	
	    	Collection<ContainerDetailsVO> containerDetailsVOs = mailAcceptanceVO.getContainerDetails();	    	
	    	log.log(Log.FINE, "\n\n UldsSelectedFlag----------> ",
					mailAcceptanceForm.getUldsSelectedFlag());
			if(!("Y").equals(mailAcceptanceForm.getUldsSelectedFlag())){
	    	  //	log.log(Log.FINE, "\n\n *******UldsSelectedFlag not Y==========> \n\n");
	    		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
	    		 for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
		    		if((ULD).equals(containerDetailsVO.getContainerType()) &&
		    				containerDetailsVO.getTotalBags() == 0 &&
		    				(NOT_ACCEPTED).equals(containerDetailsVO.getAcceptedFlag())){
		    			emptyContainers.add(containerDetailsVO);
		    		}
		    	 }
	    		}
	    		log.log(Log.FINE, "\n\n emptyContainers----------> \n",
						emptyContainers);
				if(emptyContainers != null && emptyContainers.size()>0){
		    		if("MANIFEST".equals(fromScreen)){
		    			invocationContext.target = SELECT_EMPTYULDS_MANIFEST;
		    		}else{
		    			invocationContext.target = SELECT_EMPTYULDS;
		    		}
		    		mailAcceptanceSession.setContainerDetailsVOs(emptyContainers);
		    		mailAcceptanceForm.setUldsSelectedFlag("N");
		    		log.log(Log.FINE, "\n\n FLAG4 \n", mailAcceptanceForm.getUldsPopupCloseFlag());
					return;
		    	}
	    	  }
	    	}
	//    	log.log(Log.FINE, "\n\n UldsSelectedFlag*********   "+mailAcceptanceForm.getUldsSelectedFlag());
	    	if(("Y").equals(mailAcceptanceForm.getUldsSelectedFlag())){
	    		
	    		String[] selectedRows = mailAcceptanceForm.getSelectULDs();
	    		emptyContainers = mailAcceptanceSession.getContainerDetailsVOs();
	    		
	    		Collection<ContainerDetailsVO> selectedULDs = new ArrayList<ContainerDetailsVO>();
	    		if(selectedRows != null && selectedRows.length >0){
		    		int size = selectedRows.length;
	    			log.log(Log.FINE, "\n\n size----------> ", size);
					if(emptyContainers != null && emptyContainers.size()>0){
		    			int index = 0;
		    			for(ContainerDetailsVO containerDetailsVO:emptyContainers){
		    				for(int i = 0; i < size;  i++){
				    			if(index == Integer.parseInt(selectedRows[i])){
				    				containerDetailsVO.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
				    				selectedULDs.add(containerDetailsVO);
				    			}
				    		}
		    				index++;
		    			}
			    		
		    		}
		    		log.log(Log.FINE, "\n\n selectedULDs----------> \n",
							selectedULDs);
					if("MANIFEST".equals(fromScreen)){
		    			  
		    			    if(selectedULDs != null && selectedULDs.size()>0){
				    			for(ContainerDetailsVO selectedULD:selectedULDs){
				    				selectedULD.setOperationFlag("I");
				    			}
				    		}
		    			  newMailAcceptanceVO.setContainerDetails(selectedULDs);
		    			  newMailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
		    	    	  newMailAcceptanceVO.setPol(logonAttributes.getAirportCode());
	    	    		  newMailAcceptanceVO.setFlightCarrierCode(mailManifestVO.getFlightCarrierCode());
	    	    		  newMailAcceptanceVO.setFlightNumber(mailManifestVO.getFlightNumber());
	    	    		  newMailAcceptanceVO.setFlightDate(mailManifestVO.getDepDate());
	    	    		  newMailAcceptanceVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	    	    		  newMailAcceptanceVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
	    	    		  newMailAcceptanceVO.setCarrierId(mailManifestVO.getCarrierId());
		    		}else{
		    			newMailAcceptanceVO.setContainerDetails(selectedULDs);
		    		}
		    		newMailAcceptanceVO.setOwnAirlineCode(
		    				logonAttributes.getOwnAirlineCode());
		    		newMailAcceptanceVO.setOwnAirlineId(
		    				logonAttributes.getOwnAirlineIdentifier());
		    		log.log(Log.FINE, "\n\n newMailAcceptanceVO----------> \n",
							newMailAcceptanceVO);
					try{
		    			delegate.saveAcceptanceDetails(newMailAcceptanceVO);
		    		}catch(BusinessDelegateException businessDelegateException){
		    			errors = handleDelegateException(businessDelegateException);
		    			invocationContext.addAllError(errors);
		    			mailAcceptanceSession.setMessageStatus("N");
		    			mailAcceptanceForm.setUldsPopupCloseFlag("N");
						invocationContext.target = POPUP_CLOSE_SUCCESS;
						log.log(Log.FINE, "\n\n FLAG1 \n", mailAcceptanceForm.getUldsPopupCloseFlag());
						return;
		    		}
	    		}
	    	}
	    	
	    	OperationalFlightVO operationalFlightVO = null;
	    		
	    	if("MANIFEST".equals(fromScreen)){
	    		operationalFlightVO = mailManifestSession.getOperationalFlightVO();
	    	}else{
	    		operationalFlightVO = mailAcceptanceSession.getOperationalFlightVO();
	    	}
	    	log.log(Log.FINE, "\n\n operationalFlightVO==========> \n",
					operationalFlightVO);
			try{
	    		//delegate.closeFlight(operationalFlightVO);
	    		//added by A-3251 Sreejith P.C. for MailAlert message
	    		delegate.closeFlightAcceptance(operationalFlightVO,mailAcceptanceVO);
	    		
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
    			invocationContext.addAllError(errors);
		    	invocationContext.target = CLOSE_FAILURE;
    			return;
	    	}
	    	if(("Y").equals(mailAcceptanceForm.getUldsSelectedFlag())){
		    	mailAcceptanceForm.setUldsSelectedFlag("");
		    	mailAcceptanceSession.setMessageStatus("Y");
		    	mailAcceptanceForm.setUldsPopupCloseFlag("Y");
		    	
		    	invocationContext.target = POPUP_CLOSE_SUCCESS;
		    	log.log(Log.FINE, "\n\n FLAG5 \n", mailAcceptanceForm.getUldsPopupCloseFlag());
				return;
	    	}
	    	
    		//Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
				//mailAcceptanceVO.getFlightNumber(),mailAcceptanceVO.getStrFlightDate()};
    		//invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.flightclosed",obj));
    		mailAcceptanceForm.setUldsPopupCloseFlag("Y");
    		mailAcceptanceSession.setMessageStatus("Y");
    		log.log(Log.FINE, "\n\n FLAG6 \n", mailAcceptanceForm.getUldsPopupCloseFlag());
			invocationContext.target = CLOSE_SUCCESS;
	    	

	    	mailAcceptanceForm.setUldsSelectedFlag("");
	    	//mailAcceptanceForm.setUldsPopupCloseFlag("");
	    	

	}

}
