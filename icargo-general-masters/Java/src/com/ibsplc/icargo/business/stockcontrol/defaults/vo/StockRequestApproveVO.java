/*
 * StockRequestApproveVO.java Created on Sep 3, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1366
 *
 */
public class StockRequestApproveVO extends AbstractVO {
    
    /**
     * Company Code
     */
    private String companyCode;
    
    /**
     * Approver Code
     */
    private String approverCode;
    /**
     * Document type
     */
    private String documentType;
    /**
     * Document sub type
     */
    private String documentSubType;
    /**
     * Collection<StockRequestVO>
     */
    private Collection<StockRequestVO> stockRequests;
    
    /**
     * Flag for indicating whether stock available 
     * check is needed or not
     */
    private boolean isStockAvailableCheck;
    
    private LocalDate lastUpdateTime;
    
    /**
	 * @return Returns the documentSubType.
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}

	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentSubType The documentSubType to set.
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}

	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public StockRequestApproveVO(){
        
    }
    
    /**
     * @return Returns the approverCode.
     */
    public String getApproverCode() {
        return approverCode;
    }
    /**
     * @param approverCode The approverCode to set.
     */
    public void setApproverCode(String approverCode) {
        this.approverCode = approverCode;
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
     * @return Returns the isStockAvailableCheck.
     */
    public boolean isStockAvailableCheck() {
        return isStockAvailableCheck;
    }
    /**
     * @param isStockAvailableCheck The isStockAvailableCheck to set.
     */
    public void setStockAvailableCheck(boolean isStockAvailableCheck) {
        this.isStockAvailableCheck = isStockAvailableCheck;
    }
    /**
     * @return Returns the stockRequests.
     */
    public Collection<StockRequestVO> getStockRequests() {
        return stockRequests;
    }
    /**
     * @param stockRequests The stockRequests to set.
     */
    public void setStockRequests(Collection<StockRequestVO> stockRequests) {
        this.stockRequests = stockRequests;
    }

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
