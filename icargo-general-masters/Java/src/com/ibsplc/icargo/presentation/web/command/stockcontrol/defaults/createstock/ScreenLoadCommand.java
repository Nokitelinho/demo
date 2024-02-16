/*
 * ScreenLoadCommand.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.createstock;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1952
 * 
 */
public class ScreenLoadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String AWB = "AWB";

	private static final String S = "S";

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
		CreateStockForm frm = (CreateStockForm) invocationContext.screenModel;

		CreateStockSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.createstock");
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		session.removeAllAttributes();
		HashMap<String, Collection<String>> documentList = null;

		try {
			loadSessionWithPartnerAirlines(session, getApplicationSession()
					.getLogonVO());
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
		}

		try {
			documentList = new HashMap<String, Collection<String>>(
					new DocumentTypeDelegate().findAllDocuments(companyCode));
			//documentList.put("",null);
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
		}

		session.setDynamicDocType(documentList);
		RangeVO row = new RangeVO();
		row.setOperationFlag(OPERATION_FLAG_INSERT);
		row.setStartRange("");
		row.setEndRange("");
		row.setNumberOfDocuments(0);

		Collection<RangeVO> rangeVO = new ArrayList<RangeVO>();
		rangeVO.add(row);  
		session.setCollectionRangeVO(rangeVO);

		frm.setDocType("");
		frm.setSubType("");
		log.exiting("ScreenLoadCommand", "execute");
		invocationContext.target = "screenload_success";
	}

	private void loadSessionWithPartnerAirlines(CreateStockSession session,
			LogonAttributes logonVO) throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO=new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(logonVO.getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(airlineLovFilterVO, 1)); 
	}

}