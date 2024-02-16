/*
 * DeleteStockRangeSession.java Created on July 12, 2017
 *
 * Copyright  IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public interface DeleteStockRangeSession extends ScreenSession {

	public StockRangeVO getStockRangeVO();

	public void setStockRangeVO(StockRangeVO stockRangeVO);

	public void removeStockRangeVO();

	public void setPartnerAirline(AirlineLovVO airlineLovVO);
	
	public AirlineLovVO getPartnerAirline();
	
	public StockFilterVO   getStockFilterVO();
	
	public void setStockFilterVO(StockFilterVO stockFilterVO);
	
	public Collection<RangeVO> getCollectionRangeVO();
	
	public void setCollectionRangeVO(Collection<RangeVO> rangeVO);

	public Page<RangeVO>   getPageRangeVO();

	public void setPageRangeVO(Page<RangeVO> rangeVO);
	
	void setIndexMap(HashMap indexMap);

	/**
	 * This method is used to get IndexMap from the session
	 */
	HashMap getIndexMap();
	
	public abstract Integer getTotalRecords();

	public abstract void setTotalRecords(int i);
}
