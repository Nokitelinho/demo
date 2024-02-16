package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-8353This exception is used to return  to android parent method to save data  even if a error is thrown
 */
public class ForceAcceptanceException extends BusinessException {
	/** 
	*/
	public ForceAcceptanceException() {
		super("NOT_FOUND", "Not Found");
	}

	public ForceAcceptanceException(String errorCode, String errorDescription) {
		super(errorCode, errorCode);
	}
}
