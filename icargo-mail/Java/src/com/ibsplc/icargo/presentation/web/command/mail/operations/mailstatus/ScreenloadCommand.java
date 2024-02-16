/*
 * ScreenloadCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailstatus;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailStatusSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailStatusForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenloadCommand extends BaseCommand{

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
		log.entering("ScreenloadMailStatusCommand","execute");    	  
		MailStatusForm mailStatusForm = (MailStatusForm)invocationContext.screenModel;
		MailStatusSession mailStatusSession = getScreenSession(MODULE_NAME,SCREEN_ID);    	
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();	
		
		/*
		 * Populating the Status Combo with the Specific Status
		 */
		Collection<String> currentStatus=new ArrayList<String>();
		currentStatus.add(MailConstantsVO.EXPECTED_MAIL);
		currentStatus.add(MailConstantsVO.EXPECTED_MAIL_CARDIT);
		//currentStatus.add(MailConstantsVO.EXPECTED_MAIL_ALLOCATION);
		currentStatus.add(MailConstantsVO.EXPECTED_MAIL_TRANSHIPS);
		currentStatus.add(MailConstantsVO.MAIL_WITHOUT_CARDIT);
		currentStatus.add(MailConstantsVO.MAIL_NOT_UPLIFTED);
		currentStatus.add(MailConstantsVO.MAIL_UPLIFTED_WITHOUT_CARDIT);
		//currentStatus.add(MailConstantsVO.MAIL_CARDIT_NOT_POSSESSED); commented by MANISH for ICRD-92082
		currentStatus.add(MailConstantsVO.MAIL_UPLIFTED_NOT_DELIVERED);
		currentStatus.add(MailConstantsVO.MAIL_ARRIVED_NOT_DELIVERED);
		currentStatus.add(MailConstantsVO.MAIL_DELIVERED);
		currentStatus.add(MailConstantsVO.MAIL_DELIVERED_WITHOUT_CARDIT);
		currentStatus.add(MailConstantsVO.MAIL_ACCEPTED_NOT_UPLIFTED);
		
		mailStatusSession.setCurrentStatus(currentStatus);	
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		mailStatusForm.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailStatusForm.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailStatusForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		invocationContext.target = TARGET;

		log.exiting("ScreenLoadCommand","execute");

	}	
}
