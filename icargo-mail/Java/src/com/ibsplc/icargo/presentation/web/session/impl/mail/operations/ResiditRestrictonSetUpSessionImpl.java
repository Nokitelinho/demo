/*
 * ResiditRestrictonSetUpSessionImpl.java Created on Sep 30, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.ResiditRestrictonVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ResiditRestrictonSetUpSession;

/**
 * @author A-3108
 *
 */
public class ResiditRestrictonSetUpSessionImpl extends AbstractScreenSession implements
ResiditRestrictonSetUpSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.residitrestrictionsetup";
	
	private static final String RESIDIT_RESTRICTION_VOS = "residitRestrictonVs";
	private static final String AIRPORT = "airportCode";
	
	/**
	 * @return Screen Id
	 */
	public String getScreenID() {
		
		return SCREEN_ID;
	}

	/**
	 * @return Module Name
	 */
	public String getModuleName() {
		
		return MODULE_NAME;
	}
	
	/**
     * @return ArrayList<ResiditRestrictonVO>
     */
	public ArrayList<ResiditRestrictonVO> getResiditRestrictonVOs() {
		return (ArrayList<ResiditRestrictonVO>)getAttribute(RESIDIT_RESTRICTION_VOS);
	}
	
	/**
     * @param residitRestrictonVOs
     */
	public void setResiditRestrictonVOs(ArrayList<ResiditRestrictonVO> residitRestrictonVs) {
		setAttribute(RESIDIT_RESTRICTION_VOS,residitRestrictonVs);
	}

	/**
	 * @return String Airport
	 */
	public String getAirportCode() {
		return (String)getAttribute(AIRPORT);
	}
	
	/**
     * @param Airport
     */
	public void setAirportCode(String airportCode) {
		setAttribute(AIRPORT,airportCode);
		
	}	

}
