package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
public class MailBookingException extends BusinessException {
	/** 
	* @param errorCode
	* @param errorData 
	*/
	public MailBookingException(String errorCode, Object[] errorData) {
		super(errorCode, errorCode);
	}

	/** 
	* @param errorCode
	*/
	public MailBookingException(String errorCode) {
		super(errorCode, "Not Found");
	}
}
