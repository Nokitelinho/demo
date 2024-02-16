/*
 * ClearScreenCommand.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.util.time.DateUtilities;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;


/**
 * @author A-1366
 *
 */
public class ClearScreenCommand extends BaseCommand {

	private static final String NULL_STRING="";

	private static final String ZERO = "0";

	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
	private static final String AWB = "AWB";
	private static final String S = "S";
	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
    	   AllocateStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
			AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;
			//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
			//allocateStockForm.setDocType(NULL_STRING);
			//allocateStockForm.setDocSubType(NULL_STRING);
			allocateStockForm.setDocType("");
			allocateStockForm.setDocSubType("");
			//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends

			allocateStockForm.setStockControlFor(NULL_STRING);
			allocateStockForm.setStockHolderType(NULL_STRING);

			allocateStockForm.setStockHolderCode(NULL_STRING);
			allocateStockForm.setStatus(NULL_STRING);
			allocateStockForm.setLevel(NULL_STRING);
			allocateStockForm.setAppremarks(NULL_STRING);
			allocateStockForm.setRemarks(NULL_STRING);
			allocateStockForm.setManual(false);
			//Added by A-7364 as part of ICRD-320756 starts
			allocateStockForm.setPartnerAirline(false);
			allocateStockForm.setAwbPrefix("");
			allocateStockForm.setAirlineName("");
			//Added by A-7364 as part of ICRD-320756 ends
			//allocateStockForm.setStartRange(ZERO); Added by A-7764 for ICRD-243078

			String fromDateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
			String toDateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
			allocateStockForm.setFromDate(fromDateString);
			allocateStockForm.setToDate(toDateString);
			session.setPageStockRequestVO(null);
			session.setRangeVO(null);
			invocationContext.target = "clear_success";
    }

}
