/*
 * ScreenLoadCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockrequest;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
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
		ListStockRequestForm listStockRequestForm = (ListStockRequestForm) invocationContext.screenModel;
		ListStockRequestSession session = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.liststockrequest");
		session.removeAllAttributes();
		
		try {
			loadSessionWithPartnerAirlines(session);
		} catch (BusinessDelegateException e) {			
//printStackTrrace()();
		}
		
		Map<String, Collection<OneTimeVO>> map = handleScreenLoadDetails(getApplicationSession()
				.getLogonVO().getCompanyCode());
		HashMap<String, Collection<String>> documentList = null;
		try {
			documentList = new HashMap<String, Collection<String>>(
					new DocumentTypeDelegate()
							.findAllDocuments(getApplicationSession()
									.getLogonVO().getCompanyCode()));
			//commented as part of icrd-4259 by A-5117
			//documentList.put("", null);
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
		}
		//docType and subType set to null part of icrd-4259 by A-5117
		listStockRequestForm.setDocType("");
		listStockRequestForm.setSubType("");
		session.setDynamicOptionList(documentList);
		if (map != null) {

			session.setOneTimeStatus(map.get("stockcontrol.defaults.status"));
			session.setOneTimeRequestedBy(map
					.get("stockcontrol.defaults.stockholdertypes"));

		}
		LogonAttributes logon = null;
		try {
			logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		if(Objects.nonNull(logon)){
			listStockRequestForm.setPartnerPrefix(logon.getOwnAirlineNumericCode());
		}		

		listStockRequestForm.setToDate(DateUtilities
				.getCurrentDate(CALENDAR_DATE_FORMAT));
		listStockRequestForm.setFromDate(DateUtilities
				.getCurrentDate(CALENDAR_DATE_FORMAT));

		invocationContext.target = "screenload_success";
	}

	/**
	 * This method will be invoked at the time of screen load
	 * 
	 * @param companyCode
	 * @return oneTimes
	 */
	public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {

			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add("stockcontrol.defaults.status");
			fieldTypes.add("stockcontrol.defaults.stockholdertypes");

			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldTypes);

			Collection<OneTimeVO> stockHolder = oneTimes
					.get("stockcontrol.defaults.stockholdertypes");
			ListStockRequestSession session = getScreenSession(
					"stockcontrol.defaults",
					"stockcontrol.defaults.liststockrequest");
			Collection<StockHolderPriorityVO> stockHolderpriorityVos = new StockControlDefaultsDelegate()
					.findStockHolderTypes(companyCode);
			populatePriorityStockHolders(stockHolderpriorityVos, stockHolder,
					session);

		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "BusinessDelegateException caught from findStockHolderTypes");
		}
		return oneTimes;
	}

	/**
	 * This method will be invoked at the time of screen load
	 * 
	 * @param stockHolderpriorityVos
	 * @param stockHolder
	 * @param session
	 * @return
	 */
	private void populatePriorityStockHolders(
			Collection<StockHolderPriorityVO> stockHolderpriorityVos,
			Collection<OneTimeVO> stockHolder, ListStockRequestSession session) {
		if (stockHolderpriorityVos != null) {
			for (StockHolderPriorityVO priorityVO : stockHolderpriorityVos) {
				for (OneTimeVO onetime : stockHolder) {
					if (onetime.getFieldValue().equals(
							priorityVO.getStockHolderType())) {
						priorityVO.setStockHolderDescription(onetime
								.getFieldDescription());
					}
				}
			}
			session.setPrioritizedStockHolders(stockHolderpriorityVos);

		}
	}

	/**
	 * Added for #102543 base product enhancement
	 * 
	 * @author A-2589
	 * @param session
	 * @throws BusinessDelegateException
	 * 
	 */
	private void loadSessionWithPartnerAirlines(ListStockRequestSession session)
			throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO = new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(
				airlineLovFilterVO, 1));
	}
}
