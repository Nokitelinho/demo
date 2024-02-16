/*
 * ValidateTransferCommand.java Created on Oct 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfercontainer;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1861
 *
 */
public class ValidateTransferCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String ASSIGN_SCREEN_ID = "mailtracking.defaults.assignContainer";
   private static final String SCREEN_ID = "mailtracking.defaults.transfercontainer";
   private static final String FLIGHT_MODULE_NAME = "flight.operation";
   private static final String FLIGHT_SCREEN_ID = "flight.operation.duplicateflight";
   
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_DESTN = "DESTINATION";
   private static final String OUTBOUND = "O";
   private static final String BLANKSPACE = "";
   private static final String SCREEN_SEARCHCONTAINER = "SEARCHCONTAINER";
   private static final String TARGET_SUCCESS = "success";
   private static final String TARGET_FAILURE = "failure"; 
   private static final String ROUTE_DELIMETER = "-";
   private static final String FROM_SCREEN_EXPORTLIST="MAIL_EXPORT_LIST";
   private static final String FROM_SCREEN_ACCEPTANCE="MAIL_ACCEPTANCE";
   private static final String FROM_SCREEN_ARRIVAL="MAIL_ARRIVAL";

       
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ValidateTransferCommand","execute");
    	  
    	TransferContainerForm transferContainerForm = 
    		(TransferContainerForm)invocationContext.screenModel;
    	TransferContainerSession transferContainerSession = 
    		(TransferContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	
    	DuplicateFlightSession duplicateFlightSession = (DuplicateFlightSession)getScreenSession(
				FLIGHT_MODULE_NAME, FLIGHT_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	// UPDATING THE CONTAINERVO IN SESSION
		ContainerVO currentvo = updateContainerVO(
				transferContainerSession,
				transferContainerForm,
				logonAttributes);
		
		transferContainerSession.setContainerVO(currentvo);
    	
    	// VALIDATING FORM
		errors = validateForm(transferContainerForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		
		if (SCREEN_SEARCHCONTAINER.equals(transferContainerForm.getFromScreen())) {
			errors = validateContainerVOs(transferContainerForm,transferContainerSession);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		}
    	
    	String reassignedto = transferContainerForm.getReassignedto();
    	log.log(Log.FINE, "REASSIGNED TO ------------> ", reassignedto);
		AirlineDelegate airlineDelegate = new AirlineDelegate();
    	AirlineValidationVO airlineValidationVO = null;
    	AreaDelegate areaDelegate = new AreaDelegate();
    	AirportValidationVO airportValidationVO = null;
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
    	
    	// IF CONTAINER REASSIGNED TO FLIGHT
    	if (CONST_FLIGHT.equals(reassignedto)) { 
    		
    		Collection<ErrorVO> fltErrors = new ArrayList<ErrorVO>();
    		
    		// VALIDATE FLIGHT CARRIER CODE
    		log.log(Log.FINE,"VALIDATING FLIGHT CARRIER CODE ------------> ");
    		String flightCarrierCode = transferContainerForm.getFlightCarrierCode();        	
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
						"mailtracking.defaults.reassigncontainer.msg.err.invalidCarrier",obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					fltErrors.addAll(errors);
        		}
        	}
        	
        	// VALIDATE POU & FINAL DESTINATION
        	log.log(Log.FINE,"VALIDATING POU & FINAL DESTINATION ------------> ");
        	Collection<String> airports = new ArrayList<String>();
        	airports.add(transferContainerForm.getFlightPou().toUpperCase());
        	Map<String,AirportValidationVO> stations = new HashMap<String,AirportValidationVO>();
        	errors = null;
        	try {
        		stations = areaDelegate.validateAirportCodes(
    					logonAttributes.getCompanyCode(),
    					airports);			
    			
    		}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			fltErrors.addAll(errors);    			
    		}    		
    		//VALIDATING POU AND CURRENT STATION
    		if(transferContainerForm.getFlightPou() != null && 
    				transferContainerForm.getFlightPou().trim().length() > 0 &&
    				logonAttributes.getAirportCode().equals(transferContainerForm.getFlightPou())) {   
    				if(errors == null) {
    					errors = new ArrayList<ErrorVO>();
    				}
    				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.invalidflightsegment");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					fltErrors.addAll(errors);
    		}
    		// VALIDATING POUS
    		log.log(Log.FINE,"VALIDATING POUS ------------> ");
        	String[] pous = transferContainerForm.getPointOfUnlading();
        	Map<String,AirportValidationVO> pointOfUnladings = new HashMap<String,AirportValidationVO>();
        	errors = null;
        	if (pous != null) {
        		Set<String> stns = new HashSet<String>();
        		for (int i = 0 ; i < pous.length ; i++) {
        			stns.add(pous[i].toUpperCase());
        		}

        		try {
        			pointOfUnladings = areaDelegate.validateAirportCodes(
        					logonAttributes.getCompanyCode(),
        					stns);

        		}catch (BusinessDelegateException businessDelegateException) {
            		errors = handleDelegateException(businessDelegateException);
        		}
        		if (errors != null && errors.size() > 0) {
        			fltErrors.addAll(errors);
        		}
        	}
        	
        	// VALIDATING FLIGHT CARRIERS
        	log.log(Log.FINE,"VALIDATING FLIGHT CARRIERS ------------> ");
        	String[] fltCarriers = transferContainerForm.getFltCarrier();        	
        	Map<String,AirlineValidationVO> carrierIds = new HashMap<String,AirlineValidationVO>();
        	errors = null;
        	if (fltCarriers != null) {
        		Set<String> carriers = new HashSet<String>();
        		for (int i = 0 ; i < fltCarriers.length ; i++) {
        			carriers.add(fltCarriers[i].toUpperCase());
        		}

        		try {
        			carrierIds = airlineDelegate.validateAlphaCodes(
        					logonAttributes.getCompanyCode(),
        					carriers);

        		}catch (BusinessDelegateException businessDelegateException) {
            		errors = handleDelegateException(businessDelegateException);
        		}
        		if (errors != null && errors.size() > 0) {
        			fltErrors.addAll(errors);
        		}
        	}
        	
        	if (fltErrors != null && fltErrors.size() > 0) {
    			invocationContext.addAllError(fltErrors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
        	// UPDATING ONWARD ROUTING VOS WITH CARRIER IDS
        	else if (fltCarriers != null) {
        		ContainerVO containerVO = transferContainerSession.getContainerVO();
        		Collection <OnwardRoutingVO> onwardRoutings = containerVO.getOnwardRoutings();
        		int row = 0;
        		for (OnwardRoutingVO routevo : onwardRoutings) {
        			routevo.setOnwardCarrierCode(upper(fltCarriers[row]));
					routevo.setOnwardCarrierId(getCarrierIdForAirline(upper(fltCarriers[row]),carrierIds));
					row++;
        		}
        	}
        	
        	// VALIDATING FLIGHT NUMBER   
        	log.log(Log.FINE,"VALIDATING FLIGHT NUMBER ------------> ");
    		FlightFilterVO flightFilterVO = handleFlightFilterVO(
    				    transferContainerForm,
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
    		
    			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {//IF NO RESULTS RETURNED
    				log.log(Log.FINE, "----------------FlightValidationVOs is NULL");
    				Object[] obj = {transferContainerForm.getFlightCarrierCode().toUpperCase(),
    						transferContainerForm.getFlightNumber().toUpperCase(),
    						transferContainerForm.getFlightDate()};
    				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.reassigncontainer.msg.err.noflightDetails",obj);
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
						
						transferContainerSession.setFlightValidationVO(flightValidVO);
						
						
			    		// validating whether pou is present in the onward routes of flight
			    		errors = isPouValid(transferContainerForm,flightValidVO,logonAttributes);
						transferContainerForm.setStatus("");
						//A-5249 from ICRD-84046
						if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ||
			                    FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())||
			                    FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus()))){
							Object[] obj = {flightCarrierCode.toUpperCase(),flightValidVO.getFlightNumber()};
							ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
							err.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(err);
							invocationContext.target = TARGET_FAILURE;							
							return;
						}
			    		if (errors != null && errors.size() > 0) {
			    			invocationContext.addAllError(errors);
			    			invocationContext.target = TARGET_FAILURE;
			    			return;
			    		}
						break;
					}
				}
    			else if(flightValidationVOs.size() > 1){ //IF MORE VOS RETURNED
					log.log(Log.FINE, "--------------Duplicate flight VO");
					duplicateFlightSession.setFlightValidationVOs(
							(ArrayList<FlightValidationVO>)flightValidationVOs);
					duplicateFlightSession.setParentScreenId(SCREEN_ID);
					duplicateFlightSession.setFlightFilterVO(flightFilterVO);  
					duplicateFlightSession.setScreenOfParent("reassignContainer");
					transferContainerForm.setStatus("showDuplicateFlights");
					invocationContext.target = TARGET_FAILURE;
					return;
				}
    			
			/*
			 * For Transfer Manifest Report
			 */
			if (transferContainerForm.getFlightCarrierCode().equals(logonAttributes.getOwnAirlineCode())) {				
				transferContainerForm.setSimilarCarrier("Y");
			} else {
				transferContainerForm.setSimilarCarrier("N");
			}    			
    	}
    	// IF CONTAINER REASSIGNED TO DESTINATION
    	else {
    		Collection<ErrorVO> destnErrors = new ArrayList<ErrorVO>();
    		
    		// VALIDATE CARRIER CODE
        	String carrierCode = transferContainerForm.getCarrier(); //added by A-7371 for ICRD-133987
        	Collection<ContainerVO> selectedVOs=transferContainerSession.getSelectedContainerVOs();
        	if(FROM_SCREEN_EXPORTLIST.equals(transferContainerForm.getFromScreen())||FROM_SCREEN_ACCEPTANCE.equals(transferContainerForm.getFromScreen())||(SCREEN_SEARCHCONTAINER.equals(transferContainerForm.getFromScreen())
					&& logonAttributes.getAirportCode().equals(selectedVOs.iterator().next().getPol()))){
        		if(carrierCode.equals(logonAttributes.getOwnAirlineCode())){
        		//throw error 'OAL transfer cannot be done to own airline'
        			
        		Collection<ErrorVO>	error = new ArrayList<ErrorVO>();
			
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.err.ownairline");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addError(errorVO);
				invocationContext.target = TARGET_FAILURE;
				return;
        		}
        	}
        		
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
						"mailtracking.defaults.reassigncontainer.msg.err.invalidCarrier",obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
        			destnErrors.addAll(errors);
        		}
        		else {
        			log.log(Log.FINE, "AirlineValidationVO--------------",
							airlineValidationVO);
					transferContainerSession.setAirlineValidationVO(airlineValidationVO);
        		}
        	}
        	
        	// VALIDATE DESTINATION 
        	String destn = transferContainerForm.getDestination();        	
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
        	
        	// VALIDATING THE SELECTED VOS
    		Collection<ContainerVO> containerVOs = transferContainerSession.getSelectedContainerVOs();
    		String firstDestn = null;
    		for (ContainerVO containervo : containerVOs) {
    			firstDestn = containervo.getFinalDestination();    			
    			break;
    		}
    		for (ContainerVO containervo : containerVOs) {
    			if(firstDestn!=null && firstDestn.length()>0){
	    			if (!firstDestn.equalsIgnoreCase(containervo.getFinalDestination())) {
	    				errors = new ArrayList<ErrorVO>();
	    				ErrorVO errorVO = new ErrorVO(
	    						"mailtracking.defaults.reassigncontainer.msg.err.reassigningDifferentDestinations");
	    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	    				errors.add(errorVO);
	    				invocationContext.addAllError(errors);
	    				invocationContext.target = TARGET_FAILURE;
	    				return;
	    			}
    			}
    		}
    		
    		/* For Transfer Manifest Report
			 * Start
			 */ 
    		if(transferContainerForm.getCarrier().equals(logonAttributes.getOwnAirlineCode())){
				transferContainerForm.setSimilarCarrier("Y");
			}else{
				transferContainerForm.setSimilarCarrier("N");					
			}
    	}
    	if(!FROM_SCREEN_EXPORTLIST.equals(transferContainerForm.getFromScreen()) && !FROM_SCREEN_ARRIVAL.equals(transferContainerForm.getFromScreen()) && !FROM_SCREEN_ACCEPTANCE.equals(transferContainerForm.getFromScreen())){//modified by A-7371 for
    	transferContainerForm.setStatus(SCREEN_SEARCHCONTAINER);
    	}else{
    	transferContainerForm.setStatus("NONE");
    	}
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ValidateTransferCommand","execute");
    	
    }
    /**
     * Method to check whether any container assigned to a destination 
     * is reassigned to same carrier
     * @param transferContainerForm
     * @param transferContainerSession
     * @return
     */ 
    private Collection<ErrorVO> validateContainerVOs(
    		TransferContainerForm transferContainerForm,
    		TransferContainerSession transferContainerSession) {
    	
    	log.entering("ValidateReassignCommand","validateContainerVOs");
    	
    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
    	
    	Collection<ContainerVO> containerVOs = transferContainerSession.getSelectedContainerVOs();
    	
    	Collection<ContainerVO> destnAssignedContainerVOs = new ArrayList<ContainerVO>();
    	for (ContainerVO vo : containerVOs) {
    		if (vo.getFlightDate() == null) {
    			destnAssignedContainerVOs.add(vo);
    		}
    	}
    	log.log(Log.FINE, "DestnAssignedContainerVOs--------------",
				destnAssignedContainerVOs);
		if (destnAssignedContainerVOs.size() > 0) {
    		if (CONST_DESTN.equals(transferContainerForm.getReassignedto())) {
    			String carrier = transferContainerForm.getCarrier().toUpperCase();
    			log.log(Log.FINE, "carrier--------------", carrier);
				for (ContainerVO vo : destnAssignedContainerVOs) {
        			if (vo.getCarrierCode().equals(carrier)) {
        				ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.reassigncontainer.msg.err.reassigningToSameCarrier");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						validationerrors.add(errorVO);
						break;
        			}
        		}
    		}    		
    	}
    	
    	return validationerrors;
    	
    }
    
    /**
     * Method is used to get the carrierId for particule Carrier code
     * from the Map
     * @param carriercode
     * @param carrierIds
     * @return int
     */
    private int getCarrierIdForAirline(
    		String carriercode,
    		Map<String,AirlineValidationVO> carrierIds) {

    	int carrierId = 0;

    	AirlineValidationVO airlineValidationVO = carrierIds.get(upper(carriercode));

    	return airlineValidationVO.getAirlineIdentifier();
    }
    /**
     * Method for updating the currnet vo that is displayed
     * @param transferContainerSession
     * @param transferContainerForm
     * @param logonAttributes
     * @return ContainerVO
     */
     private ContainerVO updateContainerVO(
    		TransferContainerSession transferContainerSession,
     		TransferContainerForm transferContainerForm,
     		LogonAttributes logonAttributes) {
     	
     	String reassignedto = transferContainerForm.getReassignedto();
     	log.log(Log.FINE, "ASSIGNED TO ------------> ", reassignedto);
		ContainerVO containerVO = transferContainerSession.getContainerVO();
     	
     	if (CONST_FLIGHT.equals(reassignedto)) {  
     		String[] fltCarriers = transferContainerForm.getFltCarrier();
     		String[] fltNos = transferContainerForm.getFltNo();
     		String[] depDates = transferContainerForm.getDepDate();
     		String[] pous = transferContainerForm.getPointOfUnlading();
     		
     		Collection <OnwardRoutingVO> onwardRoutings = containerVO.getOnwardRoutings();
     		if (onwardRoutings == null) {
     			onwardRoutings = new ArrayList<OnwardRoutingVO>();
     		}
     		if (fltCarriers != null) {						
     			int row = 0;
     			for (OnwardRoutingVO routevo : onwardRoutings) {
     				//routevo.setCarrierId();
     				routevo.setOnwardCarrierCode(upper(fltCarriers[row]));						
     				routevo.setOnwardFlightNumber(fltNos[row]);
     				LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);			 		
     				if (depDates[row] != null 
     						&& !BLANKSPACE.equals(depDates[row])) {
     					routevo.setOnwardFlightDate(date.setDate(
     							depDates[row]));
     				}
     				routevo.setPou(upper(pous[row]));
     				
     				row++;
     			}		
     		}
     		
     		containerVO.setOnwardRoutings(onwardRoutings);
     	}
     	
     	containerVO.setRemarks(transferContainerForm.getRemarks());
 				
 		return containerVO;   	
     }
     /**
      * This method is used to convert a string to upper case if 
      * it is not null
 	 * @param input
 	 * @return String
 	 */
 	private String upper(String input){//to convert sting to uppercase

 		if(input!=null){
 			return input.trim().toUpperCase();
 		}else{
 			return BLANKSPACE;
 		}
 	}
    /**
     * Method to create the filter vo for flight validation
     * @param reassignContainerForm
     * @param logonAttributes
     * @return
     */
    private FlightFilterVO handleFlightFilterVO(
    		TransferContainerForm transferContainerForm,
			LogonAttributes logonAttributes){

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(transferContainerForm.getFlightNumber().toUpperCase());
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setStringFlightDate(transferContainerForm.getFlightDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(
 				transferContainerForm.getFlightDate()));
		return flightFilterVO;
	}
    /**
	 * This method is used for validating the form for the particular action
	 * @param reassignContainerForm - ReassignContainerForm
	 * @param logonAttributes - LogonAttributes
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			TransferContainerForm transferContainerForm,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		
		String reassignedto = transferContainerForm.getReassignedto();
		
		/*if (reassignContainerForm.getRemarks().equals("")) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.reassigncontainer.msg.err.noremarks");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}*/
		
		if (CONST_FLIGHT.equals(reassignedto)) {    		
			if (("").equals(transferContainerForm.getFlightCarrierCode())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightCarrierCode");
				formErrors.add(errorVO);
			}
			if (("").equals(transferContainerForm.getFlightNumber())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightNumber");
				formErrors.add(errorVO);
			}
			if (("").equals(transferContainerForm.getFlightDate())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightDate");
				formErrors.add(errorVO);
			}
			if (("").equals(transferContainerForm.getFlightPou())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightPou");
				formErrors.add(errorVO);
			}
						
			String[] fltCarriers = transferContainerForm.getFltCarrier();
			String[] fltNos = transferContainerForm.getFltNo();
			String[] depDates = transferContainerForm.getDepDate();
			String[] pous = transferContainerForm.getPointOfUnlading();
			if (fltCarriers != null) {
				boolean hasFlightError = false;
				for (int i = 0 ; i < fltCarriers.length ; i++) {
					if (("").equals(fltCarriers[i])) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.reassigncontainer.msg.err.noFltCarrier");
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (("").equals(fltNos[i])) {
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.noFltNo");
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (("").equals(depDates[i])) {
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.noFltDate");
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (("").equals(pous[i])) {
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.noPou");
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (hasFlightError) {
						break;
					}
				}
				log.log(Log.INFO, "hasFlightError-----------------",
						hasFlightError);
				//	validating whether POU is equal to current airport
				boolean hasPouError = false;
				for (int i = 0 ; i < pous.length ; i++) {
					if ( pous[i] != null && !("").equals(pous[i])) {
						if (pous[i].equalsIgnoreCase(logonAttributes.getAirportCode())) {
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.pouEqualsCurrentAirport");
							formErrors.add(errorVO);
							hasPouError = true;
						}
					}
					if (hasPouError) {
						break;
					}
				}
				
				//	validation for duplicate pous				
				int rows = pous.length;
				for (int i=0; i<rows; i++) {
					for (int j=i+1; j<rows; j++) {
						if ((pous[i] != null) &&
								 (pous[i].equalsIgnoreCase(pous[j]))){
							Object obj[] = {pous[i].toUpperCase()};
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.pouduplicated",obj);
							formErrors.add(errorVO);
							break;
						}
					}
				}
				
			}
			
		}
		else {			
			
			if (("").equals(transferContainerForm.getCarrier())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noCarrier");
				formErrors.add(errorVO);
			}
			else {
				if(CONST_DESTN.equals(transferContainerForm.getAssignedto())) {					
					AssignContainerSession assignContainerSession = 
			    		getScreenSession(MODULE_NAME,ASSIGN_SCREEN_ID);
					AirlineValidationVO airlineValidationVO = assignContainerSession.getAirlineValidationVO();
					if (airlineValidationVO.getAlphaCode().equalsIgnoreCase(transferContainerForm.getCarrier())) {
						ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.reassigningToSameCarrier");
						formErrors.add(errorVO);
					}
				}
			} 
			if (("").equals(transferContainerForm.getDestination())) {
			ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noDestination");
			formErrors.add(errorVO);
			}
			else if(logonAttributes.getAirportCode().equals(transferContainerForm.getDestination())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.destnEqualToCurrentAirport");
				formErrors.add(errorVO);
			}
		}

		return formErrors;
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
    		TransferContainerForm transferContainerForm,
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
		
		if(!pointOfUnladings.contains(transferContainerForm.getFlightPou().toUpperCase())) {
			Object[] obj = {transferContainerForm.getFlightPou().toUpperCase(),
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
		log.exiting("SaveTransferContainerCommand", "getPOUs");
		
		return pointOfUnladings;
	}
}
