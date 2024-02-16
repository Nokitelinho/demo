/*
 * MaintainRoleGroupSessionInterface.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;


import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-1952
 *
 */
public interface ViewRangeSession extends ScreenSession {
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

	public String  getCode();
	/**
	 *
	 * @param code
	 */
	public void setCode(String code);
	
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
	public Page<String> getAWBStockPage();
	
	public void setAWBStockPage(Page<String>  awbStockPage);
    public Integer getTotalRecords();

    public void setTotalRecords(int i);
}
