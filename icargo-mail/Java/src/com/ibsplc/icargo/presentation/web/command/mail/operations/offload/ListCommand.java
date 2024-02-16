/*
 * ListCommand.java Created on July 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.offload;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OffloadSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OffloadForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class ListCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILTRACKING");

   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "list_success";
   private static final String TARGET_FAILURE = "list_failure";
   private static final String TARGET_OFFLOAD_SUCCESS = "save_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.offload";
   private static final String FLIGHT_MODULE_NAME = "flight.operation";
   private static final String FLIGHT_SCREEN_ID = "flight.operation.duplicateflight";

   private static final String OUTBOUND = "O";
   private static final String FLAG_YES = "YES";
   private static final String FLAG_NORMALSEARCH = "NORMAL";
   private static final String FLAG_ADVANCEDSEARCH = "ADVANCED";
   private static final String BLANKSPACE = "";

   private static final String CONST_SEARCH_CONTAINER = "SEARCHCONTAINER";
   private static final String CONST_MAILBAG_ENQUIRY = "MAILBAGENQUIRY";
   private static final String CONST_DSN_ENQUIRY = "DSNENQUIRY";

   private static final String FLAG_OFFLOADED = "offloaded";
   
   private static final String OFFLOAD_SUCCESS = "mailtracking.defaults.offload.info.offloadsuccess";
   private static final String NO_DETAILS = "mailtracking.defaults.offload.msg.err.noDetails";
   private static final String PARTIAL_CONTAINERDETAILS="mailtracking.defaults.offload.msg.err.enterfullcontainerdetails";
   private static final String PARTIAL_FLIGHTDETAILS= "mailtracking.defaults.offload.msg.err.enterfullflightdetails";
   private static final String DETAILS_MANDATORY = "mailtracking.defaults.offload.msg.err.mandatorycheck";
   private static final String PARTIAL_CONTAINERANDFLIGHTDETAILS=  "mailtracking.defaults.offload.msg.err.mandatorycheck";
   private static final String FLIGHT_DEPARTED="flight_departed";
   private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";
   private static final String ULD_TYPE = "U";	//Added for ICRD-128804
   private static final String INVALID_ULD ="mailtracking.defaults.offload.msg.err.invaliduldnumber";
   private static final String OFFLOAD_SUCCESS_FOR_CONTAINER = "mailtracking.defaults.offload.info.offloadsuccessforcontainer";
   private static final String OFFLOAD_DSN_TYPE="D";//added by A-7371 for ICRD-214756

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ListCommand","execute");

    	OffloadForm offloadForm =
    		(OffloadForm)invocationContext.screenModel;
    	OffloadSession offloadSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();
		OffloadVO mainOffloadVO = null;

		String fromscreen = offloadForm.getFromScreen();
		log.log(Log.FINE, "fromscreen ------------> ", fromscreen);
		// IF OFFLOAD SCREEN INVOKED FROM SEARCHCONTAINER SCREEN
		if (CONST_SEARCH_CONTAINER.equals(fromscreen)) {

			handleOffloadFromSearchContainer(
					offloadForm,
					offloadSession);
			
			//This initialization is for a flagging purpose,
			//done as a work around
			mainOffloadVO = new OffloadVO();
		}
		// IF OFFLOAD SCREEN INVOKED FROM MAILBAG ENQUIRY SCREEN
		else if (CONST_MAILBAG_ENQUIRY.equals(fromscreen)) {

			handleOffloadFromMailBagEnquiry(
					offloadForm,
					offloadSession);
			//This initialization is for a flagging purpose,
			//done as a work around
			mainOffloadVO = new OffloadVO();
		}
		// IF OFFLOAD SCREEN INVOKED FROM DSN ENQUIRY SCREEN
		else if (CONST_DSN_ENQUIRY.equals(fromscreen)) {

			handleOffloadFromDsnEnquiry(
					offloadForm,
					offloadSession);
			//This initialization is for a flagging purpose,
			//done as a work around
			mainOffloadVO = new OffloadVO();
		}
		else {
			// VALIDATING FORM
			errors = validateForm(offloadForm);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}

			offloadSession.setOffloadVO(null);

			DuplicateFlightSession duplicateFlightSession = (DuplicateFlightSession)getScreenSession(
					FLIGHT_MODULE_NAME, FLIGHT_SCREEN_ID);

			AirlineDelegate airlineDelegate = new AirlineDelegate();
	    	AirlineValidationVO airlineValidationVO = null;
	    	
	    	// Added by A-5153 for BUG_ICRD-90006
	    	boolean continueForTbaTbcStatus = false;
	    	
	    	if((FLAG_NORMALSEARCH.equals(offloadForm.getMode()) &&
	    			!FLAG_YES.equals(offloadForm.getStatus()))
	    			|| (FLAG_ADVANCEDSEARCH.equals(offloadForm.getMode()) &&
	    					offloadSession.getFlightValidationVO() == null )) {

	    		offloadSession.setFlightValidationVO(null);

	    		// VALIDATE FLIGHT CARRIER CODE
	    		String flightCarrierCode = offloadForm.getFlightCarrierCode();
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
	    					"mailtracking.defaults.offload.msg.err.invalidCarrier",obj);
	    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	    				errors.add(errorVO);
	        			invocationContext.addAllError(errors);
	        			invocationContext.target = TARGET_FAILURE;
	        			return;
	        		}
	        	}
	        	

	        	// VALIDATING FLIGHT NUMBER
	        	if(!("").equals(offloadForm.getDate())&&!("").equals(offloadForm.getFlightCarrierCode())&&!("").equals(offloadForm.getFlightNumber())){
	    		FlightFilterVO flightFilterVO = handleFlightFilterVO(
	    					offloadForm,
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
	    		if (errors != null && errors.size() > 0) {
	    			invocationContext.addAllError(errors);
	    			invocationContext.target = TARGET_FAILURE;
	    			return;
	    		}
	    		else {
	    			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {//IF NO RESULTS RETURNED
	    				log.log(Log.FINE, "----------------FlightValidationVOs is NULL");
	    				Object[] obj = {offloadForm.getFlightCarrierCode().toUpperCase(),
	    						offloadForm.getFlightNumber().toUpperCase(),
	    						offloadForm.getDate()};
	    				ErrorVO errorVO = new ErrorVO(
	    						"mailtracking.defaults.offload.msg.err.noflightDetails",obj);
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
							if(!FlightValidationVO.FLAG_YES.equalsIgnoreCase(offloadForm.getReListFlag()) 
									&& (FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) 
											|| FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus()))){
								if(!FlightValidationVO.FLAG_YES.equals(offloadForm.getWarningOveride())){
								ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
								err.setErrorDisplayType(ErrorDisplayType.WARNING);
								invocationContext.addError(err);
								invocationContext.target = TARGET_FAILURE;
								offloadForm.setWarningFlag(FLIGHT_TBC_TBA);
								return;
								}else{
									offloadForm.setWarningFlag("");
									offloadForm.setWarningOveride(null);
									continueForTbaTbcStatus = true;
								}
								
							}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus())){
				            	Object[] obj = {flightValidVO.getCarrierCode().toUpperCase(),flightValidVO.getFlightNumber()};
								ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
								err.setErrorDisplayType(ErrorDisplayType.ERROR);
								invocationContext.addError(err);
								invocationContext.target = TARGET_FAILURE;
								return;
							} 
							
	    					offloadSession.setFlightValidationVO(flightValidVO);
	    					break;
	    				}
	    			}
	    			else if(flightValidationVOs.size() > 1){ //IF MORE VOS RETURNED
	    				log.log(Log.FINE, "--------------Duplicate flight VO");
	    				duplicateFlightSession.setFlightValidationVOs(
	    						(ArrayList<FlightValidationVO>)flightValidationVOs);
	    				duplicateFlightSession.setParentScreenId(SCREEN_ID);
	    				duplicateFlightSession.setFlightFilterVO(flightFilterVO);
	    				duplicateFlightSession.setScreenOfParent("Offload");
	    				offloadForm.setStatus("showDuplicateFlights");
	    				invocationContext.target = TARGET_FAILURE;
	    				return;
	    			}
	    		}

	    	}
	        	//VALIDATE ULD NUMBER Added for ICRD-128804 starts
	    		ULDDelegate uldDelegate = new ULDDelegate();
	    		String containerNumber=offloadForm.getContainerNumber();
	    		String containerType=offloadForm.getContainerType();
	    		boolean isULDType = ULD_TYPE.equals(containerType);
	    		if(isULDType&&containerNumber!=null&&containerNumber.trim().length()>0){
					try {
						uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNumber);
					}catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
	        		if (errors != null && errors.size() > 0) {
	        			errors = new ArrayList<ErrorVO>();
	        			Object[] obj = {containerNumber};
	    				ErrorVO errorVO = new ErrorVO(
	    						INVALID_ULD,obj);
	    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	    				errors.add(errorVO);
	        			invocationContext.addAllError(errors);
	        			invocationContext.target = TARGET_FAILURE;
	        			return;
	        		}
				}
	    		//VALIDATE ULD NUMBER Added for ICRD-128804 ends

	    	}
	    	OffloadFilterVO offloadFilterVO = handleFilterDetails(
								    			offloadForm,
								    			offloadSession,
								    			logonAttributes,
								    			duplicateFlightSession);

	    	log
					.log(Log.FINE, "OffloadFilterVO-------------->",
							offloadFilterVO);
			try {  //added by A-7371 for ICRD-214756
				if(!OFFLOAD_DSN_TYPE.equals(offloadForm.getType())){				
	    		mainOffloadVO = mailTrackingDefaultsDelegate.findOffloadDetails(offloadFilterVO);
						}
	    		log
						.log(Log.FINE, "mainOffloadVO-------------->",
								mainOffloadVO);

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				offloadForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			
	    	// FINDING THE LISTING VALUES
			if (!continueForTbaTbcStatus && !("").equals(offloadForm.getDate())
					&& !("").equals(offloadForm.getFlightCarrierCode())
					&& !("").equals(offloadForm.getFlightNumber())) {
	    	boolean isFlightClosed = false;
			FlightValidationVO flightValidationVO = offloadSession.getFlightValidationVO();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
	    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
	    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
	    	operationalFlightVO.setDirection(OUTBOUND);
	    	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
	    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
	    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	    	boolean isTbaWithoutMailBag = false;
	    	if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) && mainOffloadVO!=null){
	    		if(mainOffloadVO.getOffloadContainers()!=null){
	    			for(ContainerVO containerVO : mainOffloadVO.getOffloadContainers())
	    				{
	    				operationalFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
	    		}
	    		}
	    		else if(mainOffloadVO.getOffloadMailbags()!=null){
	    			for(MailbagVO mailbagVO : mainOffloadVO.getOffloadMailbags())
	    				{
	    				operationalFlightVO.setLegSerialNumber(mailbagVO.getLegSerialNumber());
	    		}
	    	}
	    	}
	    	else{
	    		if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()))
	    			{
	    			isTbaWithoutMailBag = true;	
	    			}	
	    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
	    	}
	    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
	    	if(!isTbaWithoutMailBag){//Added by A-5160 as for tba flights with no containers/mailbags this check can be bypassed	    		
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
			log.log(Log.FINE, "@@@@@@@@^@@@@@@@@@@isFlightClosed",
					isFlightClosed);
			if(!isFlightClosed){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.offload.flightclosed"));
	 	   		invocationContext.target =TARGET_SUCCESS;
	 	   		return;
				}
			}
		}
			
			if (mainOffloadVO != null ) {
				offloadSession.setOffloadVO(mainOffloadVO);
			}
		}
		if(FLAG_NORMALSEARCH.equals(offloadForm.getMode())) {
			offloadForm.setStatus("NORMAL_SEARCH_DONE");
		}
		/*
		 * For ANZ BUG 50420
		 * START
		 */
		if(!FLIGHT_DEPARTED.equalsIgnoreCase(offloadForm.getFlightStatus())) {
			offloadForm.setUldSubCheck(null);
			offloadForm.setMailbagSubCheck(null);
			offloadForm.setDsnSubCheck(null);
		}
		//END BUG 50420
		
		
		if(FLAG_OFFLOADED.equalsIgnoreCase(offloadForm.getFlightStatus())) {				
			//Modified for ICRD-153556 starts
			if("U".equals(offloadForm.getType())){
			ErrorVO errorVO = new ErrorVO(OFFLOAD_SUCCESS_FOR_CONTAINER);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(errorVO);
			invocationContext.addAllError(errors);		
			if(offloadSession.getFlightValidationVO()!=null)
			{
			offloadForm.setFlightStatus(offloadSession.getFlightValidationVO().getLegStatus());
			}
			offloadForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = TARGET_OFFLOAD_SUCCESS;
			}else{
			ErrorVO errorVO = new ErrorVO(OFFLOAD_SUCCESS);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(errorVO);
			invocationContext.addAllError(errors);		
			if(offloadSession.getFlightValidationVO()!=null)
			{
			offloadForm.setFlightStatus(offloadSession.getFlightValidationVO().getLegStatus());
			}
			offloadForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = TARGET_OFFLOAD_SUCCESS;					
			}
			//Modified for ICRD-153556 ends
		}else {	

			
			if (mainOffloadVO == null ) {
				ErrorVO errorVO = new ErrorVO(NO_DETAILS);
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				if(offloadSession.getFlightValidationVO()!=null){
				offloadForm.setFlightStatus(offloadSession.getFlightValidationVO().getLegStatus());
				}
				offloadForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET_FAILURE;
			}else {
				offloadForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET_SUCCESS;
			}
		}
		
    	log.exiting("ListCommand","execute");

    }
    /**
     * Method is to set filter values to form when invoked from
     * search container screen
     * @param offloadForm
     * @param offloadSession
     */
    private void handleOffloadFromSearchContainer(
    		OffloadForm offloadForm,
    		OffloadSession offloadSession) {

    	log.entering("ListCommand","handleOffloadFromSearchContainer");

    	OffloadVO offloadVO = offloadSession.getOffloadVO();
    	if (offloadVO != null) {

    		offloadForm.setFlightCarrierCode(offloadVO.getCarrierCode());
    		offloadForm.setFlightNumber(offloadVO.getFlightNumber());
    		offloadForm.setDate(offloadVO.getFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
    		offloadForm.setType("U");
    		 Collection<ContainerVO> containerVOs = offloadVO.getOffloadContainers();
    		 if(containerVOs!=null){
    		for(ContainerVO containerVO: containerVOs){
    			offloadForm.setContainerNumber(containerVO.getContainerNumber());
    			offloadForm.setContainerType(containerVO.getType());
    		}
    		log.log(Log.FINE, "handleOffloadFromSearchContainer :container num ",offloadForm.getContainerNumber());	
    		log.log(Log.FINE, "handleOffloadFromSearchContainer :container num ",offloadForm.getContainerType());	
    		 }
    		offloadForm.setMode(FLAG_NORMALSEARCH);
    		//offloadForm.setFlightStatus("");
			offloadForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	}

    	log.exiting("ListCommand","handleOffloadFromSearchContainer");

    }

    /**
     * Method is to set filter values to form when invoked from
     * mailbag enquiry screen
     * @param offloadForm
     * @param offloadSession
     */
    private void handleOffloadFromMailBagEnquiry(
    		OffloadForm offloadForm,
    		OffloadSession offloadSession) {

    	log.entering("ListCommand","handleOffloadFromMailBagEnquiry");

    	OffloadVO offloadVO = offloadSession.getOffloadVO();
    	if (offloadVO != null) {

    		offloadForm.setFlightCarrierCode(offloadVO.getCarrierCode());
    		offloadForm.setFlightNumber(offloadVO.getFlightNumber());
    		offloadForm.setDate(offloadVO.getFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
    		offloadForm.setType("M");
    		 Collection<MailbagVO> mailbagVOs = offloadVO.getOffloadMailbags();
    		 if(mailbagVOs!=null){
    		for(MailbagVO mailbagVO: mailbagVOs){
    			offloadForm.setContainerNumber(mailbagVO.getContainerNumber());
    			offloadForm.setContainerType(mailbagVO.getContainerType());
    		}
    		log.log(Log.FINE, "handleOffloadFromMailBagEnquiry :container num ",offloadForm.getContainerNumber());	
    		log.log(Log.FINE, "handleOffloadFromMailBagEnquiry :container typ ",offloadForm.getContainerType());	
    		 }
    		offloadForm.setMode(FLAG_NORMALSEARCH);
    		//offloadForm.setFlightStatus("");
			offloadForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	}

    	log.exiting("ListCommand","handleOffloadFromMailBagEnquiry");

    }

    /**
     * Method is to set filter values to form when invoked from
     * mailbag enquiry screen
     * @param offloadForm
     * @param offloadSession
     */
    private void handleOffloadFromDsnEnquiry(
    		OffloadForm offloadForm,
    		OffloadSession offloadSession) {

    	log.entering("ListCommand","handleOffloadFromDsnEnquiry");

    	OffloadVO offloadVO = offloadSession.getOffloadVO();
    	if (offloadVO != null) {

    		offloadForm.setFlightCarrierCode(offloadVO.getCarrierCode());
    		offloadForm.setFlightNumber(offloadVO.getFlightNumber());
    		offloadForm.setDate(offloadVO.getFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
    		offloadForm.setType("D");
    		 Collection<DespatchDetailsVO> despatchDetailsVOs = offloadVO.getOffloadDSNs();
    		 if(despatchDetailsVOs!=null){
    		for(DespatchDetailsVO despatchDetailsVO: despatchDetailsVOs){
    			offloadForm.setContainerNumber(despatchDetailsVO.getContainerNumber());
    			offloadForm.setContainerType(despatchDetailsVO.getContainerType());
    		}
    		log.log(Log.FINE, "handleOffloadFromDsnEnquiry :container num ",offloadForm.getContainerNumber());	
    		log.log(Log.FINE, "handleOffloadFromDsnEnquiry :container typ ",offloadForm.getContainerType());	
    		 }
    		offloadForm.setMode(FLAG_NORMALSEARCH);
    		//offloadForm.setFlightStatus("");
			offloadForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	}

    	log.exiting("ListCommand","handleOffloadFromDsnEnquiry");

    }

    /**
     * This method is to get the filter details
     * @param offloadForm
     * @param offloadSession
     * @param logonAttributes
     * @param duplicateFlightSession
     * @return
     */
    private OffloadFilterVO handleFilterDetails(
    		OffloadForm offloadForm,
			OffloadSession offloadSession,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession) {

    	log.entering("ListCommand","handleFilterDetails");

    	FlightValidationVO flightValidationVO = null;

    	log.log(Log.FINE, "Status-------------->", offloadForm.getStatus());
		log.log(Log.FINE, "Mode-------------->", offloadForm.getMode());
		if(FLAG_YES.equals(offloadForm.getStatus())) {
    		flightValidationVO = duplicateFlightSession.getFlightValidationVO();
    		offloadSession.setFlightValidationVO(flightValidationVO);
    	}
    	else {
    		flightValidationVO = offloadSession.getFlightValidationVO();
    	}

    	OffloadFilterVO offloadFilterVO = new OffloadFilterVO();

    	String offloadtype = offloadForm.getType();
    	if(flightValidationVO != null ){
		offloadFilterVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	offloadFilterVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	offloadFilterVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
    	offloadFilterVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	offloadFilterVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	offloadFilterVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		offloadFilterVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	offloadFilterVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	offloadFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	offloadFilterVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
    	offloadFilterVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	offloadFilterVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	offloadFilterVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	}
    	offloadFilterVO.setPol(logonAttributes.getAirportCode());
    	offloadFilterVO.setOffloadType(offloadForm.getType());
    	offloadFilterVO.setContainerType("ALL");
    	/*Advanced search commented since after offlaod it can be normal or advanced search.
    	 * Done for bug 51166 */
    	//if(FLAG_ADVANCEDSEARCH.equals(offloadForm.getMode())) {

    		
        	offloadFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
        	
        	offloadFilterVO.setPol(logonAttributes.getAirportCode());
        	offloadFilterVO.setOffloadType(offloadForm.getType());

        	String contNumber = offloadForm.getContainerNumber();
   /*      	int length = 0;
        	if (contNumber != null && !contNumber.equals("")) {
        		length = contNumber.length();
        	}

        	offloadFilterVO.setContainerNumber(upper(contNumber));
        	if(length == 0){
				offloadFilterVO.setContainerType(null);
				offloadFilterVO.setContainerNumber(null);
			}
        	else if (length == 13) {
				offloadFilterVO.setContainerType(MailConstantsVO.BULK_TYPE);
			}
			else {
				offloadFilterVO.setContainerType(MailConstantsVO.ULD_TYPE);
			}*/
        	offloadFilterVO.setContainerNumber(upper(contNumber));
        	offloadFilterVO.setContainerType(offloadForm.getContainerType());

    		if (("D").equals(offloadtype)) {
    			offloadFilterVO.setDsn(offloadForm.getDespatchSn());
            	offloadFilterVO.setDsnDestinationExchangeOffice(upper(offloadForm.getDestnOE()));
            	offloadFilterVO.setDsnMailClass(offloadForm.getMailClass());
            	offloadFilterVO.setDsnOriginExchangeOffice(upper(offloadForm.getOriginOE()));
            	if (!BLANKSPACE.equals(offloadForm.getYear())) {
            		offloadFilterVO.setDsnYear(offloadForm.getYear());
            	}
    		}
    		else if (("M").equals(offloadtype)) {
    			offloadFilterVO.setMailbagCategoryCode(offloadForm.getMailbagCategory());
            	offloadFilterVO.setMailbagDestinationExchangeOffice(upper(offloadForm.getMailbagDestnOE()));
            	offloadFilterVO.setMailbagDsn(upper(offloadForm.getMailbagDsn()));
            	offloadFilterVO.setMailbagOriginExchangeOffice(upper(offloadForm.getMailbagOriginOE()));
            	offloadFilterVO.setMailbagRsn(upper(offloadForm.getMailbagRsn()));
            	offloadFilterVO.setMailbagSubclass(upper(offloadForm.getMailbagSubClass()));
            	offloadFilterVO.setMailbagId(upper(offloadForm.getMailbagId()));//Added as part of ICRD-205027
            	if (!BLANKSPACE.equals(offloadForm.getMailbagYear())) {
            		offloadFilterVO.setMailbagYear(Integer.parseInt(offloadForm.getMailbagYear()));
            	}
    		}
    //	}

    	offloadFilterVO.setPageNumber(Integer.parseInt(offloadForm
				.getDisplayPageNum()));

		log.exiting("ListCommand","handleFilterDetails");

    	return offloadFilterVO;

    }
    /**
     * Method to create the filter vo for flight validation
     * @param offloadForm
     * @param logonAttributes
     * @return FlightFilterVO
     */
    private FlightFilterVO handleFlightFilterVO(
    		OffloadForm offloadForm,
			LogonAttributes logonAttributes){

    	log.entering("ListCommand","handleFlightFilterVO");

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(offloadForm.getFlightNumber().toUpperCase());
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		if(!("").equals(offloadForm.getDate())&&!("").equals(offloadForm.getFlightCarrierCode())&&!("").equals(offloadForm.getFlightNumber())){
		flightFilterVO.setStringFlightDate(offloadForm.getDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(offloadForm.getDate()));
		}
 		
 		

 		log.exiting("ListCommand","handleFlightFilterVO");

		return flightFilterVO;
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
	 * This method is used for validating the form for the particular action
	 * @param offloadForm - OffloadForm
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			OffloadForm offloadForm) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		//added by A-5945 for ICRD-83320 starts
		if(("").equals(offloadForm.getContainerNumber())&&("").equals(offloadForm.getFlightCarrierCode())&&
				("").equals(offloadForm.getFlightNumber())&&("").equals(offloadForm.getDate())&&("").equals(offloadForm.getContainerType())){
			ErrorVO errorVO = new ErrorVO(DETAILS_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		if(("").equals(offloadForm.getContainerNumber())&&("").equals(offloadForm.getContainerType())){
			if(!(("").equals(offloadForm.getFlightCarrierCode()))||!(("").equals(offloadForm.getFlightNumber()))||!(("").equals(offloadForm.getDate()))){
				if(("").equals(offloadForm.getFlightCarrierCode())||("").equals(offloadForm.getFlightNumber())||("").equals(offloadForm.getDate())){
					log.log(Log.FINE, "empty container details and partial flight details");	
					ErrorVO errorVO = new ErrorVO(PARTIAL_FLIGHTDETAILS);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);	

				}
			}
		}
		if(("").equals(offloadForm.getFlightCarrierCode())&&("").equals(offloadForm.getFlightNumber())&&("").equals(offloadForm.getDate())){
			if(!(("").equals(offloadForm.getContainerNumber()))||!(("").equals(offloadForm.getContainerType()))){
				if(("").equals(offloadForm.getContainerNumber())||("").equals(offloadForm.getContainerType())){
					log.log(Log.FINE, "empty  flight details and partial container details ");
					ErrorVO errorVO = new ErrorVO(PARTIAL_CONTAINERANDFLIGHTDETAILS);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);	


				}
			}	
		}

		if((!(("").equals(offloadForm.getFlightCarrierCode()))||!(("").equals(offloadForm.getFlightNumber()))||!(("").equals(offloadForm.getDate())))
				&& (!(("").equals(offloadForm.getContainerNumber()))||!(("").equals(offloadForm.getContainerType())))){
			if(!(("").equals(offloadForm.getFlightCarrierCode()))&&!(("").equals(offloadForm.getFlightNumber()))&&!(("").equals(offloadForm.getDate()))||(!(("").equals(offloadForm.getContainerNumber()))&&!(("").equals(offloadForm.getContainerType())))){

				log.log(Log.FINE, " full  flight details or full container details ");	
			}
			else{
				log.log(Log.FINE, " partial  flight details and partial container details ");
				ErrorVO errorVO = new ErrorVO(PARTIAL_CONTAINERANDFLIGHTDETAILS);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);	
			}

		}

		/*if (("").equals(offloadForm.getFlightCarrierCode())) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.offload.msg.err.noFlightCarrierCode");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		if (("").equals(offloadForm.getFlightNumber())) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.offload.msg.err.noFlightNumber");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		if (("").equals(offloadForm.getDate())) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.offload.msg.err.noFlightDate");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}*/
  //This check commented for bug 50079
		/*if(FLAG_ADVANCEDSEARCH.equals(offloadForm.getMode())) {

			String offloadtype = offloadForm.getType();

			if (offloadtype.equals("D")) {

				if (!offloadForm.getDespatchSn().equals("")
						|| !offloadForm.getDestnOE().equals("")
						|| !offloadForm.getOriginOE().equals("")
						|| !offloadForm.getYear().equals("")) {

					if (offloadForm.getDespatchSn().equals("")) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.offload.msg.err.noDespatchSn");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
					}
					if (offloadForm.getDestnOE().equals("")) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.offload.msg.err.noDestnOE");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
					}
					if (offloadForm.getOriginOE().equals("")) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.offload.msg.err.noOriginOE");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
					}
					if (offloadForm.getYear().equals("")) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.offload.msg.err.noyear");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
					}
				}
    		}
		}*/


		return formErrors;
	}
}
