/*
 * OffloadSessionImpl.java Created on June 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OffloadSession;

/**
 * @author A-1861
 *
 */
public class OffloadSessionImpl extends AbstractScreenSession
        implements OffloadSession {

	private static final String SCREEN_ID = "mailtracking.defaults.offload";
	private static final String MODULE_NAME = "mail.operations";

	private static final String ONETIME_OFFLOAD_TYPE = "offload_type";
	private static final String ONETIME_MAILCATEGORY = "mailcategory";
	private static final String ONETIME_CONTAINERTYPES = "onetime_containertypes";
	private static final String ONETIME_OFFLOAD_REASONCODE = "offload_reasoncode";
	private static final String ONETIME_MAILCLASS = "mailclass";
	private static final String FLIGHTVALIDATIONVO = "flightvalidationvo";
	private static final String OFFLOADVO = "offloadvo";
			
	/**
     * This method is used to set onetime values to the session
     * @param containerTypes - Collection<OneTimeVO>
     */
	public void setContainerTypes(Collection<OneTimeVO> containerTypes) {
		setAttribute(ONETIME_CONTAINERTYPES,(ArrayList<OneTimeVO>)containerTypes);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_CONTAINERTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getContainerTypes() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_CONTAINERTYPES);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param offloadtype - Collection<OneTimeVO>
     */
	public void setOffloadType(Collection<OneTimeVO> offloadtype) {
		setAttribute(ONETIME_OFFLOAD_TYPE,(ArrayList<OneTimeVO>)offloadtype);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_OFFLOAD_TYPE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOffloadType() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_OFFLOAD_TYPE);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory) {
		setAttribute(ONETIME_MAILCATEGORY,(ArrayList<OneTimeVO>)mailCategory);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAILCATEGORY);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param offloadreasoncode - Collection<OneTimeVO>
     */
	public void setOffloadReasonCode(Collection<OneTimeVO> offloadreasoncode) {
		setAttribute(ONETIME_OFFLOAD_REASONCODE,(ArrayList<OneTimeVO>)offloadreasoncode);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_OFFLOAD_REASONCODE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOffloadReasonCode() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_OFFLOAD_REASONCODE);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param mailClass - Collection<OneTimeVO>
     */
	public void setMailClass(Collection<OneTimeVO> mailClass) {
		setAttribute(ONETIME_MAILCLASS,(ArrayList<OneTimeVO>)mailClass);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCLASS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailClass() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAILCLASS);
	}
	
	/**
	 * This method is used to set FlightValidationVO to the session
	 * @param flightValidationVO - FlightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO){
		setAttribute(FLIGHTVALIDATIONVO,flightValidationVO);
	}

	/**
	 * This method returns the FlightValidationVO
	 * @return FLIGHTVALIDATIONVO - FlightValidationVO
	 */
	public FlightValidationVO getFlightValidationVO(){
		return getAttribute(FLIGHTVALIDATIONVO);
	}
	
	/**
	 * This method is used to set OffloadVO to the session
	 * @param offloadVO - OffloadVO
	 */
	public void setOffloadVO(OffloadVO offloadVO) {
		setAttribute(OFFLOADVO,offloadVO);
	}

	/**
	 * This method returns the OffloadVO
	 * @return OFFLOADVO - OffloadVO
	 */
	public OffloadVO getOffloadVO() {
		return getAttribute(OFFLOADVO);
	}
	
    /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}


}
