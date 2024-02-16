/*
 * ClearCommand.java Created on Jan 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.listawbstockhistory;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRangeHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListAwbStockHistoryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @Author-3155 & A-3184
 * 
 */
 
public class ClearCommand extends BaseCommand { 

	

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand 
	 * @author A-3155 & A-3184
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */   

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ClearCommand","execute");
		ListAwbStockHistoryForm listAwbStockHistoryForm = (ListAwbStockHistoryForm) invocationContext.screenModel;
		ListStockRangeHistorySession session =
			(ListStockRangeHistorySession)getScreenSession("stockcontrol.defaults","stockcontrol.defaults.listawbstockhistory");
 		
		
		listAwbStockHistoryForm.setRangeFrom("");
		listAwbStockHistoryForm.setRangeTo("");
		listAwbStockHistoryForm.setStockHolderCode("");
		listAwbStockHistoryForm.setStockStatus("");
		listAwbStockHistoryForm.setStockType("");
		listAwbStockHistoryForm.setAccountNumber("");
		listAwbStockHistoryForm.setHistory(false);
		listAwbStockHistoryForm.setAwb("");
		listAwbStockHistoryForm.setDocType("");
		listAwbStockHistoryForm.setDocSubType("");
		//Added by A-4803 for BUG ICRD-14359
		listAwbStockHistoryForm.setUserId("");
		listAwbStockHistoryForm.setPartnerAirline(false);
		listAwbStockHistoryForm.setAirlineName("");
		listAwbStockHistoryForm.setAwbPrefix("");
		//session.setStockRangeHistoryVOs(null);
	    //session.setStockRangeHistoryVO(null);
		session.removePageStockRangeHistoryVOs();//added by T-1927 for ICRD-19368
		
		String dateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
		listAwbStockHistoryForm.setStartDate(dateString);
		listAwbStockHistoryForm.setEndDate(dateString);
		
		
		invocationContext.target = "screenload_success";
		log.exiting("ClearCommand","execute");
	} 
}

