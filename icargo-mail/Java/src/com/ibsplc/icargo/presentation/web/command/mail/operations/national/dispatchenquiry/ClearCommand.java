/**
 *  ClearCommand.java Created on February 20, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.dispatchenquiry;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.DispatchEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ClearCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.dispatchEnquiry";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		log.entering("ClearCommand", "execute");
		DispatchEnquiryForm dispatchEnquiryForm = (DispatchEnquiryForm) invocationcontext.screenModel;
		DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		dispatchEnquiryForm.setCategory("");
		dispatchEnquiryForm.setConsignmentNo("");
		dispatchEnquiryForm.setDestination("");
		dispatchEnquiryForm.setFlightDate("");
		dispatchEnquiryForm.setFlightNo("");
		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		dispatchEnquiryForm.setFromDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		dispatchEnquiryForm.setToDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		dispatchEnquiryForm.setOrigin("");		
		dispatchEnquiryForm.setUserId("");
		dispatchEnquiryForm.setStatus("");
		dispatchEnquiryForm.setDisplayPage("");
		dispatchEnquiryForm.setLastPageNum("");	
		//Added by A-4810 as part of bug-fix icrd-15125
		dispatchEnquiryForm.setTransferFlag("");
		dispatchEnquirySession.setDespatchDetailsVO(null);
		dispatchEnquirySession.setDSNEnquiryFilterVO(null);
		dispatchEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);
		invocationcontext.target = CLEAR_SUCCESS;
		log.exiting("ClearCommand", "execute");


	}

}
