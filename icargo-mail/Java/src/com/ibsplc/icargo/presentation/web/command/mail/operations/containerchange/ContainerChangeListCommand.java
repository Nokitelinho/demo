/*
 * MailChangeListCommand.java Created on Jul 1 2016
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
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
/**
 * @author A-5991
 *
 */
public class ContainerChangeListCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "success";
   private static final String TARGET_FAILURE = "failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   private static final String FLTSTATUS = "mailtracking.defaults.flightstatus";
   private static final String FLTLEGSTATUS ="flight.operation.flightlegstatus";
   private static final String INBOUND = "I";
   private static final String FLIGHT_CLOSED="mailtracking.defaults.changeflight.flightclosed";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ContainerChangeListCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
        		(MailArrivalForm)invocationContext.screenModel;
        	MailArrivalSession mailArrivalSession = 
        		getScreenSession(MODULE_NAME,SCREEN_ID);
    		DuplicateFlightSession duplicateFlightSession = getScreenSession(
    				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
    			
    		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    		
    		MailArrivalVO mailArrivalVO = mailArrivalSession.getFlightMailArrivalVO();
    		
    		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
    		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
    		operationalFlightVO.setPou(logonAttributes.getAirportCode());
    		
    		boolean isFlightClosed = false;
    		
    		MailArrivalVO newMailArrivalVO = new MailArrivalVO();
    		FlightValidationVO flightValidationVO = new FlightValidationVO();
    		mailArrivalForm.setOperationalStatus("");
    			
    		if(FLAG_YES.equals(mailArrivalForm.getDuplicateFlightStatus())){
    			mailArrivalForm.setListFlag("");
    			mailArrivalSession.setChangeFlightValidationVO(duplicateFlightSession.getFlightValidationVO());
    			mailArrivalForm.setDuplicateFlightStatus(FLAG_NO);
    		}
    		log.log(Log.FINE, "flightValidationVO in MailChangeListCommand..",
    				mailArrivalSession.getChangeFlightValidationVO());
    		flightValidationVO = mailArrivalSession.getChangeFlightValidationVO();
    		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
    		if(oneTimes!=null){
    			log.log(Log.FINE, "oneTimes is not null");
    			Collection<OneTimeVO> resultStatus=
    				oneTimes.get(FLTLEGSTATUS);
    			flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
    			flightValidationVO.setDirection(INBOUND);
    		}
    		mailArrivalForm.setListFlag("");
    		mailArrivalSession.setChangeFlightValidationVO(flightValidationVO);
    	
    		operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    		operationalFlightVO.setFlightDate(flightValidationVO.getFlightDate());
    		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    		operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
    		operationalFlightVO.setDirection(INBOUND);
    		operationalFlightVO.setPol(flightValidationVO.getLegOrigin());
    		
    		try {
        		if(new MailTrackingDefaultsDelegate().validateInboundFlight(
        				operationalFlightVO) != null) {
        			isFlightClosed = new MailTrackingDefaultsDelegate().
        				isFlightClosedForInboundOperations(operationalFlightVO);
        		}
    			
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    		log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
    		if (isFlightClosed) {
    			invocationContext.addError(new ErrorVO(FLIGHT_CLOSED));    			
    			invocationContext.target = TARGET_FAILURE;
    			return;				
    		}
    		
    		
    		MailArrivalFilterVO mailArrivalFilterVO = mailArrivalSession.getFlightMailArrivalFilterVO();
    		
    		if(mailArrivalFilterVO==null){
    			mailArrivalFilterVO = constructMailArrivalFilterVO(operationalFlightVO);
    			mailArrivalFilterVO.setMailStatus("ALL");
    			mailArrivalFilterVO.setNextCarrierId(mailArrivalVO.getTransferCarrierId());
    			mailArrivalFilterVO.setPaCode(mailArrivalVO.getArrivalPA());
    			mailArrivalFilterVO.setNextCarrierCode(mailArrivalVO.getTransferCarrier());
            }
    		
    		
    		/** For report*/
    		/*mailArrivalForm.setCarrierId(mailArrivalFilterVO.getCarrierId());
    		mailArrivalForm.setFlightSequenceNumber(mailArrivalFilterVO.getFlightSequenceNumber());
    		mailArrivalForm.setLegSerialNumber(mailArrivalFilterVO.getLegSerialNumber());*/
    		
    		log.log(Log.FINE, "mailArrivalFilterVO b4 gng to server call...",
    				mailArrivalFilterVO);
    			try {
    			    	newMailArrivalVO = new MailTrackingDefaultsDelegate().findArrivalDetails(
    			    								mailArrivalFilterVO);
    	          }catch (BusinessDelegateException businessDelegateException) {
    	    			errors = handleDelegateException(businessDelegateException);
    	    	  }
        	
    			
    			
    			
    			
    			
    			
	  		log.log(Log.FINE, "newMailArrivalVO ----->>", newMailArrivalVO);
	  		
	    	  newMailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	  newMailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
			  newMailArrivalVO.setFlightCarrierCode(mailArrivalVO.getFlightCarrierCode());
			  newMailArrivalVO.setFlightNumber(mailArrivalVO.getFlightNumber());
			  newMailArrivalVO.setArrivalDate(mailArrivalVO.getArrivalDate());
			  newMailArrivalVO.setCarrierId(mailArrivalVO.getCarrierId());
			  newMailArrivalVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			  newMailArrivalVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber()); 
			  newMailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			  newMailArrivalVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
			  mailArrivalSession.setFlightMailArrivalVO(newMailArrivalVO);
			
			if(newMailArrivalVO.getContainerDetails() != null && newMailArrivalVO.getContainerDetails().size() >0){
			for (ContainerDetailsVO  containerdtlsvo : newMailArrivalVO.getContainerDetails()){
				double manifestedWeight=0;
				 int manifestedBags=0;
				 double receivedWeight=0;
				 int receivedBags=0;
				Collection<DSNVO> dSNVOs =  new ArrayList<DSNVO>();
				dSNVOs = containerdtlsvo.getDsnVOs();
				if( dSNVOs != null && dSNVOs.size() >0){
					for (DSNVO  dsnvo : dSNVOs){
						manifestedWeight=manifestedWeight+dsnvo.getWeight().getRoundedSystemValue(); //Added by A-7550
						manifestedBags=manifestedBags+dsnvo.getBags();
						receivedWeight=receivedWeight+dsnvo.getReceivedWeight().getRoundedSystemValue(); //Added by A-7550
						receivedBags=receivedBags+dsnvo.getReceivedBags();
					}
				} 
				containerdtlsvo.setTotalBags(manifestedBags);
				//containerdtlsvo.setTotalWeight(manifestedWeight);
				containerdtlsvo.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, manifestedWeight)); //Added by A-7550
				containerdtlsvo.setReceivedBags(receivedBags);
				//containerdtlsvo.setReceivedWeight(receivedWeight);
				containerdtlsvo.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, receivedWeight)); //Added by A-7550
			}
	    }
			mailArrivalSession.setFlightMailArrivalVO(newMailArrivalVO);
			
	    	mailArrivalSession.setChangedOperationalFlightVO(operationalFlightVO);
			LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			String date = currentDate.toDisplayDateOnlyFormat();
			String time = currentDate.toDisplayFormat("HH:mm");
			mailArrivalForm.setFlightScanDate(date);
			mailArrivalForm.setFlightScanTime(time);
	    	mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	mailArrivalForm.setAddLinkFlag(FLAG_YES);
	    	invocationContext.target = TARGET_SUCCESS;
	    	
    	log.exiting("ContainerChangeListCommand","execute");
    	
    	
    }
    
    
    /**
	 * TODO Purpose
	 * Jan 22, 2007, A-1739
	 * @param operationalFlightVO
	 * @return
	 */
	private MailArrivalFilterVO constructMailArrivalFilterVO(
			OperationalFlightVO operationalFlightVO) {
		MailArrivalFilterVO filterVO = new MailArrivalFilterVO();
		filterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		filterVO.setCarrierId(operationalFlightVO.getCarrierId());
		filterVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		filterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		filterVO.setFlightSequenceNumber(
				operationalFlightVO.getFlightSequenceNumber());
		filterVO.setFlightDate(operationalFlightVO.getFlightDate());
		filterVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		filterVO.setPou(operationalFlightVO.getPou());
		//setting ALL for default work. change to appropirate
		filterVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ALL);
		return filterVO;
	}
    
    
    
    
    
    
    /**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		
		Collection<ErrorVO> errors = null;
		
		try{
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add(FLTLEGSTATUS);
			fieldValues.add(FLTSTATUS);
			
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;

		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
	
	
	

	/**
	 * This method will the status description corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		if (oneTimeVOs != null) {
			for (OneTimeVO oneTimeVO:oneTimeVOs){
				if(status.equals(oneTimeVO.getFieldValue())){
					return oneTimeVO.getFieldDescription();
				}
			}
		}

		return null;
	}
       
    
       
}
