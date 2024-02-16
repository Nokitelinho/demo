/*
 * ListCN51ScreenSessionImpl.java Created on Mar 14,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.defaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListCN51ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 *	@author A-2049
 */
public class ListCN51ScreenSessionImpl extends AbstractScreenSession
									   implements ListCN51ScreenSession {

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";

	private static final String KEY_INTBLGTYPE
								= "mailtracking.mra.defaults.intBlgType";

	private static final String KEY_ARLC51SMYVOS
								= "mailtracking.mra.defaults.arlCN51SmyVOs";

	private static final String KEY_ONETIME_MAP
								= "mailtracking.mra.defaults.oneTimeMap";

	private static final String KEY_ARLCN51FILTERVO
								= "mailtracking.mra.defaults.airlineCN51FilterVO";
	private static final String  TOTALRECORDS = "totalRecords";

	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}

	/*public Collection<OneTimeVO> getInterlineBlgTypeOneTimes() {
		// TODO Auto-generated method stub
		return (Collection<OneTimeVO>)this.getAttribute(KEY_INTBLGTYPE);
	}*/

	/*public void setInterlineBlgTypeOneTimes(Collection<OneTimeVO> oneTimeVOs) {
		this.setAttribute(KEY_INTBLGTYPE,(ArrayList<OneTimeVO>)oneTimeVOs);
	}*/
	/**
	 * Method to getAirlineCN51SummaryVOs
	 * @return Page<AirlineCN51SummaryVO>
	 */

	public Page<AirlineCN51SummaryVO> getAirlineCN51SummaryVOs() {
		//		 TODO Auto-generated method stub
		return (Page<AirlineCN51SummaryVO>)this.getAttribute(KEY_ARLC51SMYVOS);
	}
    /**
     * Method to setAirlineCN51SummaryVOs
     * @param airlineCN51SummaryVOs
     */
	public void setAirlineCN51SummaryVOs(Page<AirlineCN51SummaryVO> airlineCN51SummaryVOs) {
		this.setAttribute(KEY_ARLC51SMYVOS,(Page<AirlineCN51SummaryVO>)airlineCN51SummaryVOs);
	}

	public Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		// TODO Auto-generated method stub
		return (HashMap<String, Collection<OneTimeVO>>)getAttribute(KEY_ONETIME_MAP);
	}

	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeMap) {
		// TODO Auto-generated method stub
		this.setAttribute(KEY_ONETIME_MAP,(HashMap<String,Collection<OneTimeVO>>)oneTimeMap);
	}

	public AirlineCN51FilterVO getAirlineCN51FilterVO() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_ARLCN51FILTERVO);
	}

	public void setAirlineCN51FilterVO(AirlineCN51FilterVO filterVO) {
		// TODO Auto-generated method stub
		setAttribute(KEY_ARLCN51FILTERVO,filterVO);
	}
	/**
	 * @return Returns the totalRecords.
	 */
	public Integer getTotalRecords() {
		return getAttribute(TOTALRECORDS);
	}
	
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTALRECORDS,totalRecords);
	}
	
	/**
     * This method removes the totalRecords in session
     */
	public void removeTotalRecords() {
	 	removeAttribute(TOTALRECORDS);
	}
}
