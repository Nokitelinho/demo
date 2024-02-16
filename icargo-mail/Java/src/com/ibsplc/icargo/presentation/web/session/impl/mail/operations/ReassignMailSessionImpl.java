/*
 * ReassignMailSessionImpl.java Created on Jun 12, 2006
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
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;

/**
 * @author A-1876
 *
 */
public class ReassignMailSessionImpl extends AbstractScreenSession
        implements ReassignMailSession {

	private static final String SCREEN_ID = "mailtracking.defaults.reassignmail";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_FLIGHTVALIDATIONVO = "searchContainerFilterVO";
	private static final String KEY_CONTAINERVOS = "containerVOs";
	private static final String KEY_MAILBAGVOS = "mailbagVOs";
	private static final String KEY_DESPATCHDETAILSVOS = "despatchdetailsVOs";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalFlightVO";
	private static final String KEY_DESTNFILTER = "destnFilterVO";
	private static final String KEY_REASSIGN = "reassignStatus";
	
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
	 * This method is used to set containerVOs to the session
	 * @param containerVOs - Collection<ContainerVO>
	 */
	public void setContainerVOs(Collection<ContainerVO> containerVOs){
		setAttribute(KEY_CONTAINERVOS,(ArrayList<ContainerVO>)containerVOs);
	}

	/**
	 * This method returns the containerVOs
	 * @return containerVOs - Collection<ContainerVO>
	 */
	public Collection<ContainerVO> getContainerVOs(){
		return (Collection<ContainerVO>)getAttribute(KEY_CONTAINERVOS);
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
     * This method is used to set DespatchDetailsVOs values to the session
     * @param despatchDetailsVOs - Collection<DespatchDetailsVO>
     */
	public void setDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs) {
		setAttribute(KEY_DESPATCHDETAILSVOS,(ArrayList<DespatchDetailsVO>)despatchDetailsVOs);
	}

	/**
     * This method returns the DespatchDetailsVOs
     * @return KEY_DESPATCHDETAILSVOS - Collection<DespatchDetailsVO>
     */
	public Collection<DespatchDetailsVO> getDespatchDetailsVOs() {
		return (Collection<DespatchDetailsVO>)getAttribute(KEY_DESPATCHDETAILSVOS);
	}
	
	 /**
     * This method is used to set MailbagVOs values to the session
     * @param mailbagVOs - Collection<MailbagVO>
     */
	public void setMailbagVOs(Collection<MailbagVO> mailbagVOs) {
		setAttribute(KEY_MAILBAGVOS,(ArrayList<MailbagVO>)mailbagVOs);
	}

	/**
     * This method returns the MailbagVOs
     * @return KEY_MAILBAGVOS - Collection<MailbagVO>
     */
	public Collection<MailbagVO> getMailbagVOs() {
		return (Collection<MailbagVO>)getAttribute(KEY_MAILBAGVOS);
	}

	public DestinationFilterVO getDestinationFilterVO() {
		return getAttribute(KEY_DESTNFILTER);
	}

	public void setDestinationFilterVO(DestinationFilterVO destinationFilterVO) {
		setAttribute(KEY_DESTNFILTER,destinationFilterVO);
	}
	@Override
	public void setReassigStatus(String status) {
		setAttribute(KEY_REASSIGN, status);
	}
	@Override
	public String getReassignStatus() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_REASSIGN);
	}
	
    
}
