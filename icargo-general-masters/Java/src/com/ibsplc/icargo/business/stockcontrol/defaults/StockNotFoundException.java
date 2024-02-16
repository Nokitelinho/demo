/*
 * StockNotFoundException.java Created on Sep 3, 2005
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
public class StockNotFoundException extends BusinessException {
    /**
     * It is used when stock is not available
     */
    public static final String STOCK_NOTFOUND = "stockcontrol.defaults.stocknotfound";
    /**
     * It is used when range is not found
     */
    public static final String RANGE_NOTFOUND = "stockcontrol.defaults.rangenotfound";
    
    public StockNotFoundException() {
		super(STOCK_NOTFOUND);
	}
    
    public StockNotFoundException(String rangeNotFound) {
		super(rangeNotFound);
	}

    public StockNotFoundException(Object[] errorData) {
		super(STOCK_NOTFOUND);
	}
}
