/**
 * ScreenLoadCommand.java Created on February 17, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.dispatchenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.DispatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ScreenLoadCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.dispatchEnquiry";
	private static final String MAIL_STATUS = "mailtracking.defaults.national.despatchstatus";
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	private static final String DIRECTION = "mailtracking.defaults.operationtype";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		log.entering("ScreenLoadCommand", "execute");
		DispatchEnquiryForm dispatchEnquiryForm = (DispatchEnquiryForm) invocationcontext.screenModel;
		DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(MAIL_STATUS);
		fieldTypes.add(CATEGORY);
		fieldTypes.add(DIRECTION);
		try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		dispatchEnquiryForm.setFlightCarrierCode(logonAttributes.getOwnAirlineCode());
		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		dispatchEnquiryForm.setFromDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		dispatchEnquiryForm.setToDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		dispatchEnquiryForm.setAirport(logonAttributes.getAirportCode());		
		dispatchEnquirySession.setDespatchDetailsVO(null);
		dispatchEnquiryForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		dispatchEnquirySession.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		invocationcontext.target = SCREENLOAD_SUCCESS;
		log.exiting("ScreenLoadCommand", "execute");

	}

}
