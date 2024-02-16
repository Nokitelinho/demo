/*
 * CheckEmbargoSession.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.checkembargos;

import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


/**
* @author A-1747
*
*/

public interface CheckEmbargoSession extends ScreenSession {
	/**
	 * Methods for getting shipment details
	 */
	public Collection<EmbargoDetailsVO> getEmbargos();
	/**
	 * Methods for setting shipment details
	 */
	public void setEmbargos(Collection<EmbargoDetailsVO> shipmentDetailsVO);

	/**
	 * Methods for getting target action
	 */
	public String getContinueAction();

	/**
	 * Methods for setting target action
	 */
	public void setContinueAction(String action);
	/**
	 * session variable to identify the from Screen status 
	 * @return
	 */
	public String getScreenMode();
	public void setScreenMode(String screenMode);
}
