/*
 * DuplicateContractException.java Created on Apr 04, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * 
 * @author a-2518
 * 
 */
public class DuplicateContractException extends BusinessException {

	/**
	 * duplicate parameters
	 */
	public static final String DUPLICATE_MAILCONTRACT = "mailtracking.mra.defaults.duplicatemailcontract";

	public DuplicateContractException() {
		super(DUPLICATE_MAILCONTRACT);
	}

	public DuplicateContractException(Object[] errorData) {
		super(DUPLICATE_MAILCONTRACT, errorData);
	}

	public DuplicateContractException(String errorCode, Object[] errorData) {
		super(errorCode, errorData);
	}

}
