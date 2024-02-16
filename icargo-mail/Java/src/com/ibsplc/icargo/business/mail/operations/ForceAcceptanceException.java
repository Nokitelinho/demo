package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
/**
 * 
 * @author A-8353
 *This exception is used to return 
 *to android parent method to save data 
 *even if a error is thrown
 */

public class ForceAcceptanceException extends BusinessException {
	
	/**
	 * 
	 */
	public ForceAcceptanceException(){
		super();
		
	}

public ForceAcceptanceException(String errorCode,  String errorDescription) {

	super(errorCode, new Object[0] ,errorDescription);
	// To be reviewed Auto-generated constructor stub
}

}
