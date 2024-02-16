package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1936
 */
public class SharedProxyException extends BusinessException {
	/** 
	* The ErrorCode when the City is invalid
	*/
	public static final String INVALID_CITY = "shared.area.invalidcity";
	/** 
	* The ErrorCode when the Country is invalid
	*/
	public static final String INVALID_COUNTRY = "shared.area.invalidcountry";
	/** 
	* The ErrorCode when the airline  is invalid
	*/
	public static final String INVALID_AIRLINE = "shared.airline.invalidairline";
	/** 
	* The ErrorCode when the ULDType  is invalid
	*/
	public static final String INVALID_ULDTYPE = "shared.uld.invaliduldType";

	public SharedProxyException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param exceptionCause
	*/
	public SharedProxyException(String errorCode, Object[] exceptionCause) {
		super(errorCode, errorCode);
	}

	/** 
	* @param errorCode
	*/
	public SharedProxyException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param ex
	*/
	public SharedProxyException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}
}
