package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * This class is used to create the Exception required when the flight status is Closed..
 * @author a-1936
 */
public class FlightClosedException extends BusinessException {
	/** 
	* The Error code for Flight Closed
	*/
	public static final String FLIGHT_STATUS_CLOSED = "mailtracking.defaults.err.flightclosed";

	public FlightClosedException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param exceptionCause
	*/
	public FlightClosedException(String errorCode, Object[] exceptionCause) {
		super(errorCode, errorCode);
	}

	/** 
	* @param errorCode
	*/
	public FlightClosedException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param ex
	*/
	public FlightClosedException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}
}
