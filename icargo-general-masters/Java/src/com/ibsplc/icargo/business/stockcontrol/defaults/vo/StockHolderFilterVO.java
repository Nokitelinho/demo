/*
 * StockHolderFilterVO.java Created on Jul 20, 2005
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
public class StockHolderFilterVO extends AbstractVO {
    /**
     * company code
     */
    private String companyCode;
    /**
     * stock holder code
     */
    private String stockHolderCode;    
    /**
     * document type
     */
    private String documentType;
    /**
     * document sub type
     */
    private String documentSubType;
    /**
     * stock holder type
     */
    private String stockHolderType;
    /**
     * stockholder priority
     */
    private int stockHolderPriority;
    
    /**
	 * @return Returns the stockHolderType.
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}
	private int pageNumber;
    
    private int absoluteIndex;
    
    private String airlineIdentifier;
    
    private int totalRecordCount;
 

	/**
	 * 	Getter for totalRecordCount 
	 *	Added by : A-5175 on 12-Oct-2012
	 * 	Used for :CR ICRD-20959
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 *  @param totalRecordCount the totalRecordCount to set
	 * 	Setter for totalRecordCount 
	 *	Added by : A-5175 on 12-Oct-2012
	 * 	Used for :CR ICRD-20959
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	/**
	 * @param stockHolderType The stockHolderType to set.
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
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

	public int getStockHolderPriority() {
		return stockHolderPriority;
	}

	public void setStockHolderPriority(int stockHolderPriority) {
		this.stockHolderPriority = stockHolderPriority;
	}

	/**
	 * @return Returns the absoluteIndex.
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}

	/**
	 * @param absoluteIndex The absoluteIndex to set.
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
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
	 * @return the airlineIdentifier
	 */
	public String getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier the airlineIdentifier to set
	 */
	public void setAirlineIdentifier(String airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
}
