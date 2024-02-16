/*
 * StockRequestAuditVO.java Created on Sep 7, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-1366
 *
 */
public class StockRequestAuditVO extends AuditVO {

    /**
     * Audit Action code of inserting stockrequest
     */
    public static final String STOCKREQUEST_INSERT_ACTIONCODE = "STKREQINS";

    /**
     * Audit Action code of updating stockrequest
     */
    public static final String STOCKREQUEST_UPDATE_ACTIONCODE = "STKREQUPD";

    /**
     * Audit Action code of cancelling stockrequest
     */
    public static final String STOCKREQUEST_CANCEL_ACTIONCODE = "STKREQDEL";
    /**
     * 
     */
    public static final String AUDIT_PRODUCTNAME = "stockcontrol";
    
    /**
     * 
     */
    public static final String AUDIT_MODULENAME = "defaults";
    
    /**
     * 
     */
    public static final String AUDIT_ENTITY = "stockcontrol.defaults.StockRequest";
    /**
     * StockHolder Code
     */
    private String stockHolderCode;
    /**
     * Request Reference Number
     */
    private String requestRefNumber;
    /**
     * Document type
     */
    private String documentType;
    /**
     * Document sub type
     */
    private String documentSubType;
    /**
     * Additional info
     */
    private String additionalInfo;

    /**
	 * @return Returns the requestRefNumber.
	 */
	public String getRequestRefNumber() {
		return requestRefNumber;
	}
	/**
	 * @param requestRefNumber The requestRefNumber to set.
	 */
	public void setRequestRefNumber(String requestRefNumber) {
		this.requestRefNumber = requestRefNumber;
	}
 /** This constructor invokes the constructor of the super class - AuditVO
    * @param product
    * @param moduleName
    * @param entityName
    */
   public StockRequestAuditVO(String product, String moduleName,
           String entityName){
   	 super(product,moduleName,entityName);

   }
    /**
     * @return Returns the additionalInfo.
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    /**
     * @param additionalInfo The additionalInfo to set.
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
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

}
