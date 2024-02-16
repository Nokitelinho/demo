package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.OfficeOfExchangeException.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Aug 4, 2016	:	Draft
 */
public class OfficeOfExchangeException extends BusinessException {
	/** 
	* The ErrorCode For OfficeExchangeAlreadyexists
	*/
	public static final String OFFICEOFEXCHANGE_ALREADY_EXISTS = "mailtracking.defaults.officeofexchangealreadyexists";
	public static final String INVALID_POSTAL_ADMIN = "mailtracking.defaults.invalidpacode";
	public static final String INVALID_COUNTRYCODE = "mailtracking.defaults.invalidcountry";

	public OfficeOfExchangeException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param exceptionCause
	*/
	public OfficeOfExchangeException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	/** 
	* @param errorCode
	*/
	public OfficeOfExchangeException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param ex
	*/
	public OfficeOfExchangeException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}
}
