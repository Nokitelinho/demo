/*
 * UploadMailSessionImpl.java Created on Oct 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;

/**
 * @author A-1876
 *
 */
public class UploadMailSessionImpl extends AbstractScreenSession
        implements UploadMailSession {

	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_SCANNEDDETAILSVO = "scannedDetailsVO";
	private static final String KEY_SCANNEDSUMMARYVO = "scannedSummaryVO";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightValidationVO";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalFlightVO";
	
	private static final String KEY_ONETIMECONTAINERTYPE = "containerType";
	private static final String KEY_ONETIMEREASONCODE = "reasonCode";
	private static final String KEY_POLS = "pols";
	private static final String POSTALADMINVOS = "PostalAdministrationVOs";
	private static final String KEY_ONETIMECAT = "oneTimeCat";
	private static final String KEY_ONETIMERI = "oneTimeRI";
	private static final String KEY_ONETIMEHNI = "oneTimeHNI";
	private static final String KEY_ONETIMEOFFLOADREASONS = "oneTimeOffloadReasons";
	
	
		
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
	
	/**
	 * The setter method for ScannedDetailsVO
	 * @param scannedDetailsVO
	 */
    public void setScannedDetailsVO(ScannedDetailsVO scannedDetailsVO) {
    	setAttribute(KEY_SCANNEDDETAILSVO,scannedDetailsVO);
    }
    /**
     * The getter method for scannedDetailsVO
     * @return scannedDetailsVO
     */
    public ScannedDetailsVO getScannedDetailsVO() {
    	return getAttribute(KEY_SCANNEDDETAILSVO);
    }
    
	/**
	 * The setter method for ScannedSummaryVO
	 * @param scannedSummaryVO
	 */
    public void setScannedSummaryVO(ScannedDetailsVO scannedSummaryVO) {
    	setAttribute(KEY_SCANNEDSUMMARYVO,scannedSummaryVO);
    }
    /**
     * The getter method for scannedSummaryVO
     * @return scannedSummaryVO
     */
    public ScannedDetailsVO getScannedSummaryVO() {
    	return getAttribute(KEY_SCANNEDSUMMARYVO);
    }
        
	/**
     * This method is used to get the flightValidationVO from the session
     * @return flightValidationVO
     */
	public FlightValidationVO getFlightValidationVO(){
	    return getAttribute(KEY_FLIGHTVALIDATIONVO);
	}
	
	/**
	 * This method is used to set the flightValidationVO in session
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO  flightValidationVO) {
	    setAttribute(KEY_FLIGHTVALIDATIONVO, flightValidationVO);
	}
	
	/**
     * This method is used to get the operationalFlightVO from the session
     * @return operationalFlightVO
     */
	public OperationalFlightVO getOperationalFlightVO(){
	    return getAttribute(KEY_OPERATIONALFLIGHTVO);
	}
	
	/**
	 * This method is used to set the operationalFlightVO in session
	 * @param operationalFlightVO
	 */
	public void setOperationalFlightVO(OperationalFlightVO  operationalFlightVO) {
	    setAttribute(KEY_OPERATIONALFLIGHTVO, operationalFlightVO);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeContainerType - Collection<OneTimeVO>
     */
	public void setOneTimeContainerType(Collection<OneTimeVO> oneTimeContainerType) {
		setAttribute(KEY_ONETIMECONTAINERTYPE,(ArrayList<OneTimeVO>)oneTimeContainerType);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMECONTAINERTYPE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeContainerType() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMECONTAINERTYPE);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeReasonCode - Collection<OneTimeVO>
     */
	public void setOneTimeReasonCode(Collection<OneTimeVO> oneTimeReasonCode) {
		setAttribute(KEY_ONETIMEREASONCODE,(ArrayList<OneTimeVO>)oneTimeReasonCode);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEREASONCODE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeReasonCode() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEREASONCODE);
	}
	
	/**
     * This method is used to set String values to the session
     * @param pols - Collection<String>
     */
	public void setPols(Collection<String> pols) {
		setAttribute(KEY_POLS,(ArrayList<String>)pols);
	}

	/**
     * This method returns the String vos
     * @return KEY_POLS - Collection<String>
     */
	public Collection<String> getPols() {
		return (Collection<String>)getAttribute(KEY_POLS);
	}
	/**
     * This method returns the PostalAdministrationVOS
     * @return POSTALADMINVOS - Collection<PostalAdministrationVO>
     */
	public Collection<PostalAdministrationVO> getPostalAdministrationVOs() {
		return (Collection<PostalAdministrationVO>)getAttribute(POSTALADMINVOS);
	}
	
	/**
     * This method is used to set PostalAdministrationVOs to the session
     * @param postalAdministrationVOs - Collection<PostalAdministrationVO>
     */
	public void setPostalAdministrationVOs(Collection<PostalAdministrationVO> postalAdministrationVOs) {
		setAttribute(POSTALADMINVOS,(ArrayList<PostalAdministrationVO>)postalAdministrationVOs);
	}
	
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat) {
		setAttribute(KEY_ONETIMECAT,(ArrayList<OneTimeVO>)oneTimeCat);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMECAT - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMECAT);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeRI - Collection<OneTimeVO>
     */
	public void setOneTimeRI(Collection<OneTimeVO> oneTimeRI) {
		setAttribute(KEY_ONETIMERI,(ArrayList<OneTimeVO>)oneTimeRI);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMERI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRI() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMERI);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI) {
		setAttribute(KEY_ONETIMEHNI,(ArrayList<OneTimeVO>)oneTimeHNI);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEHNI);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeOffloadReasons - Collection<OneTimeVO>
     */
	public void setOffloadOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeOffloadReasons) {
		setAttribute(KEY_ONETIMEOFFLOADREASONS,(HashMap<String, Collection<OneTimeVO>>)oneTimeOffloadReasons);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEOFFLOADREASONS - Collection<OneTimeVO>
     */
	public HashMap<String, Collection<OneTimeVO>> getOffloadOneTimeVOs() {
		return (HashMap<String, Collection<OneTimeVO>>)getAttribute(KEY_ONETIMEOFFLOADREASONS);
	}

}
