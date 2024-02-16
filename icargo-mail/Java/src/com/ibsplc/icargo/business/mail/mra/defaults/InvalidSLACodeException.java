/*
 * InvalidSLACodeException.java Created on Apr 16, 2007
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
public class InvalidSLACodeException extends BusinessException {

	/**
	 * duplicate parameters
	 */
	public static final String INVALID_SLA_CODE = "mailtracking.mra.defaults.invalidslacode";

	public InvalidSLACodeException() {
		super(INVALID_SLA_CODE);
	}

	public InvalidSLACodeException(Object[] errorData) {
		super(INVALID_SLA_CODE, errorData);
	}

	public InvalidSLACodeException(String errorCode, Object[] errorData) {
		super(errorCode, errorData);
	}

}
