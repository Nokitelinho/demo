/*
 * ReturnDsnCommand.java Created on July 01, 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
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
public class ReturnDsnCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "returndsn_success";
   private static final String TARGET_FAILURE = "returndsn_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";	
   
   private static final String CONST_RETURNDSN = "showReturnDsnPopup";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ReturnDsnCommand","execute");
    	  
    	DsnEnquiryForm dsnEnquiryForm = 
    		(DsnEnquiryForm)invocationContext.screenModel;
    	DsnEnquirySession dsnEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	Collection<DespatchDetailsVO> despatchDetailsVOs = dsnEnquirySession.getDespatchDetailsVOs();
		ArrayList<DespatchDetailsVO> selectedDespatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
    
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
		// VALIDATING WHETHER PLT FLAGS ARE ENABLED
    	boolean isPltEnabled = false;
        for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs) {
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
     	
     	if(isPltEnabled){
   		 	ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.dsnenquiry.msg.err.cannotReturnPltenabled");
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);			
			invocationContext.target = TARGET_FAILURE;
			return;
    	}
     	
     	// Getting the partner carriers
     	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
     	Collection<PartnerCarrierVO> partnerCarrierVOs = null;
     	
     	try {
     		partnerCarrierVOs = mailTrackingDefaultsDelegate.findAllPartnerCarriers(
     				logonAttributes.getCompanyCode(),
     				logonAttributes.getOwnAirlineCode(),
     				logonAttributes.getAirportCode());
			            			
		}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		
		/*
         * Check Whether the Despatches CarrierCode
         * is not Owners or its Partner Carrier Code
         * If true Throw Exception
         */
		String carrierCode = null;
		boolean isInvalidDespatch = false;
		for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs) {
		 carrierCode = selectedvo.getCarrierCode();
   		 	if(!(carrierCode.equals(logonAttributes.getOwnAirlineCode()))
   				&& !(validateCarrierCodeFromPartner(partnerCarrierVOs,carrierCode))) {
   			 		isInvalidDespatch = true;   			 		
   		    }
   		 break;
		}
				
        if(isInvalidDespatch) { 
	       		       	
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.invaliddespatchesforreturn");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors = new ArrayList<ErrorVO>();			
			errors.add(errorVO);
			invocationContext.addAllError(errors);			
			invocationContext.target = TARGET_FAILURE;
			return;
        }
        /*
         * validating wether the flight is closed 
         * Need Only in Case of OUTBOUND.
         */

        if("O".equals(dsnEnquiryForm.getOperationType())){
        	boolean isFlightClosed = false;
        	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
        	for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs){
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
        					new ErrorVO("mailtracking.defaults.msg.err.flightclosed.selecteddsn",
        							new Object[]{selectedvo.getCarrierCode(),selectedvo.getFlightNumber(),selectedvo.getFlightDate().toString().substring(0, 11)}));
        			invocationContext.target = TARGET_FAILURE;
        			return;

        		}
        	}
        }
//      VALIDATION FOR already transferred
    	int  errorPort = 0;
        String contPort = "";
 	   	for (DespatchDetailsVO selectedvo : selectedDespatchDetailsVOs) {
 	   		if (selectedvo.getContainerNumber() != null ) {
 	   			if("Y".equals(selectedvo.getTransferFlag())){
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
 	   	} 
 	     if(errorPort == 1){
 	   	   	invocationContext.addError(
 	   	   			new ErrorVO("mailtracking.defaults.msg.err.alreadytransferred",
 	   	   			new Object[]{contPort}));
 	   	   	invocationContext.target = TARGET_FAILURE;
 	   	   	return;
 	     }
        dsnEnquiryForm.setStatus(CONST_RETURNDSN);
    	
        invocationContext.target = TARGET_SUCCESS;
       	
        log.exiting("ReturnDsnCommand","execute");
    	
    }  
    /**
     * This method is used to Check whether the CarrierCode is
     * Present in one amongst the Partners if Present
     * return true else false
     * @param partnerCarierVos
     * @param carrierCode
     * @return
     */
    private boolean validateCarrierCodeFromPartner(
    		Collection<PartnerCarrierVO> partnerCarierVos,
    		String carrierCode){
    	boolean isValid = false;
 	    if(partnerCarierVos != null && partnerCarierVos.size() > 0){
    	   for(PartnerCarrierVO partnerCarrierVO:partnerCarierVos){
	 		   log.log(Log.FINE, "The Carrier Code is ", carrierCode);
			log.log(Log.FINE, "The  Partner Carrier Code is ", partnerCarrierVO.getPartnerCarrierCode());
			if(carrierCode.equals(partnerCarrierVO.getPartnerCarrierCode())){
	 			   isValid=true;
	 			   break;
	 		   }
 		   }
 	    }
 	    log.log(Log.FINE, "Is PartnerPresent----------->>", isValid);
	return isValid;
    }
   
}
