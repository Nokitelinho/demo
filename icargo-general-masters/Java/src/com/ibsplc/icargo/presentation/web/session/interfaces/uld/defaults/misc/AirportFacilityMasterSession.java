/*
 * AirportFacilityMasterSession.java Created on Jan 16, 2006
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
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2052
 *
 */
public interface AirportFacilityMasterSession extends ScreenSession {

	/**
     * This method returns the ULDDamageDetailsListVO in session
     * @return Page<ClearanceListingVO>
     */

	public Collection<ULDAirportLocationVO> getULDAirportLocationVOs();

	/**
	 * This method sets the ULDDamageDetailsListVO in session
	 * @param paramCode
	 */
	public void setULDAirportLocationVOs(Collection<ULDAirportLocationVO> paramCode);
		
	/**
	 * This method removes the ULDDamageDetailsListVO in session
	 */
	public void removeULDAirportLocationVOs();
	// added by a-3278 for QF1006 on 04Aug08
	/**
	 * 
	 * @return
	 */
	public Collection<OneTimeVO> getContent();

	/**
	 * Methods for setting content
	 */
	public void setContent(Collection<OneTimeVO> content);

	/**
	 * Methods for removing content
	 */
	public void removeContent();

	/**
	 * @return Returns the listStatus.
	 */
	public String getContentValue();

	/**
	 * @param listStatus
	 *            The listStatus to set.
	 */
	public void setContentValue(String content);

	// added by a-3278 for QF1006 on 04Aug08 ends
	/**
	 * 
	 * @return
	 */
	public Collection<OneTimeVO> getFacilityType();
	/**
	 * Methods for setting status
	 */
	public void setFacilityType(Collection<OneTimeVO> facilityType);
	/**
	 * Methods for removing status
	 */
	public void removeFacilityType();
	
	/**
	 * @return Returns the listStatus.
	 */
	public String getAirportCode();
	
	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setAirportCode(String airportCode);
	
	/**
	 * @return Returns the listStatus.
	 */
	public String getFacilityTypeValue() ;
	
	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setFacilityTypeValue(String airportCode);
	
	/**
	 * @return
	 */
	public ArrayList<ULDAirportLocationVO> getFacilityCode();
	
	/**
	 * 
	 * @param currency
	 */
	public void setFacilityCode(ArrayList<ULDAirportLocationVO> currency);
	
}
