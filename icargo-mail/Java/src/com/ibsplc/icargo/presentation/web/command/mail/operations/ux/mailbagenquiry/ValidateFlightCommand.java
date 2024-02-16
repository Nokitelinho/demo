/*
 * ValidateFlightCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */

public class ValidateFlightCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("ValidateFlightCommand");
	
	private static final String CONST_FLIGHT = "FLIGHT";
	private static final String OUTBOUND = "O";
	private static final String HAS_PRIVILEGE_FOR_VIEW_ALL_MAIL_TRUCK="mail.operations.viewalltruckflightsforoperatios";
	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ValidateFlightCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		ArrayList results = new ArrayList();	
		ResponseVO responseVO = new ResponseVO();

		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();

				
		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();		

		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedMailbags() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");						
				
			mailbagEnquiryModel.setDuplicateFlightStatus(FLAG_NO);
		    
		    AirlineDelegate airlineDelegate = new AirlineDelegate();
		    AreaDelegate areaDelegate = new AreaDelegate();
		    
		    String assignTo = mailbagEnquiryModel.getAssignToFlight();
		    log.log(Log.FINE, "assignTo ===", assignTo);
			if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
		    	errors = (ArrayList)validateFormFlight(mailbagEnquiryModel);
		    	if (errors != null && errors.size() > 0) {  
		    		actionContext.addAllError(errors);
					return;
		    	}
	    	FlightFilterVO flightFilterVO = handleFlightFilterVO(
	    			mailbagEnquiryModel,logonAttributes);	
	    	String flightNum = (mailbagEnquiryModel.getFlightNumber().toUpperCase());
			
	    	AirlineValidationVO airlineValidationVO = null;
		    String flightCarrierCode = mailbagEnquiryModel.getFlightCarrierCode();        	
	    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
	    		try {        			
	    			airlineValidationVO = airlineDelegate.validateAlphaCode(
	    					logonAttributes.getCompanyCode(),
	    					flightCarrierCode.trim().toUpperCase());

	    		}catch (BusinessDelegateException businessDelegateException) {
	    			errors = (ArrayList)handleDelegateException(businessDelegateException);
	    		}
	    		if (errors != null && errors.size() > 0) {            			
	    			errors = new ArrayList<ErrorVO>();
	    			Object[] obj = {flightCarrierCode.toUpperCase()};
					ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.invalidcarrier",obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					actionContext.addAllError(errors);
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
				//added as part of IASCB-56622
				removeIncompatibleTruckFlight(flightValidationVOs);

			}catch (BusinessDelegateException businessDelegateException) {
				errors = (ArrayList)handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
				return;
			}
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
				log.log(Log.FINE, "flightValidationVOs is NULL");
				Object[] obj = {mailbagEnquiryModel.getFlightCarrierCode(),
						mailbagEnquiryModel.getFlightNumber(),
						mailbagEnquiryModel.getFlightDate().toString().substring(0,11)};
				
				errors.add(new ErrorVO("mailtracking.defaults.noflightdetails",obj));
				if(errors != null && errors.size() >0){
					actionContext.addAllError(errors);
					return;
				}
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
				flightValidationVO.setDirection("O");
				
				mailbagEnquiryModel.setFlightValidation(MailOperationsModelConverter.constructFlightValidation(flightValidationVO,logonAttributes));
				mailbagEnquiryModel.setPort(logonAttributes.getAirportCode());
				
				
				
				log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
				
			}else {
				//Duplicate case to be handled
				mailbagEnquiryModel.setDuplicateFlightStatus(FLAG_YES);
				
			   }
		    }else{
		    	errors = (ArrayList)validateFormDestination(mailbagEnquiryModel);
		    	if (errors != null && errors.size() > 0) {  
		    		actionContext.addAllError(errors);
					return;
		    	}
		    	Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
		    	AirlineValidationVO airlineValidationVO = null;
		    	 String carrierCode = mailbagEnquiryModel.getCarrierCode();        	
		     	if (carrierCode != null && !"".equals(carrierCode)) {        		
		     		try {        			
		     			airlineValidationVO = airlineDelegate.validateAlphaCode(
		     					logonAttributes.getCompanyCode(),
		     					carrierCode.toUpperCase());
		     		}catch (BusinessDelegateException businessDelegateException) {
		     			errors = (ArrayList)handleDelegateException(businessDelegateException);
		     		}
		     		if (errors != null && errors.size() > 0) {            			
		     			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidcarrier",
				   				new Object[]{carrierCode.toUpperCase()}));
		     		}
		     	}
		     	mailbagEnquiryModel.setCarrierID(airlineValidationVO.getAirlineIdentifier());
		     	AirportValidationVO airportValidationVO = null;
		    	 String destination = mailbagEnquiryModel.getDestination();        	
		     	if (destination != null && !"".equals(destination)) {        		
		     		try {        			
		     			airportValidationVO = areaDelegate.validateAirportCode(
		     					logonAttributes.getCompanyCode(),
		     					destination.toUpperCase());
		     		}catch (BusinessDelegateException businessDelegateException) {
		     			errors = (ArrayList)handleDelegateException(businessDelegateException);
		     		}
		     		if (errors != null && errors.size() > 0) {            			
		     			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidairport",
				   				new Object[]{destination.toUpperCase()}));
		     		}
		     	}
		     	
		     	
		     	if (errorsMail != null && errorsMail.size() > 0) {
		     		actionContext.addAllError(errors);
					return;
		     	}
		     
		       	
		    }
			 	    
		

		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
		}
		results.add(mailbagEnquiryModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ValidateFlightCommand", "execute");

	}
	/**
	 *  This method will check mail aircraft incompatibility, and same is reused in different command class
	 *    mail.operations.ux.outbound.ListMailOutboundCommand.java
	 * @param flightValidationVOs
	 */
	public void removeIncompatibleTruckFlight(Collection<FlightValidationVO> flightValidationVOs) {
		if(flightValidationVOs==null || flightValidationVOs.size()==0) {
			return;
		}
		boolean hasPrivilegeForMonitoring = false;
		try {
			hasPrivilegeForMonitoring = SecurityAgent.getInstance()
					.checkBusinessPrivilege(HAS_PRIVILEGE_FOR_VIEW_ALL_MAIL_TRUCK);
		} catch (SystemException systemException) {
			hasPrivilegeForMonitoring = false;
		}

		if (!hasPrivilegeForMonitoring) {
			Collection<String> systemUnitValues = new ArrayList<String>();
			Map<String, String> systemUnitCodes = null;
			String mailTruckTypes = null;
			systemUnitValues.add("mail.operations.truckaircrafttypesforoperations");
			log.log(Log.INFO, "-------------------Calling findSystemParameterByCodes");
			try {
				systemUnitCodes = new SharedDefaultsDelegate().findSystemParameterByCodes(systemUnitValues);
			} catch (BusinessDelegateException e) {
				systemUnitCodes = null;
			}
			if(systemUnitCodes!=null && systemUnitCodes.containsKey("mail.operations.truckaircrafttypesforoperations")) {
				mailTruckTypes = systemUnitCodes.get("mail.operations.truckaircrafttypesforoperations");				
			}
			if(mailTruckTypes!=null && mailTruckTypes.trim().length()>0 && !mailTruckTypes.equals("N")) {
				Iterator<FlightValidationVO> flightterator = flightValidationVOs.iterator();
				FlightValidationVO flightValidationVO = null;
				while(flightterator.hasNext()) {
					flightValidationVO = flightterator.next();
					if("T".equals(flightValidationVO.getFlightType()) && flightValidationVO.getAircraftType()!=null && !mailTruckTypes.contains(flightValidationVO.getAircraftType())) {
						flightterator.remove();
					}
				}
			}
		}

	}
	/**
	 * Method to validate form in FLIGHT MODE.
	 * @param reassignMailForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormFlight(
			MailbagEnquiryModel mailbagEnquiryModel) {
		String flightCarrierCode = mailbagEnquiryModel.getFlightCarrierCode();
		String flightNumber = mailbagEnquiryModel.getFlightNumber();
		String depDate = mailbagEnquiryModel.getFlightDate();
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
	 * Method to validate form in FLIGHT MODE.
	 * @param MailbagEnquiryModel
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormDestination(
			MailbagEnquiryModel mailbagEnquiryModel) {

		String carrierCode = mailbagEnquiryModel.getCarrierCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		if(carrierCode == null || ("".equals(carrierCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.carriercode.empty"));
		}
		return errors;
	}
    
       /**
	     * Method to create the filter vo for flight validation
	     * @param MailbagEnquiryModel
	     * @param logonAttributes
	     * @return FlightFilterVO
	     */
	    private FlightFilterVO handleFlightFilterVO(
	    		MailbagEnquiryModel mailbagEnquiryModel,
				LogonAttributes logonAttributes){

			FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			
			flightFilterVO.setStation(logonAttributes.getAirportCode());
			flightFilterVO.setDirection(OUTBOUND);
			//while inputing flight details for transfer or reassign flow, duplicate flight scenarios not handled this 
			//causing script error in react popup. (in case two flight with one is cancelled and another is active)
			flightFilterVO.setActiveAlone(true);
			flightFilterVO.setStringFlightDate(mailbagEnquiryModel.getFlightDate());
	 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
	 		flightFilterVO.setFlightDate(date.setDate(mailbagEnquiryModel.getFlightDate()));
			return flightFilterVO;
		}

}
