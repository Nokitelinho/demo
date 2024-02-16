package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1936The ReturnNotPossibleException
 */
public class ReturnNotPossibleException extends BusinessException {
	/** 
	* The ErrorCode fOr InvalidMailBags For Return
	*/
	public static final String INVALID_MAILBAGS_FORRETURN = "mailtracking.defaults.invalidmailbagsforreturn";
	/** 
	* The ErrorCode For InvalidDespatches For Return 
	*/
	public static final String INVALID_DESPATCHES_FORRETURN = "mailtracking.defaults.invaliddespatchesforreturn";

	/** 
	* The defaultConstructor
	*/
	public ReturnNotPossibleException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param errorData
	*/
	public ReturnNotPossibleException(String errorCode, Object[] errorData) {
		super(errorCode, errorCode);
	}

	/** 
	* @param ex
	*/
	public ReturnNotPossibleException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	*/
	public ReturnNotPossibleException(String errorCode) {
		super(errorCode, "Not Found");
	}
}
