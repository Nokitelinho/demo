package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1936The InvalidMailTagFormatException used when the MailTag is Invalid
 */
public class InvalidMailTagFormatException extends BusinessException {
	/** 
	* The ErrorCode for InvalidMailFormat
	*/
	public static final String INVALID_MAILFORMAT = "mailtracking.defaults.invalidmailformat";

	/** 
	* The defaultConstructor
	*/
	public InvalidMailTagFormatException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param errorData
	*/
	public InvalidMailTagFormatException(String errorCode, Object[] errorData) {
		super(errorCode, errorData);
	}

	/** 
	* @param ex
	*/
	public InvalidMailTagFormatException(BusinessException ex) {
		super(ex);
	}

	/** 
	* @param errorCode
	*/
	public InvalidMailTagFormatException(String errorCode) {
		super(errorCode, "Not Found");
	}
}
