/*
 * ScreenloadReassignMailCommand.java Created on July 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.reassignmail;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class ScreenloadReassignMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.reassignmail";
   private static final String SCREEN_ID_MBE = "mailtracking.defaults.mailBagEnquiry";	
   private static final String SCREEN_ID_DSN = "mailtracking.defaults.dsnEnquiry";	
   private static final String PREASSIGNMENT_SYS = "mailtracking.defaults.acceptance.preassignmentneeded";

  
   private static final String CONST_FLIGHT = "FLIGHT";
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadReassignMailCommand","execute");
    	  
    	ReassignMailForm reassignMailForm = 
    		(ReassignMailForm)invocationContext.screenModel;
    	ReassignMailSession reassignMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	MailBagEnquirySession mailBagEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID_MBE);
    	DsnEnquirySession dsnEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID_DSN);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		
		Collection<String> codes = new ArrayList<String>();
    	codes.add(PREASSIGNMENT_SYS);
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
    	if(results != null && results.size() > 0) {
    		reassignMailForm.setPreassignFlag(results.get(PREASSIGNMENT_SYS));    		
    		log.log(Log.FINE, "****mailAcceptanceForm.getPreassignFlag()***",
					reassignMailForm.getPreassignFlag());
    	}

		
		log.log(Log.FINE, "From Screen ===>", reassignMailForm.getFromScreen());
		String selectedContainers = reassignMailForm.getContainer();
		String[] primaryKey = selectedContainers.split(",");
		if("MAILBAG_ENQUIRY".equals(reassignMailForm.getFromScreen())){
		   Collection<MailbagVO> mailBagEnquiryVOs = 
			                          mailBagEnquirySession.getMailbagVOs();
		   Collection<MailbagVO> selMailbagVOs = new ArrayList<MailbagVO>();
		   int cnt = 0;
		   int count = 0;
	       int primaryKeyLen = primaryKey.length;
	       if (mailBagEnquiryVOs != null && mailBagEnquiryVOs.size() != 0) {
	       	for (MailbagVO mailbagVO : mailBagEnquiryVOs) {
	       		String primaryKeyFromVO = String.valueOf(count);
	       		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
	       				          equalsIgnoreCase(primaryKey[cnt].trim())) {
	       			selMailbagVOs.add(mailbagVO);
	       			cnt++;
	       		}
	       		count++;
	       	  }
	       	}
	       reassignMailSession.setMailbagVOs(selMailbagVOs);
	       log.log(Log.FINE, "selMailbagVOs in RM session ===>", selMailbagVOs);
		}
		if("DSN_ENQUIRY".equals(reassignMailForm.getFromScreen())){
		   Collection<DespatchDetailsVO> despatchDetailsVOs = 
			                   dsnEnquirySession.getDespatchDetailsVOs();
		   Collection<DespatchDetailsVO> selDespatchDetailsVOs = 
			                   new ArrayList<DespatchDetailsVO>();
		   int cnt = 0;
		   int count = 0;
		   int primaryKeyLen = primaryKey.length;
		   if (despatchDetailsVOs != null && despatchDetailsVOs.size() != 0) {
		   	for (DespatchDetailsVO desVO : despatchDetailsVOs) {
		       		String primaryKeyFromVO = String.valueOf(count);
		   		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
		   				          equalsIgnoreCase(primaryKey[cnt].trim())) {
		   			selDespatchDetailsVOs.add(desVO);
		   			cnt++;
		   		}
		  		count++;
		  	  }
		   	}
		   reassignMailSession.setDespatchDetailsVOs(selDespatchDetailsVOs);
		}
		
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		reassignMailSession.setContainerVOs(containerVOs);
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		reassignMailSession.setFlightValidationVO(flightValidationVO);
		reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		reassignMailForm.setDeparturePort(logonAttributes.getAirportCode());
		
		LocalDate dat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		String date = dat.toDisplayDateOnlyFormat();
		String time = dat.toDisplayFormat("HH:mm");		
		reassignMailForm.setScanDate(date);
		reassignMailForm.setMailScanTime(time);
		
		if("FLIGHT".equals(reassignMailForm.getHideRadio())){
			reassignMailForm.setAssignToFlight("DESTINATION");
		}else if("CARRIER".equals(reassignMailForm.getHideRadio())){
			reassignMailForm.setAssignToFlight(CONST_FLIGHT);
			
		}else{
			reassignMailForm.setHideRadio("NONE");
			reassignMailForm.setAssignToFlight(CONST_FLIGHT);
			reassignMailForm.setInitialFocus(FLAG_YES);
		}
		reassignMailForm.setReassignFocus("Y");
    	invocationContext.target = TARGET;
    	log.exiting("ScreenloadReassignMailCommand","execute");
    }
       
}
