/*
 * StockDetailsSessionImpl.java Created on May19,2011
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

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockDetailsSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class StockDetailsSessionImpl extends AbstractScreenSession
implements StockDetailsSession {
	
	 private static final String PRIORITISED_STOCKHOLDERTYPE = "prioritisedstockholdertype";
	 private static final String STOCKHOLDER_TYPE = "stockholdertype";
	 private static final String DOCUMENT_TYPE = "documenttype";
	  private static final String KEY_FILTERDETAILS = "stockDetailsVO";
	  private static final String STOCK_DETAILS="stockDetails";
	  private static final String KEY_INDEXMAP = "indexMap";
	@Override
	public String getModuleName() {

		return "stockcontrol.defaults";
	}

	@Override
	public String getScreenID() {
	
		return "stockcontrol.defaults.stockdetails";
	}
	public Page<StockDetailsVO> getStockDetails(){
		return (Page<StockDetailsVO>)getAttribute(STOCK_DETAILS);
	}
	public void setStockDetails(Page<StockDetailsVO>  stockDetails){
	    setAttribute(STOCK_DETAILS, (Page<StockDetailsVO>)stockDetails);
	}
	public void removeStockDetails(){
		removeAttribute(STOCK_DETAILS);
	}
	public void setStockHolderType(Collection<OneTimeVO>  stockHolderType){
	    setAttribute(STOCKHOLDER_TYPE, (ArrayList<OneTimeVO>)stockHolderType);
	}
	public Collection<OneTimeVO>  getStockHolderType( ){
	   return(Collection<OneTimeVO>)getAttribute(STOCKHOLDER_TYPE );
	}
	
	public void setDynamicDocType(HashMap<String,Collection<String>> dynamicDocType) {
		setAttribute(DOCUMENT_TYPE,(HashMap<String,Collection<String>>)dynamicDocType);
	}
	public HashMap<String,Collection<String>> getDynamicDocType() {
		return (HashMap<String,Collection<String>>)getAttribute(DOCUMENT_TYPE);
	}
	public void setStockDetailsVO(StockDetailsVO stockDetailsVO){
		 setAttribute(KEY_FILTERDETAILS,(StockDetailsVO) stockDetailsVO);
	}
	public StockDetailsVO getStockDetailsVO(){
		return (StockDetailsVO)getAttribute(KEY_FILTERDETAILS);
	}
	public void removeStockDetailsVO(){
		removeAttribute(KEY_FILTERDETAILS);
	}
	public HashMap<String,String>getIndexMap(){
		 return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	}

	/**Sets the hashmap for Absolute index of page
	 * @param indexMap
	 */
	public void setIndexMap(HashMap<String,String>indexMap){
		 setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexMap);
	}
	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos){
		  setAttribute(PRIORITISED_STOCKHOLDERTYPE, (ArrayList<StockHolderPriorityVO>)stockHolderpriorityVos);
	}
	 public Collection<StockHolderPriorityVO>   getPrioritizedStockHolders(){
		 return (Collection<StockHolderPriorityVO>)getAttribute(PRIORITISED_STOCKHOLDERTYPE);

	}
}
