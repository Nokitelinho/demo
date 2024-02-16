/*
 * ClearCommand.java Created on JUL 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.damagemailreport;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamageMailReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ClearCommand   extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");	

	/**
	 * TARGET
	 */
	private static final String TARGET = "screenload_success";   
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailstatus"; 

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {    	
		log.entering("ClearMailStatusCommand","execute");    	  
		  
		DamageMailReportForm damageMailReportForm = (DamageMailReportForm)invocationContext.screenModel;
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();	

		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		damageMailReportForm.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		damageMailReportForm.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		damageMailReportForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		damageMailReportForm.setAirport("");
		damageMailReportForm.setDamageCode("");
		//added by A-5844 for ICRD-67196 starts
		damageMailReportForm.setFlightCarrierCode("");
		damageMailReportForm.setFlightNumber("");
		damageMailReportForm.setFlightDate("");
		damageMailReportForm.setFlightOrigin("");
		damageMailReportForm.setFlightDestination("");
		damageMailReportForm.setGpaCode("");
		damageMailReportForm.setOriginOE("");
		damageMailReportForm.setDestinationOE("");
		damageMailReportForm.setSubClassCode("");
		damageMailReportForm.setSubClassGroup("");		
		//added by A-5844 for ICRD-67196 ends
    	invocationContext.target = TARGET;
       	
    	log.exiting("ClearMailStatusCommand","execute");
    	
	}

}
