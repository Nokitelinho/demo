package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1739
 */
public class InvalidFlightSegmentException extends BusinessException {
	public static final String FLIGHT_SEG_INVALID = "mailtracking.defaults.err.invalidflightsegment";

	public InvalidFlightSegmentException(Object[] errData) {
		super(FLIGHT_SEG_INVALID, errData);
	}
}
