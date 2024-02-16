/*
 * ListStockRangeCommand.java Created on Jan 07, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.returnstockrange;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReturnStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReturnStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-5642
 * The Class ListStockRangeCommand.java.
 */
public class ListStockRangeCommand extends BaseCommand {
	
	private static final String SCREEN_ID = "stockcontrol.defaults.returnstockrange";
	
	private static final String MODULE_NAME = "stockcontrol.defaults";
	
	private static final String ONE = "1";
	
	private static final String ACTION_SUCCESS = "screenload_success";
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {	
		
		ReturnStockForm returnStockForm = (ReturnStockForm) invocationContext.screenModel;
		ReturnStockSession returnStockSession = (ReturnStockSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		HashMap<String, String> indexMap = getIndexMap(
				returnStockSession.getIndexMap(), invocationContext);
		HashMap finalMap = null;
		if (returnStockSession.getIndexMap() != null) {
			indexMap = returnStockSession.getIndexMap();
		}
		if (indexMap == null) {
			indexMap = new HashMap();
			indexMap.put(ONE, ONE);
		}
		int nAbsoluteIndex = 0;
		String dispPage = returnStockForm.getDisplayPage();
		int displayPage = 1;
		if (returnStockForm.getDisplayPage() != null && returnStockForm.getDisplayPage().trim().length() > 0) {		
			displayPage = Integer.parseInt(returnStockForm.getDisplayPage());
		}
		StockFilterVO stockFilterVO = new StockFilterVO();
		
		if (returnStockSession.getStockFilterVO() != null) {
			stockFilterVO = returnStockSession.getStockFilterVO();
		}
		stockFilterVO.setPageNumber(displayPage);
		
		String strAbsoluteIndex = (String) indexMap.get(dispPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		stockFilterVO.setAbsoluteIndex(nAbsoluteIndex);

		if (returnStockSession.getTotalRecords() != null) {
			stockFilterVO.setTotalRecords(returnStockSession
					.getTotalRecords().intValue());
		} else {
			stockFilterVO.setTotalRecords(-1);
		}
		Page<RangeVO> rangeVOPages = findAvailableRangesForPagination(stockFilterVO);
		if (returnStockSession.getStockRangeVO() != null && rangeVOPages != null
				&& rangeVOPages.size() > 0) {
			returnStockSession.setTotalRecords(rangeVOPages
					.getTotalRecordCount());
			finalMap = buildIndexMap(indexMap,
					rangeVOPages);
			returnStockSession.setIndexMap((HashMap<String, String>) super
					.setIndexMap(finalMap, invocationContext));
			returnStockSession.setPageRangeVO(rangeVOPages);
		}
		invocationContext.target = ACTION_SUCCESS;
	}
	
	/**
	 * @param existingMap
	 * @param page
	 * @return
	 */
	private HashMap buildIndexMap(HashMap existingMap, Page page) {
		HashMap finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}
	
	/**
	 * @param stockFilterVO
	 * @return
	 */
	private Page<RangeVO> findAvailableRangesForPagination(StockFilterVO stockFilterVO) {
		Page<RangeVO> rangeVOPages = null;
		try {
			rangeVOPages = new StockControlDefaultsDelegate()
					.findAvailableRanges(stockFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
		}
		return rangeVOPages;
	}
}