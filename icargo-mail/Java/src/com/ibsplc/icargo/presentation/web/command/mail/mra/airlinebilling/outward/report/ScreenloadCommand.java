/*
 * ScreenloadCommand.java Created on March 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.report;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.report.OutwardBillingReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 *@ author Shivjith
 * Command class for screenload of airlineexceptions screen.
 *
 * Revision History
 *
 * Version      Date           		Author          		Description
 *
 *  0.1         March 15, 2007   Shivjith   			Initial draft
 *
 */
public class ScreenloadCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	private static final String CLASS_NAME = "ScreenloadCommand";
	
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	//private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.reports";
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	private static final String BLG_TYPE_OUTWD = "O";	

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
		
		OutwardBillingReportForm form = 
			(OutwardBillingReportForm)invocationContext.screenModel;
		form.setBillingType(BLG_TYPE_OUTWD);
		
		invocationContext.target = SCREENLOAD_SUCCESS;
		
		log.exiting(CLASS_NAME, "execute");
	}

}