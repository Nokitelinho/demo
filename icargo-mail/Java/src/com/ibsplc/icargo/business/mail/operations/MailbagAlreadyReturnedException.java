/*
 * MailbagAlreadyReturnedException.java Created on Jul 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * TODO Add the purpose of this class
 *
 * @author A-1739
 *
 */
/*
 *  Revision History
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			Jul 18, 2006				   A-1739			First Draft
 */
public class MailbagAlreadyReturnedException extends BusinessException {
    /**
     * The ErrorCode for mailBag AlraedyReturned
     */
    public static final String MAILBAG_ALREADY_RETURNED = 
        "mailtracking.defaults.err.returnedmailbag";
    /**
     * 
     * @param errorData
     */
    public MailbagAlreadyReturnedException(Object[] errorData) {
        super(MAILBAG_ALREADY_RETURNED, errorData);
    }
    
    public MailbagAlreadyReturnedException(String errorCode) {
        super(errorCode);
    }
}
