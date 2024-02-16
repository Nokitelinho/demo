/*
 * ClearCommand.java Created on Apr 03, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermailmanifest;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailManifestForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2553
 *
 */
public class ClearCommand extends BaseCommand {
	 private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * declaring
	    * TARGET , MODULE & SCREENID
	    */
	   private static final String TARGET = "clear_success";
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.transfermailmanifest";
	   /**
	    * @param invocationContext
	    * @throws CommandInvocationException
	    */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ClearCommand","execute");
		TransferMailManifestForm transferMailManifestForm = (TransferMailManifestForm)invocationContext.screenModel;
		TransferMailManifestSession transferMailManifestSession = getScreenSession(MODULE_NAME, SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		transferMailManifestForm.setReferenceNumber("");
		transferMailManifestForm.setInCarrierCode("");
		transferMailManifestForm.setInFlightNumber("");
		transferMailManifestForm.setOutCarrierCode("");
		transferMailManifestForm.setOutFlightNumber("");
		transferMailManifestForm.setInFlightDate("");
		transferMailManifestForm.setOutFlightDate("");
		transferMailManifestForm.setAirportCode("");
		transferMailManifestForm.setTransferStatus("");
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		transferMailManifestForm.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		transferMailManifestForm.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		transferMailManifestSession.setTransferManifestVOs(null);
		invocationContext.target = TARGET;
       	
    	log.exiting("ClearCommand","execute");
		
		
	}
	

}
