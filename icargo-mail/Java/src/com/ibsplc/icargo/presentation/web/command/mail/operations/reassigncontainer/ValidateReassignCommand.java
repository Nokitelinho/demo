/*
 * ValidateReassignCommand.java Created on June 22, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigncontainer;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
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
public class ValidateReassignCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String ASSIGN_SCREEN_ID = "mailtracking.defaults.assignContainer";
   private static final String SCREEN_ID = "mailtracking.defaults.reassignContainer";
   private static final String FLIGHT_MODULE_NAME = "flight.operation";
   private static final String FLIGHT_SCREEN_ID = "flight.operation.duplicateflight";
   private static final String MAILAXP_SCREEN_ID = "mailtracking.defaults.mailacceptance";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_DESTN = "DESTINATION";
   private static final String OUTBOUND = "O";
   private static final String BLANKSPACE = "";
   private static final String SCREEN_SEARCHCONTAINER = "SEARCHCONTAINER";
   private static final String TARGET_SUCCESS = "reassign_validate_success";
   private static final String TARGET_FAILURE = "reassign_validate_failure"; 
   
   private static final String ERR_BULK_CARRIER_TO_CARRIER = "mailtracking.defaults.reassigncontainer.msg.err.reassigningBlkFromCarrierToCarrier"; 
  
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ValidateReassignCommand","execute");
    	  
    	ReassignContainerForm reassignContainerForm = 
    		(ReassignContainerForm)invocationContext.screenModel;
    	ReassignContainerSession reassignContainerSession = 
    		(ReassignContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	
    	DuplicateFlightSession duplicateFlightSession = (DuplicateFlightSession)getScreenSession(
				FLIGHT_MODULE_NAME, FLIGHT_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	// UPDATING THE CONTAINERVO IN SESSION
		ContainerVO currentvo = updateContainerVO(
				reassignContainerSession,
				reassignContainerForm,
				logonAttributes);
		
		reassignContainerSession.setContainerVO(currentvo);
    	
    	// VALIDATING FORM
		errors = validateForm(reassignContainerForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		
		if (SCREEN_SEARCHCONTAINER.equals(reassignContainerForm.getFromScreen())) {
			errors = validateContainerVOs(reassignContainerForm,reassignContainerSession);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		}
    	
    	String reassignedto = reassignContainerForm.getReassignedto();
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
    		String flightCarrierCode = reassignContainerForm.getFlightCarrierCode();        	
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
        	airports.add(reassignContainerForm.getFlightPou().toUpperCase());
    			
        	errors=validateAirports(logonAttributes.getCompanyCode(), airports);        
    		if (errors != null && errors.size() > 0) {
    			fltErrors.addAll(errors);    			
    		}    		
    		
    		// VALIDATING POUS
    		log.log(Log.FINE,"VALIDATING POUS ------------> ");
    		
    		
        	String[] pous = reassignContainerForm.getPointOfUnlading();
        	
        	if (pous != null) {
        		Set<String> stns = new HashSet<String>();
        		for (int i = 0 ; i < pous.length ; i++) {
        			stns.add(pous[i].toUpperCase());
        		}
        		errors=validateAirports(logonAttributes.getCompanyCode(), stns);
        		
        		if (errors != null && errors.size() > 0) {
        			fltErrors.addAll(errors);
        		}
        	}
        	
        	// VALIDATING FLIGHT CARRIERS
        	log.log(Log.FINE,"VALIDATING FLIGHT CARRIERS ------------> ");
        	String[] fltCarriers = reassignContainerForm.getFltCarrier();        	
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
        	ContainerVO containerVO = reassignContainerSession.getContainerVO();
    		Collection <OnwardRoutingVO> onwardRoutings = containerVO.getOnwardRoutings();
        	if (fltCarriers != null) {
        		
        		int row = 0;
        		for (OnwardRoutingVO routevo : onwardRoutings) {
        			routevo.setOnwardCarrierCode(upper(fltCarriers[row]));
					routevo.setOnwardCarrierId(getCarrierIdForAirline(upper(fltCarriers[row]),carrierIds));
        		}
        	}
        	
        	// validate Flight POU and POU in onwardRoutingVO
        	errors = new ArrayList<ErrorVO>();
        	log.log(Log.FINE,"validate Final destination and last POU ------------> " );
            if(onwardRoutings != null && onwardRoutings.size() > 0) {
                String flightPou = reassignContainerForm.getFlightPou();
                for (OnwardRoutingVO routevo : onwardRoutings) {
	                if(flightPou.equalsIgnoreCase(routevo.getPou())) {
	                    ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.onwardroutingpousame");
	                    errors.add(errorVO);
	                    break;
	                }
                }
            } 
            
            if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
            
        	
        	// VALIDATING FLIGHT NUMBER   
        	log.log(Log.FINE,"VALIDATING FLIGHT NUMBER ------------> ");
    		FlightFilterVO flightFilterVO = handleFlightFilterVO(
    					reassignContainerForm,logonAttributes);
    		
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
    				Object[] obj = {reassignContainerForm.getFlightCarrierCode().toUpperCase(),
    						reassignContainerForm.getFlightNumber().toUpperCase(),
    						reassignContainerForm.getFlightDate()};
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
						
						reassignContainerSession.setFlightValidationVO(flightValidVO);
						reassignContainerForm.setStatus("");
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
						break;
					}
				}
    			else if(flightValidationVOs.size() > 1){ //IF MORE VOS RETURNED
					log.log(Log.FINE, "-------------Duplicate flight VO");
					duplicateFlightSession.setFlightValidationVOs(
							(ArrayList<FlightValidationVO>)flightValidationVOs);
					duplicateFlightSession.setParentScreenId(SCREEN_ID);
					duplicateFlightSession.setFlightFilterVO(flightFilterVO);  
					duplicateFlightSession.setScreenOfParent("reassignContainer");
					reassignContainerForm.setStatus("showDuplicateFlights");
					invocationContext.target = TARGET_FAILURE;
					return;
				}

            	// VALIDATING THE SELECTED VOS
        		Collection<ContainerVO> containerVOs = reassignContainerSession.getSelectedContainerVOs();
                String flightPou = reassignContainerForm.getFlightPou();
        		String contErrorInReassign = "";
                int errorInReassign = 0;
                for (ContainerVO containervo : containerVOs) {
                	if (flightPou != null && 
                			!flightPou.equalsIgnoreCase(containervo.getFinalDestination())) {
                		errorInReassign = 1;
                		if("".equals(contErrorInReassign)){
                			contErrorInReassign = containervo.getContainerNumber();
                		}else{
                			contErrorInReassign = new StringBuilder(contErrorInReassign)
                			.append(",")
                			.append(containervo.getContainerNumber())
                			.toString();	
                		}
                	}
                }
                /**
                 * Commented for bug 72996 starts
                 */
