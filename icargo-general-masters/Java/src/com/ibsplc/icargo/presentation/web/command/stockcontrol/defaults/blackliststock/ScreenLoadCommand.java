/*
 * ScreenLoadCommand.java Created on Sep 5, 2005
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.BlackListStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.BlackListStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1952
 * 
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String AWB = "AWB";

	private static final String S = "S";

	private static final String VOID_CHARGE_REQUIRED="cashiering.defaults.payment.voidingchargesrequired";
	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadCommand", "execute");

		BlackListStockForm frm = (BlackListStockForm) invocationContext.screenModel;
		BlackListStockSession session = getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.blackliststock");
		session.removeAllAttributes();
	
		/* Added by A-4803 for CR ICRD-3205 starts. This is to check whether 
		 * voiding charges should be considered while voiding.*/
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
		//Added by A-4803 for CR ICRD-3205 ends 
		 if("confirmStock".equals(frm.getFromScreen())){
			ConfirmStockSession confirmStockSession= getScreenSession( 
			    	"stockcontrol.defaults","stockcontrol.defaults.confirmstock");
			Collection transitStockDetails = new ArrayList();
			transitStockDetails = confirmStockSession.getSelectedTransitStockVOs();
			TransitStockVO transitStockVO = new TransitStockVO();
			if(transitStockDetails.size()!=0){
			transitStockVO = (TransitStockVO)((ArrayList)transitStockDetails).get(0);
			frm.setDocType(transitStockVO.getDocumentType());
			frm.setSubType(transitStockVO.getDocumentSubType());
			frm.setRangeFrom(transitStockVO.getMissingStartRange());
			frm.setRangeTo(transitStockVO.getMissingEndRange());
			frm.setStockHolderCode(transitStockVO.getStockHolderCode());
			frm.setRemarks(transitStockVO.getMissingRemarks());      
			frm.setFromScreen("confirmStock");
			}
		}
		
		try {
			loadSessionWithPartnerAirlines(session);
		} catch (BusinessDelegateException e) {			
			//printStackTrrace()();
			log.log(Log.SEVERE, "BusinessDelegateException caught from loadSessionWithPartnerAirlines");
		} 
		
		HashMap<String, Collection<String>> documentList = null;
		try {
			documentList = new HashMap<String, Collection<String>>(
					new DocumentTypeDelegate()
							.findAllDocuments(getApplicationSession()
									.getLogonVO().getCompanyCode()));
			//documentList.put("", null);
		} catch (BusinessDelegateException businessDelegateException) {
			//printStackTrrace()();
			log.log(Log.SEVERE, "BusinessDelegateException caught from findAllDocuments");
		}
		frm.setDocType("");
		frm.setSubType("");
		session.setDynamicDocType(documentList);
		log.exiting("ScreenLoadCommand", "execute");
		invocationContext.target = "screenload_success";

	}

	/**
	 * For #102543
	 * 
	 * @author A-2589
	 * @param session
	 * @throws BusinessDelegateException
	 * 
	 */
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
