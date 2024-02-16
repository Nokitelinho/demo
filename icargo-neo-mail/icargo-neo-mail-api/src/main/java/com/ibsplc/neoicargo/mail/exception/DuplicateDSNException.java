package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-2553This class is used to create an exception to be thrown when checks for the ContainerAssignment are Done.
 */
public class DuplicateDSNException extends BusinessException {
	/** 
	* The ErrorCode dsn already in despatch or mailbags
	*/
	public static final String DSN_IN_MAILBAG_DESPATCH = "mailtracking.defaults.dsninmailbaganddespatch";
	public static final String DSN_IN_MAILBAG = "mailtracking.defaults.consignment.duplicatemailbag";
	public static final String DSN_IN_DESPATCH = "mailtracking.defaults.consignment.duplicatedespatch";

	/** 
	* The Constructor
	*/
	public DuplicateDSNException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param exceptionCause
	*/
	public DuplicateDSNException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	/** 
	* @param errorCode
	*/
	public DuplicateDSNException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param ex
	*/
	public DuplicateDSNException(BusinessException ex) {
		super(ex);
	}
}
