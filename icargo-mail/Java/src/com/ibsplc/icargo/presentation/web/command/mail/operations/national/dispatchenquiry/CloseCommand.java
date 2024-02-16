/**
 * CloseCommand.java Created on February 22, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.dispatchenquiry;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
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
public class CloseCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String SCREENID = "mailtracking.defaults.national.dispatchEnquiry";	
	private static final String MODULE_NAME = "mail.operations";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException	 * 
	 */
	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {
		
		log.entering("CloseCommand", "execute");
		DispatchEnquiryForm dispatchEnquiryForm = (DispatchEnquiryForm) invocationcontext.screenModel;
		DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREENID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		dispatchEnquiryForm.setFlightCarrierCode(logonAttributes.getOwnAirlineCode());
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
		dispatchEnquirySession.setDSNEnquiryFilterVO(null);
		dispatchEnquirySession.setDespatchDetailsVO(null);
		invocationcontext.target = CLOSE_SUCCESS;
		log.exiting("CloseCommand", "execute");
		
	}

}
