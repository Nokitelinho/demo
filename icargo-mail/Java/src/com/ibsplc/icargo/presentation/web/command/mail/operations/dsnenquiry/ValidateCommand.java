/*
 * ValidateCommand.java Created on July 01, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dsnenquiry;


import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ValidateCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "validation_success";
   private static final String TARGET_FAILURE = "validation_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";
   
   private static final String CONST_VIEW = "VIEW"; 
   private static final String CONST_REASSIGN = "REASSIGN";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListCommand","execute");
    	  
    	DsnEnquiryForm dsnEnquiryForm = 
    		(DsnEnquiryForm)invocationContext.screenModel;
    	DsnEnquirySession dsnEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();  	    	
		Collection<ErrorVO> errors = null;
		
		String status = dsnEnquiryForm.getStatus();
		
		Collection<DespatchDetailsVO> despatchDetailsVOs = dsnEnquirySession.getDespatchDetailsVOs();
		ArrayList<DespatchDetailsVO> selectedvos = new ArrayList<DespatchDetailsVO>();
		
		String[] selectedRows = dsnEnquiryForm.getSubCheck();    	
    	int row = 0;
    	for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
    		if (row == Integer.parseInt(selectedRows[0])) {
    			selectedvos.add(despatchDetailsVO);
			}
    		row++;
    	}
    	log.log(Log.FINE, "selectedvos --------->>", selectedvos);
		for(DespatchDetailsVO selectedvo : selectedvos) {
	    	/*
	    	 * Mail Captured through Capture Consignemnt,But not Accedpted to the system
	    	 * Done for ANZ CR AirNZ1039
	    	 */
			if(MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(selectedvo.getCapNotAcceptedStatus())) {
	    		ErrorVO errorVO = new ErrorVO(
	    		"mailtracking.defaults.err.capturedbutnotaccepted");	
	    		invocationContext.addError(errorVO);			
	    		invocationContext.target = TARGET_FAILURE;
	    		return;
			}
    	}
    	
		if (CONST_VIEW.equals(status)) {
			
			/*
			 * VALIDATE WHETHER SELECTED DESPATCH IS PLT ENABLED - 
			 * only PLT enabled mailbags can be viewed
			 */
			DespatchDetailsVO selectedvo = selectedvos.get(0);
			if (!Boolean.valueOf(selectedvo.getPltEnabledFlag())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.dsnenquiry.msg.err.pltNotEnabled");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);			
				invocationContext.target = TARGET_FAILURE;
				return;
			}
				dsnEnquiryForm.setStatus("showViewMailEnquiry");
		}
		else if (CONST_REASSIGN.equals(status)) {
			
			boolean isPltSelected = false;
			boolean isAssignedToFlight = false;
			for (DespatchDetailsVO selectedvo : selectedvos) {
				if (selectedvo.getFlightDate() != null) {
	     			isAssignedToFlight = true;
	     		}
				if (Boolean.valueOf(selectedvo.getPltEnabledFlag())) {
					isPltSelected = true;
					break;
				}
			}
			
			/*
			 *  VALIDATE PORT 
			 */ 
	        int errorPort = 0;
		     String contPort = "";
		     for (DespatchDetailsVO selectedvo : selectedvos) {
		    	 if(!logonAttributes.getAirportCode().equals(selectedvo.getAirportCode())){
		    		 errorPort = 1;
	   				if("".equals(contPort)){
	   					contPort = selectedvo.getDsn();
		       		}else{
		       			contPort = new StringBuilder(contPort)
					              .append(",")
					              .append(selectedvo.getDsn())
					              .toString();	
		       		}
		    	 }
		     }
		     if(errorPort == 1){
		   	   	invocationContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));
		   	   	invocationContext.target = TARGET_FAILURE;
		   	   	return;
		     }
		     
		     // to prevent re assigning of despatches which hav already arrived at another port
		     int errorBags = 0;
		     for (DespatchDetailsVO selectedvo : selectedvos) {
		    	 if(selectedvo.getReceivedBags()>0){
		    		 errorBags=1;
		    	 }
		     }
		     if(errorBags == 1){
		   	   	invocationContext.addError(new ErrorVO("mailtracking.defaults.dsnenquiry.msg.err.cannotReassignArrivedDSN"));
		   	   	invocationContext.target = TARGET_FAILURE;
		   	   	return;
		     }		
		        
			/*
			 *  VALIDATE WHETHER SELECTED DESPATCH IS INBOUND - 
			 *  Inbound despatches cannot be reassigned
			 */
			if (isAssignedToFlight) {
				String opertnType = dsnEnquiryForm.getOperationType();
				if ("I".equals(opertnType)) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.dsnenquiry.msg.err.cannotReassignInboundDsn");
					errors = new ArrayList<ErrorVO>();
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);			
					invocationContext.target = TARGET_FAILURE;
					return;
				}
			}			
			
			/*
			 * VALIDATE WHETHER SELECTED DESPATCH IS PLT ENABLED - 
			 * PLT enabled despatches cannot be reassigned
			 */ 
			if (isPltSelected) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.dsnenquiry.msg.err.pltEnabled");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);			
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		     /*
	         * validating wether the flight is closed
	         */
		     	/*TODO
		         * Further Discussion Needed
		         * For OUTBOUND Alone OR BOTH
		         */
		     if("O".equals(dsnEnquiryForm.getOperationType())){
		    	 boolean isFlightClosed = false;
		    	 OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		    	 for (DespatchDetailsVO selectedvo : selectedvos){
		    		 AirlineValidationVO airlineValidationVO = null;  
		    		 FlightFilterVO flightFilterVO = null;

		    		 try {
		    			 if (selectedvo.getCarrierCode() != null && !"".equals(selectedvo.getCarrierCode())) { 
		    				 airlineValidationVO = airlineDelegate.validateAlphaCode(
		    						 logonAttributes.getCompanyCode(),
		    						 selectedvo.getCarrierCode().trim().toUpperCase());
		    			 }
		    			 if(selectedvo.getFlightNumber() != null && !"".equals(selectedvo.getFlightNumber())){


		    				 //setting filterVO for validate flight
		    				 flightFilterVO = new FlightFilterVO();
		    				 flightFilterVO.setCarrierCode(selectedvo.getCarrierCode());
		    				 flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		    				 flightFilterVO.setFlightNumber(selectedvo.getFlightNumber());
		    				 flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		    				 flightFilterVO.setStation(logonAttributes.getAirportCode());
		    				 flightFilterVO.setDirection(dsnEnquiryForm.getOperationType());
		    				 flightFilterVO.setActiveAlone(false);
		    				 flightFilterVO.setStringFlightDate(selectedvo.getFlightDate().toString());
		    				 flightFilterVO.setFlightDate(selectedvo.getFlightDate());

		    				 Collection<FlightValidationVO> flightValidationVOs = null;

		    				 flightValidationVOs =
		    					 mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

		    				 if(flightValidationVOs!=null && flightValidationVOs.size()>0){
		    					 for (FlightValidationVO flightValidationVo : flightValidationVOs) {
		    						 //setting operationalflightVO for checking close flight
		    						 operationalFlightVO.setCarrierCode(flightValidationVo.getCarrierCode());
		    						 operationalFlightVO.setCarrierId(flightValidationVo.getFlightCarrierId());
		    						 operationalFlightVO.setCompanyCode(flightValidationVo.getCompanyCode());
		    						 operationalFlightVO.setDirection(dsnEnquiryForm.getOperationType());
		    						 operationalFlightVO.setFlightDate(flightValidationVo.getApplicableDateAtRequestedAirport());
		    						 operationalFlightVO.setFlightNumber(flightValidationVo.getFlightNumber());
		    						 operationalFlightVO.setFlightSequenceNumber(flightValidationVo.getFlightSequenceNumber());
		    						 operationalFlightVO.setLegSerialNumber(flightValidationVo.getLegSerialNumber());
		    						 operationalFlightVO.setPol(logonAttributes.getAirportCode());

		    						 
		    						 isFlightClosed = 
		    							 mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);   		        		
		    					 }	
		    				 }
		    			 }
		    		 }catch (BusinessDelegateException businessDelegateException) {
		    			 errors = handleDelegateException(businessDelegateException);
		    		 }
		    		 if (errors != null && errors.size() > 0) {
		    			 invocationContext.addAllError(errors);
		    			 invocationContext.target = TARGET_FAILURE;
		    			 return;
		    		 }
		    		 if(isFlightClosed){
		    			 invocationContext.addError(
		    					 new ErrorVO("mailtracking.defaults.msg.err.flightclosedreaasign.selecteddsn",
		    							 new Object[]{selectedvo.getCarrierCode(),selectedvo.getFlightNumber(),selectedvo.getFlightDate().toString().substring(0, 11)}));
		    			 invocationContext.target = TARGET_FAILURE;
		    			 return;

		    		 }
		    	 }
		     }
			dsnEnquiryForm.setStatus("showReassign");
		}
        
		dsnEnquirySession.setSelectedDespatchDetailsVOs(selectedvos);
    	invocationContext.target = TARGET_SUCCESS;
       	
    	log.exiting("ListCommand","execute");
    	
    }
  
}
