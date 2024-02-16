/*
 * StockRangeUtilisationVO.java Created on Jan 18,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Calendar;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-3184 & A-3155
 *
 */
public class StockRangeUtilisationVO extends AbstractVO {

	/**
	 * constant for stock holder audit product name
	 */
	public static final String STOCKHOLDER_AUDIT_PRODUCTNAME = "stockcontrol";
    /**
     * constant for stock holder audit module name
     */
	public static final String STOCKHOLDER_AUDIT_MODULENAME = "defaults";
	/**
	 * constant for stock holder entity name
	 */
	public static final String STOCKHOLDER_AUDIT_ENTITYNAME = "stockcontrol.defaults.StockHolder";

	private String companyCode;

	private int airlineIdentifier;

	private String stockHolderCode;

	private String documentType;

	private String documentSubType;

	private String serialNumber;
 
	private String startRange;

	private String endRange;

	private long asciiStartRange;

	private long asciiEndRange;

	private long numberOfDocuments;

	private String status;

	private String rangeType;

	private Calendar transactionDate;

	private LocalDate lastUpdateTime;

	private String lastUpdateUser;

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
	 * @return Returns the asciiEndRange.
	 */
	public long getAsciiEndRange() {
		return asciiEndRange;
	}

	/**
	 * @param asciiEndRange The asciiEndRange to set.
	 */
	public void setAsciiEndRange(long asciiEndRange) {
		this.asciiEndRange = asciiEndRange;
	}

	/**
	 * @return Returns the asciiStartRange.
	 */
	public long getAsciiStartRange() {
		return asciiStartRange;
	}

	/**
	 * @param asciiStartRange The asciiStartRange to set.
	 */
	public void setAsciiStartRange(long asciiStartRange) {
		this.asciiStartRange = asciiStartRange;
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
	 * @return Returns the serialNumber.
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return Returns the transactionDate.
	 */
	public Calendar getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Calendar transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the numberOfDocuments
	 */
	public long getNumberOfDocuments() {
		return numberOfDocuments;
	}

	/**
	 * @param numberOfDocuments the numberOfDocuments to set
	 */
	public void setNumberOfDocuments(long numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}

	/**
	 * @return the rangeType
	 */
	public String getRangeType() {
		return rangeType;
	}

	/**
	 * @param rangeType the rangeType to set
	 */
	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}







}
