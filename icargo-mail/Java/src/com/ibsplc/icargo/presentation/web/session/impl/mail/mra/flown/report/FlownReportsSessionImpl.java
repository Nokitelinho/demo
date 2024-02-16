/*
 * FlownReportsSessionImpl.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.flown.report;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.report.FlownReportsSession;


/**
 * @author a-2449
 *
 */
public class FlownReportsSessionImpl extends AbstractScreenSession
implements FlownReportsSession{
	
	
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String KEY_FLIGHTSTATUS = "flightstatus";
	
	private static final String SCREEN_ID = "mra.flown.flownreports";
	
	
	/**
	 * @return KEY_SCREEN_ID String
	 */
	public String getScreenID() {
		return SCREEN_ID;
	}
	/**
	 * @return MODULE_NAME String
	 */
	public String getModuleName() {
		return MODULE_NAME;
	}
	
	/**
	 *
	 * @return ArrayList<OneTimeVO>
	 */
	public ArrayList<OneTimeVO> getFlightStatus(){
		return getAttribute(KEY_FLIGHTSTATUS);
	}
	/**
	 *
	 * @param flightStatus
	 */
	public void setFlightStatus(ArrayList<OneTimeVO> flightStatus) {
		setAttribute(KEY_FLIGHTSTATUS, flightStatus);
	}
	
	
}
