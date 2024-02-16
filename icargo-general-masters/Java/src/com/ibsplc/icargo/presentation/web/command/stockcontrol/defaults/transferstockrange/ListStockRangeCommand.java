/*
 * ListStockRangeCommand.java Created on Jan 07, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.transferstockrange;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.TransferStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.TransferStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-5642
 * The Class ListStockRangeCommand.java.
 */
public class ListStockRangeCommand extends BaseCommand {
	
	private static final String SCREEN_ID = "stockcontrol.defaults.transferstockrange";
	
	private static final String MODULE_NAME = "stockcontrol.defaults";
	
	private static final String ZERO = "0";
	
	private static final String ONE = "1";
	
	private static final int RANGE_LENGTH = 7;
	
	private static final String ACTION_SUCCESS = "screenload_success";
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {	
		
		TransferStockForm transferStockForm = (TransferStockForm) invocationContext.screenModel;
		TransferStockSession transferSession = (TransferStockSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		HashMap<String, String> indexMap = getIndexMap(
				transferSession.getIndexMap(), invocationContext);
		HashMap finalMap = null;
		if (transferSession.getIndexMap() != null) {
			indexMap = transferSession.getIndexMap();
		}
		if (indexMap == null) {
			indexMap = new HashMap();
			indexMap.put(ONE, ONE);
		}
		int nAbsoluteIndex = 0;
		String dispPage = transferStockForm.getDisplayPage();
		int displayPage = 1;
		if (transferStockForm.getDisplayPage() != null && transferStockForm.getDisplayPage().trim().length() > 0) {		
			displayPage = Integer.parseInt(transferStockForm.getDisplayPage());
		}
		StockFilterVO stockFilterVO = new StockFilterVO();
		
		if (transferSession.getStockFilterVO() != null) {
			stockFilterVO = transferSession.getStockFilterVO();
		}
		stockFilterVO.setPageNumber(displayPage);
		
		String strAbsoluteIndex = (String) indexMap.get(dispPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		stockFilterVO.setAbsoluteIndex(nAbsoluteIndex);

		if (transferSession.getTotalRecords() != null) {
			stockFilterVO.setTotalRecords(transferSession
					.getTotalRecords().intValue());
		} else {
			stockFilterVO.setTotalRecords(-1);
		}
		Page<RangeVO> rangeVOPages = findAvailableRangesForPagination(stockFilterVO);
		if (transferSession.getStockRangeVO() != null && rangeVOPages != null
				&& rangeVOPages.size() > 0) {
			formatPageRangeVOAndPopulateSession(
					transferSession,rangeVOPages, stockFilterVO);
			transferSession.setTotalRecords(rangeVOPages
					.getTotalRecordCount());
			finalMap = buildIndexMap(indexMap,
					rangeVOPages);
			transferSession.setIndexMap((HashMap<String, String>) super
					.setIndexMap(finalMap, invocationContext));
			transferSession.setPageRangeVO(rangeVOPages);
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
	
	/**
	 * Added for pagination
	 * @param transferSession
	 * @param rangeVOPages
	 * @param stockFilterVO
	 */
	private void formatPageRangeVOAndPopulateSession(TransferStockSession transferSession,Page<RangeVO> rangeVOPages,
			StockFilterVO stockFilterVO) {
		int startRangeCount = 0;
		int endRangeCount = 0;
		String startRange = null;
		String endRange = null;
		for(RangeVO rangeVo : rangeVOPages) {
			startRangeCount = 0;
			endRangeCount = 0;
			if (DocumentValidationVO.DOC_TYP_AWB.equals(stockFilterVO.getDocumentType())) {
				startRange = rangeVo.getStartRange();
				endRange = rangeVo.getEndRange();
				if (startRange.length() < RANGE_LENGTH) {
					startRangeCount = RANGE_LENGTH - startRange.length();
					for(int i = 0 ; i < startRangeCount ; i++) {
						rangeVo.setStartRange(ZERO + startRange);
					}
				}
				if (endRange.length() < RANGE_LENGTH) {
					endRangeCount = RANGE_LENGTH - endRange.length();
					for (int i = 0 ; i < endRangeCount ; i++) {
						rangeVo.setEndRange(ZERO + endRange);
					}
				}
			}
		}
	}
}