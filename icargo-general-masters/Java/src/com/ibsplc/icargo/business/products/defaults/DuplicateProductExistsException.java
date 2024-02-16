/*
 * BookingExistsException.java Created on Jul 6, 2005
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
public class DuplicateProductExistsException extends BusinessException {
    /**
     * Specifies whether any product with same name with same period exists
     */
    public static final String DUPLICATE_PRODUCT_EXISTS = "product.defaults.duplicateProductsExists";

	public DuplicateProductExistsException() {
		super(DUPLICATE_PRODUCT_EXISTS);
		// To be reviewed Auto-generated constructor stub
	}
	
	public DuplicateProductExistsException(Object[] errorData) {
		super(DUPLICATE_PRODUCT_EXISTS);
	}

}
