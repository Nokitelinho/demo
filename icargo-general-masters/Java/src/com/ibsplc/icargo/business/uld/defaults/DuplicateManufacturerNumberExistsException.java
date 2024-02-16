/*
 *	DuplicateManufacturerNumberExistsException.java Created on Dec 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1496
 *
 */
public class DuplicateManufacturerNumberExistsException 
extends BusinessException {

	/**
	 * 
	 */
public static final String DUPLICATE_EXISTS="uld.defaults.duplicateexists";	

/**
 * 
 * @param errorCode
 * @param exceptionCause
 */
public DuplicateManufacturerNumberExistsException(String errorCode, 
		Object[] exceptionCause) {
    super(errorCode, exceptionCause);
    // To be reviewed Auto-generated constructor stub
}
}
