/*
 * ValidateUcmFlightCommand.java Created on Jul 20,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmflightexceptionlist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMFlightExceptionListSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMFlightExceptionListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to list the UCM error logs
 * @author A-1862
 */

public class ValidateUcmFlightCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListUcmFlightException Error Logs");
	private static final String MODULE = "uld.defaults";
	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID =
		"uld.defaults.ucmflightexceptionlist";
	 /*
     * Module name and Screen id for duplicate flight
     */
    private static final String SCREEN_ID_DUPFLIGHT = 
    					"flight.operation.duplicateflight";
    
    private static final String MODULE_NAME_FLIGHT = 
    					"flight.operation";
	private static final String SCREEN_OF_PARENT = "ucmExceptionFlight";
	/*
	 * The constant for out bound
	 */
	private static final String OPR_FLH_OUTBOUND = "O";
    
	private static final String VALIDATION_SUCCESS = "validation_success";
	private static final String VALIDATION_FAILURE = "validation_failure";
 
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	UCMFlightExceptionListForm ucmFlightExceptionListForm = 
			(UCMFlightExceptionListForm) invocationContext.screenModel;
		UCMFlightExceptionListSession ucmFlightExceptionListSession = 
			(UCMFlightExceptionListSession)getScreenSession(MODULE,SCREENID);
		ArrayList<UCMExceptionFlightVO> ucmExceptionFlightVOs =
			ucmFlightExceptionListSession.getUcmExceptionFlightVOs();
		
		if(ucmExceptionFlightVOs != null &&
				ucmExceptionFlightVOs.size() > 0) {
			int lastVosIndex = -1;		
			int noOfElements = ucmExceptionFlightVOs.size();
			for(int i = noOfElements - 1; i >= 0; i-- ) {
				if(!AbstractVO.OPERATION_FLAG_DELETE.equals(
						ucmExceptionFlightVOs.get(i).getOpeartionalFlag())) {
					lastVosIndex = i;
					break;
				}
			}
			if(lastVosIndex < 0) {
				invocationContext.target = VALIDATION_SUCCESS;
				return;
			}
			UCMExceptionFlightVO ucmExceptionFlightVO =
			ucmFlightExceptionListSession.getUcmExceptionFlightVOs().get(lastVosIndex);
			if(ucmExceptionFlightVO.getFlightSequenceNumber() > 0) {
				invocationContext.target = VALIDATION_SUCCESS; 
				return;
			}
			
			
		}
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();
		String  airportCode = ucmFlightExceptionListForm.getAirportCode().toUpperCase();
		/*
		 * Obtain the duplicate flight session
		 */
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		
		Collection<ErrorVO> errors = validateForm(ucmFlightExceptionListForm);
		if(errors != null &&
				errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATION_FAILURE;
			return;
		}
		/*
			String dates[] = ucmFlightExceptionListForm.getFlightDate();
	        String fltnums[] = ucmFlightExceptionListForm.getFlightNumber();
	        String fltcar[] = ucmFlightExceptionListForm.getFlightCarrier();
	        int i = 0;
	        
	        for(i=0;i<fltnums.length;i++) 
	        {
	        	for(UCMExceptionFlightVO ucmExceptionFlightVO:ucmExceptionFlightVOs)
	        	{	            
	            if(ucmExceptionFlightVO.getFlightDate() != null && ucmExceptionFlightVO.getFlightNumber() != null && 
	            		ucmExceptionFlightVO.getFlightNumber().length() > 0 && 
	            		ucmExceptionFlightVO.getCarrierCode() != null && ucmExceptionFlightVO.getCarrierCode().length() > 0)
	            {
	            	
	                if(!ucmExceptionFlightVO.getFlightDate().equals(dates[i]) ||
	                		!ucmExceptionFlightVO.getFlightNumber().equals(fltnums[i]) || 
	                		!ucmExceptionFlightVO.getCarrierCode().equals(fltcar[i]))
	                	{
	                    validateFlight(ucmFlightExceptionListForm,
	                    		compCode, airportCode, fltcar[i], 
	                    		fltnums[i], dates[i], invocationContext, 
	                    		duplicateFlightSession);
	                   
	                } else
	                {
	                    
	                }
	            i++;
	         }
	        }
	        }
	        */

		String[] carrierCodes = ucmFlightExceptionListForm.getFlightCarrier();
		String[] flightNumbers = ucmFlightExceptionListForm.getFlightNumber();
		String[] flightDates = ucmFlightExceptionListForm.getFlightDate();
		if(flightNumbers != null && flightNumbers.length > 0) {
			int lastIndex = flightDates.length - 1;
			validateFlight(ucmFlightExceptionListForm,
					compCode,airportCode,
					carrierCodes[lastIndex],
					flightNumbers[lastIndex],
					flightDates[lastIndex],
					invocationContext,
					duplicateFlightSession);
		}
		invocationContext.target = VALIDATION_SUCCESS;
        
    }
    /**
     * 
     * @param ucmErrorLogForm
     * @param companyCode
     * @return
     */
    
	private Collection<ErrorVO> validateForm
	(UCMFlightExceptionListForm ucmFlightExceptionListForm){
		log.entering("ListUCMErorLogCommand", "validateForm");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		String[] carrierCodes = ucmFlightExceptionListForm.getFlightCarrier();
		String[] flightNumbers = ucmFlightExceptionListForm.getFlightNumber();
		String[] flightDates = ucmFlightExceptionListForm.getFlightDate();
		if(flightNumbers != null && flightNumbers.length > 0) {
			int lastIndex = flightDates.length - 1;
			if(carrierCodes[lastIndex] == null || 
					carrierCodes[lastIndex].trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.ucmflightexceptionlist.flighcarriermandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(flightNumbers[lastIndex] == null || 
					flightNumbers[lastIndex].trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.ucmflightexceptionlist.flighnumbermandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(flightDates[lastIndex] == null || 
					flightDates[lastIndex].trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.ucmflightexceptionlist.flighdatemandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}	
		log.exiting("ListUCMErrorLogCommand", "validateForm");
		return errors;
	}
    /**
	 * 
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param invocationContext
	 * @param duplicateFlightSession
	 * @return
	 */
	private boolean validateFlight(
			UCMFlightExceptionListForm ucmFlightExceptionListForm,
			String companyCode, String airportCode,
			String carrierCode,
			String flightNumber,
			String flightDate,
			InvocationContext invocationContext,
			DuplicateFlightSession duplicateFlightSession) {
		log.entering("ListCommand","validateFlight");
		boolean isValidFlight = true;
		String alphaCode = carrierCode.toUpperCase();
		AirlineValidationVO airlineValidationVO = populateAirlineValidationVO(
				companyCode, alphaCode, invocationContext);
		if(airlineValidationVO == null) {
			isValidFlight = false;
		}
		else {
			Collection<FlightValidationVO> flightValidationVOs =
				populateFlightValidationVO(companyCode, airportCode,
						flightNumber, flightDate,  
					airlineValidationVO, invocationContext,
					OPR_FLH_OUTBOUND);
			/*
			 * If no error and flightValidationVOs is not null
			 */
			if(flightValidationVOs != null && flightValidationVOs.size() > 0){
				/*
				 * If the size of flightValidationVOs is 1
				 * then obtain the flightValidationVO from
				 * the first element of the collection
				 */
				if(flightValidationVOs.size() == 1){
					duplicateFlightSession.setFlightValidationVO(
				   ((ArrayList<FlightValidationVO>)flightValidationVOs).get(0));
				}
				/*
				 * If there are more than one element in the collection
				 * then display the duplicate flight popup
				 */
				else if(flightValidationVOs.size() > 1){
					//To be reviewed : Display the duplicate flight popup
					log.log(Log.INFO,"###### Duplicate Exist####");
					duplicateFlightSession.setFlightValidationVOs((
							ArrayList<FlightValidationVO>)flightValidationVOs);
					duplicateFlightSession.setParentScreenId(SCREENID);
					//duplicateFlightSession.setScreenOfParent(SCREEN_OF_PARENT);
					ucmFlightExceptionListForm.setDuplicateFlightStatus(FlightValidationVO.FLAG_YES);
					isValidFlight = false;
				}
			}
			else {
				log.log(Log.FINE, "flightValidationVOs is NULL");
				isValidFlight = false;
			}
		}
		log.exiting("ListCommand","validateFlight");
		return isValidFlight;
		
	}
	
	/**
	 * Method to get the AirlineValidationVO
	 * @param companyCode
	 * @param alphaCode
	 * @param invocationContext
	 * @return airlineValidationVO
	 */
	private AirlineValidationVO populateAirlineValidationVO(String companyCode,
			String alphaCode, InvocationContext invocationContext){
		log.entering("ListCommand","populateAirlineValidationVO");
		AirlineDelegate airlineDelegate = new AirlineDelegate();

		AirlineValidationVO airlineValidationVO = null;
		Collection<ErrorVO> errors = null;

		try {
			airlineValidationVO =
				airlineDelegate.validateAlphaCode(companyCode, alphaCode);

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			e.getMessage();
		}

		if (errors != null && errors.size() > 0){
			ErrorVO error = new ErrorVO(
				"uld.defaults.ucmflightexceptionlist.invalidcarriercode",
				new Object[]{alphaCode});
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
		}
		log.exiting("ListCommand","populateAirlineValidationVO");
		return airlineValidationVO;
	}
	
	/**
	 * Validate the flight and if the flight is valid ,
	 * returns the collection of flightvalidation vos
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param airlineValidationVO
	 * @param invocationContext
	 * @param direction
	 * @return
	 */
	private Collection<FlightValidationVO> populateFlightValidationVO(
			String companyCode,	String airportCode, 
			String flightNumber,String flightDate,
			AirlineValidationVO airlineValidationVO,
			InvocationContext invocationContext,String direction){

			log.entering("ListCommand","populateFlightValidationVO");
			Collection<ErrorVO> errors = null;
			/*
			 * Populate the flightFilterVo
			 */
			FlightFilterVO flightFilterVO =
				getFlightFilterVO(companyCode, airportCode,
						flightNumber,flightDate,
						direction, airlineValidationVO);
			log.log(Log.FINE, "\nflightFilterVO ---> ", flightFilterVO);
			/*
			 * Validate flight - obtain the flightValidationVO
			 */
			ULDDefaultsDelegate delegate =
									new ULDDefaultsDelegate();
			Collection<FlightValidationVO> flightValidationVOs = null;
			try {
				flightValidationVOs =
					delegate.fetchFlightDetails(flightFilterVO);
				log.log(Log.FINE, "flightValidationVOs --> ",
						flightValidationVOs);
			} catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
			/*
			 * If error is present then set it to the
			 * invocationContext and return
			 */
			if(flightValidationVOs == null || flightValidationVOs.size() == 0){
				if(errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
				}
				else {
					 ErrorVO error = new ErrorVO (
					"uld.defaults.ucmflightexceptionlist.flightnotpresent");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					// errors.add(error);
					invocationContext.addError(error);
				}
			}
			log.exiting("ListCommand","populateFlightValidationVO");
			return flightValidationVOs;
     }
	
	/**
	 * Methd to get the Flight FilterVO
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param direction
	 * @param airlineValidationVO
	 * @return
	 */			
	private FlightFilterVO getFlightFilterVO(
			String companyCode, String airportCode,
			String flightNumber,String flightDate, String direction,
				AirlineValidationVO airlineValidationVO){
		log.entering("ListCommand", "getFlightFilterVO");
		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setStation(airportCode);
		flightFilterVO.setCompanyCode(companyCode);
		flightFilterVO.setFlightNumber(
				flightNumber.toUpperCase());
		flightFilterVO.setFlightCarrierId(
				airlineValidationVO.getAirlineIdentifier());

		if(flightDate != null 
				&& flightDate.trim().length() > 0) {
			LocalDate flightTmpDate =
				new LocalDate(airportCode,Location.ARP,false) ;
			flightFilterVO.setFlightDate(
					flightTmpDate.setDate(flightDate));
		}
		flightFilterVO.setDirection(direction);

		log.exiting("ListCommand", "getFlightFilterVO");
		return flightFilterVO;
	}
}
