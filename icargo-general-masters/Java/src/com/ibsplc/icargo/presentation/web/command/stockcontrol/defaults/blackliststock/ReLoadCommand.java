/*
 * ReLoadCommand.java Created on March 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.blackliststock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.BlackListStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.BlackListStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-4803
 *This is used when the user clicks the close button without paying the 
 *voiding charges.
 */
public class ReLoadCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("STOCK CONTROL BLACKLIST");
	private static final String MODULE_NAME = "stockcontrol.defaults";
	private static final String SCREEN_ID =
		"stockcontrol.defaults.blackliststock";
	private static final String RELOAD_SUCCESS ="reload_success";
	private static final String AWB = "AWB";
	private static final String VOID_CHARGE_REQUIRED="cashiering.defaults.payment.voidingchargesrequired";
	private static final String S = "S";

	/**
	 *  @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws 
	CommandInvocationException {
		log.entering("ReLoadCommand","execute");
		
		BlackListStockForm frm = (BlackListStockForm) invocationContext.screenModel;
		BlackListStockSession session = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		BlacklistStockVO blacklistStockVO = new BlacklistStockVO();
		
		blacklistStockVO=session.getBlacklistStockVO();
		frm.setRangeFrom(blacklistStockVO.getRangeFrom());
		frm.setRangeTo(blacklistStockVO.getRangeTo());
		frm.setDocType(blacklistStockVO.getDocumentType());
		frm.setSubType(blacklistStockVO.getDocumentSubType());
		frm.setRemarks(blacklistStockVO.getRemarks());
		frm.setStockHolderCode(blacklistStockVO.getStockHolderCode());
		session.removeAllAttributes();
	
		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(VOID_CHARGE_REQUIRED);
		try {
				map = new SharedDefaultsDelegate().findSystemParameterByCodes
						(systemparameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "BusinessDelegateException caught from findSystemParameterByCodes");
		}
		 String voidingChargesRequired = map.get(VOID_CHARGE_REQUIRED);
		 frm.setVoidNeeded(voidingChargesRequired);
		
		try {
			loadSessionWithPartnerAirlines(session);
		} catch (BusinessDelegateException e) {	
			log.log(Log.SEVERE, "BusinessDelegateException caught from loadSessionWithPartnerAirlines");
		} 
		
		HashMap<String, Collection<String>> documentList = null;
		try {
			documentList = new HashMap<String, Collection<String>>(
					new DocumentTypeDelegate()
							.findAllDocuments(getApplicationSession()
									.getLogonVO().getCompanyCode()));
			documentList.put("", null);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "BusinessDelegateException caught from findAllDocuments");
		}
		frm.setDocType(AWB);
		frm.setSubType(S);
		session.setDynamicDocType(documentList);
		invocationContext.target = RELOAD_SUCCESS;
		
		log.exiting("ReLoadCommand","execute");
	}
	
	private void loadSessionWithPartnerAirlines(BlackListStockSession session)
	throws BusinessDelegateException {
AirlineLovFilterVO airlineLovFilterVO = new AirlineLovFilterVO();
airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
		.getCompanyCode());
airlineLovFilterVO.setIsPartnerAirline("Y");
session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(
		airlineLovFilterVO, 1));
}


}
