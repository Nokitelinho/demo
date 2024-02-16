/*
 * DeleteStockRangeSessionImpl.java Created on July 12, 2017
 *
 * Copyright  IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.DeleteStockRangeSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class DeleteStockRangeSessionImpl extends AbstractScreenSession implements DeleteStockRangeSession{

	public static final String SCREEN_ID = "stockcontrol.defaults.deletestockrange";
	public static final String MODULE_NAME = "stockcontrol.defaults";
	private static final String KEY_STOCKRANGEVO = "deletestockrange";
	 private static final String KEY_PARTNER_AIRLINE="partnerairline";
	 private static final String KEY_STOCKFILTERVO = "stockfiltervo";
	 private static final String KEY_RANGEVO = "deleterange";
	private static final String KEY_RANGEVO_PAGE = "rangeVO";
	public static final String KEY_INDEXMAP = "indexMap";
	private static final String KEY_TOTAL_RECORDS = "totalRecords";
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}

	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	@Override
	public StockRangeVO getStockRangeVO() {
		return getAttribute(KEY_STOCKRANGEVO);
	}

	@Override
	public void setStockRangeVO(StockRangeVO stockRangeVO) {
		setAttribute(KEY_STOCKRANGEVO,stockRangeVO);
	}

	@Override
	public void removeStockRangeVO() {
		removeAttribute(KEY_STOCKRANGEVO);
	}

	@Override
	public void setPartnerAirline(AirlineLovVO airlineLovVO) {
		setAttribute(KEY_PARTNER_AIRLINE,airlineLovVO);
		
	}

	@Override
	public AirlineLovVO getPartnerAirline() {
		return getAttribute(KEY_PARTNER_AIRLINE);
	}

	@Override
	public StockFilterVO getStockFilterVO() {
		return getAttribute(KEY_STOCKFILTERVO);
	}

	@Override
	public void setStockFilterVO(StockFilterVO stockFilterVO) {
		setAttribute(KEY_STOCKFILTERVO,stockFilterVO);
	}

	@Override
	public Collection<RangeVO> getCollectionRangeVO() {
		return getAttribute(KEY_RANGEVO);
	}

	@Override
	public void setCollectionRangeVO(Collection<RangeVO> rangeVO) {
		setAttribute(KEY_RANGEVO,(ArrayList<RangeVO>)rangeVO);
	}
	public Page<RangeVO> getPageRangeVO() {
		return (Page<RangeVO>) getAttribute(KEY_RANGEVO_PAGE);

	}
	public void setPageRangeVO(Page<RangeVO> rangeVO) {
		setAttribute(KEY_RANGEVO_PAGE, (Page<RangeVO>) rangeVO);
	}
	public void setIndexMap(HashMap indexMap) {
		setAttribute(KEY_INDEXMAP, (HashMap<String, String>) indexMap);
	}
	public HashMap getIndexMap() {
		return (HashMap<String, String>) getAttribute(KEY_INDEXMAP);
	}
	public Integer getTotalRecords() {
		return (Integer)getAttribute(KEY_TOTAL_RECORDS);
	}

	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTAL_RECORDS, Integer.valueOf(totalRecords));
	}
}
