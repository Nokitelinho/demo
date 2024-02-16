package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1936
 */
public class InvalidPartnerException extends BusinessException {
	/** 
	* The ErrorCode for Invalidpartner( Partner other than our Partner)
	*/
	public static final String INVALID_PARTNER = "mailtracking.defaults.invalidpartner";
	/** 
	* The ErrorCode for DuplicatePartner
	*/
	public static final String DUPLICATE_PARTNER = "mailtracking.defaults.duplicatepartner";

	/** 
	* The defaultConstructor
	*/
	public InvalidPartnerException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param errorData
	*/
	public InvalidPartnerException(String errorCode, Object[] errorData) {
		super(errorCode, errorCode);
	}

	/** 
	* @param ex
	*/
	public InvalidPartnerException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	*/
	public InvalidPartnerException(String errorCode) {
		super(errorCode, "Not Found");
	}
}
