/*
 * MailBookingException.java Created on FEB 02, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-3227 RENO K ABRAHAM
 * 
 */
public class MailBookingException extends BusinessException {
	
	/**
     * @param errorCode
     * @param errorData 
     */
    public MailBookingException(String errorCode, Object[] errorData) {
        super(errorCode, errorData);
    }
    
    /**
    * @param errorCode
    */
   public MailBookingException(String errorCode) {
       super(errorCode);
   }
}
