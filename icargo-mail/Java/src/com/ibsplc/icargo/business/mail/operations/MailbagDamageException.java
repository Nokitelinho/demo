/*
 * MailbagDamageException.java Created on Jul 31, 2006
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
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			Jul 31, 2006				   A-1739			First Draft
 */
public class MailbagDamageException extends BusinessException {

    /**
     * @param errorCode
     * @param errorData 
     */
    public MailbagDamageException(String errorCode, String[] errorData) {
        super(errorCode, errorData);
    }

}
