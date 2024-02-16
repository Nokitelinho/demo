/*
 * UploadMailSession.java Created on Oct 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1876
 *
 */
public interface UploadMailSession extends ScreenSession {

	/**
	 * The setter method for scannedDetailsVO
	 * @param scannedDetailsVO
	 */
	public void setScannedDetailsVO(ScannedDetailsVO scannedDetailsVO);
    /**
     * The getter method for ScannedDetailsVO
     * @return ScannedDetailsVO
     */
    public ScannedDetailsVO getScannedDetailsVO();
    
	/**
	 * The setter method for scannedSummaryVO
	 * @param scannedSummaryVO
	 */
	public void setScannedSummaryVO(ScannedDetailsVO scannedSummaryVO);
    /**
     * The getter method for ScannedSummaryVO
     * @return ScannedSummaryVO
     */
    public ScannedDetailsVO getScannedSummaryVO();
    
    
	/**
	 * Method for getting FlightValidationVO from session
	 * @return flightValidationVO
	 */
	public FlightValidationVO  getFlightValidationVO();
	/**
	 * Method for setting FlightValidationVO to session
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO);
	
	/**
	 * The setter method for OperationalFlightVO
	 * @param operationalFlightVO
	 */
	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO);
    /**
     * The getter method for OperationalFlightVO
     * @return OperationalFlightVO
     */
    public OperationalFlightVO getOperationalFlightVO();
    
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeContainerType - Collection<OneTimeVO>
     */
	public void setOneTimeContainerType(Collection<OneTimeVO> oneTimeContainerType);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeContainerType - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeContainerType();
	
	/**
     * This method is used to set String values to the session
     * @param pols - Collection<String>
     */
	public void setPols(Collection<String> pols);

	/**
     * This method returns the String vos
     * @return Collection<String>
     */
	public Collection<String> getPols();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeReasonCode - Collection<OneTimeVO>
     */
	public void setOneTimeReasonCode(Collection<OneTimeVO> oneTimeReasonCode);

	/**
     * This method returns the onetime vos
     * @return oneTimeReasonCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeReasonCode();
	/**
     * This method returns the PostalAdministrationVOS
     * @return POSTALADMINVOS - Collection<PostalAdministrationVO>
     */
	public Collection<PostalAdministrationVO> getPostalAdministrationVOs();
	
	/**
     * This method is used to set PostalAdministrationVOs to the session
     * @param postalAdministrationVOs - Collection<PostalAdministrationVO>
     */
	public void setPostalAdministrationVOs(Collection<PostalAdministrationVO> postalAdministrationVOs);
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeCat - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeRI - Collection<OneTimeVO>
     */
	public void setOneTimeRI(Collection<OneTimeVO> oneTimeRI);

	/**
     * This method returns the onetime vos
     * @return ONETIME_oneTimeRI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRI();
	
	/**
     * This method is used to set onetime values to the session
     * @param offloadReasons - Collection<OneTimeVO>
     */
	public void setOffloadOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeOffloadReasons);

	/**
     * This method returns the onetime vos
     * @return ONETIME_offloadReasons - Collection<OneTimeVO>
     */
	public HashMap<String, Collection<OneTimeVO>> getOffloadOneTimeVOs();

}

