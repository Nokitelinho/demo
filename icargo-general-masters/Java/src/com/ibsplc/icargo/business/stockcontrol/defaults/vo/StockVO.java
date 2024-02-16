/*
 * StockVO.java Created on Jul 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * Value Object representing the stock held by a particular Stock Holder
 * corresponding to a specific document type and subtype
 *
 */
public class StockVO extends AbstractVO {

	/**
	 * Company code
	 */
    private String companyCode;
    /**
     * 
     */
    private String airline;
    /**
     *
     */
    private int airlineIdentifier;
    /**
     * Stock Holder code
     */
    private String stockHolderCode;
    /**
     * document type
     */
    private String documentType;

    /**
     * Document sub type
     */
    private String documentSubType;

    /**
     * This field holds the company code of the approver of this stock
     */
    private String stockApproverCompany;

    /**
     * This field holds the stockHolderCode for the approver of
     * this stock
     */
    private String stockApproverCode;

    /**
     * Reorder lvel of teh stock. When stock inhand reaches this level
     * system autorequest for new stock or generate alerts based on setup
     */
    private long reorderLevel;

    /**
     * Indicates the quantity to be ordered along with each automatic request
     */
    private int reorderQuantity;

    /**
     * Indicates whether an alert is to be generated when stock reaches
     * the reorder level. Possible values are 'Y' and 'N'
     */
    private boolean isReorderAlertFlag;

    /**
     * Indicates whether stock request needs to be raised automatically when
     * stock reaches the reorder level
     */
    private boolean isAutoRequestFlag;

    /**
     * General remarks
     */
    private String remarks;
    /**
     * operation flag
     */
    private String operationFlag;

    /**
     * Collection of ranges that constitute this stock
     * Collection<RangeVO>
     */
    private Collection<RangeVO> ranges;
    
    /**
     * 
     */
    private int airlineId;
    
    /**
     * 
     */
    private double totalStock;
    
    /**
     * autoprocessQuantity
     */
    private int autoprocessQuantity;

    private boolean isAutoPopulateFlag;
    /**
     * @return Returns the autoRequestFlag.
     */
    public boolean isAutoRequestFlag() {
        return isAutoRequestFlag;
    }

    /**
     * @param isAutoRequestFlag The autoRequestFlag to set.
     */
    public void setAutoRequestFlag(boolean isAutoRequestFlag) {
        this.isAutoRequestFlag = isAutoRequestFlag;
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
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * @param airline The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
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
     * @return Returns the ranges.
     */
    public Collection<RangeVO> getRanges() {
        return ranges;
    }

    /**
     * @param ranges The ranges to set.
     */
    public void setRanges(Collection<RangeVO> ranges) {
        this.ranges = ranges;
    }

    /**
     * @return Returns the remarks.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks The remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return Returns the reorderAlertFlag.
     */
    public boolean isReorderAlertFlag() {
        return isReorderAlertFlag;
    }

    /**
     * @param isReorderAlertFlag The reorderAlertFlag to set.
     */
    public void setReorderAlertFlag(boolean isReorderAlertFlag) {
        this.isReorderAlertFlag = isReorderAlertFlag;
    }

    /**
     * @return Returns the reorderLevel.
     */
    public long getReorderLevel() {
        return reorderLevel;
    }

    /**
     * @param reorderLevel The reorderLevel to set.
     */
    public void setReorderLevel(long reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    /**
     * @return Returns the reorderQuantity.
     */
    public int getReorderQuantity() {
        return reorderQuantity;
    }

    /**
     * @param reorderQuantity The reorderQuantity to set.
     */
    public void setReorderQuantity(int reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    /**
     * @return Returns the stockApproverCode.
     */
    public String getStockApproverCode() {
        return stockApproverCode;
    }

    /**
     * @param stockApproverCode The stockApproverCode to set.
     */
    public void setStockApproverCode(String stockApproverCode) {
        this.stockApproverCode = stockApproverCode;
    }

    /**
     * @return Returns the stockApproverCompany.
     */
    public String getStockApproverCompany() {
        return stockApproverCompany;
    }

    /**
     * @param stockApproverCompany The stockApproverCompany to set.
     */
    public void setStockApproverCompany(String stockApproverCompany) {
        this.stockApproverCompany = stockApproverCompany;
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
     * getting operation flag
     * @return operationFlag
     */
    public String getOperationFlag(){
    	return operationFlag;
    }
    /**
     * Method for setting operationFlag
     * @param operationFlag
     */
    public void setOperationFlag(String operationFlag){
    	this.operationFlag=operationFlag;
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
	 * @return Returns the totalStock.
	 */
	public double getTotalStock() {
		return totalStock;
	}

	/**
	 * @param totalStock The totalStock to set.
	 */
	public void setTotalStock(double totalStock) {
		this.totalStock = totalStock;
	}

	/**
	 * @return Returns the autoprocessQuantity.
	 */
	public int getAutoprocessQuantity() {
		return autoprocessQuantity;
	}

	/**
	 * @param autoprocessQuantity The autoprocessQuantity to set.
	 */
	public void setAutoprocessQuantity(int autoprocessQuantity) {
		this.autoprocessQuantity = autoprocessQuantity;
	}
	/**
	 * 
	 * @return isAutoPopulateFlag
	 */
	public boolean isAutoPopulateFlag() {
		return isAutoPopulateFlag;
	}
	/**
	 * 
	 * @param isAutoPopulateFlag
	 */
	public void setAutoPopulateFlag(boolean isAutoPopulateFlag) {
		this.isAutoPopulateFlag = isAutoPopulateFlag;
	}
}
