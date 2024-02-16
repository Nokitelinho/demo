/*
 * ListCN51ScreenSession.java Created on Mar 14,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-2049
 * 
 */
public interface ListCN51ScreenSession extends ScreenSession {

	/**
	 * 
	 * @return
	 */
	// public Collection<OneTimeVO> getInterlineBlgTypeOneTimes();

	/**
	 * 
	 * @param oneTimeVOs
	 */
	// public void setInterlineBlgTypeOneTimes(Collection<OneTimeVO>
	// oneTimeVOs);

	/**
	 * Method to getAirlineCN51SummaryVOs
	 * 
	 * @return Page<AirlineCN51SummaryVO>
	 */
	public Page<AirlineCN51SummaryVO> getAirlineCN51SummaryVOs();

	/**
	 * Method to setAirlineCN51SummaryVOs
	 * 
	 * @param airlineCN51SummaryVOs
	 */
	void setAirlineCN51SummaryVOs(
			Page<AirlineCN51SummaryVO> airlineCN51SummaryVOs);

	/**
	 * 
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * 
	 * @param oneTimeMap
	 */
	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeMap);

	/**
	 * 
	 * @return
	 */
	public AirlineCN51FilterVO getAirlineCN51FilterVO();

	/**
	 * 
	 * @param filterVO
	 * @return
	 */
	public void setAirlineCN51FilterVO(AirlineCN51FilterVO filterVO);

	public Integer getTotalRecords();

	/**
	 * @param totalRecords
	 *            The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords);

	/**
	 * This method removes the totalRecords in session
	 */
	public void removeTotalRecords();

}
