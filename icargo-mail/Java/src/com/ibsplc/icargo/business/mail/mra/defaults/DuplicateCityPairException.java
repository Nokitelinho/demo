/*
 * DuplicateCityPairException.java Created on Mar 21, 2007
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
 * @author a-2518
 * 
 */
public class DuplicateCityPairException extends BusinessException {

	/**
	 * duplicate city pair
	 */
	public static final String DUPLICATE_CITY_PAIR = "mailtracking.mra.defaults.duplicatecitypair";

	public DuplicateCityPairException() {
		super();
	}

	public DuplicateCityPairException(Object[] errorData) {
		super(DUPLICATE_CITY_PAIR, errorData);
	}

}
