/*
 * CopyRateSession.java Created on Feb 8, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2280
 *
 */
public interface CopyRateSession extends ScreenSession{

	/**
	 *
	 * @param rateCardVO
	 */
	public void setRateCardVO(RateCardVO rateCardVO);
	/**
	 *
	 * @return
	 */
	public RateCardVO getRateCardVO();
	/**
	 *
	 *
	 */
	public void removeRateCardVO();
	/**
	 *
	 * @param rateLineVOs
	 */
	public void setSelectedRateLines(Collection<RateLineVO> rateLineVOs);
	/**
	 *
	 * @return
	 */
	public Collection<RateLineVO> getSelectedRateLines();
	/**
	 *
	 *
	 */
	public void removeSelectedRateLines();
}
