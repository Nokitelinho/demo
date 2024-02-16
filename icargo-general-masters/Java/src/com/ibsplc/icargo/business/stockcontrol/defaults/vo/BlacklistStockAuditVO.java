/*
 * BlacklistStockAuditVO.java Created on Sep 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-1366
 *
 */
public class BlacklistStockAuditVO extends AuditVO implements Serializable {
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
	    public static final String AUDIT_ENTITY = "stockcontrol.defaults.BlackListStock";
    /**
     * Action code for blacklisting a stock
     */
    public static final String STOCK_BLKLST_ACTIONCODE = "STKBLKLST";

    /**
     * Action code for revoking a blacklisted stock
     */
    public static final String STOCK_BLKLST_REVOKE_ACTIONCODE = "STKBLKLSTRVK";


    /**
     * Document type
     */
    private String documentType;

    /**
     * Document subtype
     */
    private String documentSubType;

    /**
     * Additional info
     */
    private String additionalInfo;


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
     * @return Returns the documentSubType.
     */
    public String getDocumentSubType() {
        return documentSubType;
    }
    /**
     * @param documentSubType The documentSubType to set.
     */
    public void setDocumentSubType(String documentSubType) {
        this.documentSubType = documentSubType;
    }
    /**
     * @return Returns the documentType.
     */
    public String getDocumentType() {
        return documentType;
    }
    /**
     * @param documentType The documentType to set.
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * This constructor invokes the constructor of the super class - AuditVO
     * @param productName
     * @param moduleName
     * @param entityName
     */
    public BlacklistStockAuditVO(String productName, String moduleName, String entityName) {
    	super(productName, moduleName, entityName);
    }
}
