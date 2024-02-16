/*
 * TransferContainerSessionImpl.java Created on Oct 04, 2006
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
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferContainerSession;

/**
 * @author A-1876
 *
 */
public class TransferContainerSessionImpl extends AbstractScreenSession
        implements TransferContainerSession {
	private static final String SCREEN_ID = "mailtracking.defaults.transfercontainer";
	private static final String MODULE_NAME = "mail.operations";
	
	private static final String FLIGHTVALIDATIONVO = "flightvalidationvo";
	private static final String SELECTED_CONTAINERVOS = "selected_containers";
	private static final String AIRLINEVALIDATIONVO = "airlinevalidationvo";
	private static final String CONTAINERVO = "containervo";

	
	/**
	 * This method is used to set ContainerVO to the session
	 * @param containerVO - ContainerVO
	 */
	public void setContainerVO(ContainerVO containerVO) {
		setAttribute(CONTAINERVO,containerVO);
	}

	/**
	 * This method returns the ContainerVO
	 * @return CONTAINERVO - ContainerVO
	 */
	public ContainerVO getContainerVO() {
		return getAttribute(CONTAINERVO);
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
	 * This method is used to set Collection<ContainerVO> to the session
	 * @param containerVOs - Collection<ContainerVO>
	 */
	public void setSelectedContainerVOs(Collection<ContainerVO> containerVOs) {
		setAttribute(SELECTED_CONTAINERVOS,(ArrayList<ContainerVO>)containerVOs);
	}

	/**
	 * This method returns the Collection<ContainerVO>
	 * @return SELECTED_CONTAINERVOS - Collection<ContainerVO>
	 */
	public Collection<ContainerVO> getSelectedContainerVOs() {
		return (Collection<ContainerVO>)getAttribute(SELECTED_CONTAINERVOS);
	}
	
	/**
	 * This method is used to set AirlineValidationVO to the session
	 * @param airlineValidationVO - AirlineValidationVO
	 */
	public void setAirlineValidationVO(AirlineValidationVO airlineValidationVO) {
		setAttribute(AIRLINEVALIDATIONVO,airlineValidationVO);
	}

	/**
	 * This method returns the FlightValidationVO
	 * @return AIRLINEVALIDATIONVO - AirlineValidationVO
	 */
	public AirlineValidationVO getAirlineValidationVO() {
		return getAttribute(AIRLINEVALIDATIONVO);
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
