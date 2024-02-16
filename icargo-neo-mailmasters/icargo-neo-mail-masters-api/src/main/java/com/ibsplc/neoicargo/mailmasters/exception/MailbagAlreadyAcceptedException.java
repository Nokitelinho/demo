package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author a-1883
 */
public class MailbagAlreadyAcceptedException extends BusinessException {
	/** 
	* Mailbag Already Accepted Exception
	*/
	public static final String MAILBAG_ALREADY_ASSIGNED = "mailtracking.defaults.err.mailbagalreadyassigned";
	/** 
	* Mail(DSN)Already Accepted Exception
	*/
	public static final String MAIL_ALREADY_ACCEPTED = "mailtracking.defaults.err.mailalreadyaccepted";

	/** 
	* Constructor
	*/
	public MailbagAlreadyAcceptedException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* Constructor
	* @param errorData
	*/
	public MailbagAlreadyAcceptedException(Object[] errorData) {
		super("NOT_FOUND", "Not Found");
	}
}
