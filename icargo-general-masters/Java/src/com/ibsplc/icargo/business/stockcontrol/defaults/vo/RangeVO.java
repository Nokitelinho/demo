/*
 * RangeVO.java Created on Jul 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * Value Object representing the stock held by a particular Stock Holder
 * corresponding to a specific document type and subtype
 *
 */
public class RangeVO extends AbstractVO {

	/**
	 * 
	 */
	public static final int SUBSTRING_COUNT = 7;
	/**
	 * 
	 */
	public static final int DOCNUM_LENGTH = 8;
	/**
	 * 
	 */
	public static final int SUBSTRING_START = 0; 

	private String companyCode;

	private String stockHolderCode;

	private String documentType;

	private String documentSubType;

	private String sequenceNumber;

	private int airlineIdentifier;
	/**
	 * start Range
	 */
	private String startRange;
	
	private String awbPrefix;
	
	/**
	 * end range
	 */
	private String endRange;
	/**
	 * asciiStartRange
	 */
	private long asciiStartRange;
	/**
	 * asciiEndRange
	 */
	private long asciiEndRange;
	/**
	 * number of documents
	 */
	private long numberOfDocuments ;
	/**
	 * operational flag
	 */
	private String operationFlag;
	/**
	 * is black list
	 */
	private boolean isBlackList;
	/**
	 * is manual
	 */
	private boolean isManual;
	
	private LocalDate lastUpdateTime;
	
	private String lastUpdateUser;
	
	private String stockHolderName;
	
	private String avlStartRange;
    
    private String avlEndRange;
    
    private long avlNumberOfDocuments;
    
    private String allocStartRange;
    
    private String allocEndRange;
    
    private long allocNumberOfDocuments;
    
    private String fromStockHolderCode;

    
    private String usedStartRange;
    
    private String usedEndRange;
    
    private String allocatedRange;
    
    private String availableRange;
    
    private String usedRange;
    
    private long usedNumberOfDocuments;
    
    /*
     *	this represents the order in which the stock is allocated, 
     *	corresponding to which the reservation can be done
     * 
     */
    private LocalDate stockAcceptanceDate;
    
    private Collection<String> masterDocumentNumbers;
    
