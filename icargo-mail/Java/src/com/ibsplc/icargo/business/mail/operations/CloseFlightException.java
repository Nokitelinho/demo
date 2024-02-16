/*
 * CloseFlightException.java Created on Jul 21, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * Author(s)			: A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 *  This class is used to create the  Exception 
 *  when a flight is opened wih mails
 * @author a-3251
 *
 */
public class CloseFlightException extends BusinessException{

	/**
	 * The ErrorCode for CloseFlightException
	 */
	public  static final String CLOSEFLIGHT_EXCEPTION = 
		"mailtracking.defaults.mailsinopenflight";
	public static final String ROUTING_UNAVAILABLE = "mailtracking.defaults.err.routingunavailable";
	public  static final String FLIGHT_ALREADY_CLOSED_EXCEPTION =
		"mailtracking.defaults.err.flightclosed";
	/**
	 * Constructor
	 *
	 */
	public CloseFlightException(){
		super();
	}
    /**
     * @param errorCode
     * @param exceptionCause
     */
    public CloseFlightException(String errorCode, 
    		                 Object[] exceptionCause) {
        super(errorCode, exceptionCause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param errorCode
     */
    public CloseFlightException(String errorCode) {
        super(errorCode);
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param ex
     */
    public CloseFlightException(BusinessException ex) {
        super(ex);
        // TODO Auto -generated constructor stub
        
        
       
    }
	
}
