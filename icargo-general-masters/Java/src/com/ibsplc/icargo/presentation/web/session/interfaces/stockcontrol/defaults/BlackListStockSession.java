/*
 * BlackListStockSession.java Created on sep 20, 2005
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1952
 *
 */
public interface BlackListStockSession extends ScreenSession {
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
public BlacklistStockVO   getBlacklistStockVO();
/**
 * 
 * @param blacklistStockVO
 */
public void setBlacklistStockVO(BlacklistStockVO blacklistStockVO);
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
}
