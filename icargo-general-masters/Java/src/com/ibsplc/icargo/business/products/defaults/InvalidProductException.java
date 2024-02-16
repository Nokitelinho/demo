/**
 * InvalidProductException.java Created on Mar 16, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author a-4823
 *
 */
public class InvalidProductException extends BusinessException{
	/**
	 * Specifies whether any product name is invalid
	 */
	public static final String INVALID_PRODUCT = "products.defaults.invalidproductnames";

	public InvalidProductException() {
		super();		
	}

	public InvalidProductException(Object[] errorData) {
		super(INVALID_PRODUCT, errorData);
	}

}
