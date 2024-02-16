/*
 * BlacklistStockVO.java Created on Mar 21, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;


import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-4443
 *
 */


public class TransitStockVO extends AbstractVO {
	
	public static final String TRANSIT_STATUS_NOT_CONFIRMED="N";
	
	public static final String TRANSIT_STATUS_MISSING="M";
	
    public static final String TRANSIT_ACTION_ALLOCATE="A";
	
    public static final String TRANSIT_ACTION_TRANSFER="T";
    
    public static final String TRANSIT_ACTION_RETURN="R";

	private String companyCode;

	/**
	 * Stock Holder Code
	 */
	private String stockHolderCode;

	private String documentType;

	private String documentSubType;

	private String sequenceNumber;

	/**
	 * Sender Stock holder code 
	 * (Stockholder who allocates/transfers/returns)
	 */
	private String stockControlFor;

	private String actualStartRange;

	private String actualEndRange;

	private String missingStartRange;

	private String missingEndRange;
	
	private long asciiMissingStartRange;
	
	private long asciiMissingEndRange;
	
	private long missingNumberOfDocs;
	
	private long numberOfDocs;

    private String confirmStatus;

	private LocalDate confirmDate;

	private LocalDate lastUpdateTime;

	private String lastUpdateUser;

	private int airlineIdentifier;
    
	private String txnCode;
	
	private LocalDate txnDate;
	
	private boolean isManual;
	
	private String operationFlag;
	
	private String missingRemarks;
	
	private String txnRemarks;
	
	private List<MissingStockVO> missingRanges;

	/**
	 * Added as part of ICRD-15656
	 */

	private String stockHolderType;
	
	/**
	 * Default Constructor
	 *
	 */
	public TransitStockVO() {

	}
	
	/**
	 * copyVO
	 * @param transitStockVO
	 */
	public TransitStockVO(TransitStockVO transitStockVO){
		
		List<MissingStockVO> missingVOs=null;
		
		this.companyCode= transitStockVO.getCompanyCode();

		this.stockHolderCode= transitStockVO.getStockHolderCode();

		this.documentType= transitStockVO.getDocumentType();

		this.documentSubType= transitStockVO.getDocumentSubType();

		this.sequenceNumber= transitStockVO.getSequenceNumber();

		this.stockControlFor= transitStockVO.getStockControlFor();

		this.actualStartRange= transitStockVO.getActualStartRange();

		this.actualEndRange= transitStockVO.getActualEndRange();

		this.missingStartRange= transitStockVO.getMissingStartRange();

		this.missingEndRange= transitStockVO.getMissingEndRange();
		
		this.asciiMissingStartRange= transitStockVO.getAsciiMissingStartRange();
		
		this.asciiMissingEndRange= transitStockVO.getAsciiMissingEndRange();
		
		this.missingNumberOfDocs= transitStockVO.getMissingNumberOfDocs();
		
		this.numberOfDocs= transitStockVO.getNumberOfDocs();

	    this.confirmStatus= transitStockVO.getConfirmStatus();

		this.confirmDate= transitStockVO.getConfirmDate();

		this.lastUpdateTime= transitStockVO.getLastUpdateTime();

		this.lastUpdateUser= transitStockVO.getLastUpdateUser();

		this.airlineIdentifier= transitStockVO.getAirlineIdentifier();
	    
		this.txnCode= transitStockVO.getTxnCode();
		
		this.txnDate= transitStockVO.getTxnDate();
		
		this.isManual= transitStockVO.isManual();
		
		this.operationFlag= transitStockVO.getOperationFlag();
		
		this.missingRemarks=transitStockVO.getMissingRemarks();
		
		this.txnRemarks=transitStockVO.getTxnRemarks();
		
		this.stockHolderType =transitStockVO.getStockHolderType();
		
//		Setting RangeVOs
		if(transitStockVO.getMissingRanges()!=null && transitStockVO.getMissingRanges().size()>0){
			if(missingVOs==null){
				missingVOs=new ArrayList<MissingStockVO>(transitStockVO.getMissingRanges().size());	
			}
				
			for(MissingStockVO rangeVO:transitStockVO.getMissingRanges()){
				MissingStockVO copyVO=new MissingStockVO(rangeVO);
				missingVOs.add(copyVO);
				
			}
		}
		this.missingRanges=missingVOs;
	}

	/**
	 * @return the actualEndRange
	 */
	public String getActualEndRange() {
		return actualEndRange;
	}

	/**
	 * @param actualEndRange the actualEndRange to set
	 */
	public void setActualEndRange(String actualEndRange) {
		this.actualEndRange = actualEndRange;
	}

	/**
	 * @return the actualStartRange
	 */
	public String getActualStartRange() {
		return actualStartRange;
	}

	/**
	 * @param actualStartRange the actualStartRange to set
	 */
	public void setActualStartRange(String actualStartRange) {
		this.actualStartRange = actualStartRange;
	}

