/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.TransferContainerSaveCommand.java
 *
 *	Created by	:	A-7929
 *	Created on	:	5-Oct-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OnwardRouting;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.TransferForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class TransferContainerSaveCommand extends AbstractCommand {

	 private Log log = LogFactory.getLogger("Mail Operations");
    
	 
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.transfercontainer";
	   private static final String CONST_FLIGHT = "FLIGHT";
	   private static final String SHOW_DUPLICATE = "showDuplicateFlights";
	   private static final String OUTBOUND = "O";
	   //For report
	   private static final String TRFMFT_REPORT_ID = "RPTOPS066";
	   private static final String PRODUCTCODE = "mail";
	   private static final String SUBPRODUCTCODE = "operations"; 
	   private static final String BUNDLE = "transferMailManifestResources";
	   private static final String ACTION = "generateTransferManifestReportForContainer";
	   //for report ends
	   private static final String FROM_SCREEN_EXPORTLIST="MAIL_EXPORT_LIST";
	   private static final String FROM_SCREEN_ACCEPTANCE="MAIL_ACCEPTANCE";
	   private static final String FROM_SCREEN_CONTAINER="SEARCHCONTAINER";
	   private static final String EMBARGO_EXISTS = "embargo_exists";
	   
	   

	   private static final String SCREEN_SEARCHCONTAINER = "SEARCHCONTAINER";
	   private static final String CONST_DESTN = "DESTINATION";
	   private static final String ROUTE_DELIMETER = "-";
	   private static final String FROM_SCREEN_ARRIVAL="MAIL_ARRIVAL";
	   private static final String AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED = "operations.flthandling.aircraftcompatibilityrequireduldtypes";
	   private static final String STNPAR_DEFUNIT_WGT = "station.defaults.unit.weight";
	   private static final String ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND = "mail.operations.ULDaircraftcompatibilityinMailInbound";
	   private static final String SECURITY_SCREENING_WARNING="mail.operations.securityscreeningwarning";
		private static final String SECURITY_SCREENING_ERROR="mail.operations.securityscreeningerror";
		private static final String APPLICABLE_REGULATION_ERROR="mail.operations.applicableregulationerror";

	   /**
	    *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.TransferContainerSaveCommand.java
	    *	Version		:	Name	:	Date			:	Updation
	    * ---------------------------------------------------
	    *		0.1		:	a-7929	:	05-Oct-2018	:	Draft
	    */
	   
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		log.entering("TransferContainerSaveCommand","execute");
		
		ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();
    	Collection<ContainerDetails> containerActionData = listContainerModel.getSelectedContainerData();
    	
    	TransferForm transferformModel = listContainerModel.getTransferForm(); 	
     	LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
    	Collection<ErrorVO> errors = null;
    	
    	Collection<String> code = new ArrayList<String>();
		code.add(ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND);
		code.add(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
    	
    	Collection<OnwardRouting> onwardRouting = listContainerModel.getOnwardRouting();
    	Collection<OnwardRoutingVO> onwardRoutingVOs = new ArrayList<OnwardRoutingVO>();
    	
    	for (OnwardRouting curOnwardRouting : onwardRouting) {     
    		onwardRoutingVOs.add(MailOperationsModelConverter.constructOnwardRoutingVO(curOnwardRouting,logonAttributes));
    	}
    	
    	
    	
    	
    	String reassignedto = transferformModel.getReassignedto();
    	FlightValidationVO singleVOFlightValidationVO = null;
    	Collection<FlightValidationVO> multipleVOFlightValidationVO = new ArrayList<FlightValidationVO>();
    	Collection<ContainerVO> selectedTransferContainerVOs = new ArrayList<ContainerVO>();  
    	String actualWeightUnit=null;
 	    actualWeightUnit=findStationParameterValue(STNPAR_DEFUNIT_WGT);
 	    //below logic moved from ValidateContainerTransferCommand, IASCB-57383   
 	    //IASCB-90239 destination check modified
 	   for(ContainerDetails containerVO : containerActionData){
      	   if((CONST_FLIGHT.equals(reassignedto) && logonAttributes.getAirportCode().equals(containerVO.getDestination()) ) || 
      			   (CONST_DESTN.equals(reassignedto) && logonAttributes.getAirportCode().equals(transferformModel.getDestination()) )){
      		  // ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.containeratfinaldest");
      		   ErrorVO errorVO = new ErrorVO("Transfer cannot be done at the final destination of the container");
      		   actionContext.addError(errorVO);	
      		   log.exiting("ValidateContainerTransferCommand............","execute");
     			return;    		   
      	   }
          }
		for (ContainerDetails container : containerActionData) {
			container.setOnwardRoutings(onwardRoutingVOs);
			ContainerVO containerVO = MailOperationsModelConverter.constructContainerVO(container, logonAttributes);
			container.setActualWeightUnit(actualWeightUnit);
			if (container.getActualWeight() != 0) {
				containerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT, 0, container.getActualWeight(),
						container.getActualWeightUnit()));
			}
			selectedTransferContainerVOs.add(containerVO);
    	}
    	
    		
    	boolean printNeeded=false;
    	if(transferformModel.getScanDate()==null && ("").equals(transferformModel.getScanDate())){
    		//actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate"));
    		actionContext.addError(new ErrorVO("Type or select the Scan Date"));
 	   		return; 
		}
		if(transferformModel.getMailScanTime()==null ||("").equals(transferformModel.getMailScanTime())){
			//actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime"));
			actionContext.addError(new ErrorVO("Type the Scan Time"));
 	   		return; 
		}
		
		
		if(transferformModel.getFlightPou()==null ||("").equals(transferformModel.getFlightPou()) || transferformModel.getDestination()==null || ("").equals(transferformModel.getDestination()) ){
			//actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyDestination"));
			actionContext.addError(new ErrorVO("Destination cannot be empty"));
 	   		return; 
		}
		
		
		
		/*if(EMBARGO_EXISTS.equals(transferformModel.getEmbargoFlag())){ //Added by A-8164 for ICRD-271652
			actionContext.target =TARGET_FAILURE; 
	   		return;  
	   	}*/
		
		
		//added from ValidateTransferCommand
		// VALIDATING FORM
		 errors = validateForm(transferformModel,logonAttributes);
		if (errors != null && errors.size() > 0) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
		if (SCREEN_SEARCHCONTAINER.equals(transferformModel.getFromScreen())) {
			errors = validateContainerVOs(transferformModel,selectedTransferContainerVOs);
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			 }
		}
				 
		//added from ValidateTransferCommand ends
		
		String scanDate= new StringBuilder().append(transferformModel.getScanDate()).append(" ").append(transferformModel.getMailScanTime()).append(":00").toString();
	    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    scanDat.setDateAndTime(scanDate);
    	
	    
	    //added from ValidateTransferCommand
	    AirlineDelegate airlineDelegate = new AirlineDelegate();
    	AirlineValidationVO airlineValidationVO = null;
    	AreaDelegate areaDelegate = new AreaDelegate();
    	AirportValidationVO airportValidationVO = null;
	   //added from ValidateTransferCommand ends
	    
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
    	
    	//String reassignedto = transferformModel.getReassignedto();
    	log.log(Log.FINE, "REASSIGNED TO ------------> ", reassignedto);
		//Collection<ContainerVO> selectedContainerVOs = transferContainerSession.getSelectedContainerVOs();
    	//Collection<ContainerVO> selectedContainerVOs = listContainerModel.getSelectedContainerData();
    	//ContainerVO currentvo = transferContainerSession.getContainerVO();
    	
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	operationalFlightVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
    	operationalFlightVO.setOwnAirlineId(
    			logonAttributes.getOwnAirlineIdentifier());
    	operationalFlightVO.setOperator(
    			logonAttributes.getUserId());
    	
    	operationalFlightVO.setOperationTime(scanDat);
    	
    	 //added from ValidateTransferCommand
    	
    	// IF CONTAINER REASSIGNED TO FLIGHT
    	if (CONST_FLIGHT.equals(reassignedto)) { 
    		
    		
    		if(transferformModel.getFlightCarrierCode()==null ||("").equals(transferformModel.getFlightCarrierCode())){
    			//actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyFlightCarrierCode"));
    			actionContext.addError(new ErrorVO("Flight Carrier code cannot be empty"));
     	   		return; 
    		}
    		if(transferformModel.getFlightNumber()==null ||("").equals(transferformModel.getFlightNumber())){
    			//actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyFlightNumber"));
    			actionContext.addError(new ErrorVO("Flight Number code cannot be empty"));
     	   		return; 
    		}
    		if(transferformModel.getFlightDate()==null ||("").equals(transferformModel.getFlightDate())){
    			//actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyFlightDate"));
    			actionContext.addError(new ErrorVO("Flight Date code cannot be empty"));
     	   		return; 
    		}
    		
    		
            Collection<ErrorVO> fltErrors = new ArrayList<ErrorVO>();
    		
    		// VALIDATE FLIGHT CARRIER CODE
    		log.log(Log.FINE,"VALIDATING FLIGHT CARRIER CODE ------------> ");
    		String flightCarrierCode = transferformModel.getFlightCarrierCode();        	
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
        	airports.add(transferformModel.getFlightPou().toUpperCase());
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
    		if(transferformModel.getFlightPou() != null && 
    				transferformModel.getFlightPou().trim().length() > 0 &&
    				logonAttributes.getAirportCode().equals(transferformModel.getFlightPou())) {   
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
        	String[] pous = transferformModel.getPointOfUnlading();
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
        	String[] fltCarriers = transferformModel.getFltCarrier();        	
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
        		actionContext.addAllError((List<ErrorVO>) fltErrors);
    			return;
    		}
        	// UPDATING ONWARD ROUTING VOS WITH CARRIER IDS
        	/*else if (fltCarriers != null) {
        		ContainerVO containerVO = transferContainerSession.getContainerVO();
        		Collection <OnwardRoutingVO> onwardRoutings = containerVO.getOnwardRoutings();
        		int row = 0;
        		for (OnwardRoutingVO routevo : onwardRoutings) {
        			routevo.setOnwardCarrierCode(upper(fltCarriers[row]));
					routevo.setOnwardCarrierId(getCarrierIdForAirline(upper(fltCarriers[row]),carrierIds));
					row++;
        		}
        	}*/
        	
        	
        	
        	// VALIDATING FLIGHT NUMBER   
        	log.log(Log.FINE,"VALIDATING FLIGHT NUMBER ------------> ");
    		FlightFilterVO flightFilterVO = handleFlightFilterVO(
    				    transferformModel,
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
    			actionContext.addAllError((List<ErrorVO>) errors);
    			return;
    		}
    		
    			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {//IF NO RESULTS RETURNED
    				log.log(Log.FINE, "----------------FlightValidationVOs is NULL");
    				Object[] obj = {transferformModel.getFlightCarrierCode().toUpperCase(),
    						transferformModel.getFlightNumber().toUpperCase(),
    						transferformModel.getFlightDate()};
    				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.reassigncontainer.msg.err.noflightDetails",obj);
    				errors = new ArrayList<ErrorVO>();
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(errorVO);
    				actionContext.addAllError((List<ErrorVO>) errors);
    				return;
    			}
    			else if ( flightValidationVOs.size() == 1) { //IF ONLY 1 VO RETURNED        				
					log.log(Log.FINE, "--------------FlightValidationVOs has one VO");
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						
						singleVOFlightValidationVO = flightValidVO ; 
						//transferContainerSession.setFlightValidationVO(flightValidVO);
						
			    		// validating whether pou is present in the onward routes of flight
			    		errors = isPouValid(selectedTransferContainerVOs,flightValidVO,logonAttributes);
			    		transferformModel.setStatus("");
			    		//based on the Soncy comment
	        			//IASCB-63549: Modified
			    		boolean isToBeActioned = FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus())   ;                  
			    		isToBeActioned = isToBeActioned && !canIgnoreToBeActionedCheck();
			    		//A-5249 from ICRD-84046
						if((isToBeActioned || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())||
			                    FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus()))){
							Object[] obj = {flightCarrierCode.toUpperCase(),flightValidVO.getFlightNumber()};
							ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
							err.setErrorDisplayType(ErrorDisplayType.ERROR);
							actionContext.addError(err);				
							return;
						}
			    		if (errors != null && errors.size() > 0) {
			    			actionContext.addAllError((List<ErrorVO>) errors);
			    			return;
			    		}
			    		//Validate ULD Type- Aircraft Compatibility starts
    		    		
			    		Map<String, String> systemParameter = getSystemParameter(code);
			    		//commented as part of IASCB-66177
			    		///if(systemParameter.get(ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND)!= null && MailConstantsVO.FLAG_YES.equals(systemParameter.get(ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND)) && listContainerModel.getNumericalScreenId().equals("MTK064")){   
			    		errors=validateULDIncomatibility(selectedTransferContainerVOs,flightValidVO,code);
			    		if (errors != null && errors.size() > 0) {
			    			actionContext.addAllError((List<ErrorVO>) errors);
			    			return;
			    		}
			    		//}
		    		//validateULDIncomatibility
			    		
						break;
					}
				}
    			else if(flightValidationVOs.size() > 1){ //IF MORE VOS RETURNED
					log.log(Log.FINE, "--------------Duplicate flight VO");
					for(FlightValidationVO flightVO: flightValidationVOs){
						multipleVOFlightValidationVO.add(flightVO);
					}
					/*duplicateFlightSession.setFlightValidationVOs(
							(ArrayList<FlightValidationVO>)flightValidationVOs);
					duplicateFlightSession.setParentScreenId(SCREEN_ID);
					duplicateFlightSession.setFlightFilterVO(flightFilterVO);  
					duplicateFlightSession.setScreenOfParent("reassignContainer");*/
					transferformModel.setStatus("showDuplicateFlights");
					return;
				}
    			
			/*
			 * For Transfer Manifest Report
			 */
			if (transferformModel.getFlightCarrierCode().equals(logonAttributes.getOwnAirlineCode())) {				
				transferformModel.setSimilarCarrier("Y");
			} else {
				transferformModel.setSimilarCarrier("N");
			}    			
    	}
        	
    	
    	
    	// IF CONTAINER REASSIGNED TO DESTINATION
    	else {
    		
    		if(transferformModel.getCarrier()==null ||("").equals(transferformModel.getCarrier())){
    			//actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyCarrier"));
    			actionContext.addError(new ErrorVO("Carrier cannot be empty"));
     	   		return; 
    		}
    		
            Collection<ErrorVO> destnErrors = new ArrayList<ErrorVO>();
    		
    		// VALIDATE CARRIER CODE
        	String carrierCode = transferformModel.getCarrier(); //added by A-7371 for ICRD-133987
        	//Collection<ContainerVO> selectedVOs=transferContainerSession.getSelectedContainerVOs();
        	if(FROM_SCREEN_EXPORTLIST.equals(transferformModel.getFromScreen())||FROM_SCREEN_ACCEPTANCE.equals(transferformModel.getFromScreen())||(SCREEN_SEARCHCONTAINER.equals(transferformModel.getFromScreen())
					&& logonAttributes.getAirportCode().equals(selectedTransferContainerVOs.iterator().next().getPol()))){
        		if(carrierCode.equals(logonAttributes.getOwnAirlineCode())){
        		//throw error 'OAL transfer cannot be done to own airline'
        			
        		Collection<ErrorVO>	error = new ArrayList<ErrorVO>();
			
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.err.ownairline");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				actionContext.addError(errorVO);
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
        		/*else {
        			log.log(Log.FINE, "AirlineValidationVO--------------",
							airlineValidationVO);
					transferContainerSession.setAirlineValidationVO(airlineValidationVO);
        		}*/
        	}
        	
        	
        	
        	// VALIDATE DESTINATION 
        	String destn = transferformModel.getDestination();        	
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
        		actionContext.addAllError((List<ErrorVO>) destnErrors);
    			return;
    		}
        	
        	
        	
        	// VALIDATING THE SELECTED VOS
    		//Collection<ContainerVO> containerVOs = transferContainerSession.getSelectedContainerVOs();
    		String firstDestn = null;
    		for (ContainerVO containervo : selectedTransferContainerVOs) {
    			firstDestn = containervo.getFinalDestination();    			
    			break;
    		}
    		for (ContainerVO containervo : selectedTransferContainerVOs) {
    			if(firstDestn!=null && firstDestn.length()>0){
	    			if (!firstDestn.equalsIgnoreCase(containervo.getFinalDestination())) {
	    				errors = new ArrayList<ErrorVO>();
	    				ErrorVO errorVO = new ErrorVO(
	    						"mailtracking.defaults.reassigncontainer.msg.err.reassigningDifferentDestinations");
	    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	    				errors.add(errorVO);
	    				actionContext.addAllError((List<ErrorVO>) errors);
	    				return;
	    			}
    			}
    		}
    		
    		/* For Transfer Manifest Report
			 * Start
			 */ 
    		if(transferformModel.getCarrier().equals(logonAttributes.getOwnAirlineCode())){
    			transferformModel.setSimilarCarrier("Y");
			}else{
				transferformModel.setSimilarCarrier("N");					
			}
    	}
        	
    	
    	if(!FROM_SCREEN_EXPORTLIST.equals(transferformModel.getFromScreen()) && !FROM_SCREEN_ARRIVAL.equals(transferformModel.getFromScreen()) && !FROM_SCREEN_ACCEPTANCE.equals(transferformModel.getFromScreen())){//modified by A-7371 for
    		transferformModel.setStatus(SCREEN_SEARCHCONTAINER);
        	}else{
        		transferformModel.setStatus("NONE");
        	}
    	
    	 //added from ValidateTransferCommand ends
    	
    	
    	
    	//---------------------------------------------- SAVE FLOW COMMAND------------------------------------
    	
    	
    	
    	// IF REASSIGNING TO FLIGHT
    	if (CONST_FLIGHT.equals(reassignedto)) {  
    		FlightValidationVO flightValidationVO = null;
    		
    		if (SHOW_DUPLICATE.equals(transferformModel.getStatus())) {
    			//flightValidationVO = duplicateFlightSession.getFlightValidationVO();
    			//flightValidationVO = singleVOFlightValidationVO;
    			transferformModel.setStatus("");
    		}
    		else {
    			//flightValidationVO = transferContainerSession.getFlightValidationVO(); 
    			flightValidationVO = singleVOFlightValidationVO;
    		}
    		log.log(Log.FINE, "FlightValidationVO ------------> ",
					flightValidationVO);
			// validating whether the container is already assigned to same flight
    		String assignedto = transferformModel.getReassignedto();
    		log.log(Log.FINE, "assignedto ------------> ", assignedto);
			if (CONST_FLIGHT.equals(assignedto)) {
    			errors = isReassignedToSameFlight(
    					flightValidationVO,
    					selectedTransferContainerVOs,
    					transferformModel);
        		if (errors != null && errors.size() > 0) {      			
        			actionContext.addAllError((List<ErrorVO>) errors);
        			//invocationContext.target = TARGET_FAILURE;
        			return;
        		}
        		if(flightValidationVO != null){
        			
        			if(flightValidationVO.isTBADueRouteChange() && !canIgnoreToBeActionedCheck()){
        				Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),transferformModel.getFlightDate()};
        				ErrorVO errorVO = new ErrorVO(
        						"mailtracking.defaults.transfercontainer.msg.err.flighttobeactioned",obj);
        				if(errors == null){
        					errors = new ArrayList<ErrorVO>();
        				}
        				errors.add(errorVO);
        				actionContext.addAllError((List<ErrorVO>) errors);
        				//invocationContext.target = TARGET_FAILURE;
        				return;
        			} 
        			//based on the Soncy comment
        			//IASCB-63549: Modified
        			boolean isToBeActioned = FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus());
        			isToBeActioned = isToBeActioned && !canIgnoreToBeActionedCheck();
        			if((isToBeActioned || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())||
                            FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))){
        				Object[] obj = {flightValidationVO.getCarrierCode().toUpperCase(),flightValidationVO.getFlightNumber()};
        				ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
        				err.setErrorDisplayType(ErrorDisplayType.ERROR);
        				actionContext.addError(err);
        				//invocationContext.target = TARGET_FAILURE;
        				return;
        			}
        		}
    		}
			//for (ContainerVO vo : selectedContainerVOs) {
			for (ContainerVO vo : selectedTransferContainerVOs) {
				//vo.setOnwardRoutings(currentvo.getOnwardRoutings());	
				//vo.setRemarks(transferformModel.getRemarks());
				vo.setReassignFlag(true);
				vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
				vo.setFinalDestination(transferformModel.getDestination().toUpperCase());
				vo.setMailSource(transferformModel.getFromScreen());//Added for IASCB-46908
				vo.setTransactionLevel(MailConstantsVO.ULD_LEVEL_TRANSACTION);
    		}
    		
    		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
        	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());    	
        	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
        	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
        	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
        	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
        	operationalFlightVO.setFlightType(flightValidationVO.getFlightType());
        	operationalFlightVO.setPou(transferformModel.getFlightPou().toUpperCase());
        	
        	if(flightValidationVO.getAtd() != null){
				operationalFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
			}
    		
    	}
    	
	

	// IF REASSIGNING TO DESTINATION
	else {
		//AirlineValidationVO airlineValidationVO = 
			//transferContainerSession.getAirlineValidationVO();
		log.log(Log.FINE, "AirlineValidationVO ------------> ",
				airlineValidationVO);
		operationalFlightVO.setCarrierCode(airlineValidationVO.getAlphaCode());
    	operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());  
    	operationalFlightVO.setFlightDate(null);
    	operationalFlightVO.setFlightNumber("-1");
    	operationalFlightVO.setFlightSequenceNumber(-1);
    	operationalFlightVO.setLegSerialNumber(-1);
    	operationalFlightVO.setPou(transferformModel.getDestination().toUpperCase());
    	
    	//for (ContainerVO vo : selectedContainerVOs) {		
    	for (ContainerVO vo : selectedTransferContainerVOs) {		
			//vo.setRemarks(transferformModel.getRemarks());
			vo.setReassignFlag(true);
			vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
			//ADDED FOR ICRD-95510
			//vo.setPou(transferContainerForm.getDestination().toUpperCase());
				if("MTK060".equals(transferformModel.getFromScreen()) &&transferformModel.getCarrier().equals(vo.getCarrierCode())  && vo.getFlightNumber().equals("-1")){
					if(transferformModel.getDestination().equalsIgnoreCase(vo.getFinalDestination())){
					ErrorVO errorVO = new ErrorVO(    
							"mailtracking.defaults.reassigncontainer.msg.err.reassigningToSameCarrier");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors = new ArrayList<>();
					errors.add(errorVO);
					break;
					}
					vo.setContainerDestChanged(true);
			}
			vo.setFinalDestination(transferformModel.getDestination().toUpperCase());
			vo.setMailSource(transferformModel.getFromScreen());//Added for IASCB-46908  
			vo.setTransactionLevel(MailConstantsVO.ULD_LEVEL_TRANSACTION);
		}
	  }
		TransferManifestVO transferManifestVO=null;
		String printFlag=transferformModel.getPrintTransferManifestFlag();
		printFlag = "N"; 
		try {
			log.log(Log.FINE, "selectedContainerVOs for saving-------> ",
					selectedTransferContainerVOs);
			log.log(Log.FINE, "operationalFlightVO for saving-------> ",
					operationalFlightVO);
			if(doSecurityAndScreeningValidations(operationalFlightVO,selectedTransferContainerVOs, actionContext,listContainerModel)){
				return;
			}
			
		
			if(FROM_SCREEN_EXPORTLIST.equals(transferformModel.getFromScreen())||FROM_SCREEN_ACCEPTANCE.equals(transferformModel.getFromScreen()) || (FROM_SCREEN_CONTAINER.equals(transferformModel.getFromScreen())
					||"MTK060".equals(transferformModel.getFromScreen())&& logonAttributes.getAirportCode().equals(selectedTransferContainerVOs.iterator().next().getPol()))){//added by A-7371 for ICRD-133987
				
				transferManifestVO=mailTrackingDefaultsDelegate.transferContainersAtExport(
	    				//selectedContainerVOs,
	    				selectedTransferContainerVOs,
	    				operationalFlightVO,printFlag);
				
			 }else{
			
			transferManifestVO=mailTrackingDefaultsDelegate.transferContainers(
					//selectedContainerVOs,
					selectedTransferContainerVOs,
					operationalFlightVO,printFlag);
			}
	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				
			    actionContext.addAllError((List<ErrorVO>) errors); 
				transferformModel.setStatus("");//Patch provided by A-4809 as discussed with Santhi
				return;
			}/*else{
		    	  log.log(Log.FINE, "\n\n DtransferManifestVO for transfer ------->",
			    		  transferManifestVO);
				  if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(printFlag)){ 
					    printNeeded=true;
						ReportSpec reportSpec = getReportSpec();    
						reportSpec.addFilterValue(transferManifestVO);
						reportSpec.setProductCode(PRODUCTCODE);
						reportSpec.setSubProductCode(SUBPRODUCTCODE);
						reportSpec.setPreview(true);
						reportSpec.setResourceBundle(BUNDLE);
						reportSpec.setReportId(TRFMFT_REPORT_ID);
						reportSpec.setAction(ACTION);   
						generateReport();  
						 
						
				      }    
			  }*/
    	
			transferformModel.setStatus("closeWindow");
			/* if(printNeeded){
		    	 invocationContext.target = getTargetPage();    
		     }else{
		    	 invocationContext.target = TARGET_SUCCESS;	 }       
				 */	
			 ResponseVO responseVO = new ResponseVO();	  
		     responseVO.setStatus("transfersave_success");
		     actionContext.setResponseVO(responseVO);  
			log.exiting("SaveTransferContainerCommand","execute");
			
		}
	
	
	
	/**
	 * 
	 * 	Method		:	TransferContainerSaveCommand.isPouValid
	 *	Added by 	:	a-7929 on 05-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	validationerrors
	 */
	 private Collection<ErrorVO> isPouValid(Collection<ContainerVO> selectedTransferContainerVOs, FlightValidationVO flightValidationVO,
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
			
			
			for(ContainerVO container : selectedTransferContainerVOs){
				if(!pointOfUnladings.contains(container.getPou().toUpperCase())) {
					Object[] obj = {container.getPou().toUpperCase(),
						flightValidationVO.getCarrierCode(),
						flightValidationVO.getFlightNumber(),
						fullroute.substring(0,fullroute.length()-1)};
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.invalidFlightpou",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);			
				validationerrors.add(errorVO);
			}
			}
	    	return validationerrors;
	}
	 
	 
	 
	 /**
		 * 
		 * 	Method		:	TransferContainerSaveCommand.getPointOfUnladings
		 *	Added by 	:	a-7929 on 05-Oct-2018
		 * 	Used for 	:
		 *	Parameters	:	@return 
		 *	Return type	: 	pointOfUnladings
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
	 
	 
	 
	 
	   /**
		 * 
		 * 	Method		:	TransferContainerSaveCommand.handleFlightFilterVO
		 *	Added by 	:	a-7929 on 05-Oct-2018
		 * 	Used for 	:
		 *	Parameters	:	@return 
		 *	Return type	: 	flightFilterVO
		 */
	private FlightFilterVO handleFlightFilterVO(TransferForm transferformModel, LogonAttributes logonAttributes) {
		 FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			flightFilterVO.setFlightNumber(transferformModel.getFlightNumber().toUpperCase());
			flightFilterVO.setStation(logonAttributes.getAirportCode());
			flightFilterVO.setDirection(OUTBOUND);
			flightFilterVO.setIncludeACTandTBC(true);
			if(transferformModel.getFlightSeqNumber()>0){
	 			flightFilterVO.setFlightSequenceNumber(transferformModel.getFlightSeqNumber());	
	 		}
	 		else{
			flightFilterVO.setStringFlightDate(transferformModel.getFlightDate());
	 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
	 		flightFilterVO.setFlightDate(date.setDate(
	 				transferformModel.getFlightDate()));
	 		}
			
			return flightFilterVO;
		
	}
	
	
	

	/**
	 * 
	 * 	Method		:	TransferContainerSaveCommand.isReassignedToSameFlight
	 *	Added by 	:   a-7929 on 05-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	validationerrors
	 */
    private Collection<ErrorVO> isReassignedToSameFlight(
    		FlightValidationVO reassignedFlightValidationVO,
    		Collection<ContainerVO> selectedContainerVOs,
    		TransferForm reassignContainerForm) {
    	
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
	 * 
	 * 	Method		:	TransferContainerSaveCommand.validateForm
	 *	Added by 	:	a-7929 on 06-OCt-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	formErrors
	 */
    private Collection<ErrorVO> validateForm(
			 TransferForm transferformModel,
				LogonAttributes logonAttributes) {

			Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
			
			String reassignedto = transferformModel.getReassignedto();
			
			/*if (reassignContainerForm.getRemarks().equals("")) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noremarks");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}*/
			
			if (CONST_FLIGHT.equals(reassignedto)) {    		
				if (("").equals(transferformModel.getFlightCarrierCode())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noFlightCarrierCode");
					formErrors.add(errorVO);
				}
				if (("").equals(transferformModel.getFlightNumber())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noFlightNumber");
					formErrors.add(errorVO);
				}
				if (("").equals(transferformModel.getFlightDate())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noFlightDate");
					formErrors.add(errorVO);
				}
				if (("").equals(transferformModel.getFlightPou())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noFlightPou");
					formErrors.add(errorVO);
				}
							
				String[] fltCarriers = transferformModel.getFltCarrier();
				String[] fltNos = transferformModel.getFltNo();
				String[] depDates = transferformModel.getDepDate();
				String[] pous = transferformModel.getPointOfUnlading();
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
				
				if (("").equals(transferformModel.getCarrier())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noCarrier");
					formErrors.add(errorVO);
				}
				/*else {
					if(CONST_DESTN.equals(transferformModel.getAssignedto())) {					
						AssignContainerSession assignContainerSession = 
				    		getScreenSession(MODULE_NAME,ASSIGN_SCREEN_ID);
						AirlineValidationVO airlineValidationVO = assignContainerSession.getAirlineValidationVO();
						if (airlineValidationVO.getAlphaCode().equalsIgnoreCase(transferformModel.getCarrier())) {
							ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.reassigncontainer.msg.err.reassigningToSameCarrier");
							formErrors.add(errorVO);
						}
					}
				} */
				if (("").equals(transferformModel.getDestination())) {
				ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noDestination");
				formErrors.add(errorVO);
				}
				else if(logonAttributes.getAirportCode().equals(transferformModel.getDestination())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.destnEqualToCurrentAirport");
					formErrors.add(errorVO);
				}
			}

			return formErrors;
		}
    
    
    
    /**
	 * 
	 * 	Method		:	TransferContainerSaveCommand.validateContainerVOs
	 *	Added by 	:	a-7929 on 06-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	validationerrors
	 */
    private Collection<ErrorVO> validateContainerVOs(TransferForm transferformModel,
			Collection<ContainerVO> selectedTransferContainerVOs) {
		  log.entering("ValidateReassignCommand","validateContainerVOs");
	    	
	    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
	    	
	    	//Collection<ContainerVO> containerVOs = transferContainerSession.getSelectedContainerVOs();
	    	
	    	Collection<ContainerVO> destnAssignedContainerVOs = new ArrayList<ContainerVO>();
	    	for (ContainerVO vo : selectedTransferContainerVOs) {
	    		if (vo.getFlightDate() == null) {
	    			destnAssignedContainerVOs.add(vo);
	    		}
	    	}
	    	log.log(Log.FINE, "DestnAssignedContainerVOs--------------",
					destnAssignedContainerVOs);
			if (destnAssignedContainerVOs.size() > 0) {
	    		if (CONST_DESTN.equals(transferformModel.getReassignedto())) {
	    			String carrier = transferformModel.getCarrier().toUpperCase();
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
	 * validateULDIncomatibility
	  *@author A-5526 for IASCB-34124 
	 * @param selectedTransferContainerVOs
	 * @param flightValidationVO
	 * @param actionContext
	 */
	private Collection<ErrorVO> validateULDIncomatibility(Collection<ContainerVO> selectedTransferContainerVOs, FlightValidationVO flightValidationVO, Collection<String> code) {

//		Collection<String> parameterCodes = new ArrayList<String>();
//		// ICRD-56719
		Collection<ErrorVO> errors = null;
//
//		parameterCodes.add(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
//
//		Map<String, String> systemParameters = null;
//		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
//		try {
//			systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterCodes);
//		} catch (BusinessDelegateException e) {
//			log.log(Log.INFO, "caught BusinessDelegateException ");
//			e.getMessageVO().getErrors();
//		}

		Map<String, String> systemParameter = getSystemParameter(code);

		ArrayList<String> uldTypeCodes = new ArrayList<String>();
		ArrayList<String> uldNumberCodes = new ArrayList<String>();

		if(selectedTransferContainerVOs!= null &&
				selectedTransferContainerVOs.size()>0){
			for(ContainerVO containerVO:selectedTransferContainerVOs){
				
					/*
					 * ULD type compatibility validation
					 */
				if(!containerVO.getType().equals("B")){
					if(containerVO.getContainerNumber() != null &&
							containerVO.getContainerNumber().trim().length() > 0 ){
						String uldType=containerVO.getContainerNumber().substring(0, 3);
						if(!uldTypeCodes.contains(uldType.toUpperCase())){
							uldTypeCodes.add(uldType.toUpperCase());
						}
						uldNumberCodes.add(containerVO.getContainerNumber());
					}
					}
				
			}
		}
		
		
		
		
		
			Collection<ULDPositionFilterVO> filterVOs = new ArrayList<ULDPositionFilterVO>();
			if (flightValidationVO != null) {
				Collection<String> aircraftTypes = new ArrayList<String>();
				aircraftTypes.add(flightValidationVO.getAircraftType());
				ULDPositionFilterVO filterVO = null;
				Collection<String> validatedUldTypeCodes = validateAirCraftCompatibilityforUldTypes(uldTypeCodes,
						systemParameter);
				if (validatedUldTypeCodes != null && validatedUldTypeCodes.size() > 0) {
					for (String uldType : validatedUldTypeCodes) {
						filterVO = new ULDPositionFilterVO();
						filterVO.setAircraftTypes(aircraftTypes);
						filterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
						filterVO.setUldCode(uldType);
						filterVOs.add(filterVO);
					}
				}
			}
			if (filterVOs != null && filterVOs.size() > 0) {
				try {
					new ULDDelegate().findULDPosition(filterVOs);
				} catch (BusinessDelegateException businessDelegateException) {
					Collection<ErrorVO> errs = handleDelegateException(businessDelegateException);
					for (ErrorVO error : errs) {
						log.log(Log.FINE, "Error code received -->>", error.getErrorCode());
						if (MailConstantsVO.ULD_INCOMPATIBLEAIRCRAFT.equals(error.getErrorCode())) {
							Object[] errorData = error.getErrorData();
							String errorDatum = null;
							if (errorData != null && errorData.length > 0) {
								errorDatum = (String) errorData[0];
							}

							
							if(errors == null){
	        					errors = new ArrayList<ErrorVO>();
	        				}
							errors.add(new ErrorVO(
									"mailtracking.defaults.reassigncontainer.msg.err.uldincompatileforaircrafttype",
									new Object[] { errorDatum }));
	        				
	        				return errors;
							
						}
					}

				}
			}
		return null;
	}
	 
	 /**
	  * @author A-5526 for IASCB-34124
	  * validateAirCraftCompatibilityforUldTypes
	  * @param uldTypeCodes
	  * @param systemParameterMap
	  * @return
	  */
	public Collection<String> validateAirCraftCompatibilityforUldTypes(Collection<String> uldTypeCodes,
			Map<String, String> systemParameterMap) {
		log.entering("SaveAcceptanceCommand", "validateAirCraftCompatibilityforUldTypes");
		ArrayList<String> uldTypeCodesForValidation = null;
		if (systemParameterMap != null && systemParameterMap.size() > 0) {
			String configuredTypes = systemParameterMap.get(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
			if (configuredTypes != null && configuredTypes.length() > 0 && !"N".equals(configuredTypes)) {
				if ("*".equals(configuredTypes)) {
					for (String uldType : uldTypeCodes) {
						if (uldTypeCodesForValidation == null) {
							uldTypeCodesForValidation = new ArrayList<String>();
						}
						uldTypeCodesForValidation.add(uldType);
					}
				} else {
					List<String> configuredTypesList = Arrays.asList(configuredTypes.split(","));
					if (uldTypeCodes != null && uldTypeCodes.size() > 0) {
						for (String uldType : uldTypeCodes) {
							if (configuredTypesList.contains(uldType)) {
								if (uldTypeCodesForValidation == null) {
									uldTypeCodesForValidation = new ArrayList<String>();
								}
								uldTypeCodesForValidation.add(uldType);
							}
						}
					}
				}
			}
		}
		log.exiting("SaveAcceptanceCommand", "validateAirCraftCompatibilityforUldTypes");
		return uldTypeCodesForValidation;
	}
	/**
	 * @author A-7779
	 * @param stnPar
	 * @return
	 */
	private String findStationParameterValue(String stnPar){
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		AreaDelegate areaDelegate = new AreaDelegate();
		//String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL); 
		Map stationParameters = null; 
		String stationCode = logonAttributes.getStationCode();	
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(STNPAR_DEFUNIT_WGT);
		try {
			stationParameters = areaDelegate.findStationParametersByCode(logonAttributes.getCompanyCode(), stationCode, parameterCodes);
		} catch (BusinessDelegateException e1) {
			e1.getMessage();
		} 
		return (String)stationParameters.get(STNPAR_DEFUNIT_WGT);
	}
	
	public Map<String, String> getSystemParameter(Collection<String> code) {

		Map<String, String> systemParameters = null;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(code);
		} catch (BusinessDelegateException e) {
			log.log(Log.INFO, "caught BusinessDelegateException");
			e.getMessageVO().getErrors();
		}
		return systemParameters;
	}
	/**
	 *  for AA no need to validate against TBC flight
	 * @return
	 */
	private boolean canIgnoreToBeActionedCheck() {
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add("mail.operations.ignoretobeactionedflightvalidation");
		Map<String, String> systemParameters = null;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterCodes);
		} catch (BusinessDelegateException e) {
			log.log(Log.INFO, "caught BusinessDelegateException ");
		}
		if(systemParameters!=null && systemParameters.containsKey("mail.operations.ignoretobeactionedflightvalidation")) {
			return "Y".equals(systemParameters.get("mail.operations.ignoretobeactionedflightvalidation"));
		}
		return false;
	}
	/**
	 * @author A-8353
	 * @param listContainerModel 
	 * @throws BusinessDelegateException 
	 */
	private boolean doSecurityAndScreeningValidations(OperationalFlightVO operationalFlightVO,
			Collection<ContainerVO> selectedContainerVOs,ActionContext actionContext, ListContainerModel listContainerModel) throws BusinessDelegateException {
		SecurityScreeningValidationVO securityScreeningValidationVO=null;
		boolean securityWarningStatus = listContainerModel.isScreenWarning();
		if(!securityWarningStatus) {
			securityScreeningValidationVO=new MailTrackingDefaultsDelegate().doSecurityAndScreeningValidationAtContainerLevel(operationalFlightVO,selectedContainerVOs);
				if(securityScreeningValidationVO!=null&& checkForWarningOrError(actionContext, securityScreeningValidationVO)){
					return true;
				}
		}
		return false;

	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param actionContext
	 * @param existigWarningMap
	 * @param securityScreeningValidationVO
	 * @return
	 */
	private boolean checkForWarningOrError( ActionContext actionContext, SecurityScreeningValidationVO securityScreeningValidationVO) {
		if ("W".equals(securityScreeningValidationVO
				.getErrorType())) {
			List<ErrorVO> warningErrors = new ArrayList<>();
			ErrorVO warningError = new ErrorVO(
					SECURITY_SCREENING_WARNING,
					new Object[]{securityScreeningValidationVO.getMailbagID()});
			warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
			warningErrors.add(warningError);
			actionContext.addAllError(warningErrors); 
			return true;

		}
		if ("E".equals(securityScreeningValidationVO
				.getErrorType())) {
			if ("AR".equals(securityScreeningValidationVO
					.getValidationType())){
				actionContext.addError(new ErrorVO(APPLICABLE_REGULATION_ERROR));
			}
			else{
			actionContext.addError(new ErrorVO(SECURITY_SCREENING_ERROR,
					new Object[]{securityScreeningValidationVO.getMailbagID()}));
			}
			return true;
		}
		return false;
	}
}

