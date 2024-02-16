/*
 * SaveUploadArriveCommand.java Created on Oct 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class SaveUploadArriveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";

	private static final String TARGET = "success";	
	private static final String HYPHEN = "-";	


	/**
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("SaveUploadArriveCommand","execute");
		UploadMailForm uploadMailForm
			= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errorsArrival = new ArrayList<ErrorVO>();

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		
		if(uploadMailForm.getEnablemode()){
			ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
			ScannedDetailsVO returnscanneddetailsVO = CompareScannedDateCommand.checkscannedDetailsVO(scannedDetailsVO,"ARR");
			uploadMailSession.setScannedDetailsVO(returnscanneddetailsVO);
		}

		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		ScannedDetailsVO  initialScannedDetailVO = null;
		
		if(scannedDetailsVO != null ){
			try {
				initialScannedDetailVO = new MailTrackingDefaultsDelegate().findDetailsForMailUpload(scannedDetailsVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		

		/**
		 * Validation 1
		 * Whether a container is assigned or not
		 */
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		Collection<ScannedMailDetailsVO> scannedArrivedVOs = initialScannedDetailVO.getArrivedMails();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		
		if(scannedArrivedVOs != null && scannedArrivedVOs.size() > 0){
		
		scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedArrivedVOs).get(0);
		Collection<MailbagVO> mailbagArriveVOs = scannedMailDetailsVO.getMailDetails();
		Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagCorrectFirstVOs = new ArrayList<MailbagVO>();
		
		try {
		if(mailbagArriveVOs != null && mailbagArriveVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagArriveVOs){
				
				if("EXPERR".equals(mailbagVO.getErrorType())){
					mailbagErrorVOs.add(mailbagVO);
				}else{
				
				if( mailbagVO.getCarrierCode() != null && !"".equals( mailbagVO.getCarrierCode())
					&& mailbagVO.getFlightNumber() != null && !"".equals(mailbagVO.getFlightNumber())
					&& !"-1".equals(mailbagVO.getFlightNumber())){
				
				if(mailbagVO.getSegmentSerialNumber() < 1){	
					String flightCarrierCode = mailbagVO.getCarrierCode().trim().toUpperCase();
					AirlineValidationVO airlineValidationVO = null;
			    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
			    		try {
			    			airlineValidationVO = airlineDelegate.validateAlphaCode(
			    					logonAttributes.getCompanyCode(),flightCarrierCode);
			    		}catch (BusinessDelegateException businessDelegateException) {
			    			errors = handleDelegateException(businessDelegateException);
			    		}
			    		if (errors != null && errors.size() > 0) {
			    			Object[] obj = {flightCarrierCode};
			    			errorsArrival.add(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
			    		}
			    	}
		    	if (errorsArrival == null || errorsArrival.size() == 0) {
					FlightFilterVO flightFilterVO = new FlightFilterVO();
					flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
					flightFilterVO.setStation(logonAttributes.getAirportCode());
					flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
					flightFilterVO.setDirection("I");
			 		flightFilterVO.setFlightDate(mailbagVO.getFlightDate());
					flightFilterVO.setCarrierCode(mailbagVO.getCarrierCode());
					flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
					Collection<FlightValidationVO> flightValidationVOs = null;
					log.log(Log.FINE, "FlightFilterVO ------------> ",
							flightFilterVO);
					flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
					
					FlightValidationVO flightValidationVO = new FlightValidationVO();
					log.log(Log.FINE, "flightValidationVOs ------------> ",
							flightValidationVOs);
					if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
						flightValidationVO = ((ArrayList<FlightValidationVO>)flightValidationVOs).get(0);
						mailbagVO.setCompanyCode(flightValidationVO.getCompanyCode());
						mailbagVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
						mailbagVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
						mailbagVO.setCarrierId(flightValidationVO.getFlightCarrierId());
						
						String route = flightValidationVO.getFlightRoute();
						String[] routeArr = route.split("-");
						Collection<String> routeColln = new ArrayList<String>();
						for(int index=0;index<routeArr.length;index++){
							if(routeArr[index].equals(logonAttributes.getAirportCode())){
								mailbagVO.setPol(routeArr[index-1]);
								break;
							}
							routeColln.add(routeArr[index]);
						}
	
						String pol = mailbagVO.getPol();
						if(pol != null){
							if(!routeColln.contains(pol)){
								mailbagVO.setErrorType("INVALIDPOL");
								mailbagVO.setErrorDescription("POL is invalid");
								mailbagErrorVOs.add(mailbagVO);
							}else{
								mailbagCorrectFirstVOs.add(mailbagVO);
							}
						}else{
							mailbagCorrectFirstVOs.add(mailbagVO);
						}
						
					}else{
	//					Object[] obj = {mailbagVO.getCarrierCode(),
	//							mailbagVO.getFlightNumber(),
	//							mailbagVO.getFlightDate().toString().substring(0,11)};
	//					ErrorVO errorVO = new ErrorVO("mail.operations.uploadmail.msg.err.invalidflight",obj);
	//					errors.add(errorVO);
						mailbagVO.setErrorType("INVALIDFLIGHT");
						mailbagVO.setErrorDescription("Invalid flight");
						mailbagErrorVOs.add(mailbagVO);
						log.log(Log.FINE,"invalidflight ------------> ");
				    }
		    	   }else{
		    		   mailbagErrorVOs.add(mailbagVO);
					}
				 }else{
						mailbagCorrectFirstVOs.add(mailbagVO);
					}
				}else{
//					Object[] obj = {mailbagVO.getMailbagId()};
//	    			ErrorVO errorVO = new ErrorVO("mail.operations.upload.noflightdetails",obj);
//					errors.add(errorVO);
					mailbagVO.setErrorType("NOFLIGHT");
					mailbagVO.setErrorDescription("No flight details ");
	    			mailbagErrorVOs.add(mailbagVO);
				}
			}
			}
		}
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		/**
		 * Validate POU
		 */
		Collection<MailbagVO> mailbagCorrectPOUVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		if(mailbagCorrectFirstVOs != null && mailbagCorrectFirstVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagCorrectFirstVOs){
				if( mailbagVO.getPou() != null && !"".equals( mailbagVO.getPou())){
					if(!logonAttributes.getAirportCode().equals(mailbagVO.getPou())){
						mailbagVO.setErrorType("INVALIDPOU");
						mailbagVO.setErrorDescription("POU is not the current port");
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectPOUVOs.add(mailbagVO);
					}
				}else{
					mailbagCorrectPOUVOs.add(mailbagVO);
				}
			}
		}

		/**
		 * Validate POL and container
		 */
		Collection<MailbagVO> mailbagCorrectSecondVOs = new ArrayList<MailbagVO>();
		if(mailbagCorrectPOUVOs != null && mailbagCorrectPOUVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagCorrectPOUVOs){
				if(mailbagVO.getContainerNumber() == null || "".equals(mailbagVO.getContainerNumber())
					|| mailbagVO.getPol() == null || "".equals(mailbagVO.getPol())){
					mailbagVO.setErrorType("CP");
					mailbagVO.setErrorDescription("No POL or Container attached to the mailbag");
					mailbagErrorVOs.add(mailbagVO);
				}else{
					mailbagCorrectSecondVOs.add(mailbagVO);
				}
			}
		}
		
		
		/**
		 * Validate container for destn assigned
		 */
		Collection<MailbagVO> mailbagCorrectContainerVOs = new ArrayList<MailbagVO>();
		try{
		if(mailbagCorrectSecondVOs != null && mailbagCorrectSecondVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagCorrectSecondVOs){
				if(mailbagVO.getSegmentSerialNumber() < 1){
					ContainerVO containerVO  = new ContainerVO();
		       		containerVO.setCompanyCode(logonAttributes.getCompanyCode());
		       		containerVO.setContainerNumber(mailbagVO.getContainerNumber().toUpperCase());
		       		containerVO.setAssignedPort(mailbagVO.getPol());

		       		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		       		containerAssignmentVO = new MailTrackingDefaultsDelegate().findContainerAssignment(containerVO);
		       		if(containerAssignmentVO != null){
		       			if(containerAssignmentVO.getFlightSequenceNumber() == -1){
		       				mailbagVO.setErrorType("DESTNASSIGNED");
							mailbagVO.setErrorDescription("mailbag in Container is Destination assigned");
							mailbagErrorVOs.add(mailbagVO);
		       			}else{
							mailbagCorrectContainerVOs.add(mailbagVO);
						}
		       		}else{
						mailbagCorrectContainerVOs.add(mailbagVO);
					}
				}else{
					mailbagCorrectContainerVOs.add(mailbagVO);
				}
			}
		}}catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
	 	}


		/**
		 * Validation 3
		 * validate mailbags - server call
		 */

		Collection<MailbagVO> mailbagCorrectThirdVOs = new ArrayList<MailbagVO>();
		try{
		if(mailbagCorrectContainerVOs != null && mailbagCorrectContainerVOs.size() > 0){
				mailbagVOs = new ArrayList<MailbagVO>();
				mailbagVOs = new MailTrackingDefaultsDelegate().validateScannedMailbagDetails(mailbagCorrectContainerVOs);
				for(MailbagVO mailbagVO:mailbagVOs){
					if(mailbagVO.getErrorType() != null  && !"Y".equals(mailbagVO.getAcknowledge())){
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectThirdVOs.add(mailbagVO);
					}
				}
		}
		}catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
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
		
	   if(mailbagCorrectThirdVOs != null && mailbagCorrectThirdVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagCorrectThirdVOs){
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


		Collection<MailbagVO> mailbagCorrectFourthVOs = new ArrayList<MailbagVO>();
		String catErr = "Mail category code is invalid";
		String hniErr = "HNI is invalid";
		String riErr = "RI is invalid";
		String errDesc = "";
		if(mailbagCorrectThirdVOs != null && mailbagCorrectThirdVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagCorrectThirdVOs){
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
						mailbagCorrectFourthVOs.add(mailbagVO);
					}
			}
		}
		

		int count = 0;
		if(mailbagCorrectFourthVOs != null && mailbagCorrectFourthVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagCorrectFourthVOs){
				String error = "";
				for(MailbagVO mailbagInner:mailbagCorrectFourthVOs){
					if(mailbagInner.getMailbagId().equals(mailbagVO.getMailbagId())){
						count++;
					}
				}
				if(count == 2){
					error = "DS";
				}
				if("".equals(error)){
					mailbagVO.setErrorType(null);
				}else{
					mailbagVO.setErrorType(error);
				}
				count = 0;
			}
		}

		Collection<MailbagVO> mailbagCorrectFifthVOs = new ArrayList<MailbagVO>();
		if(mailbagCorrectFourthVOs != null && mailbagCorrectFourthVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagCorrectFourthVOs){
					if(mailbagVO.getErrorType() != null){
						if("DS".equals(mailbagVO.getErrorType())){
							errDesc = "Duplicate Mailbags in Same container";
						}
						mailbagVO.setErrorDescription(errDesc);
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectFifthVOs.add(mailbagVO);
					}
			}
		}


		log.log(Log.FINE, "mailbagCorrectFifthVOs ...in command",
				mailbagCorrectFifthVOs);
		log.log(Log.FINE, "mailbagErrorVOs ...in command", mailbagErrorVOs);
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<MailArrivalVO> mailArrivalVOs = new ArrayList<MailArrivalVO>();
		HashMap<String,MailArrivalVO> mailArrivalMap = new HashMap<String,MailArrivalVO>();
		if(mailbagCorrectFifthVOs != null && mailbagCorrectFifthVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagCorrectFifthVOs){
				String pk = new StringBuilder().append(mailbagVO.getCompanyCode())
				   .append(mailbagVO.getCarrierId())
				   .append(mailbagVO.getFlightNumber())
		           .append(mailbagVO.getFlightSequenceNumber()).toString();
				if(mailArrivalMap.get(pk) == null){

				mailArrivalVO = new MailArrivalVO();
				mailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
				mailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
				mailArrivalVO.setCarrierId(mailbagVO.getCarrierId());
				mailArrivalVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
				mailArrivalVO.setFlightNumber(mailbagVO.getFlightNumber());
				mailArrivalVO.setFlightCarrierCode(mailbagVO.getCarrierCode());
				mailArrivalVO.setArrivalDate(mailbagVO.getFlightDate());
				mailArrivalVO.setLegSerialNumber(mailbagVO.getLegSerialNumber());
				mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
				mailArrivalVO.setScanned(true);

				containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
				Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
				HashMap<String,Collection<MailbagVO>> mailbagMap = new HashMap<String,Collection<MailbagVO>>();
				
				for(MailbagVO mailbagVOInner:mailbagCorrectFifthVOs){
					if(mailbagVO.getCompanyCode().equals(mailbagVOInner.getCompanyCode())
						&& mailbagVO.getCarrierId() == mailbagVOInner.getCarrierId()
						&& mailbagVO.getFlightNumber().equals(mailbagVOInner.getFlightNumber())
						&& mailbagVO.getFlightSequenceNumber() == mailbagVOInner.getFlightSequenceNumber()){
						
							String bagpk = mailbagVOInner.getContainerNumber();
							if(mailbagMap.get(bagpk) == null){
								mailVOs = new ArrayList<MailbagVO>();
								for(MailbagVO mailbagVOinner:mailbagCorrectFifthVOs){
									if(bagpk.equals(mailbagVOinner.getContainerNumber())){
										if(!mailVOs.contains(mailbagVOinner)) {
											mailVOs.add(mailbagVOinner);
										}
									}
								}
							}else{
								mailVOs = new ArrayList<MailbagVO>();
								mailVOs = mailbagMap.get(bagpk);
								for(MailbagVO mailbagVOinner:mailbagCorrectFifthVOs){
									if(bagpk.equals(mailbagVOinner.getContainerNumber())){
										if(!mailVOs.contains(mailbagVOinner)) {
											mailVOs.add(mailbagVOinner);
										}
									}
								}
							}
							log.log(Log.FINE, "bagpk..in command", bagpk);
							log.log(Log.FINE, "mailVOs..in command", mailVOs);
							mailbagMap.put(bagpk,mailVOs);
					}
				}

				for(String container:mailbagMap.keySet()){

						ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
						containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
						containerDetailsVO.setContainerNumber(container);
						containerDetailsVO.setFlightNumber(mailbagVO.getFlightNumber());
						containerDetailsVO.setFlightDate(mailbagVO.getFlightDate());
						containerDetailsVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
						containerDetailsVO.setDestination(logonAttributes.getAirportCode());
						containerDetailsVO.setPou(logonAttributes.getAirportCode());
						containerDetailsVO.setCarrierId(mailbagVO.getCarrierId());
						containerDetailsVO.setCarrierCode(mailbagVO.getCarrierCode());
						containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ARR);


						String pol = "";
						String contFlag = "";
						int segSerial = 0;
						mailVOs = mailbagMap.get(container);
						if(mailVOs != null && mailVOs.size() > 0){
							for(MailbagVO mailbagVOActual:mailVOs){
								mailbagVOActual.setCompanyCode(logonAttributes.getCompanyCode());
								mailbagVOActual.setDamageFlag("N");
								mailbagVOActual.setCarrierCode(mailbagVO.getCarrierCode());
								mailbagVOActual.setFlightDate(mailbagVO.getFlightDate());
								mailbagVOActual.setFlightNumber(mailbagVO.getFlightNumber());
								mailbagVOActual.setContainerNumber(container);
								mailbagVOActual.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
								mailbagVOActual.setCarrierId(mailbagVO.getCarrierId());
								mailbagVOActual.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
								mailbagVOActual.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
								mailbagVOActual.setUldNumber(container);
								//added by anitha
								if(mailbagVOActual.getOffloadedRemarks()!=null){
									containerDetailsVO.setRemarks(mailbagVOActual.getOffloadedRemarks());
								}
								mailbagVOActual.setArrivedFlag("Y");
								pol = mailbagVOActual.getPol();
								mailbagVOActual.setScannedPort(logonAttributes.getAirportCode());
								mailbagVOActual.setScannedUser(logonAttributes.getUserId().toUpperCase());
								if(0 < mailbagVOActual.getSegmentSerialNumber()){
									mailbagVOActual.setOperationalFlag("U");
								}else{
									mailbagVOActual.setOperationalFlag("I");
								}

								if(!"B".equals(mailbagVOActual.getContainerNumber().substring(0,1))){
								try{
							       		ContainerVO containerVO  = new ContainerVO();
							       		containerVO.setCompanyCode(logonAttributes.getCompanyCode());
							       		containerVO.setContainerNumber(mailbagVOActual.getContainerNumber().toUpperCase());
							       		containerVO.setAssignedPort(mailbagVOActual.getPol());

							       		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
							       		containerAssignmentVO = new MailTrackingDefaultsDelegate().findContainerAssignment(containerVO);
							       		if(containerAssignmentVO == null){
							       			contFlag = "I";
							       		}else{
							       			if(containerAssignmentVO.getCompanyCode().equals(mailbagVOActual.getCompanyCode())
													&& containerAssignmentVO.getCarrierId() == mailbagVOActual.getCarrierId()
													&& containerAssignmentVO.getFlightNumber().equals(mailbagVOActual.getFlightNumber())
													&& containerAssignmentVO.getFlightSequenceNumber() == mailbagVOActual.getFlightSequenceNumber()){
							       				segSerial = containerAssignmentVO.getSegmentSerialNumber();
							       				containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
							       				contFlag = "U";
							       			}else{
							       				contFlag = "I";
							       			}
							       		}
							       		
								}catch (BusinessDelegateException businessDelegateException) {
									errors = handleDelegateException(businessDelegateException);
								}
								//containerDetailsVO.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
							  }else{
								  contFlag = "U";
							  }
							}
					    }

						containerDetailsVO.setSegmentSerialNumber(segSerial);
						containerDetailsVO.setOperationFlag(contFlag);
						containerDetailsVO.setPol(pol);

						HashMap<String,DSNVO> dsnMap = new HashMap<String,DSNVO>();
						 if(mailVOs != null && mailVOs.size() > 0){
							for(MailbagVO mailbgVO:mailVOs){
								int numBags = 0;
								double bagWgt = 0;
								String outerpk = mailbgVO.getOoe()+mailbgVO.getDoe()
								           +(mailbgVO.getMailSubclass())
                                           + mailbgVO.getMailCategoryCode()
								           +mailbgVO.getDespatchSerialNumber()+mailbgVO.getYear();
								if(dsnMap.get(outerpk) == null){
									DSNVO dsnVO = new DSNVO();
									dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
									dsnVO.setDsn(mailbgVO.getDespatchSerialNumber());
									dsnVO.setOriginExchangeOffice(mailbgVO.getOoe());
									dsnVO.setDestinationExchangeOffice(mailbgVO.getDoe());
									dsnVO.setMailClass(mailbgVO.getMailSubclass().substring(0,1));
                                    dsnVO.setMailSubclass(mailbgVO.getMailSubclass());
                                    dsnVO.setMailCategoryCode(mailbgVO.getMailCategoryCode());
									dsnVO.setOperationFlag("U");
									dsnVO.setYear(mailbgVO.getYear());
									dsnVO.setPltEnableFlag("Y");
								for(MailbagVO innerVO:mailVOs){
									String innerpk = innerVO.getOoe()+innerVO.getDoe()
							           +(innerVO.getMailSubclass())
                                       + innerVO.getMailCategoryCode()
                                       +innerVO.getDespatchSerialNumber()+innerVO.getYear();
									if(outerpk.equals(innerpk)){
										numBags = numBags + 1;
										//bagWgt = bagWgt + innerVO.getWeight();
										bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();//added
									}
								}
								dsnVO.setReceivedBags(numBags);
								//dsnVO.setReceivedWeight(bagWgt);
								dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371

								dsnMap.put(outerpk,dsnVO);
								numBags = 0;
								bagWgt = 0;
								}
							}
						}
						 Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
						 int totBags = 0;
						 double totWgt = 0;
							for(String key:dsnMap.keySet()){
								DSNVO dsnVO = dsnMap.get(key);
								totBags = totBags + dsnVO.getReceivedBags();
								//totWgt = totWgt + dsnVO.getReceivedWeight();
								totWgt = totWgt + dsnVO.getReceivedWeight().getRoundedSystemValue();//added by A-7371
								newDSNVOs.add(dsnVO);
							}

						containerDetailsVO.setMailDetails(mailVOs);
						containerDetailsVO.setContainerType(mailbagVO.getContainerType());
						containerDetailsVO.setDsnVOs(newDSNVOs);
						containerDetailsVO.setReceivedBags(totBags);
						//containerDetailsVO.setReceivedWeight(totWgt);
						containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,totWgt));//added by A-7371
						containerDetailsVOs.add(containerDetailsVO);
				}
				log.log(Log.FINE, "containerDetailsVOs..in command",
						containerDetailsVOs);
				mailArrivalVO.setContainerDetails(containerDetailsVOs);
				mailArrivalVOs.add(mailArrivalVO);
				mailArrivalMap.put(pk,mailArrivalVO);
				}
			}
		}
		


	    log.log(Log.INFO, "mailArrivalVOs....to server.\n", mailArrivalVOs);
		Collection<ScannedMailDetailsVO> scannedVOs = new ArrayList<ScannedMailDetailsVO>();
		try {
			 scannedVOs = new MailTrackingDefaultsDelegate().saveScannedInboundMails(mailArrivalVOs);
        }catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
 	    }
	   
	  log.log(Log.INFO, "scannedVOs....from server.\n", scannedVOs);
	Collection<MailbagVO> mailbagsColl = new ArrayList<MailbagVO>();
	  if(scannedVOs != null && scannedVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedVOs){
				if(scannedOuter.getMailDetails() != null && scannedOuter.getMailDetails().size() > 0){
					mailbagsColl.addAll(scannedOuter.getMailDetails());
				}
			}
	  }
	  
	  if(mailbagsColl != null && mailbagsColl.size() > 0){
	      mailbagErrorVOs.addAll(mailbagsColl);
	  }
	  
	  scannedMailDetailsVO.setMailDetails(mailbagErrorVOs);
	  Collection<ScannedMailDetailsVO> scannedArrvVOs = new ArrayList<ScannedMailDetailsVO>();
	  scannedArrvVOs.add(scannedMailDetailsVO);
	  scannedDetailsVO.setArrivedMails(scannedArrvVOs);
	  
	  
	  	/**
		 * For Summary Details
		 */
		ScannedDetailsVO summaryVO = uploadMailSession.getScannedSummaryVO();
		Collection<ScannedMailDetailsVO> scannedSummaryVOs = summaryVO.getArrivedMails();
		Collection<ScannedMailDetailsVO> scannedArrVOs = scannedDetailsVO.getArrivedMails();
		
		if(scannedSummaryVOs != null && scannedSummaryVOs.size() > 0){
			String tempKeyOne="";
			String tempKeyTwo="";		
			for(ScannedMailDetailsVO scannedSummaryVO:scannedSummaryVOs){
				int unsaved = 0;
				tempKeyOne=scannedSummaryVO.getCarrierCode()+HYPHEN
						+scannedSummaryVO.getFlightNumber()+HYPHEN
						+scannedSummaryVO.getFlightDate();	
				if(scannedArrVOs != null && scannedArrVOs.size() > 0){
					for(ScannedMailDetailsVO scannedMailVO:scannedArrVOs){
						for(MailbagVO scannedArvdMailVO:scannedMailVO.getMailDetails()){
							tempKeyTwo=scannedArvdMailVO.getCarrierCode()+HYPHEN
									+scannedArvdMailVO.getFlightNumber()+HYPHEN
									+scannedArvdMailVO.getFlightDate();
							if(tempKeyOne.equals(tempKeyTwo)){
								unsaved++;
							}
						}
						scannedSummaryVO.setUnsavedBags(unsaved);
						scannedSummaryVO.setSavedBags(scannedSummaryVO.getScannedBags() - scannedSummaryVO.getUnsavedBags());	
						break;
					}
				}else{
					scannedSummaryVO.setUnsavedBags(unsaved);
					scannedSummaryVO.setSavedBags(scannedSummaryVO.getScannedBags() - scannedSummaryVO.getUnsavedBags());
				}
			}
		}

		summaryVO.setArrivedMails(scannedSummaryVOs);

		uploadMailSession.setScannedSummaryVO(summaryVO);
		
		}// end for scannedArrivedVOs != null

	  log.log(Log.INFO, "scannedDetailsVO....from server.\n", scannedDetailsVO);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		uploadMailForm.setDisableStat("SAVED");
		invocationContext.addAllError(errorsArrival);
		invocationContext.target = TARGET;
		log.exiting("SaveUploadArriveCommand","execute");

	}


}
