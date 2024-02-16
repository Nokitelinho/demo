/*
 * ReassignMailbagSession.java 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3251
 *
 */
public interface ReassignMailbagSession extends ScreenSession {

	/**
	 * Method for getting FlightValidationVO from session
	 * @return flightValidationVO
	 */
	public FlightValidationVO getFlightValidationVO();
	/**
	 * Method for setting FlightValidationVO to session
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO);
	
	/**
     * This method is used to set ContainerVOs to the session
     * @param containerVOs - Collection<ContainerVO>
     */
	public void setContainerVOs(Collection<ContainerVO> containerVOs);

	/**
     * This method returns the ContainerDetails vos
     * @return containerVOs - Collection<ContainerVO>
     */
	public Collection<ContainerVO> getContainerVOs();
	/**
     * This method is used to set MailbagVOs to the session
     * @param mailbagVOs - Collection<MailbagVO>
     */
	public void setMailbagVOs(Page<MailbagVO> mailbagVOs);

	/**
     * This method returns the MailbagVOs vos
     * @return MailbagVOs - Page<MailbagVO>
     */
	public Page<MailbagVO> getMailbagVOs();
	
	/**
     * This method is used to set DespatchDetailsVOs to the session
     * @param despatchDetailsVOs - Collection<DespatchDetailsVO>
     */
	public void setDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs);

	/**
     * This method returns the DespatchDetailsVOs vos
     * @return DespatchDetailsVOs - Collection<DespatchDetailsVO>
     */
	public Collection<DespatchDetailsVO> getDespatchDetailsVOs();
	/**
     * This method returns the DespatchDetailsVOs vos 
     */	
	public void setDestinationFilterVO(DestinationFilterVO destinationFilterVO);
	/**
     * This method returns the DespatchDetailsVOs vos
     * @return DespatchDetailsVO - Collection<DespatchDetailsVO>
     */
	public DestinationFilterVO getDestinationFilterVO();
	/**
     * This method is used to set onetime values to the session
     * @param currentStatus - Collection<OneTimeVO>
     */
	public void setCurrentStatus(Collection<OneTimeVO> currentStatus);

	/**
     * This method returns the onetime vos
     * @return ONETIME_CURRENTSTATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getCurrentStatus();
	
}


