/*
 * ULDServiceabilityMasterSessionImpl.java Created on Sep 1, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDServiceabilityMasterSession;

/**
 * @author A-2052
 * 
 */
public class ULDServiceabilityMasterSessionImpl extends AbstractScreenSession
		implements ULDServiceabilityMasterSession {

	private static final String LIST_DETAILS = "ULDServiceabilityVO";

	private static final String KEY_FACILITYCODE = "facilitycode";

	private static final String FILTERTYPE_STATUS = "uld.defaults.PartyType";

	private static final String SCREENID = "uld.defaults.uldserviceability";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String PARTYTYPE_VALUE = "partyType";

	/**
	 * @return
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * @return
	 */
	public String getModuleName() {
		return MODULE_NAME;
	}

	/**
	 * This method returns the ULDServiceabilityVO in session
	 * 
	 * @return Page<ULDServiceabilityVO>
	 */

	public Collection<ULDServiceabilityVO> getULDServiceabilityVOs() {
		return (Collection<ULDServiceabilityVO>) getAttribute(LIST_DETAILS);
	}

	/**
	 * This method sets the ULDServiceabilityVO in session
	 * 
	 * @param paramCode
	 */
	public void setULDServiceabilityVOs(
			Collection<ULDServiceabilityVO> paramCode) {
		setAttribute(LIST_DETAILS, (ArrayList<ULDServiceabilityVO>) paramCode);
	}

	/**
	 * This method removes the ULDServiceabilityVO in session
	 */
	public void removeULDServiceabilityVOs() {
		removeAttribute(LIST_DETAILS);
	}

	/**
	 * @return
	 */
	public Collection<OneTimeVO> getPartyType() {
		return (Collection<OneTimeVO>) getAttribute(FILTERTYPE_STATUS);
	}

	/**
	 * @param PartyType
	 */
	public void setPartyType(Collection<OneTimeVO> partyType) {
		setAttribute(FILTERTYPE_STATUS, (ArrayList<OneTimeVO>) partyType);
	}

	/**
	 * Methods for removing status
	 */
	public void removePartyType() {
		removeAttribute(FILTERTYPE_STATUS);
	}

	/**
	 * @return
	 */
	public ArrayList<ULDServiceabilityVO> getFacilityCode() {
		return getAttribute(KEY_FACILITYCODE);
	}

	/**
	 * @param currency
	 */
	public void setFacilityCode(ArrayList<ULDServiceabilityVO> currency) {
		setAttribute(KEY_FACILITYCODE, currency);
	}

	/**
	 * @return Returns the partyType.
	 */
	public String getPartyTypeValue() {
		return getAttribute(PARTYTYPE_VALUE);
	}

	/**
	 * @param partyType
	 */
	public void setPartyTypeValue(String partyType) {
		setAttribute(PARTYTYPE_VALUE, partyType);
	}
}
