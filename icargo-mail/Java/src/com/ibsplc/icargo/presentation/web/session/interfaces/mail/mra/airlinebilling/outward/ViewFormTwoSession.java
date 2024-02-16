/*
 * ViewFormTwoSession.java Created on July 22, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 *  
 * @author A-3434
 * 
 */

public interface ViewFormTwoSession extends ScreenSession{
	/**
	 * 
	 * @return Collection<AirlineForBillingVO>
	 */
	public Collection<AirlineForBillingVO> getAirlineForBillingVOs();
	
	/**
	 * 
	 * @param airlineForBillingVOs
	 */
	public void setAirlineForBillingVOs(Collection<AirlineForBillingVO> airlineForBillingVOs);
	
	/**
	 * 
	 *
	 */
	public void removeAirlineForBillingVOs();
}
