/*
 * EmbargoBusinessException.java Created on Jul 6, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information 
 * of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1358
 *
 */
public class EmbargoRulesBusinessException extends BusinessException {
    
	public EmbargoRulesBusinessException() {
		super();
		// To be reviewed Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public EmbargoRulesBusinessException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
		// To be reviewed Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 */
	public EmbargoRulesBusinessException(String errorCode) {
		super(errorCode);
		// To be reviewed Auto-generated constructor stub
	}
	
	public EmbargoRulesBusinessException(BusinessException exception) {
		super(exception);
		// To be reviewed Auto-generated constructor stub
	}	
}
