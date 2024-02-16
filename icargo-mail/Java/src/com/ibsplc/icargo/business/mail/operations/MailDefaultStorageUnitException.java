package com.ibsplc.icargo.business.mail.operations;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;


public class MailDefaultStorageUnitException extends BusinessException{

	    public MailDefaultStorageUnitException(String errorCode, Object[] errorData) {
	        super(errorCode, errorData);
	    }
	    
	    /**
	    * @param errorCode
	    */
	   public MailDefaultStorageUnitException(String errorCode) {
	       super(errorCode);
	   }
	
}
