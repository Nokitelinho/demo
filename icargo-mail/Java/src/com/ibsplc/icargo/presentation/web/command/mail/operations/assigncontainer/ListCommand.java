/*
 * ListCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ListCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String FLIGHT_MODULE_NAME = "flight.operation";
   private static final String FLIGHT_SCREEN_ID = "flight.operation.duplicateflight";
   private static final String SCREEN_IDACC = "mailtracking.defaults.mailacceptance";   
	
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String OUTBOUND = "O";
   private static final String OPEN = "O";
   private static final String CLOSED = "C";
   private static final String FLAG_YES = "YES";  
   private static final String FLAG_REASSIGN = "REASSIGN";
   private static final String FLAG_MAILACCEPTANCE = "MAILACCEPTANCE";
   private static final String FLAG_FROMMAILACCEPTANCE = "FROMMAILACCEPTANCE";
   private static final String FLAG_MAILBAGENQUIRY = "MAILBAGENQUIRY";
   private static final String FLTSTATUS = "mailtracking.defaults.flightstatus";
	
   private static final String TARGET_SUCCESS = "list_success";
   private static final String TARGET_FAILURE = "list_failure"; 
   private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListCommand","execute");
    	
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_IDACC);
    	
    	
    	DuplicateFlightSession duplicateFlightSession = (DuplicateFlightSession)getScreenSession(
				FLIGHT_MODULE_NAME, FLIGHT_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	// VALIDATING FORM
		errors = validateForm(assignContainerForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
    	
    	String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		AirlineDelegate airlineDelegate = new AirlineDelegate();
    	AirlineValidationVO airlineValidationVO = null;
    	
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
    	
    	Collection<ContainerVO> containervos = null;
    	
    	assignContainerSession.setPointOfLadings(null);
    	assignContainerSession.setContainerVOs(null);
    	assignContainerSession.setSelectedContainerVOs(null);    	 
    	assignContainerForm.setSubCheck(null);
    	
    	if (CONST_FLIGHT.equals(assignedto)) {    		
    		if(!FLAG_YES.equals(assignContainerForm.getStatus()) 
    				&& !FLAG_REASSIGN.equals(assignContainerForm.getStatus())
    				&& !FLAG_MAILACCEPTANCE.equals(assignContainerForm.getFromScreen())
    				&& !FLAG_FROMMAILACCEPTANCE.equals(assignContainerForm.getFromScreen())
    				&& !FLAG_MAILBAGENQUIRY.equals(assignContainerForm.getFromScreen())) {
    			
    			// VALIDATE FLIGHT CARRIER CODE
        		String flightCarrierCode = assignContainerForm.getFlightCarrierCode();        	
            	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
            		try {        			
            			airlineValidationVO = airlineDelegate.validateAlphaCode(
            					logonAttributes.getCompanyCode(),
            					flightCarrierCode.trim().toUpperCase());

            		}catch (BusinessDelegateException businessDelegateException) {
            			errors = handleDelegateException(businessDelegateException);
            		}
            		if (errors != null && errors.size() > 0) {            			
            			errors = new ArrayList<ErrorVO>();
            			Object[] obj = {flightCarrierCode.toUpperCase()};
        				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.assigncontainer.msg.err.invalidCarrier",obj);
    					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    					errors.add(errorVO);
            			invocationContext.addAllError(errors);
            			invocationContext.target = TARGET_FAILURE;
            			return;
            		}
            	}
        		
        		// VALIDATING FLIGHT NUMBER        		
        		FlightFilterVO flightFilterVO = handleFlightFilterVO(
    	    				assignContainerForm,
    						logonAttributes);
        		
        		flightFilterVO.setCarrierCode(flightCarrierCode.toUpperCase());
        		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
        		        		
        		Collection<FlightValidationVO> flightValidationVOs = null;

    			try {
    				log.log(Log.FINE, "FlightFilterVO ------------> ",
							flightFilterVO);
					flightValidationVOs =
    					mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

        		}catch (BusinessDelegateException businessDelegateException) {
        			errors = handleDelegateException(businessDelegateException);
        		}
        		if (errors != null && errors.size() > 0) {
        			invocationContext.addAllError(errors);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
        		Collection<FlightValidationVO> tempFlightValidationVOs = new ArrayList<FlightValidationVO>();
        		 //Added by A-5160 for ICRD-92869 starts
        		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
        			for (FlightValidationVO flightValidVO : flightValidationVOs) {
        				flightValidVO.setDirection(OUTBOUND);
        				try {
        					FlightValidationVO tempFlightValidationVO = new FlightValidationVO();
        					BeanHelper.copyProperties(tempFlightValidationVO,
        							flightValidVO);
        					tempFlightValidationVOs.add(tempFlightValidationVO);
        				} catch (SystemException e) {
        					e.getMessage();
        				}
        				//TBA validation removed by  A-5945 for ICRD 129952
        				if(FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())){
        					flightValidVO.setFlightRoute(null);
        					flightValidVO.setAircraftType(null);
        					flightValidVO.setEta(null);
        					flightValidVO.setEtd(null);
        					flightValidVO.setSta(null);
        					flightValidVO.setStd(null);
        					flightValidVO.setAta(null);
        					flightValidVO.setAtd(null);
        				}
        			}
        		}
        		 //Added by A-5160 for ICRD-92869 ends
        		//Added by A-5160 to find the flights from mail table when listed from a different airport due to route change
        		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
        			try {
        				log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
        				flightValidationVOs =
        					mailTrackingDefaultsDelegate.validateMailFlight(flightFilterVO);
        				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
        					for (FlightValidationVO flightValidVO : flightValidationVOs) {
        						flightValidVO.setFlightStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
        						flightValidVO.setLegStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
        					}
        				}
        			}catch (BusinessDelegateException businessDelegateException) {
        				errors = handleDelegateException(businessDelegateException);
        			}
        		}
    			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {//IF NO RESULTS RETURNED
    				log.log(Log.FINE, "----------------FlightValidationVOs is NULL");
    				Object[] obj = {assignContainerForm.getFlightCarrierCode().toUpperCase(),
    						assignContainerForm.getFlightNumber().toUpperCase(),
    						assignContainerForm.getFlightDate()};
    				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.assigncontainer.msg.err.noflightDetails",obj);
    				errors = new ArrayList<ErrorVO>();
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(errorVO);
    				invocationContext.addAllError(errors);
    				invocationContext.target = TARGET_FAILURE;
    				return;
    			}
    			else if ( flightValidationVOs.size() == 1) { //IF ONLY 1 VO RETURNED        				
					log.log(Log.FINE, "--------------FlightValidationVOs has one VO");
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						//A-5249 from ICRD-84046					
						/*if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ||
								FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())){
							if(!FlightValidationVO.FLAG_YES.equals(assignContainerForm.getOverrideFlag())){
							ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
							err.setErrorDisplayType(ErrorDisplayType.WARNING);
							invocationContext.addError(err);
							invocationContext.target = TARGET_FAILURE;
							assignContainerForm.setWarningFlag(FLIGHT_TBC_TBA);
							return;
							}else{
								assignContainerForm.setWarningFlag("");
								assignContainerForm.setOverrideFlag(null);
							}
							assignContainerForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
						}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus())){
							Object[] obj = {flightCarrierCode.toUpperCase(),flightValidVO.getFlightNumber()};
							ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
							err.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(err);
							invocationContext.target = TARGET_FAILURE;
							return;
						} */
						/*
						 * setting the flight status description to flightVlaidationVO
						 * as part of the new flight display information component
						 */
						flightValidVO.setStatusDescription(getStatusDescription(
								flightValidVO.getLegStatus(),assignContainerSession));
						flightValidVO.setDirection(OUTBOUND);
						assignContainerSession.setFlightValidationVO(flightValidVO);
						break;
					}
				}
    			else if(flightValidationVOs.size() > 1){
    				if(!MailConstantsVO.FLAG_YES.equals(assignContainerForm.getDuplicateAndTbaTbc())){
    				//IF MORE VOS RETURNED
					log.log(Log.FINE, "--------------Duplicate flight VO");
					duplicateFlightSession.setFlightValidationVOs(
							(ArrayList<FlightValidationVO>)tempFlightValidationVOs);
					duplicateFlightSession.setParentScreenId(SCREEN_ID);
					duplicateFlightSession.setFlightFilterVO(flightFilterVO);  
					duplicateFlightSession.setScreenOfParent("assignContainer");
					assignContainerForm.setStatus("showDuplicateFlights");
					invocationContext.target = TARGET_FAILURE;
					
				}
					return;
				}
    		}
    		
    		
    		if(FLAG_MAILACCEPTANCE.equals(assignContainerForm.getFromScreen()))
    		{
    			 assignContainerSession.setFlightValidationVO( mailAcceptanceSession.getFlightValidationVO());
    		}
    		
			// Added by A-5153 for BUG_ICRD-90012
			if (FLAG_MAILBAGENQUIRY.equals(assignContainerForm.getFromScreen())) {
				FlightValidationVO validationVO = assignContainerSession.getFlightValidationVO();
				//TBA validation removed by  A-5945 for ICRD 129952
				if ( FlightValidationVO.FLT_LEG_STATUS_TBC.equals(validationVO.getFlightStatus())) {
					assignContainerForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
				}
			}

    		// FINDING THE ASSIGNED CONTAINERS
    		try {
    			containervos = handleFlightDetails(
			        				duplicateFlightSession,
			        				assignContainerSession,
			        				assignContainerForm,
			        				mailTrackingDefaultsDelegate,
			        				logonAttributes);
    			
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}   		
    		
    	}
    	else {
    		assignContainerSession.setFlightValidationVO(null);
    		
    		if (!FLAG_MAILACCEPTANCE.equals(assignContainerForm.getFromScreen())
    			&& !FLAG_MAILBAGENQUIRY.equals(assignContainerForm.getFromScreen())
    			&& !FLAG_FROMMAILACCEPTANCE.equals(assignContainerForm.getFromScreen())) {
    			
    			Collection<ErrorVO> destnErrors = new ArrayList<ErrorVO>();
        		
        		// VALIDATE CARRIER CODE
            	String carrierCode = assignContainerForm.getCarrier();        	
            	if (carrierCode != null && !"".equals(carrierCode)) {        		
            		try {        			
            			airlineValidationVO = airlineDelegate.validateAlphaCode(
            					logonAttributes.getCompanyCode(),
            					carrierCode.toUpperCase());

            		}catch (BusinessDelegateException businessDelegateException) {
            			errors = handleDelegateException(businessDelegateException);
            		}
            		if (errors != null && errors.size() > 0) {        			
            			errors = new ArrayList<ErrorVO>();
            			Object[] obj = {carrierCode.toUpperCase()};
        				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.assigncontainer.msg.err.invalidCarrier",obj);
    					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    					errors.add(errorVO);
            			destnErrors.addAll(errors);
            		}
            		else {
            			log.log(Log.FINE, "AirlineValidationVO--------------",
								airlineValidationVO);
						assignContainerSession.setAirlineValidationVO(airlineValidationVO);
            		}
            	}
            	
            	// VALIDATE DESTINATION 
            	String destn = assignContainerForm.getDestn();            	
            	AreaDelegate areaDelegate = new AreaDelegate();
            	AirportValidationVO airportValidationVO = null;
            	errors = null;
            	if (destn != null && !"".equals(destn)) {
            		try {
            			airportValidationVO = areaDelegate.validateAirportCode(
            					logonAttributes.getCompanyCode(),
            					destn.toUpperCase());
            			            			
            		}catch (BusinessDelegateException businessDelegateException) {
                		errors = handleDelegateException(businessDelegateException);
            		}
            		if (errors != null && errors.size() > 0) {
            			destnErrors.addAll(errors);
            		}
            	} 
            	
            	if (destnErrors != null && destnErrors.size() > 0) {
        			invocationContext.addAllError(destnErrors);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
    		}
    		
    		if(FLAG_MAILACCEPTANCE.equals(assignContainerForm.getFromScreen()))
    		{
    			MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
    			AirlineValidationVO airlineValidationVO1 = new AirlineValidationVO();
        		airlineValidationVO1.setAirlineIdentifier(mailAcceptanceVO.getCarrierId());
        		airlineValidationVO1.setAlphaCode(mailAcceptanceVO.getFlightCarrierCode());
        		assignContainerSession.setAirlineValidationVO(airlineValidationVO1);
    		}

    		
        	// FINDING THE ASSIGNED CONTAINERS
        	try {
    			containervos = handleDestinationDetails(
			        				assignContainerForm,
			        				logonAttributes,
			        				mailTrackingDefaultsDelegate,
			        				assignContainerSession.getAirlineValidationVO());
    			
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
        	
    	}
    	
    	log.log(Log.FINE, "containervos ----->>", containervos);
		if (containervos == null || containervos.size() <= 0) {
			if(assignContainerSession.getFlightValidationVO()!=null){
				FlightValidationVO flightValidationVO =assignContainerSession.getFlightValidationVO();
				/*if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())){
					Object[] obj = {assignContainerForm.getFlightCarrierCode().trim().toUpperCase(),flightValidationVO.getFlightNumber()};
					ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightintba",obj);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);					
					invocationContext.addError(err);
    				invocationContext.target = TARGET_FAILURE;
    				return;
				}				
				else if(FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())){
					Object[] obj = {assignContainerForm.getFlightCarrierCode().trim().toUpperCase(),flightValidationVO.getFlightNumber()};
					ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightintbc",obj);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);					
					invocationContext.addError(err);
					invocationContext.target = TARGET_FAILURE;
					return;
				}			
				else */
				if(	FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())){
					assignContainerForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
					if(!FlightValidationVO.FLAG_YES.equals(assignContainerForm.getOverrideFlag()) && !FLAG_REASSIGN.equals(assignContainerForm.getStatus())){
						ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
						err.setErrorDisplayType(ErrorDisplayType.WARNING);
						invocationContext.addError(err);
						invocationContext.target = TARGET_FAILURE;
						assignContainerForm.setWarningFlag(FLIGHT_TBC_TBA);                         
						return;
					}/*else{
						assignContainerForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_NO);                     
					}*/
				}
				//TBA validation removed by  A-5945 for ICRD 129952
				if(	FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())){
					if(!FlightValidationVO.FLAG_YES.equals(assignContainerForm.getOverrideFlag()) && !FLAG_REASSIGN.equals(assignContainerForm.getStatus())){
						flightValidationVO.setFlightRoute(null);
						flightValidationVO.setAircraftType(null);
						flightValidationVO.setEta(null);
						flightValidationVO.setEtd(null);
						flightValidationVO.setSta(null);
						flightValidationVO.setStd(null);
						flightValidationVO.setAta(null);
						flightValidationVO.setAtd(null);
					ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
					err.setErrorDisplayType(ErrorDisplayType.WARNING);
					invocationContext.addError(err);
					invocationContext.target = TARGET_FAILURE;
					assignContainerForm.setWarningFlag(FLIGHT_TBC_TBA);
					if(assignContainerSession.getFlightValidationVO().getOperationalStatus()!=null){
						assignContainerSession.getFlightValidationVO().setOperationalStatus(null);
    				}
    				if(assignContainerSession.getFlightValidationVO().getStatusDescription()!=null){
    					assignContainerSession.getFlightValidationVO().setStatusDescription(null);
    				}
					return;
					}else{
						assignContainerForm.setWarningFlag("");
						assignContainerForm.setOverrideFlag(null);
						flightValidationVO.setFlightRoute(null);
						flightValidationVO.setAircraftType(null);
						flightValidationVO.setEta(null);
						flightValidationVO.setEtd(null);
						flightValidationVO.setSta(null);
						flightValidationVO.setStd(null);
						flightValidationVO.setAta(null);
						flightValidationVO.setAtd(null);
					}
					if(FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())){
					assignContainerForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
					}
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.noContainersAssigned");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(errorVO);
					invocationContext.target = TARGET_FAILURE;
					return;
				}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus())){
					Object[] obj = {assignContainerForm.getFlightCarrierCode().toUpperCase(),flightValidationVO.getFlightNumber()};
					ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(err);
					invocationContext.target = TARGET_FAILURE;
					return;
				} 
			}
    		ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.assigncontainer.msg.err.noContainersAssigned");
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			assignContainerForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_DETAIL);
			invocationContext.target = TARGET_FAILURE;
			return;
    	}
		if(assignContainerSession.getFlightValidationVO()!=null){
			FlightValidationVO flightValidationVO =assignContainerSession.getFlightValidationVO();
			if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())){
				
				if(!FlightValidationVO.FLAG_YES.equals(assignContainerForm.getOverrideFlag())&& !FLAG_REASSIGN.equals(assignContainerForm.getStatus())){
					ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");        
					err.setErrorDisplayType(ErrorDisplayType.WARNING);
					invocationContext.addError(err);
					invocationContext.target = TARGET_FAILURE;
					assignContainerForm.setWarningFlag(FLIGHT_TBC_TBA);
					return;      
				}else{
					assignContainerForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_NO);
				}
				}
			//TBA validation removed by  A-5945 for ICRD 129952
			if(FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())){
				if(!FlightValidationVO.FLAG_YES.equals(assignContainerForm.getOverrideFlag())&& !FLAG_REASSIGN.equals(assignContainerForm.getStatus())){
					flightValidationVO.setFlightRoute(null);
					flightValidationVO.setAircraftType(null);
					flightValidationVO.setEta(null);
					flightValidationVO.setEtd(null);
					flightValidationVO.setSta(null);
					flightValidationVO.setStd(null);
					flightValidationVO.setAta(null);
					flightValidationVO.setAtd(null);
				ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
				err.setErrorDisplayType(ErrorDisplayType.WARNING);
				invocationContext.addError(err);
				invocationContext.target = TARGET_FAILURE;
				assignContainerForm.setWarningFlag(FLIGHT_TBC_TBA);
				if(assignContainerSession.getFlightValidationVO().getOperationalStatus()!=null){
					assignContainerSession.getFlightValidationVO().setOperationalStatus(null);
				}
				if(assignContainerSession.getFlightValidationVO().getStatusDescription()!=null){
					assignContainerSession.getFlightValidationVO().setStatusDescription(null);
				}
				return;
				}else{
					assignContainerForm.setWarningFlag("");
					assignContainerForm.setOverrideFlag(null);
					flightValidationVO.setFlightRoute(null);
					flightValidationVO.setAircraftType(null);
					flightValidationVO.setEta(null);
					flightValidationVO.setEtd(null);
					flightValidationVO.setSta(null);
					flightValidationVO.setStd(null);
					flightValidationVO.setAta(null);
					flightValidationVO.setAtd(null);
				}
				if(FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())){
				assignContainerForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
				}
			}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus())){
				Object[] obj = {assignContainerForm.getFlightCarrierCode().toUpperCase(),flightValidationVO.getFlightNumber()};
				ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(err);
				invocationContext.target = TARGET_FAILURE;
				return;
			} 
		}
		
    	
    	assignContainerSession.setContainerVOs(containervos);
    	 //Added by A-5160 for ICRD-92869 starts
    	HashMap<String, Collection<String>> polPouMap = new HashMap<String, Collection<String>>();
		if(containervos!=null && containervos.size()>0){			
			for(ContainerVO containerVO : containervos){					
				 Collection<String> pous = new ArrayList<String>();
				 if(containerVO.getAssignedPort()!=null && containerVO.getPou()!=null){
					if(polPouMap.containsKey(containerVO.getAssignedPort())){
						pous = polPouMap.get(containerVO.getAssignedPort());
						if(!pous.contains(containerVO.getPou())){
							pous.add(containerVO.getPou());
							polPouMap.put(containerVO.getAssignedPort(), pous);
						}
					}
					else{
						pous = new ArrayList<String>();
						pous.add(containerVO.getPou());
						polPouMap.put(containerVO.getAssignedPort(), pous);
					}
				 }
				 assignContainerSession.setPolPouMap(polPouMap);
			}			
		}
		 //Added by A-5160 for ICRD-92869 ends
