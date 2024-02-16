/*
 * ClearULDDiscrepancyReportCommand.java Created on Mar 20, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy.report;

//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ULDDiscrepancyReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class ClearULDDiscrepancyReportCommand extends BaseCommand {
	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String SCREENID = "uld.defaults.ulddiscrepancyreport";

	private static final String MODULE = "uld.defaults";

	private Log log = LogFactory 
			.getLogger("ClearULDDiscrepancyReportCommand");
	
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearULDDiscrepancyReportCommand---------------->>>>",
			"Entering");
		ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		ULDDiscrepancyReportForm uldDiscrepancyReportForm = 
			(ULDDiscrepancyReportForm) invocationContext.screenModel;
		uldDiscrepancyReportForm.setFromDate("");
		uldDiscrepancyReportForm.setToDate("");
		uldDiscrepancyReportForm.setUldNumber("");
		uldDiscrepancyReportForm.setAirlineCode("");
		uldDiscrepancyReportForm.setReportingAirportCode("");
		log.exiting("ClearULDDiscrepancyReportCommand---------------->>>>",
			"Exiting");
		invocationContext.target = CLEAR_SUCCESS;		
	}
}
