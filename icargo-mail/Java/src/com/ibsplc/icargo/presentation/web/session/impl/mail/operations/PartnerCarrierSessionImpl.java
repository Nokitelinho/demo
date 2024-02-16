/*
 * PartnerCarrierSessionImpl.java Created on August 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PartnerCarrierSession;

/**
 * @author A-2047
 *
 */
public class PartnerCarrierSessionImpl extends AbstractScreenSession implements
		PartnerCarrierSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.masters.subclass";
	
	private static final String PARTNER_CARRIER_VOS = "partnerCarrierVOs";
	private static final String AIRPORT = "airport";
	
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
     * @return ArrayList<PartnerCarrierVO>
     */
	public ArrayList<PartnerCarrierVO> getPartnerCarrierVOs() {
		return (ArrayList<PartnerCarrierVO>)getAttribute(PARTNER_CARRIER_VOS);
	}
	
	/**
     * @param partnerCarrierVOs
     */
	public void setPartnerCarrierVOs(ArrayList<PartnerCarrierVO> partnerCarrierVOs) {
		setAttribute(PARTNER_CARRIER_VOS,partnerCarrierVOs);
	}

	/**
	 * @return String Airport
	 */
	public String getAirport() {
		return (String)getAttribute(AIRPORT);
	}
	
	/**
     * @param Airport
     */
	public void setAirport(String Airport) {
		setAttribute(AIRPORT,Airport);
		
	}	

}
