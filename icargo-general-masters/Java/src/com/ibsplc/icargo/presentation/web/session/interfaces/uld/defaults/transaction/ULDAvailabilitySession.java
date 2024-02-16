/*
 * ULDAvailabilitySession.java Created on Mar 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-3278
 * 
 */
public interface ULDAvailabilitySession extends ScreenSession {
	/**
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode);

	/**
	 * 
	 * @return
	 */
	public String getCompanyCode();

	/**
	 * 
	 * @return
	 */
	public ArrayList<OneTimeVO> getPartyType();

	/**
	 * 
	 * @param partyType
	 */
	public void setPartyType(ArrayList<OneTimeVO> partyType);

}