	/**
	 * @return the airlineIdentifier
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier the airlineIdentifier to set
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the confirmDate
	 */
	public LocalDate getConfirmDate() {
		return confirmDate;
	}

	/**
	 * @param confirmDate the confirmDate to set
	 */
	public void setConfirmDate(LocalDate confirmDate) {
		this.confirmDate = confirmDate;
	}

	/**
	 * @return the confirmStatus
	 */
	public String getConfirmStatus() {
		return confirmStatus;
	}

	/**
	 * @param confirmStatus the confirmStatus to set
	 */
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	/**
	 * @return the documentSubType
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}

	/**
	 * @param documentSubType the documentSubType to set
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}

	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

	/**
	 * @return the missingEndRange
	 */
	public String getMissingEndRange() {
		return missingEndRange;
	}

	/**
	 * @param missingEndRange the missingEndRange to set
	 */
	public void setMissingEndRange(String missingEndRange) {
		this.missingEndRange = missingEndRange;
	}

	/**
	 * @return the missingStartRange
	 */
	public String getMissingStartRange() {
		return missingStartRange;
	}

	/**
	 * @param missingStartRange the missingStartRange to set
	 */
	public void setMissingStartRange(String missingStartRange) {
		this.missingStartRange = missingStartRange;
	}

	/**
	 * @return the sequenceNumber
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the stockControlFor
	 */
	public String getStockControlFor() {
		return stockControlFor;
	}

	/**
	 * @param stockControlFor the stockControlFor to set
	 */
	public void setStockControlFor(String stockControlFor) {
		this.stockControlFor = stockControlFor;
	}

	/**
	 * @return the stockHolderCode
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode the stockHolderCode to set
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return the asciiMissingEndRange
	 */
	public long getAsciiMissingEndRange() {
		return asciiMissingEndRange;
	}

	/**
	 * @param asciiMissingEndRange the asciiMissingEndRange to set
	 */
	public void setAsciiMissingEndRange(long asciiMissingEndRange) {
		this.asciiMissingEndRange = asciiMissingEndRange;
	}

	/**
	 * @return the asciiMissingStartRange
	 */
	public long getAsciiMissingStartRange() {
		return asciiMissingStartRange;
	}

	/**
	 * @param asciiMissingStartRange the asciiMissingStartRange to set
	 */
	public void setAsciiMissingStartRange(long asciiMissingStartRange) {
		this.asciiMissingStartRange = asciiMissingStartRange;
	}

	/**
	 * @return the txnCode
	 */
	public String getTxnCode() {
		return txnCode;
	}

	/**
	 * @param txnCode the txnCode to set
	 */
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	/**
	 * @return the txnDate
	 */
	public LocalDate getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate the txnDate to set
	 */
	public void setTxnDate(LocalDate txnDate) {
		this.txnDate = txnDate;
	}

	/**
	 * @return the isManual
	 */
	public boolean isManual() {
		return isManual;
	}

	/**
	 * @param isManual the isManual to set
	 */
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}

	/**
	 * @return the missingNumberOfDocs
	 */
	public long getMissingNumberOfDocs() {
		return missingNumberOfDocs;
	}

	/**
	 * @param missingNumberOfDocs the missingNumberOfDocs to set
	 */
	public void setMissingNumberOfDocs(long missingNumberOfDocs) {
		this.missingNumberOfDocs = missingNumberOfDocs;
	}

	/**
	 * @return the missingRanges
	 */
	public List<MissingStockVO> getMissingRanges() {
		return missingRanges;
	}

	/**
	 * @param missingRanges the missingRanges to set
	 */
	public void setMissingRanges(List<MissingStockVO> missingRanges) {
		this.missingRanges = missingRanges;
	}

	/**
	 * @return the numberOfDocs
	 */
	public long getNumberOfDocs() {
		return numberOfDocs;
	}

	/**
	 * @param numberOfDocs the numberOfDocs to set
	 */
	public void setNumberOfDocs(long numberOfDocs) {
		this.numberOfDocs = numberOfDocs;
	}

	/**
	 * @return the operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag the operationFlag to set
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return the missingRemarks
	 */
	public String getMissingRemarks() {
		return missingRemarks;
	}

	/**
	 * @param missingRemarks the missingRemarks to set
	 */
	public void setMissingRemarks(String missingRemarks) {
		this.missingRemarks = missingRemarks;
	}

	/**
	 * @return the txnRemarks
	 */
	public String getTxnRemarks() {
		return txnRemarks;
	}

	/**
	 * @param txnRemarks the txnRemarks to set
	 */
	public void setTxnRemarks(String txnRemarks) {
		this.txnRemarks = txnRemarks;
	}

	/**
	 * @param stockHolderType the stockHolderType to set
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}

	/**
	 * @return the stockHolderType
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}

	
}
