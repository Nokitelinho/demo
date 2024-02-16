/*
 * CloseDailyMailStationCommand.java Created on Jul 1 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dailymailstation;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DailyMailStationForm;
/**
 * @author A-5991
 *
 */
public class CloseDailyMailStationCommand extends BaseCommand{


	private static final String MODULE = "mail.operations";
	private static final String SCREENID ="mailtracking.defaults.DailyMailStation";
	private static final String CLOSE_SUCCESS = "close_success";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {

		DailyMailStationForm form = (DailyMailStationForm)invocationContext.screenModel;
		
		form.setCarrierCode("");
		form.setFlightNumber("");
		form.setDestination("");
		form.setFlightDate("");
		
		form.setAccarrierCode("");
		form.setAccompanyCode("");
		form.setAcdestination("");
		//form.setAcfilghtDate("");
		//Commented flightDate and Added FlightFrom and ToDate for CR_ICRD-197259
		form.setAcfilghtFromDate("");
		form.setAcfilghtToDate("");
		form.setAcflightCarrireID(0);
		form.setAcflightNumber("");
		form.setAcflightSeqNumber(0);
		form.setAcorigin("");
		form.setValidreport("INVALD");

		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = CLOSE_SUCCESS;
	}



}