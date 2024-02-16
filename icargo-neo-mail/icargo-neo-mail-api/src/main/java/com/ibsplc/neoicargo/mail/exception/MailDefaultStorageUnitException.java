package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

public class MailDefaultStorageUnitException extends BusinessException {
	public MailDefaultStorageUnitException(String errorCode, Object[] errorData) {
		super(errorCode, errorCode);
	}

	/** 
	* @param errorCode
	*/
	public MailDefaultStorageUnitException(String errorCode) {
		super(errorCode, "Not Found");
	}
}