//                if(errorInReassign == 1) {
//    				errors = new ArrayList<ErrorVO>();
//    				ErrorVO errorVO = new ErrorVO(
//    						"mailtracking.defaults.reassigncontainer.msg.err.destinationFlightPouMismatch",
//    						new Object[]{contErrorInReassign});
//    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
//    				errors.add(errorVO);
//    				invocationContext.addAllError(errors);
//    				invocationContext.target = TARGET_FAILURE;
//    				return;
//                }
                /**
                 * Commented for bug 72996 ends
                 */
    	}
    	// IF CONTAINER REASSIGNED TO DESTINATION
    	else {
    		Collection<ErrorVO> destnErrors = new ArrayList<ErrorVO>();
    		
    		// VALIDATE CARRIER CODE
        	String carrierCode = reassignContainerForm.getCarrier();        	
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
					reassignContainerSession.setAirlineValidationVO(airlineValidationVO);
        		}
        	}
        	
        	// VALIDATE DESTINATION 
        	String destn = reassignContainerForm.getDestination();        	
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
    		Collection<ContainerVO> containerVOs = reassignContainerSession.getSelectedContainerVOs();
    		String firstDestn = null;
    		for (ContainerVO containervo : containerVOs) {
    			firstDestn = containervo.getFinalDestination();    			
    			break;
    		}
    		for (ContainerVO containervo : containerVOs) {
    			if (firstDestn != null && 
    					!firstDestn.equalsIgnoreCase(containervo.getFinalDestination())) {
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
    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ValidateReassignCommand","execute");
    	
    }
    /**
     * Method to check whether any container assigned to a destination 
     * is reassigned to same carrier
     * @param reassignContainerForm
     * @param reassignContainerSession
     * @return
     */ 
    private Collection<ErrorVO> validateContainerVOs(
    		ReassignContainerForm reassignContainerForm,
    		ReassignContainerSession reassignContainerSession) {
    	
    	log.entering("ValidateReassignCommand","validateContainerVOs");
    	
    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
    	
    	Collection<ContainerVO> containerVOs = reassignContainerSession.getSelectedContainerVOs();
    	
    	Collection<ContainerVO> destnAssignedContainerVOs = new ArrayList<ContainerVO>();
    	for (ContainerVO vo : containerVOs) {
    		if (vo.getFlightDate() == null) {
    			destnAssignedContainerVOs.add(vo);
    		}
    	}
    	log.log(Log.FINE, "DestnAssignedContainerVOs--------------",
				destnAssignedContainerVOs);
		if (destnAssignedContainerVOs.size() > 0) {
    		if (CONST_DESTN.equals(reassignContainerForm.getReassignedto())) {
    			String carrier = reassignContainerForm.getCarrier().toUpperCase();
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
     * @param reassignContainerSession
     * @param reassignContainerForm
     * @param logonAttributes
     * @return ContainerVO
     */
     private ContainerVO updateContainerVO(
     		ReassignContainerSession reassignContainerSession,
     		ReassignContainerForm reassignContainerForm,
     		LogonAttributes logonAttributes) {
     	
     	String reassignedto = reassignContainerForm.getReassignedto();
     	log.log(Log.FINE, "ASSIGNED TO ------------> ", reassignedto);
		ContainerVO containerVO = reassignContainerSession.getContainerVO();
     	
     	if (CONST_FLIGHT.equals(reassignedto)) {  
     		String[] fltCarriers = reassignContainerForm.getFltCarrier();
     		String[] fltNos = reassignContainerForm.getFltNo();
     		String[] depDates = reassignContainerForm.getDepDate();
     		String[] pous = reassignContainerForm.getPointOfUnlading();
     		
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
     	
     	containerVO.setRemarks(reassignContainerForm.getRemarks());
 				
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
    		ReassignContainerForm reassignContainerForm,
			LogonAttributes logonAttributes){

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(reassignContainerForm.getFlightNumber().toUpperCase());
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setStringFlightDate(reassignContainerForm.getFlightDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(
 				reassignContainerForm.getFlightDate()));
		return flightFilterVO;
	}
    /**
	 * This method is used for validating the form for the particular action
	 * @param reassignContainerForm - ReassignContainerForm
	 * @param logonAttributes - LogonAttributes
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			ReassignContainerForm reassignContainerForm,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		
		String reassignedto = reassignContainerForm.getReassignedto();
		
		//Added by A-5945 for ICRD-96261 ends
		if (CONST_FLIGHT.equals(reassignedto)) {
			//Added by A-5945 for ICRD-96261 starts
			Collection<PartnerCarrierVO> partnerCarrierVOs = null;
			ArrayList<String> partnerCarriers = new ArrayList<String>();
			MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
	    		new MailTrackingDefaultsDelegate();
			String companyCode =logonAttributes.getCompanyCode();
			String ownCarrierCode = logonAttributes.getOwnAirlineCode();
			String airportCode = logonAttributes.getAirportCode();
		/*if (reassignContainerForm.getRemarks().equals("")) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.reassigncontainer.msg.err.noremarks");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}*/
			String CarrierCode =reassignContainerForm.getFlightCarrierCode();
			try {
				
				partnerCarrierVOs=mailTrackingDefaultsDelegate.findAllPartnerCarriers(companyCode,ownCarrierCode,airportCode);
			}catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			log.log(Log.INFO, "partnerCarrierVOs-----------------",
					partnerCarrierVOs);
			if(partnerCarrierVOs!= null){
				for(PartnerCarrierVO partner :partnerCarrierVOs){
					String partnerCarrier =	 partner.getPartnerCarrierCode();
					partnerCarriers.add(partnerCarrier);
					} 
					partnerCarriers.add(ownCarrierCode);  
				if(!(partnerCarriers.contains(CarrierCode))){
					log.log(Log.INFO, "partnerCarriers-----------------",
							partnerCarriers);
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.reassigncontainer.msg.err.tonotHandledCarrier");
			formErrors.add(errorVO);
				}
				
				
			}
			if (("").equals(reassignContainerForm.getFlightCarrierCode())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightCarrierCode");
				formErrors.add(errorVO);
			}
			if (("").equals(reassignContainerForm.getFlightNumber())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightNumber");
				formErrors.add(errorVO);
			}
			if (("").equals(reassignContainerForm.getFlightDate())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightDate");
				formErrors.add(errorVO);
			}
			if (("").equals(reassignContainerForm.getFlightPou())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightPou");
				formErrors.add(errorVO);
			}
						
			String[] fltCarriers = reassignContainerForm.getFltCarrier();
			String[] fltNos = reassignContainerForm.getFltNo();
			String[] depDates = reassignContainerForm.getDepDate();
			String[] pous = reassignContainerForm.getPointOfUnlading();
			
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
			//Added by A-5945 for ICRD-96261 starts
			Collection<PartnerCarrierVO> partnerCarrierVOs = null;
			ArrayList<String> partnerCarriers = new ArrayList<String>();
			MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
	    		new MailTrackingDefaultsDelegate();
			String companyCode =logonAttributes.getCompanyCode();
			String ownCarrierCode = logonAttributes.getOwnAirlineCode();
			String airportCode = logonAttributes.getAirportCode();
			/*if (reassignContainerForm.getRemarks().equals("")) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noremarks");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}*/
			String CarrierCode =reassignContainerForm.getCarrier();
			try {
				
				partnerCarrierVOs=mailTrackingDefaultsDelegate.findAllPartnerCarriers(companyCode,ownCarrierCode,airportCode);
			}catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			log.log(Log.INFO, "partnerCarrierVOs-----------------",
					partnerCarrierVOs);
			if(partnerCarrierVOs!= null){
				for(PartnerCarrierVO partner :partnerCarrierVOs){
					String partnerCarrier =	 partner.getPartnerCarrierCode();
					partnerCarriers.add(partnerCarrier);
					} 
					partnerCarriers.add(ownCarrierCode);  
				if(!(partnerCarriers.contains(CarrierCode))){
					log.log(Log.INFO, "partnerCarriers-----------------",
							partnerCarriers);
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.reassigncontainer.msg.err.tonotHandledCarrier");
			formErrors.add(errorVO);
				}
				
				
			}
			 
			if ((reassignContainerForm.getDestination()== null) ||("".equals(reassignContainerForm.getDestination().trim()))) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noDestination");
				formErrors.add(errorVO);
			}
			else {
			
				if(CONST_DESTN.equals(reassignContainerForm.getAssignedto())) {					
					AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
					MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
					if("ASSIGNCONTAINER".equals(reassignContainerForm.getAssignedto())) {	
						AssignContainerSession assignContainerSession = 
				    		getScreenSession(MODULE_NAME,ASSIGN_SCREEN_ID);
						airlineValidationVO = assignContainerSession.getAirlineValidationVO();
						if (airlineValidationVO.getAlphaCode().equalsIgnoreCase(reassignContainerForm.getCarrier().toUpperCase())) {
							ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.reassigncontainer.msg.err.reassigningToSameCarrier");
							formErrors.add(errorVO);
						}
					}
					if("MAILACCEPTANCE".equals(reassignContainerForm.getAssignedto())) {
						MailAcceptanceSession mailAcceptanceSession = getScreenSession(
								MODULE_NAME, MAILAXP_SCREEN_ID);
						mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
						if (mailAcceptanceVO.getFlightCarrierCode().equalsIgnoreCase(reassignContainerForm.getCarrier().toUpperCase())) {
							ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.reassigncontainer.msg.err.reassigningToSameCarrier");
							formErrors.add(errorVO);
						}
					}
				}
				if(CONST_DESTN.equals(reassignContainerForm.getReassignedto())) {
					if("MAILACCEPTANCE".equals(reassignContainerForm.getFromScreen())) {
						MailAcceptanceSession mailAcceptanceSession = getScreenSession(
								MODULE_NAME, MAILAXP_SCREEN_ID);
						/**
						 * COMMENTED FOR BUG 80663 STARTS
						 */
//						if (mailAcceptanceSession.getMailAcceptanceVO().getFlightCarrierCode().equalsIgnoreCase(reassignContainerForm.getCarrier().toUpperCase())) {
//							ErrorVO errorVO = new ErrorVO(
//								"mailtracking.defaults.reassigncontainer.msg.err.reassigningToSameCarrier");
//							formErrors.add(errorVO);
//						}
						/**
						 * COMMENTED FOR BUG 80663 ENDS
						 */
						
					}
				}
			}
			if ((reassignContainerForm.getDestination()== null) ||("".equals(reassignContainerForm.getDestination().trim()))) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noDestination");
				formErrors.add(errorVO);
			}
			else if(logonAttributes.getAirportCode().equals(reassignContainerForm.getDestination())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.destnEqualToCurrentAirport");
				formErrors.add(errorVO);
			}
		}

		return formErrors;
	}
	private Collection<ErrorVO> validateAirports(String companycode,Collection<String> airports){
		AreaDelegate areaDelegate = new AreaDelegate();
    	Map<String,AirportValidationVO> stations = new HashMap<String,AirportValidationVO>();
    	Collection<ErrorVO> errors = null;
    	try {
    		stations = areaDelegate.validateAirportCodes(
    				companycode,airports);			
			
		}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
		return  errors;
	}
}
