/*
 * ListStockRequestSession.java Created on Aug 26, 2005
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1366
 *This class implements the session interface for ListStockRequest screen
 * The session in this case holds the document types and status which are used as filter
 */
public interface ListStockRequestSession extends ScreenSession{


	public Collection<OneTimeVO> getOneTimeStatus();

	public void setOneTimeStatus(Collection<OneTimeVO>  oneTimeStatus);

	public Collection<OneTimeVO> getOneTimeRequestedBy();

	public void setOneTimeRequestedBy(Collection<OneTimeVO>  oneTimeRequestedBy);

	public HashMap<String,Collection<String>> getDynamicOptionList();

	public void setDynamicOptionList(HashMap<String,Collection<String>>  dynamicOptionList);

	public Page<StockRequestFilterVO>   getPageStockRequestFilterVO();

	public void setPageStockRequestFilterVO(Page<StockRequestFilterVO> stockRequestFilterVO);

	public Page<StockRequestVO>   getPageStockRequestVO();

	public void setPageStockRequestVO(Page<StockRequestVO> stockRequestVO);

	public Collection<StockRequestVO>   getCollectionStockRequestVO();

	public void setCollectionStockRequestVO(Collection<StockRequestVO> stockRequestVO);

	public String  getRefNo();

	public void setRefNo(String refNo);

	public Collection<StockHolderPriorityVO>   getPrioritizedStockHolders();

	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos);
    
	public void setStockRequestFilterDetails(StockRequestFilterVO filterDetails);
	
	public StockRequestFilterVO getStockRequestFilterDetails();
	
	public void removeStockRequestFilterDetails();
	
	/**
	 * @author A-2589
	 * @param partnerAirlines
	 * 
	 */
	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines);
	
	/**
	 * @author A-2589
	 * @return Page<AirlineLovVO>
	 * 
	 */
	public Page<AirlineLovVO> getPartnerAirlines();
	
	/* added by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-20959 and ScreenId: STK016
	 */
	Integer getTotalRecords();
	
	void setTotalRecords(int totalRecords);

}
