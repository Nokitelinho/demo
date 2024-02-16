/*
 * InvalidStockHolderException.java Created on Sep 9, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1366
 *
 */
public class InvalidStockHolderException extends BusinessException {
    /**
     * Constant for Invalid stock holder exception
     */
	public static final String INVALID_STOCKHOLDER = 
		"stockcontrol.defaults.invalidstockholder";
	
	/**
	 * 
	 */
	public static final String INVALID_STOCKHOLDER_FOR_MONITOR = 
		"stockcontrol.defaults.invalidstockholderformonitor";
	/**
	 * Constant for already deleted stock holder exception
	 */
	public static final String STOCKHOLDER_ALREADY_DELETED = 
		"stockcontrol.defaults.alreadydeletedstockholder";

    /**
     * 
     */
    public InvalidStockHolderException() {
		super(INVALID_STOCKHOLDER);
		// To be reviewed Auto-generated constructor stub
	}
    /**
     * @param alreadyDeletedStockholder
     */
    public InvalidStockHolderException(String alreadyDeletedStockholder){
    	super(alreadyDeletedStockholder);
    }

    /**
     * @param errorData
     */
    public InvalidStockHolderException(Object[] errorData) {
		super(INVALID_STOCKHOLDER,errorData);
	}
}
