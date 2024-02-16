/*
 * DuplicateParameterException.java Created on Jan 7, 2009
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
 * @author A-3434
 *
 */
public class DuplicateParameterException extends BusinessException {

	/**
	 * Duplicate billing line exists
	 */
	public static final String DUPLICATE_PARAMETER_EXIST = "mailtracking.mra.defaults.duplicateparameter";

	
          /**
	 * @param errorCode
	 */
	public DuplicateParameterException(String errorCode) {
		super(errorCode);

	}

	/**
	 * @param errorCode
	 * @param errorData
	 */
	public DuplicateParameterException(String errorCode, Object[] errorData) {
		super(errorCode, errorData);

	}

	/**
	 *
	 */
	public DuplicateParameterException() {
		super();

	}

	/**
	 * @param exception
	 */
	public DuplicateParameterException(Throwable exception) {
		super(exception);

	}

	/**
	 * @param errorCode
	 * @param exception
	 */
	public DuplicateParameterException(String errorCode, Throwable exception) {
		super(errorCode, exception);

	}

	/**
	 * @param businessException
	 */
	public DuplicateParameterException(BusinessException businessException) {
		super(businessException);

	}

}
