package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.MailSubClassException.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Aug 4, 2016	:	Draft
 */
public class MailSubClassException extends BusinessException {
	/** 
	* The ErrorCode for MailSubClassAlreadyExists
	*/
	public static final String MAILSUBCLASS_ALREADY_EXISTS = "mailtracking.defaults.mailsubclassalreadyexists";

	public MailSubClassException() {
		super(MAILSUBCLASS_ALREADY_EXISTS, MAILSUBCLASS_ALREADY_EXISTS);
	}

	/** 
	* @param errorCode
	* @param exceptionCause
	*/
	public MailSubClassException(String errorCode, Object[] exceptionCause) {
		super(errorCode, errorCode);
	}

	/** 
	* @param errorCode
	*/
	public MailSubClassException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param ex
	*/
	public MailSubClassException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}
}
