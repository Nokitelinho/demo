/*
 * BlackListStockSessionImpl.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;

import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.BlackListStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

import java.util.HashMap;

/**
 * @author A-1952
 * 
 */
public class BlackListStockSessionImpl extends AbstractScreenSession implements
		BlackListStockSession {

	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.blackliststock";

	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";

	private static final String DOCUMENT_TYPE = "documenttype";

	private static final String KEY_BLACKLISTSTOCKVO = "blackliststockvo";

	private static final String KEY_MODE = "mode";
	
	private static final String KEY_PARTNER_AIRLINES="partner.airlines";

	/**
	 * This method returns the SCREEN ID for the BlackList Stock holder screen
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}

	/**
	 * This method returns the MODULE name for the BlackList Stock holder screen
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}

	/**
	 * This method returns the BlacklistStockVO
	 * @return BlacklistStockVO
	 */
	public BlacklistStockVO getBlacklistStockVO() {
		return (BlacklistStockVO) getAttribute(KEY_BLACKLISTSTOCKVO);
	}
/**
 * @param blacklistStockVO
 */
	public void setBlacklistStockVO(BlacklistStockVO blacklistStockVO) {
		setAttribute(KEY_BLACKLISTSTOCKVO, (BlacklistStockVO) blacklistStockVO);
	}
/**
 * @param dynamicDocType
 */
	public void setDynamicDocType(
			HashMap<String, Collection<String>> dynamicDocType) {
		setAttribute(DOCUMENT_TYPE,
				(HashMap<String, Collection<String>>) dynamicDocType);
	}
/**
 * This method returns the document type
 * @return String
 */
	public HashMap<String, Collection<String>> getDynamicDocType() {
		return (HashMap<String, Collection<String>>) getAttribute(DOCUMENT_TYPE);
	}
/**
 * This method returns the mode
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

public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
	setAttribute(KEY_PARTNER_AIRLINES,partnerAirlines);
	
}

public Page<AirlineLovVO> getPartnerAirlines() {
	return getAttribute(KEY_PARTNER_AIRLINES);
}
}
