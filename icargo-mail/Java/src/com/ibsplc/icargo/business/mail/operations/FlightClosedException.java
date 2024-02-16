/*
 * FlightClosedException.java Created on JULY 10, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * This class is used to create the Exception required when the flight status is
 * Closed..
 * 
 * @author a-1936
 * 
 */
public class FlightClosedException extends BusinessException {
	/**
	 * The Error code for Flight Closed
	 */
	public static final String FLIGHT_STATUS_CLOSED = "mailtracking.defaults.err.flightclosed";

	public FlightClosedException() {
		super();
	}

	/**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public FlightClosedException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 */
	public FlightClosedException(String errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param ex
	 */
	public FlightClosedException(BusinessException ex) {
		super(ex);
		// TODO Auto-generated constructor stub
	}

}
