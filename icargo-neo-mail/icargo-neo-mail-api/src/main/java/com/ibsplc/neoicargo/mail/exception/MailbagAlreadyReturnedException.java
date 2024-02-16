package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * TODO Add the purpose of this class
 * @author A-1739
 */
public class MailbagAlreadyReturnedException extends BusinessException {
	/** 
	* The ErrorCode for mailBag AlraedyReturned
	*/
	public static final String MAILBAG_ALREADY_RETURNED = "mailtracking.defaults.err.returnedmailbag";

	/** 
	* @param errorData
	*/
	public MailbagAlreadyReturnedException(Object[] errorData) {
		super("NOT_FOUND", "Not Found");
	}

	public MailbagAlreadyReturnedException(String errorCode) {
		super(errorCode, "Not Found");
	}
}
