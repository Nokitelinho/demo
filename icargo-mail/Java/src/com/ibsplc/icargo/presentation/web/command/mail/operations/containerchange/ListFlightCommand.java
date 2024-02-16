/*
 * ListFlightCommand.java Created on Jul 1 2016
 *
 * Copyright 2016 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.containerchange;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *@author a-5991
 * Command class for List FlightDetails.
 *
 * Revision History
 *

 */
public class ListFlightCommand extends BaseCommand {
    /**
     * Log
     */
    private Log log = LogFactory.getLogger("MAILOPERATIONS");

    
    
    private static final String MODULE_NAME = "mail.operations";	
    
    private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
    
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
    private static final String SCREENLOAD_SUCCESS = "success";
    
    /**
     * Target string
     */
    private static final String SCREENLOAD_FAILURE = "failure";
    
    /**
     * Target string
     */
    private static final String DUPLICATE_SUCCESS = "duplicate";
    
    /**
     *  Status of flag
     */
    private static final String INBOUND = "I";
    
    private static final String  INVALID_CARRIER="mailtracking.defaults.invalidcarrier";
    private static final String  NO_FLIGHT_DETAILS="mailtracking.defaults.noflightdetails";
    private static final String  FLIGHT_CARRIER_CODE_EMPTY="mailtracking.defaults.flightcarriercode.empty";
    private static final String  FLIGHT_NUMBER_EMPTY="mailtracking.defaults.flightnumber.empty";
    private static final String  DEPDATE_EMPTY="mailtracking.defaults.flightdate.empty";
    private static final String  FLIGHTINTBCORTBA="mailtracking.defaults.changeflight.flightintbcortba";
    private static final String  CANCELLED_FLIGHT="mailtracking.defaults.consignment.err.flightcancelled";
    private static final String  IS_SAME_FLIGHT="mailtracking.defaults.changeflight.issameflight";
    
    private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";

