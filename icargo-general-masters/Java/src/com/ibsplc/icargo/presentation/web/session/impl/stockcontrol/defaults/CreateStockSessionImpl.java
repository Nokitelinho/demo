/*
 * CreateStockSessionImpl.java Created on Aug 26, 2005
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1952
 * 
 */
public class CreateStockSessionImpl extends AbstractScreenSession implements
		CreateStockSession {
	
	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.createstock";
	
	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";

	private static final String DOCUMENT_TYPE = "createdocumenttype";

	private static final String KEY_RANGEVO = "createrange";

	private static final String KEY_STOCKALLOCATIONVO = "createstockallocationvo";

	private static final String KEY_MODE = "createmode";
	
	private static final String KEY_PARTNER_AIRLINES="partner.airlines";

	/**
	 * This method returns the SCREEN ID for the Create Stock holder screen
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
	 *
	 * @param dynamicDocType
	 *            
	 */

	public void setDynamicDocType(
			HashMap<String, Collection<String>> dynamicDocType) {
		setAttribute(DOCUMENT_TYPE,
				(HashMap<String, Collection<String>>) dynamicDocType);
	}

	/**
	 * @return documenttype
	 */
	public HashMap<String, Collection<String>> getDynamicDocType() {
		return (HashMap<String, Collection<String>>) getAttribute(DOCUMENT_TYPE);
	}

	/**
	 * @return RangeVO
	 */
	public Collection<RangeVO> getCollectionRangeVO() {
		return (Collection<RangeVO>) getAttribute(KEY_RANGEVO);

	}

	/**
	 * @param rangeVO
	 */
	public void setCollectionRangeVO(Collection<RangeVO> rangeVO) {
		setAttribute(KEY_RANGEVO, (ArrayList<RangeVO>) rangeVO);
	}

	/**
	 * @return StockAllocationVO
	 */
	public StockAllocationVO getStockAllocationVO() {
		return (StockAllocationVO) getAttribute(KEY_STOCKALLOCATIONVO);

	}

	/**
	 * @param stockAllocationVO
	 */
	public void setStockAllocationVO(StockAllocationVO stockAllocationVO) {
		setAttribute(KEY_STOCKALLOCATIONVO,
				(StockAllocationVO) stockAllocationVO);
	}

	/**
	 * @return String
	 */
	public String getMode() {
		return (getAttribute(KEY_MODE));
	}

	/**
	 * @param mode
	 */
	public void setMode(String mode) {
		setAttribute(KEY_MODE, mode);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockSession#setPartnerAirlines(java.util.Collection)
	 */
	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
		setAttribute(KEY_PARTNER_AIRLINES,partnerAirlines);
		
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockSession#getPartnerAirlines()
	 */
	public Page<AirlineLovVO> getPartnerAirlines() {
		return getAttribute(KEY_PARTNER_AIRLINES);
	}

}
