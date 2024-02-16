/*
 * ListStockRangeCommand.java Created on Jan 07, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.deletestockrange;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.DeleteStockRangeSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.DeleteStockRangeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-5642
 * The Class ListStockRangeCommand.java.
 */
public class ListStockRangeCommand extends BaseCommand {
	
	private static final String SCREEN_ID = "stockcontrol.defaults.deletestockrange";
	
	private static final String MODULE_NAME = "stockcontrol.defaults";
	
	private static final String ONE = "1";
	
	private static final String ACTION_SUCCESS = "screenload_success";
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {	
		
		DeleteStockRangeForm deleteStockRangeForm = (DeleteStockRangeForm) invocationContext.screenModel;
		DeleteStockRangeSession deleteStockRangeSession = (DeleteStockRangeSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		HashMap<String, String> indexMap = getIndexMap(
				deleteStockRangeSession.getIndexMap(), invocationContext);
		HashMap finalMap = null;
		if (deleteStockRangeSession.getIndexMap() != null) {
			indexMap = deleteStockRangeSession.getIndexMap();
		}
		if (indexMap == null) {
			indexMap = new HashMap();
			indexMap.put(ONE, ONE);
		}
		int nAbsoluteIndex = 0;
		String dispPage = deleteStockRangeForm.getDisplayPage();
		int displayPage = 1;
		if (deleteStockRangeForm.getDisplayPage() != null && deleteStockRangeForm.getDisplayPage().trim().length() > 0) {		
			displayPage = Integer.parseInt(deleteStockRangeForm.getDisplayPage());
		}
		StockFilterVO stockFilterVO = new StockFilterVO();
		
		if (deleteStockRangeSession.getStockFilterVO() != null) {
			stockFilterVO = deleteStockRangeSession.getStockFilterVO();
		}
		stockFilterVO.setPageNumber(displayPage);
		
		String strAbsoluteIndex = (String) indexMap.get(dispPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		stockFilterVO.setAbsoluteIndex(nAbsoluteIndex);

		if (deleteStockRangeSession.getTotalRecords() != null) {
			stockFilterVO.setTotalRecords(deleteStockRangeSession
					.getTotalRecords().intValue());
		} else {
			stockFilterVO.setTotalRecords(-1);
		}
		Page<RangeVO> rangeVOPages = findAvailableRangesForPagination(stockFilterVO);
		if (deleteStockRangeSession.getStockRangeVO() != null && rangeVOPages != null
				&& rangeVOPages.size() > 0) {
			deleteStockRangeSession.setTotalRecords(rangeVOPages
					.getTotalRecordCount());
			finalMap = buildIndexMap(indexMap,
					rangeVOPages);
			deleteStockRangeSession.setIndexMap((HashMap<String, String>) super
					.setIndexMap(finalMap, invocationContext));
			deleteStockRangeSession.setPageRangeVO(rangeVOPages);
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
	public Page<RangeVO> findAvailableRangesForPagination(StockFilterVO stockFilterVO) {
		Page<RangeVO> rangeVOPages = null;
		try {
			rangeVOPages = new StockControlDefaultsDelegate()
					.findAvailableRanges(stockFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
		}
		return rangeVOPages;
	}
}