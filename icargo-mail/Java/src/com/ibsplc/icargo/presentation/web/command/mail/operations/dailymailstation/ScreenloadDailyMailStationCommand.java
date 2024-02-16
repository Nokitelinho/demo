/*
 * ScreenloadDailyMailStationCommand.java Created on Jul 1 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dailymailstation;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DailyMailStationForm;
/**
 * @author A-5991
 *
 */
public class ScreenloadDailyMailStationCommand extends BaseCommand {

	
	private static final String MODULE = "mail.operations";
	private static final String SCREENID ="mailtracking.defaults.DailyMailStation";
	private static final String SCREENLOAD = "screenload_success";
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		DailyMailStationForm form = (DailyMailStationForm)invocationContext.screenModel;
					
		/*LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		form.setFlightDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));	*/
		
		//Commented flightDate and Added FlightFrom and ToDate for CR_ICRD-197259
				LocalDate fromDate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
				LocalDate toDate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
				form.setFlightFromDate(fromDate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
				form.setFlightToDate(toDate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREENLOAD;
		
		
}
}


















