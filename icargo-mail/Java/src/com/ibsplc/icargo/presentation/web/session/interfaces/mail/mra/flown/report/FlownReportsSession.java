/*
 * FlownReportsSession.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.report;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-2449
 *
 */
public interface FlownReportsSession extends ScreenSession{
	
	/**
	 *
	 * @return ArrayList<OneTimeVO> flightStatus
	 */
	public ArrayList<OneTimeVO> getFlightStatus();
	
	/**
	 *
	 * @param flightStatus
	 */
	public void setFlightStatus(ArrayList<OneTimeVO> flightStatus);
	

}
