package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * TODO Add the purpose of this class
 * @author A-1739
 */
public class FlightDepartedException extends BusinessException {
	/** 
	* The Flight Status Departed
	*/
	public static final String FLIGHT_STATUS_DEPARTED = "mailtracking.defaults.warn.flightdeparted";

	/** 
	* @param exceptionCause
	*/
	public FlightDepartedException(Object[] exceptionCause) {
		super("NOT_FOUND", "Not Found");
	}
}
