/*
 * ClearCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.maildiscrepancy;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailDiscrepancyForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ClearCommand  extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");	

	/**
	 * TARGET
	 */
	private static final String TARGET = "success";   
	

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {    	
    	log.entering("ClearCommand","execute");
    	
    	MailDiscrepancyForm form =(MailDiscrepancyForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		
		form.setFlightCarrierCode("");
		form.setFlightNumber("");
		form.setFlightCarrierIdr(null);
		form.setAirport("");
		form.setDiscType("");
		
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		form.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		form.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		
		invocationContext.target = TARGET;
       	
    	log.exiting("ClearCommand","execute");
    	
	}

}
