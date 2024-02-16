/*
 * ListStockRequestSessionImpl.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRequestSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;

/**
 * @author A-1952
 * 
 */
public class ListStockRequestSessionImpl extends AbstractScreenSession
		implements ListStockRequestSession {
	/**
	 * KEY_SCREEN_ID
	 */
	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.liststockrequest";

	/**
	 * KEY_MODULE_NAME
	 */
	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";

	private static final String DOCUMENT_TYPE = "documenttype";

	private static final String STKHOLDER_TYPES = "stockholdertypes";

	private static final String STATUS = "status";

	private static final String LIST_STOCKREQUEST = "liststockrequest";

	private static final String KEY_ONETIMEVO = "oneTimeVO";

	private static final String KEY_STOCKREQUESTVO = "stockRequestVO";

	private static final String KEY_STOCKREQUESTFILTERVO = "stockRequestFilterVO";
	
	private static final String STOCK_REQUEST_FILTERDETAILS = "stockRequestfilterdetails";

	private static final String KEY_REFNO = "refno";

	private static final String KEY_STOCKREQUESTVOS = "stockRequestVO1";

	private static final String KEY_PRIORITISEDSTOCKHOLDER = "stockHolderpriorityVo";
	
	private static final String KEY_PARTNER_AIRLINES="partner.airlines";
	
	private static final String KEY_TOTAL_RECORDS = "totalRecords";

	/**
	 * This method returns the SCREEN ID for the create Stock holder screen
	 * @return null
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}

	/**
	 * This method returns the MODULE NAME for the create Stock holder screen
	 * @return null
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}

	/**
	 * All document types and subtypes are fetched on screenload and stored as a
	 * Map with document type as key and subtype collection as value. This
	 * method returns this map object from the session
	 * 
	 * @return Map
	 */
	public Map getDocumentTypes() {
		return null;
	}

	/**
	 * This method is used to set documentType-subtype map object into the
	 * session
	 * 
	 * @param documentTypes
	 */
	public void setDocumentTypes(Map documentTypes) {
		// setAttribute(DOCUMENT_TYPE,documentTypes);
	}

	/**
	 * This method is used to remove the documentType-subtype map object from
	 * session
	 * 
	 */
	public void removeDocumentTypes() {
		removeAttribute(DOCUMENT_TYPE);
	}

	/**
	 * This method is used to fetch the stockholdertypes stored in session.
	 * Stockholder types are fetched from onetime during screenload
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getStockHolderTypes() {
		return null;
	}

	/**
	 * This method is used to set the stockholder types into the session
	 * 
	 * @param stockHolderTypes
	 *            Collection<String>
	 */
	public void setStockHolderTypes(Collection<String> stockHolderTypes) {
		// setAttribute(STKHOLDER_TYPES,stockHolderTypes);
	}

	/**
	 * This method is used to remove the tockholder types from session
	 * 
	 */
	public void removeStockHolderTypes() {
		removeAttribute(STKHOLDER_TYPES);
	}

	/**
	 * This method is used to fetch the stock request statuses stored in
	 * session. Stock request statuses are fetched from onetime during screen
	 * load
	 * 
	 * @return Collection <OneTimeVO>
	 */
	public Collection<OneTimeVO> getStatus() {
		return null;
	}

	/**
	 * This method is used to set the status obtained from onetime into session
	 * 
	 * @param status
	 */
	public void setStatus(Collection<OneTimeVO> status) {
		// setAttribute(STATUS,status);
	}

	/**
	 * This method is used to remove the status from session
	 * 
	 */
	public void removeStatus() {
		removeAttribute(STATUS);
	}

	/**
	 * This method is used to fetch the list of stock requests from session.
	 * 
	 * @return Page<StockRequestVO>
	 */
	public Page getListStockRequest() {
		return null;
	}

	/**
	 * This method is used to set the listStockRequest into session
	 * 
	 * @param listStockRequest
	 */
	public void setListStockRequest(Page listStockRequest) {
		// setAttribute(LIST_STOCKREQUEST,listStockRequest);
	}

	/**
	 * This method is used to remove listStockRequest from session
	 * 
	 */
	public void removeListStockRequest() {
		removeAttribute(LIST_STOCKREQUEST);
	}

	/**
	 * Thie method is used to get the OneTimeVO for child screen from session
	 * 
	 * @return OneTimeVO
	 */
	public Collection<OneTimeVO> getOneTimeStatus() {
		return (Collection<OneTimeVO>) getAttribute(STATUS);
	}

	/**
	 * This method is used to set the OneTimeVO in session for child
	 * 
	 * @param oneTimeStatus
	 */
	public void setOneTimeStatus(Collection<OneTimeVO> oneTimeStatus) {
		setAttribute(STATUS, (ArrayList<OneTimeVO>) oneTimeStatus);
	}

	/**
	 * Thie method is used to get the OneTimeVO for child screen from session
	 * 
	 * @return OneTimeVO
	 */
	public Collection<OneTimeVO> getOneTimeRequestedBy() {
		return (Collection<OneTimeVO>) getAttribute(KEY_ONETIMEVO);
	}

	/**
	 * This method is used to set the OneTimeVO in session for child
	 * 
	 * @param oneTimeRequestedBy
	 */
	public void setOneTimeRequestedBy(Collection<OneTimeVO> oneTimeRequestedBy) {
		setAttribute(KEY_ONETIMEVO, (ArrayList<OneTimeVO>) oneTimeRequestedBy);
	}
/**
 * @param removeOneTimeRequestedBy
 *
 */
	public void removeOneTimeRequestedBy() {
		removeAttribute(KEY_ONETIMEVO);
	}
/**
 * @param removeOneTimeStatus
 *
 */
	public void removeOneTimeStatus() {
		removeAttribute(KEY_ONETIMEVO);
	}
/**
 * @param removeDynamicOptionList
 *
 */
	public void removeDynamicOptionList() {
		removeAttribute(DOCUMENT_TYPE);
	}
/**
 * @param dynamicOptionList
 */
	public void setDynamicOptionList(
			HashMap<String, Collection<String>> dynamicOptionList) {
		setAttribute(DOCUMENT_TYPE,
				(HashMap<String, Collection<String>>) dynamicOptionList);
	}
/**
 * @return dynamicOptionList
 */
	public HashMap<String, Collection<String>> getDynamicOptionList() {
		return (HashMap<String, Collection<String>>) getAttribute(DOCUMENT_TYPE);
	}
/**
 * @return stockRequestFilterVO
 */
	public Page<StockRequestFilterVO> getPageStockRequestFilterVO() {
		return (Page<StockRequestFilterVO>) getAttribute(KEY_STOCKREQUESTFILTERVO);
	}
/**
 * @param stockRequestFilterVO
 */
	public void setPageStockRequestFilterVO(
			Page<StockRequestFilterVO> stockRequestFilterVO) {
		setAttribute(KEY_STOCKREQUESTFILTERVO,
				(Page<StockRequestFilterVO>) stockRequestFilterVO);
	}
/**
 * @return stockRequestVO
 */
	public Page<StockRequestVO> getPageStockRequestVO() {
		return (Page<StockRequestVO>) getAttribute(KEY_STOCKREQUESTVO);

	}
/**
 * @param stockRequestVO
 */
	public void setPageStockRequestVO(Page<StockRequestVO> stockRequestVO) {
		setAttribute(KEY_STOCKREQUESTVO, (Page<StockRequestVO>) stockRequestVO);
	}
/**
 * @param removePageStockRequestVO
 *
 */
	public void removePageStockRequestVO() {
		removeAttribute(KEY_STOCKREQUESTVO);
	}
/**
 * @param removePageStockRequestFilterVO
 *
 */
	public void removePageStockRequestFilterVO() {
		removeAttribute(KEY_STOCKREQUESTFILTERVO);
	}
/**
 * @return stockRequestVO
 */
	public Collection<StockRequestVO> getCollectionStockRequestVO() {
		return (Collection<StockRequestVO>) getAttribute(KEY_STOCKREQUESTVOS);

	}
/**
 * @param stockRequestVO
 */
	public void setCollectionStockRequestVO(
			Collection<StockRequestVO> stockRequestVO) {
		setAttribute(KEY_STOCKREQUESTVOS,
				(ArrayList<StockRequestVO>) stockRequestVO);
	}
/**
 * @return refNo
 */
	public String getRefNo() {
		return (getAttribute(KEY_REFNO));
	}
/**
 * @param refNo
 */
	public void setRefNo(String refNo) {
		setAttribute(KEY_REFNO, refNo);
	}
/**
 * @return stockHolderpriorityVos
 */
	public Collection<StockHolderPriorityVO> getPrioritizedStockHolders() {
		return ((Collection<StockHolderPriorityVO>) getAttribute(KEY_PRIORITISEDSTOCKHOLDER));
	}
/**
 * @param stockHolderpriorityVos
 */
	public void setPrioritizedStockHolders(
			Collection<StockHolderPriorityVO> stockHolderpriorityVos) {
		setAttribute(KEY_PRIORITISEDSTOCKHOLDER,
				(ArrayList<StockHolderPriorityVO>) stockHolderpriorityVos);
	}
	/**
	 * @param details
	 */
	public void setStockRequestFilterDetails(StockRequestFilterVO filterDetails) {
		setAttribute(STOCK_REQUEST_FILTERDETAILS,
				(StockRequestFilterVO) filterDetails);
	}
	/**
	 * @return collection
	 */
	public StockRequestFilterVO getStockRequestFilterDetails() {
		return (StockRequestFilterVO) getAttribute(STOCK_REQUEST_FILTERDETAILS);
	}
	/**
	 * Remove method
	 */
	public void removeStockRequestFilterDetails() {
		removeAttribute(STOCK_REQUEST_FILTERDETAILS);
	}

	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
		setAttribute(KEY_PARTNER_AIRLINES,partnerAirlines);
		
	}

	public Page<AirlineLovVO> getPartnerAirlines() {
		return getAttribute(KEY_PARTNER_AIRLINES);
	}
	/* added by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-20959 and ScreenId: STK016
	 * This method is used to set and get the total records values in session
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTAL_RECORDS, Integer.valueOf(totalRecords));
	}

	public Integer getTotalRecords() {
		return (Integer) getAttribute(KEY_TOTAL_RECORDS);
	}
}
