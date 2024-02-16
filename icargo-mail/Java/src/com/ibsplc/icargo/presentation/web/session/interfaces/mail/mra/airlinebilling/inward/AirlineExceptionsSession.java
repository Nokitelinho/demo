/*
 * AirlineExceptionsSession.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-2521
 * 
 */
public interface AirlineExceptionsSession extends ScreenSession {

	/**
	 * @return airlineExceptionsVOs
	 */
	public Page<AirlineExceptionsVO> getAirlineExceptionsVOs();

	/**
	 * sets airlineExceptionsVOs
	 */
	void setAirlineExceptionsVOs(Page<AirlineExceptionsVO> airlineExceptionsVOs);

	/**
	 * 
	 * removes airlineExceptionsVOs
	 */
	public void removeAirlineExceptionsVOs();

	/**
	 * sets airlineExceptionsFilterVO
	 * 
	 * @param airlineExceptionsFilterVO
	 */
	public void setAirlineExceptionsFilterVO(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO);

	/**
	 * gets airlineExceptionsFilterVO
	 * 
	 * @return airlineExceptionsFilterVO
	 */
	public AirlineExceptionsFilterVO getAirlineExceptionsFilterVO();

	/**
	 * 
	 * removes airlineExceptionsFilterVO from session
	 */
	public void removeAirlineExceptionsFilterVO();

	/**
	 * 
	 * @return HashMap<String, Collection<OneTimeVO>>
	 */
	public HashMap<String, Collection<OneTimeVO>> getExceptionsOneTime();

	/**
	 * 
	 * @param ExceptionsOneTimeVOs
	 */
	public void setExceptionsOneTime(
			HashMap<String, Collection<OneTimeVO>> exceptionsOneTimeVOs);

	/**
	 * removes onetime from session
	 * 
	 */
	public void removeExceptionsOneTime();

	/**
	 * @return String[]
	 */
	public String[] getSelectedRows();

	/**
	 * @param str
	 */
	public void setSelectedRows(String[] str);

	/**
     * 
     */
	public void removeSelectedRows();

	/**
	 * Method to getTotalRecords
	 * 
	 * @return Integer
	 */
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
