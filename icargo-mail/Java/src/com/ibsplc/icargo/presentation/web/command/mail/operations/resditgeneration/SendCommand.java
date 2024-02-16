/*
 * SendCommand.java Created on Oct 08, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.resditgeneration;



import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ResditGenerationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ResditGenerationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2122
 *
 */
public class SendCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.resditgeneration";
   private static final String SEND_SUCCESS = "send_success";
   private static final String SEND_FAILURE = "send_failure";
   
   private static final String OPERATION_FLAG_NOOP = "NOOP";
    
   private static final String TRANSFER_RESDIT = "41,42,43";
	 /**
    * Screen id
    */
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   /**
    * Screen id
    */
   private static final String SCREEN_ID_FLIGHT = "flight.operation.maintainflight";
   /**
    * Module name
    */
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   /**
    * Target string
    */
   private static final String DUPLICATE_SUCCESS = "duplicate_success";
   /**
    * Flight string
    */
   private String flightDate;
  
   private static final String OUTBOUND = "O";
   private static final String INBOUND = "I";
	 /**
   *  Status of flag
   */
   private static final String FLAG_YES = "Y";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SendCommand","execute");    	  
    	ResditGenerationForm resditGenerationForm = 
    		(ResditGenerationForm)invocationContext.screenModel;
    	ResditGenerationSession resditGenerationSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
    	MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
    	
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	
    	  MailTrackingDefaultsDelegate  mailTrackingDefaultsDelegate=new MailTrackingDefaultsDelegate();

		ErrorVO error = null;
		//MailbagVO formOneVO = null;
		
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		Collection<MailbagVO> newMailbagVOs 	= new ArrayList<MailbagVO>();




		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();

		
		//For updating the session with the current values from form
		updateSession(resditGenerationForm, resditGenerationSession, companyCode);

		
		if(resditGenerationSession.getMailbagVOs() == null || resditGenerationSession.getMailbagVOs().size() == 0 ||
				(resditGenerationSession.getMailbagVOs() != null && ((ArrayList<MailbagVO>)resditGenerationSession.getMailbagVOs()).get(0) != null &&
				(((ArrayList<MailbagVO>)resditGenerationSession.getMailbagVOs()).get(0).getMailbagId() == null || 
				((ArrayList<MailbagVO>)resditGenerationSession.getMailbagVOs()).get(0).getMailbagId().trim().length()==0))){
				error = new ErrorVO("mailtracking.defaults.resditgeneration.msg.err.norecordstosave");
				error.setErrorDisplayType(ErrorDisplayType.ERROR); 
				errors.add(error);
		}

		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
			invocationContext.target=SEND_FAILURE;
			return;
		}


		//Validating the Flight details 
		//Modified for Bug 102256 by A-3270
		if((resditGenerationForm.getFlightCarrierCode() !=null && resditGenerationForm.getFlightCarrierCode().trim().length() >0 )&& (resditGenerationForm.getFlightNumber()!=null && resditGenerationForm.getFlightNumber().trim().length() >0)){	
		 
			//Added by A-4213 for bug 105394 starts
			 if(resditGenerationForm.getFlightDate() == null || "".equals(resditGenerationForm.getFlightDate()))
	            {
	                error = new ErrorVO("mailtracking.defaults.resditgeneration.msg.err.flightdatemandatory");
	                error.setErrorDisplayType(ErrorDisplayType.ERROR);
	                errors.add(error);
	                resditGenerationForm.setDuplicateFlightStatus("N");
	            }
	            if(errors != null && errors.size() > 0)
	            {
	                invocationContext.addAllError(errors);
	                invocationContext.target = "send_failure";
	                return;
	            }

	          //Added by A-4213 for bug 105394 ends
				
		FlightFilterVO flightFilterVO = handleFlightFilterVO(
    			resditGenerationForm,logonAttributes);
		 AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = null;
	     String flightCarrierCode = resditGenerationForm.getFlightCarrierCode().trim().toUpperCase();
	     if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
	    	try{
		   			airlineValidationVO = airlineDelegate.validateAlphaCode(
		   					logonAttributes.getCompanyCode(),flightCarrierCode);
		   		}catch (BusinessDelegateException businessDelegateException) {
		   			errors = handleDelegateException(businessDelegateException);
		   		}
	   		if (errors != null && errors.size() > 0) {
							Object[] obj = {flightCarrierCode};							
							invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
	   			invocationContext.target = SEND_FAILURE;
	   			return;
	   		}
	     }
	     log.log(Log.FINE,
				"resditGenerationForm.getResditType() ------------> ",
				resditGenerationForm.getResditType());
		if((MailConstantsVO.RESDIT_RECEIVED.equals(resditGenerationForm.getResditType())) || (MailConstantsVO.RESDIT_UPLIFTED.equals(resditGenerationForm.getResditType())) || 
				 (MailConstantsVO.RESDIT_PENDING.equals(resditGenerationForm.getResditType())) ||(MailConstantsVO.RESDIT_DELIVERED.equals(resditGenerationForm.getResditType()))
				 || (MailConstantsVO.RESDIT_ASSIGNED.equals(resditGenerationForm.getResditType())) ||(TRANSFER_RESDIT.equals(resditGenerationForm.getResditType()))){
	    	 //For validating  mandatory fields for event codes 
	 		errors = validateEventCodes(resditGenerationForm,resditGenerationSession);
	 		log.log(Log.FINE,
					"resditGenerationForm.getFlightDate() ------------> ",
					resditGenerationForm.getFlightDate());
			if(resditGenerationForm.getFlightDate()== null || "".equals(resditGenerationForm.getFlightDate())){
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.resditgeneration.msg.err.freeflightdateexists");
				errorVO.setErrorDisplayType(ERROR);
				errors.add(errorVO);
			}
	 		
	 		if(errors != null && errors.size() > 0){
	 			invocationContext.addAllError(errors);
	 			invocationContext.target=SEND_FAILURE;
	 			return;
	 		}
		}
	     
	     //Added by A-2052 for the bug 102472 ends
			/*******************************************************************
			 * validate Flight
			 ******************************************************************/
	    //Added by A-4213 for bug 105394 starts
	        flightFilterVO.setStringFlightDate(flightDate);
            flightFilterVO.setStation(logonAttributes.getAirportCode());
            flightFilterVO.setDirection("O");
       //Added by A-4213 for bug 105394 ends  
	    	flightFilterVO.setCarrierCode(flightCarrierCode);
			flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
			resditGenerationSession.getMailbagVO().setCarrierId(airlineValidationVO.getAirlineIdentifier());
			flightFilterVO.setFlightNumber(resditGenerationForm.getFlightNumber().toUpperCase());
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
				//mailArrivalForm.setListFlag("FAILURE");
				invocationContext.addAllError(errors);				
				invocationContext.target = SEND_FAILURE;
				return;
			}
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
				log.log(Log.FINE, "flightValidationVOs is NULL");
				// Modified for Bug 102259 by A-3270
				Object[] obj = {resditGenerationForm.getFlightCarrierCode(),
						resditGenerationForm.getFlightNumber()
						//,resditGenerationForm.getFlightDate().substring(0,11)
						};
				//mailArrivalForm.setListFlag("FAILURE");
				invocationContext.addError(new ErrorVO("mailtracking.defaults.resditgeneration.msg.err.noflightdetailsFound",obj));
				invocationContext.target = SEND_FAILURE;
				return;
			} else if ( flightValidationVOs.size() == 1) {
				log.log(Log.FINE, "flightValidationVOs has one VO");
				try {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						BeanHelper.copyProperties(flightValidationVO,
								flightValidVO);
						break;
					}
				} catch (SystemException systemException) {
					systemException.getMessage();
				}
				log
						.log(Log.FINE, "flightValidationVOs ===",
								flightValidationVO);
				for(MailbagVO mailbagVO:resditGenerationSession.getMailbagVOs()){
					mailbagVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					mailbagVO.setCarrierId(flightValidationVO.getFlightCarrierId());
					mailbagVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					mailbagVO.setSegmentSerialNumber(1);
					newMailbagVOs.add(mailbagVO);
					log.log(Log.FINE, "mailbagVOS size", newMailbagVOs.size());
				}
			}else {
				duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)flightValidationVOs);
				duplicateFlightSession.setParentScreenId(SCREEN_ID);
				duplicateFlightSession.setFlightFilterVO(flightFilterVO);
				maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
				resditGenerationForm.setDuplicateFlightStatus(FLAG_YES);
				invocationContext.target =DUPLICATE_SUCCESS;
				return;
			}
		}
		
		if(resditGenerationForm.getUldNumber() != null && resditGenerationForm.getUldNumber().trim().length() >0){
			//Validate UldNumber			
			ULDDelegate uldDelegate = new ULDDelegate();
			try {
				uldDelegate.validateULD(logonAttributes.getCompanyCode(),resditGenerationForm.getUldNumber());
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}			
			if (errors != null && errors.size() > 0) {      
				invocationContext.addError(new ErrorVO("mailtracking.defaults.resditgeneration.msg.err.invaliduldnumber",
		   				new Object[]{resditGenerationForm.getUldNumber()}));
		  		invocationContext.target = SEND_FAILURE;  	
		  		return;
			}
		}
		//	For checking whether free rows or columns exists in the table
		errors = validateTable(resditGenerationForm,resditGenerationSession);
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
			invocationContext.target=SEND_FAILURE;
			return;
		}
		//	For checking whether duplicate records exist
		errors = checkDuplicate(resditGenerationForm,resditGenerationSession);
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
			invocationContext.target=SEND_FAILURE;
			return;
		}
		//	For validating  mandatory fields for event codes 
		errors = validateEventCodes(resditGenerationForm,resditGenerationSession);
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
			invocationContext.target=SEND_FAILURE;
			return;
		}
		log.log(Log.FINE, "After Updation the MailbagVOs", newMailbagVOs);
		if(newMailbagVOs != null && newMailbagVOs.size() >0){
			//Moved inside if loop for Bug 103181 by A-3830
			resditGenerationSession.setMailbagVOs(newMailbagVOs);
			//Added by A-3830 ends
			//	Validate Mail bags
			MailbagVO mailbagVO = ((ArrayList<MailbagVO>)newMailbagVOs).get(0);
			if(mailbagVO != null && mailbagVO.getOoe() != null && mailbagVO.getDoe() != null){
	    	
	    	
			  try {
			    new MailTrackingDefaultsDelegate().validateMailBags(newMailbagVOs);
			  }catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
		  	  }
		  	  if (errors != null && errors.size() > 0) {
		  		invocationContext.addAllError(errors);	  		
		  		invocationContext.target = SEND_FAILURE;
		  		return;
			  	  }		
		  	  }	
		  	  
    	String eventCode =  resditGenerationSession.getMailbagVO().getResditEventString();
    	if((TRANSFER_RESDIT.equals(resditGenerationForm.getResditType())) &&( "QF".equals(resditGenerationForm.getReceivedFromCarrier()))
    			&& resditGenerationForm.getReceivedFromCarrier().equals(resditGenerationForm.getTransferredToCarrier())){
    		eventCode="41";
		}else if((TRANSFER_RESDIT.equals(resditGenerationForm.getResditType())) && ( "QF".equals(resditGenerationForm.getReceivedFromCarrier()))){
			eventCode="42";
		}else if((TRANSFER_RESDIT.equals(resditGenerationForm.getResditType())) && ( "QF".equals(resditGenerationForm.getTransferredToCarrier()))){
			eventCode="43";
		}
    	log.log(Log.FINE, "Event Code", eventCode);
		try{
    		Map<String,Collection<String>> errorMap =mailTrackingDefaultsDelegate.flagResditsForMissedEvents(newMailbagVOs, eventCode);
    		log.log(Log.FINE, "errormap", errorMap);
			log.log(Log.FINE, "errormap.size", errorMap.size());
			if(errorMap == null||errorMap.size() == 0 || errorMap.keySet().iterator().next()==null){
    	        	 error = new ErrorVO(
    	        		"mailtracking.defaults.resditgeneration.msg.info.sendsuccessfully");
    	        	error.setErrorDisplayType(ErrorDisplayType.INFO);
    	        	errors.add(error);
    	        	//Modified for Bug 102262 by A-3270
    	        	resditGenerationSession.setMailbagVO(null);
    	        	resditGenerationSession.setMailbagVOs(null);
    	        	resditGenerationForm.setResditType("");
    	        	resditGenerationForm.setFlightCarrierCode("");
    	        	resditGenerationForm.setFlightNumber("");
    	        	resditGenerationForm.setFlightDate("");
    	        	resditGenerationForm.setEventLocation("");
    	        	resditGenerationForm.setEventDate("");
    	        	resditGenerationForm.setEventTime("");
    	        	resditGenerationForm.setFlightNumber("");
    	        	resditGenerationForm.setReturnReasonCode("");
    	        	resditGenerationForm.setOffloadReasonCode("");
    	        	resditGenerationForm.setReceivedFromCarrier("");
    	        	resditGenerationForm.setTransferredToCarrier("");
    	        	resditGenerationForm.setUldNumber("");
    	        	resditGenerationForm.setDuplicateFlightStatus("");
    	     }else{
    	    	/* Collection<String> keys = errorMap.keySet();
    	    	 for(String key:keys){
    	    		 Collection<String> mailbagIds = errorMap.get(key);
    	    	 }*/
    	    	 Collection<String> mailbagIds = new ArrayList<String>();
    	  		String key = errorMap.keySet().iterator().next();    	
    	      	mailbagIds = errorMap.get(key);
    	     	StringBuilder mailbags =  new StringBuilder();
    	     	for (String mailbagID:mailbagIds){    		
    	     		mailbags.append(mailbagID);
    	     		mailbags.append(",");    		
    	     	}
    	     	String mailbagId =mailbags.toString();
    	     	log.log(Log.FINE, "mailbagId", mailbagId);
				log.log(Log.FINE, "key--->", key);
				Object [] obj = {mailbagId};
    	    	 ErrorVO errorVO = new ErrorVO(key,obj);
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);			
    				errors.add(errorVO);
    				resditGenerationSession.setMailbagVOs(newMailbagVOs);
    	     }
    	        	invocationContext.addAllError(errors);  

    	}catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}    	
    	
    	invocationContext.target = SEND_SUCCESS;
	}else{
		error = new ErrorVO("mailtracking.defaults.resditgeneration.msg.err.norecordstosave");
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
		invocationContext.addAllError(errors);				
		invocationContext.target = SEND_FAILURE;
	}
		resditGenerationForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	log.exiting("SendCommand","execute");
    	
    }



