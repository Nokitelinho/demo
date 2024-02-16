/*
 * ClearCommand.java Created on Jul 1 2016
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagreconciliation;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailbagReconciliationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailbagReconciliationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ClearCommand extends BaseCommand {
	
	
	
	private static final String MODULENAME = "mail.operations";
	
	private static final String SCREENID = "mailtracking.defaults.MailbagReconciliation";
	
	private static final String CLEAR_SUCCESS="clear_success";
	
	private Log log = LogFactory.getLogger("Clear Forwarded Area command");

	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "ClearCommand");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	
	    MailbagReconciliationForm form = 
    		(MailbagReconciliationForm)invocationContext.screenModel;
	    MailbagReconciliationSession session = 
    		getScreenSession(MODULENAME, SCREENID);
	    form.setOrginOE("");
	    form.setDestinationOE("");
	    form.setSelectedMailBagId("");
	    form.setWeight("");
	    form.setDisplayPage("1");
	    form.setControlReferenceNumber("");
	    form.setCategory("");
	    form.setSubClass("");
	    form.setYear("");
	    form.setDsn("");
	    form.setRsn("");
	    form.setResidit("");
	    form.setConsignmentId("");
	    form.setPaCode("");
	    form.setCarditOrgin("");
	    form.setCarditDestination("");
	    form.setAirport("");
	    form.setCarditStatus("");
	    form.setMessageMissing("");
	    LocalDate fromdate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		LocalDate todate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		fromdate.addDays(-30);
		form.setFromDate(fromdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		form.setToDate(todate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
	    form.setDelayPeriod(false);
	    session.removeMailReconciliationFilterVO();
	    session.removeMailReconciliationDetailsVOs();
		
		invocationContext.target=CLEAR_SUCCESS;
	}
}
