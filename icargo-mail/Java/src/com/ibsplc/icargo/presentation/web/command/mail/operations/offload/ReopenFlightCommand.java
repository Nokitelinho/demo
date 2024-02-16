/*
 * ReopenFlightCommand.java Created on July 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.offload;

import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OffloadSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OffloadForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1861
 *
 */
public class ReopenFlightCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILTRACKING,ReopenFlightCommand");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET_SUCCESS = "reopen_success";
	   private static final String TARGET_FAILURE = "reopen_failure";
	
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.offload";	
	   
	   private static final String OUTBOUND = "O";

		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ReopenFlightCommand","execute");
	    	OffloadForm offloadForm = 
	    		(OffloadForm)invocationContext.screenModel;   	
	    	OffloadSession offloadSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = null;
	    	
	    	FlightValidationVO flightValidationVO = offloadSession.getFlightValidationVO();
	    	
	    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate 
	    			= new MailTrackingDefaultsDelegate();
	    	
	    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
	    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
	    	operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	operationalFlightVO.setDirection(OUTBOUND);
	    	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
	    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
	    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
	    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
	    	//operationalFlightVO.setPou();
	    	
	    	log.log(Log.FINE, "\n operationalFlightVO----------> \n",
					operationalFlightVO);
			try{
	    		
	    		mailTrackingDefaultsDelegate.reopenFlight(operationalFlightVO);
	    		
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    	}
	    	if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
	    	
	    	Object[] obj = {flightValidationVO.getCarrierCode(),
	    			flightValidationVO.getFlightNumber(),
	    			flightValidationVO.getApplicableDateAtRequestedAirport().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT)};
			invocationContext.addError(new ErrorVO("mailtracking.defaults.offload.msg.err.flightReopenedSuccessfully",obj));				
			offloadForm.setCloseflight("Y");
	    	invocationContext.target = TARGET_SUCCESS;
	    	
	    	log.exiting("ReopenFlightCommand","execute");
	}
}
