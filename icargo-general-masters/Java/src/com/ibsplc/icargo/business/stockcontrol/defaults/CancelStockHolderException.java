/*
 * CancelStockHolderException.java Created on Jun 06, 2006
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
 * @author A-1927
 *
 */
public class CancelStockHolderException extends BusinessException {
    /**
     * Constant for Invalid stock holder exception
     */
	public static final String CANCEL_STOCKHOLDER = 
		"stockcontrol.defaults.stockholdercannotbecancelled";
	
    public CancelStockHolderException() {
		super(CANCEL_STOCKHOLDER);
		// To be reviewed Auto-generated constructor stub
	}
    
    public CancelStockHolderException(String errorCode) {
		super(errorCode);
		// To be reviewed Auto-generated constructor stub
	}
    public CancelStockHolderException(String errorCode,Object[] errorData) {
    	super(errorCode,errorData);
    }
}
