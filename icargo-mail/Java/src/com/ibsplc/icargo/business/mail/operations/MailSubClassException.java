/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailSubClassException.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Aug 4, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailSubClassException.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Aug 4, 2016	:	Draft
 */
public class MailSubClassException extends BusinessException{
	/**
	 * The ErrorCode for MailSubClassAlreadyExists
	 */
	public  static final String MAILSUBCLASS_ALREADY_EXISTS =
        "mailtracking.defaults.mailsubclassalreadyexists";
	
	public MailSubClassException(){
		super();
	}
	   /**
     * @param errorCode
     * @param exceptionCause
     */
    public MailSubClassException(String errorCode, 
    		                 Object[] exceptionCause) {
        super(errorCode, exceptionCause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param errorCode
     */
    public MailSubClassException(String errorCode) {
        super(errorCode);
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param ex
     */
    public MailSubClassException(BusinessException ex) {
        super(ex);
        // TODO Auto-generated constructor stub
    }

}
