/*
 * ListReassignMailCommand.java Created on July 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassignmail;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class ListReassignMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "list_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.reassignmail";	
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String OUTBOUND = "O";
   private static final String OPEN = "O";
   private static final String CLOSED = "C";
   private static final String FLTSTATUS = "mailtracking.defaults.flightstatus";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListMailAcceptanceCommand","execute");
    	  
    	ReassignMailForm reassignMailForm = 
    		(ReassignMailForm)invocationContext.screenModel;
    	ReassignMailSession reassignMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
				
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		operationalFlightVO.setPol(logonAttributes.getAirportCode());
		
		 FlightValidationVO flightValidationVO = new FlightValidationVO();
		 if(FLAG_YES.equals(reassignMailForm.getDuplicateFlightStatus())){
			 reassignMailForm.setAssignToFlight(CONST_FLIGHT);
		 }
		String assignTo = reassignMailForm.getAssignToFlight();
		log.log(Log.FINE, "assignTo === ", assignTo);
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){

			log.log(Log.FINE, "*******FLIGHT MODE******");

			if(FLAG_YES.equals(reassignMailForm.getDuplicateFlightStatus())){
				log.log(Log.FINE, "flightValidationVO in duplicateFlightSession ...",
						duplicateFlightSession.getFlightValidationVO());	
				FlightValidationVO	flightValidationVOduplicate =	duplicateFlightSession.getFlightValidationVO();
				flightValidationVOduplicate.setDirection(OUTBOUND);
				reassignMailSession.setFlightValidationVO(flightValidationVOduplicate);
				reassignMailForm.setDuplicateFlightStatus(FLAG_NO);
			}
			log.log(Log.FINE, "flightValidationVO in MA session...",
					reassignMailSession.getFlightValidationVO());
			flightValidationVO = reassignMailSession.getFlightValidationVO();
			Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
			if(oneTimes!=null){
				Collection<OneTimeVO> resultStatus=
					oneTimes.get("flight.operation.flightlegstatus");
				log.log(Log.FINE, "*******flightlegstatus******");
				flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
			}
			reassignMailSession.setFlightValidationVO(flightValidationVO);
            
			/**
			 * ADDED FOR SAA 410 STARTS
			 */
			if(flightValidationVO != null){
    			if(flightValidationVO.isTBADueRouteChange()){
    				Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),reassignMailForm.getDepDate()};
    				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.reassignmail.msg.err.flighttobeactioned",obj);
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
				reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET;
				return;
	    	}
			boolean flightStatus=false;
	    	try{
	    	 
				  flightStatus=mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);
			  
	    	}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
	    	String status;
	    	log.log(Log.FINE, "flightStatus --------->>", flightStatus);
			if(flightStatus) {
				status=CLOSED;
			} else {
				status=OPEN;
			}
	    	if(flightValidationVO!=null && flightValidationVO.getFlightRoute()!=null){
	    		  if(status!=null && status.trim().length()>0){
			    	if(oneTimes!=null){
						Collection<OneTimeVO> resultStatus=oneTimes.get(FLTSTATUS);
						flightValidationVO.setOperationalStatus(findOneTimeDescription(resultStatus,status));			 		  
					}
	    		  }
	    		  reassignMailSession.setFlightValidationVO(flightValidationVO);
	    	}
	    	//Modified for icrd-93392 by A-4810
	    	 if(CLOSED.equals(status)) {
	    		 Object [] obj = {new StringBuilder().append(flightValidationVO.getCarrierCode()).append(operationalFlightVO.getFlightNumber()).toString(),
	    				 reassignMailForm.getDepDate()};
		    	ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.flightclosed",obj);
				errors = new ArrayList<ErrorVO>();
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				//reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.
						//SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET;
				return;
	    	 }
		}else{
			log.log(Log.FINE, "*******DESTINATION MODE******");

			DestinationFilterVO destinationFilterVO = new DestinationFilterVO();
			destinationFilterVO.setAirportCode(logonAttributes.getAirportCode());
			destinationFilterVO.setCarrierCode(reassignMailForm.getCarrierCode().toUpperCase());
			destinationFilterVO.setCarrierId(reassignMailForm.getCarrierId());
			destinationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			destinationFilterVO.setDestination(reassignMailForm.getDestination().toUpperCase());

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
			reassignMailSession.setDestinationFilterVO(destinationFilterVO);
			log.log(Log.FINE, "containervos ----->>", containerVOs);
			if (containerVOs == null || containerVOs.size() <= 0) {
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noresultsfound");
				errors = new ArrayList<ErrorVO>();
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET;
				return;
			}
		}
    	
    	reassignMailSession.setContainerVOs(containerVOs);
    	reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET;
    	log.exiting("ListMailAcceptanceCommand","execute");
    	
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
