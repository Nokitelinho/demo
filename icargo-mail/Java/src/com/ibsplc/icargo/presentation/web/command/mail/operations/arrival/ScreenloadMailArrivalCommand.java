/*
 * ScreenloadMailArrivalCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
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
public class ScreenloadMailArrivalCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
   
   private static final String MAILSTATUS = "mailtracking.defaults.mailarrivalstatus";
   private static final String CONTAINERTYPES = "mailtracking.defaults.containertype";
   private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadMailArrivalCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	mailArrivalForm.setOperationalStatus("");
    	mailArrivalSession.setMessageStatus("");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
	
		
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
	    if(oneTimes!=null){
		    Collection<OneTimeVO> mailStatusVOs = oneTimes.get(MAILSTATUS);
		    Collection<OneTimeVO> containerTypes = oneTimes.get(CONTAINERTYPES);
		    mailArrivalSession.setOneTimeMailStatus(mailStatusVOs);
		    mailArrivalSession.setOneTimeContainerType(containerTypes);
	    }
	    //Added by A-5945 for ICRD-104487
	    Collection<String> codes = new ArrayList<String>();
	    codes.add(MAIL_COMMODITY_SYS);
	    Map<String, String> results = null;
	    try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
    	if(results != null && results.size() > 0) {
    		mailArrivalSession.setMailCommidityCode(results.get(MAIL_COMMODITY_SYS));
    		log.log(Log.FINE, "*******DEnsity Factor******", results.get(MAIL_COMMODITY_SYS));
    	}
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		mailArrivalSession.setMailArrivalVO(mailArrivalVO);
		mailArrivalForm.setListFlag("FAILURE");
		mailArrivalForm.setOperationalStatus("");
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		mailArrivalSession.setFlightValidationVO(flightValidationVO);
		mailArrivalForm.setInitialFocus(FLAG_YES);
		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mailArrivalForm.setArrivalPort(logonAttributes.getAirportCode());
    	invocationContext.target = TARGET;
       	
    	log.exiting("ScreenloadMailArrivalCommand","execute");
    	
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
			fieldValues.add(MAILSTATUS);
			fieldValues.add(CONTAINERTYPES);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
    
       
}
