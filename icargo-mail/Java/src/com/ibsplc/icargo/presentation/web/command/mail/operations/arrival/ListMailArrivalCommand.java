/*
 * ListMailArrivalCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
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

/**
 * @author A-5991
 *
 */
public class ListMailArrivalCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "list_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
   /**
    * Module name
    */
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   /**
    * Screen id
    */
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   
   private static final String INBOUND = "I";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListMailArrivalCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
			
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		//Modified by A-7794 as part of ICRD-197439
		operationalFlightVO.setPou(mailArrivalForm.getArrivalPort());
		
		MailArrivalVO newMailArrivalVO = new MailArrivalVO();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		mailArrivalForm.setOperationalStatus("");		
			
		if(FLAG_YES.equals(mailArrivalForm.getDuplicateFlightStatus())){
			mailArrivalForm.setListFlag("");
			mailArrivalSession.setFlightValidationVO(duplicateFlightSession.getFlightValidationVO());
			mailArrivalForm.setDuplicateFlightStatus(FLAG_NO);
		}
		log.log(Log.FINE, "flightValidationVO in ListMailArrivalCommand..",
				mailArrivalSession.getFlightValidationVO());
		flightValidationVO = mailArrivalSession.getFlightValidationVO();
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			log.log(Log.FINE, "oneTimes is not null");
			Collection<OneTimeVO> resultStatus=
				oneTimes.get("flight.operation.flightlegstatus");
			flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
			flightValidationVO.setDirection(INBOUND);
		}
		mailArrivalForm.setListFlag("");
		mailArrivalSession.setFlightValidationVO(flightValidationVO);
	
		operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setFlightDate(mailArrivalVO.getArrivalDate());
		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
		operationalFlightVO.setDirection(INBOUND);
		operationalFlightVO.setPol(flightValidationVO.getLegOrigin());
		
		
		MailArrivalFilterVO mailArrivalFilterVO = mailArrivalSession.getMailArrivalFilterVO();
		
		if(mailArrivalFilterVO==null){
			mailArrivalFilterVO = constructMailArrivalFilterVO(operationalFlightVO);
			mailArrivalFilterVO.setMailStatus(mailArrivalVO.getMailStatus());
			mailArrivalFilterVO.setNextCarrierId(mailArrivalVO.getTransferCarrierId());
			mailArrivalFilterVO.setPaCode(mailArrivalVO.getArrivalPA());
			mailArrivalFilterVO.setNextCarrierCode(mailArrivalVO.getTransferCarrier());
        }
		
		
		/** For report*/
		mailArrivalForm.setCarrierId(mailArrivalFilterVO.getCarrierId());
		mailArrivalForm.setFlightSequenceNumber(mailArrivalFilterVO.getFlightSequenceNumber());
		mailArrivalForm.setLegSerialNumber(mailArrivalFilterVO.getLegSerialNumber());
		
		log.log(Log.FINE, "mailArrivalFilterVO b4 gng to server call...",
				mailArrivalFilterVO);
			try {
			    	newMailArrivalVO = new MailTrackingDefaultsDelegate().findArrivalDetails(
			    								mailArrivalFilterVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
    	  if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
    		mailArrivalSession.setMailArrivalVO(mailArrivalVO);
    		invocationContext.target = TARGET;
    		return;
    	  }else{
    		  FlightValidationVO fltVal=mailArrivalSession.getFlightValidationVO();
    	    	
    	    	mailArrivalSession.setFromScreen("MailArrival");
    	    	mailArrivalSession.setMailArrivalFilterVO(mailArrivalFilterVO);
    	    	
    	    	if(fltVal!=null && fltVal.getFlightRoute()!=null){
    	    		
    	    		
    	    		if(newMailArrivalVO.getFlightStatus()==null || "".equals(newMailArrivalVO.getFlightStatus()))
    	    		{
    	    			mailArrivalForm.setOperationalStatus("NONE");
    	    		}else{
    	    		if(oneTimes!=null){
    	    			Collection<OneTimeVO> resultStatus=
    	    				oneTimes.get("mailtracking.defaults.flightstatus");
    	    			fltVal.setOperationalStatus(findOneTimeDescription(resultStatus,newMailArrivalVO.getFlightStatus()));
    	    		}
    	    		
    	    		if("O".equals(newMailArrivalVO.getFlightStatus()))
    	    		{
    	    			mailArrivalForm.setOperationalStatus("OPEN");
    	    		}else
    	    		if("C".equals(newMailArrivalVO.getFlightStatus()))
    	    		{
    	    			mailArrivalForm.setOperationalStatus("CLOSED");
    	    			mailArrivalSession.setMessageStatus("Y");
    	    		}}
    	    		mailArrivalForm.setListFlag("");
    	    		mailArrivalSession.setFlightValidationVO(fltVal);
    	    	}
    	    	//Added by A-7794 as part of ICRD-197439
    	    	if(!Objects.equals(logonAttributes.getAirportCode(), mailArrivalForm.getArrivalPort())){
    	    		   mailArrivalForm.setDisableButtonsForAirport(FLAG_YES);
    	    		    }   else {
    	    			mailArrivalForm.setDisableButtonsForAirport(FLAG_NO);
    	    			}
    	  }
			
		
		 
    	  if(newMailArrivalVO == null){
    		  newMailArrivalVO = new MailArrivalVO();
    	  }
    	  newMailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
    	  //Modified by A-7794 as part of ICRD-197439
    	  newMailArrivalVO.setAirportCode(mailArrivalForm.getArrivalPort());
		  newMailArrivalVO.setFlightCarrierCode(mailArrivalVO.getFlightCarrierCode());
		  newMailArrivalVO.setFlightNumber(mailArrivalVO.getFlightNumber());
		  newMailArrivalVO.setArrivalDate(mailArrivalVO.getArrivalDate());
		  newMailArrivalVO.setCarrierId(mailArrivalVO.getCarrierId());
		  newMailArrivalVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		  newMailArrivalVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	
    	
    	if("Y".equals(mailArrivalSession.getMessageStatus())	){
    		log.log(Log.FINE, "**mailArrivalForm.getArrivalDate()*",
					mailArrivalForm.getArrivalDate());
				mailArrivalSession.setMessageStatus("");
    	    	Object[] obj = {newMailArrivalVO.getFlightCarrierCode(),
    	    			newMailArrivalVO.getFlightNumber(),newMailArrivalVO.getArrivalDate().toDisplayDateOnlyFormat()};
    			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailarrival.flightclosed",obj);
    			invocationContext.addError(errorVO);
        	}	
    	if("REOPENED".equals(mailArrivalSession.getMessageStatus())	){
    		log.log(Log.FINE, "**mailArrivalForm.getArrivalDate()*",
					mailArrivalForm.getArrivalDate());
			mailArrivalSession.setMessageStatus("");
    		Object[] obj = {mailArrivalForm.getFlightCarrierCode(),
					mailArrivalForm.getFlightNumber(),mailArrivalForm.getArrivalDate()};
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.flightreopened",obj));
    	}	
    	newMailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
    	newMailArrivalVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
    	log.log(Log.FINE,
				"*******MAIL ARRIVAL VOS***IN ListMailArrivalCommand***",
				newMailArrivalVO);
		mailArrivalSession.setMailArrivalVO(newMailArrivalVO);
		HashMap<String, Collection<DSNVO>> dsnvoMap = new HashMap<String,Collection<DSNVO>>();
		//Added as part of ICRD-125266
		if(newMailArrivalVO.getContainerDetails() != null && newMailArrivalVO.getContainerDetails().size() >0){
		for (ContainerDetailsVO  containerdtlsvo : newMailArrivalVO.getContainerDetails()){
			//Added as part of Bug ICRD-129392 by A-5526 starts.
			//This is to avoid the count mismatch in the summary section of containers in concurrent transactions.
			double manifestedWeight=0;
			 int manifestedBags=0;
			 double receivedWeight=0;
			 int receivedBags=0;
			//Added as part of Bug ICRD-129392 by A-5526 ends.
			Collection<DSNVO> dSNVOs =  new ArrayList<DSNVO>();
			dSNVOs = containerdtlsvo.getDsnVOs();
			if( dSNVOs != null && dSNVOs.size() >0){
				for (DSNVO  dsnvo : dSNVOs){
					//Added as part of Bug ICRD-129392 by A-5526 starts.
					//manifestedWeight=manifestedWeight+dsnvo.getWeight();
					manifestedWeight=manifestedWeight+dsnvo.getWeight().getRoundedSystemValue();//added by A-7371
					manifestedBags=manifestedBags+dsnvo.getBags();
					//receivedWeight=receivedWeight+dsnvo.getReceivedWeight();
					receivedWeight=receivedWeight+dsnvo.getReceivedWeight().getRoundedSystemValue();//added by A-7371
					receivedBags=receivedBags+dsnvo.getReceivedBags();
					//Added as part of Bug ICRD-129392 by A-5526 ends.
					Collection<DSNVO> despatchVOs = null;
					if(dsnvo.getCsgDocNum() != null && !"".equals(dsnvo.getCsgDocNum())){
						if(dsnvoMap.containsKey(dsnvo.getCsgDocNum())){
							despatchVOs = dsnvoMap.get(dsnvo.getCsgDocNum());
						}
						else
						{
							despatchVOs = new ArrayList<DSNVO>();
						}
					    despatchVOs.add(dsnvo);
					    dsnvoMap.put(dsnvo.getCsgDocNum(),despatchVOs);
					}
				}
		} //Added as part of Bug ICRD-129392 by A-5526 starts.
			containerdtlsvo.setTotalBags(manifestedBags);
			//containerdtlsvo.setTotalWeight(manifestedWeight);
			containerdtlsvo.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,manifestedWeight));//added by A-7371
			containerdtlsvo.setReceivedBags(receivedBags);
			containerdtlsvo.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,receivedWeight));//added by A-7371
			//Added as part of Bug ICRD-129392 by A-5526 ends.
			mailArrivalSession.setConsignmentMap(dsnvoMap);
		}
    }
    	mailArrivalSession.setOperationalFlightVO(operationalFlightVO);
    	//Added For Bug 89693 starts
    	mailArrivalForm.setSelectContainer(null);
    	mailArrivalForm.setChildContainer(null);
//    	Added For Bug 89693 ends
    	mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET;
    	log.exiting("ListMailArrivalCommand","execute");
    	
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

			fieldValues.add("flight.operation.flightlegstatus");
			fieldValues.add("mailtracking.defaults.flightstatus");
			
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
