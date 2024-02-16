/*
 * ReassignMailSession.java Created on Jun 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1556
 *
 */
public interface ReassignMailSession extends ScreenSession {

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
	public void setMailbagVOs(Collection<MailbagVO> mailbagVOs);

	/**
     * This method returns the MailbagVOs vos
     * @return MailbagVOs - Collection<MailbagVO>
     */
	public Collection<MailbagVO> getMailbagVOs();
	
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
		
	public void setDestinationFilterVO(DestinationFilterVO destinationFilterVO);
	
	public DestinationFilterVO getDestinationFilterVO();

	public void setReassigStatus(String status);
	public String getReassignStatus();
}
