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
public class RateNotDefinedException extends BusinessException {
    
    public static final String RATE_NOT_DEFINED = "product.defaults.rateNotDefined";

	public RateNotDefinedException() {
		super(RATE_NOT_DEFINED);
		// To be reviewed Auto-generated constructor stub
	}
	
	public RateNotDefinedException(Object[] errorData) {
		super(RATE_NOT_DEFINED);
	}

}
