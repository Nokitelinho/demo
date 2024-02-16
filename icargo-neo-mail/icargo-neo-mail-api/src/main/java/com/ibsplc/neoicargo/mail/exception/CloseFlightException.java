package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * This class is used to create the  Exception  when a flight is opened wih mails
 * @author a-3251
 */
public class CloseFlightException extends BusinessException {
	/** 
	* The ErrorCode for CloseFlightException
	*/
	public static final String CLOSEFLIGHT_EXCEPTION = "mailtracking.defaults.mailsinopenflight";
	public static final String ROUTING_UNAVAILABLE = "mailtracking.defaults.err.routingunavailable";
	public static final String FLIGHT_ALREADY_CLOSED_EXCEPTION = "mailtracking.defaults.err.flightclosed";

	/** 
	* Constructor
	*/
	public CloseFlightException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param exceptionCause
	*/
	public CloseFlightException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	/** 
	* @param errorCode
	*/
	public CloseFlightException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param ex
	*/
	public CloseFlightException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}
}
