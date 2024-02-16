/*
 * ClearCommand.java Created on May18,2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stockdetails;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

public class ClearCommand  extends BaseCommand{

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String AWB = "AWB";
	private static final String S = "S";
	private static final String NULL_STRING="";
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
	
		log.entering("ClearStockDetailsCommand", "execute");
		StockDetailsForm stockDetailsForm = (StockDetailsForm) invocationContext.screenModel;
		StockDetailsSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.stockdetails");
		
		stockDetailsForm.setDocType("");
		stockDetailsForm.setSubType("");
		stockDetailsForm.setStockHolderType("");
		
		stockDetailsForm.setStockHolderCode(NULL_STRING);
		stockDetailsForm.setDisplayPage("1");
		stockDetailsForm.setLastPageNum("0");
		stockDetailsForm.setAbsoluteIndex("0");
		String fromDateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
		String toDateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
		stockDetailsForm.setFromDate(fromDateString);
		stockDetailsForm.setToDate(toDateString);
		session.removeStockDetails();
		session.removeStockDetailsVO();
		invocationContext.target = "clear_success";
	}
}
