/*
 * ProductDefaultsBusinessException.java Created on Jul 4, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1358
 *
 */
public class ProductDefaultsBusinessException extends BusinessException {
	
	public static final String PRODUCT_NOT_EXISTS = "products.defaults.productnotexists";
	
	public static final String PRODUCT_ASSIGNED_TOANOTHERDOCTYPE = 
		"products.defaults.productassignedtoanotherdoctype";
	
	
	
    /**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public ProductDefaultsBusinessException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
		// To be reviewed Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 */
	public ProductDefaultsBusinessException(String errorCode) {
		super(errorCode);
		// To be reviewed Auto-generated constructor stub
	} 
	
	public ProductDefaultsBusinessException(BusinessException businessException) {
		super(businessException);
		// To be reviewed Auto-generated constructor stub
	} 
}
