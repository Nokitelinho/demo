/*
 * ListStockHolderSession.java Created on Aug 11, 2005
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
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1358
 * 
 * This class implements the session interface for ListStockHolder screen
 * The session in this case holds the document types which are used as filter
 */
public interface ListStockHolderSession extends ScreenSession {
   
	
	public void setOneTimeStock(Collection<OneTimeVO> stockHolderTypes);
	public Collection<OneTimeVO> getOneTimeStock();
	public void removeOneTimeStock();
	
	public void setDocTypeMap(HashMap<String,Collection<String>> docType);
	public HashMap<String,Collection<String>> getDocTypeMap();
	public void removeDocTypeMap();
	
	
	public void setStockHolderDetails( Page<StockHolderDetailsVO> details);
	public Page<StockHolderDetailsVO> getStockHolderDetails();
	public void removeStockHolderDetails();
	
	public void setStockHolderFilterDetails(StockHolderFilterVO filterDetails);
	public StockHolderFilterVO getStockHolderFilterDetails();
	public void removeStockHolderFilterDetails();
	
	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> prioritizedStockHolder);
	public Collection<StockHolderPriorityVO> getPrioritizedStockHolders();
	public void removePrioritizedStockHolders();
	
	/**
	 * The method is used to handle PageAwareMultiMapper
	 */
	public HashMap<String,String>getIndexMap();
	
	/**
	 * This method is used to set the IndexMap
	 */
	public void setIndexMap(HashMap<String,String>indexMap);
	
	/**
	 * For #102543 Base product bug
	 * @author A-2589
	 * @param findAirlineLov
	 * 
	 */
	public void setPartnerAirlines(Page<AirlineLovVO> findAirlineLov);
	
	//added by A-5175 for QF CR ICRD-20959 starts
	public Integer getTotalRecords();
	
	public void setTotalRecords(int totalRecords);
	//added by A-5175 for QF CR ICRD-20959 ends
}
