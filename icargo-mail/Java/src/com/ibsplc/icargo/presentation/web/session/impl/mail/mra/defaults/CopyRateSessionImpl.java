/*
 * CopyRateSessionImpl.java Created on Feb 8, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyRateSession;

/**
 * @author A-2280
 *
 */
public class CopyRateSessionImpl extends AbstractScreenSession
                                         implements CopyRateSession{

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.copyrate";
	private static final String KEY_RATECARDVO="ratecardvo";

	private static final String KEY_SELECTED_RATELINES="ratelinevos";

	/**
	 *
	 * @param rateCardVO
	 */
	public void setRateCardVO(RateCardVO rateCardVO) {
		setAttribute(KEY_RATECARDVO,rateCardVO);

	}

	/**
	 *
	 * @return
	 */
	public RateCardVO getRateCardVO() {

		return getAttribute(KEY_RATECARDVO);

	}
	/**
	 *
	 *
	 */

	public void removeRateCardVO() {
		removeAttribute(KEY_RATECARDVO);

	}

	/**
	 *
	 * @param rateLineVOs
	 */
	public void setSelectedRateLines(Collection<RateLineVO> rateLineVOs) {
		setAttribute(KEY_SELECTED_RATELINES,(ArrayList<RateLineVO>)rateLineVOs);

	}

	/**
	 *
	 * @return
	 */
	public Collection<RateLineVO> getSelectedRateLines() {
		// TODO Auto-generated method stub
		return (Collection<RateLineVO>)getAttribute(KEY_SELECTED_RATELINES);
	}

	/**
	 *
	 */
	public void removeSelectedRateLines() {
		removeAttribute(KEY_SELECTED_RATELINES);

	}



	@Override
	public String getScreenID() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MODULE_NAME;
	}

}
