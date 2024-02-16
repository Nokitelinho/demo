/*
 * StockRangeHistoryVO.java Created on Jan 18,2008.
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Calendar;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author A-3184 & A-3155
 *
 */
public class StockRangeHistoryVO extends AbstractVO {
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
	public static final String STOCKHOLDER_AUDIT_ENTITYNAME = "stockcontrol.defaults.StockRangeHistory";

	private String companyCode;

	private int airlineIdentifier;    

	private String fromStockHolderCode;  

	private String documentType;

	private String documentSubType;

	private String serialNumber; 

	private String accountNumber;

	private String rangeType;

	private String startRange;

	private String endRange;

	private String awbRange;
	
	private int checkDigit;

	private long asciiStartRange;

	private long asciiEndRange;

	private long numberOfDocuments;

	private String status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
	private Calendar transactionDate;
	
	private String transDateStr;

	private String toStockHolderCode;

	private LocalDate lastUpdateTime;

	private String lastUpdateUser;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
	private Calendar startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
	private Calendar endDate;

	  /**
	 * Added by A-2881 for ICRD-3082
	 */
	
	private String userId;
	
	private String remarks;
	
	private double voidingCharge;
	
	private String currencyCode;
	
	private String autoAllocated;

	  /**
     * Default Constructor
     *
     */
    public StockRangeHistoryVO(){

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
	 * @return Returns the fromStockHolderCode.
	 */
	public String getFromStockHolderCode() {
		return fromStockHolderCode;
	}

	/**
	 * @param fromStockHolderCode The fromStockHolderCode to set.
	 */
	public void setFromStockHolderCode(String fromStockHolderCode) {
		this.fromStockHolderCode = fromStockHolderCode;
	}

	/**
	 * @return Returns the numberOfDocuments.
	 */
	public long getNumberOfDocuments() {
		return numberOfDocuments;
	}

	/**
	 * @param numberOfDocuments The numberOfDocuments to set.
	 */
	public void setNumberOfDocuments(long numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}

	/**
	 * @return Returns the rangeType.
	 */
	public String getRangeType() {
		return rangeType;
	}

	/**
	 * @param rangeType The rangeType to set.
	 */
	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
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
	 * @return Returns the toStockHolderCode.
	 */
	public String getToStockHolderCode() {
		return toStockHolderCode;
	}

	/**
	 * @param toStockHolderCode The toStockHolderCode to set.
	 */
	public void setToStockHolderCode(String toStockHolderCode) {
		this.toStockHolderCode = toStockHolderCode;
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
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return Returns the awbRange.
	 */
	public String getAwbRange() {
		return awbRange;
	}

	/**
	 * @param awbRange The awbRange to set.
	 */
	public void setAwbRange(String awbRange) {
		this.awbRange = awbRange;
	}

	/**
	 * @return the transDateStr
	 */
	public String getTransDateStr() {
		return transDateStr;
	}

	/**
	 * @param transDateStr the transDateStr to set
	 */
	public void setTransDateStr(String transDateStr) {
		this.transDateStr = transDateStr;
	}

	public int getCheckDigit() {
		return checkDigit;
	}

	public void setCheckDigit(int checkDigit) {
		this.checkDigit = checkDigit;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the voidingCharge
	 */
	public double getVoidingCharge() {
		return voidingCharge;
	}

	/**
	 * @param voidingCharge the voidingCharge to set
	 */
	public void setVoidingCharge(double voidingCharge) {
		this.voidingCharge = voidingCharge;
	}
	
	/**
	 * @return the autoAllocated
	 */
	public String getAutoAllocated() {
		return autoAllocated;
	}
	
	/**
	 * @param autoAllocated the autoAllocated to set
	 */

	public void setAutoAllocated(String autoAllocated) {
		this.autoAllocated = autoAllocated;
	}




}
