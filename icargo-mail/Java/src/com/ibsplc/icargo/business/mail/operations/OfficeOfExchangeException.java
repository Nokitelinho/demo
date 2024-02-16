/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.OfficeOfExchangeException.java
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.OfficeOfExchangeException.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Aug 4, 2016	:	Draft
 */
public class OfficeOfExchangeException extends BusinessException {
	 /**
	  * The ErrorCode For OfficeExchangeAlreadyexists
	  */
	public  static final String OFFICEOFEXCHANGE_ALREADY_EXISTS =
       "mailtracking.defaults.officeofexchangealreadyexists";
	
	//for invalid postal admin
	public static final String INVALID_POSTAL_ADMIN = 
		"mailtracking.defaults.invalidpacode";
	
	//for invalid country code
	public static final String INVALID_COUNTRYCODE = 
		"mailtracking.defaults.invalidcountry";
	
	public OfficeOfExchangeException(){
		super();
	}
    /**
     * @param errorCode
     * @param exceptionCause
     */
    public OfficeOfExchangeException(String errorCode, 
    		                 Object[] exceptionCause) {
        super(errorCode, exceptionCause);
    }

    /**
     * @param errorCode
     */
    public OfficeOfExchangeException(String errorCode) {
        super(errorCode);
    }
    /**
     * 
     * @param ex
     */
    public OfficeOfExchangeException(BusinessException ex) {
        super(ex);
    }

}
