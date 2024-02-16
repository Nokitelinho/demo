/*
 * TransferStockSessionImpl.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.TransferStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-1952
 *
 */
public class TransferStockSessionImpl extends AbstractScreenSession
	implements TransferStockSession{
	/**
	 * KEY_SCREEN_ID
	 */
	public static final String KEY_SCREEN_ID = "stockcontrol.defaults.transferstockrange";

	/**
	 * KEY_MODULE_NAME
	 */
	public static final String KEY_MODULE_NAME = "stockcontrol.defaults";
	
   private static final String DOCUMENT_TYPE = "transferdocumenttype";

   private static final String KEY_MONITORSTOCKVO = "transfermonitorstockvo";

   private static final String KEY_STOCKFILTERVO = "transferstockfiltervo";

   private static final String KEY_RANGEVO = "transferrange";

   private static final String KEY_STOCKRANGEVO = "transferstockrange";
   
   private static final String KEY_PARTNER_AIRLINE="partner.airline";   
   
   private static final String KEY_STOCKALLOCATIONVO = "stockallocationvo";
   
   private static final String KEY_RANGEVO_PAGE = "rangeVO";

   public static final String KEY_INDEXMAP = "indexMap";
   
   private static final String KEY_TOTAL_RECORDS = "totalRecords";
   /**
	 * This method returns the SCREEN ID for the create Stock holder screen
	 * @return null
	 */
   public String getScreenID() {
       return null;
   }

   /**
	 * This method returns the
	 * MODULE AME for the create Stock holder screen
	 * @return null
	 */
   public String getModuleName() {
       return null;
   }
/**
 * @param dynamicDocType
 */

	public void setDynamicDocType(HashMap<String,Collection<String>> dynamicDocType) {
		setAttribute(DOCUMENT_TYPE,(HashMap<String,Collection<String>>)dynamicDocType);
	}
	/**
	 * @return dynamicDocType
	 */
	public HashMap<String,Collection<String>> getDynamicDocType() {
			return (HashMap<String,Collection<String>>)getAttribute(DOCUMENT_TYPE);
	}
/**
 * @return monitorStockVO
 */
	public Collection<MonitorStockVO>   getCollectionMonitorStockVO(){
		   return (Collection<MonitorStockVO>)getAttribute(KEY_MONITORSTOCKVO);

	}
/**
 * @param monitorStockVO
 */
	public void setCollectionMonitorStockVO(Collection<MonitorStockVO> monitorStockVO){
			  setAttribute(KEY_MONITORSTOCKVO, (ArrayList<MonitorStockVO>)monitorStockVO);
	}
/**
 * @return stockFilterVO
 */
	public StockFilterVO   getStockFilterVO(){
		   return (StockFilterVO)getAttribute(KEY_STOCKFILTERVO);

    }
/**
 * @param stockFilterVO
 */
    public void setStockFilterVO(StockFilterVO stockFilterVO){
			  setAttribute(KEY_STOCKFILTERVO, (StockFilterVO)stockFilterVO);
	}
    /**
     * @return rangeVO
     */
	public Collection<RangeVO>   getCollectionRangeVO(){
		   return (Collection<RangeVO>)getAttribute(KEY_RANGEVO);

	}
/**
 * @param rangeVO
 */
	public void setCollectionRangeVO(Collection<RangeVO> rangeVO){
			  setAttribute(KEY_RANGEVO, (ArrayList<RangeVO>)rangeVO);
	}
	/**
	 * @return stockRangeVO
	 */
	public StockRangeVO   getStockRangeVO()	{
		return (StockRangeVO)getAttribute(KEY_STOCKRANGEVO);
	}
/**
 * @param stockRangeVO
 */
    public void setStockRangeVO(StockRangeVO stockRangeVO){
		setAttribute(KEY_STOCKRANGEVO, (StockRangeVO)stockRangeVO);
	}
/**
 * @param stockRangeVO
 */
    public void removeStockRangeVO(){
    	removeAttribute(KEY_STOCKRANGEVO);
    }

public void setPartnerAirline(AirlineLovVO airlineLovVO) {
	setAttribute(KEY_PARTNER_AIRLINE,airlineLovVO);
	
}

public AirlineLovVO getPartnerAirline() {
	return getAttribute(KEY_PARTNER_AIRLINE);
}

/**
 * This method returns the stockAllocationVO
 * @return stockAllocationVO
 */
public StockAllocationVO   getStockAllocationVO(){
   return (StockAllocationVO)getAttribute(KEY_STOCKALLOCATIONVO);
}
/**
* @param stockAllocationVO
*/
public void setStockAllocationVO(StockAllocationVO stockAllocationVO){
	  setAttribute(KEY_STOCKALLOCATIONVO, (StockAllocationVO)stockAllocationVO);
}
public Page<RangeVO> getPageRangeVO() {
	return (Page<RangeVO>) getAttribute(KEY_RANGEVO_PAGE);

}
/**
* @param rangeVO
*/
public void setPageRangeVO(Page<RangeVO> rangeVO) {
	setAttribute(KEY_RANGEVO_PAGE, (Page<RangeVO>) rangeVO);
}
public void setIndexMap(HashMap indexMap) {
	setAttribute(KEY_INDEXMAP, (HashMap<String, String>) indexMap);
}

/**
 * get indexmap
 * 
 * @return HashMap
 */
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
