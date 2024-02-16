/*
 * ListReassignDSNCommand.java Created on APR 01, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigndsn;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignDSNSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignDSNForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author RENO K ABRAHAM
 * Command class : ListReassignDSNCommand
 *
 * Revision History
 *
 * Version      	Date      	    Author        		Description
 *
 *  0.1         APR 01, 2008  	 RENO K ABRAHAM         Coding
 */
public class ListReassignDSNCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "list_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.reassigndsn";	
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String OUTBOUND = "O";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListReassignDSNCommand","execute");
    	  
    	ReassignDSNForm reassignDSNForm = 
    		(ReassignDSNForm)invocationContext.screenModel;
    	ReassignDSNSession reassignDSNSession = 
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
		 if(FLAG_YES.equals(reassignDSNForm.getDuplicateFlightStatus())){
			 reassignDSNForm.setAssignToFlight(CONST_FLIGHT);
		 }
		String assignTo = reassignDSNForm.getAssignToFlight();
		log.log(Log.FINE, "assignTo === ", assignTo);
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
			
			log.log(Log.FINE, "*******FLIGHT MODE******");
			
				if(FLAG_YES.equals(reassignDSNForm.getDuplicateFlightStatus())){
					reassignDSNSession.setFlightValidationVO(duplicateFlightSession.getFlightValidationVO());
					reassignDSNForm.setDuplicateFlightStatus(FLAG_NO);
				}
				log.log(Log.FINE, "flightValidationVO in MA session...",
						reassignDSNSession.getFlightValidationVO());
				flightValidationVO = reassignDSNSession.getFlightValidationVO();
				Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
				if(oneTimes!=null){
					Collection<OneTimeVO> resultStatus=
						oneTimes.get("flight.operation.flightlegstatus");
					log.log(Log.FINE, "*******flightlegstatus******");
					flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
				}
				reassignDSNSession.setFlightValidationVO(flightValidationVO);
		    	
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
	  			reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.
	  					SCREEN_STATUS_DETAIL);
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
				status="C";
			} else {
				status="O";
			}
	      	
			if(oneTimes!=null){
				Collection<OneTimeVO> resultStatus =
					oneTimes.get("mailtracking.defaults.flightstatus");
				flightValidationVO.setOperationalStatus(findOneTimeDescription(resultStatus,status));
			}
			
		}else{
			log.log(Log.FINE, "*******DESTINATION MODE******");
			
	            DestinationFilterVO destinationFilterVO = new DestinationFilterVO();
	        	destinationFilterVO.setAirportCode(logonAttributes.getAirportCode());
	        	destinationFilterVO.setCarrierCode(reassignDSNForm.getCarrierCode().toUpperCase());
	        	destinationFilterVO.setCarrierId(reassignDSNForm.getCarrierId());
	        	destinationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	        	destinationFilterVO.setDestination(reassignDSNForm.getDestination().toUpperCase());
	        	
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
				reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET;
				return;
	    	}
	    	  reassignDSNSession.setDestinationFilterVO(destinationFilterVO);
		}		
    	reassignDSNSession.setContainerVOs(containerVOs);
    	reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET;
    	log.exiting("ListReassignDSNCommand","execute");
    	
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
