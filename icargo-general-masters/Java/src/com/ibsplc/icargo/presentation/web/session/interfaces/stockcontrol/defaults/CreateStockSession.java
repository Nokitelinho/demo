/*
 * CreateStockSession.java Created on Aug 26, 2005
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-1952
 *This class implements the session interface for CreateStock screen
 * The session in this case holds the document types and status which are used as filter
 */
public interface CreateStockSession extends ScreenSession{

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
	public Collection<RangeVO>   getCollectionRangeVO();
/**
 * 
 * @param rangeVO
 */
	public void setCollectionRangeVO(Collection<RangeVO> rangeVO);
/**
 * 
 * @return
 */
	public StockAllocationVO   getStockAllocationVO();
/**
 * 
 * @return
 */
	public String  getMode();
/**
 * 
 * @param mode
 */
	public void setMode(String mode);
/**
 * 
 * @param StockAllocationVO
 */
	public void setStockAllocationVO(StockAllocationVO stockAllocationVO);
	
	/**
	 * @author A-2589
	 * @param partnerAirlines
	 * 
	 */
	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines);
	
	/**
	 * @author A-2589
	 * @return Collection<OneTimeVO>
	 * 
	 */
	public Page<AirlineLovVO> getPartnerAirlines();


}
