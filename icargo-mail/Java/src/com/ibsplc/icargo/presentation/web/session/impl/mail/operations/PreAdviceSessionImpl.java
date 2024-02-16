/*
 * PreAdviceSessionImpl.java Created on July 03, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PreAdviceSession;

/**
 * @author A-2047
 *
 */
public class PreAdviceSessionImpl extends AbstractScreenSession implements
		PreAdviceSession {

	private static final String SCREEN_ID = "mailtracking.defaults.preadvice";
	private static final String MODULE_NAME = "mail.operations";
	
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalFlightVO";
	private static final String KEY_PREADVICEVO = "preAdviceVO";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightValidationVO";
	
    /**
     * @return SCREEN_ID - String
     */
	
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	
	public String getModuleName() {
		return MODULE_NAME;
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
	 * This method is used to set the preAdviceVO in session
	 * @param preAdviceVO
	 */
	public void setPreAdviceVO(PreAdviceVO preAdviceVO) {
		setAttribute(KEY_PREADVICEVO, preAdviceVO);
	}

	/**
     * This method is used to get the preAdviceVO from the session
     * @return preAdviceVO
     */
	public PreAdviceVO getPreAdviceVO() {
		return getAttribute(KEY_PREADVICEVO);
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

}