    /**
     * Execute method
     * @param invocationContext InvocationContext
     * @throws CommandInvocationException
     */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering("ContainerChangeListCommand", "execute");
        MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
        MailArrivalSession mailArrivalSession = getScreenSession(
			MODULE_NAME, SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
		
		mailArrivalSession.setFlightMailArrivalFilterVO(null);
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		mailArrivalVO.setAirportCode(mailArrivalForm.getArrivalPort());
		mailArrivalVO.setMailStatus(mailArrivalForm.getMailStatus());
		
		FlightValidationVO parentFlightValidationVO=mailArrivalSession.getFlightValidationVO();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		mailArrivalForm.setDuplicateFlightStatus(FLAG_NO);
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    AirlineDelegate airlineDelegate = new AirlineDelegate();
	    
	    	errors = validateFormFlight(mailArrivalForm);
	    	if (errors != null && errors.size() > 0) {  
	    		invocationContext.addAllError(errors);
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
	    	}
	    	
	    	
    	FlightFilterVO flightFilterVO = handleFlightFilterVO(
    			mailArrivalForm,logonAttributes);	
    	String flightNum = (mailArrivalForm.getFltNumber().toUpperCase());
		
    	AirlineValidationVO airlineValidationVO = null;
	    String flightCarrierCode = mailArrivalForm.getFltCarrierCode();        	
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
						INVALID_CARRIER,obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
    			invocationContext.addAllError(errors);
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
    		}
    	}
		
		/*******************************************************************
		 * validate Flight 
		 ******************************************************************/
    	flightFilterVO.setCarrierCode(flightCarrierCode.toUpperCase());
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		flightFilterVO.setFlightNumber(flightNum);
		Collection<FlightValidationVO> flightValidationVOs = null;
		try {
			log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
			flightValidationVOs =
				mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			Object[] obj = {mailArrivalForm.getFltCarrierCode(),
					mailArrivalForm.getFltNumber(),
					mailArrivalForm.getArrDate().toString().substring(0,11)};
			errors.add(new ErrorVO(NO_FLIGHT_DETAILS,obj));
			if(errors != null && errors.size() >0){
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		} 
		 else if ( flightValidationVOs.size() == 1) {
				log.log(Log.FINE, "flightValidationVOs has one VO");
				try {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						BeanHelper.copyProperties(flightValidationVO,
								flightValidVO);
						// Added by A-5153 for BUG_ICRD-90139
						if(FlightValidationVO.FLT_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
								FlightValidationVO.FLT_STATUS_TBC.equals(flightValidationVO.getFlightStatus())){
							if(!FlightValidationVO.FLAG_YES.equals(mailArrivalForm.getWarningOveride())){
							ErrorVO err = new ErrorVO(FLIGHTINTBCORTBA);
							err.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(err);
							invocationContext.target = SCREENLOAD_FAILURE;
							mailArrivalForm.setWarningFlag(FLIGHT_TBC_TBA);
							return;
							}else{
								mailArrivalForm.setWarningFlag("");
								mailArrivalForm.setWarningOveride(null);
							}
							mailArrivalForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
						}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus())){
							Object[] obj = {flightCarrierCode.toUpperCase(),flightValidationVO.getFlightNumber()};
							ErrorVO err = new ErrorVO(CANCELLED_FLIGHT,obj);                   
							err.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(err);
							invocationContext.target = SCREENLOAD_FAILURE;                   
							return;
						}
						else if(parentFlightValidationVO.getFlightNumber().equals(flightValidationVO.getFlightNumber())&&(parentFlightValidationVO.getFlightSequenceNumber()==flightValidationVO.getFlightSequenceNumber())){
							ErrorVO err = new ErrorVO(IS_SAME_FLIGHT);                   
							err.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(err);
							invocationContext.target = SCREENLOAD_FAILURE;                   
							return;
						}
						
						break;
					}
				} catch (SystemException systemException) {
					systemException.getMessage();
				}
				flightValidationVO.setDirection(INBOUND);
				mailArrivalForm.setListFlag("");
				mailArrivalSession.setChangeFlightValidationVO(flightValidationVO);
				log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
				
				operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
				operationalFlightVO.setPol(flightValidationVO.getLegOrigin());
				
		    	
				operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
				operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
				operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
				operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				operationalFlightVO.setDirection(INBOUND);
			
			  log.log(Log.FINE, "operationalFlightVO in MA session...",
					operationalFlightVO);
			try {
				  containerVOs = new MailTrackingDefaultsDelegate().findFlightAssignedContainers(operationalFlightVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
	    		invocationContext.target = SCREENLOAD_FAILURE;
	    		return;
	    	  }
	    	  mailArrivalSession.setContainerVOs(containerVOs);
				invocationContext.target = SCREENLOAD_SUCCESS;
			}else {
			duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)flightValidationVOs);
			duplicateFlightSession.setParentScreenId(SCREEN_ID);
			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
			mailArrivalForm.setDuplicateFlightStatus(FLAG_YES);
			 invocationContext.target =DUPLICATE_SUCCESS;
		   }
		
		 log.exiting("ContainerChangeListCommand", "execute");
    }
    
	   
    
	/**
	 * Method to validate form in FLIGHT MODE.
	 * @param reassignMailForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormFlight(
			MailArrivalForm mailArrivalForm) {
		String flightCarrierCode = mailArrivalForm.getFltCarrierCode();
		String flightNumber = mailArrivalForm.getFltNumber();
		String depDate = mailArrivalForm.getArrDate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(flightCarrierCode == null || ("".equals(flightCarrierCode.trim()))){
			errors.add(new ErrorVO(FLIGHT_CARRIER_CODE_EMPTY));
		}
		if(flightNumber == null || ("".equals(flightNumber.trim()))){
			errors.add(new ErrorVO(FLIGHT_NUMBER_EMPTY));
		}
		if(depDate == null || ("".equals(depDate.trim()))){
			errors.add(new ErrorVO(DEPDATE_EMPTY));
		}
		return errors;
	}
	
    
	/**
     * Method to create the filter vo for flight validation
     * @param mailArrivalForm
     * @param logonAttributes
     * @return FlightFilterVO
     */
    private FlightFilterVO handleFlightFilterVO(
    		MailArrivalForm mailArrivalForm,
			LogonAttributes logonAttributes){

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(INBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setStringFlightDate(mailArrivalForm.getArrivalDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(mailArrivalForm.getArrDate()));
		return flightFilterVO;
	}
		

}
