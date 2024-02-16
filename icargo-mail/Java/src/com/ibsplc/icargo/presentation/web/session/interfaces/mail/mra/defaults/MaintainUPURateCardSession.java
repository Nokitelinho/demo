/*
 * MaintainUPURateCardSession.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1556
 *
 */
public interface MaintainUPURateCardSession extends ScreenSession {
	/**
	 * @param rateCardVO
	 */
	public void setRateCardDetails(RateCardVO ratecardVO);
	/**
	 * @return
	 */
	public RateCardVO getRateCardDetails();

	/**
	 * to remove ratecard details
	 */
	public void removeRateCardDetails();
	/**
	 * @param ratelineVOs
	 */
	public void setRateLineDetails(Page<RateLineVO> ratelineVOs);
	/**
	 * @return
	 */
	public Page<RateLineVO> getRateLineDetails();

	/**
	 * to remove RateLine details
	 */
	public void removeRateLineDetails();

	/**
	 * @param ratelineVOs
	 */
	public void setNewRateLineDetails(Page<RateLineVO> ratelineVOs);
	/**
	 * @return
	 */
	public Page<RateLineVO> getNewRateLineDetails();

	/**
	 * to remove RateLine details
	 */
	public void removeNewRateLineDetails();

	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 * to remove one time values
	 */
	public void removeOneTimeVOs();
	/**
	 * @param fromPage
	 */
	public void setFromPage(String fromPage);
	/**
	 * @return String
	 */
	public String getFromPage();
	/**
	 * to remove fromPage
	 */
	public void removeFromPage();

	/**
	 * returns errorVOs
	 *
	 *  @return errorVOs
	 */
	public Collection<ErrorVO> getErrorVOs();

	/**
	 * sets errorVOs
	 */
	public void setErrorVOs(ArrayList<ErrorVO> errorVOs);

	/**
	 * removes errorVOs
	 */
	public void removeErrorVOs();
}
