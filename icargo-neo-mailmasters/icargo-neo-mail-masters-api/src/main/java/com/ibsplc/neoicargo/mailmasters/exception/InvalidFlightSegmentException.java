package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1739
 */
public class InvalidFlightSegmentException extends BusinessException {
	public static final String FLIGHT_SEG_INVALID = "mailtracking.defaults.err.invalidflightsegment";

	public InvalidFlightSegmentException(Object[] errData) {
		super("NOT_FOUND", "Not Found");
	}
}
