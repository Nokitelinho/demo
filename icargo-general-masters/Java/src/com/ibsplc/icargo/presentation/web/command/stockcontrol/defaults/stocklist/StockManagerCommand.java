/*
 * StockManagerCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stocklist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockManagerSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class StockManagerCommand extends BaseCommand {
	private static final String MODULE_NAME = "stockcontrol.defaults";

	private Log log = LogFactory.getLogger("STOCK MANAGER COMMAND ");

	private static final String SCREEN_ID = "stockcontrol.defaults.stockmanager";

	private static final String SYSTEMPARAMETERVALUE = 
		"stock.defaults.defaultstockholdercodeforcto";

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		StockListForm form = (StockListForm) invocationContext.screenModel;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		StockControlDefaultsDelegate delegate = new StockControlDefaultsDelegate();
		StockManagerSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		StockListSession stockListSession = getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.cto.stocklist");
		AreaDelegate areaDelegate = new AreaDelegate();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		StockFilterVO stockFilterVO = new StockFilterVO();
		Collection<DocumentVO> docvoCol = stockListSession.getDocumentVO();
		String[] rowid = form.getRowId();
		String[] row = rowid[0].split("-");
		log.log(Log.FINE, "-------->Inside rowid rowid------->", String.valueOf(rowid[0]));
		form
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);

		try {
			AirlineValidationVO airlineVO = airlineDelegate.validateAlphaCode(
					logonAttributes.getCompanyCode(), row[0].toUpperCase());
			stockFilterVO.setAirlineIdentifier(airlineVO
					.getAirlineIdentifier());
			stockFilterVO.setAirlineCode(row[0].trim().toUpperCase());
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}

		Collection<String> stockHolderCodes = new ArrayList<String>();
		stockHolderCodes.add(SYSTEMPARAMETERVALUE);

		Map<String, String> stockHolderCodeMap = null;

		try {
			stockHolderCodeMap = areaDelegate.
			findAirportParametersByCode(
					logonAttributes.getCompanyCode(),
					logonAttributes.getAirportCode(), stockHolderCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			// To be reviewed Auto-generated catch block
//printStackTrrace()();
		}

		if (stockHolderCodeMap != null) {
			session.setStockHolderCode((String) stockHolderCodeMap
					.get(SYSTEMPARAMETERVALUE));
		}
		stockFilterVO.setDocumentType(row[1]);
		for (DocumentVO vo : docvoCol) {
			if (row[2].trim().equals(vo.getDocumentSubTypeDes().trim())) {
				stockFilterVO.setDocumentSubType(vo.getDocumentSubType());
			}
		}
		String stockholdercode = getStockHolder(logonAttributes);
		stockFilterVO.setStockHolderCode(stockholdercode);
		stockFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		stockFilterVO.setAirportCode(logonAttributes.getAirportCode());
		stockListSession.setFilterFromList(stockFilterVO);
		form.setForMessage("Y");
		form.setFromStockList("Y");
		session.setDocumentValues(stockListSession.getDocumentValues());
		session.setDocumentVO(stockListSession.getDocumentVO());
		invocationContext.target = "stockmanagerscreenload_success";
	}

	/**
	 * @return
	 */
	private String getStockHolder(LogonAttributes logonAttributes) {

		HashMap<String, String> sysparVal = new HashMap<String, String>();
		AreaDelegate areaDelegate = new AreaDelegate();
		Collection<String> sysPar = new ArrayList<String>();
		sysPar.add(SYSTEMPARAMETERVALUE);
		try {
			sysparVal = (HashMap<String, String>) areaDelegate.
			findAirportParametersByCode(
					logonAttributes.getCompanyCode(),
					logonAttributes.getAirportCode(), sysPar);
					
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		return sysparVal.get(SYSTEMPARAMETERVALUE);

	}

}
