/*
 * MailbagAlreadyAcceptedException.java Created on July 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author a-1883
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			Jul 19, 2006				   A-1883			First Draft
 */
public class MailbagAlreadyAcceptedException extends BusinessException {
    /**
     * Mailbag Already Accepted Exception
     */
    public static final String MAILBAG_ALREADY_ASSIGNED  = 
        "mailtracking.defaults.err.mailbagalreadyassigned";
    /**
     * Mail(DSN)Already Accepted Exception
     */
    public static final String MAIL_ALREADY_ACCEPTED  = 
        "mailtracking.defaults.err.mailalreadyaccepted";
    /**
     * Constructor
     */
    public MailbagAlreadyAcceptedException() {
        super(MAILBAG_ALREADY_ASSIGNED);
    }
    /**
     * Constructor
     * @param errorData
     */
    public MailbagAlreadyAcceptedException(Object[] errorData) {
        super(MAILBAG_ALREADY_ASSIGNED, errorData);
    }
 }
