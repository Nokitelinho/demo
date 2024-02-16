/*
 * AirportFacilityMasterSessionImpl.java Created on Jan 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.AirportFacilityMasterSession;
import java.util.ArrayList;
import java.util.Collection;
/**
 * @author A-2052
 *
 */
public class AirportFacilityMasterSessionImpl extends AbstractScreenSession
        implements AirportFacilityMasterSession {
	
	private static final String LIST_DETAILS="ULDAirportLocationVO";
	
	private static final String AIRPORTCODE="airportcode";
	
	private static final String KEY_FACILITYCODE="facilitycode";
	
	private static final String FACILITYTYPE_VALUE="facilityType";
	
	private static final String FILTERTYPE_STATUS = "uld.defaults.facilitytypes";
	
	// added by a-3278 for QF1006 on 04Aug08

	private static final String ONETIME_CONTENT = "uld.defaults.contentcodes";

	private static final String CONTENT_VALUE = "content";

	// a-3278 ends
    
    private static final String SCREENID = "uld.defaults.airportfacilitymaster";
	
	private static final String MODULE_NAME = "uld.defaults";
	
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
     * This method returns the ULDDamageDetailsListVO in session
     * @return Page<ClearanceListingVO>
     */

	public Collection<ULDAirportLocationVO> getULDAirportLocationVOs(){
    	return (Collection<ULDAirportLocationVO>)getAttribute(LIST_DETAILS);
    }
	/**
     * This method sets the ULDDamageDetailsListVO in session
     * @param paramCode
     */
	public void setULDAirportLocationVOs(Collection<ULDAirportLocationVO> paramCode){
		setAttribute(LIST_DETAILS,(ArrayList<ULDAirportLocationVO>)paramCode);
	}
	/**
     * This method removes the ULDDamageDetailsListVO in session
     */
	public void removeULDAirportLocationVOs(){
		removeAttribute(LIST_DETAILS);
	}
	
	/**
	    * @return
	    */
	public Collection<OneTimeVO> getFacilityType(){
		return (Collection<OneTimeVO>) getAttribute(FILTERTYPE_STATUS);
	}
	/**
	 * @param facilityType
	 */
	public void setFacilityType(Collection<OneTimeVO> facilityType){
		setAttribute(FILTERTYPE_STATUS,(ArrayList<OneTimeVO>) facilityType);
	}
	/**
	 * Methods for removing status
	 */
	public void removeFacilityType(){
		removeAttribute(FILTERTYPE_STATUS);
	}
	
	//added by a-3278 for QF1006 on 04Aug08 starts
	/**
	 * @return
	 */
	public Collection<OneTimeVO> getContent() {
		return (Collection<OneTimeVO>) getAttribute(ONETIME_CONTENT);
	}

	/**
	 * @param facilityType
	 */
	public void setContent(Collection<OneTimeVO> content) {
		setAttribute(ONETIME_CONTENT, (ArrayList<OneTimeVO>) content);
	}

	/**
	 * Methods for removing status
	 */
	public void removeContent() {
		removeAttribute(ONETIME_CONTENT);
	}

	/**
	 * @return Returns the listStatus.
	 */
	public String getContentValue() {
		return getAttribute(CONTENT_VALUE);
	}

	/**
	 * @param content
	 */
	public void setContentValue(String content) {
		setAttribute(CONTENT_VALUE, content);
	}
	//a-3278 ends

	 /**
	 * @return Returns the listStatus.
	 */
	public String getAirportCode() {
		return getAttribute(AIRPORTCODE);
	}

	/**
	 * @param airportCode
	 */
	public void setAirportCode(String airportCode) {
		setAttribute(AIRPORTCODE,airportCode);
	}
	 /**
	 * @return Returns the listStatus.
	 */
	public String getFacilityTypeValue() {
		return getAttribute(FACILITYTYPE_VALUE);
	}

	/**
	 * @param airportCode
	 */
	public void setFacilityTypeValue(String airportCode) {
		setAttribute(FACILITYTYPE_VALUE,airportCode);
	}
	
	/**
	 * @return
	 */
	public ArrayList<ULDAirportLocationVO> getFacilityCode(){
		return getAttribute(KEY_FACILITYCODE);
	}
	/**
	 * @param currency
	 */
	public void setFacilityCode(ArrayList<ULDAirportLocationVO> currency){
		setAttribute(KEY_FACILITYCODE,currency);	
	}	
	
}
