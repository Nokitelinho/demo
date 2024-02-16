/*
/*
 * NavigateCustomerEnquiryCommand.java Created on Aug 01, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customerenquiry;

import java.util.HashMap;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
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
public class NavigateCustomerEnquiryCommand extends BaseCommand{

	private static final String SCREENID = "customermanagement.defaults.customerenquiry";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String BOOKING = "bookingdetails";
	private static final String SPOT = "spotrates";
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	private static final String YES ="Y";
	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");
	
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		log.entering("CUSTOMERMANAGEMENT", "NavigateCustomerEnquiryCommand");
		CustomerEnquiryForm form = (CustomerEnquiryForm)invocationContext.screenModel;
		//CustomerEnquiryForm formSession = (CustomerEnquiryForm)invocationContext.screenModel;
		CustomerEnquirySession session = 
			(CustomerEnquirySession)getScreenSession(MODULENAME,SCREENID);
		CustomerVO customerVO = null;
		
		
		if(session.getEnquiryDetails() == null ){
			invocationContext.target = FAILURE;
			return;
		}
		customerVO = session.getEnquiryDetails();
		form.setEnquiryScreen("Y");
		form.setCustomerName(customerVO.getCustomerName());
		form.setCompanyCode(customerVO.getCompanyCode());
		form.setCustomerCode(customerVO.getCustomerCode());
		form.setStation(customerVO.getStationCode());
		if(customerVO.getStatus() != null &&
				"A".equals(customerVO.getStatus())){
			form.setStatus("Active");
		}else{
			form.setStatus("Inactive");
		}
		form.setCustomerGroup(customerVO.getCustomerGroup());
		form.setAgentCode(customerVO.getAgentCode());
		form.setAirportCode(customerVO.getAirportCode());
		form.setPrimaryContact(customerVO.getContactCustomer());
		form.setFromDate(customerVO.getFromDate());
		form.setToDate(customerVO.getToDate());
		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if(session.getEnquiryType() != null){
			form.setEnquiryType(session.getEnquiryType());
			//formSession=session.getEnquiryForm();
			if(BOOKING.equals(session.getEnquiryType())){
				if(YES.equals(session.getEnquiryBookingList())){
					form.setEnquiryBookingList(YES);
				}
			}
			if(SPOT.equals(session.getEnquiryType())){
				if(YES.equals(session.getEnquirySpotList())){
					form.setEnquirySpotList(YES);
				}
			}
			if (session.getIndexMap() != null) {
				indexMap = session.getIndexMap();
			} else {
				indexMap = new HashMap<String, String>();
				indexMap.put("1", "1");
			}
			String strAbsoluteIndex = (String) indexMap.get(form
					.getDisplayPageNum());
			form.setAbsoluteIndex(strAbsoluteIndex);
			int nAbsoluteIndex = 0;
			if (strAbsoluteIndex != null) {
				nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
			}
		}else{
			form.setDisplayPageNum("1");
			form.setAbsoluteIndex("1");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
			clearSession();
		}
		form.setFromPage("navigateCustomer");
		invocationContext.target = SUCCESS;
		
	}

	
	private void clearSession(){
		
		CustomerEnquirySession session = 
			(CustomerEnquirySession)getScreenSession(MODULENAME,SCREENID);
		session.setListBookingDisplayPage(null);
		session.setListClaimDisplayPage(null);
		session.setListStockDisplayPage(null);
		session.setListMessageDisplayPage(null);
		session.setListAllotmentDisplayPage(null);
		session.setListSpotDisplayPage(null);
		session.setListContractDisplayPage(null);
		session.setListTerminalDisplayPage(null);
		session.setListCharterDisplayPage(null);
		session.setIndexMap(null);
		
	}
	
}
