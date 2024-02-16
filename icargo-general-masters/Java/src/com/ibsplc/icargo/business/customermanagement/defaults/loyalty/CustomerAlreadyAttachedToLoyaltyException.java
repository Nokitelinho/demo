/*
 * CustomerAlreadyAttachedToLoyaltyException.java Created on may 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
/**
 * @author Jeen M
 *
 */

public class CustomerAlreadyAttachedToLoyaltyException extends BusinessException {

	/**
     * 
	 */
	public static final String CUSTOMER_ATTACHED_TOLOYALTY = 
		"customer.defaults.customerAttachedToLoyalty";
	/**
     * 
     *
	 */
	public CustomerAlreadyAttachedToLoyaltyException() {
		super();
	}

    /**
     * @param errorCode
     */
	public CustomerAlreadyAttachedToLoyaltyException( String  errorCode) {
		super(errorCode);
	}
    /**
     * @param errorCode
     * @param errorData
     */
	public CustomerAlreadyAttachedToLoyaltyException(String  errorCode, Object[] errorData) {
		super(errorCode, errorData);
	}

}




