/*
 * SaveContainerCommand.java Created on June 22, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigncontainer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1861
 *
 */
public class SaveContainerCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.reassignContainer";
   private static final String FLIGHT_MODULE_NAME = "flight.operation";
   private static final String FLIGHT_SCREEN_ID = "flight.operation.duplicateflight";
   
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_YES = "YES";
   private static final String OUTBOUND = "O";
   private static final String ROUTE_DELIMETER = "-";
   private static final String TARGET_SUCCESS = "save_reassign_success";
   private static final String TARGET_FAILURE = "save_reassign_failure"; 
       
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SaveContainerCommand","execute");
    	  
    	ReassignContainerForm reassignContainerForm = 
    		(ReassignContainerForm)invocationContext.screenModel;
    	ReassignContainerSession reassignContainerSession = 
    		(ReassignContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,"mailtracking.defaults.assignContainer");
    	
    	DuplicateFlightSession duplicateFlightSession = (DuplicateFlightSession)getScreenSession(
				FLIGHT_MODULE_NAME, FLIGHT_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	if(reassignContainerForm.getScanDate()==null || ("").equals(reassignContainerForm.getScanDate())){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate"));
 	   		invocationContext.target =TARGET_FAILURE; 
 	   		return; 
		}
		if(reassignContainerForm.getMailScanTime()==null ||("").equals(reassignContainerForm.getMailScanTime())){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime"));
 	   		invocationContext.target =TARGET_FAILURE; 
 	   		return; 
		}
		String scanDate= new StringBuilder().append(reassignContainerForm.getScanDate()).append(" ").append(reassignContainerForm.getMailScanTime()).append(":00").toString();
	    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    scanDat.setDateAndTime(scanDate);
    	
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
    	
    	String reassignedto = reassignContainerForm.getReassignedto();
    	log.log(Log.FINE, "REASSIGNED TO ------------> ", reassignedto);
		Collection<ContainerVO> selectedContainerVOs = reassignContainerSession.getSelectedContainerVOs();
    	ContainerVO currentvo = reassignContainerSession.getContainerVO();
    	
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	operationalFlightVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
    	operationalFlightVO.setOwnAirlineId(
    			logonAttributes.getOwnAirlineIdentifier());
    	operationalFlightVO.setOperator(logonAttributes.getUserId());
    	
    	operationalFlightVO.setOperationTime(scanDat);
    	
    	// IF REASSIGNING TO FLIGHT
    	if (CONST_FLIGHT.equals(reassignedto)) {  
    		FlightValidationVO flightValidationVO = null;
    		
    		if (CONST_YES.equals(reassignContainerForm.getStatus())) {
    			flightValidationVO = duplicateFlightSession.getFlightValidationVO();
    		}
    		else {
    			flightValidationVO = reassignContainerSession.getFlightValidationVO(); 
    		}
    		log.log(Log.FINE, "FlightValidationVO ------------> ",
					flightValidationVO);
			// validating whether the container is already assigned to same flight
    		String assignedto = reassignContainerForm.getAssignedto();
    		log.log(Log.FINE, "assignedto ------------> ", assignedto);
			if (CONST_FLIGHT.equals(assignedto)) {
    			errors = isReassignedToSameFlight(
    					flightValidationVO,
    					selectedContainerVOs,
    					reassignContainerForm);
        		if (errors != null && errors.size() > 0) {      			
        			invocationContext.addAllError(errors);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
        		/**
        		 * Added for SA 410 STARTS
        		 */
        		if(flightValidationVO != null){
        			if(flightValidationVO.isTBADueRouteChange()){
        				Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),reassignContainerForm.getFlightDate()};
        				ErrorVO errorVO = new ErrorVO(
        						"mailtracking.defaults.reassigncontainer.msg.err.flighttobeactioned",obj);
        				if(errors == null){
        					errors = new ArrayList<ErrorVO>();
        				}
        				errors.add(errorVO);
        				invocationContext.addAllError(errors);
        				invocationContext.target = TARGET_FAILURE;
        				return;
        			}
        		}
        		/**
        		 * Added for SA 410 ENDS
        		 */
    		}
    		
    		// validating whether pou is present in the onward routes of flight
    		errors = isPouValid(
    				reassignContainerForm,
    				flightValidationVO,
    				logonAttributes);
    		//Added as part of ICRD-115893 starts
    		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
        	try{
            	containerVOs = mailTrackingDefaultsDelegate
        		.findAllULDsInAssignedFlight(flightValidationVO);
            	}catch (BusinessDelegateException businessDelegateException) {
            		errors = handleDelegateException(businessDelegateException);
        			log.log(Log.INFO,"ERROR--SERVER------isReassignedToOldFlight---->>");
        			invocationContext.addAllError(errors);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
    		errors = isReassignedToOldFlight(
    				containerVOs,
    				selectedContainerVOs);
    		//Added as part of ICRD-115893 ends
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    		
    		//Added by A-7540
    		//Validating if the containers are already present in the flight with different container type
    		Collection<ContainerVO> newContainerVOs = new ArrayList<ContainerVO>();
        	try{
        		newContainerVOs = mailTrackingDefaultsDelegate
        		.findAllContainersInAssignedFlight(flightValidationVO);
            	}catch (BusinessDelegateException businessDelegateException) {
            		errors = handleDelegateException(businessDelegateException);
        			log.log(Log.INFO,"ERROR--SERVER------isReassignedToOldFlight---->>");
        			invocationContext.addAllError(errors);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
        	errors = isReassignedWithSameContainer(
        			newContainerVOs,
    				selectedContainerVOs);
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    		
    		for (ContainerVO vo : selectedContainerVOs) {
				vo.setOnwardRoutings(currentvo.getOnwardRoutings());	
				vo.setRemarks(reassignContainerForm.getRemarks());
				vo.setReassignFlag(true);
				vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
				vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				vo.setMailSource(reassignContainerForm.getNumericalScreenId());//Added for ICRD-156218
    		}
    		
    		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
        	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());    	
        	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
        	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
        	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
        	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
        	operationalFlightVO.setPou(reassignContainerForm.getFlightPou().toUpperCase());
        	
			if(flightValidationVO.getAtd() != null){
				operationalFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
			}
    		
    	}
    	// IF REASSIGNING TO DESTINATION
    	else {
    		AirlineValidationVO airlineValidationVO = 
    			reassignContainerSession.getAirlineValidationVO();
    		log.log(Log.FINE, "AirlineValidationVO ------------> ",
					airlineValidationVO);
			operationalFlightVO.setCarrierCode(airlineValidationVO.getAlphaCode());
        	operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());  
        	operationalFlightVO.setFlightDate(null);
        	operationalFlightVO.setFlightNumber("-1");
        	operationalFlightVO.setFlightSequenceNumber(-1);
        	operationalFlightVO.setLegSerialNumber(-1);
        	operationalFlightVO.setPou(reassignContainerForm.getDestination().toUpperCase());
        	
        	for (ContainerVO vo : selectedContainerVOs) {					
				vo.setRemarks(reassignContainerForm.getRemarks());
				vo.setReassignFlag(true);
				vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
				vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				vo.setMailSource(reassignContainerForm.getNumericalScreenId());//Added for ICRD-156218
				vo.setFinalDestination(reassignContainerForm.getDestination());//Added for ICRD-134048
    		}
    	}
    	/*
    	boolean isFromInventory = false;
    	if(("INVENTORYLIST").equals(reassignContainerForm.getFromScreen())){
    		isFromInventory = true;
    		for (ContainerVO vo : selectedContainerVOs) {	
    			vo.setFinalDestination(reassignContainerForm.getFlightPou().toUpperCase());
    		}    		
    	}*/
    	
    	try {
    		log.log(Log.FINE, "selectedContainerVOs for saving-------> ",
					selectedContainerVOs);
			log.log(Log.FINE, "operationalFlightVO for saving-------> ",
					operationalFlightVO);
				mailTrackingDefaultsDelegate.reassignContainers(
	    				selectedContainerVOs,
	    				operationalFlightVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors =
		handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);			
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		assignContainerSession.setReassignStatus("SUCCESS");    	
    	reassignContainerForm.setStatus("closeWindow");
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("SaveContainerCommand","execute");
    	
    }
    /**
     * Method to validate whether the container is reassigned to same flight segment
     * @param reassignedFlightValidationVO
     * @param selectedContainerVOs
     * @param reassignContainerForm
     * @return Collection<ErrorVO>
     */
    private Collection<ErrorVO> isReassignedToSameFlight(
    		FlightValidationVO reassignedFlightValidationVO,
    		Collection<ContainerVO> selectedContainerVOs,
    		ReassignContainerForm reassignContainerForm) {
    	
    	log.entering("SaveContainerCommand","isReassignedToSameFlight");
    	
    	boolean isSameFlight = false;
    	StringBuilder errorcode = new StringBuilder("");
    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();    	
    	log.log(Log.FINE, "ReassignedFlightValidationVO-------> ",
				reassignedFlightValidationVO);
		for (ContainerVO selectedvo : selectedContainerVOs) {
    		if (!("-1").equals(selectedvo.getFlightNumber())) {
    			if ((reassignedFlightValidationVO.getFlightCarrierId() == selectedvo.getCarrierId())
            			&& (reassignedFlightValidationVO.getFlightNumber().equals(selectedvo.getFlightNumber()))
            			&& (reassignedFlightValidationVO.getLegSerialNumber() == selectedvo.getLegSerialNumber())
            			&& (reassignedFlightValidationVO.getFlightSequenceNumber() == selectedvo.getFlightSequenceNumber())
            			&& (reassignContainerForm.getFlightPou().equalsIgnoreCase(selectedvo.getPou()))
        			) {
    				errorcode.append(selectedvo.getContainerNumber()).append(",");
    				isSameFlight = true;
            	}    			
    		}    		    		
    	}
    	
    	log.log(Log.FINE, "isSameFlight-------> ", isSameFlight);
		log.log(Log.FINE, "errorcode-------> ", errorcode);
		if (isSameFlight) {
    		Object[] obj = {errorcode.substring(0,errorcode.length()-1)};
    		ErrorVO errorVO = new ErrorVO(
    				"mailtracking.defaults.reassigncontainer.msg.err.cannotReassignToSameFlight",obj);
    		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    		validationerrors.add(errorVO);
    	}
    	  	
    	return validationerrors;
    }
    
    /**
     * Method is used to find whether the flight POU is there in
     * the flight route
     * @param reassignContainerForm
     * @param flightValidationVO
     * @param logonAttributes
     * @return Collection<ErrorVO>
     */
    private Collection<ErrorVO> isPouValid(
    		ReassignContainerForm reassignContainerForm,
    		FlightValidationVO flightValidationVO,
    		LogonAttributes logonAttributes) {
    	
    	log.entering("SaveContainerCommand","isPouValid");
    	
    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
    	
    	/*
		 * Obtain the collection of POUs
		 */
		Collection<String> pointOfUnladings =
			getPointOfUnladings(
					flightValidationVO.getFlightRoute(), logonAttributes);	
		StringBuilder fullroute = new StringBuilder("");
		for (String route : pointOfUnladings) {
			fullroute.append(route).append("-");
		}
		
		if(!pointOfUnladings.contains(reassignContainerForm.getFlightPou().toUpperCase())) {
			Object[] obj = {reassignContainerForm.getFlightPou().toUpperCase(),
					flightValidationVO.getCarrierCode(),
					flightValidationVO.getFlightNumber(),
					fullroute.substring(0,fullroute.length()-1)};
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.invalidFlightpou",obj);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);			
			validationerrors.add(errorVO);
		}
    	
    	return validationerrors;
    }
    /**
	 * The method will obtain the collection of POUs
	 * from the route
	 * @param route
	 * @param logonAttributes
	 * @return pointOfUnladings
	 */
	private Collection<String> getPointOfUnladings(
			String route, LogonAttributes logonAttributes) {
		log.exiting("SaveContainerCommand", "getPOUs");

		Collection<String> pointOfUnladings = new ArrayList<String>();
		/*
		 * Split the route string using the delimeter "-"
		 */
		String[] stations = route.split(ROUTE_DELIMETER);
		if(route != null && route.length() > 0){

			for(int index=1; index < stations.length; index++) {
				/*
				 * the pous should start from the current station
				 */
				pointOfUnladings.add(stations[index]);
			}
		}
		log.log(Log.FINE, "pointOfUnladings ---> ", pointOfUnladings);
		log.exiting("SaveContainerCommand", "getPOUs");
		
		return pointOfUnladings;
	}
	/**
	 * Added as part of ICRD-115893
	 * Method is used to find whether the flight being reassigned to Already has the container
	 * @param reassignedFlightValidationVO
	 * @param selectedContainerVOs
	 * @return
	 */
	private Collection<ErrorVO> isReassignedToOldFlight(
			Collection<ContainerVO> containerVOs,
    		Collection<ContainerVO> selectedContainerVOs) {
    	log.entering("SaveContainerCommand","isReassignedToOldFlight");
    	boolean isOldFlight = false;
    	boolean diffContType = false;
    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();  
    	if(containerVOs!=null && containerVOs.size()>0){
		for (ContainerVO containerVO : containerVOs) {
			if(selectedContainerVOs!=null && selectedContainerVOs.size()>0){
				for (ContainerVO selectedvo : selectedContainerVOs) {
					if (!("-1").equals(selectedvo.getFlightNumber())) {
						if(selectedvo.getContainerNumber()!=null && selectedvo.getContainerNumber().trim().length()>0 &&
						   selectedvo.getAssignedPort()!=null && selectedvo.getAssignedPort().trim().length()>0){
			if(selectedvo.getContainerNumber().equalsIgnoreCase(containerVO.getContainerNumber())&&
					selectedvo.getAssignedPort().equalsIgnoreCase(containerVO.getAssignedPort())){
    				isOldFlight = true;
					break;
								}
							}
						} 
					}
				}
			}
    	}
    	log.log(Log.FINE, "isSameFlight-------> ", isOldFlight);
		if (isOldFlight) {
    		ErrorVO errorVO = new ErrorVO(
    				"mailtracking.defaults.reassigncontainer.msg.err.containeralreadypresentinanotherflight");
    		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    		validationerrors.add(errorVO);
    	}
		
		if(diffContType){
			ErrorVO errorVO = new ErrorVO(
    				"mailtracking.defaults.reassigncontainer.msg.err.containeralreadypresentwithdiffconttype");
    		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    		validationerrors.add(errorVO);
		}
    	return validationerrors;
	}
	
	/**
	 * @author a-7540
	 * Method is used to find whether the flight being reassigned to Already has the container of different type
	 * @param reassignedFlightValidationVO
	 * @param selectedContainerVOs
	 * @return
	 */
	private Collection<ErrorVO> isReassignedWithSameContainer(
			Collection<ContainerVO> newContainerVOs,
    		Collection<ContainerVO> selectedContainerVOs) {
    	log.entering("SaveContainerCommand","isReassignedWithSameContainer");
    	boolean diffContType = false;
    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();  
    	if(newContainerVOs!=null && newContainerVOs.size()>0){
		for (ContainerVO containerVO : newContainerVOs) {
			if(selectedContainerVOs!=null && selectedContainerVOs.size()>0){
				for (ContainerVO selectedvo : selectedContainerVOs) {
					if(selectedvo.getContainerNumber()!=null && selectedvo.getContainerNumber().trim().length()>0 &&
						   selectedvo.getAssignedPort()!=null && selectedvo.getAssignedPort().trim().length()>0){
						if(selectedvo.getContainerNumber().equalsIgnoreCase(containerVO.getContainerNumber())&&
								selectedvo.getAssignedPort().equalsIgnoreCase(containerVO.getAssignedPort()) &&
								!(selectedvo.getType().equalsIgnoreCase(containerVO.getType()))){
							diffContType = true;
							break;
						}
					}
					}
				}
			}
    	}
    	log.log(Log.FINE, "isSameFlight-------> ", diffContType);		
		if(diffContType){
			ErrorVO errorVO = new ErrorVO(
    				"mailtracking.defaults.reassigncontainer.msg.err.containeralreadypresentwithdiffconttype");
    		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    		validationerrors.add(errorVO);
		}
    	return validationerrors;
	}
    
}
