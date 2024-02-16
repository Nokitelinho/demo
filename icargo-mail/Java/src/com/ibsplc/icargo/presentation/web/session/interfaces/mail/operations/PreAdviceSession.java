/*
 * PreAdviceSession.java Created on Jul 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2047
 *
 */
public interface PreAdviceSession extends ScreenSession {

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
	 * The setter method for PreAdviceVO
	 * @param preAdviceVO
	 */
	public void setPreAdviceVO(PreAdviceVO preAdviceVO);
    /**
     * The getter method for PreAdviceVO
     * @return PreAdviceVO
     */
    public PreAdviceVO getPreAdviceVO();
    
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
}
