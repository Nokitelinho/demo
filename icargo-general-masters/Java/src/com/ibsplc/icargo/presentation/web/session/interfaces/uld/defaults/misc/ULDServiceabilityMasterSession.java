/*
 * ULDServiceabilityMasterSession.java Created on Sep 1, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2052
 *
 */
public interface ULDServiceabilityMasterSession extends ScreenSession {

	/**
	 * This method returns the ULDDamageDetailsListVO in session
	 * @return Page<ClearanceListingVO>
	 */

	public Collection<ULDServiceabilityVO> getULDServiceabilityVOs();

	/**
	 * This method sets the ULDServiceabilityVO in session
	 * @param paramCode
	 */
	public void setULDServiceabilityVOs(
			Collection<ULDServiceabilityVO> paramCode);

	/**
	 * This method removes the ULDServiceabilityVO in session
	 */
	public void removeULDServiceabilityVOs();

	/**
	 * @return
	 */
	public ArrayList<ULDServiceabilityVO> getFacilityCode();

	/**
	 * 
	 * @param currency
	 */
	public void setFacilityCode(ArrayList<ULDServiceabilityVO> currency);

	/**
	 * @return
	 */
	public Collection<OneTimeVO> getPartyType();

	/**
	 * @param facilityType
	 */
	public void setPartyType(Collection<OneTimeVO> partyType);

	/**
	 * Methods for removing status
	 */
	public void removePartyType();

	/**
	 * @return Returns the listStatus.
	 */
	public String getPartyTypeValue();

	/**
	 * @param airportCode
	 */
	public void setPartyTypeValue(String airportCode);
}
