/*
 * SaveUploadOffloadCommand.java Created on Sept 22, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;


import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
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
 * @author A-2107
 *
 */
public class SaveUploadOffloadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	
	private static final String TARGET = "success";
	
	private static final String SAVED ="SAVED";
	
		
	/** 
	 * The execute method for SaveUploadOffloadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
		log.entering("UploadCommand","execute");
		UploadMailForm uploadMailForm 
			= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errorsOffload = new ArrayList<ErrorVO>();
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		if(uploadMailForm.getEnablemode()){
			ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
			ScannedDetailsVO returnscanneddetailsVO = CompareScannedDateCommand.checkscannedDetailsVO(scannedDetailsVO,"OFL");
			uploadMailSession.setScannedDetailsVO(returnscanneddetailsVO);
		}
		
		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		Collection<ScannedMailDetailsVO> scannedOffloadVOs = scannedDetailsVO.getOffloadMails();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
	
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		
		if(scannedOffloadVOs != null && scannedOffloadVOs.size() > 0){
			scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedOffloadVOs).get(0);
			Collection<MailbagVO> mailbagOffloadVOs = scannedMailDetailsVO.getMailDetails();
			Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
			Collection<MailbagVO> mailbagCorrectFirstVOs = new ArrayList<MailbagVO>();
			Collection<MailbagVO> mailbagVOValidateFlight = new ArrayList<MailbagVO>();
			
			/**
			 * Validation 1 :-
			 * Validating Flight
			 */
			for(MailbagVO mailbagVO:mailbagOffloadVOs){
				if("EXPERR".equalsIgnoreCase(mailbagVO.getErrorType())){
					mailbagErrorVOs.add(mailbagVO);
				}else{
					if( mailbagVO.getCarrierCode() != null && !"".equals( mailbagVO.getCarrierCode())
							&& mailbagVO.getFlightNumber() != null && !"".equals(mailbagVO.getFlightNumber())
							&& !"-1".equals(mailbagVO.getFlightNumber())){
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
				    			errorsOffload.add(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
				    		}
				    	}
				    	
				    	if (errorsOffload == null || errorsOffload.size() == 0) {
							FlightFilterVO flightFilterVO = new FlightFilterVO();
							flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
							flightFilterVO.setStation(logonAttributes.getAirportCode());
							flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
							flightFilterVO.setDirection("O");
					 		flightFilterVO.setFlightDate(mailbagVO.getFlightDate());
							flightFilterVO.setCarrierCode(mailbagVO.getCarrierCode());
							flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
							Collection<FlightValidationVO> flightValidationVOs = null;
							try {
								flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
							}catch (BusinessDelegateException businessDelegateException) {
				    			errors = handleDelegateException(businessDelegateException);
				    		}
							FlightValidationVO flightValidationVO = new FlightValidationVO();
							if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
								flightValidationVO = ((ArrayList<FlightValidationVO>)flightValidationVOs).get(0);
								mailbagVO.setCompanyCode(flightValidationVO.getCompanyCode());
								mailbagVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
								mailbagVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
								mailbagVO.setCarrierId(flightValidationVO.getFlightCarrierId());
								mailbagVOValidateFlight.add(mailbagVO);
							}else{
								mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
								mailbagVO.setErrorDescription("Invalid flight");
								mailbagErrorVOs.add(mailbagVO);
								log.log(Log.FINE,"invalidflight ------------> ");
						    }
				    	}
				   }else{
						mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
						mailbagVO.setErrorDescription("No flight details ");
						mailbagErrorVOs.add(mailbagVO);
					}
				}	
			}
			/**
			 * Validation 2:-
			 * validate mailbags - server call
			 */
			Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		
			try{
		        mailbagVOs = new ArrayList<MailbagVO>();
				mailbagVOs = new MailTrackingDefaultsDelegate().validateScannedMailbagDetails(mailbagVOValidateFlight);
				for(MailbagVO mailbagVO:mailbagVOs){
					if(mailbagVO.getErrorType() != null  && !"Y".equals(mailbagVO.getAcknowledge())){
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectFirstVOs.add(mailbagVO);
					}
				}
			}catch (BusinessDelegateException businessDelegateException) {
	 			errors = handleDelegateException(businessDelegateException);
		 	}
			

			/**
			 * Validation 3:-
			 * validate mailbags  - with onetime
			 */
		   Collection<OneTimeVO> catVOs = uploadMailSession.getOneTimeCat();
	       Collection<OneTimeVO> hniVOs = uploadMailSession.getOneTimeHNI();
	       Collection<OneTimeVO> riVOs = uploadMailSession.getOneTimeRI();
	       			
	       int onetimeerror = 0;
			for(MailbagVO mailbagVO:mailbagCorrectFirstVOs){
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
			Collection<MailbagVO> mailbagCorrectSecondVOs = new ArrayList<MailbagVO>();
			String catErr = "Mail category code is invalid";
			String hniErr = "HNI is invalid";
			String riErr = "RI is invalid";
			String errDesc = "";
			for(MailbagVO mailbagVO:mailbagCorrectFirstVOs){
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
					mailbagCorrectSecondVOs.add(mailbagVO);
				}
			}
			
			
			/**
			 * Validation 4:-
			 * validate duplicate mailbags in clientend
			 */
		
			  int count = 0;
		        Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
		       for(MailbagVO mailbagOuter:mailbagCorrectSecondVOs){
					String error = "";
					for(MailbagVO mailbagInner:mailbagCorrectSecondVOs){
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
				
		
				Collection<MailbagVO> mailbagCorrectThirdVOs = new ArrayList<MailbagVO>();
				for(MailbagVO mailbagVO:mailbagCorrectSecondVOs){
					if(mailbagVO.getErrorType() != null){
						if("DS".equals(mailbagVO.getErrorType())){
							errDesc = "Duplicate Mailbags present";
						}
						mailbagVO.setErrorDescription(errDesc);
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectThirdVOs.add(mailbagVO);
					}
				}
		
		
		
		Collection<ScannedMailDetailsVO> scannedOffloadAfterVOs = new ArrayList<ScannedMailDetailsVO>();
		if(mailbagCorrectThirdVOs != null && mailbagCorrectThirdVOs.size() > 0){
			try {
				scannedOffloadAfterVOs = new MailTrackingDefaultsDelegate().offloadScannedMailbags(mailbagCorrectThirdVOs);
	        }catch (BusinessDelegateException businessDelegateException) {
	 			errors = handleDelegateException(businessDelegateException);
	 	    }
		}
		
		Collection<MailbagVO> mailbagsColl = new ArrayList<MailbagVO>();
		if(scannedOffloadAfterVOs != null && scannedOffloadAfterVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedOffloadAfterVOs){
				if(scannedOuter.getMailDetails() != null && scannedOuter.getMailDetails().size() > 0){
					mailbagsColl.addAll(scannedOuter.getMailDetails());
				}
			}
		}
		  
		if(mailbagsColl != null && mailbagsColl.size() > 0){
		      mailbagErrorVOs.addAll(mailbagsColl);
		}
		  
		  scannedMailDetailsVO.setMailDetails(mailbagErrorVOs);
		  Collection<ScannedMailDetailsVO> scannedOffVOs = new ArrayList<ScannedMailDetailsVO>();
		  scannedOffVOs.add(scannedMailDetailsVO);
		  scannedDetailsVO.setOffloadMails(scannedOffVOs);
		  
		}
		
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		uploadMailForm.setDisableStat(SAVED);
		invocationContext.target = TARGET;
		log.exiting("UploadCommand","execute");
		  
	
	}
}

	
	
	


