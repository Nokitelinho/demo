/*
 * AssignContainerSessionImpl.java 
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
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;

/**
 * @author A-1556
 *
 */
public class AssignContainerSessionImpl extends AbstractScreenSession
        implements AssignContainerSession {

	private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";
	private static final String MODULE_NAME = "mail.operations";

	private static final String PARAMETER_DEPARTED_CONFIGURATION = "parameter_departed_configuration";
	private static final String PARAMETER_NONDEPARTED_CONFIGURATION = "parameter_nondeparted_configuration";
	private static final String ONETIME_CONTAINERTYPES = "onetime_containertypes";
	private static final String ONETIME_FLIGHTSTATUS = "onetime_flightstatus";
	private static final String CONTAINERVOS = "containervos";
	private static final String FLIGHTVALIDATIONVO = "flightvalidationvo";
	private static final String POINT_OF_UNLADINGS = "pointofunladings";
	private static final String SELECTED_CONTAINERVOS = "selected_containers";
	private static final String AIRLINEVALIDATIONVO = "airlinevalidationvo";
	private static final String CONTAINERASSIGNMENTVO = "containerassignmentvo";
	 //Added by A-5160 for ICRD-92869 
	private static final String KEY_POL_POU = "polpou";
	private static final String REASSIGN_STATUS="reassignstatus";

	
	/**
	 * This method is used to set ContainerAssignmentVO to the session
	 * @param containerAssignmentVO - ContainerAssignmentVO
	 */
	public void setContainerAssignmentVO(ContainerAssignmentVO containerAssignmentVO) {
		setAttribute(CONTAINERASSIGNMENTVO,containerAssignmentVO);
	}

	/**
	 * This method returns the ContainerAssignmentVO
	 * @return CONTAINERASSIGNMENTVO - ContainerAssignmentVO
	 */
	public ContainerAssignmentVO getContainerAssignmentVO() {
		return getAttribute(CONTAINERASSIGNMENTVO);
	}
	
	/**
	 * The setter method for pointOfLadings
	 * @param pointOfLadings
	 */
    public void setPointOfLadings(
    		Collection<String> pointOfLadings) {
    	setAttribute(POINT_OF_UNLADINGS,(ArrayList<String>)pointOfLadings);
    }
    /**
     * The getter method for pointOfLadings
     * @return pointOfLadings
     */
    public Collection<String> getPointOfLadings() {
    	return (Collection<String>)getAttribute(POINT_OF_UNLADINGS);
    }
	
	/**
     * This method is used to set config value to the session
     * @param nondepartedConfig - String
     */
	public void setNonDepartedConfiguration(String nondepartedConfig) {
		setAttribute(PARAMETER_NONDEPARTED_CONFIGURATION,nondepartedConfig);
	}
	/**
     * This method returns the config value
     * @return PARAMETER_CONFIGURATION - String
     */
	public String getNonDepartedConfiguration() {
		return getAttribute(PARAMETER_NONDEPARTED_CONFIGURATION);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param flightStatus - Collection<OneTimeVO>
     */
	public void setFlightStatus(Collection<OneTimeVO> flightStatus) {
		setAttribute(ONETIME_FLIGHTSTATUS,(ArrayList<OneTimeVO>)flightStatus);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_FLIGHTSTATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getFlightStatus() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_FLIGHTSTATUS);
	}
	
	/**
     * This method is used to set config value to the session
     * @param departedConfig
     */
	public void setDepartedConfiguration(String departedConfig) {
		setAttribute(PARAMETER_DEPARTED_CONFIGURATION,departedConfig);
	}
	/**
     * This method returns the config value
     * @return PARAMETER_CONFIGURATION - String
     */
	public String getDepartedConfiguration() {
		return getAttribute(PARAMETER_DEPARTED_CONFIGURATION);
	}
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
	 * This method is used to set ContainerVOs to the session
	 * @param containervos - Collection<ContainerVO>
	 */
	public void setContainerVOs(Collection<ContainerVO> containervos){
		setAttribute(CONTAINERVOS,(ArrayList<ContainerVO>)containervos);
	}

	/**
	 * This method returns the ContainerVOs
	 * @return CONTAINERVOS - Collection<ContainerVO>
	 */
	public Collection<ContainerVO> getContainerVOs(){
		return (Collection<ContainerVO>)getAttribute(CONTAINERVOS);
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

	public HashMap<String,Collection<String>> getPolPouMap(){
		return (HashMap<String, Collection<String>>) getAttribute(KEY_POL_POU);
	}
	

	public void setPolPouMap(HashMap<String,Collection<String>> polPouMap){
		setAttribute(KEY_POL_POU, (HashMap<String, Collection<String>>) polPouMap);
	}
	
	public void setReassignStatus(String status) {
		setAttribute(REASSIGN_STATUS,status);
	}

	public String getReassignStatus() {
		return getAttribute(REASSIGN_STATUS);
	}

}
