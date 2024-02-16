/*
 * BlacklistedRangeExistsException.java Created on Sep 6, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1366
 *
 */
public class BlacklistedRangeExistsException extends BusinessException {
    /**
     * Constant for black listed range exists exception
     */
	public static final String BLACKLISTED_RANGE_EXISTS = "stockcontrol.defaults.blklistedrangeexists";
	
	/**
	 * 
	 */
	public static final String ALREADY_BLACKLISTED_RANGE = "stockcontrol.defaults.alreadyblklistedrange";

    public BlacklistedRangeExistsException() {
		super(BLACKLISTED_RANGE_EXISTS);
		// To be reviewed Auto-generated constructor stub
	}
    public BlacklistedRangeExistsException(String alreadyblklistedrange) {
		super(alreadyblklistedrange);
		
	}
    public BlacklistedRangeExistsException(Object[] errorData) {
		super(BLACKLISTED_RANGE_EXISTS,errorData);
	}
}
