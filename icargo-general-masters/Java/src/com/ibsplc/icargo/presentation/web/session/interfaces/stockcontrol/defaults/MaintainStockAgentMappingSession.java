/*
 * MaintainStockAgentMappingSession.java Created on Oct 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;


import java.util.Map;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-2394
 *
 */
public interface MaintainStockAgentMappingSession extends ScreenSession{
	
	public void setStockHolderAgentMapping(Page<StockAgentVO> pages);
	
	public Page<StockAgentVO> getStockHolderAgentMapping();
	
	public void setStockHolderAgentMappingOriginal(Map<String,StockAgentVO> maps);
	
	public Map<String,StockAgentVO> getStockHolderAgentMappingOriginal();

}
