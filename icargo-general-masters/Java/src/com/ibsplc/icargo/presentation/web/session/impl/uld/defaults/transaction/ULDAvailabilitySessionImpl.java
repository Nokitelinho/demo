/*
 * ULDAvailabilitySessionImpl.java Created on Mar 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.transaction;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ULDAvailabilitySession;

/**
 * @author A-3278
 * 
 */
public class ULDAvailabilitySessionImpl extends AbstractScreenSession implements
		ULDAvailabilitySession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.uldavailability";

	private static final String KEY_COMPANYCODE = "companyCode";

	private static final String KEY_PARTYTYP = "partyType";

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
	public String getCompanyCode() {
		return getAttribute(KEY_COMPANYCODE);
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		setAttribute(KEY_COMPANYCODE, companyCode);
	}

	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getPartyType() {
		return getAttribute(KEY_PARTYTYP);
	}

	/**
	 * @param partyType
	 */
	public void setPartyType(ArrayList<OneTimeVO> partyType) {
		setAttribute(KEY_PARTYTYP, partyType);
	}

}
