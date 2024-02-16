package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1739
 */
public class CapacityBookingProxyException extends BusinessException {
	public CapacityBookingProxyException(String message, BusinessException ex) {
		super(message, message);
	}

	public CapacityBookingProxyException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}
}
