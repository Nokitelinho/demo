/*
 * ListFlightCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for List FlightDetails.
 *
 * Revision History
 *
 * Version      Date        Author         Description
 *
 *  0.1         Jul 1 2016  A-5991         Coding
 */
public class ListFlightCommand extends BaseCommand {
    /**
     * Log
     */
    private Log log = LogFactory.getLogger("MAILOPERATIONS");

    
    
    private static final String MODULE_NAME = "mail.operations";	
    
    private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";	
    
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
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
    /**
     * Target string
     */
    private static final String SCREENLOAD_FAILURE = "screenload_failure";
    
    /**
     * Target string
     */
    private static final String DUPLICATE_SUCCESS = "duplicate_success";
    
    /**
     *  Status of flag
     */
    private static final String OUTBOUND = "O";
    
    /** The Constant FLIGHT_TBC_TBA. */
    private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";
    

    /**
     * Execute method
     * @param invocationContext InvocationContext
     * @throws CommandInvocationException
     */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering("ListFlightCommand", "execute");
        MailManifestForm mailManifestForm = 
    		(MailManifestForm)invocationContext.screenModel;
        MailManifestSession mailManifestSession = getScreenSession(
			MODULE_NAME, SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
		
		mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
		
		MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();
		if(!MailConstantsVO.OPERATION_OUTBOUND.equals(mailManifestForm.getFromScreen())){
		mailManifestVO.setDepPort(mailManifestForm.getDeparturePort());
		}
		
		mailManifestForm.setDuplicateFlightStatus(FLAG_NO);
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    AirlineDelegate airlineDelegate = new AirlineDelegate();
	    /**
	     * for QF 1258
	     */
	    if(MailConstantsVO.OPERATION_OUTBOUND.equals(mailManifestForm.getFromScreen())){
	    	if(mailManifestVO.getFlightCarrierCode()!=null &&mailManifestVO.getFlightCarrierCode().trim().length()>0){
	    		mailManifestForm.setFlightCarrierCode(mailManifestVO.getFlightCarrierCode());
	    	}
	    	if(mailManifestVO.getFlightNumber()!=null && mailManifestVO.getFlightNumber().trim().length()>0){
	    		mailManifestForm.setFlightNumber(mailManifestVO.getFlightNumber());
	    	}
	    	if(mailManifestVO.getDepDate()!=null){
	    		mailManifestForm.setDepDate(mailManifestVO.getDepDate().toDisplayDateOnlyFormat());
	    	}
	    	if(mailManifestVO.getDepPort()!=null){
	    		mailManifestForm.setDeparturePort(mailManifestVO.getDepPort());
	    	}
	    }
    	mailManifestVO.setStrDepDate(mailManifestForm.getDepDate());	
	    String flightNum = (mailManifestForm.getFlightNumber().toUpperCase());
	    /**
	     * for QF 1258
	     */
	    if(!MailConstantsVO.OPERATION_OUTBOUND.equals(mailManifestForm.getFromScreen())){
    	mailManifestVO.setFlightNumber(flightNum);
    	mailManifestVO.setFlightCarrierCode(mailManifestForm.getFlightCarrierCode().toUpperCase());

	    }
    	mailManifestSession.setMailManifestVO(mailManifestVO);
    	errors = validateFormFlight(mailManifestForm);
    	if (errors != null && errors.size() > 0) {  
    		invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
    	}
    	FlightFilterVO flightFilterVO = handleFlightFilterVO(
    			mailManifestForm,logonAttributes);
    	mailManifestVO.setDepDate(flightFilterVO.getFlightDate());	
    	AirlineValidationVO airlineValidationVO = null;
	    String flightCarrierCode = mailManifestForm.getFlightCarrierCode().trim().toUpperCase();        	
    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
    		try {        			
    			airlineValidationVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),
    					flightCarrierCode);

    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {            			
    			Object[] obj = {flightCarrierCode};
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
    		}
    	}
		
		/*******************************************************************
		 * validate Flight 
		 ******************************************************************/
    	flightFilterVO.setCarrierCode(flightCarrierCode);
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		mailManifestVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
		flightFilterVO.setFlightNumber(flightNum);
		// Added by A-1868 on 15Nov10 for Bug 102837 - starts
		//flightFilterVO.setActiveAlone(true);
		// Added by A-1868 on 15Nov10 for Bug 102837 - ends
		Collection<FlightValidationVO> flightValidationVOs = null;
		try {
			log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
			flightValidationVOs =
				mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		Collection<FlightValidationVO> tempFlightValidationVOs = new ArrayList<FlightValidationVO>();
		 //Added by A-5160 for ICRD-92869 starts
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
			for (FlightValidationVO flightValidVO : flightValidationVOs) {
				flightValidVO.setDirection(OUTBOUND);
				try {
					FlightValidationVO tempFlightValidationVO = new FlightValidationVO();
					BeanHelper.copyProperties(tempFlightValidationVO,
							flightValidVO);
					tempFlightValidationVOs.add(tempFlightValidationVO);
				} catch (SystemException e) {
					e.getMessage();
				}
				if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ||
						FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())){
					flightValidVO.setFlightRoute(null);
					flightValidVO.setAircraftType(null);
					flightValidVO.setEta(null);
					flightValidVO.setEtd(null);
					flightValidVO.setSta(null);
					flightValidVO.setStd(null);
					flightValidVO.setAta(null);
					flightValidVO.setAtd(null);
				}
			}
		}
		 //Added by A-5160 for ICRD-92869 ends
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
			mailManifestSession.setMailManifestVO(mailManifestVO);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			Object[] obj = {mailManifestVO.getFlightCarrierCode(),
					mailManifestVO.getFlightNumber(),
					mailManifestVO.getDepDate().toString().substring(0,11)};
			//Added by A-7794 as part of ICRD-197439 ; to reset the containerVO 
			Collection<ContainerDetailsVO> containerVO = new ArrayList<ContainerDetailsVO>();
			mailManifestVO.setContainerDetails(containerVO);
			invocationContext.addError(new ErrorVO("mailtracking.defaults.noflightdetails",obj));
			invocationContext.target = SCREENLOAD_FAILURE;
		} else if ( flightValidationVOs.size() == 1) {
			log.log(Log.FINE, "flightValidationVOs has one VO");
			try {
				for (FlightValidationVO flightValidVO : flightValidationVOs) {					
					//A-5249 from ICRD-84046					
					if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ||
							FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())){
						if(!FlightValidationVO.FLAG_YES.equals(mailManifestForm.getWarningOveride())){
						ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
						err.setErrorDisplayType(ErrorDisplayType.WARNING);
						invocationContext.addError(err);
						invocationContext.target = SCREENLOAD_FAILURE;
						mailManifestForm.setWarningFlag(FLIGHT_TBC_TBA);
						return;
						}else{
							mailManifestForm.setWarningFlag("");
							mailManifestForm.setWarningOveride(null);
						}
						mailManifestForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
					}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus())){
						Object[] obj = {flightCarrierCode.toUpperCase(),flightValidVO.getFlightNumber()};
						ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
						err.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(err);
						invocationContext.target = SCREENLOAD_FAILURE;
						return;
					} 
					
					BeanHelper.copyProperties(flightValidationVO,
							flightValidVO);
					break;
				}
			} catch (SystemException systemException) {
				systemException.getMessage();
			}
			flightValidationVO.setDirection("O");
			mailManifestSession.setFlightValidationVO(flightValidationVO);
			log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
			mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		    invocationContext.target = SCREENLOAD_SUCCESS;
		}else {
			duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)tempFlightValidationVOs);
			duplicateFlightSession.setParentScreenId(SCREEN_ID);
			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
			mailManifestForm.setDuplicateFlightStatus(FLAG_YES);
			 invocationContext.target =DUPLICATE_SUCCESS;
			 if(MailConstantsVO.FLAG_YES.equals(mailManifestForm.getDuplicateAndTbaTbc())){
   				 invocationContext.target =SCREENLOAD_SUCCESS; 
   			 }
		   }
		mailManifestSession.setMailManifestVO(mailManifestVO);
	    
		  log.exiting("ListFlightCommand", "execute");
    }
	   
    
	/**
	 * Method to validate form in FLIGHT MODE.
	 * @param mailManifestForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormFlight(
			MailManifestForm mailManifestForm) {
		String flightCarrierCode = mailManifestForm.getFlightCarrierCode();
		String flightNumber = mailManifestForm.getFlightNumber();
		String depDate = mailManifestForm.getDepDate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(flightCarrierCode == null || ("".equals(flightCarrierCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.flightcarriercode.empty"));
		}
		if(flightNumber == null || ("".equals(flightNumber.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.flightnumber.empty"));
		}
		if(depDate == null || ("".equals(depDate.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.depdate.empty"));
		}
		return errors;
	}
	
    
       /**
	     * Method to create the filter vo for flight validation
	     * @param mailManifestForm
	     * @param logonAttributes
	     * @return FlightFilterVO
	     */
	    private FlightFilterVO handleFlightFilterVO(
	    		MailManifestForm mailManifestForm,
				LogonAttributes logonAttributes){

			FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			//Modified by A-7794 as part of ICRD-197439
			
			flightFilterVO.setDirection(OUTBOUND);
			flightFilterVO.setActiveAlone(false);
			flightFilterVO.setStringFlightDate(mailManifestForm.getDepDate());
			//Modified by A-7794 as part of ICRD-197439
			//Modified by A-7794 as part of ICRD-261318
			LocalDate date;
			if(mailManifestForm.getDeparturePort() != null && !(mailManifestForm.getDeparturePort().length() == MailConstantsVO.ZERO)){
				date = new LocalDate(mailManifestForm.getDeparturePort(),ARP,false);
				flightFilterVO.setStation(mailManifestForm.getDeparturePort());
			}else{
				date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
				flightFilterVO.setStation(logonAttributes.getAirportCode());
			}
	 		
	 		flightFilterVO.setFlightDate(date.setDate(
	 				mailManifestForm.getDepDate()));
			return flightFilterVO;
		}
		
		/**
		 * This method is to format flightNumber
		 * Not using accoding to the CRQ-AirNZ989-12
		 * @param flightNumber 
		 * @return String
		 */
	/*	private String formatFlightNumber(String flightNumber){
		int numLength = flightNumber.length();
		String newFlightNumber = "" ;
        if(numLength == 1) { 
        	newFlightNumber = new  StringBuilder("000").append(flightNumber).toString();
        }
        else if(numLength == 2) {
        	newFlightNumber = new  StringBuilder("00").append(flightNumber).toString();
        }
        else if(numLength == 3) { 
        	newFlightNumber = new  StringBuilder("0").append(flightNumber).toString();
        }
        else {
        	newFlightNumber = flightNumber ;
        }
			return newFlightNumber;
		}*/
}
