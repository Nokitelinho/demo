/*
 * OKFlightDetailsCommand.java Created on Feb 26, 2007
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
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class OKFlightDetailsCommand extends BaseCommand {
	
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
				
		log.entering("OKFlightDetailsCommand","execute");
		UploadMailForm uploadMailForm 
		= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    
		if("FLIGHT".equals(uploadMailForm.getAssignToFlight())){
	    	errors = validateFormFlight(uploadMailForm);
	    	if (errors != null && errors.size() > 0) {  
	    		invocationContext.addAllError(errors);
				invocationContext.target = TARGET;
				return;
	    	}
	    }else{
	    	errors = validateFormDestination(uploadMailForm);
	    	if (errors != null && errors.size() > 0) {  
	    		invocationContext.addAllError(errors);
				invocationContext.target = TARGET;
				return;
	    	}
	    }
		
		int flightDetails = Integer.parseInt(uploadMailForm.getFlightDetails());
		log.log(Log.INFO, "flightDetails.\n", flightDetails);
		ScannedDetailsVO scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		Collection<ScannedMailDetailsVO> scannedAcceptVOs = scannedDetailsVO.getOutboundMails();
		
			//for(int cnt=0;cnt<primKeyArr.length;cnt++){
				//String[] keyArr = primKeyArr[cnt].split("-");
				//int key = Integer.parseInt(keyArr[0]);
				int countArr = 0;
				if(scannedAcceptVOs != null && scannedAcceptVOs.size() > 0){
				//  for(ScannedMailDetailsVO scannedMailDetailsVO:scannedAcceptVOs){	
					ScannedMailDetailsVO scannedMailDetailsVO = (ScannedMailDetailsVO)((ArrayList<ScannedMailDetailsVO>)scannedAcceptVOs).get(flightDetails);
					//if(countArr == flightDetails){
						if("FLIGHT".equals(uploadMailForm.getAssignToFlight())){
							
							AirlineValidationVO airlineValidationVO = null;
						    String flightCarrierCode = uploadMailForm.getAcpCarrierCode();        	
					    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) { 
					    		flightCarrierCode = uploadMailForm.getAcpCarrierCode().trim().toUpperCase(); 
					    		try {        			
					    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
					    					logonAttributes.getCompanyCode(),flightCarrierCode);
					    		}catch (BusinessDelegateException businessDelegateException) {
					    			errors = handleDelegateException(businessDelegateException);
					    		}
					    		log.log(Log.INFO, "airlineValidationVO.\n",
										airlineValidationVO);
								if (errors != null && errors.size() > 0) {            			
					    			Object[] obj = {flightCarrierCode};
					    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
					    			invocationContext.target = TARGET;
					    			return;
					    		}
					    	}
					    	
					    	AirportValidationVO airportValidationVO = null;
					    	String pou = uploadMailForm.getAcpPou();        	
					     	if (pou != null && !"".equals(pou)) { 
					     		pou = uploadMailForm.getAcpPou().trim().toUpperCase(); 
					     		try {        			
					     			airportValidationVO = new AreaDelegate().validateAirportCode(
					     					logonAttributes.getCompanyCode(),pou);
					     		}catch (BusinessDelegateException businessDelegateException) {
					     			errors = handleDelegateException(businessDelegateException);
					     		}
					     		log.log(Log.INFO, "airportValidationVO.\n",
										airportValidationVO);
								if (errors != null && errors.size() > 0) {            			
					    			Object[] obj = {pou};
					    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",obj));
					    			invocationContext.target = TARGET;
					    			return;
					    		}
					     	}
					     	
					     	airportValidationVO = null;
					    	String destination = uploadMailForm.getDestnFlight();        	
					     	if (destination != null && !"".equals(destination)) { 
					     		destination = uploadMailForm.getDestnFlight().trim().toUpperCase(); 
					     		try {        			
					     			airportValidationVO = new AreaDelegate().validateAirportCode(
					     					logonAttributes.getCompanyCode(),destination);
					     		}catch (BusinessDelegateException businessDelegateException) {
					     			errors = handleDelegateException(businessDelegateException);
					     		}
					     		log.log(Log.INFO, "airportValidationVO.\n",
										airportValidationVO);
								if (errors != null && errors.size() > 0) {            			
					    			Object[] obj = {destination};
					    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",obj));
					    			invocationContext.target = TARGET;
					    			return;
					    		}
					     	}
					     	
					    	scannedMailDetailsVO.setCarrierCode(uploadMailForm.getAcpCarrierCode());
				       		scannedMailDetailsVO.setFlightNumber(uploadMailForm.getAcpFlightNumber().toUpperCase());
				       		scannedMailDetailsVO.setPou(uploadMailForm.getAcpPou());
			       			LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			       			scannedMailDetailsVO.setFlightDate(sd.setDate(uploadMailForm.getAcpFlightDate()));
							
					    	FlightFilterVO flightFilterVO = new FlightFilterVO();
							flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
							flightFilterVO.setStation(logonAttributes.getAirportCode());
							flightFilterVO.setDirection("O");
							flightFilterVO.setStringFlightDate(uploadMailForm.getAcpFlightDate());
					 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
					 		flightFilterVO.setFlightDate(date.setDate(uploadMailForm.getAcpFlightDate()));
					 		flightFilterVO.setCarrierCode(flightCarrierCode);
							flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
							flightFilterVO.setFlightNumber(uploadMailForm.getAcpFlightNumber().toUpperCase());
							
							Collection<FlightValidationVO> flightValidationVOs = null;
							try {
								log.log(Log.FINE,
										"FlightFilterVO ------------> ",
										flightFilterVO);
								flightValidationVOs =
									new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);

							}catch (BusinessDelegateException businessDelegateException) {
								errors = handleDelegateException(businessDelegateException);
							}
							if (errors != null && errors.size() > 0) {
								invocationContext.addAllError(errors);
								invocationContext.target = TARGET;
								return;
							}
							FlightValidationVO flightValidationVO = new FlightValidationVO();
							if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
								log.log(Log.FINE, "flightValidationVOs is NULL");
								Object[] obj = {scannedMailDetailsVO.getCarrierCode(),
										scannedMailDetailsVO.getFlightNumber(),
										scannedMailDetailsVO.getFlightDate().toString().substring(0,11)};
								invocationContext.addError(new ErrorVO("mailtracking.defaults.noflightdetails",obj));
								invocationContext.target = TARGET;
								return;
							} 
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
							
							/*
							 * Obtain the collection of POUs
							 */
							
							Collection<String> pointOfUnladings = new ArrayList<String>();
							String route = flightValidationVO.getFlightRoute();
							String[] stations = route.split("-");
							int flag = 0;
							if(route != null && route.length() > 0){
								for(int index=0; index < stations.length; index++) {
									if(flag == 1){
										pointOfUnladings.add(stations[index]);
									}
									if(stations[index].equals(logonAttributes.getAirportCode())){
										flag = 1;
									}
								}
							}
							log.log(Log.FINE, "pointOfUnladings...",
									pointOfUnladings);
							if(!pointOfUnladings.contains(uploadMailForm.getAcpPou().toUpperCase())) {
								Object[] obj = {uploadMailForm.getAcpPou().toUpperCase(),
										flightValidationVO.getCarrierCode(),
										flightValidationVO.getFlightNumber(),
										flightValidationVO.getFlightRoute()};
								invocationContext.addError(new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.invalidFlightpou",obj));
								invocationContext.target = TARGET;
								return;
							}
							
							scannedMailDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
							scannedMailDetailsVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
							scannedMailDetailsVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
							scannedMailDetailsVO.setSegmentSerialNumber(0);
				       		scannedMailDetailsVO.setDestination(destination);
				       		
						}else{
							
							AirlineValidationVO airlineValidationVO = null;
						    String carrierCode = uploadMailForm.getAcpDestCarrier();        	
					    	if (carrierCode != null && !"".equals(carrierCode)) { 
					    		carrierCode = uploadMailForm.getAcpDestCarrier().trim().toUpperCase(); 
					    		try {        			
					    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
					    					logonAttributes.getCompanyCode(),carrierCode);
					    		}catch (BusinessDelegateException businessDelegateException) {
					    			errors = handleDelegateException(businessDelegateException);
					    		}
					    		if (errors != null && errors.size() > 0) {            			
					    			Object[] obj = {carrierCode};
					    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
					    			invocationContext.target = TARGET;
					    			return;
					    		}
					    	}
					    	
					    	AirportValidationVO airportValidationVO = null;
					    	String destination = uploadMailForm.getAcpDestination();        	
					     	if (destination != null && !"".equals(destination)) { 
					     		destination = uploadMailForm.getAcpDestination().trim().toUpperCase(); 
					     		try {        			
					     			airportValidationVO = new AreaDelegate().validateAirportCode(
					     					logonAttributes.getCompanyCode(),destination);
					     		}catch (BusinessDelegateException businessDelegateException) {
					     			errors = handleDelegateException(businessDelegateException);
					     		}
					     		
					     		if (errors != null && errors.size() > 0) {            			
					    			Object[] obj = {destination};
					    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",obj));
					    			invocationContext.target = TARGET;
					    			return;
					    		}
					     	}
					    	
							scannedMailDetailsVO.setCarrierCode(uploadMailForm.getAcpDestCarrier());
							scannedMailDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
				       		scannedMailDetailsVO.setDestination(uploadMailForm.getAcpDestination());
				       		scannedMailDetailsVO.setFlightNumber("");
				       		scannedMailDetailsVO.setPou("");
			       			scannedMailDetailsVO.setFlightDate(null);
			       			scannedMailDetailsVO.setSegmentSerialNumber(-1);
			       			scannedMailDetailsVO.setFlightSequenceNumber(-1);
							scannedMailDetailsVO.setLegSerialNumber(-1);
						}
				   //}
		       // }
		      }
				log.log(Log.INFO, "scannedArriveVOs.\n", scannedAcceptVOs);
			// }
			scannedDetailsVO.setOutboundMails(scannedAcceptVOs);
			uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		
		
		uploadMailForm.setFlightFlag("Y");
		invocationContext.target = TARGET;
		log.exiting("OKFlightDetailsCommand","execute");
		
	}
	
	
	/**
	 * Method to validate form in FLIGHT MODE.
	 * @param uploadMailForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormFlight(
							UploadMailForm uploadMailForm) {
		String flightCarrierCode = uploadMailForm.getAcpCarrierCode();
		String flightNumber = uploadMailForm.getAcpFlightNumber();
		String flightDate = uploadMailForm.getAcpFlightDate();
		String pou = uploadMailForm.getAcpPou();
		String destination = uploadMailForm.getDestnFlight();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(flightCarrierCode == null || ("".equals(flightCarrierCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.flightcarriercode.empty"));
		}
		if(flightNumber == null || ("".equals(flightNumber.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.flightnumber.empty"));
		}
		if(flightDate == null || ("".equals(flightDate.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.depdate.empty"));
		}
		if(pou == null || ("".equals(pou.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.pou.empty"));
		}
		if(destination == null || ("".equals(destination.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.destination.empty"));
		}
		return errors;
	}
	
	/**
	 * Method to validate form in DEST MODE.
	 * @param uploadMailForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormDestination(
			UploadMailForm uploadMailForm) {
//		String destination = uploadMailForm.getAcpDestination();
		String carrierCode = uploadMailForm.getAcpDestCarrier();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
//		if(destination == null || ("".equals(destination.trim()))){
//			errors.add(new ErrorVO("mailtracking.defaults.destination.empty"));
//		}
		if(carrierCode == null || ("".equals(carrierCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.carriercode.empty"));
		}
		return errors;
	}
    
		
		/**
		 * This method is to format flightNumber
		 * Not using - CRQ-AirNZ989-12
		 * @param flightNumber 
		 * @return String
		 */
		/*private String formatFlightNumber(String flightNumber){
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
