/*
 * OffloadDsnCommand.java Created on July 01, 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OffloadSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class OffloadDsnCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "offload_success";
   private static final String TARGET_FAILURE = "offload_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";	
   private static final String OFFLOAD_SCREEN_ID = "mailtracking.defaults.offload";	
   
   private static final String CONST_OFFLOAD = "showOffloadScreen";
   private static final String OUTBOUND = "O";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("OffloadDsnCommand","execute");
    	  
    	DsnEnquiryForm dsnEnquiryForm = 
    		(DsnEnquiryForm)invocationContext.screenModel;
    	DsnEnquirySession dsnEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	OffloadSession offloadSession = 
    		getScreenSession(MODULE_NAME,OFFLOAD_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	Collection<DespatchDetailsVO> despatchDetailsVOs = dsnEnquirySession.getDespatchDetailsVOs();
		Page<DespatchDetailsVO> selectedDespatchDetailsVOs = new Page<DespatchDetailsVO>(new ArrayList<DespatchDetailsVO>(),0,0,0,0,0,false);
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
		    
    	// Getting the selected MailBags
		String[] selectedRows = dsnEnquiryForm.getSubCheck();  
    	int size = selectedRows.length;
    	int row = 0;
    	for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				selectedDespatchDetailsVOs.add(despatchDetailsVO);
    			}    			
			}
    		row++;
    	}
    	
    	log.log(Log.FINE, "selectedDespatchDetailsVOs---->",
				selectedDespatchDetailsVOs);
		boolean isPltEnabled = false;
        boolean isAssignedToFlight = false;
        for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs) {
     		if (selectedvo.getFlightDate() != null) {
     			isAssignedToFlight = true;
     		}
        	if ("true".equals(selectedvo.getPltEnabledFlag())) {
     			isPltEnabled = true;
     			break;
     		}
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
        
        /*
		 *  VALIDATE PORT 
		 */ 
        int errorPort = 0;
	     String contPort = "";
	     for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs) {
	    	 if(!logonAttributes.getAirportCode().equals(selectedvo.getAirportCode())){
	    		 errorPort = 1;
   				if("".equals(contPort)){
   					contPort = selectedvo.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
					                  .append(",")
					                  .append(selectedvo.getContainerNumber())
					                  .toString();	
	       			}
	    	 }
	     }
	     if(errorPort == 1){
	   	   	invocationContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));
	   	   	invocationContext.target = TARGET_FAILURE;
	   	   	return;
	     }
        
        
        /*
		 *  VALIDATE WHETHER SELECTED DESPATCH IS INBOUND - 
		 *  Inbound despatches cannot be offloaded
		 */
		if (isAssignedToFlight) {
			String opertnType = dsnEnquiryForm.getOperationType();
			if ("I".equals(opertnType)) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.dsnenquiry.msg.err.cannotOffloadInboundDsn");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);			
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		}        
     	
		// VALIDATING WHETHER PLT FLAGS ARE ENABLED
     	if(isPltEnabled){
   		 	ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.dsnenquiry.msg.err.cannotOffloadPltenabled");
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);			
			invocationContext.target = TARGET_FAILURE;
			return;
    	}
    	
    	//	Validation to check whether any mailbags are destination assigned 
     	StringBuffer dsns = new StringBuffer();
    	for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs) {
    		if (selectedvo.getFlightNumber() == null
    				|| "-1".equals(selectedvo.getFlightNumber())) {
    			dsns.append(selectedvo.getDsn()+",");
    		}
	     }
    	
    	log.log(Log.FINE, "dsns.toString()---->", dsns.toString());
		if(dsns.length() > 0){
    		Object[] obj = {dsns.toString().substring(0,dsns.length()-1)};
   		 	ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.dsnenquiry.msg.err.notFlightAssigned",obj);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);			
			invocationContext.target = TARGET_FAILURE;
			return;
    	}
	    
	     String carriercode = "";
         String fltNo = "";
         String airPort = "";
         long fltseqNo = 0;
         //int segSerialNo = 0;
         int carrierid = 0;
         LocalDate fltDate = null;
         String containerNumber="";
         String containerType="";
        
         for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs) {
         	carriercode = selectedvo.getCarrierCode();
         	fltNo = selectedvo.getFlightNumber();
         	airPort = selectedvo.getAirportCode();         	
         	fltseqNo = selectedvo.getFlightSequenceNumber();
        	//segSerialNo = selectedvo.getSegmentSerialNumber();
         	carrierid = selectedvo.getCarrierId();
         	fltDate = selectedvo.getFlightDate();
         	containerNumber=selectedvo.getContainerNumber();
         	containerType=selectedvo.getContainerType();
         	break;
         }   
      	    
    	// Validating whether asigned to different flight
         for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs) {
        	if (!carriercode.equals(selectedvo.getCarrierCode())
        			|| !fltNo.equals(selectedvo.getFlightNumber())
        			|| fltseqNo != selectedvo.getFlightSequenceNumber()
        			|| !airPort.equals(selectedvo.getAirportCode())) {
        		ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.dsnenquiry.msg.err.notassignedToSameFlight");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);			
				invocationContext.target = TARGET_FAILURE;
				return;
        	}
        }
         
        // Validating Flight to obtain the LegSerialNumber
        FlightFilterVO flightFilterVO = new FlightFilterVO();
  		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
  		flightFilterVO.setFlightNumber(fltNo);
  		flightFilterVO.setStation(airPort);
  		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
  		flightFilterVO.setStringFlightDate(String.valueOf(fltDate));
   		flightFilterVO.setFlightDate(fltDate);
   		flightFilterVO.setCarrierCode(carriercode);
 		flightFilterVO.setFlightCarrierId(carrierid);
 		
 		Collection<FlightValidationVO> flightValidationVOs = null;
 		FlightValidationVO flightValidationVO = null;

 		try {
 			log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
			flightValidationVOs =
 				mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

 		}catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
 		}
 		
 		if (flightValidationVOs != null) {
 			log
					.log(Log.FINE, "SIZE ------------> ", flightValidationVOs.size());
			if (flightValidationVOs.size() == 1 ) { 
 				for (FlightValidationVO validationVO : flightValidationVOs) {
 					flightValidationVO = validationVO;
 					break;
 				}
 			}
 			else if(flightValidationVOs.size() > 1) {
 				for (FlightValidationVO validationVO : flightValidationVOs) {
 					if (validationVO.getFlightSequenceNumber() == fltseqNo) {
 						flightValidationVO = validationVO;
 						break;
 					}				
 				}
 			}
 		}		
 		log.log(Log.FINE, "flightValidationVO ------------> ",
				flightValidationVO);
		// 		Validating Flight Closure
    	boolean isFlightClosed = false;
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	try {	    		
    		isFlightClosed = 
    				mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);   		        		
			
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		log.log(Log.FINE,
				"DSNEnquiry-->OffloadDSNCommand--->isFlightClosed----->",
				isFlightClosed);
		if(!isFlightClosed){
			Object[] obj = {new StringBuilder(carriercode).append("").append(fltNo).toString(),fltDate.toDisplayDateOnlyFormat()};				
			invocationContext.addError(new ErrorVO("mailtracking.defaults.err.flightclosed",obj));
 	   		invocationContext.target =TARGET_SUCCESS; 
 	   		return;
		}
 		// 	 Setting LegSerialNumber to the selected DespatchDetailsVOs
 		for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs) {
			selectedvo.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		}
        
 		// Creating the OffloadVO for offloading 
        OffloadVO offloadVO = new OffloadVO();
        offloadVO.setCompanyCode(logonAttributes.getCompanyCode());
        offloadVO.setFlightNumber(fltNo);
        offloadVO.setFlightSequenceNumber(fltseqNo);
        offloadVO.setFlightDate(fltDate);
        offloadVO.setCarrierCode(carriercode);
        offloadVO.setCarrierId(carrierid);
        offloadVO.setOffloadDSNs(selectedDespatchDetailsVOs);
        offloadVO.setPol(logonAttributes.getAirportCode());
        offloadVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
       
        offloadSession.setOffloadVO(offloadVO); 
        offloadSession.setFlightValidationVO(flightValidationVO);
        dsnEnquiryForm.setStatus(CONST_OFFLOAD);
    	
        invocationContext.target = TARGET_SUCCESS;
       	
        log.exiting("OffloadDsnCommand","execute");
    	
    }
       
}
