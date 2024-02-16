/*
 * RangeExistsException.java Created on Sep 5, 2005
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
public class RangeExistsException extends BusinessException {
    /**
     * 
     */
	public static final String RANGE_EXISTS = "stockcontrol.defaults.rangeexists";
    /**
     * 
     */
	public static final String RANGE_NOT_EXISTS = "stockcontrol.defaults.rangenotexists";
    /**
     * 
     */
	public static final String RANGEVALUE_EXISTS="stockcontrol.defaults.rangevalueexists";
    /**
     *
     */
	public RangeExistsException() {
		super(RANGE_EXISTS);
		// To be reviewed Auto-generated constructor stub
	}

    public RangeExistsException(String rangeExists) {
		super(rangeExists);
		// To be reviewed Auto-generated constructor stub
	}
    public RangeExistsException(Object[] errorData) {
		super(RANGE_EXISTS);
	}
}
