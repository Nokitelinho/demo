package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1739
 */
public class ReassignmentException extends BusinessException {
	/** 
	* The ErrorCode
	*/
	public static final String MAILBAG_REASSIGN_FROM_PABUILT = "mailtracking.defaults.err.mailbagreassignfrompabuiltuld";
	/** 
	* The ErrorCode
	*/
	public static final String DESPATCH_REASSIGN_FROM_PABUILT = "mailtracking.defaults.err.despatchreassignfrompabuiltuld";
	/** 
	* The ErrorCode
	*/
	public static final String MAILBAG_REASSIGN_NOT_AVAILABLE = "mailtracking.defaults.err.mailbagreassignnotavailable";

	/** 
	* @param errorCode
	*/
	public ReassignmentException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param errorCode
	* @param errorData
	*/
	public ReassignmentException(String errorCode, Object[] errorData) {
		super(errorCode, errorCode);
	}

	/** 
	*/
	public ReassignmentException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param exception
	*/
	public ReassignmentException(Throwable exception) {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param exception
	*/
	public ReassignmentException(String errorCode, Throwable exception) {
		super(errorCode, errorCode);
	}

	/** 
	* @param businessException
	*/
	public ReassignmentException(BusinessException businessException) {
		super("NOT_FOUND", "Not Found");
	}
}
