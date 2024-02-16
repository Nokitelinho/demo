/*
 * MailExportListSessionImpl.java Created on Mar 28, 2008
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
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;

/**
 * @author A-3227  RENO K ABRAHAM
 *
 */
public class MailExportListSessionImpl extends AbstractScreenSession
        implements MailExportListSession {

	private static final String SCREEN_ID = "mailtracking.defaults.mailexportlist";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_MAILACCEPTANCEVO = "mailAcceptanceVO";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightValidationVO";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalFlightVO";	
	private static final String KEY_DESPATCHDETAILSVOS = "selectedDespatchDetailsVOs";	
	private static final String KEY_CONTAINERDETAILSVOS = "containerDetailsVOs";
	private static final String KEY_CONTAINERDETAILSVO = "containerDetailsVO";
	private static final String KEY_DSNVOS = "DSNVOs";
	private static final String KEY_SELECTEDDSNVOS = "selectedDSNVOs";
	private static final String KEY_MAILBAGENQUIRYFILTER = "mailbagEnquiryFilterVO";

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
	 * The setter method for MailAcceptanceVO
	 * @param mailAcceptanceVO
	 */
    public void setMailAcceptanceVO(MailAcceptanceVO mailAcceptanceVO) {
    	setAttribute(KEY_MAILACCEPTANCEVO,mailAcceptanceVO);
    }
    /**
     * The getter method for mailAcceptanceVO
     * @return mailAcceptanceVO
     */
    public MailAcceptanceVO getMailAcceptanceVO() {
    	return getAttribute(KEY_MAILACCEPTANCEVO);
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
	 * The setter method for containerDetailsVO
	 * @param containerDetailsVO
	 */
    public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO) {
    	setAttribute(KEY_CONTAINERDETAILSVO,containerDetailsVO);
    }
    /**
     * The getter method for containerDetailsVO
     * @return containerDetailsVO
     */
    public ContainerDetailsVO getContainerDetailsVO() {
    	return getAttribute(KEY_CONTAINERDETAILSVO);
    }
    
    /**
     * This method is used to set ContainerDetailsVOs values to the session
     * @param containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs) {
		setAttribute(KEY_CONTAINERDETAILSVOS,(ArrayList<ContainerDetailsVO>)containerDetailsVOs);
	}

	/**
     * This method returns the ContainerDetailsVOs
     * @return KEY_CONTAINERDETAILSVOS - Collection<ContainerDetailsVO>
     */
	public Collection<ContainerDetailsVO> getContainerDetailsVOs() {
		return (Collection<ContainerDetailsVO>)getAttribute(KEY_CONTAINERDETAILSVOS);
	}
    
    /**
     * This method is used to set dsnVOs values to the session
     * @param dsnVOs - Collection<DSNVO>
     */
	public void setDSNVOs(Collection<DSNVO> dsnVOs) {
		setAttribute(KEY_DSNVOS,(ArrayList<DSNVO>)dsnVOs);
	}

	/**
     * This method returns the dsnVOs
     * @return KEY_DSNVOS - Collection<DSNVO>
     */
	public Collection<DSNVO> getDSNVOs() {
		return (Collection<DSNVO>)getAttribute(KEY_DSNVOS);
	}
    
    /**
     * This method is used to set selectedDSNVOs values to the session
     * @param selectedDSNVOs - Collection<DSNVO>
     */
	public void setSelectedDSNVOs(Collection<DSNVO> selectedDSNVOs) {
		setAttribute(KEY_SELECTEDDSNVOS,(ArrayList<DSNVO>)selectedDSNVOs);
	}

	/**
     * This method returns the selectedDSNVOs
     * @return KEY_SELECTEDDSNVOS - Collection<DSNVO>
     */
	public Collection<DSNVO> getSelectedDSNVOs() {
		return (Collection<DSNVO>)getAttribute(KEY_SELECTEDDSNVOS);
	}
    
    /**
     * This method is used to set MailbagEnquiryFilterVO values to the session
     * @param mailbagEnquiryFilterVO - MailbagEnquiryFilterVO
     */
	public void setMailbagEnquiryFilterVO(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) {
		setAttribute(KEY_MAILBAGENQUIRYFILTER,mailbagEnquiryFilterVO);
	}

	/**
     * This method returns the mailbagEnquiryFilterVO
     * @return KEY_SELECTEDDSNVOS - MailbagEnquiryFilterVO
     */
	public MailbagEnquiryFilterVO getMailbagEnquiryFilterVO() {
		return getAttribute(KEY_MAILBAGENQUIRYFILTER);
	}
    
    /**
     * This method is used to set MailbagEnquiryFilterVO values to the session
     * @param mailbagEnquiryFilterVO - MailbagEnquiryFilterVO
     */
	public void setDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs) {
		setAttribute(KEY_DESPATCHDETAILSVOS,(ArrayList<DespatchDetailsVO>) despatchDetailsVOs);
	}

	/**
     * This method returns the mailbagEnquiryFilterVO
     * @return KEY_SELECTEDDSNVOS - MailbagEnquiryFilterVO
     */
	public Collection<DespatchDetailsVO> getDespatchDetailsVOs() {
		return (Collection<DespatchDetailsVO>) getAttribute(KEY_DESPATCHDETAILSVOS);
	}


}
