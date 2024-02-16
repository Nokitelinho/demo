/*
 * MailFlightSummarySessionImpl.java Created on Jan 25,2006
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
import java.util.Map;

import com.ibsplc.icargo.business.capacity.booking.vo.MailBookingVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInFlightSummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailFlightSummarySession;


/**
 * @author A-1862
 *
 */
public class MailFlightSummarySessionImpl extends AbstractScreenSession
        implements MailFlightSummarySession {

	private static final String SCREEN_ID = "mailtracking.defaults.mailflightsummary";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_MAILSUMMARYVO = "mailSummaryVO";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightValidationVO";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalFlightVO";
	private static final String KEY_BOOKINGVO = "bookingVos";
	private static final String KEY_DOCUMENTMAP = "documentMap";
	/**
	 * Constant for the session variable oneTimeVOs
	 */
	private static final String KEY_ONETIME_VO = "oneTimeVOs";
	
		
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
     * This method returns the MailInFlightSummaryVOs
     * @return KEY_MAILSUMMARYVO - Collection<MailInFlightSummaryVO>
     */
	public Collection<MailInFlightSummaryVO> getSummaryVOs() {
		return (Collection<MailInFlightSummaryVO>)getAttribute(KEY_MAILSUMMARYVO);
	}
    
	/**
     * This method is used to set MailInFlightSummaryVOs to the session
     * @param summaryvos - Collection<MailInFlightSummaryVO>
     */
	public void setSummaryVOs(Collection<MailInFlightSummaryVO> summaryvos) {
		setAttribute(KEY_MAILSUMMARYVO,(ArrayList<MailInFlightSummaryVO>)summaryvos);
	}
	
	/**
     * This method returns the MailBookingVOs
     * @return KEY_BOOKINGVO - Collection<MailBookingVO>
     */
	public Collection<MailBookingVO> getBookingVOs() {
		return (Collection<MailBookingVO>)getAttribute(KEY_BOOKINGVO);
	}
    
	/**
     * This method is used to set MailBookingVOs to the session
     * @param bookings - Collection<MailBookingVO>
     */
	public void setBookingVOs(Collection<MailBookingVO> bookings) {
		setAttribute(KEY_BOOKINGVO,(ArrayList<MailBookingVO>)bookings);
	}
	
	/**
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {
		return (HashMap<String, Collection<OneTimeVO>>)
									getAttribute(KEY_ONETIME_VO);
	}
	
	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(
						HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VO,(
						HashMap<String, Collection<OneTimeVO>>)oneTimeVOs);
	}
	
	
	 /** (non-Javadoc)
     * @return documentMap(Map<String, Collection<String>>)
     */
    public Map<String, Collection<String>> getDocumentMap() {
        return (Map<String, Collection<String>>)getAttribute(KEY_DOCUMENTMAP);
    }
  
    /** (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.operations.interfaces.AcceptanceSessionInterface
     * #setDocumentMap(Map<String, Collection<String>> documentMap)
     * @param documentMap
     */
    public void setDocumentMap(Map<String, Collection<String>> documentMap) {
    	setAttribute(KEY_DOCUMENTMAP, (HashMap<String, Collection<String>>)documentMap);
    }
	
	
}
