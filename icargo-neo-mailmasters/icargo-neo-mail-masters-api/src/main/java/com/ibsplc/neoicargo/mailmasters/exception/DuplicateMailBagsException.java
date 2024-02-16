package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * This class is used to create the  Exception  when there exists an DuplicateMailBag
 * @author a-1936
 */
public class DuplicateMailBagsException extends BusinessException {
	/** 
	* The ErrorCode for DuplicateMailBags
	*/
	public static final String DUPLICATEMAILBAGS_EXCEPTION = "mailtracking.defaults.acceptance.err.duplicatemailbags";
	public static final String INVALIDACCEPATNCE_EXCEPTION = "mailtracking.defaults.acceptance.err.inavlidacceptance";
	public static final String MAILBAG_ALREADY_ARRIVAL_EXCEPTION = "mailtracking.defaults.arrival.err.alreadyarrivedmailbag";
	public static final String MAILBAG_ALREADY_ARRIVAL_INSAMEFLIGHT_DIFF_CNTAINER_EXCEPTION = "mailtracking.defaults.arrival.err.alreadyarrivedmailbaginsameflightdiffcontainer";
	public static final String MAILBAG_ALREADY_RETURNED_EXCEPTION = "mailtracking.defaults.err.acceptance.returnedmailbag";

	/** 
	* Constructor
	*/
	public DuplicateMailBagsException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param exceptionCause
	*/
	public DuplicateMailBagsException(String errorCode, Object[] exceptionCause) {
		super(errorCode, errorCode);
	}

	/** 
	* @param errorCode
	*/
	public DuplicateMailBagsException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param ex
	*/
	public DuplicateMailBagsException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}
}
