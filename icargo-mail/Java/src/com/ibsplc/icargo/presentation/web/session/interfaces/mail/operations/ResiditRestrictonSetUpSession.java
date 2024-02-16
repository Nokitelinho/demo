/*
 * ResiditRestrictonSetUpSession.java Created on Sep 30, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.ResiditRestrictonVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-3108
 *
 */
public interface ResiditRestrictonSetUpSession extends ScreenSession {
	
	 /**
     * @return ArrayList<ResiditRestrictonVO>
     */
    ArrayList<ResiditRestrictonVO> getResiditRestrictonVOs();
    
    /**
     * @param residitRestrictonVOs
     */
    void setResiditRestrictonVOs(ArrayList<ResiditRestrictonVO> residitRestrictonVOs);
    
    /**
     * @return airportCode
     */
    String getAirportCode();
    
    /**
     * @param airportCode
     */
    void setAirportCode(String airportCode);
}
