/*
 * MailFlightSummarySession.java Created on Jan 25,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.capacity.booking.vo.MailBookingVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1862
 *
 */
public interface MailFlightSummarySession extends ScreenSession {

	    
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
     * This method returns the summaryVOs
     * @return summaryVOs - Collection<MailInFlightSummaryVO>
     */
	public Collection<MailInFlightSummaryVO> getSummaryVOs();
	
	/**
     * This method is used to set summaryVOs to the session
     * @param summaryVOs - Collection<MailInFlightSummaryVO>
     */
	public void setSummaryVOs(Collection<MailInFlightSummaryVO> summaryVOs);
	
	/**
     * This method returns the bookingVOs
     * @return bookingVOs - Collection<MailBookingVO>
     */
	public Collection<MailBookingVO> getBookingVOs();
	
	/**
     * This method is used to set bookingVOs to the session
     * @param bookingVOs - Collection<MailBookingVO>
     */
	public void setBookingVOs(Collection<MailBookingVO> bookingVOs);
	/**
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	
	/**
	 * @return Returns the documentMap.
	 */
	public Map<String, Collection<String>> getDocumentMap();
	/**
	 * @param documentMap The documentMap to set.
	 */
	public void setDocumentMap(Map<String, Collection<String>> documentMap);
	
		

}

