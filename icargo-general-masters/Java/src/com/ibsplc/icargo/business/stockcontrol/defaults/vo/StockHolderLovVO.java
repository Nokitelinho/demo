/*
 * StockHolderLovVO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 *
 * This VO is used to populate the stockholder LOV 
 */
public class StockHolderLovVO extends AbstractVO {
    /**
     * company code
     */
    private String companyCode;
    /**
     * stock holder code
     */
    private String stockHolderCode;
    /**
     * description
     */
    private String description;
    
    /**
     * Denotes the stockholder type. Possible values are H-headqarters,
     * R-region, S-station,A-agent
     */
    private String stockHolderType;
    /**
     * stock holder name
     */
    private String stockHolderName;
    
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
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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
	 * getting stock holder name
	 * @return stockHolderName
	 */
    public String getStockHolderName() {
		return stockHolderName;
	}
    /**
     * setting stock holder name
     * @param stockHolderName
     */
	public void setStockHolderName(String stockHolderName) {
		this.stockHolderName = stockHolderName;
	}
}
