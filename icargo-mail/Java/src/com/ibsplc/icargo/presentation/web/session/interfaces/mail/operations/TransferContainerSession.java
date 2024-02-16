/*
 * TransferContainerSession.java Created on Oct 4, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1876
 *
 */
public interface TransferContainerSession extends ScreenSession {
	/**
	 * This method is used to set ContainerVO to the session
	 * @param containerVO - ContainerVO
	 */
	public void setContainerVO(ContainerVO containerVO);

	/**
	 * This method returns the ContainerVO
	 * @return CONTAINERVO - ContainerVO
	 */
	public ContainerVO getContainerVO();
	
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
	 * This method is used to set Collection<ContainerVO> to the session
	 * @param containerVOs - Collection<ContainerVO>
	 */
	public void setSelectedContainerVOs(Collection<ContainerVO> containerVOs);

	/**
	 * This method returns the Collection<ContainerVO>
	 * @return SELECTED_CONTAINERVOS - Collection<ContainerVO>
	 */
	public Collection<ContainerVO> getSelectedContainerVOs();
	
	/**
	 * This method is used to set AirlineValidationVO to the session
	 * @param airlineValidationVO - AirlineValidationVO
	 */
	public void setAirlineValidationVO(AirlineValidationVO airlineValidationVO);

	/**
	 * This method returns the FlightValidationVO
	 * @return AIRLINEVALIDATIONVO - AirlineValidationVO
	 */
	public AirlineValidationVO getAirlineValidationVO();
	
}

