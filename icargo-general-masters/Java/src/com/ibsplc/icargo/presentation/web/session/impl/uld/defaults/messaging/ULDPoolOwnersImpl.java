/*
 * ULDPoolOwnersImpl.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.messaging;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDPoolOwnersSession;

/**
 * 
 * @author A-2046
 * 
 */
public class ULDPoolOwnersImpl extends AbstractScreenSession implements
		ULDPoolOwnersSession {

	private static final String SCREENID = "uld.defaults.uldpoolowners";

	private static final String MODULE = "uld.defaults";

	private static final String KEY_FLIGHTDETAILS = "flightDetails";

	private static final String KEY_POOLDETAILS = "pooldetails";

	/**
	 * 
	 * /** Method to get ScreenID
	 * 
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 * 
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}
/**
 * @return
 */
	public FlightValidationVO getFlightValidationVOSession() {
		return getAttribute(KEY_FLIGHTDETAILS);
	}
	/**
	 * @param flightValidationVO
	 */
	public void setFlightValidationVOSession(
			FlightValidationVO flightValidationVO) {
		setAttribute(KEY_FLIGHTDETAILS, flightValidationVO);
	}
	/**
	 * @return
	 */
	public ULDPoolOwnerVO getUldPoolOwnerVO() {
		return getAttribute(KEY_POOLDETAILS);

	}
/**
 * @param poolOwnerVO
 */
	public void setUldPoolOwnerVO(ULDPoolOwnerVO poolOwnerVO) {
		setAttribute(KEY_POOLDETAILS, poolOwnerVO);
	}

}
