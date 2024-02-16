/*
 * StockDetailsSession.java Created on May19,2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public interface StockDetailsSession extends ScreenSession{ 
	
	public void setStockHolderType(Collection<OneTimeVO>  stockHolderType);
	public Collection<OneTimeVO>   getStockHolderType();
	
	public Collection<StockHolderPriorityVO>   getPrioritizedStockHolders();
	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos);
	
	public HashMap<String,Collection<String>> getDynamicDocType();
	public void setDynamicDocType(HashMap<String,Collection<String>>  dynamicDocType);
	//public void removeDynamicDocType();
	
	public StockDetailsVO getStockDetailsVO();
	public void setStockDetailsVO(StockDetailsVO stockDetailsVO);
	public void removeStockDetailsVO();
	
	public Page<StockDetailsVO> getStockDetails();  
	public void setStockDetails(Page<StockDetailsVO> stockDetails);
	public void removeStockDetails();
	
	public HashMap<String,String>getIndexMap();
	public void setIndexMap(HashMap<String,String>indexMap);

}
