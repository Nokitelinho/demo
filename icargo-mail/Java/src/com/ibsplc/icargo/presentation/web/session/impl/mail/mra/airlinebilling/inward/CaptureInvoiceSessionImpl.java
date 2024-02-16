/*
 * CaptureInvoiceSessionImpl.java Created on july 30, 2008
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.inward;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureInvoiceSession;

/**
 * @author A-3456
 * 
 */
public class CaptureInvoiceSessionImpl extends AbstractScreenSession implements
		CaptureInvoiceSession {
	/**
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.inward";

	/**
	 * Screen Id
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	/**
	 * Key Filter Vo
	 */
	private static final String KEY_FILTERVO = "AirlineCN51FilterVO";

	/**
	 * Key Form Vo
	 */
	private static final String KEY_FormVO = "AirlineCN51SummaryVO";

	/**
	 * @author a-3447 Key for getting one time values
	 * 
	 */

	private static final String KEY_ONETIME_CODES_MAP = "onetimecodesmap";
	/**
	 * @author A-5526 Key for getting WeightRoundingVos
	 * 
	 */
	private static final String KEY_WEIGHTROUNDINGVO = "rounding_wt_vo";
	/**
	 * @return
	 */
	@Override
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * @return
	 */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}

	/**
	 * @return Returns the AirlineCN51FilterVO.
	 */
	public AirlineCN51FilterVO getFilterVo() {
		return ((AirlineCN51FilterVO) getAttribute(KEY_FILTERVO));
	}

	/**
	 * @param AirlineCN51FilterVO
	 */
	public void setFilterVo(AirlineCN51FilterVO airlineCN51FilterVO) {
		setAttribute(KEY_FILTERVO, (AirlineCN51FilterVO) airlineCN51FilterVO);
	}

	/**
	 * set AirlineCN51SummaryVO
	 * 
	 * @param AirlineCN51SummaryVO
	 */

	public void setAirlineCN51SummaryVO(
			AirlineCN51SummaryVO airlineCN51SummaryVO) {
		setAttribute(KEY_FormVO, airlineCN51SummaryVO);
	}

	/**
	 * 
	 * get AirlineCN51SummaryVO
	 */

	public AirlineCN51SummaryVO getAirlineCN51SummaryVO() {
		return getAttribute(KEY_FormVO);
	}

	/**
	 * 
	 * remove AirlineCN51SummaryVO
	 */

	public void removeAirlineCN51SummaryVO() {
		removeAttribute(KEY_FormVO);
	}

	public HashMap<String, Collection<OneTimeVO>> getOneTimeMap() {
		return (HashMap<String, Collection<OneTimeVO>>) getAttribute(KEY_ONETIME_CODES_MAP);
	}

	/**
	 * Method to set the Onetimes map to session
	 * 
	 * @param oneTimeMap
	 *            The one time map to be set to session
	 */
	public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap) {
		setAttribute(KEY_ONETIME_CODES_MAP,
				(HashMap<String, Collection<OneTimeVO>>) oneTimeMap);
	}

	/**
	 * Method to remove One Time Map from session
	 */
	public void removeOneTimeMap() {
		removeAttribute(KEY_ONETIME_CODES_MAP);

	}
	/**
	 * Removing Filter Vos 
	 */
	public void removeFilterVo() {
		removeAttribute(KEY_FILTERVO);

	}
	/**
	 * @param WeightRoundingVO WeightRoundingVO
	 */
	public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO) {
		setAttribute(KEY_WEIGHTROUNDINGVO, unitRoundingVO);
	}

	/**
	 * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
	 */
	public UnitRoundingVO getWeightRoundingVO() {
		return getAttribute(KEY_WEIGHTROUNDINGVO);
	}

	/**
	 * @param key String
	 */
	public void removeWeightRoundingVO(String key) {
	}    

}
