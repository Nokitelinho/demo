/*
 * ULDPoolOwnersSession.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
/**
 * 
 * @author A-2046
 *
 */
public interface ULDPoolOwnersSession extends ScreenSession {
	/**
	 * 
	 * @return screenID
	 */
	public String getScreenID();

	/**
	 * 
	 * @return modulename
	 */
	public String getModuleName();

	/**
	 * 
	 * @return FlightValidationVO
	 */
	public FlightValidationVO getFlightValidationVOSession();

	/**
	 * 
	 * @param flightValidationVO
	 */
	public void setFlightValidationVOSession(
			FlightValidationVO flightValidationVO);
	/**
	 * 
	 * @return
	 */
	public ULDPoolOwnerVO getUldPoolOwnerVO();
	/**
	 * 
	 * @param poolDetails
	 */
	public void setUldPoolOwnerVO(ULDPoolOwnerVO poolOwnerVO);

}
