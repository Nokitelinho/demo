/*
 * CloseCustomerEnquiryCommand.java Created on Jul 16, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customerenquiry;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.CustomerEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.CustomerEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2883
 *
 */
public class CloseCustomerEnquiryCommand extends BaseCommand{

	private static final String SCREENID = "customermanagement.defaults.customerenquiry";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String BLANK ="";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");
		log.entering("CUSTOMERMANAGEMENT", "ClearCustomerEnquiryCommand");
		CustomerEnquiryForm form = (CustomerEnquiryForm)invocationContext.screenModel;
		CustomerEnquirySession session = 
			(CustomerEnquirySession)getScreenSession(MODULENAME,SCREENID);
		
		form.setCustomerCode(BLANK);
		form.setAgentCode(BLANK);
		form.setAbsoluteIndex(null);
		form.setAirportCode(BLANK);
		form.setApproverCode(BLANK);
		form.setApproverName(BLANK);
		form.setAwbNo(BLANK);
		form.setCommodity(BLANK);
		form.setCustomerGroup(BLANK);
		form.setCustomerName(BLANK);
		form.setDestination(BLANK);
		form.setEnquiryType(BLANK);
		form.setFromDate(BLANK);
		form.setGlobalCustGroup(BLANK);
		form.setEnquiryBookingList(BLANK);
		form.setOrgin(BLANK);
		form.setPrimaryContact(BLANK);
		form.setRequiredPlaced(BLANK);
		form.setRequiredReceived(BLANK);
		form.setSpotRateID(BLANK);
		form.setStation(BLANK);
		form.setStationCode(BLANK);
		form.setStatus(BLANK);
		form.setStockHolderCode(BLANK);
		form.setStockHolderName(BLANK);
		form.setStockReceived(BLANK);
		form.setStockUsed(BLANK);
		form.setToDate(BLANK);
		form.setUbrNo(BLANK);
		form.setEnquiryScreen("N");
		session.setEnquiryType(null);
		session.setListAllotmentDisplayPage(null);
		session.setListBookingDisplayPage(null);
		session.setListCharterDisplayPage(null);
		session.setListClaimDisplayPage(null);
		session.setListContractDisplayPage(null);
		session.setListInvoiceDisplayPage(null);
		session.setListMessageDisplayPage(null);
		session.setListSpotDisplayPage(null);
		session.setListStockDisplayPage(null);
		session.setStockDetailsDisplayPage(null);
		
		invocationContext.target = "success";
		
	}

	
	
}
