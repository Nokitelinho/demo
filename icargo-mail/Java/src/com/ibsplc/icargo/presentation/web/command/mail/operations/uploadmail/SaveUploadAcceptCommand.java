/*
 * SaveUploadAcceptCommand.java Created on Oct 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;


import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class SaveUploadAcceptCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";
	private static final String TARGET = "success";


	/**
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("SaveUploadAcceptCommand","execute");
		UploadMailForm uploadMailForm
			= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

		log.log(Log.INFO, " Enablemode..", uploadMailForm.getEnablemode());
		if(uploadMailForm.getEnablemode()){
			ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
			ScannedDetailsVO returnscanneddetailsVO = CompareScannedDateCommand.checkscannedDetailsVO(scannedDetailsVO,"ACP");
			uploadMailSession.setScannedDetailsVO(returnscanneddetailsVO);
		}
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errorsAccept = new ArrayList<ErrorVO>();

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		ScannedDetailsVO  initialScannedDetailVO = null;
		
		Collection<ScannedMailDetailsVO> scannedOutboundAssignVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedOutboundFinalVOs = new ArrayList<ScannedMailDetailsVO>();
		
		
		/**
		 * To check whether same container exists in both flight and carrier
		 */
		if(scannedDetailsVO != null && 
				scannedDetailsVO.getOutboundMails() != null && 
				scannedDetailsVO.getOutboundMails().size() > 0 ){
			List<ScannedMailDetailsVO> scannedmaildetailsvos = new ArrayList<ScannedMailDetailsVO>(scannedDetailsVO.getOutboundMails());
			ScannedMailDetailsVO scannedmaildetailsvoOne = null;
			ScannedMailDetailsVO scannedmaildetailsvoTwo = null;
			if( scannedmaildetailsvos != null && scannedmaildetailsvos.size() > 1) {
				int innerscannedmaildetailsvosize = scannedmaildetailsvos.size();
				 for(int j = 0; j < innerscannedmaildetailsvosize; j++) {
					 scannedmaildetailsvoOne = scannedmaildetailsvos.get(j);
		            	for(int k = (j + 1); k < innerscannedmaildetailsvosize; k++) {
		            		scannedmaildetailsvoTwo = scannedmaildetailsvos.get(k);
		            		if(scannedmaildetailsvoOne.getContainerNumber().equalsIgnoreCase(scannedmaildetailsvoTwo.getContainerNumber())){
		            			Collection<MailbagVO> mailbagvos1 = scannedmaildetailsvoOne.getMailDetails();
								for(MailbagVO mailbagvo :mailbagvos1){
									mailbagvo.setErrorType(MailConstantsVO.EXCEPT_FATAL);
									mailbagvo.setErrorDescription("Same Container exists both in carrier and flight");
								}
								Collection<MailbagVO> mailbagvos2 = scannedmaildetailsvoTwo.getMailDetails();
								for(MailbagVO mailbagvo :mailbagvos2){
									mailbagvo.setErrorType(MailConstantsVO.EXCEPT_FATAL);
									mailbagvo.setErrorDescription("Same Container exists both in carrier and flight");
								}
							}
		            	}
				 }
			}
			
		}
		
		if(scannedDetailsVO != null  && 
				scannedDetailsVO.getOutboundMails() != null && 
				scannedDetailsVO.getOutboundMails().size() > 0 ){
			List<ScannedMailDetailsVO> scannedmaildetailsvos = new ArrayList<ScannedMailDetailsVO>(scannedDetailsVO.getOutboundMails());
			ScannedMailDetailsVO scannedmaildetailsvoOne = null;
			int innerscannedmaildetailsvosize = scannedmaildetailsvos.size();
			if( innerscannedmaildetailsvosize > 1) {
			 for(int j = 0; j < innerscannedmaildetailsvosize; j++) {
				 scannedmaildetailsvoOne = scannedmaildetailsvos.get(j);
				 Collection<MailbagVO> mailbagvos1 = scannedmaildetailsvoOne.getMailDetails();
					for(MailbagVO mailbagvo :mailbagvos1){
						if(MailConstantsVO.EXCEPT_FATAL.equalsIgnoreCase(mailbagvo.getErrorType())){
							scannedOutboundFinalVOs.add(scannedmaildetailsvoOne);
							scannedDetailsVO.getOutboundMails().remove(scannedmaildetailsvoOne);
							break;
						}
					}
			 	}
			 }
		}
		
		
		
		if(scannedDetailsVO != null  && 
				scannedDetailsVO.getOutboundMails() != null && 
				scannedDetailsVO.getOutboundMails().size() > 0 ){
			try {
				initialScannedDetailVO = new MailTrackingDefaultsDelegate().findDetailsForMailUpload(scannedDetailsVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}

		Collection<ScannedMailDetailsVO> scannedOutboundVOs = null;
		if(initialScannedDetailVO != null) {
			scannedOutboundVOs = initialScannedDetailVO.getOutboundMails();
		}
		
		/**
		 * Validation 1
		 * Whether a container is assigned or not
		 */
		
		
		
		if(scannedOutboundVOs != null && scannedOutboundVOs.size() > 0){
			for(ScannedMailDetailsVO scannedVO:scannedOutboundVOs){
				if(scannedVO.getCarrierCode() == null ||
						("").equals(scannedVO.getCarrierCode())){
					Object[] obj = {scannedVO.getContainerNumber()};
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.uploadmail.msg.err.notassigned",obj);
					errorsAccept.add(errorVO);
					scannedOutboundFinalVOs.add(scannedVO);
				}else{
					scannedOutboundAssignVOs.add(scannedVO);
				}
			}
		}
		
		
		/**
		 * Validation 1.1
		 * Whether a valid flight
		 */
		Collection<ScannedMailDetailsVO> scannedOutboundFirstVOs = new ArrayList<ScannedMailDetailsVO>();
		if(scannedOutboundAssignVOs != null && scannedOutboundAssignVOs.size() > 0){
			for(ScannedMailDetailsVO scannedVO:scannedOutboundAssignVOs){

				if(scannedVO.getFlightNumber() != null 
					&& !"".equals(scannedVO.getFlightNumber())
					&& !"-1".equals(scannedVO.getFlightNumber())){

					boolean isCarrier = true;
					AirlineValidationVO airlineValidationVO = null;
					Collection<ErrorVO> errorsCarr = new ArrayList<ErrorVO>();
				    String flightCarrierCode = scannedVO.getCarrierCode();        	
			    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) { 
			    		flightCarrierCode = scannedVO.getCarrierCode().trim().toUpperCase(); 
			    		try {        			
			    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
			    					logonAttributes.getCompanyCode(),flightCarrierCode);
			    		}catch (BusinessDelegateException businessDelegateException) {
			    			errorsCarr = handleDelegateException(businessDelegateException);
			    		}
			    		if (errorsCarr != null && errorsCarr.size() > 0) {            			
			    			Object[] obj = {flightCarrierCode};
			    			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.invalidcarrier",obj);
			    			errorsAccept.add(errorVO);
							isCarrier = false;
							scannedOutboundFinalVOs.add(scannedVO);
			    		}
			    	}

					if(isCarrier){
					FlightFilterVO flightFilterVO = new FlightFilterVO();
					flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
					flightFilterVO.setStation(logonAttributes.getAirportCode());
					flightFilterVO.setDirection("O");
			 		flightFilterVO.setFlightDate(scannedVO.getFlightDate());
			 		flightFilterVO.setCarrierCode(scannedVO.getCarrierCode());
					flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
					flightFilterVO.setFlightNumber(scannedVO.getFlightNumber());

					Collection<FlightValidationVO> flightValidationVOs = null;
					try {
						flightValidationVOs =
							new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);

					}catch (BusinessDelegateException businessDelegateException) {
						handleDelegateException(businessDelegateException);
					}

					FlightValidationVO flightValidationVO = new FlightValidationVO();
					if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
						Object[] obj = {scannedVO.getCarrierCode(),scannedVO.getFlightNumber(),
								scannedVO.getFlightDate().toString().substring(0,11)};
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noflightdetails",obj);
						errorsAccept.add(errorVO);
						scannedOutboundFinalVOs.add(scannedVO);
					} else {
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

						scannedVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
						scannedVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
						scannedVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());

						if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
							flightValidationVO = ((ArrayList<FlightValidationVO>)flightValidationVOs).get(0);
							OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
							operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
							operationalFlightVO.setPol(logonAttributes.getAirportCode());
							operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
							operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
							operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
							operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
							operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
							operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
							operationalFlightVO.setDirection("O");

							boolean flightStatus=false;
					    	try {
								flightStatus=new MailTrackingDefaultsDelegate().isFlightClosedForMailOperations(operationalFlightVO);
							} catch (BusinessDelegateException e) {
								handleDelegateException(e);
							}
							if(flightStatus)  {
								Object[] obj = {new StringBuilder(operationalFlightVO.getCarrierCode())
								 .append("").append(operationalFlightVO.getFlightNumber()).toString(),
								 operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat()};
								ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.flightclosed",obj);
				    			errorsAccept.add(errorVO);
								isCarrier = false;
								scannedOutboundFinalVOs.add(scannedVO);
							}else{
								log.log(Log.FINE, "flightStatus------------> ",
										flightStatus);
								scannedOutboundFirstVOs.add(scannedVO);
							}


						}

					}
				  }
				}else{
					AirlineValidationVO airlineValidationVO = null;
				    String flightCarrierCode = scannedVO.getCarrierCode(); 
				    Collection<ErrorVO> errorsCarr = new ArrayList<ErrorVO>();
			    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) { 
			    		flightCarrierCode = scannedVO.getCarrierCode().trim().toUpperCase(); 
			    		try {        			
			    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
			    					logonAttributes.getCompanyCode(),flightCarrierCode);
			    		}catch (BusinessDelegateException businessDelegateException) {
			    			errorsCarr = handleDelegateException(businessDelegateException);
			    		}
			    	}

			    	if (errorsCarr != null && errorsCarr.size() > 0) {            			
		    			Object[] obj = {flightCarrierCode};
		    			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.invalidcarrier",obj);
		    			errorsAccept.add(errorVO);
						scannedOutboundFinalVOs.add(scannedVO);
		    		}else{

		    			AirportValidationVO airportValidationVO = null;
				    	String destination = scannedVO.getDestination();        	
				     	if (destination != null && !"".equals(destination)) { 
				     		destination = scannedVO.getDestination().trim().toUpperCase(); 
				     		try {        			
				     			airportValidationVO = new AreaDelegate().validateAirportCode(
				     					logonAttributes.getCompanyCode(),destination);
				     		}catch (BusinessDelegateException businessDelegateException) {
				     			errorsCarr = handleDelegateException(businessDelegateException);
				     		}
				     	}
				     	if (errorsCarr != null && errorsCarr.size() > 0) {            			
				    		Object[] obj = {destination};
				    		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.invalidairport",obj);
				    		errorsAccept.add(errorVO);
							scannedOutboundFinalVOs.add(scannedVO);
				     	}else{
					    	scannedVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
						    scannedOutboundFirstVOs.add(scannedVO);
				     	}
		    		}
				}
				if(("N").equals(scannedDetailsVO.getPreassignFlag())){
					ContainerVO containerVO = new ContainerVO();
					containerVO.setCompanyCode(scannedVO.getCompanyCode());
					containerVO.setContainerNumber(scannedVO.getContainerNumber());
					containerVO.setAssignedPort(scannedVO.getPol());
					containerVO.setCarrierId(scannedVO.getCarrierId());
					containerVO.setFlightNumber(scannedVO.getFlightNumber());
					containerVO.setFlightSequenceNumber(scannedVO.getFlightSequenceNumber());
					containerVO.setLegSerialNumber(scannedVO.getLegSerialNumber());

			    	try {	    
			    		if("B".equals(scannedVO.getContainerType())){
			    			containerVO = new MailTrackingDefaultsDelegate().validateContainer(logonAttributes.getAirportCode(), containerVO);
			    		}else{

			    			boolean isULDType = "U".equals(scannedVO.getContainerType());
							if(!isULDType && containerVO.getContainerNumber().length()>= 3) {
								String containerPart = containerVO.getContainerNumber().substring(0,3);
								log.log(Log.FINE,
										"$$$$$$containerPart------->>",
										containerPart);
								Collection<String> containerParts = new ArrayList<String>();
								containerParts.add(containerPart);
								try {
									new ULDDelegate().validateULDTypeCodes(
											logonAttributes.getCompanyCode(),containerParts);
									isULDType = true;
								}catch (BusinessDelegateException businessDelegateException) {
									isULDType = false;
								}
							}

							if(isULDType){	    	
								try {
									ULDValidationVO uLDValidationVO= new ULDDelegate().validateULD(logonAttributes.getCompanyCode(),containerVO.getContainerNumber());
									if(uLDValidationVO != null){
										containerVO = new MailTrackingDefaultsDelegate().validateContainer(logonAttributes.getAirportCode(), containerVO);
									}else{
										scannedVO.setContainerType("B");
										containerVO = new MailTrackingDefaultsDelegate().validateContainer(logonAttributes.getAirportCode(), containerVO);
									}
								}catch (BusinessDelegateException businessDelegateException) {
									errors = handleDelegateException(businessDelegateException);
								}

								
							}	

			    	}

					}catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
						if (errors != null && errors.size() > 0) {
							for(ErrorVO vo : errors) {
								log.log(Log.FINE,
										"vo.getErrorCode() ------------> ", vo.getErrorCode());
								if (("mailtracking.defaults.openedflight").equals(vo.getErrorCode())) {
									Object[] obj = vo.getErrorData();
									ContainerAssignmentVO containerAssignmentVO = (ContainerAssignmentVO)obj[2];
									log
											.log(
													Log.FINE,
													"ContainerAssignmentVO (Flight)------------> ",
													containerAssignmentVO);
									break;
								}
								else if (("mailtracking.defaults.canreassigned").equals(vo.getErrorCode())) {
									Object[] obj = vo.getErrorData();
									ContainerAssignmentVO containerAssignmentVO = (ContainerAssignmentVO)obj[2];
									log
											.log(
													Log.FINE,
													"ContainerAssignmentVO (Destn)------------> ",
													containerAssignmentVO);
									break;
								}
								else {								
									log.log(Log.FINE, "@@@@@@@@@@@@@@@@@XXXXXXXXXXXXXX");					
									break;
								}
							}
						}					
						
					}
				}
			}
		}

		
		/**
		 * Validation 2
		 * validate mailbags - server call
		 */

		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<ScannedMailDetailsVO> scannedOutboundSecondVOs = new ArrayList<ScannedMailDetailsVO>();
		try{
		if(scannedOutboundFirstVOs != null && scannedOutboundFirstVOs.size() > 0){
			for(ScannedMailDetailsVO scannedVO:scannedOutboundFirstVOs){
				ScannedMailDetailsVO scannedMailErrorVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(scannedMailErrorVO,scannedVO);
				ScannedMailDetailsVO scannedMailCorrectVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(scannedMailCorrectVO,scannedVO);
				Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
				Collection<MailbagVO> mailbagCorrectVOs = new ArrayList<MailbagVO>();
				mailbagVOs = new ArrayList<MailbagVO>();
				mailbagVOs = new MailTrackingDefaultsDelegate().validateScannedMailbagDetails(scannedVO.getMailDetails());
				for(MailbagVO mailbagVO:mailbagVOs){
					if(mailbagVO.getErrorType() != null && !"Y".equals(mailbagVO.getAcknowledge())){
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectVOs.add(mailbagVO);
					}
				}
				scannedMailErrorVO.setMailDetails(mailbagErrorVOs);
				if(mailbagErrorVOs.size() > 0){
					scannedOutboundFinalVOs.add(scannedMailErrorVO);
				}
				scannedMailCorrectVO.setMailDetails(mailbagCorrectVOs);
				if(mailbagCorrectVOs.size() > 0){
					scannedOutboundSecondVOs.add(scannedMailCorrectVO);
				}
			}
		}
		}catch (BusinessDelegateException businessDelegateException) {
 			handleDelegateException(businessDelegateException);
	 	}catch (SystemException e) {
			e.getMessage();
		}


	   /*
        * Getting OneTime values
        */
       Collection<OneTimeVO> catVOs = uploadMailSession.getOneTimeCat();
       Collection<OneTimeVO> hniVOs = uploadMailSession.getOneTimeHNI();
       Collection<OneTimeVO> riVOs = uploadMailSession.getOneTimeRI();
	   
	   /**
		 * Validation 3
		 * validate mailbags - with onetime
		 */
		int onetimeerror = 0;
	   if(scannedOutboundSecondVOs != null && scannedOutboundSecondVOs.size() > 0){
			for(ScannedMailDetailsVO scannedVO:scannedOutboundSecondVOs){
				mailbagVOs = scannedVO.getMailDetails();
				for(MailbagVO mailbagVO:mailbagVOs){
					String error = "";
					for(OneTimeVO catVO:catVOs){
						if(mailbagVO.getMailCategoryCode().equals(catVO.getFieldValue())){
							onetimeerror = 1;
						}
					}
					if(onetimeerror != 1){
						error = "C";
					}
					onetimeerror = 0;
					for(OneTimeVO hniVO:hniVOs){
						if(mailbagVO.getHighestNumberedReceptacle().equals(hniVO.getFieldValue())){
							onetimeerror = 1;
						}
					}
					if(onetimeerror != 1){
						error = new StringBuilder(error).append("H").toString();
					}
					onetimeerror = 0;
					for(OneTimeVO riVO:riVOs){
						if(mailbagVO.getRegisteredOrInsuredIndicator().equals(riVO.getFieldValue())){
							onetimeerror = 1;
						}
					}
					if(onetimeerror != 1){
						error = new StringBuilder(error).append("R").toString();
					}
					if("".equals(error)){
						mailbagVO.setErrorType(null);
					}else{
						mailbagVO.setErrorType(error);
					}
					onetimeerror = 0;
				}
			}
		}


		Collection<ScannedMailDetailsVO> scannedOutboundThirdVOs = new ArrayList<ScannedMailDetailsVO>();
		String catErr = "Mail category code is invalid";
		String hniErr = "HNI is invalid";
		String riErr = "RI is invalid";
		String errDesc = "";
		try{
		if(scannedOutboundSecondVOs != null && scannedOutboundSecondVOs.size() > 0){
			for(ScannedMailDetailsVO scannedVO:scannedOutboundSecondVOs){
				ScannedMailDetailsVO scannedMailErrorVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(scannedMailErrorVO,scannedVO);
				ScannedMailDetailsVO scannedMailCorrectVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(scannedMailCorrectVO,scannedVO);
				Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
				Collection<MailbagVO> mailbagCorrectVOs = new ArrayList<MailbagVO>();
				mailbagVOs = scannedVO.getMailDetails();
				for(MailbagVO mailbagVO:mailbagVOs){
					if(mailbagVO.getErrorType() != null){
						if("C".equals(mailbagVO.getErrorType())){
							errDesc = catErr;
						}
						if("CH".equals(mailbagVO.getErrorType())){
							errDesc = new StringBuilder(catErr).append(",").append(hniErr).toString();
						}
						if("CHR".equals(mailbagVO.getErrorType())){
							errDesc = new StringBuilder(catErr).append(",").append(hniErr).append(",").append(riErr).toString();
						}
						if("H".equals(mailbagVO.getErrorType())){
							errDesc = hniErr;
						}
						if("HR".equals(mailbagVO.getErrorType())){
							errDesc = new StringBuilder(hniErr).append(",").append(riErr).toString();
						}
						if("R".equals(mailbagVO.getErrorType())){
							errDesc = riErr;
						}
						mailbagVO.setErrorDescription(errDesc);
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectVOs.add(mailbagVO);
					}
				}
				scannedMailErrorVO.setMailDetails(mailbagErrorVOs);
				if(mailbagErrorVOs.size() > 0){
					scannedOutboundFinalVOs.add(scannedMailErrorVO);
				}
				scannedMailCorrectVO.setMailDetails(mailbagCorrectVOs);
				if(mailbagCorrectVOs.size() > 0){
					scannedOutboundThirdVOs.add(scannedMailCorrectVO);
				}
			}
		}
		}catch (SystemException e) {
			e.getMessage();
		}
		

		/**
		 * Checking for duplicate mailbags in same container
		 */
		int count = 0;
		Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
		if(scannedOutboundThirdVOs != null && scannedOutboundThirdVOs.size() > 0){
			for(ScannedMailDetailsVO scannedVO:scannedOutboundThirdVOs){
				mailbags = new ArrayList<MailbagVO>();
				mailbagVOs = scannedVO.getMailDetails();
				for(MailbagVO mailbagOuter:mailbagVOs){
					String error = "";
					for(MailbagVO mailbagInner:mailbagVOs){
						if(mailbagInner.getMailbagId().equals(mailbagOuter.getMailbagId())){
							count++;
						}
					}
					if(count > 1){
						error = "DS";
					}
					if("".equals(error)){
						mailbagOuter.setErrorType(null);
					}else{
						mailbagOuter.setErrorType(error);
					}
					mailbags.add(mailbagOuter);
					count = 0;
				}
				scannedVO.setMailDetails(mailbags);
			}
		}

		Collection<ScannedMailDetailsVO> scannedOutboundFourthVOs = new ArrayList<ScannedMailDetailsVO>();
		try{
		if(scannedOutboundThirdVOs != null && scannedOutboundThirdVOs.size() > 0){
			for(ScannedMailDetailsVO scannedVO:scannedOutboundThirdVOs){
				ScannedMailDetailsVO scannedMailErrorVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(scannedMailErrorVO,scannedVO);
				ScannedMailDetailsVO scannedMailCorrectVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(scannedMailCorrectVO,scannedVO);
				Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
				Collection<MailbagVO> mailbagCorrectVOs = new ArrayList<MailbagVO>();
				mailbagVOs = scannedVO.getMailDetails();
				for(MailbagVO mailbagVO:mailbagVOs){
					if(mailbagVO.getErrorType() != null){
						if("DS".equals(mailbagVO.getErrorType())){
							errDesc = "Duplicate Mailbags in Same container";
						}
						mailbagVO.setErrorDescription(errDesc);
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectVOs.add(mailbagVO);
					}
				}
				scannedMailErrorVO.setMailDetails(mailbagErrorVOs);
				if(mailbagErrorVOs.size() > 0){
					scannedOutboundFinalVOs.add(scannedMailErrorVO);
				}
				scannedMailCorrectVO.setMailDetails(mailbagCorrectVOs);
				if(mailbagCorrectVOs.size() > 0){
					scannedOutboundFourthVOs.add(scannedMailCorrectVO);
				}
			}
		}
		}catch (SystemException e) {
			e.getMessage();
		}


		/**
		 * Checking for duplicate mailbags acrosss container
		 */
		count = 0;
		Collection<MailbagVO> newMailbags = new ArrayList<MailbagVO>();
		if(scannedOutboundFourthVOs != null && scannedOutboundFourthVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedOutboundFourthVOs){
				newMailbags = new ArrayList<MailbagVO>();
				Collection<MailbagVO> mailbagsOuter = scannedOuter.getMailDetails();
				for(MailbagVO mailbagOuter:mailbagsOuter){
					String error = "";
					for(ScannedMailDetailsVO scannedInner:scannedOutboundFourthVOs){
						Collection<MailbagVO> mailbagsInner = scannedInner.getMailDetails();
						for(MailbagVO mailbagInner:mailbagsInner){
							if(mailbagInner.getMailbagId().equals(mailbagOuter.getMailbagId())){
								count++;
							}
						}
					}
					if(count == 2){
						error = "DA";
					}
					if("".equals(error)){
						mailbagOuter.setErrorType(null);
					}else{
						mailbagOuter.setErrorType(error);
					}
					newMailbags.add(mailbagOuter);
					count = 0;
				}
				scannedOuter.setMailDetails(newMailbags);
			}
		}

		Collection<ScannedMailDetailsVO> scannedOutboundFifthVOs = new ArrayList<ScannedMailDetailsVO>();
		try{
		if(scannedOutboundFourthVOs != null && scannedOutboundFourthVOs.size() > 0){
			for(ScannedMailDetailsVO scannedVO:scannedOutboundFourthVOs){
				ScannedMailDetailsVO scannedMailErrorVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(scannedMailErrorVO,scannedVO);
				ScannedMailDetailsVO scannedMailCorrectVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(scannedMailCorrectVO,scannedVO);
				Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
				Collection<MailbagVO> mailbagCorrectVOs = new ArrayList<MailbagVO>();
				mailbagVOs = scannedVO.getMailDetails();
				for(MailbagVO mailbagVO:mailbagVOs){
					if(mailbagVO.getErrorType() != null){
						if("DA".equals(mailbagVO.getErrorType())){
							errDesc = "Duplicate Mailbags Across container";
						}
						mailbagVO.setErrorDescription(errDesc);
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectVOs.add(mailbagVO);
					}
				}
				scannedMailErrorVO.setMailDetails(mailbagErrorVOs);
				if(mailbagErrorVOs.size() > 0){
					scannedOutboundFinalVOs.add(scannedMailErrorVO);
				}
				scannedMailCorrectVO.setMailDetails(mailbagCorrectVOs);
				if(mailbagCorrectVOs.size() > 0){
					scannedOutboundFifthVOs.add(scannedMailCorrectVO);
				}
			}
		}
		}catch (SystemException e) {
			e.getMessage();
		}

		/**
		 * Checking Container Assignment Status
		 */


		Collection<ScannedMailDetailsVO> scannedOutboundSixthVOs = new ArrayList<ScannedMailDetailsVO>();
		if(scannedOutboundFifthVOs != null && scannedOutboundFifthVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedOutboundFifthVOs){
				try{
					ContainerVO containerVO  = new ContainerVO();
					containerVO.setCompanyCode(logonAttributes.getCompanyCode());
					containerVO.setContainerNumber(scannedOuter.getContainerNumber());
					containerVO.setAssignedPort(scannedOuter.getPol());

					ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
					containerAssignmentVO = new MailTrackingDefaultsDelegate().findContainerAssignment(containerVO);
					/*
					 * For AirNZ Bug:40940
					 * For time being, 
					 * 1. Not Assigned to any Carrier/Flight -> New
					 * 2. Assigned to the same Carrier -> Update
					 * 3. Assigned to some other Flight/Carrier -> Error
					 */
					if(containerAssignmentVO !=null) {
						if(scannedOuter.getFlightNumber() != null	&& scannedOuter.getFlightNumber().trim().length() > 0 
								&& containerAssignmentVO.getCompanyCode().equals(scannedOuter.getCompanyCode())
								&& containerAssignmentVO.getCarrierId() == scannedOuter.getCarrierId()
								&& containerAssignmentVO.getFlightNumber().equals(scannedOuter.getFlightNumber())
								&& containerAssignmentVO.getFlightSequenceNumber() == scannedOuter.getFlightSequenceNumber()){
							// Assigned to same Flight
							scannedOutboundSixthVOs.add(scannedOuter);
						}else 	if((scannedOuter.getFlightNumber() == null || 
									scannedOuter.getFlightNumber() != null && scannedOuter.getFlightNumber().trim().length() == 0 )
								&& containerAssignmentVO.getCompanyCode().equals(scannedOuter.getCompanyCode())
								&& containerAssignmentVO.getCarrierId() == scannedOuter.getCarrierId()
								&& containerAssignmentVO.getDestination().equals(scannedOuter.getDestination())){
							
							// Assigned to same Carrier - Destination
							scannedOutboundSixthVOs.add(scannedOuter);
						}else {
							// Assigned to some Carrier/ Flight -> So, "ERROR"
							String container = "";
							String carrierCode = "";
							String fltNum = "";
							String fltDate = "";
							if(scannedOuter.getContainerNumber() != null && scannedOuter.getContainerNumber().trim().length() > 0) {
								container = scannedOuter.getContainerNumber();
							}		
							if(scannedOuter.getCarrierCode() != null && scannedOuter.getCarrierCode().trim().length() > 0) {
								carrierCode = containerAssignmentVO.getCarrierCode();
							} 
							if(scannedOuter.getFlightNumber() != null && scannedOuter.getFlightNumber().trim().length() > 0) {
								fltNum = containerAssignmentVO.getFlightNumber();
							}
							if(scannedOuter.getFlightDate() != null) {
								fltDate = containerAssignmentVO.getFlightDate().toDisplayDateOnlyFormat();
							}
							scannedOuter.setErrorType("CU");
							scannedOuter.setErrorDescription(
									"Container "
									.concat(container)
									.concat(" already assigned to ")
									.concat(carrierCode)
									.concat(" ")
									.concat(fltNum)
									.concat(" ")
									.concat(fltDate));
							if(scannedOuter.getMailDetails() != null && scannedOuter.getMailDetails().size() > 0) {
								for(MailbagVO mails : scannedOuter.getMailDetails()) {
									mails.setErrorType("CU");
									mails.setErrorDescription(
											"Container "
											.concat(container)
											.concat(" already assigned to ")
											.concat(carrierCode)
											.concat(" ")
											.concat(fltNum)
											.concat(" ")
											.concat(fltDate));
								}
							}
							scannedOutboundFinalVOs.add(scannedOuter);
						}
					}else{
						// Not assigned to any Carrier/ Flight ---> So, NEW
						scannedOutboundSixthVOs.add(scannedOuter);
					}
				}catch (BusinessDelegateException businessDelegateException) {
		       		errors = handleDelegateException(businessDelegateException);
		       	}				
			}
		}
		
		Collection<ScannedMailDetailsVO> scannedOutboundFltVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedObndDestVOs = new ArrayList<ScannedMailDetailsVO>();
		if(scannedOutboundSixthVOs != null && scannedOutboundSixthVOs.size() > 0){
			for(ScannedMailDetailsVO scannedVO:scannedOutboundSixthVOs){
				if(scannedVO.getFlightNumber() != null
						&& !"".equals(scannedVO.getFlightNumber())){
					scannedOutboundFltVOs.add(scannedVO);
				}else{
					scannedObndDestVOs.add(scannedVO);
				}
			}
		}
		
		
		/**
		 * making MailAcceptanceVO for flight assigned
		 */
		
		Collection<ScannedMailDetailsVO> scannedOutboundFlightVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<String> containerNumColl = new ArrayList<String>();
		if(scannedOutboundFltVOs != null && scannedOutboundFltVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedOutboundFltVOs){
				Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
				if(!containerNumColl.contains(scannedOuter.getContainerNumber())){
				containerNumColl.add(scannedOuter.getContainerNumber());
				for(ScannedMailDetailsVO scannedInner:scannedOutboundFltVOs){
					if(scannedOuter.getCompanyCode().equals(scannedInner.getCompanyCode())
						&& scannedOuter.getCarrierId() == scannedInner.getCarrierId()
						&& scannedOuter.getFlightNumber().equals(scannedInner.getFlightNumber())
						&& scannedOuter.getFlightSequenceNumber() == scannedInner.getFlightSequenceNumber()
						&& scannedOuter.getContainerNumber().equals(scannedInner.getContainerNumber())){
						if(mailVOs != null && mailVOs.size() > 0){
							mailVOs.addAll(scannedInner.getMailDetails());
						}else{
							mailVOs =  new ArrayList<MailbagVO>();
							mailVOs.addAll(scannedInner.getMailDetails());
						}
					}
				}
				scannedOuter.setMailDetails(mailVOs);
				scannedOutboundFlightVOs.add(scannedOuter);
			  }
			}
		}
	
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<MailAcceptanceVO> mailAcceptanceVOs = new ArrayList<MailAcceptanceVO>();
		HashMap<String,MailAcceptanceVO> mailFlightMap = new HashMap<String,MailAcceptanceVO>();
		if(scannedOutboundFlightVOs != null && scannedOutboundFlightVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedOutboundFlightVOs){
				String pk = new StringBuilder().append(scannedOuter.getCompanyCode())
				.append(scannedOuter.getCarrierId())
				.append(scannedOuter.getFlightNumber())
				.append(scannedOuter.getFlightSequenceNumber()).toString();
				if(mailFlightMap.get(pk) == null){

					mailAcceptanceVO = new MailAcceptanceVO();
					mailAcceptanceVO.setCompanyCode(scannedOuter.getCompanyCode());
					mailAcceptanceVO.setCarrierId(scannedOuter.getCarrierId());
					mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
					mailAcceptanceVO.setFlightCarrierCode(scannedOuter.getCarrierCode());
					mailAcceptanceVO.setFlightDate(scannedOuter.getFlightDate());
					mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
					mailAcceptanceVO.setFlightSequenceNumber(scannedOuter.getFlightSequenceNumber());
					mailAcceptanceVO.setFlightNumber(scannedOuter.getFlightNumber());
					mailAcceptanceVO.setLegSerialNumber(scannedOuter.getLegSerialNumber());
					mailAcceptanceVO.setPol(scannedOuter.getPol());
					mailAcceptanceVO.setScanned(true);
					if("Y".equals(uploadMailForm.getPreassignFlag())){
						mailAcceptanceVO.setPreassignNeeded(true);
					}else{
						mailAcceptanceVO.setPreassignNeeded(false);
					}
					containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
					for(ScannedMailDetailsVO scannedInner:scannedOutboundFlightVOs){
						if(scannedOuter.getCompanyCode().equals(scannedInner.getCompanyCode())
								&& scannedOuter.getCarrierId() == scannedInner.getCarrierId()
								&& scannedOuter.getFlightNumber().equals(scannedInner.getFlightNumber())
								&& scannedOuter.getFlightSequenceNumber() == scannedInner.getFlightSequenceNumber()){

							ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
							containerDetailsVO.setCompanyCode(scannedInner.getCompanyCode());
							containerDetailsVO.setContainerNumber(scannedInner.getContainerNumber());
							containerDetailsVO.setFlightNumber(scannedInner.getFlightNumber());
							containerDetailsVO.setFlightDate(scannedInner.getFlightDate());
							containerDetailsVO.setFlightSequenceNumber(scannedInner.getFlightSequenceNumber());
							containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
							containerDetailsVO.setAcceptedFlag(scannedInner.getAcceptedFlag());
							containerDetailsVO.setDestination(scannedInner.getDestination());
							containerDetailsVO.setPol(scannedInner.getPol());
							containerDetailsVO.setPou(scannedInner.getPou());
							containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
							containerDetailsVO.setSegmentSerialNumber(scannedInner.getSegmentSerialNumber());
							containerDetailsVO.setCarrierId(scannedInner.getCarrierId());
							containerDetailsVO.setCarrierCode(scannedInner.getCarrierCode());
							//added by anitha
							containerDetailsVO.setContainerType(scannedInner.getContainerType());
							/*
							 * For AirNZ Bug:40940
							 */
							try{
								ContainerVO containerVO  = new ContainerVO();
								containerVO.setCompanyCode(logonAttributes.getCompanyCode());
								containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
								containerVO.setAssignedPort(containerDetailsVO.getPol());

								ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
								containerAssignmentVO = new MailTrackingDefaultsDelegate().findContainerAssignment(containerVO);
								/*
								 * For time being, 
								 * 1. Not Assigned to any Carrier/Flight -> New Container
								 * 2. Assigned to the same Carrier -> Update Container
								 */
								if(containerAssignmentVO !=null 
										&& containerAssignmentVO.getCompanyCode().equals(containerDetailsVO.getCompanyCode())
										&& containerAssignmentVO.getCarrierId() == containerDetailsVO.getCarrierId()
										&& containerAssignmentVO.getFlightNumber().equals(containerDetailsVO.getFlightNumber())
										&& containerAssignmentVO.getFlightSequenceNumber() == containerDetailsVO.getFlightSequenceNumber()){
									// Assigned to same Flight -> So, "UPDATE CONTAINER"
									containerDetailsVO.setOperationFlag("U");			
									containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
									containerDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());						
								}else{
									// Not assigned to any Carrier/ Flight -> So, " NEW CONTAINER"
									containerDetailsVO.setOperationFlag("I");
									containerDetailsVO.setContainerOperationFlag("I");
								}

							}catch (BusinessDelegateException businessDelegateException) {
								errors = handleDelegateException(businessDelegateException);
							}
							Collection<MailbagVO> mailVOs = scannedInner.getMailDetails();
							if(mailVOs != null && mailVOs.size() > 0){
								for(MailbagVO mailbagVO:mailVOs){
									if("Y".equalsIgnoreCase(mailbagVO.getReassignFlag())){
										mailAcceptanceVO.setDuplicateMailOverride("Y");
										mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
									}else {
										mailbagVO.setCompanyCode(scannedInner.getCompanyCode());
										mailbagVO.setCarrierCode(containerDetailsVO.getCarrierCode());
										mailbagVO.setFlightDate(containerDetailsVO.getFlightDate());
										mailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
										mailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
										mailbagVO.setContainerType(containerDetailsVO.getContainerType());
										mailbagVO.setPou(containerDetailsVO.getPou());
										mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
										mailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
										mailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
										mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
										mailbagVO.setFromSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
										mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
										mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());

										mailbagVO.setScannedPort(containerDetailsVO.getPol());
										mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
										mailbagVO.setArrivedFlag("N");
										mailbagVO.setDeliveredFlag("N");
										mailbagVO.setAcceptanceFlag("Y");
										mailbagVO.setOperationalFlag("I");
										mailbagVO.setArrivedFlag("N");
										mailbagVO.setDeliveredFlag("N");
										mailbagVO.setContainerType(containerDetailsVO.getContainerType());
									}
									//added by anitha
									if(mailbagVO.getOffloadedRemarks()!=null){
										containerDetailsVO.setRemarks(mailbagVO.getOffloadedRemarks());
									}
								}
							}
							containerDetailsVO.setMailDetails(mailVOs);

							HashMap<String,DSNVO> dsnMap = new HashMap<String,DSNVO>();
							if(mailVOs != null && mailVOs.size() > 0){
								for(MailbagVO mailbagVO:mailVOs){
									int numBags = 0;
									double bagWgt = 0;
									double vol = 0;
									String outerpk = mailbagVO.getOoe()+mailbagVO.getDoe()
									+mailbagVO.getMailSubclass()
									+ mailbagVO.getMailCategoryCode()
									+mailbagVO.getDespatchSerialNumber()+mailbagVO.getYear();
									if(dsnMap.get(outerpk) == null){
										DSNVO dsnVO = new DSNVO();
										dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
										dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
										dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
										dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
										dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
										dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
										dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
										dsnVO.setOperationFlag("U");
										dsnVO.setYear(mailbagVO.getYear());
										dsnVO.setPltEnableFlag("Y");
										for(MailbagVO innerVO:mailVOs){
											String innerpk = innerVO.getOoe()+innerVO.getDoe()
											+(innerVO.getMailSubclass())
											+ innerVO.getMailCategoryCode()
											+innerVO.getDespatchSerialNumber()+innerVO.getYear();
											if(outerpk.equals(innerpk)){
												if(!"Y".equalsIgnoreCase(innerVO.getReassignFlag())) {
													numBags = numBags + 1;
													//bagWgt = bagWgt + innerVO.getWeight();
													bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();//added by A-7371
													//vol = vol + innerVO.getVolume();
													vol = vol + innerVO.getVolume().getRoundedSystemValue();//added  by A-7371
												}
											}
										}
										dsnVO.setBags(numBags);
										//dsnVO.setWeight(bagWgt);
										dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
										//dsnVO.setAcceptedVolume(vol);
										dsnVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,vol));//added by A-7371
										//dsnVO.setStatedVolume(vol);
										dsnVO.setStatedVolume(new Measure(UnitConstants.VOLUME,vol));//added by A-7371
										dsnMap.put(outerpk,dsnVO);
										numBags = 0;
										bagWgt = 0;
										vol = 0;
									}
								}
							}
							Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
							int totBags = 0;
							double totWgt = 0;
							for(String key:dsnMap.keySet()){
								DSNVO dsnVO = dsnMap.get(key);
								totBags = totBags + dsnVO.getBags();
								//totWgt = totWgt + dsnVO.getWeight();
								totWgt = totWgt + dsnVO.getWeight().getRoundedSystemValue();//added by A-7371
								newDSNVOs.add(dsnVO);
							}

							containerDetailsVO.setDsnVOs(newDSNVOs);
							containerDetailsVO.setTotalBags(totBags);
							//containerDetailsVO.setTotalWeight(totWgt);
							containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,totWgt));//added by A-7371
							containerDetailsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
							containerDetailsVOs.add(containerDetailsVO);

						}
					}
					mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
					mailAcceptanceVOs.add(mailAcceptanceVO);
					mailFlightMap.put(pk,mailAcceptanceVO);
				}
			}
		}

		/**
		 * making MailAcceptanceVO for Destn assigned
		 */
		
		Collection<ScannedMailDetailsVO> scannedOutboundDestVOs = new ArrayList<ScannedMailDetailsVO>();
		containerNumColl = new ArrayList<String>();
		if(scannedObndDestVOs != null && scannedObndDestVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedObndDestVOs){
				Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
				if(!containerNumColl.contains(scannedOuter.getContainerNumber())){
				containerNumColl.add(scannedOuter.getContainerNumber());
				for(ScannedMailDetailsVO scannedInner:scannedObndDestVOs){
					if(scannedOuter.getCompanyCode().equals(scannedInner.getCompanyCode())
						&& scannedOuter.getCarrierId() == scannedInner.getCarrierId()
						&& scannedOuter.getFlightNumber().equals(scannedInner.getFlightNumber())
						&& scannedOuter.getFlightSequenceNumber() == scannedInner.getFlightSequenceNumber()
						&& scannedOuter.getContainerNumber().equals(scannedInner.getContainerNumber())){
						if(mailVOs != null && mailVOs.size() > 0){
							mailVOs.addAll(scannedInner.getMailDetails());
						}else{
							mailVOs =  new ArrayList<MailbagVO>();
							mailVOs.addAll(scannedInner.getMailDetails());
						}
					}
				}
				scannedOuter.setMailDetails(mailVOs);
				scannedOutboundDestVOs.add(scannedOuter);
			  }
			}
		}
		
		mailAcceptanceVO = new MailAcceptanceVO();
		containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<MailAcceptanceVO> mailAcceptanceDestVOs = new ArrayList<MailAcceptanceVO>();
		HashMap<String,MailAcceptanceVO> mailDestMap = new HashMap<String,MailAcceptanceVO>();
		if(scannedOutboundDestVOs != null && scannedOutboundDestVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedOutboundDestVOs){
				String pk = new StringBuilder().append(scannedOuter.getCompanyCode())
				.append(scannedOuter.getCarrierId())
				.append(scannedOuter.getDestination()).toString();
				if(mailDestMap.get(pk) == null){
					mailAcceptanceVO = new MailAcceptanceVO();
					mailAcceptanceVO.setCompanyCode(scannedOuter.getCompanyCode());
					mailAcceptanceVO.setCarrierId(scannedOuter.getCarrierId());
					mailAcceptanceVO.setFlightCarrierCode(scannedOuter.getCarrierCode());
					mailAcceptanceVO.setFlightNumber("-1");
					mailAcceptanceVO.setFlightSequenceNumber(-1);
					mailAcceptanceVO.setLegSerialNumber(-1);
					mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					if(scannedOuter.getDestination() != null && !"".equals(scannedOuter.getDestination())){
						mailAcceptanceVO.setInventory(false);
						mailAcceptanceVO.setDestination(scannedOuter.getDestination());
					}else{
						mailAcceptanceVO.setInventory(true);
					}
					mailAcceptanceVO.setPol(scannedOuter.getPol());
					mailAcceptanceVO.setScanned(true);
					containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
					for(ScannedMailDetailsVO scannedInner:scannedOutboundDestVOs){

						if((scannedOuter.getDestination() != null
								&& scannedInner.getDestination() != null) ||
								(scannedOuter.getDestination() == null
										&& scannedInner.getDestination() == null)){

							if(scannedOuter.getCompanyCode().equals(scannedInner.getCompanyCode())
									&& scannedOuter.getCarrierId() == scannedInner.getCarrierId()){

								ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
								containerDetailsVO.setCompanyCode(scannedInner.getCompanyCode());
								containerDetailsVO.setContainerNumber(scannedInner.getContainerNumber());
								containerDetailsVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
								containerDetailsVO.setFlightDate(mailAcceptanceVO.getFlightDate());
								containerDetailsVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
								containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
								containerDetailsVO.setAcceptedFlag(scannedInner.getAcceptedFlag());
								containerDetailsVO.setDestination(scannedInner.getDestination());
								containerDetailsVO.setPol(scannedInner.getPol());
								containerDetailsVO.setPou(scannedInner.getPou());
								containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
								containerDetailsVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
								containerDetailsVO.setCarrierId(scannedInner.getCarrierId());
								containerDetailsVO.setCarrierCode(scannedInner.getCarrierCode());
								//added by anitha
								containerDetailsVO.setContainerType(scannedInner.getContainerType());
								containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
								/*
								 * For AirNZ Bug:40940
								 */
								try{
									ContainerVO containerVO  = new ContainerVO();
									containerVO.setCompanyCode(logonAttributes.getCompanyCode());
									containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
									containerVO.setAssignedPort(containerDetailsVO.getPol());

									ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
									containerAssignmentVO = new MailTrackingDefaultsDelegate().findContainerAssignment(containerVO);
									/*
									 * 1. Not Assigned to any Carrier/Flight -> New Container
									 * 2. Assigned to the same Carrier -> Update Container
									 */
									if(containerAssignmentVO !=null
											&& containerAssignmentVO.getCompanyCode().equals(containerDetailsVO.getCompanyCode())
											&& containerAssignmentVO.getCarrierId() == containerDetailsVO.getCarrierId()
											&& containerAssignmentVO.getDestination().equals(containerDetailsVO.getDestination())){
										// Assigned to same Carrier -> So, "UPDATE CONTAINER"
										containerDetailsVO.setOperationFlag("U");		
									}else{
										// Not assigned to any Carrier/ Flight -> So, " NEW CONTAINER"
										containerDetailsVO.setOperationFlag("I");
										containerDetailsVO.setContainerOperationFlag("I");
									}
								}catch (BusinessDelegateException businessDelegateException) {
									errors = handleDelegateException(businessDelegateException);
								}

								Collection<MailbagVO> mailVOs = scannedInner.getMailDetails();
								if(mailVOs != null && mailVOs.size() > 0){
									for(MailbagVO mailbagVO:mailVOs){
										if("Y".equalsIgnoreCase(mailbagVO.getReassignFlag())){
											mailAcceptanceVO.setDuplicateMailOverride("Y");
											mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
										}else {
											mailbagVO.setCompanyCode(containerDetailsVO.getCompanyCode());
											mailbagVO.setCarrierCode(containerDetailsVO.getCarrierCode());
											mailbagVO.setFlightDate(containerDetailsVO.getFlightDate());
											mailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
											mailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
											mailbagVO.setContainerType(containerDetailsVO.getContainerType());
											mailbagVO.setPou(containerDetailsVO.getPou());
											mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
											mailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
											mailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
											mailbagVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
											mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
											mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
											mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
											mailbagVO.setScannedPort(containerDetailsVO.getPol());
											mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
											mailbagVO.setArrivedFlag("N");
											mailbagVO.setDeliveredFlag("N");
											mailbagVO.setAcceptanceFlag("Y");
											mailbagVO.setOperationalFlag("I");
											mailbagVO.setArrivedFlag("N");
											mailbagVO.setDeliveredFlag("N");
										}
//										mailbagVO.setContainerType("U");
										//	added by anitha
										if(mailbagVO.getOffloadedRemarks()!=null){
											containerDetailsVO.setRemarks(mailbagVO.getOffloadedRemarks());
										}
									}
								}
								containerDetailsVO.setMailDetails(mailVOs);
								HashMap<String,DSNVO> dsnMap = new HashMap<String,DSNVO>();
								if(mailVOs != null && mailVOs.size() > 0){
									for(MailbagVO mailbagVO:mailVOs){
										int numBags = 0;
										double bagWgt = 0;
										double vol = 0;
										String outerpk = mailbagVO.getOoe()
										+mailbagVO.getDoe()
										+mailbagVO.getMailSubclass()
										+mailbagVO.getMailCategoryCode()
										+mailbagVO.getDespatchSerialNumber()
										+mailbagVO.getYear();
										if(dsnMap.get(outerpk) == null){
											DSNVO dsnVO = new DSNVO();
											dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
											dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
											dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
											dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
											dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
											dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
											dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
											dsnVO.setYear(mailbagVO.getYear());
											dsnVO.setPltEnableFlag("Y");
											dsnVO.setOperationFlag("U");
											for(MailbagVO innerVO:mailVOs){
												String innerpk = innerVO.getOoe()
												+innerVO.getDoe()
												+innerVO.getMailSubclass()
												+innerVO.getMailCategoryCode()
												+innerVO.getDespatchSerialNumber()
												+innerVO.getYear();
												if(outerpk.equals(innerpk)){
													if(!"Y".equalsIgnoreCase(innerVO.getReassignFlag())) {
														numBags = numBags + 1;
														//bagWgt = bagWgt + innerVO.getWeight();
														bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();
														//vol = vol + innerVO.getVolume();
														vol = vol + innerVO.getVolume().getRoundedSystemValue();//added by A-7371
													}
												}
											}
											dsnVO.setBags(numBags);
											//dsnVO.setWeight(bagWgt);
											dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
											//dsnVO.setAcceptedVolume(vol);
											dsnVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,vol));//added by A-7371
											//dsnVO.setStatedVolume(vol);
											dsnVO.setStatedVolume(new Measure(UnitConstants.VOLUME,vol));//added by A-7371
											dsnMap.put(outerpk,dsnVO);
											numBags = 0;
											bagWgt = 0;
											vol = 0;
										}
									}
								}
								Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
								int totBags = 0;
								double totWgt = 0;
								for(String key:dsnMap.keySet()){
									DSNVO dsnVO = dsnMap.get(key);
									totBags = totBags + dsnVO.getBags();
									//totWgt = totWgt + dsnVO.getWeight();
									totWgt = totWgt + dsnVO.getWeight().getRoundedSystemValue();//added by A-7371
									newDSNVOs.add(dsnVO);
								}
								containerDetailsVO.setDsnVOs(newDSNVOs);
								containerDetailsVO.setTotalBags(totBags);
								//containerDetailsVO.setTotalWeight(totWgt);
								containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,totWgt));//added by A-7371
								containerDetailsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
								containerDetailsVOs.add(containerDetailsVO);
							}
						}
					}

					mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
					mailAcceptanceDestVOs.add(mailAcceptanceVO);
					mailDestMap.put(pk,mailAcceptanceVO);

				}
			}
		}
		if(mailAcceptanceDestVOs != null && mailAcceptanceDestVOs.size() > 0){
			mailAcceptanceVOs.addAll(mailAcceptanceDestVOs);
		}
		
		
		Collection<ScannedMailDetailsVO> scannedVOs = new ArrayList<ScannedMailDetailsVO>();
		try {
			if(mailAcceptanceVOs != null && mailAcceptanceVOs.size() > 0) {
				scannedVOs = new MailTrackingDefaultsDelegate().saveScannedOutboundDetails(mailAcceptanceVOs);
			}
			
        }catch (BusinessDelegateException businessDelegateException) {
        	errors = handleDelegateException(businessDelegateException);
 		}
        
        if(scannedVOs !=null && scannedVOs.size() >0){
		    ScannedMailDetailsVO scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedVOs).get(0);
	        for(ScannedMailDetailsVO scannedVO:scannedVOs){
	    		   Collection<ExistingMailbagVO> existingMailbagVOs = scannedMailDetailsVO.getExistingMailbagVOS();
	         	   if(existingMailbagVOs!=null && existingMailbagVOs.size()>0){
	         		   ExistingMailbagVO existingMailbagVO = ((ArrayList<ExistingMailbagVO>)existingMailbagVOs).get(0);
	         		   if("C".equals(existingMailbagVO.getFlightStatus())){
	         			  ErrorVO errorVO = new ErrorVO("mailtracking.defaults.flightisclosed");
			    		  errorsAccept.add(errorVO);
					   } else if(scannedVO.getPol() != null && !scannedVO.getPol().equals(existingMailbagVO.getCurrentAirport())){
	         			    ErrorVO errorVO = new ErrorVO("mailtracking.defaults.scannedportsarediff");
			    			errorsAccept.add(errorVO);
					   }else{
	         			  log.log(Log.INFO,"can reassignmail bags");
	         			  for(MailbagVO mailbagvo : scannedVO.getMailDetails()){
	         				 mailbagvo.setErrorType(MailConstantsVO.EXCEPT_WARN);
	         				 mailbagvo.setErrorDescription("Can reassign mailbags");
	         			  }
	         		  }
	         	   }
	    		}
	        }
        
        
        if (errors != null && errors.size() > 0) {
        	 List<ErrorVO> erorlist = new ArrayList<ErrorVO>(errors);
        	  for(ErrorVO errorvo : erorlist) {
        		if(errorvo.getErrorDisplayType()== ErrorDisplayType.ERROR ){
        			 errorsAccept.addAll(errors);
        			 scannedOutboundFinalVOs.addAll(scannedVOs);
        		}
        	 }
        }
        
      
   	  /**
	   * merging scanned VOs from server with available scanned error VOs
	   */

        log.log(Log.INFO, "scannedOutboundFinalVOs >>> ",
				scannedOutboundFinalVOs);
		log.log(Log.INFO, "scannedVOs >>> ", scannedVOs);
		boolean flag = false;
        Collection<ScannedMailDetailsVO> finalscannedOutboundFinalVOs = new ArrayList<ScannedMailDetailsVO>();
        if(scannedOutboundFinalVOs != null && scannedOutboundFinalVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedOutboundFinalVOs){
				if(scannedVOs != null && scannedVOs.size() > 0){
					for(ScannedMailDetailsVO scannedInner:scannedVOs){
						 flag = false;
						 if(scannedOuter.getCompanyCode().equals(scannedInner.getCompanyCode())
										&& scannedOuter.getCarrierId() == scannedInner.getCarrierId()
										&& scannedOuter.getFlightNumber().equals(scannedInner.getFlightNumber())
										&& scannedOuter.getFlightSequenceNumber() == scannedInner.getFlightSequenceNumber()
										&& scannedOuter.getContainerNumber().equals(scannedInner.getContainerNumber())){
								 		log.log(Log.INFO,"same vos.....");
								 		flag = true;
						            	break;
						 }
					}
				 if(!flag){
					 finalscannedOutboundFinalVOs.add(scannedOuter);
			     }
				}
			}
			if(scannedVOs != null && scannedVOs.isEmpty()){
				finalscannedOutboundFinalVOs.addAll(scannedOutboundFinalVOs);
			}else{
				finalscannedOutboundFinalVOs.addAll(scannedVOs);
			}
			
        }else{
        	finalscannedOutboundFinalVOs.addAll(scannedVOs);
        }
        
	  scannedDetailsVO.setOutboundMails(finalscannedOutboundFinalVOs);
	  
	 
		/**
		 * For Summary Details
		 */
		ScannedDetailsVO summaryVO = uploadMailSession.getScannedSummaryVO();
		Collection<ScannedMailDetailsVO> scannedSummaryVOs = summaryVO.getOutboundMails();
		Collection<ScannedMailDetailsVO> scannedAcpVOs = scannedDetailsVO.getOutboundMails();
		if(scannedSummaryVOs != null && scannedSummaryVOs.size() > 0){
			for(ScannedMailDetailsVO scannedSummaryVO:scannedSummaryVOs){
				int unsaved = 0;
				if(scannedAcpVOs != null && scannedAcpVOs.size() > 0){
					for(ScannedMailDetailsVO scannedMailVO:scannedAcpVOs){

						mailbagVOs = new ArrayList<MailbagVO>();
						mailbagVOs = scannedMailVO.getMailDetails();
						if(mailbagVOs != null && mailbagVOs.size() > 0){
							unsaved = mailbagVOs.size();
						}

						if(scannedMailVO.getFlightNumber() == null){
							if(scannedSummaryVO.getFlightNumber() == null){
								if(scannedSummaryVO.getContainerNumber().equals(scannedMailVO.getContainerNumber())){
									scannedSummaryVO.setUnsavedBags(unsaved);
									scannedSummaryVO.setSavedBags(scannedSummaryVO.getScannedBags() - scannedSummaryVO.getUnsavedBags());
								}
							}
						}else if("-1".equals(scannedMailVO.getFlightNumber())){
							if("-1".equals(scannedSummaryVO.getFlightNumber())){
								if((scannedSummaryVO.getDestination() != null && scannedMailVO.getDestination() != null) ||
										(scannedSummaryVO.getDestination() == null && scannedMailVO.getDestination() == null)){
								if(scannedSummaryVO.getContainerNumber().equals(scannedMailVO.getContainerNumber())
									&& scannedSummaryVO.getCarrierId() == scannedMailVO.getCarrierId()){
									scannedSummaryVO.setUnsavedBags(unsaved);
									scannedSummaryVO.setSavedBags(scannedSummaryVO.getScannedBags() - scannedSummaryVO.getUnsavedBags());
								}
							  }
							}
						}else{
							if(scannedSummaryVO.getContainerNumber().equals(scannedMailVO.getContainerNumber())
									&& scannedSummaryVO.getFlightNumber().equals(scannedMailVO.getFlightNumber())
									&& scannedSummaryVO.getCarrierId() == scannedMailVO.getCarrierId()
									&& scannedSummaryVO.getFlightSequenceNumber() == scannedMailVO.getFlightSequenceNumber()){
								scannedSummaryVO.setUnsavedBags(unsaved);
								scannedSummaryVO.setSavedBags(scannedSummaryVO.getScannedBags() - scannedSummaryVO.getUnsavedBags());
							}else{
								scannedSummaryVO.setUnsavedBags(unsaved);
								scannedSummaryVO.setSavedBags(scannedSummaryVO.getScannedBags() - scannedSummaryVO.getUnsavedBags());
							}
						}
					}
				}else{
					scannedSummaryVO.setUnsavedBags(unsaved);
					scannedSummaryVO.setSavedBags(scannedSummaryVO.getScannedBags() - scannedSummaryVO.getUnsavedBags());
				}
			}
		}

		summaryVO.setOutboundMails(scannedSummaryVOs);

		uploadMailSession.setScannedSummaryVO(summaryVO);


	  uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		uploadMailForm.setDisableStat("SAVED");
		invocationContext.addAllError(errorsAccept);
		invocationContext.target = TARGET;
		log.exiting("SaveUploadAcceptCommand","execute");

	}
	
	
	
	

}
