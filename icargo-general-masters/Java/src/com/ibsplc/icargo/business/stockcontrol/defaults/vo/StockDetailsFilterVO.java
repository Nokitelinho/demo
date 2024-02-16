/*
 * StockDetailsFilterVO.java Created on July 9, 2008
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
 * @author A-3184
 *
 */
public class StockDetailsFilterVO extends AbstractVO {

    /**
     * Company Code
     */
    private String companyCode;

	/**
	* airline Identifier
	*/
    private int airlineId;
    
    private String customerCode;
    
    private String stockHolderCode;
    
    private String documentType;
    
    private String documentSubType;
    
    private LocalDate startDate;

	private LocalDate endDate;
	
	private int awbPrefix;
	
	private int pageNumber;
    
    private int absoluteIndex;
    
    private boolean isManual;
/*
 * Added by Chippy for CR 1878
 * */
    private String stockHolderType;
    private int pageSize;
    
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
    
	public String getStockHolderType() {
		return stockHolderType;
	}

	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
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
	 * @return Returns the endDate.
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return Returns the startDate.
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return airlineId
	 */
	public int getAirlineId(){
		return airlineId;
	}
	/**
	 * @param airlineId
	 */
	public void setAirlineId(int airlineId){
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
	 * @return Returns the customerCode.
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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
	 * @return Returns the awbPrefix.
	 */
	public int getAwbPrefix() {
		return awbPrefix;
	}

	/**
	 * @param awbPrefix The awbPrefix to set.
	 */
	public void setAwbPrefix(int awbPrefix) {
		this.awbPrefix = awbPrefix;
	}

}

