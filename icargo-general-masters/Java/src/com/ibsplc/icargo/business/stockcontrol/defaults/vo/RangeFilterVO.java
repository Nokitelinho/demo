/*
 * RangeFilterVO.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1366
 *
 */
public class RangeFilterVO extends AbstractVO {
    
    /**
     * Company Code
     */
    private String companyCode;
    
    /**
     * Airline Identifier
     */
    private int airlineIdentifier;
    
    /**
     * Stock Holder Code
     */
    private String stockHolderCode;
    
    /**
     * Document Type
     */
    private String documentType;
    
    /**
     * Document Sub Type
     */
    private String documentSubType;
    
    /**
     * Start Range
     */
    private String startRange;
    
    /**
     * End Range
     */
    private String endRange;
    
    /**
     * Number of documents
     */
    private String numberOfDocuments;
    /**
     * manual flag
     */
    private boolean isManual;
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
     * Default Constructor
     *
     */
    public RangeFilterVO(){
        
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
     * @return Returns the endRange.
     */
    public String getEndRange() {
        return endRange;
    }
    /**
     * @param endRange The endRange to set.
     */
    public void setEndRange(String endRange) {
        this.endRange = endRange;
    }
    /**
     * @return Returns the numberOfDocuments.
     */
    public String getNumberOfDocuments() {
        return numberOfDocuments;
    }
    /**
     * @param numberOfDocuments The numberOfDocuments to set.
     */
    public void setNumberOfDocuments(String numberOfDocuments) {
        this.numberOfDocuments = numberOfDocuments;
    }
    /**
     * @return Returns the startRange.
     */
    public String getStartRange() {
        return startRange;
    }
    /**
     * @param startRange The startRange to set.
     */
    public void setStartRange(String startRange) {
        this.startRange = startRange;
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
     * 
     * @return
     */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	
	/**
	 * 
	 * @param airlineIdentifier
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
}
