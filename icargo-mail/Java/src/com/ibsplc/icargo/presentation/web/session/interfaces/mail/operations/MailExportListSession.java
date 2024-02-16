/*
 * MailExportListSession.java Created on Mar 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-3227  RENO K ABRAHAM
 *
 */
public interface MailExportListSession extends ScreenSession {

	/**
	 * The setter method for mailAcceptanceVO
	 * @param mailAcceptanceVO
	 */
	public void setMailAcceptanceVO(MailAcceptanceVO mailAcceptanceVO);
    /**
     * The getter method for MailAcceptanceVO
     * @return MailAcceptanceVO
     */
    public MailAcceptanceVO getMailAcceptanceVO();
    
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
     * This method is used to set ContainerDetailsVOs to the session
     * @param containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs);

	/**
     * This method returns the ContainerDetails vos
     * @return containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public Collection<ContainerDetailsVO> getContainerDetailsVOs();
	/**
	 * The setter method for containerDetailsVO
	 * @param containerDetailsVO
	 */
	public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO);
    /**
     * The getter method for containerDetailsVO
     * @return containerDetailsVO
     */
    public ContainerDetailsVO getContainerDetailsVO();
	/**
	 * The setter method for DSN VOs
	 * @param dsnVOs - Collection<DSNVO>
	 */
	public void setDSNVOs(Collection<DSNVO> dsnVOs);
    /**
     * The getter method for DSN VOs
     * @return DSNVOs - Collection<DSNVO>
     */
    public Collection<DSNVO> getDSNVOs();
	/**
	 * The setter method for selectedDSN VOs
	 * @param selectedDSNVOs - Collection<DSNVO>
	 */
	public void setSelectedDSNVOs(Collection<DSNVO> selectedDSNVOs);
    /**
     * The getter method for selectedDSN VOs
     * @return selectedDSNVOs - Collection<DSNVO>
     */
    public Collection<DSNVO> getSelectedDSNVOs();
	/**
	 * The setter method for mailbagEnquiryFilterVO
	 * @param mailbagEnquiryFilterVO - MailbagEnquiryFilterVO
	 */
	public void setMailbagEnquiryFilterVO(MailbagEnquiryFilterVO mailbagEnquiryFilterVO);
    /**
     * The getter method for mailbagEnquiryFilterVO
     * @return mailbagEnquiryFilterVO - MailbagEnquiryFilterVO
     */
    public MailbagEnquiryFilterVO getMailbagEnquiryFilterVO();
	/**
	 * The setter method for despatchDetailsVOs
	 * @param despatchDetailsVOs - Collection<DespatchDetailsVO>
	 */
	public void setDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs);
    /**
     * The getter method for despatchDetailsVOs
     * @return despatchDetailsVOs - Collection<DespatchDetailsVO>
     */
    public Collection<DespatchDetailsVO> getDespatchDetailsVOs();
	
}
