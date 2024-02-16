/*
 * StockHolderValidationVO.java Created on Jul 20, 2005
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
 */
public class StockHolderValidationVO extends AbstractVO {
    /**
     * company code
     */
    private String companyCode;
    /**
     * stock holder code
     */
    private String stockHolderCode;
                      
    /**
     * @return companyCode
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
     * @return stockHolderCode
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
}
