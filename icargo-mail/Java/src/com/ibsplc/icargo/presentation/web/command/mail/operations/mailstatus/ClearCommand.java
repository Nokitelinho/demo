/*
 * ClearCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailstatus;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailStatusForm;
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
		MailStatusForm mailStatusForm = 
			(MailStatusForm)invocationContext.screenModel;
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();	

		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		mailStatusForm.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailStatusForm.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
//		mailStatusForm.setScreenStatusFlag("");
		mailStatusForm.setCarrierCode("");
		mailStatusForm.setCarrierIdr("");
		mailStatusForm.setFlightCarrierCode("");
		mailStatusForm.setFlightCarrierIdr("");
		mailStatusForm.setFlightNumber("");
		mailStatusForm.setPol("");
		mailStatusForm.setPou("");
		mailStatusForm.setPaCode("");
		mailStatusForm.setCurrentStatus("");
		mailStatusForm.setValidFlag("");
		mailStatusForm.setStatus("");

    	invocationContext.target = TARGET;
       	
    	log.exiting("ClearMailStatusCommand","execute");
    	
	}
}
