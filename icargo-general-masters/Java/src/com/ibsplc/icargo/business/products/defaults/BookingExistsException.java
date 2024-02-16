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
public class BookingExistsException extends BusinessException {
    /**
     * Specifies whether any booking exists for a product
     */
    public static final String BOOKING_EXISTS = "product.defaults.bookingExists";

	public BookingExistsException() {
		super(BOOKING_EXISTS);
	}
	
	public BookingExistsException(Object[] errorData) {
		super(BOOKING_EXISTS);
	}
	
}
