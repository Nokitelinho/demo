/*
 * AssignContainerSession.java Created on May 31, 2006
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
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1556
 *
 */
public interface AssignContainerSession extends ScreenSession {

	/**
	 * This method is used to set ContainerAssignmentVO to the session
	 * @param containerAssignmentVO - ContainerAssignmentVO
	 */
	public void setContainerAssignmentVO(ContainerAssignmentVO containerAssignmentVO);

	/**
	 * This method returns the ContainerAssignmentVO
	 * @return CONTAINERASSIGNMENTVO - ContainerAssignmentVO
	 */
	public ContainerAssignmentVO getContainerAssignmentVO();
	
	/**
	 * The setter method for pointOfLadings
	 * @param pointOfLadings
	 */
    void setPointOfLadings(
    		Collection<String> pointOfLadings);
    /**
     * The getter method for pointOfLadings
     * @return pointOfLadings
     */
    Collection<String> getPointOfLadings();
	
	/**
     * This method is used to set Configuration value to the session
     * @param departed_config - String
     */
	public void setNonDepartedConfiguration(String nondepartedConfig);

	/**
     * This method returns the Configuration value
     * @return PARAMETER_NONDEPARETD_CONFIGURATION - String
     */
	public String getNonDepartedConfiguration();
	
	/**
     * This method is used to set onetime values to the session
     * @param containerTypes - Collection<OneTimeVO>
     */
	public void setFlightStatus(Collection<OneTimeVO> flightStatus);

	/**
     * This method returns the onetime vos
     * @return ONETIME_FLIGHTSTATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getFlightStatus();
	
	/**
     * This method is used to set Configuration value to the session
     * @param departed_config - String
     */
	public void setDepartedConfiguration(String departedConfig);

	/**
     * This method returns the Configuration value
     * @return PARAMETER_CONFIGURATION - String
     */
	public String getDepartedConfiguration();

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
	 * This method is used to set ContainerVOs to the session
	 * @param containervos - Collection<ContainerVO>
	 */
	public void setContainerVOs(Collection<ContainerVO> containervos);

	/**
	 * This method returns the ContainerVOs
	 * @return CONTAINERVOS - Collection<ContainerVO>
	 */
	public Collection<ContainerVO> getContainerVOs();

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
	
	HashMap<String,Collection<String>> getPolPouMap();
	

	void setPolPouMap(HashMap<String,Collection<String>> polPouMap);
	
	public void setReassignStatus(String status);
	
	public String getReassignStatus();

}