	public Collection<String> getMasterDocumentNumbers() {
		return masterDocumentNumbers;
	}
	public void setMasterDocumentNumbers(Collection<String> masterDocumentNumbers) {
		this.masterDocumentNumbers = masterDocumentNumbers;
	}
	public RangeVO(RangeVO rangeVO) {
		// TODO Auto-generated constructor stub
	}
	public RangeVO() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Method for getting the start range
	 * @return startRange
	 */
	public  String getStartRange(){
		return this.startRange;
	}
	/**
	 * Method for setting the start range
	 * @param startRange
	 */
	public  void setStartRange(String startRange){
		this.startRange=startRange;
	}
	/**
	 * method for getting the endrange
	 * @return endRange
	 */
	public String getEndRange(){
		return this.endRange;
	}
	/**
	 * Method for setting the end range
	 * @param endRange
	 */
	public void setEndRange(String endRange){
		this.endRange=endRange;
	}
	/**
	 * Method for getting the operation flag
	 * @return operationFlag
	 */
	public String getOperationFlag(){
		return this.operationFlag;
	}
	/**
	 * Method for setting the operation flag
	 * @param operationFlag
	 */
	public void setOperationFlag(String operationFlag){
		this.operationFlag=operationFlag;
	}
	/**
	 * Method for getting the isblacklist flag
	 * @return isBlackList
	 */
	public boolean isBlackList() {
		return isBlackList;
	}
	/**
	 * Method for setting isBlackList flag
	 * @param isBlackList
	 */
	public void setBlackList(boolean isBlackList) {
		this.isBlackList = isBlackList;
	}
	/**
	 * Method for getting number of documents
	 * @return numberOfDocuments
	 */
	public long getNumberOfDocuments() {
		return numberOfDocuments;
	}
	/**
	 * Method for setting numberOfDocuments
	 * @param numberOfDocuments
	 */
	public void setNumberOfDocuments(long numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}
	/**
	 * Method for getting isManual flag
	 * @return isManual
	 */
	public boolean isManual() {
		return isManual;
	}
	/**
	 * Method for setting isManual flag
	 * @param isManual
	 */
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}
	/**
	 * getting asciiEndRange
	 * @return asciiEndRange
	 */
	public long getAsciiEndRange() {
		return asciiEndRange;
	}
	/**
	 * setting asciiEndRange
	 * @param asciiEndRange
	 */
	public void setAsciiEndRange(long asciiEndRange) {
		this.asciiEndRange = asciiEndRange;
	}
	/**
	 * Getting asciiStartRange
	 * @return asciiStartRange
	 */
	public long getAsciiStartRange() {
		return asciiStartRange;
	}
	/**
	 * Setting asciiStartRange
	 * @param asciiStartRange
	 */
	public void setAsciiStartRange(long asciiStartRange) {
		this.asciiStartRange = asciiStartRange;
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
	 * @return
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}
	/**
	 * @param documentSubType
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}
	/**
	 * @return
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * @param documentType
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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
	 * @return
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * @param stockHolderCode
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	/**
	 * @return Returns the stockAcceptanceDate.
	 */
	public LocalDate getStockAcceptanceDate() {
		return stockAcceptanceDate;
	}
	/**
	 * @param stockAcceptanceDate The stockAcceptanceDate to set.
	 */
	public void setStockAcceptanceDate(LocalDate stockAcceptanceDate) {
		this.stockAcceptanceDate = stockAcceptanceDate;
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
	 * return the hashcode corresponding to that product
	 * @return int
	 */
	 public int hashCode() {
		 int hashCodePk;
		 if(stockHolderCode==null && documentType==null && documentSubType==null && airlineIdentifier==0){
			 hashCodePk = System.identityHashCode(this);
		 }else{
			hashCodePk =new StringBuilder().append(companyCode)
				.append(airlineIdentifier)
				.append(stockHolderCode)
				.append(documentType)
				.append(documentSubType)				
				.toString().hashCode();
		 }
		return hashCodePk;
	}
	 /**
	  * Compare two object and return boolean. Written for comparing two VOS
	  * @param other
	  * @return boolean
	  */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	/**
	 * @return the stockHolderName
	 */
	public String getStockHolderName() {
		return stockHolderName;
	}
	/**
	 * @param stockHolderName the stockHolderName to set
	 */
	public void setStockHolderName(String stockHolderName) {
		this.stockHolderName = stockHolderName;
	}
	/**
	 * @return the allocEndRange
	 */
	public String getAllocEndRange() {
		return allocEndRange;
	}
	/**
	 * @param allocEndRange the allocEndRange to set
	 */
	public void setAllocEndRange(String allocEndRange) {
		this.allocEndRange = allocEndRange;
	}
	/**
	 * @return the allocNumberOfDocuments
	 */
	public long getAllocNumberOfDocuments() {
		return allocNumberOfDocuments;
	}
	/**
	 * @param allocNumberOfDocuments the allocNumberOfDocuments to set
	 */
	public void setAllocNumberOfDocuments(long allocNumberOfDocuments) {
		this.allocNumberOfDocuments = allocNumberOfDocuments;
	}
	/**
	 * @return the allocStartRange
	 */
	public String getAllocStartRange() {
		return allocStartRange;
	}
	/**
	 * @param allocStartRange the allocStartRange to set
	 */
	public void setAllocStartRange(String allocStartRange) {
		this.allocStartRange = allocStartRange;
	}
	/**
	 * @return the avlEndRange
	 */
	public String getAvlEndRange() {
		return avlEndRange;
	}
	/**
	 * @param avlEndRange the avlEndRange to set
	 */
	public void setAvlEndRange(String avlEndRange) {
		this.avlEndRange = avlEndRange;
	}
	/**
	 * @return the avlNumberOfDocuments
	 */
	public long getAvlNumberOfDocuments() {
		return avlNumberOfDocuments;
	}
	/**
	 * @param avlNumberOfDocuments the avlNumberOfDocuments to set
	 */
	public void setAvlNumberOfDocuments(long avlNumberOfDocuments) {
		this.avlNumberOfDocuments = avlNumberOfDocuments;
	}
	/**
	 * @return the avlStartRange
	 */
	public String getAvlStartRange() {
		return avlStartRange;
	}
	/**
	 * @param avlStartRange the avlStartRange to set
	 */
	public void setAvlStartRange(String avlStartRange) {
		this.avlStartRange = avlStartRange;
	}
	/**
	 * @return the allocatedRange
	 */
	public String getAllocatedRange() {
		return allocatedRange;
	}
	/**
	 * @param allocatedRange the allocatedRange to set
	 */
	public void setAllocatedRange(String allocatedRange) {
		this.allocatedRange = allocatedRange;
	}
	/**
	 * @return the availableRange
	 */
	public String getAvailableRange() {
		return availableRange;
	}
	/**
	 * @param availableRange the availableRange to set
	 */
	public void setAvailableRange(String availableRange) {
		this.availableRange = availableRange;
	}
	/**
	 * @return the usedEndRange
	 */
	public String getUsedEndRange() {
		return usedEndRange;
	}
	/**
	 * @param usedEndRange the usedEndRange to set
	 */
	public void setUsedEndRange(String usedEndRange) {
		this.usedEndRange = usedEndRange;
	}
	/**
	 * @return the usedRange
	 */
	public String getUsedRange() {
		return usedRange;
	}
	/**
	 * @param usedRange the usedRange to set
	 */
	public void setUsedRange(String usedRange) {
		this.usedRange = usedRange;
	}
	/**
	 * @return the usedStartRange
	 */
	public String getUsedStartRange() {
		return usedStartRange;
	}
	/**
	 * @param usedStartRange the usedStartRange to set
	 */
	public void setUsedStartRange(String usedStartRange) {
		this.usedStartRange = usedStartRange;
	}
	/**
	 * @return the fromStockHolderCode
	 */
	public String getFromStockHolderCode() {
		return fromStockHolderCode;
	}
	/**
	 * @param fromStockHolderCode the fromStockHolderCode to set
	 */
	public void setFromStockHolderCode(String fromStockHolderCode) {
		this.fromStockHolderCode = fromStockHolderCode;
	}
	/**
	 * @return the usedNumberOfDocuments
	 */
	public long getUsedNumberOfDocuments() {
		return usedNumberOfDocuments;
	}
	/**
	 * @param usedNumberOfDocuments the usedNumberOfDocuments to set
	 */
	public void setUsedNumberOfDocuments(long usedNumberOfDocuments) {
		this.usedNumberOfDocuments = usedNumberOfDocuments;
	}
	/**
	 * @return the awbPrefix
	 */
	public String getAwbPrefix() {
		return awbPrefix;
	}
	/**
	 * @param awbPrefix the awbPrefix to set
	 */
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
}
