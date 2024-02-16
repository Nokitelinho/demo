/*
 * OKContainerDetailsCommand.java Created on Oct 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
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
public class OKContainerDetailsCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
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
				
		log.entering("UploadCommand","execute");
		UploadMailForm uploadMailForm 
					= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
					getScreenSession(MODULE_NAME,SCREEN_ID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateFormFlight(uploadMailForm);
    	if (errors != null && errors.size() > 0) {  
    		invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			return;
    	}
		
    	ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
    	Collection<ScannedMailDetailsVO> scannedArriveVOs = scannedDetailsVO.getArrivedMails();
		ScannedMailDetailsVO scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedArriveVOs).get(0);
		Collection<MailbagVO> mailbagArriveVOs = scannedMailDetailsVO.getMailDetails();
		
		String mailBag = uploadMailForm.getContinerDetails();
		log.log(Log.INFO, "mailBag.....\n", mailBag);
		int count = 0;
		try{
			if(mailbagArriveVOs != null && mailbagArriveVOs.size() > 0){
			  for(MailbagVO mailbagVO:mailbagArriveVOs){
				  count++;
                if(count == Integer.parseInt(mailBag)){
				
	       		AirlineValidationVO airlineValidationVO = null;
			    String flightCarrierCode = uploadMailForm.getArrCarrierCode();        	
		    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) { 
		    		flightCarrierCode = uploadMailForm.getArrCarrierCode().trim().toUpperCase(); 
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
		    	
		    	/**
		    	 * Validate flight
		    	 */				
		    	FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				flightFilterVO.setStation(logonAttributes.getAirportCode());
				flightFilterVO.setDirection("I");
				flightFilterVO.setStringFlightDate(uploadMailForm.getArrFlightDate());
		 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		 		flightFilterVO.setFlightDate(date.setDate(uploadMailForm.getArrFlightDate()));
		 		flightFilterVO.setCarrierCode(flightCarrierCode);
				flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
				flightFilterVO.setFlightNumber(uploadMailForm.getArrFlightNumber().toUpperCase());
				
				Collection<FlightValidationVO> flightValidationVOs = null;
				try {
					flightValidationVOs =
						new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);

				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				FlightValidationVO flightValidationVO = new FlightValidationVO();
				if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
					log.log(Log.FINE, "flightValidationVOs is NULL");
					Object[] obj = {flightFilterVO.getCarrierCode(),flightFilterVO.getFlightNumber(),
							flightFilterVO.getFlightDate().toString().substring(0,11)};
					invocationContext.addError(new ErrorVO("mailtracking.defaults.noflightdetails",obj));
					invocationContext.target = TARGET;
					return;
				} 
				log.log(Log.FINE, "flightValidationVOs has one VO");
				try {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						BeanHelper.copyProperties(flightValidationVO,flightValidVO);
						break;
					}
				} catch (SystemException systemException) {
					systemException.getMessage();
				}
				
				/*
				 * to set POL
				 */
				String route = flightValidationVO.getFlightRoute();
				String[] stations = route.split("-");
				String pol = "";
				if(route != null && route.length() > 0){
					for(int index=stations.length-1;index >= 0;index--){
						if(stations[index].equals(logonAttributes.getAirportCode())){
							pol = stations[index-1];
						}
					}
				}
				
				if(!"B".equals(uploadMailForm.getContainerType())){
					String containerNum = uploadMailForm.getContainerNumber().toUpperCase();
					ULDDelegate uldDelegate = new ULDDelegate();
					try {
						uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNum);
					}catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					
					if (errors != null && errors.size() > 0) {      
						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.invaliduldnumber",
				   				new Object[]{containerNum}));
				  		invocationContext.target = TARGET;
				  		return;
					}
					
					ContainerVO containerVO  = new ContainerVO();
		       		containerVO.setCompanyCode(logonAttributes.getCompanyCode());
		       		containerVO.setContainerNumber(uploadMailForm.getContainerNumber().toUpperCase());
		       		containerVO.setAssignedPort(pol);
		       		containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
		        	containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		        	containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		       		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		       		containerAssignmentVO = new MailTrackingDefaultsDelegate().findContainerAssignment(containerVO);
		       		if(containerAssignmentVO != null){
		       			if(containerAssignmentVO.getFlightSequenceNumber() == -1){
				       		Object[] obj = {mailbagVO.getMailbagId()};
							invocationContext.addError(new ErrorVO("mailtracking.defaults.uploadmail.msg.err.destassigned",obj));
							invocationContext.target = TARGET;
							return;
		       			}else{
		       				Object[] obj = {mailbagVO.getMailbagId()};
							invocationContext.addError(new ErrorVO("mailtracking.defaults.uploadmail.msg.err.anotherflight",obj));
							invocationContext.target = TARGET;
							return;
		       			}
		       		}
		       		
				}
				
		       	mailbagVO.setErrorType(null);
				mailbagVO.setErrorDescription(null);
				if("B".equals(uploadMailForm.getContainerType())){
	       			mailbagVO.setContainerNumber(new StringBuilder("BULK")
	       					.append("-").append(pol).toString());
	       		}else{
	       			mailbagVO.setContainerNumber(uploadMailForm.getContainerNumber().toUpperCase());
	       		}
				mailbagVO.setPol(pol);
	       		mailbagVO.setContainerType(uploadMailForm.getContainerType());
	       		mailbagVO.setCarrierCode(uploadMailForm.getArrCarrierCode());
	       		mailbagVO.setFlightNumber(uploadMailForm.getArrFlightNumber().toUpperCase());
	       		LocalDate mfd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
	       		mailbagVO.setFlightDate(mfd.setDate(uploadMailForm.getArrFlightDate()));
	       		mailbagVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
				mailbagVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				mailbagVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		     }
		   }
		 }
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		log.log(Log.INFO, "mailbagArriveVOs.\n", mailbagArriveVOs);
		scannedMailDetailsVO.setMailDetails(mailbagArriveVOs);
		
		Collection<ScannedMailDetailsVO> scannedArrVOs = new ArrayList<ScannedMailDetailsVO>();
		
		scannedArrVOs.add(scannedMailDetailsVO);
		scannedDetailsVO.setArrivedMails(scannedArrVOs);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		
		/**
		 * For Summary Details
		 */
		ScannedDetailsVO summaryVO = uploadMailSession.getScannedSummaryVO();
		Collection<ScannedMailDetailsVO> scannedNewArrVOs = new ArrayList<ScannedMailDetailsVO>();

		if(scannedArrVOs != null && scannedArrVOs.size() > 0){
			HashSet<String> key=new HashSet<String>();
			String tempKeyOne=null;
			String tempKeyTwo=null;
			Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
			for(ScannedMailDetailsVO scannedMailVO:scannedArrVOs){
				for(MailbagVO arrivedMailVO:scannedMailVO.getMailDetails()){
					ScannedMailDetailsVO arrivedSummayVO=new ScannedMailDetailsVO();
					tempKeyOne=arrivedMailVO.getCarrierCode()+HYPHEN
					+arrivedMailVO.getFlightNumber()+HYPHEN
					+arrivedMailVO.getFlightDate();
					if(!(key.contains(tempKeyOne))){
						mailVOs = new ArrayList<MailbagVO>();
						arrivedSummayVO.setCarrierCode(arrivedMailVO.getCarrierCode());
						arrivedSummayVO.setCarrierId(arrivedMailVO.getCarrierId());
						arrivedSummayVO.setCompanyCode(arrivedMailVO.getCompanyCode());
						arrivedSummayVO.setContainerNumber(arrivedMailVO.getContainerNumber());
						arrivedSummayVO.setPol(arrivedMailVO.getPol());
						arrivedSummayVO.setPou(arrivedMailVO.getPou());
						arrivedSummayVO.setFlightDate(arrivedMailVO.getFlightDate());
						arrivedSummayVO.setFlightNumber(arrivedMailVO.getFlightNumber());
						arrivedSummayVO.setFlightSequenceNumber(arrivedMailVO.getFlightSequenceNumber());
						arrivedSummayVO.setLegSerialNumber(arrivedMailVO.getLegSerialNumber());

						mailVOs.add(arrivedMailVO);
						arrivedSummayVO.setMailDetails(mailVOs);
						arrivedSummayVO.setScannedBags(arrivedSummayVO.getMailDetails().size());
						arrivedSummayVO.setUnsavedBags(arrivedSummayVO.getScannedBags());
						arrivedSummayVO.setSavedBags(0);

						key.add(arrivedSummayVO.getCarrierCode()+HYPHEN
								+arrivedSummayVO.getFlightNumber()+HYPHEN
								+arrivedSummayVO.getFlightDate());
						scannedNewArrVOs.add(arrivedSummayVO);
					}else{
						for(ScannedMailDetailsVO arrivedSummaryVO:scannedNewArrVOs){
							tempKeyTwo=arrivedSummaryVO.getCarrierCode()+HYPHEN
							+arrivedSummaryVO.getFlightNumber()+HYPHEN
							+arrivedSummaryVO.getFlightDate();
							if(tempKeyOne.equals(tempKeyTwo)){
								mailVOs.add(arrivedMailVO);
								arrivedSummaryVO.setMailDetails(mailVOs);
								arrivedSummaryVO.setScannedBags(arrivedSummaryVO.getScannedBags()+1);
								arrivedSummaryVO.setUnsavedBags(arrivedSummaryVO.getScannedBags());
							}
						}
					}
				}
			}
		}

		summaryVO.setArrivedMails(scannedNewArrVOs);
		uploadMailSession.setScannedSummaryVO(summaryVO);
		
		uploadMailForm.setContinerFlag("CLOSE");
		invocationContext.target = TARGET;
		log.exiting("UploadCommand","execute");
		
	}
	
	
	/**
	 * Method to validate form in FLIGHT MODE.
	 * @param uploadMailForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormFlight(
							UploadMailForm uploadMailForm) {
		String flightCarrierCode = uploadMailForm.getArrCarrierCode();
		String flightNumber = uploadMailForm.getArrFlightNumber();
		String flightDate = uploadMailForm.getArrFlightDate();
		String containerNumber = uploadMailForm.getContainerNumber();
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
		if(!"B".equals(uploadMailForm.getContainerType())){
			if(containerNumber == null || ("".equals(containerNumber.trim()))){
				errors.add(new ErrorVO("mailtracking.defaults.containernumber.empty"));
			}
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