log.log(Log.FINE, "FlightValidationVO --------->>",
				assignContainerSession.getFlightValidationVO());
		if(assignContainerSession.getFlightValidationVO()!=null){
    	FlightValidationVO flightValidationVO =assignContainerSession.getFlightValidationVO();
    	// CREATING FILTER VO FOR LISTING
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	//operationalFlightVO.setFlightDate(flightValidationVO.getFlightDate());
    	LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
    	operationalFlightVO.setFlightDate(date.setDate(
 				assignContainerForm.getFlightDate()));
    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())){
    		for(ContainerVO containerVO : containervos)
    			{
    			operationalFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
    			}
    		assignContainerForm.setDisableButtonsForTBA("Y");
    	}
    	else
    	{
    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	}
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	//operationalFlightVO.setPou();
    	
    	log.log(Log.FINE, "OperationalFlightVO --------->>",
				operationalFlightVO);
		boolean flightStatus=false;
    	try{
    	  
			  flightStatus=mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);
		  
    	}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
    	Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
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
    	assignContainerSession.setFlightValidationVO(flightValidationVO);
    }
 // 	added to display the operational status in the component-END
    	
    	
    	assignContainerForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET_SUCCESS;
    	log.exiting("ListCommand","execute");
    	
    }
    /**
     * Method for finding the containers assigned to destination
     * @param assignContainerForm
     * @param logonAttributes
     * @param mailTrackingDefaultsDelegate
     * @param airlineValidationVO
     * @return Collection<ContainerVO>
     * @throws BusinessDelegateException
     */
    public Collection<ContainerVO> handleDestinationDetails(
    		AssignContainerForm assignContainerForm,
    		LogonAttributes logonAttributes,
    		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate,
    		AirlineValidationVO airlineValidationVO) 
    		throws BusinessDelegateException{
    	
    	log.entering("ListCommand","handleDestinationDetails");   
    	
    	// CREATING FILTER VO FOR LISTING
    	DestinationFilterVO destinationFilterVO = new DestinationFilterVO();
    	destinationFilterVO.setAirportCode(logonAttributes.getAirportCode());
    	destinationFilterVO.setCarrierCode(assignContainerForm.getCarrier().toUpperCase());
    	destinationFilterVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
    	destinationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	destinationFilterVO.setDestination(assignContainerForm.getDestn().toUpperCase());
    	
    	log.log(Log.FINE, "DestinationFilterVO ----->>", destinationFilterVO);
    	
    	return mailTrackingDefaultsDelegate.findDestinationAssignedContainers(destinationFilterVO);    	    	
    }
    /**
     * Method for finding the containers assigned to flight
     * @param duplicateFlightSession
     * @param assignContainerSession
     * @param assignContainerForm
     * @param mailTrackingDefaultsDelegate
     * @param logonAttributes
     * @return Collection<ContainerVO>
     * @throws BusinessDelegateException
     */
    private Collection<ContainerVO> handleFlightDetails(
    		DuplicateFlightSession duplicateFlightSession,
    		AssignContainerSession assignContainerSession,
    		AssignContainerForm assignContainerForm,
    		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate,
    		LogonAttributes logonAttributes) 
    		throws BusinessDelegateException{
    	
    	log.entering("ListCommand","handleFlightDetails");
    	
    	log
				.log(Log.FINE, "Status --------->>", assignContainerForm.getStatus());
		FlightValidationVO flightValidationVO = null;
    	
    	if(FLAG_YES.equals(assignContainerForm.getStatus())) {
    		flightValidationVO = duplicateFlightSession.getFlightValidationVO();
    		/*
			 * setting the flight status description to flightVlaidationVO
			 * as part of the new flight display information component
			 */
			flightValidationVO.setStatusDescription(getStatusDescription(
					flightValidationVO.getLegStatus(),assignContainerSession));
    		flightValidationVO.setDirection(OUTBOUND);
    		assignContainerSession.setFlightValidationVO(flightValidationVO);
    	}
    	else {
    		flightValidationVO = assignContainerSession.getFlightValidationVO();
    	}   	
    	
    	log.log(Log.FINE, "FlightValidationVO --------->>",
				assignContainerSession.getFlightValidationVO());
		// CREATING FILTER VO FOR LISTING
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	//operationalFlightVO.setFlightDate(flightValidationVO.getFlightDate());
    	LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
    	operationalFlightVO.setFlightDate(date.setDate(
 				assignContainerForm.getFlightDate()));
    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	//operationalFlightVO.setPou();
    	
    	log.log(Log.FINE, "OperationalFlightVO --------->>",
				operationalFlightVO);
    	
    	return mailTrackingDefaultsDelegate.findFlightAssignedContainers(operationalFlightVO);
    	
    }
    /**
     * Method to create the filter vo for flight validation
     * @param assignContainerForm
     * @param logonAttributes
     * @return FlightFilterVO
     */
    private FlightFilterVO handleFlightFilterVO(
    		AssignContainerForm assignContainerForm,
			LogonAttributes logonAttributes){

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(assignContainerForm.getFlightNumber().trim().toUpperCase());
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setStringFlightDate(assignContainerForm.getFlightDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(
 				assignContainerForm.getFlightDate()));
		return flightFilterVO;
	}
    
    /**
	 * Method to obtain the statusDescription from the
	 * code obtained. The method will iterate through the
	 * onetime values to find the statusDescription
	 * @param statusCode
	 * @param assignContainerSession
	 * @return statusDescription
	 */
	private String getStatusDescription(
			String statusCode,
			AssignContainerSession assignContainerSession){

		log.entering("ListCommand", "getStatusDescription");
		
    	String statusDescription = null;
    	/*
    	 * Obtain the statusDescription from oneTimeVOs
    	 */
    	
    	Collection<OneTimeVO> oneTimeVOs
    		= assignContainerSession.getFlightStatus();
    	for(OneTimeVO onetimeVO : oneTimeVOs) {
    		if(onetimeVO.getFieldDescription() != null &&
    				onetimeVO.getFieldValue().equals(statusCode)) {
    			statusDescription = onetimeVO.getFieldDescription();
    		}
    	}
    	log.log(Log.FINE, "statusDescription ---> ", statusDescription);
		log.exiting("ListCommand", "getStatusDescription");
    	
    	return statusDescription;
	}
	
	/**
	 * This method is used for validating the form for the particular action
	 * @param assignContainerForm - AssignContainerForm
	 * @param logonAttributes - LogonAttributes
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			AssignContainerForm assignContainerForm,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		
		String assignedto = assignContainerForm.getAssignedto(); 
		
		
		if (CONST_FLIGHT.equals(assignedto)) {    		
			if (assignContainerForm.getFlightCarrierCode() == null
					|| ("").equals(assignContainerForm.getFlightCarrierCode())) {
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.noFlightCarrierCode");
				formErrors.add(errorVO);
			}
			if (assignContainerForm.getFlightNumber() == null
					|| ("").equals(assignContainerForm.getFlightNumber())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.noFlightNumber");
				formErrors.add(errorVO);
			}
			if (assignContainerForm.getFlightDate() == null
					|| ("").equals(assignContainerForm.getFlightDate())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.noFlightDate");
				formErrors.add(errorVO);
			}
		}
		else {
			if (assignContainerForm.getCarrier() == null
					|| ("").equals(assignContainerForm.getCarrier())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.noCarrier");
				formErrors.add(errorVO);
			}
			if (assignContainerForm.getDestn() == null
					|| ("").equals(assignContainerForm.getDestn())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.noDestination");
				formErrors.add(errorVO);
			}
			if (assignContainerForm.getDestn() != null
					&& !("").equals(assignContainerForm.getDestn())) {
		        if(logonAttributes.getAirportCode().equalsIgnoreCase(assignContainerForm.getDestn())){
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.DestinationEqualsCurrentAirport");
					formErrors.add(errorVO);
			    }
			}
		}

		return formErrors;
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
