/*
 * OffloadSession.java Created on June 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1861
 *
 */
public interface OffloadSession extends ScreenSession {

	/**
     * This method is used to set onetime values to the session
     * @param offloadtype - Collection<OneTimeVO>
     */
	public void setOffloadType(Collection<OneTimeVO> offloadtype);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OFFLOAD_TYPE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOffloadType();
	
	/**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory);

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory();
	
	/**
     * This method is used to set onetime values to the session
     * @param containerTypes - Collection<OneTimeVO>
     */
	public void setContainerTypes(Collection<OneTimeVO> containerTypes);

	/**
     * This method returns the onetime vos
     * @return ONETIME_CONTAINERTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getContainerTypes();
	
	/**
     * This method is used to set onetime values to the session
     * @param offloadreasoncode - Collection<OneTimeVO>
     */
	public void setOffloadReasonCode(Collection<OneTimeVO> offloadreasoncode);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OFFLOAD_REASONCODE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOffloadReasonCode();
	
	/**
     * This method is used to set onetime values to the session
     * @param mailClass - Collection<OneTimeVO>
     */
	public void setMailClass(Collection<OneTimeVO> mailClass);

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCLASS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailClass();
	
	/**
	 * This method is used to set FlightValidationVO to the session
	 * @param flightValidationVO - FlightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO);

	/**
	 * This method returns the FlightValidationVO
	 * @return FLIGHTVALIDATIONVO - FlightValidationVO
	 */
	public FlightValidationVO getFlightValidationVO();
	
	/**
	 * This method is used to set OffloadVO to the session
	 * @param offloadVO - OffloadVO
	 */
	public void setOffloadVO(OffloadVO offloadVO);

	/**
	 * This method returns the OffloadVO
	 * @return OFFLOADVO - OffloadVO
	 */
	public OffloadVO getOffloadVO();
	
}