/**
 * @param resditGenerationForm
 * @param resditGenerationSession
 * @param companyCode
 */
public void updateSession(ResditGenerationForm resditGenerationForm,ResditGenerationSession resditGenerationSession,String companyCode){
	log.entering("updatesession","execute");

	String resditType = resditGenerationForm.getResditType();
	String flightCarrierCode = resditGenerationForm.getFlightCarrierCode();
	String flightNumber = resditGenerationForm.getFlightNumber();
	String eventLocation = resditGenerationForm.getEventLocation();
	LocalDate flightDate=null;
	LocalDate eventDateAndTime=null;
	log.log(Log.FINE, "Flight Date in form ", resditGenerationForm.getFlightDate());
	if((!("").equals(resditGenerationForm.getEventLocation())) && resditGenerationForm.getEventLocation() != null){
		log.log(Log.FINE, "Flight Date in form ", resditGenerationForm.getEventLocation());
		flightDate = new LocalDate(eventLocation, Location.ARP,true);
		 eventDateAndTime = new LocalDate(eventLocation, Location.ARP,true);
	}else{
		 flightDate = new LocalDate( getApplicationSession().getLogonVO().getStationCode(),Location.ARP,true);
		 eventDateAndTime = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.ARP,true);
	}
    String returnReasonCode  = resditGenerationForm.getReturnReasonCode();
    String offloadReasonCode = resditGenerationForm.getOffloadReasonCode();
    String fromCarrier = resditGenerationForm.getReceivedFromCarrier();
    String toCarrier= resditGenerationForm.getTransferredToCarrier();
    String uldNumber = resditGenerationForm.getUldNumber();
	String[] originOE  	= resditGenerationForm.getOriginOE();

	String[] destinationOE 	= resditGenerationForm.getDestOE();

	String[] category	= resditGenerationForm.getCategory();

	String[] subClass	= resditGenerationForm.getSubClass();
	
	int[] year = resditGenerationForm.getYear();

	String[] dsn	= resditGenerationForm.getDsn();
	
	String[] rsn	= resditGenerationForm.getRsn();
	
	String [] hni = resditGenerationForm.getHni();
	
	String [] ri = resditGenerationForm.getRi();

	String[] weight = resditGenerationForm.getWeight();
	
	String[] hiddenOperationFlags = resditGenerationForm.getHiddenOperationFlag();
	Collection<MailbagVO> updatedMailbagVOs =new ArrayList<MailbagVO>();
	if(resditGenerationSession.getMailbagVOs() != null &&
			resditGenerationSession.getMailbagVOs().size()>0){
		 updatedMailbagVOs =
			(ArrayList<MailbagVO>)resditGenerationSession.getMailbagVOs();
	}
	
		if(resditGenerationSession.getMailbagVOs() == null ||
				resditGenerationSession.getMailbagVOs().size() == 0){
			updatedMailbagVOs = new ArrayList<MailbagVO>();
			resditGenerationSession.setMailbagVOs(updatedMailbagVOs);
					
		}
		
		
		int length = updatedMailbagVOs.size();
		//Added collection By A-3830 for Bug 103181
		Collection<MailbagVO> mailsToRemove  = new ArrayList<MailbagVO>();
		log.log(Log.FINE, "Updated mailbagVOs size", length);
		MailbagVO mailbagVO = null;		
			for(int i = length - 1;i >= 0;i--){
				if(OPERATION_FLAG_NOOP.equals(hiddenOperationFlags[i])) { 
					mailsToRemove.add(((ArrayList<MailbagVO>)updatedMailbagVOs).get(i));
					//updatedMailbagVOs.remove(i);
				}
				else {
					
					mailbagVO=((ArrayList<MailbagVO>)updatedMailbagVOs).get(i);

					mailbagVO.setCompanyCode(companyCode);

					if(!("").equals(originOE[i])){
						mailbagVO.setOoe(originOE[i]);
					}else {
						mailbagVO.setOoe("");
					}
					log.log(Log.FINE, "originOE[i]", originOE, i);
					if(!("").equals(destinationOE[i])){
						mailbagVO.setDoe(destinationOE[i]);
					}
					else {
						mailbagVO.setDoe("");
					}
					if(!("").equals(category[i])){
						mailbagVO.setMailCategoryCode(category[i]);
					}
					else {
						mailbagVO.setMailCategoryCode("");
					}
					
					if(!("").equals(subClass[i])){
						log.log(Log.FINE, "subclass", subClass, i);
						mailbagVO.setMailSubclass(subClass[i]);
					}
					else {
						mailbagVO.setMailSubclass("");
					}

					if(0 != year[i]){
						log.log(Log.FINE, "year", year, i);
						mailbagVO.setYear(year[i]);
					}
					else {
						mailbagVO.setYear(0);
					}
					
					if(!("").equals(dsn[i])){
						log.log(Log.FINE, "DSN", dsn, i);
						mailbagVO.setDespatchSerialNumber(dsn[i]);
					}
					else {
						mailbagVO.setDespatchSerialNumber("");
					}

					if(!("").equals(rsn[i])){
						log.log(Log.FINE, "RSN", rsn, i);
						mailbagVO.setReceptacleSerialNumber(rsn[i]);
					}
					else {
						mailbagVO.setReceptacleSerialNumber("");
					}
					
					if(!("").equals(hni[i])){
						log.log(Log.FINE, "hni", hni, i);
						mailbagVO.setHighestNumberedReceptacle(hni[i]);
					}
					else {
						mailbagVO.setHighestNumberedReceptacle("");
					}
					
					
					if(!("").equals(ri[i])){
						log.log(Log.FINE, "ri", ri, i);
						mailbagVO.setRegisteredOrInsuredIndicator(ri[i]);
					}
					else {
						mailbagVO.setRegisteredOrInsuredIndicator("");
					}
					
					 //Added by A-2052 for the bug 102472 starts
					if(!((("").equals(weight[i])) ||(("0.0").equals(weight[i])))){
						log.log(Log.FINE, "weight", weight, i);
						//mailbagVO.setWeight(Double.parseDouble(weight[i]));
						mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(weight[i])));//added by A-7371
						//A-3830 for BUG 103181
						//mailbagVO.setStrWeight(weight[i]);
						mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(weight[i])));//added by A-7371
						 //Added by A-2052 for the bug 102472 ends
					}
					else {
						//mailbagVO.setWeight(0.0);
						mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0));//added by A-7371
					}
					int wtLength = weight[i].length();
					String updatedWeight = null;
					if(wtLength < 4){
					    StringBuilder newWeight = new StringBuilder();						
						if(wtLength == 1) {
							newWeight.append("000").append(weight[i]);
						} else if(wtLength == 2) {							
							newWeight.append("00").append(weight[i]);
						} else if(wtLength == 3) {							
							newWeight.append("0").append(weight[i]);
						}
						updatedWeight=newWeight.toString();
						log.log(Log.FINE,"Updated weight in first loop");
						
					}else{
						updatedWeight=weight[i];
					}
					StringBuilder mailbagId = new StringBuilder();
					mailbagId.append(mailbagVO.getOoe()).append(mailbagVO.getDoe()).
					append(mailbagVO.getMailCategoryCode()).append(mailbagVO.getMailSubclass()).
					append(mailbagVO.getYear()).append(mailbagVO.getDespatchSerialNumber()).append(mailbagVO.getReceptacleSerialNumber()).
					append(mailbagVO.getHighestNumberedReceptacle()).append(mailbagVO.getRegisteredOrInsuredIndicator()).append(updatedWeight);				
					mailbagVO.setMailbagId(mailbagId.toString());
					log.log(Log.FINE, "mailbagVO.getmailbagID()", mailbagVO.getMailbagId());
					mailbagVO.setOperationalFlag(hiddenOperationFlags[i]);
					mailbagVO.setResditEventString(resditType);					
					mailbagVO.setFlightNumber(flightNumber);
					mailbagVO.setCarrierCode(flightCarrierCode);
					if((!("").equals(resditGenerationForm.getFlightDate())) && resditGenerationForm.getFlightDate() != null){
						flightDate.setDate(resditGenerationForm.getFlightDate());
					}					
					mailbagVO.setFlightDate(flightDate);
					log.log(Log.FINE, "Flight date in mailbagVO", mailbagVO.getFlightDate());
					if(!("").equals(resditGenerationForm.getEventDate()) && resditGenerationForm.getEventDate() != null ){
					  eventDateAndTime.setDate(resditGenerationForm.getEventDate());
					}	
					if( resditGenerationForm.getEventTime() != null  && !("").equals(resditGenerationForm.getEventTime()) && resditGenerationForm.getEventTime().trim().length() >0 ){
						eventDateAndTime.setTime(resditGenerationForm.getEventTime() + ":00");
					}else{						
						eventDateAndTime.setTime("00:00:00");
					}
					mailbagVO.setResditEventDate(eventDateAndTime);
					log.log(Log.FINE, "Event Date and time  in mailbagVO",
							mailbagVO.getResditEventDate());
					mailbagVO.setResditEventPort(eventLocation);
					mailbagVO.setReturnedReason(returnReasonCode);
					mailbagVO.setOffloadedReason(offloadReasonCode);
					mailbagVO.setTransferFromCarrier(fromCarrier);
					mailbagVO.setMailOutCarrier(toCarrier);
					mailbagVO.setUldNumber(uldNumber);
					
			}
		 }
			 //Added by A-3830 for Bug 103181
				updatedMailbagVOs.removeAll(mailsToRemove);
		
		
			for(int i = length ; i < hiddenOperationFlags.length - 1; i++){
				log.log(Log.FINE,"into second  loop in update session");
				if(!OPERATION_FLAG_NOOP.equals(hiddenOperationFlags[i])) {
					mailbagVO= new MailbagVO();
					log.log(Log.FINE,"into second  loop in update session");
					mailbagVO.setCompanyCode(companyCode);

					mailbagVO.setCompanyCode(companyCode);

					if(!("").equals(originOE[i])){
						mailbagVO.setOoe(originOE[i]);
					}else {
						mailbagVO.setOoe("");
					}
					log.log(Log.FINE, "originOE[i]", originOE, i);
					if(!("").equals(destinationOE[i])){
						mailbagVO.setDoe(destinationOE[i]);
					}
					else {
						mailbagVO.setDoe("");
					}
					if(!("").equals(category[i])){
						mailbagVO.setMailCategoryCode(category[i]);
					}
					else {
						mailbagVO.setMailCategoryCode("");
					}
					
					if(!("").equals(subClass[i])){
						log.log(Log.FINE, "subclass", subClass, i);
						mailbagVO.setMailSubclass(subClass[i]);
					}
					else {
						mailbagVO.setMailSubclass("");
					}

					if(0 != year[i]){
						log.log(Log.FINE, "year", year, i);
						mailbagVO.setYear(year[i]);
					}
					else {
						mailbagVO.setYear(0);
					}
					
					if(!("").equals(dsn[i])){
						log.log(Log.FINE, "DSN", dsn, i);
						mailbagVO.setDespatchSerialNumber(dsn[i]);
					}
					else {
						mailbagVO.setDespatchSerialNumber("");
					}

					if(!("").equals(rsn[i])){
						log.log(Log.FINE, "RSN", rsn, i);
						mailbagVO.setReceptacleSerialNumber(rsn[i]);
					}
					else {
						mailbagVO.setReceptacleSerialNumber("");
					}
					
					if(!("").equals(hni[i])){
						log.log(Log.FINE, "hni", hni, i);
						mailbagVO.setHighestNumberedReceptacle(hni[i]);
					}
					else {
						mailbagVO.setHighestNumberedReceptacle("");
					}
					
					
					if(!("").equals(ri[i])){
						log.log(Log.FINE, "ri", ri, i);
						mailbagVO.setRegisteredOrInsuredIndicator(ri[i]);
					}
					else {
						mailbagVO.setRegisteredOrInsuredIndicator("");
					}
					
					
					if(!("").equals(weight[i])){
						log.log(Log.FINE, "weight", weight, i);
						//mailbagVO.setWeight(Integer.parseInt(weight[i]));
						mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(weight[i])));//added by A-7371
						//A-3830 for BUG 103181
						//mailbagVO.setStrWeight(weight[i]);
						mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(weight[i])));//added by A-7371
					}
					else {
						//mailbagVO.setWeight(0.0);
						mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0));//added by A-7371
					}
					
					int wtLength = weight[i].length();
					String updatedWeight = null;
					if(wtLength < 4){
					    StringBuilder newWeight = new StringBuilder();						
						if(wtLength == 1) {
							newWeight.append("000").append(weight[i]);
						} else if(wtLength == 2) {							
							newWeight.append("00").append(weight[i]);
						} else if(wtLength == 3) {							
							newWeight.append("0").append(weight[i]);
						}
						updatedWeight=newWeight.toString();
						log.log(Log.FINE,"Updated weight in first loop");
					}else{
						updatedWeight=weight[i];
					}
					StringBuilder mailbagId = new StringBuilder();
					mailbagId.append(mailbagVO.getOoe()).append(mailbagVO.getDoe()).
					append(mailbagVO.getMailCategoryCode()).append(mailbagVO.getMailSubclass()).
					append(mailbagVO.getYear()).append(mailbagVO.getDespatchSerialNumber()).append(mailbagVO.getReceptacleSerialNumber()).
					append(mailbagVO.getHighestNumberedReceptacle()).append(mailbagVO.getRegisteredOrInsuredIndicator()).append(updatedWeight);
					mailbagVO.setMailbagId(mailbagId.toString());
					log.log(Log.FINE, "mailbagVO.getmailbagId", mailbagVO.getMailbagId());
				mailbagVO.setOperationalFlag(
						OPERATION_FLAG_INSERT);
				mailbagVO.setResditEventString(resditType);					
				mailbagVO.setFlightNumber(flightNumber);
					mailbagVO.setCarrierCode(flightCarrierCode);
					if((!("").equals(resditGenerationForm.getFlightDate())) && resditGenerationForm.getFlightDate() != null){
						flightDate.setDate(resditGenerationForm.getFlightDate());
					}					
					mailbagVO.setFlightDate(flightDate);
					log.log(Log.FINE, "Flight date in mailbagVO", mailbagVO.getFlightDate());
					if(!("").equals(resditGenerationForm.getEventDate()) && resditGenerationForm.getEventDate() != null ){
						  eventDateAndTime.setDate(resditGenerationForm.getEventDate());
					}	
					if( resditGenerationForm.getEventTime() != null  && !("").equals(resditGenerationForm.getEventTime()) && resditGenerationForm.getEventTime().trim().length() >0 ){
						eventDateAndTime.setTime(resditGenerationForm.getEventTime() + ":00");
					}else{						
						eventDateAndTime.setTime("00:00:00");
					}
					mailbagVO.setResditEventDate(eventDateAndTime);	
					log.log(Log.FINE, "Event Date and time  in mailbagVO",
							mailbagVO.getResditEventDate());
				mailbagVO.setResditEventPort(eventLocation);
				mailbagVO.setReturnedReason(returnReasonCode);
				mailbagVO.setOffloadedReason(offloadReasonCode);
				mailbagVO.setTransferFromCarrier(fromCarrier);
				mailbagVO.setMailOutCarrier(toCarrier);
				mailbagVO.setUldNumber(uldNumber);
				updatedMailbagVOs.add( mailbagVO );				
				
				log.log(Log.FINE, "updatedMailbagVOs value", updatedMailbagVOs);
				}
	
			
			}
		

	
	resditGenerationSession.setMailbagVOs(updatedMailbagVOs);
	if(updatedMailbagVOs != null && updatedMailbagVOs.size()>0){
		resditGenerationSession.setMailbagVO(((ArrayList<MailbagVO>)updatedMailbagVOs).get(0));
	}
	
	log.log(Log.FINE, "MailbagVOs from Session UpdateSession method",
			resditGenerationSession.getMailbagVOs());
	log.exiting("updatesession","execute");
}
//For checking whether free rows or columns exists in the table
/**
 * @return Collection<ErrorVO>
 * @param resditGenerationForm
 * @param resditGenerationSession
 */
