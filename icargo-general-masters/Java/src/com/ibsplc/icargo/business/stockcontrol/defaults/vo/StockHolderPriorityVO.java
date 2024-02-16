/*
 * StockHolderPriorityVO.java Created on Sep 3, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1366
 *
 */
public class StockHolderPriorityVO extends AbstractVO {
    /**
     * Company Code
     */
    private String companyCode;
    /**
     * Stock Holder Type
     */
    private String stockHolderType;
    
    /**
     * priority
     */
    private long priority;
    /**
     * Default constructor
     *
     */
    
    /**
     * StockHolder Description
     */
    private String stockHolderDescription;
    
    /**
     * StockHolder Code
     */
    private String stockHolderCode;
    
    /**
     * Default Constructor
     *
     */
    public StockHolderPriorityVO(){
        
    }
    
    
    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }
    /**
     * @param companyCode The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    /**
     * @return Returns the priority.
     */
    public long getPriority() {
        return priority;
    }
    /**
     * @param priority The priority to set.
     */
    public void setPriority(long priority) {
        this.priority = priority;
    }
    /**
     * @return Returns the stockHolderType.
     */
    public String getStockHolderType() {
        return stockHolderType;
    }
    /**
     * @param stockHolderType The stockHolderType to set.
     */
    public void setStockHolderType(String stockHolderType) {
        this.stockHolderType = stockHolderType;
    }
    /**
     * @return Returns the stockHolderCode.
     */
    public String getStockHolderCode() {
        return stockHolderCode;
    }
    /**
     * @param stockHolderCode The stockHolderCode to set.
     */
    public void setStockHolderCode(String stockHolderCode) {
        this.stockHolderCode = stockHolderCode;
    }
    /**
     * @return Returns the stockHolderDescription.
     */
    public String getStockHolderDescription() {
        return stockHolderDescription;
    }
    /**
     * @param stockHolderDescription The stockHolderDescription to set.
     */
    public void setStockHolderDescription(String stockHolderDescription) {
        this.stockHolderDescription = stockHolderDescription;
    }
}
