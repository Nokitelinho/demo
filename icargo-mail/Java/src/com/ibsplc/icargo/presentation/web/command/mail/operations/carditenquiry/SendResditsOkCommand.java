/*
 * SendResditsOkCommand.java Created on Jul 1 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.CarditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class SendResditsOkCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "sendresditsok_success";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.carditenquiry";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("SendResditsOkCommand","execute");
    	CarditEnquiryForm carditEnquiryForm = 
    		(CarditEnquiryForm)invocationContext.screenModel;
    	CarditEnquirySession carditEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	CarditEnquiryVO  carditEnquiryVO = carditEnquirySession.getCarditEnquiryVO();
    	carditEnquiryVO.setResditEventCode(carditEnquiryForm.getEvent());
    	carditEnquiryVO.setResditEventPort(carditEnquiryForm.getPort());
    	carditEnquiryVO.setFlightType(MailConstantsVO.FLIGHT_TYP_CARDIT);
    	
    	Collection<ContainerVO> containerVOs = carditEnquirySession.getContainerVOs();
    	String eventDate=carditEnquiryForm.getEventDate();
    	String eventTime=carditEnquiryForm.getEventTime();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	if("".equals(carditEnquiryForm.getPort())||carditEnquiryForm.getPort().length()==0){
    		errors.add(new ErrorVO("mailtracking.defaults.reassignmail.emptyport"));    		
		}
		if("".equals(carditEnquiryForm.getEventDate())||carditEnquiryForm.getEventDate().length()==0){
			errors.add(new ErrorVO("mailtracking.defaults.reassignmail.emptyeventdate"));			
		}
		if("".equals(carditEnquiryForm.getEventTime())||carditEnquiryForm.getEventTime().length()==0){
			errors.add(new ErrorVO("mailtracking.defaults.reassignmail.emptyeventtime"));			 
		}
    	
    	if(("Delivered").equals(carditEnquiryForm.getEvent())){ 
    		if("".equals(carditEnquiryForm.getDeliveredPa())||carditEnquiryForm.getDeliveredPa().length()==0){
    			errors.add(new ErrorVO("mailtracking.defaults.reassignmail.emptydeliveredpa"));    			
    		}
    	}
    	if(("Uplifted").equals(carditEnquiryForm.getEvent())){
    		if("".equals(carditEnquiryForm.getPou())||carditEnquiryForm.getPou().length()==0){
    			errors.add(new ErrorVO("mailtracking.defaults.reassignmail.emptypou"));    			 
    		}
    	}
    	if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			return;
    	}

    	log.log(Log.FINE, "**********", containerVOs);
			for(ContainerVO containerVO : containerVOs){
	    		if(eventDate != null && eventTime != null) {
						LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						String eventDT = new StringBuilder(eventDate).append(" ")
						                 .append(eventTime).append(":00").toString();//					
						containerVO.setEventTime(sd.setDateAndTime(eventDT,false));
						if(("Recieved").equals(carditEnquiryForm.getEvent())){  
							containerVO.setEventCode(MailConstantsVO.RESDIT_RECEIVED);
							containerVO.setAssignedPort(carditEnquiryForm.getPort());
				    	}
						if(("Delivered").equals(carditEnquiryForm.getEvent())){  
				    		containerVO.setEventCode(MailConstantsVO.RESDIT_DELIVERED);
				    		containerVO.setAssignedPort(carditEnquiryForm.getPort());
				    	}
						if(("Uplifted").equals(carditEnquiryForm.getEvent())){
				    		containerVO.setEventCode(MailConstantsVO.RESDIT_UPLIFTED);
				    	}
						log.log(Log.FINE, "%%%%%%%%%%%%%%%");						
				}
			}    	
	    	FlightValidationVO flightValidationVO = carditEnquirySession.getFlightValidationVO();
	    	log.log(Log.FINE, "&&&&&&&&&&&&&&&&&&flightValidationVO",
					flightValidationVO);
			if(("Recieved").equals(carditEnquiryForm.getEvent())){ 
	    		carditEnquiryVO.setUnsendResditEvent(MailConstantsVO.RESDIT_RECEIVED);
	    		carditEnquiryVO.setResditEventCode(MailConstantsVO.RESDIT_RECEIVED);
	    		
	    	}
	    	if(("Delivered").equals(carditEnquiryForm.getEvent())){ 
	    		carditEnquiryVO.setUnsendResditEvent(MailConstantsVO.RESDIT_DELIVERED);
	    		carditEnquiryVO.setResditEventCode(MailConstantsVO.RESDIT_DELIVERED);
	    		for(ContainerVO containerVO : containerVOs){
	    			containerVO.setHandedOverPartner(carditEnquiryForm.getDeliveredPa());
	    			containerVO.setPaCode(carditEnquiryForm.getDeliveredPa());
	    		}
	    	}
	    	if(("Uplifted").equals(carditEnquiryForm.getEvent())){
	    		carditEnquiryVO.setUnsendResditEvent(MailConstantsVO.RESDIT_UPLIFTED);
	    		carditEnquiryVO.setResditEventCode(MailConstantsVO.RESDIT_UPLIFTED);
	    		for(ContainerVO containerVO : containerVOs){
	    			containerVO.setAssignedPort(carditEnquiryForm.getPort());
	    			containerVO.setPou(carditEnquiryForm.getPou());
	    			containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
	    			containerVO.setSegmentSerialNumber(0);
	    			containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
	    			containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	    			containerVO.setCarrierCode(flightValidationVO.getCarrierCode());
	    			containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());  
	    			log.log(Log.FINE, "#######");	
	    		}
	    	}
    	
    	carditEnquiryVO.setContainerVos(containerVOs);
    	/*
   	     * Added By Paulson  
    	 * Since this Command is Specific for the container to be used 
    	 * 
    	 * 
    	 * 
    	 */
    	carditEnquiryVO.setMailbagVos(null);
    	log.log(Log.FINE, "***********carditEnquiryVO*********",
				carditEnquiryVO);
		try{
   	     	new MailTrackingDefaultsDelegate().sendResdit(carditEnquiryVO);

   	    }catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
   	    ErrorVO error = new ErrorVO(
   	    	"mailtracking.defaults.carditenquiry.msg.info.sendsuccessfully");
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		invocationContext.addError(error);    	
		
		carditEnquirySession.removeCarditEnquiryVO();
		carditEnquiryForm.setCarrierCode("");
		carditEnquiryForm.setConsignmentDocument("");
		carditEnquiryForm.setDespatchSerialNumber("");
		carditEnquiryForm.setDoe("");
		carditEnquiryForm.setFlightDate("");
		carditEnquiryForm.setFlightNumber("");
		carditEnquiryForm.setFromDate("");
		carditEnquiryForm.setMailCategoryCode("");
		carditEnquiryForm.setMailSubclass("");
		carditEnquiryForm.setOoe("");
		carditEnquiryForm.setReceptacleSerialNumber("");
		carditEnquiryForm.setResdit("");
		carditEnquiryForm.setSearchMode("");
		carditEnquiryForm.setToDate("");
		carditEnquiryForm.setYear("");		
		carditEnquiryForm.setDeparturePort("");
		carditEnquiryForm.setFlightType("");
		carditEnquiryForm.setYear("");    	
		carditEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		carditEnquiryForm.setPopUpClose("CLOSEPOPUP");
    	invocationContext.target = TARGET;
    	log.exiting("SendResditsOkCommand","execute");
    	
    }

    
}
