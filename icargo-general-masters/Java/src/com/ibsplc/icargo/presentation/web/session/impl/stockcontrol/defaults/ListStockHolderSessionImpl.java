/*
 * ListStockHolderSessionImpl.java Created on Aug 11, 2005
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
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockHolderSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1358
 * 
 * This class realizes the session interface for ListStockHolder screen The
 * session in this case holds the document types which are used as filter
 */
public class ListStockHolderSessionImpl extends AbstractScreenSession implements
		ListStockHolderSession {

	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.liststockholder";

	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";

	private static final String STOCK_HOLDER_ONETIME = "stockholderonetime";

	private static final String DOCUMENT_TYPE_MAP = "doctypemap";

	private static final String STOCK_HOLDER_DETAILS = "stockholderdetails";
	
	private static final String STOCK_HOLDER_FILTERDETAILS = "stockholderfilterdetails";

	private static final String PRIRIRIZED_STOCKHOLDER = "prioririzedstockholder";
	
	public static final String KEY_INDEXMAP = "indexMap";
	  
	private static final String KEY_PARTNER_AIRLINE="partner.airlines";
	
	private static final String KEY_TOTALRECORDS = "totalrecords";

	/**
	 * @return sceen id
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}

	/**
	 * @return moduleName
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}

	/**
	 * @param stockHolderTypes
	 */
	public void setOneTimeStock(Collection<OneTimeVO> stockHolderTypes) {
		setAttribute(STOCK_HOLDER_ONETIME,
				(ArrayList<OneTimeVO>) stockHolderTypes);
	}
/**
 * @return oneTime
 */
	public Collection<OneTimeVO> getOneTimeStock() {
		return (Collection<OneTimeVO>) getAttribute(STOCK_HOLDER_ONETIME);
	}

	/**
	 *  Remove method
	 */
	public void removeOneTimeStock() {
		removeAttribute(STOCK_HOLDER_ONETIME);
	}
	/**
	 * @param docType
	 */
	public void setDocTypeMap(HashMap<String, Collection<String>> docType) {
		setAttribute(DOCUMENT_TYPE_MAP,
				(HashMap<String, Collection<String>>) docType);
	}
	/**
	 * @return doctypemap
	 */
	public HashMap<String, Collection<String>> getDocTypeMap() {
		return (HashMap<String, Collection<String>>) getAttribute(DOCUMENT_TYPE_MAP);
	}
	/**
	 * @param stockHolderTypes
	 */
	public void removeDocTypeMap() {
		removeAttribute(DOCUMENT_TYPE_MAP);
	}
	/**
	 * @param details
	 */
	public void setStockHolderDetails(Page<StockHolderDetailsVO> details) {
		setAttribute(STOCK_HOLDER_DETAILS,
				(Page<StockHolderDetailsVO>) details);
	}
	/**
	 * @return collection
	 */
	public Page<StockHolderDetailsVO> getStockHolderDetails() {
		return (Page<StockHolderDetailsVO>) getAttribute(STOCK_HOLDER_DETAILS);
	}
	/**
	 * Remove method
	 */
	public void removeStockHolderDetails() {
		removeAttribute(STOCK_HOLDER_DETAILS);
	}
	/**
	 * @param prioritizedStockHolder
	 */
	public void setPrioritizedStockHolders(
			Collection<StockHolderPriorityVO> prioritizedStockHolder) {
		setAttribute(PRIRIRIZED_STOCKHOLDER,
				(ArrayList<StockHolderPriorityVO>) prioritizedStockHolder);
	}
	/**
	 * @return collection
	 */
	public Collection<StockHolderPriorityVO> getPrioritizedStockHolders() {
		return (Collection<StockHolderPriorityVO>) getAttribute(PRIRIRIZED_STOCKHOLDER);
	}
	/**
	 *  Remove method
	 */
	public void removePrioritizedStockHolders() {
		removeAttribute(PRIRIRIZED_STOCKHOLDER);
	}
	/**
	 * @param details
	 */
	public void setStockHolderFilterDetails(StockHolderFilterVO filterDetails) {
		setAttribute(STOCK_HOLDER_FILTERDETAILS,
				(StockHolderFilterVO) filterDetails);
	}
	/**
	 * @return collection
	 */
	public StockHolderFilterVO getStockHolderFilterDetails() {
		return (StockHolderFilterVO) getAttribute(STOCK_HOLDER_FILTERDETAILS);
	}
	/**
	 * Remove method
	 */
	public void removeStockHolderFilterDetails() {
		removeAttribute(STOCK_HOLDER_FILTERDETAILS);
	}
	
	/**
	 * This method is used for PageAwareMultiMapper to get the Index Map
	 * @return  HashMap<String,String>
	 */
	public HashMap<String,String>getIndexMap(){
		 return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	}

	/**Sets the hashmap for Absolute index of page
	 * @param indexMap
	 */
	public void setIndexMap(HashMap<String,String>indexMap){
		 setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexMap);
	}

	public void setPartnerAirlines(Page<AirlineLovVO> findAirlineLov) {
		setAttribute(KEY_PARTNER_AIRLINE,findAirlineLov);
		
	}
	
	public Page<AirlineLovVO> getPartnerAirlines(){
		return getAttribute(KEY_PARTNER_AIRLINE);
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockHolderSession#getTotalRecords()
	 *	Added by 			: A-5175 on 12-Oct-2012
	 * 	Used for 	:ICRD-20959
	 *	Parameters	:	@return 
	 */
	
	public Integer getTotalRecords() {
			return getAttribute(KEY_TOTALRECORDS);
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockHolderSession#setTotalRecords(int)
	 *	Added by 			: A-5175 on 12-Oct-2012
	 * 	Used for 	:ICRD-20959
	 *	Parameters	:	@param totalRecords 
	 */
	
	public void setTotalRecords(int totalRecords) {
			setAttribute(KEY_TOTALRECORDS, totalRecords);	
	}
	

}
