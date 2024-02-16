/*
 * ListMailAcceptanceCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
/**
 * @author A-5991
 *
 */
public class ListMailAcceptanceCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "list_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   private static final String SCREEN_IDAC = "mailtracking.defaults.assignContainer";
   /**
    * Screen id
    */
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   /**
    * Module name
    */
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_ASSIGNCONTAINER = "ASSIGNCONTAINER";
   private static final String OUTBOUND = "O";
   private static final String PREASSIGNMENT_SYS 
                = "mailtracking.defaults.acceptance.preassignmentneeded";
    
   private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListMailAcceptanceCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		mailAcceptanceForm.setOperationalStatus("");
		//Added as part of bug ICRD-155214 by A-5526 starts
		mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_NO);   
		//Added by A-7794 as part of ICRD-197439
		mailAcceptanceForm.setDisableButtonsForAirport(FLAG_NO);
//Added as part of bug ICRD-155214 by A-5526 ends		
		mailAcceptanceForm.setSelectMail(null);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		
	//----------------------------------------------------TO go Directly to Mail Acceptance screen ---------------------------------------------------		
		
		
		if (CONST_ASSIGNCONTAINER.equals(mailAcceptanceForm.getFromScreen())) {
		
			log.log(Log.FINE, "mailAcceptanceForm.getFromScreen():::>>>",
					mailAcceptanceForm.getFromScreen());
		AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_IDAC);  	  	
		
		Collection<ContainerVO> containerVOs = 
			assignContainerSession.getContainerVOs();
		
		/*
		//	VALIDATE WHETHER THERE ARE UNSAVED CONTAINERS 
		if(containerVOs != null && containerVOs.size() > 0){
	    	for (ContainerVO containerVO : containerVOs) {
	    		if (ContainerVO.OPERATION_FLAG_INSERT.equals(
	    				containerVO.getOperationFlag())) {
	    			
	    			errors = new ArrayList<ErrorVO>();
	    			ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.containersNotSavedForAccept");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = TARGET_FAILURE;
					return;
	    			
	    		}
	    	}
		}
		*/
    	
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		//Modified by A-7794 as part of ICRD-197439
		operationalFlightVO.setPol(mailAcceptanceForm.getDeparturePort());
		
		String assignedto = mailAcceptanceForm.getAssignToFlight();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		if (CONST_FLIGHT.equals(assignedto)) { 
			
			FlightValidationVO flightValidationVO = 
				assignContainerSession.getFlightValidationVO();
			mailAcceptanceSession.setFlightValidationVO(flightValidationVO);
			
			operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
			operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
			operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
			operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			/**
			 * ADDED FOR BUG 72779 
			 */
			operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
			/**
			 * ADDED FOR BUG 72779  ENDS
			 */
			operationalFlightVO.setDirection(OUTBOUND);
		}
		else {
			
			AirlineValidationVO airlineValidationVO = 
				assignContainerSession.getAirlineValidationVO();
			if(mailAcceptanceForm.getCarrier() != null && !"".equals(mailAcceptanceForm.getCarrier())) {
			operationalFlightVO.setCarrierCode(mailAcceptanceForm.getCarrier().toUpperCase());
			}
			else {
				if(mailAcceptanceVO.getFlightCarrierCode() != null) {
					operationalFlightVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				}
			}
			operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
			if(mailAcceptanceForm.getDestn() != null && !"".equals(mailAcceptanceForm.getDestn())) {
			operationalFlightVO.setPou(mailAcceptanceForm.getDestn().toUpperCase());			
			}
			else {
				if(mailAcceptanceVO.getDestination() != null) {
					operationalFlightVO.setPou(mailAcceptanceVO.getDestination());	
				}
			}
            operationalFlightVO.setFlightNumber("-1");
            operationalFlightVO.setLegSerialNumber(-1);
            operationalFlightVO.setFlightSequenceNumber(-1);
		}
		
		mailAcceptanceSession.setOperationalFlightVO(operationalFlightVO);
		}
    	
    	//--------------------------------------------------------------------------------------------------------------	 
		

		
		
		
		//MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		//Modified by A-7794 as part of ICRD-197439
		operationalFlightVO.setPol(mailAcceptanceForm.getDeparturePort());
		
		 MailAcceptanceVO newMailAcceptanceVO = new MailAcceptanceVO();
		 FlightValidationVO flightValidationVO = new FlightValidationVO();
		 if(FLAG_YES.equals(mailAcceptanceForm.getDuplicateFlightStatus())){
			 mailAcceptanceForm.setAssignToFlight(CONST_FLIGHT);
		 }
		String assignTo = mailAcceptanceForm.getAssignToFlight();
		log.log(Log.FINE, "assignTo === ", assignTo);
		String fromscreen = mailAcceptanceForm.getFromScreen();
		//Added by A-7794 as part of ICRD-197439
		if(!Objects.equals(logonAttributes.getAirportCode(), mailAcceptanceForm.getDeparturePort())){
			   mailAcceptanceForm.setDisableButtonsForAirport(FLAG_YES);
			   mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
			    }   else {
				mailAcceptanceForm.setDisableButtonsForAirport(FLAG_NO);
				mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_NO);
				}
		
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
			
			log.log(Log.FINE, "*******FLIGHT MODE******");
			
			
				if(FLAG_YES.equals(mailAcceptanceForm.getDuplicateFlightStatus())){
					mailAcceptanceSession.setFlightValidationVO(duplicateFlightSession.getFlightValidationVO());
					mailAcceptanceForm.setDuplicateFlightStatus(FLAG_NO);
					//A-5249 from ICRD-84046
					FlightValidationVO flightValidVO = duplicateFlightSession.getFlightValidationVO();
					if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ||
							FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())){
						if(!FlightValidationVO.FLAG_YES.equals(mailAcceptanceForm.getWarningOveride())){
							flightValidVO.setFlightRoute(null);
							flightValidVO.setAircraftType(null);
							flightValidVO.setEta(null);
							flightValidVO.setEtd(null);
							flightValidVO.setSta(null);
							flightValidVO.setStd(null);
							flightValidVO.setAta(null);
							flightValidVO.setAtd(null);
						ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
						err.setErrorDisplayType(ErrorDisplayType.WARNING);
						invocationContext.addError(err);
						invocationContext.target = TARGET;
						mailAcceptanceForm.setWarningFlag("list_duplicate_tbc_tba"); 
						mailAcceptanceForm.setDuplicateAndTbaTbc(MailConstantsVO.FLAG_YES);     
						//mailAcceptanceForm.setTbaTbcWarningFlag(FLAG_YES);  
						
						return;                                  
						}else{
							flightValidVO.setFlightRoute(null);
							flightValidVO.setAircraftType(null);
							flightValidVO.setEta(null);
							flightValidVO.setEtd(null);
							flightValidVO.setSta(null);
							flightValidVO.setStd(null);
							flightValidVO.setAta(null);
							flightValidVO.setAtd(null);
							mailAcceptanceForm.setWarningFlag("");
							mailAcceptanceForm.setWarningOveride(null);
						}
						mailAcceptanceForm.setDisableButtons(FlightValidationVO.FLAG_YES);
					}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus())){
						Object[] obj = {flightValidVO.getCarrierCode().toUpperCase(),flightValidVO.getFlightNumber()};
						ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
						err.setErrorDisplayType(ErrorDisplayType.ERROR);
						mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
						invocationContext.addError(err);
						invocationContext.target = TARGET;
						return;
					} 
					
				}
				log.log(Log.FINE, "flightValidationVO in MA session...",
						mailAcceptanceSession.getFlightValidationVO());
				flightValidationVO = mailAcceptanceSession.getFlightValidationVO();
				Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
				if(oneTimes!=null){
					Collection<OneTimeVO> resultStatus=
						oneTimes.get("flight.operation.flightlegstatus");
					log.log(Log.FINE, "*******flightlegstatus******");
					flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
					flightValidationVO.setDirection(OUTBOUND);
				}
				mailAcceptanceSession.setFlightValidationVO(flightValidationVO);
				if (CONST_ASSIGNCONTAINER.equals(fromscreen)) {
					mailAcceptanceVO = new MailAcceptanceVO();
					operationalFlightVO = mailAcceptanceSession.getOperationalFlightVO();
					mailAcceptanceForm.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
					mailAcceptanceForm.setFlightNumber(operationalFlightVO.getFlightNumber());
					mailAcceptanceForm.setDepDate(operationalFlightVO.getFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
					mailAcceptanceVO.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
					mailAcceptanceVO.setCarrierId(operationalFlightVO.getCarrierId());
					mailAcceptanceVO.setFlightNumber(operationalFlightVO.getFlightNumber());
					mailAcceptanceVO.setFlightDate(operationalFlightVO.getFlightDate());
					mailAcceptanceVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
					mailAcceptanceVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
					mailAcceptanceVO.setStrFlightDate(operationalFlightVO.getFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));    		    
				}else {	
					operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
					operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
					operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
					operationalFlightVO.setFlightDate(mailAcceptanceVO.getFlightDate());
					operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
					operationalFlightVO.setDirection(OUTBOUND);
					// Added by A-5153 for BUG_ICRD-90623
					operationalFlightVO.setFlightStatus(flightValidationVO.getFlightStatus());
				}
			
			log.log(Log.FINE, "operationalFlightVO in MA session...",
					operationalFlightVO);
			try {
			    	newMailAcceptanceVO = new MailTrackingDefaultsDelegate().findFlightAcceptanceDetails(operationalFlightVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
	    		mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
	    		invocationContext.target = TARGET;
	    		return;
	    	  }
	    		  FlightValidationVO fltVal=mailAcceptanceSession.getFlightValidationVO();
	    		  log.log(Log.FINE, "*******newMailAcceptanceVO******",
						newMailAcceptanceVO);
					log.log(Log.FINE, "*******fltVal******", fltVal);
					if(fltVal!=null){
	    	    		if(newMailAcceptanceVO.getFlightStatus()==null || "".equals(newMailAcceptanceVO.getFlightStatus())){
	    					mailAcceptanceForm.setOperationalStatus("NONE");	    					
	    				}else{
	    				if(oneTimes!=null){
	    					Collection<OneTimeVO> resultStatus=
	    						oneTimes.get("mailtracking.defaults.flightstatus");
	    					log.log(Log.FINE, "*******flightlegstatus******");
	    					fltVal.setOperationalStatus(findOneTimeDescription(resultStatus,newMailAcceptanceVO.getFlightStatus()));
	    				}	    				
	    				if("O".equals(newMailAcceptanceVO.getFlightStatus())){
	    					mailAcceptanceForm.setOperationalStatus("OPEN");	  	    					
	    				}else
	    				if("C".equals(newMailAcceptanceVO.getFlightStatus())){
	    					mailAcceptanceForm.setOperationalStatus("CLOSED");	  	    					
	    					//Added as part of bug ICRD-203009 by A-5526 starts
	    					if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(fltVal.getFlightStatus()) ||
							FlightValidationVO.FLT_LEG_STATUS_TBC.equals(fltVal.getFlightStatus())){     
	    					mailAcceptanceForm.setDisableButtons("TBC");               
	    					} 
	    					//Added as part of bug ICRD-203009 by A-5526 ends
	    				}
	    				}
	    	    		
	    	    		
	    	    		
	    	    		mailAcceptanceSession.setFlightValidationVO(fltVal);
	    	    	}
		}else{
			log.log(Log.FINE, "*******DESTINATION MODE******");
			
			if (CONST_ASSIGNCONTAINER.equals(fromscreen)) {
				mailAcceptanceVO = new MailAcceptanceVO();
				operationalFlightVO = mailAcceptanceSession.getOperationalFlightVO();
				mailAcceptanceForm.setCarrierCode(operationalFlightVO.getCarrierCode());
				mailAcceptanceForm.setDestination(operationalFlightVO.getPou());
				mailAcceptanceVO.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
				mailAcceptanceVO.setCarrierId(operationalFlightVO.getCarrierId());
				mailAcceptanceVO.setDestination(operationalFlightVO.getPou());
			}
			else {
				operationalFlightVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				operationalFlightVO.setCarrierId(mailAcceptanceVO.getCarrierId());
				operationalFlightVO.setPou(mailAcceptanceVO.getDestination());
	            operationalFlightVO.setFlightNumber("-1");
	            operationalFlightVO.setLegSerialNumber(-1);
	            operationalFlightVO.setFlightSequenceNumber(-1);
			}
			
			log.log(Log.FINE, "operationalFlightVO in MA session...",
					operationalFlightVO);
			try {
			    	newMailAcceptanceVO = new MailTrackingDefaultsDelegate().findDestinationAcceptanceDetails(operationalFlightVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
	    		mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
	    		invocationContext.target = TARGET;
	    		return;
	    	  }
		}
		 
    	  if(newMailAcceptanceVO == null){
    		  newMailAcceptanceVO = new MailAcceptanceVO();
    	  }
    	  newMailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
    	  newMailAcceptanceVO.setPol(logonAttributes.getAirportCode());
    	  if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
    		  newMailAcceptanceVO.setFlightCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
    		  newMailAcceptanceVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
    		  newMailAcceptanceVO.setFlightDate(mailAcceptanceVO.getFlightDate());
    		  newMailAcceptanceVO.setCarrierId(mailAcceptanceVO.getCarrierId());
    		  newMailAcceptanceVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			  if (!(newMailAcceptanceVO.getLegSerialNumber() > 0)) {
				newMailAcceptanceVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			  }
    		  newMailAcceptanceVO.setStrFlightDate(mailAcceptanceVO.getStrFlightDate());
    	  }else{
    		  newMailAcceptanceVO.setDestination(mailAcceptanceVO.getDestination());
    		  newMailAcceptanceVO.setFlightCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
    		  newMailAcceptanceVO.setCarrierId(mailAcceptanceVO.getCarrierId());
    		  newMailAcceptanceVO.setFlightNumber("-1");
    		  newMailAcceptanceVO.setFlightSequenceNumber(-1);
    		  newMailAcceptanceVO.setLegSerialNumber(-1);
    		  mailAcceptanceForm.setDisableDestnFlag(FLAG_YES);
    	  }
    	  
    	  
    	Collection<String> codes = new ArrayList<String>();
      	codes.add(PREASSIGNMENT_SYS);
      	Map<String, String> results = null;
      	try {
      		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
      	} catch(BusinessDelegateException businessDelegateException) {
      		handleDelegateException(businessDelegateException);
      	}
      	if(results != null && results.size() > 0) {
      		mailAcceptanceForm.setPreassignFlag(results.get(PREASSIGNMENT_SYS));
      		log.log(Log.FINE, "****mailAcceptanceForm.getPreassignFlag()***",
					mailAcceptanceForm.getPreassignFlag());
      	}
    	  
    	  if("Y".equals(mailAcceptanceForm.getPreassignFlag())){
    		  newMailAcceptanceVO.setPreassignNeeded(true);
    	  }else{
    		  newMailAcceptanceVO.setPreassignNeeded(false);
    	  }
    	
    	Collection<ContainerDetailsVO> containerDetails = newMailAcceptanceVO.getContainerDetails();  
    	if(containerDetails == null ||containerDetails.size() == 0){
    		
    		if("Y".equals(mailAcceptanceForm.getPreassignFlag())){
    			if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(operationalFlightVO.getFlightStatus()) ||
    					FlightValidationVO.FLT_LEG_STATUS_TBC.equals(operationalFlightVO.getFlightStatus()))){
    				if(!FlightValidationVO.FLAG_YES.equals(mailAcceptanceForm.getWarningOveride()) && !FLAG_YES.equals(mailAcceptanceForm.getTbaTbcWarningFlag())){
    				ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
    				err.setErrorDisplayType(ErrorDisplayType.WARNING);
    				invocationContext.addError(err);
    				invocationContext.target = TARGET;
    				mailAcceptanceForm.setWarningFlag(FLIGHT_TBC_TBA);
    				if(mailAcceptanceSession.getFlightValidationVO().getOperationalStatus()!=null){
    					mailAcceptanceSession.getFlightValidationVO().setOperationalStatus(null);
    				}
    				if(mailAcceptanceSession.getFlightValidationVO().getStatusDescription()!=null){
    					mailAcceptanceSession.getFlightValidationVO().setStatusDescription(null);
    				}
    				mailAcceptanceForm.setDisableButtons(FlightValidationVO.FLAG_YES);
    				mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
    				return;
    				}else{
    					mailAcceptanceForm.setWarningFlag("");
    					mailAcceptanceForm.setWarningOveride(null);
    				}
    				mailAcceptanceForm.setDisableButtons(FlightValidationVO.FLAG_YES);
    				//Object[] obj = {mailAcceptanceForm.getFlightCarrierCode().trim().toUpperCase(),operationalFlightVO.getFlightNumber()};
    				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.noContainersAssigned");
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    				mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
    				invocationContext.addError(errorVO);    				
    			}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(operationalFlightVO.getFlightStatus())){
    				Object[] obj = {mailAcceptanceForm.getFlightCarrierCode().trim().toUpperCase(),operationalFlightVO.getFlightNumber()};
    				ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
    				err.setErrorDisplayType(ErrorDisplayType.ERROR);
    				mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
    				invocationContext.addError(err);
    				invocationContext.target = TARGET;
    				return;
    			}
    			else{
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.nocontainerdetails")); 	 
    			mailAcceptanceForm.setDisableSaveFlag(FLAG_YES);
    		}
    		}
    		/*else if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(operationalFlightVO.getFlightStatus()) || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(operationalFlightVO.getFlightStatus())){
    			//invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.nocontainerdetailspreassign"));    
    			Object[] obj = {mailAcceptanceForm.getFlightCarrierCode().trim().toUpperCase(),operationalFlightVO.getFlightNumber()};
				ErrorVO err = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.noContainersAssigned",obj);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
    			mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
				invocationContext.addError(err);
				invocationContext.target = TARGET;
				return;
    			
    		}*/
    		else{
    			if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(operationalFlightVO.getFlightStatus()) ||
    					FlightValidationVO.FLT_LEG_STATUS_TBC.equals(operationalFlightVO.getFlightStatus()))){
    				if(!FlightValidationVO.FLAG_YES.equals(mailAcceptanceForm.getWarningOveride()) && !FLAG_YES.equals(mailAcceptanceForm.getTbaTbcWarningFlag())){
    					if(MailConstantsVO.FLAG_YES.equals(mailAcceptanceForm.getDuplicateAndTbaTbc())){     
    				ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
    				err.setErrorDisplayType(ErrorDisplayType.WARNING);
    				invocationContext.addError(err);
    				invocationContext.target = TARGET;
    				mailAcceptanceForm.setWarningFlag(FLIGHT_TBC_TBA);
    				if(mailAcceptanceSession.getFlightValidationVO().getOperationalStatus()!=null){
    					mailAcceptanceSession.getFlightValidationVO().setOperationalStatus(null);
    				}
    				if(mailAcceptanceSession.getFlightValidationVO().getStatusDescription()!=null){
    					mailAcceptanceSession.getFlightValidationVO().setStatusDescription(null);
    				}
    				mailAcceptanceForm.setDisableButtons(FlightValidationVO.FLAG_YES);
    				mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
    				return;
    				}}else{
    					mailAcceptanceForm.setWarningFlag("");
    					mailAcceptanceForm.setWarningOveride(null);
    				}
    				mailAcceptanceForm.setDisableButtons(FlightValidationVO.FLAG_YES);
    				//Object[] obj = {mailAcceptanceForm.getFlightCarrierCode().trim().toUpperCase(),operationalFlightVO.getFlightNumber()};
    				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.noContainersAssigned");
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    				mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
    				invocationContext.addError(errorVO);
    			}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(operationalFlightVO.getFlightStatus())){
    				Object[] obj = {mailAcceptanceForm.getFlightCarrierCode().trim().toUpperCase(),operationalFlightVO.getFlightNumber()};
    				ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
    				err.setErrorDisplayType(ErrorDisplayType.ERROR);
    				mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
    				invocationContext.addError(err);
    				invocationContext.target = TARGET;
    				return;
    			}
    			else
    				//Added by A-7794 as part of ICRD-197439
    				if(Objects.equals(logonAttributes.getAirportCode(), mailAcceptanceForm.getDeparturePort())){
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.nocontainerdetailspreassign"));    				
    				}
    		}
    		
    	}
    	else{
    		if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(operationalFlightVO.getFlightStatus()) ||
					FlightValidationVO.FLT_LEG_STATUS_TBC.equals(operationalFlightVO.getFlightStatus())) && !FLAG_YES.equals(mailAcceptanceForm.getTbaTbcWarningFlag())){
				if(!FlightValidationVO.FLAG_YES.equals(mailAcceptanceForm.getWarningOveride())){
				ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
				err.setErrorDisplayType(ErrorDisplayType.WARNING);
				invocationContext.addError(err);
				invocationContext.target = TARGET;
				mailAcceptanceForm.setWarningFlag(FLIGHT_TBC_TBA);
				if(mailAcceptanceSession.getFlightValidationVO().getOperationalStatus()!=null){
					mailAcceptanceSession.getFlightValidationVO().setOperationalStatus(null);
				}
				if(mailAcceptanceSession.getFlightValidationVO().getStatusDescription()!=null){
					mailAcceptanceSession.getFlightValidationVO().setStatusDescription(null);
				}
				//Modified as part of bug ICRD-203009 by A-5526 starts
				if(!"TBC".equals(mailAcceptanceForm.getDisableButtons())){
				mailAcceptanceForm.setDisableButtons(FlightValidationVO.FLAG_YES);
				}
				//Modified as part of bug ICRD-203009 by A-5526 ends
				mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
				return;
				}else{
					mailAcceptanceForm.setWarningFlag("");
					mailAcceptanceForm.setWarningOveride(null);
				}
				mailAcceptanceForm.setDisableButtons(FlightValidationVO.FLAG_YES);
			}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(operationalFlightVO.getFlightStatus())){
				Object[] obj = {mailAcceptanceForm.getFlightCarrierCode().trim().toUpperCase(),operationalFlightVO.getFlightNumber()};
				ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
				invocationContext.addError(err);
				invocationContext.target = TARGET;
				return;
			}
    	}
    	log.log(Log.FINE, "\n\n\n\n*******MAIL ACCEPTANCE VOS******",
				mailAcceptanceForm.getUldsPopupCloseFlag());
		if("Y".equals(mailAcceptanceForm.getUldsPopupCloseFlag()) ||
    		"Y".equals(mailAcceptanceSession.getMessageStatus())	){
    		mailAcceptanceForm.setUldsPopupCloseFlag("");
    		mailAcceptanceSession.setMessageStatus("");
	    	Object[] obj = {newMailAcceptanceVO.getFlightCarrierCode(),
	    			newMailAcceptanceVO.getFlightNumber(),newMailAcceptanceVO.getStrFlightDate()};
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.flightclosed",obj);
			invocationContext.addError(errorVO);
    	}	
    	if("REOPENED".equals(mailAcceptanceSession.getMessageStatus())	){        		
        		mailAcceptanceSession.setMessageStatus("");
        		Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
    					mailAcceptanceVO.getFlightNumber(),mailAcceptanceVO.getStrFlightDate()};
    			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.flightreopened",obj);
    			errors.add(errorVO);
    			invocationContext.addError(errorVO);
       	}	
    	
    	log.log(Log.FINE, "*******MAIL ACCEPTANCE VOS******",
				newMailAcceptanceVO);
		mailAcceptanceSession.setMailAcceptanceVO(newMailAcceptanceVO);
		 //Added by A-5160 for ICRD-92869 starts
		HashMap<String, Collection<String>> polPouMap = new HashMap<String, Collection<String>>();
		if(newMailAcceptanceVO!=null){
			if(newMailAcceptanceVO.getContainerDetails()!=null && newMailAcceptanceVO.getContainerDetails().size()>0){
				for(ContainerDetailsVO containerDetailsVO : newMailAcceptanceVO.getContainerDetails()){					
					 Collection<String> pous = new ArrayList<String>();
					 if(containerDetailsVO.getPol()!=null && containerDetailsVO.getPou()!=null){
						if(polPouMap.containsKey(containerDetailsVO.getPol())){
							pous = polPouMap.get(containerDetailsVO.getPol());
							if(!pous.contains(containerDetailsVO.getPou())){
								pous.add(containerDetailsVO.getPou());
								polPouMap.put(containerDetailsVO.getPol(), pous);
							}
						}
						else{
							pous = new ArrayList<String>();
							pous.add(containerDetailsVO.getPou());
							polPouMap.put(containerDetailsVO.getPol(), pous);
						}
					 }
					 newMailAcceptanceVO.setPolPouMap(polPouMap);
					 updateContainerSummary(containerDetailsVO,logonAttributes);//Added to avoid count mismatch
				}
			}
		}
		 //Added by A-5160 for ICRD-92869 ends
    	mailAcceptanceSession.setOperationalFlightVO(operationalFlightVO);
		mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
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
	/***
	 * Added for ICRD-175088
	 * Method to avoid the count mismatch in the summary section of containers in concurrent transactions.
	 * @param containerDetailsVO
	 * @param logonAttributes 
	 */
	private void updateContainerSummary(ContainerDetailsVO  containerDetailsVO, LogonAttributes logonAttributes){
		double manifestedWeight=0;
		double dsnWeight=0;
		int manifestedBags=0; 
		Collection<DSNVO> dSNVOs =  new ArrayList<DSNVO>();
		dSNVOs = containerDetailsVO.getDsnVOs();
		if( dSNVOs != null && dSNVOs.size() >0){
			for (DSNVO  dsnvo : dSNVOs){
				//manifestedWeight=manifestedWeight+dsnvo.getWeight();
			/*
			 * A-5526 Added For ICRD-286407 Begin
			 * Now weight conversion from hg to kg is not happening.
			 * Remove the code block after releasing Measure changes CR ICRD-211400 . 
			  */
			/*if(!"AA".equals(logonAttributes.getCompanyCode())){
				dsnWeight=dsnvo.getWeight().getRoundedSystemValue();
				dsnvo.setWeight(new Measure(UnitConstants.MAIL_WGT, dsnWeight/10));
			}*/
			//A-5526 Added For ICRD-286407 ends
				manifestedWeight=manifestedWeight+dsnvo.getWeight().getSystemValue();  //Added by A-7550
				manifestedBags=manifestedBags+dsnvo.getBags();
				}
			
			
			}
		containerDetailsVO.setTotalBags(manifestedBags);
		//containerDetailsVO.setTotalWeight(manifestedWeight);
		containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, manifestedWeight)); //Added by A-7550
	}
       
}