public Collection<ErrorVO> validateTable(ResditGenerationForm resditGenerationForm,ResditGenerationSession resditGenerationSession){
	log.entering("validateTable","execute");
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

	String[] originOE  	= resditGenerationForm.getOriginOE();

	String[] destOE 	= resditGenerationForm.getDestOE();

	String[] category   = resditGenerationForm.getCategory();
	
	String[] subclass   = resditGenerationForm.getSubClass();

	//int[] year	= resditGenerationForm.getYear();
	
	String[] dsn= resditGenerationForm.getDsn();
	
	String[] rsn = resditGenerationForm.getRsn();
	
	
	String[] hni= resditGenerationForm.getHni();
	
	String[] ri = resditGenerationForm.getRi();
	
	String[] weight= resditGenerationForm.getWeight();
	
	
	String[] operationFlag = resditGenerationForm.getHiddenOperationFlag();

	if(originOE!=null ) {
		for(int i=0;i<originOE.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(("").equals(originOE[i])){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freeoriginoeexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}

		for(int i=0;i<destOE.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(("").equals(destOE[i])){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freedestoeexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}

		for(int i=0;i<category.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(("").equals(category[i])){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freecategoryexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}
		
		
		for(int i=0;i<subclass.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(("").equals(subclass[i])){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freesubclassexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}
		
		//Year check not required since it will default 0 as the value
		/*for(int i=0;i<year.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(0 == year[i]){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freeyearexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}*/
		
		
		for(int i=0;i<dsn.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(("").equals(dsn[i])){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freedsnexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}
		
		for(int i=0;i<rsn.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(("").equals(rsn[i])){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freersnexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}
		
		
		for(int i=0;i<hni.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(("").equals(hni[i])){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freehniexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}
		
		
		for(int i=0;i<ri.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(("").equals(ri[i])){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freeriexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}
		
		
		for(int i=0;i<weight.length - 1;i++) {
			if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
					&& !(OPERATION_FLAG_NOOP).equals(operationFlag[i])){
				if(("").equals(weight[i])){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.resditgeneration.msg.err.freeweightexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					break;
				}
			}

		}
		
		
	}
	log.exiting("validateTable","execute");
	return errors;

}

//For checking whether duplicate records exist
/**
 * @return Collection<ErrorVO>
 * @param resditGenerationForm
 * @param resditGenerationSession
 */
public Collection<ErrorVO> checkDuplicate(ResditGenerationForm resditGenerationForm,ResditGenerationSession resditGenerationSession){
	log.entering("checkDuplicate","execute");

	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	if(resditGenerationSession.getMailbagVOs()!= null){
		//String[] invoiceDate  	= captureFormOneForm.getInvoiceDates();

		String[] originOE  	= resditGenerationForm.getOriginOE();

		String[] destOE 	= resditGenerationForm.getDestOE();

		String[] category   = resditGenerationForm.getCategory();
		
		String[] subclass   = resditGenerationForm.getSubClass();

		int[] year	= resditGenerationForm.getYear();
		
		String[] dsn= resditGenerationForm.getDsn();
		
		String[] rsn = resditGenerationForm.getRsn();
		
		
		String[] hni= resditGenerationForm.getHni();
		
		String[] ri = resditGenerationForm.getRi();
		
		String[] weight= resditGenerationForm.getWeight();
		
		String[] operationFlag = resditGenerationForm.getHiddenOperationFlag();
		if(originOE!=null) {
			
			int mailbagIdFlag = 0;

			

			for(int i=0;i<originOE.length-1;i++) {
				for(int j=0;j<originOE.length-1;j++) {
					log.log(Log.INFO, "Going to compare ", originOE, i);
					log.log(Log.INFO, " and ", originOE, j);
					if(i!=j) {
						if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i]) && !(OPERATION_FLAG_NOOP).equals(operationFlag[i]) &&
								!(OPERATION_FLAG_DELETE).equals(operationFlag[j]) && !(OPERATION_FLAG_NOOP).equals(operationFlag[j])) {
							if(originOE[i].equals(originOE[j]) &&
									destOE[i].equals(destOE[j]) &&
									category[i].equals(category[j]) && 
									subclass[i].equals(subclass[j]) &&
									year[i]==(year[j]) &&
									dsn[i].equals(dsn[j]) &&
									rsn[i].equals(rsn[j]) &&
									hni[i].equals(hni[j]) &&
									ri[i].equals(ri[j]) &&
									weight[i].equals(weight[j])									
									) {
										mailbagIdFlag = 1;
									}
						}
					}
				}
			}


		
			if(mailbagIdFlag ==1) {
				log.log(Log.INFO, "dup mailbagID >>>>>>", mailbagIdFlag);
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.resditgeneration.msg.err.dupmailbagidexists");
				errorVO.setErrorDisplayType(ERROR);
				errors.add(errorVO);
			}
		}
	}
	log.exiting("checkDuplicate","execute");
	return errors;
}
//For checking whether the mandatory fields for each eventCodes
/**
 * @return Collection<ErrorVO>
 * @param resditGenerationForm
 * @param resditGenerationSession
 */
public Collection<ErrorVO> validateEventCodes(ResditGenerationForm resditGenerationForm,ResditGenerationSession resditGenerationSession){
	log.entering("validateEventCodes","execute");
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
//	Validating all event codes
	if("41,42,43" .equals(resditGenerationForm.getResditType())){		
		if(("").equals(resditGenerationForm.getReceivedFromCarrier()) ||("").equals(resditGenerationForm.getTransferredToCarrier())){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.resditgeneration.msg.err.freecarriercodeexists");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);		
		}		
	}
	if ( (MailConstantsVO.RESDIT_RECEIVED.equals(resditGenerationForm.getResditType())) || (MailConstantsVO.RESDIT_UPLIFTED.equals(resditGenerationForm.getResditType())) || 
		(MailConstantsVO.RESDIT_DELIVERED.equals(resditGenerationForm.getResditType())) || (MailConstantsVO.RESDIT_PENDING.equals(resditGenerationForm.getResditType())) ||
		(MailConstantsVO.RESDIT_RETURNED.equals(resditGenerationForm.getResditType())) || (MailConstantsVO.RESDIT_ASSIGNED.equals(resditGenerationForm.getResditType())) ||
		(TRANSFER_RESDIT.equals(resditGenerationForm.getResditType()) && resditGenerationForm.getReceivedFromCarrier() != null &&
				resditGenerationForm.getReceivedFromCarrier().equals(resditGenerationForm.getTransferredToCarrier())) ){
		if(("").equals(resditGenerationForm.getEventLocation())){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.resditgeneration.msg.err.freeeventlocationexists");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);		
		}
		if(resditGenerationSession.getMailbagVO().getResditEventDate()== null){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.resditgeneration.msg.err.freeeventdateexists");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);
		}
		if(("").equals(resditGenerationForm.getFlightCarrierCode())){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.resditgeneration.msg.err.freeflightcarriercodeexists");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);		
		}
		if(("").equals(resditGenerationForm.getFlightNumber())){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.resditgeneration.msg.err.freeflightnumberexists");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);		
		}
		if(resditGenerationSession.getMailbagVO().getFlightDate()== null){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.resditgeneration.msg.err.freeflightdateexists");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);
		}
	}
	if (MailConstantsVO.RESDIT_PENDING.equals(resditGenerationForm.getResditType())) {
		if(("").equals(resditGenerationForm.getOffloadReasonCode())){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.resditgeneration.msg.err.freeoffloadreasoncodeexists");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);		
		}
	}
	if (MailConstantsVO.RESDIT_RETURNED.equals(resditGenerationForm.getResditType())) {
		if(("").equals(resditGenerationForm.getReturnReasonCode())){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.resditgeneration.msg.err.freereturnreasoncodeexists");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);		
		}
	}
	//Added for Bug 102258 by A-3270
	if(("").equals(resditGenerationForm.getEventDate()) || ("").equals(resditGenerationForm.getEventTime())){
		ErrorVO errorVO = new ErrorVO(
		"mailtracking.defaults.resditgeneration.msg.err.freeeventdateandtime");   
		errorVO.setErrorDisplayType(ERROR);
		errors.add(errorVO);		
	}
	log.exiting("validateEventCodes","execute");
	return errors;
}
/**
 * Method to create the filter vo for flight validation
 * @param resditGenerationForm
 * @param logonAttributes
 * @return FlightFilterVO
 */
private FlightFilterVO handleFlightFilterVO(
		ResditGenerationForm resditGenerationForm,
		LogonAttributes logonAttributes){
	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	flightFilterVO.setStation(resditGenerationForm.getEventLocation());
	if((MailConstantsVO.RESDIT_RECEIVED.equals(resditGenerationForm.getResditType())) || (MailConstantsVO.RESDIT_UPLIFTED.equals(resditGenerationForm.getResditType())) || 
			 (MailConstantsVO.RESDIT_PENDING.equals(resditGenerationForm.getResditType())) ||(MailConstantsVO.RESDIT_RETURNED.equals(resditGenerationForm.getResditType()))
			 || (MailConstantsVO.RESDIT_ASSIGNED.equals(resditGenerationForm.getResditType())) ||(TRANSFER_RESDIT.equals(resditGenerationForm.getResditType()))){
		flightFilterVO.setDirection(OUTBOUND);
	}else if(MailConstantsVO.RESDIT_DELIVERED.equals(resditGenerationForm.getResditType())) {
		flightFilterVO.setDirection(INBOUND);
	}
	flightFilterVO.setActiveAlone(false);
	flightFilterVO.setStringFlightDate(resditGenerationForm.getFlightDate());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		log.log(Log.FINE, "date inside handle", date);
		log.log(Log.FINE, "Airport Code inside handle", logonAttributes.getAirportCode());
		log.log(Log.FINE, "date inside form", resditGenerationForm.getFlightDate());
		if(resditGenerationForm.getFlightDate() != null && resditGenerationForm.getFlightDate().trim().length()>0){
			date.setDate(resditGenerationForm.getFlightDate());
			log.log(Log.FINE, "date after ", date);
		}
		flightFilterVO.setFlightDate(date);
	return flightFilterVO;
}
       
}
