/*
 * UCMFlightExceptionListSession.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;

import java.util.ArrayList;
import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2001
 *
 */
public interface UCMFlightExceptionListSession extends ScreenSession {
	    
	/**
	 * @return
	 */
	public ArrayList<UCMExceptionFlightVO> getUcmExceptionFlightVOs();

	/**
	 * @param paramCode
	 */
	public void setUcmExceptionFlightVOs(
			ArrayList<UCMExceptionFlightVO> ucmExceptionFlightVOs);

	/**
	 * 
	 * 
	 */
	public void removeUcmExceptionFlightVOs();
    
}
