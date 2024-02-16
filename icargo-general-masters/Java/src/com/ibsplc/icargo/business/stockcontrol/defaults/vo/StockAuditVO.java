/*
 * StockAuditVO.java Created on Jul 20, 2005
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
 * @author A-1358
 *
 */
public class StockAuditVO extends AuditVO {

    /**
     * Audit Action code of inserting stockholder
     */
    public static final String STOCKHOLDER_INSERT_ACTIONCODE = "STKHLDINS";

    /**
     * Audit Action code of updating stockholder
     */
    public static final String STOCKHOLDER_UPDATE_ACTIONCODE = "STKHLDUPD";

    /**
     * Audit Action code of deleting stockholder
     */
    public static final String STOCKHOLDER_DELETE_ACTIONCODE = "STKHLDDEL";

    /**
     * Audit Key for sequence number generation
     */
    public static final String STOCKHOLDER_AUDIT_KEY = "STOCKHOLDER_AUDIT_KEY";

    /**
     * Stock insertion action code is given
     */
    public static final String STOCK_INSERT_ACTIONCODE="STKINS";
    /**
     * stock updation action code is given
     */
    public static final String STOCK_UPDATE_ACTIONCODE="STKUPD";
    /**
     * stock deletion action code is given
     */
    public static final String STOCK_DELETE_ACTIONCODE="STKDEL";


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
    public static final String AUDIT_ENTITY = "stockcontrol.defaults.Stock";

    private String docType;
    
    private String docSubType;
    
    private int airlineId;
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
     * lastUpdateTime
     */
    private Calendar lastUpdateTime;

    /**
     * lastUpdateUser
     */
    private String lastUpdateUser;




    /**
     * This constructor invokes the constructor of the super class - AuditVO
     * @param productName
     * @param moduleName
     * @param entityName
     */
    public StockAuditVO(String productName, String moduleName, String entityName) {
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
	 * @return
	 */
	public int getAirlineId() {
		return airlineId;
	}

	/**
	 * @param airlineId
	 */
	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
	}

	/**
	 * @return
	 */
	public String getDocSubType() {
		return docSubType;
	}

	/**
	 * @param docSubType
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}

	/**
	 * @return
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}


}
