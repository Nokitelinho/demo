package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1883	Revision History ------------------------------------------------------------------------- Revision 		Date 					Author 		Description -------------------------------------------------------------------------  0.1     		   Mar 8, 2007			  	 A-1883		Created
 */
public class ULDDefaultsProxyException extends BusinessException {
	/** 
	* Constructor to hold the error causes also. This cause consist of the desctiption text displayed in the front end.
	* @param errorCode
	* @param exceptionCause
	*/
	public ULDDefaultsProxyException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	/** 
	* @param errorCode
	*/
	public ULDDefaultsProxyException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* This is the default constructor which can be used while storing multiple messages in from the same messages. e.g. storing Collection<ErrorVO>
	* @param errorCode
	*/
	public ULDDefaultsProxyException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param exception
	*/
	public ULDDefaultsProxyException(BusinessException exception) {
		super("NOT_FOUND", "Not Found");
	}
}
