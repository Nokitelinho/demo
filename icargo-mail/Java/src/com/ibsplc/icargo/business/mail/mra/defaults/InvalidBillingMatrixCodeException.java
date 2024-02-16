/*
 * InvalidBillingMatrixCodeException.java Created on Apr 16, 2007
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
public class InvalidBillingMatrixCodeException extends BusinessException {

	/**
	 * duplicate parameters
	 */
	public static final String INVALID_BILLING_MATRIX_CODE = "mailtracking.mra.defaults.invalidbillingmatrixcode";

	public InvalidBillingMatrixCodeException() {
		super(INVALID_BILLING_MATRIX_CODE);
	}

	public InvalidBillingMatrixCodeException(Object[] errorData) {
		super(INVALID_BILLING_MATRIX_CODE, errorData);
	}

	public InvalidBillingMatrixCodeException(String errorCode,
			Object[] errorData) {
		super(errorCode, errorData);
	}

}
