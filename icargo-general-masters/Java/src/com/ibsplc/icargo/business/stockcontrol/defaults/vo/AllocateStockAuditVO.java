/*
 * AllocateStockAuditVO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.io.Serializable;
import java.util.Calendar;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-1358
 *
 */
public class AllocateStockAuditVO extends AuditVO implements Serializable{

    /**
     * Audit Action code of inserting stockholder
     */
    public static final String ALLOCATERANGE_INSERT_ACTIONCODE = "STKRNGINS";

    /**
     * Audit Action code of updating stockholder
     */
    public static final String ALLOCATERANGE_UPDATE_ACTIONCODE = "STKRNGUPD";

    /**
     * Audit Action code of deleting stockholder
     */
    public static final String ALLOCATERANGE_DEPLETE_ACTIONCODE = "STKRNGDEL";

    /**
     * Audit Key for sequence number generation
     */
    public static final String ALLOCATERANGE_AUDIT_KEY = "ALLOCATESTOCK_AUDIT_KEY";

     /**
     * Stock holder type
     */
    private String stockHolderType;

    /**
     * Additional info
     */
    private String additionalInfo;
    /**
     * Stock holder Code
     */
    private String stockHolderCode;

    /**
     * Document Type
     */
    private String docType;
    /**
     * Document sub Type
     */
    private String docSubType;
    /**
     * Last update time
     */
    private Calendar lastUpdateTime;

    /**
     * Last update user
     */
    private String lastUpdateUser;




    /**
     * This constructor invokes the constructor of the super class - AuditVO
     * @param productName
     * @param moduleName
     * @param entityName
     */
    public AllocateStockAuditVO(String productName, String moduleName, String entityName) {
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
     * Method returning stock holder code
     * @return stockHolderCode
     */
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * Method for setting the stock holder code
	 * @param stockHolderCode
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	/**
	 * Method for getting the last update time
	 * @return lastUpdateTime
	 */
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * Method for setting last update time
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * Method for getting last update user
	 * @return lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * Method for setting last update user
	 * @param lastUpdateUser
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * Method for getting document sub type
	 * @return docSubType
	 */
	public String getDocSubType() {
		return docSubType;
	}
	/**
	 * Method for setting the document sub type
	 * @param docSubType
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}
	/**
	 * Method for getting the document type
	 * @return docType
	 */
	public String getDocType() {
		return docType;
	}
	/**
	 * Method for setting the document type
	 * @param docType
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}


}
