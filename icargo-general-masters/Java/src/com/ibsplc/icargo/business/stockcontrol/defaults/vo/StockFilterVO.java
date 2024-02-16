/*
 * StockFilterVO.java Created on Sep 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1366
 *
 */
public class StockFilterVO extends AbstractVO {
    /**
     * Company Code
     */
    private String companyCode;
    /**
     *
     */
    private int airlineIdentifier;
    /**
     *
     */
    private String airlineCode;
    
    private int totalRecords;//Added by A-5214 as part from the ICRD-20959
    /**
     * Stock Holder Code
     */
    private String stockHolderCode;
    /**
     * Stock Holder Type
     */
    private String stockHolderType;
    /**
     * Document Type
     */
    private String documentType;

    /**
     * Document Sub Type
     */
    private String documentSubType;
    /**
     * manual flag
     */
    private boolean isManual;
    /**
     * Is View Range call- to identify whether the call is for view range.
     */
    private boolean isViewRange;
    /**
     * Range From
     */
    private String rangeFrom;

    /**
     * Range To
     */
    private String rangeTo;

    /**
     * From Date
     */
    private LocalDate fromDate;

    /**
     * To Date
     */
    private LocalDate toDate;
    
    private String airportCode;
    
    private int pageNumber;
    
    private int absoluteIndex;
    //Added as part of ICRD-105821
    private String privilegeLevelType;

    private String privilegeLevelValue;
    
    private String privilegeRule;
	/**
	 * @return Returns the isViewRange.
	 */
	public boolean isViewRange() {
		return isViewRange;
	}
	/**
	 * @param isViewRange The isViewRange to set.
	 */
	public void setViewRange(boolean isViewRange) {
		this.isViewRange = isViewRange;
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
     * Default Constructor
     *
     */
    public StockFilterVO(){

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
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
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
     * @return Returns the fromDate.
     */
    public LocalDate getFromDate() {
        return fromDate;
    }
    /**
     * @param fromDate The fromDate to set.
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    /**
     * @return Returns the rangeFrom.
     */
    public String getRangeFrom() {
        return rangeFrom;
    }
    /**
     * @param rangeFrom The rangeFrom to set.
     */
    public void setRangeFrom(String rangeFrom) {
        this.rangeFrom = rangeFrom;
    }
    /**
     * @return Returns the rangeTo.
     */
    public String getRangeTo() {
        return rangeTo;
    }
    /**
     * @param rangeTo The rangeTo to set.
     */
    public void setRangeTo(String rangeTo) {
        this.rangeTo = rangeTo;
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
     * @return Returns the toDate.
     */
    public LocalDate getToDate() {
        return toDate;
    }
    /**
     * @param toDate The toDate to set.
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
    /**
     * @return Returns the isManual.
     */
    public boolean isManual() {
        return isManual;
    }
    /**
     * @param isManual The isManual to set.
     */
    public void setManual(boolean isManual) {
        this.isManual = isManual;
    }
	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	/**
	 * 
	 * @return
	 */
    public int getAbsoluteIndex() {
		return absoluteIndex;
	}
    
    /**
     * 
     * @param absoluteIndex
     */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @return the privilegeLevelType
	 */
	public String getPrivilegeLevelType() {
		return privilegeLevelType;
	}
	/**
	 * @param privilegeLevelType the privilegeLevelType to set
	 */
	public void setPrivilegeLevelType(String privilegeLevelType) {
		this.privilegeLevelType = privilegeLevelType;
	}
	/**
	 * @return the privilegeLevelValue
	 */
	public String getPrivilegeLevelValue() {
		return privilegeLevelValue;
	}
	/**
	 * @param privilegeLevelValue the privilegeLevelValue to set
	 */
	public void setPrivilegeLevelValue(String privilegeLevelValue) {
		this.privilegeLevelValue = privilegeLevelValue;
	}
	/**
	 * @return the privilegeRule
	 */
	public String getPrivilegeRule() {
		return privilegeRule;
	}
	/**
	 * @param privilegeRule the privilegeRule to set
	 */
	public void setPrivilegeRule(String privilegeRule) {
		this.privilegeRule = privilegeRule;
	}
	
	
}
