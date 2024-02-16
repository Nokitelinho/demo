package com.ibsplc.neoicargo.mail.exception;

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
	public MailbagAlreadyAcceptedException(String errorCode,Object[] errorData) {
		super(errorCode,errorData);
	}
	public MailbagAlreadyAcceptedException(BusinessException ex) {
		super(ex);
	}
	public MailbagAlreadyAcceptedException(String errorCode) {
		super(errorCode, "Not Found");
	}
}
