/*
 * ListTransferMailCommand.java Created on July 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermail;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class ListTransferMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.transfermail";	
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String OPEN = "O";
   private static final String CLOSED = "C";
   private static final String FLTSTATUS = "mailtracking.defaults.flightstatus";
   private static final String OUTBOUND = "O";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListTransferMailCommand","execute");
    	  
    	TransferMailForm transferMailForm = 
    		(TransferMailForm)invocationContext.screenModel;
    	TransferMailSession transferMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
				
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		operationalFlightVO.setPol(logonAttributes.getAirportCode());
		
		 FlightValidationVO flightValidationVO = new FlightValidationVO();
		 FlightValidationVO duplicateflightValidationVO = new FlightValidationVO();
		 if(FLAG_YES.equals(transferMailForm.getDuplicateFlightStatus())){
			 transferMailForm.setAssignToFlight(CONST_FLIGHT);
		 }
		String assignTo = transferMailForm.getAssignToFlight();
		log.log(Log.FINE, "assignTo === ", assignTo);
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
			
			log.log(Log.FINE, "*******FLIGHT MODE******");
			
				if(FLAG_YES.equals(transferMailForm.getDuplicateFlightStatus())){
					duplicateflightValidationVO = duplicateFlightSession.getFlightValidationVO();
					duplicateflightValidationVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
					transferMailSession.setFlightValidationVO(duplicateflightValidationVO);
					transferMailForm.setDuplicateFlightStatus(FLAG_NO);
				}
				log.log(Log.FINE, "flightValidationVO in MA session...",
						transferMailSession.getFlightValidationVO());
				flightValidationVO = transferMailSession.getFlightValidationVO();
				Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
				if(oneTimes!=null){
					Collection<OneTimeVO> resultStatus=
						oneTimes.get("flight.operation.flightlegstatus");
					log.log(Log.FINE, "*******flightlegstatus******");
					flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
				}
				transferMailSession.setFlightValidationVO(flightValidationVO);
				
				/**
				 * ADDED FOR SAA 410 STARTS
				 */
				if(flightValidationVO != null){
	    			if(flightValidationVO.isTBADueRouteChange()){
	    				Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),transferMailForm.getFlightDate()};
	    				ErrorVO errorVO = new ErrorVO(
	    						"mailtracking.defaults.transfermail.msg.err.flighttobeactioned",obj);
	    				if(errors == null){
	    					errors = new ArrayList<ErrorVO>();
	    				}
	    				errors.add(errorVO);
	    				invocationContext.addAllError(errors);
	    				invocationContext.target = TARGET;
	    				return;
	    			}
	    		}
				/**
				 * ADDED FOR SAA 410 ENDS
				 */
		    	
				operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
				operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
				operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
				operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				operationalFlightVO.setDirection(OUTBOUND);
			
			  log.log(Log.FINE, "operationalFlightVO in MA session...",
					operationalFlightVO);
			try {
				  containerVOs = new MailTrackingDefaultsDelegate().findFlightAssignedContainers(operationalFlightVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
	    		invocationContext.target = TARGET;
	    		return;
	    	  }
	  		log.log(Log.FINE, "containervos ----->>", containerVOs);
			if (containerVOs == null || containerVOs.size() <= 0) {
	    		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noresultsfound");
				errors = new ArrayList<ErrorVO>();
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_DETAIL);
				transferMailForm.setHidScanTime("Y");
				invocationContext.target = TARGET;
				return;
	    	}
	    		  boolean flightStatus=false;
	    	    	try{
	            		
	    	    	  flightStatus = new MailTrackingDefaultsDelegate().isFlightClosedForMailOperations(operationalFlightVO);
	    	    	
	    	    	}catch (BusinessDelegateException businessDelegateException) {
	    				errors = handleDelegateException(businessDelegateException);
	    			}
	    	    	
	    	    	String status="";
	    	    	log.log(Log.FINE, "flightStatus --------->>", flightStatus);
					if(flightStatus) {
						status=CLOSED;
					} else {
						status=OPEN;
					}
	    	    	
	    	    	if(oneTimes!=null){
	    				Collection<OneTimeVO> resultStatus=
	    					oneTimes.get(FLTSTATUS);
	    				log.log(Log.FINE, "*******flightlegstatus******");
	    				flightValidationVO.setOperationalStatus(findOneTimeDescription(resultStatus,status));
	    			}   
	    	    	transferMailSession.setFlightValidationVO(flightValidationVO);
	    	    	/**
	    	    	 * Added for Bug 77985
	    	    	 * For flight closure check starts
	    	    	 */
	    	    	if(flightStatus){
	    	    		Object[] obj = {flightValidationVO.getCarrierCode(),
	    	    				flightValidationVO.getFlightNumber(),
	    	    				flightValidationVO.getApplicableDateAtRequestedAirport().toString().substring(0,11)};
	    	    		errors.add(new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.flightclosed",obj));
	    	    	}
	    	    	if(errors != null && errors.size() >0){
	    	    		invocationContext.addAllError(errors);
	    	    		invocationContext.target = TARGET;
	    	    		return;
	    	    	}
	    	    	/**
	    	    	 * Added for Bug 77985
	    	    	 * For flight closure check ends
	    	    	 */
	    	  
		}else{
			log.log(Log.FINE, "*******DESTINATION MODE******");
			
	            DestinationFilterVO destinationFilterVO = new DestinationFilterVO();
	        	destinationFilterVO.setAirportCode(logonAttributes.getAirportCode());
	        	destinationFilterVO.setCarrierCode(transferMailForm.getCarrierCode().toUpperCase());
	        	destinationFilterVO.setCarrierId(transferMailForm.getCarrierID());
	        	destinationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	        	destinationFilterVO.setDestination(transferMailForm.getDestination().toUpperCase());
	        	
	        	log.log(Log.FINE, "DestinationFilterVO ----->>",
						destinationFilterVO);
			try {
				  containerVOs = new MailTrackingDefaultsDelegate().findDestinationAssignedContainers(destinationFilterVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
	    		invocationContext.target = TARGET;
	    		return;
	    	  }
	  		log.log(Log.FINE, "containervos ----->>", containerVOs);
			if (containerVOs == null || containerVOs.size() <= 0) {
	    		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noresultsfound");
				errors = new ArrayList<ErrorVO>();
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_DETAIL);
				transferMailForm.setHidScanTime("Y");
				invocationContext.target = TARGET;
				return;
	    	}
		}
    	
    	transferMailSession.setContainerVOs(containerVOs);
    	transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	transferMailForm.setHidScanTime("Y");
    	invocationContext.target = TARGET;
    	log.exiting("ListTransferMailCommand","execute");
    	
    }
    
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add("flight.operation.flightlegstatus");
			fieldValues.add("mailtracking.defaults.flightstatus");
			
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;

		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

	/**
	 * This method will the status description corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		if (oneTimeVOs != null) {
			for (OneTimeVO oneTimeVO:oneTimeVOs){
				if(status.equals(oneTimeVO.getFieldValue())){
					return oneTimeVO.getFieldDescription();
				}
			}
		}

		return null;
	}
       
}
