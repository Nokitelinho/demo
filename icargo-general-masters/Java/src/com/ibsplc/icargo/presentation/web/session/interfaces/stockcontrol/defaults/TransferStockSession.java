/*
 * TransferStockSession.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-1952
 *This class implements the session interface for CreateStock screen
 * The session in this case holds the document types and status which are used as filter
 */
public interface TransferStockSession extends ScreenSession{
	/**
	 *
	 * @return
	 */

		public StockRangeVO   getStockRangeVO();
	/**
	 *
	 * @param stockRangeVO
	 */
		public void setStockRangeVO(StockRangeVO stockRangeVO);
	 /**
	 *
	 * @param stockRangeVO
	 */
		public void removeStockRangeVO();
	/**
	 *
	 * @return
	 */

		public HashMap<String,Collection<String>> getDynamicDocType();
	/**
	 *
	 * @param dynamicDocType
	 */
		public void setDynamicDocType(HashMap<String,Collection<String>>  dynamicDocType);
	/**
	 *
	 * @return
	 */

		public Collection<MonitorStockVO>   getCollectionMonitorStockVO();
	/**
	 *
	 * @param monitorStockVO
	 */
		public void setCollectionMonitorStockVO(Collection<MonitorStockVO> monitorStockVO);
	/**
	 *
	 * @return
	 */

		public StockFilterVO   getStockFilterVO();
	/**
	 *
	 * @param stockFilterVO
	 */
		public void setStockFilterVO(StockFilterVO stockFilterVO);
	/**
	 *
	 * @return
	 */

		public Collection<RangeVO>   getCollectionRangeVO();
	/**
	 *
	 * @param rangeVO
	 */
		public void setCollectionRangeVO(Collection<RangeVO> rangeVO);

		/**
		 * @author A-2589
		 * @param airlineLovVO
		 * 
		 */
		public void setPartnerAirline(AirlineLovVO airlineLovVO);
		
		/**
		 * @author A-2589
		 * @return AirlineLovVO
		 * 
		 */
		public AirlineLovVO getPartnerAirline();
		
		
		/**
		 *
		 * @return
		 */

			public StockAllocationVO   getStockAllocationVO();
		/**
		 *
		 * @param stockAllocationVO
		 */
			public void setStockAllocationVO(StockAllocationVO stockAllocationVO);
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
