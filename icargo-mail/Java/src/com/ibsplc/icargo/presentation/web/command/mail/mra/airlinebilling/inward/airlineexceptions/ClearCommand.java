/*
 * ClearCommand.java Created on Feb 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.airlineexceptions;


import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.AirlineExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * Command class for Clearing of airlineexceptions screen.
 *
 * Revision History
 *
 * Version      Date           Author          	 Description
 *
 *  0.1 	 Feb 19, 2007		Shivjith   		Initial draft
 *
 */
public class ClearCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	
	private static final String CLASS_NAME = "ClearCommand";
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREEN_ID = 
		"mailtracking.mra.airlinebilling.inward.airlineexceptions";
	
	private static final String CLEAR_SUCCESS = "clear_success";
	
	private static final String BLANK = "";
	
	/**
	 *
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		
		AirlineExceptionsSession airLineExceptionsSession = 
			(AirlineExceptionsSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		AirlineExceptionsForm airlineExceptionsForm = 
			(AirlineExceptionsForm)invocationContext.screenModel;
		
		airLineExceptionsSession.removeAirlineExceptionsVOs();
		airLineExceptionsSession.removeAirlineExceptionsFilterVO();
		airLineExceptionsSession.setAirlineExceptionsFilterVO(null);
		airlineExceptionsForm.setAirlineCode(BLANK);
		airlineExceptionsForm.setDsn(BLANK);
		airlineExceptionsForm.setOriginOfficeOfExchange(BLANK);
		airlineExceptionsForm.setDestinationOfficeOfExchange(BLANK);
		airlineExceptionsForm.setMailCategory(BLANK);
		airlineExceptionsForm.setSubClass(BLANK);
		airlineExceptionsForm.setYear(BLANK);
		airlineExceptionsForm.setReceptacleSerialNumber(BLANK);
		airlineExceptionsForm.setHighestNumberIndicator(BLANK);
		airlineExceptionsForm.setRegisteredIndicator(BLANK);
		airlineExceptionsForm.setExceptionCode(BLANK);
		airlineExceptionsForm.setFromDate(BLANK);
		airlineExceptionsForm.setToDate(BLANK);
		airlineExceptionsForm.setInvoiceRefNo(BLANK);
		airlineExceptionsForm.setCloseStatusFlag(BLANK);
		airlineExceptionsForm.setDisplayPage("1");
		airlineExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	
}