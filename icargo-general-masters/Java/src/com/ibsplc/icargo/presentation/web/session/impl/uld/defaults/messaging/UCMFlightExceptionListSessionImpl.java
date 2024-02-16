/*
 * UCMFlightExceptionListSessionImpl.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.messaging;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMFlightExceptionListSession;

/**
 * @author A-2001
 * 
 */
public class UCMFlightExceptionListSessionImpl extends AbstractScreenSession implements
	UCMFlightExceptionListSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.ucmflightexceptionlist";

	private static final String KEY_UCMEXCEPIONFLIGHTVOS = "ucmExceptionFlightVOs";

	
	/**
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
	public ArrayList<UCMExceptionFlightVO> getUcmExceptionFlightVOs() {
		return (ArrayList<UCMExceptionFlightVO>) getAttribute(KEY_UCMEXCEPIONFLIGHTVOS);
	}

	/**
	 * @param paramCode
	 */
	public void setUcmExceptionFlightVOs(
			ArrayList<UCMExceptionFlightVO> ucmExceptionFlightVOs) {
		setAttribute(KEY_UCMEXCEPIONFLIGHTVOS,
				(ArrayList<UCMExceptionFlightVO>) ucmExceptionFlightVOs);
	}

	/**
	 * 
	 * 
	 */
	public void removeUcmExceptionFlightVOs() {
		removeAttribute(KEY_UCMEXCEPIONFLIGHTVOS);
	}

}
