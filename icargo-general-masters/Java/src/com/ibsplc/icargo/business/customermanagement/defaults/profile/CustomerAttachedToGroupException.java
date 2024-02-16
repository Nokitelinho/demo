/*
 * CustomerAttachedToGroupException.java Created on APR 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.profile;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
/**
 * @author Jeen M
 *
 */

public class CustomerAttachedToGroupException extends BusinessException {

	/**
     * 
	 */
	public static final String CUSTOMER_ATTACHED_TOGROUP = 
		"customer.defaults.customerAttachedTogroup";
	/**
     * 
     *
	 */
	public CustomerAttachedToGroupException() {
		super();
	}

    /**
     * @param errorCode
     */
	public CustomerAttachedToGroupException( String  errorCode) {
		super(errorCode);
	}
    /**
     * @param errorCode
     * @param errorData
     */
	public CustomerAttachedToGroupException(String  errorCode, Object[] errorData) {
		super(errorCode, errorData);
	}

}




