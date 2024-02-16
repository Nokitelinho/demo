/*
 * ClearCommand.java Created on Jul 09, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchFlightForm;

/**
 * @author A-3817
 *
 */
public class ClearCommand extends BaseCommand {
	private static final String SCREENID = "mailtracking.defaults.searchflight";

	private static final String MODULENAME = "mail.operations";
	private static final String BLANK = "";
	private static final String CLEAR_SUCCESS = "clear_success";

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		SearchFlightForm form = (SearchFlightForm) invocationContext.screenModel;
		SearchFlightSession session = (SearchFlightSession) getScreenSession(
				MODULENAME, SCREENID);
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		form.setCarrierCode(logonAttributes.getOwnAirlineCode());
		form.setFlightNumber(BLANK);
		form.setListStatus(BLANK);
		
		if(MailConstantsVO.OPERATION_INBOUND.equals(form.getFromScreen())){
			form.setArrivalPort(logonAttributes.getAirportCode());
			form.setDepartingPort(BLANK);
		}
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(form.getFromScreen())){
			form.setDepartingPort(logonAttributes.getAirportCode());
			form.setArrivalPort(BLANK);
		}
		form.setArrivalDate(BLANK);
		
		form.setDepartureDate(BLANK);
		form.setDepFromDate(BLANK);
		form.setDepToDate(BLANK);
		form.setArrFromDate(BLANK);
		form.setArrToDate(BLANK);
		form.setMailStatus(BLANK);
		session.setOperationalFlightVOs(null);
		invocationContext.target=CLEAR_SUCCESS;
		
	}
	
	
}
