/*
 * StockAirlineAuditVO.java Created on Oct 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Calendar;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-1954
 *
 */
public class StockAirlineAuditVO extends AuditVO {

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
    public static final String AUDIT_ENTITY = "stockcontrol.defaults.StockAirlineAudit";

    private int airlineId;
    private String documentType;
    private String documentSubType;
    private String stockHolderType;
    private String additionalInfo;
    private String stockHolderCode;
    private Calendar lastUpdateTime;
    private String lastUpdateUser;

    /**
     * This constructor invokes the constructor of the super class - AuditVO
     * @param productName
     * @param moduleName
     * @param entityName
     */
    public StockAirlineAuditVO(String productName, String moduleName, String entityName) {
    	super(productName, moduleName, entityName);
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
     * Method for getting stockHoldercode
     * @return stockHolderCode
     */
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * Method for setting stockHolderCode
	 * @param stockHolderCode
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	/**
	 * Method for getting lastUpdateTime
	 * @return lastUpdateTime
	 */
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * Method for setting lastUpdateTime
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * Method for setting lastUpdateUser
	 * @return lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * Method for setting lastUpdateUser
	 * @param lastUpdateUser
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the airlineId.
	 */
	public int getAirlineId() {
		return airlineId;
	}

	/**
	 * @param airlineId The airlineId to set.
	 */
	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
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


}
