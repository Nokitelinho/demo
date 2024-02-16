/*
 * SubProductNotExistingBusinessException.java Created on Nov 12, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1648
 * This exception is thrown when subproduct does not exist.
 *
 */
public class SubProductNotExistingBusinessException extends BusinessException {
	
	private static final String SUBPRODUCT_NOTEXISTING = "products.defaults.subproductnotexisting";
	/**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public SubProductNotExistingBusinessException() {
		super(SUBPRODUCT_NOTEXISTING);		
	}

	/**
	 * Use this constructor for handling collection of Error VO's.
	 * @param businessException
	 */
    public SubProductNotExistingBusinessException(BusinessException businessException) {
    	super(SUBPRODUCT_NOTEXISTING, businessException);
    }   	

}
