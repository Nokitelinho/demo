/*
 * MissingStockVO.java Created on Mar 21, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-4443
 *
 */

public class MissingStockVO extends AbstractVO {

	private String companyCode;

	private String stockHolderCode;

	private String documentType;

	private String documentSubType;

	private String sequenceNumber;

	private String missingStartRange;

	private String missingEndRange;

	private long asciiMissingStartRange;

	private long asciiMissingEndRange;

	private String numberOfDocs;

	private String operationFlag;
	
	private String missingRemarks;

	public static final String OPERATION_FLAG_NOOPERATION = "NOOP";

	/**
	 * Default Constructor
	 * 
	 */
	public MissingStockVO() {

	}
	
	/**
	 * method for copying
	 * @param missingStockVO
	 */
	public MissingStockVO(MissingStockVO missingStockVO) {
		this.companyCode = missingStockVO.getCompanyCode();

		this.stockHolderCode = missingStockVO.getStockHolderCode();

		this.documentType = missingStockVO.getDocumentType();

		this.documentSubType = missingStockVO.getDocumentSubType();

		this.sequenceNumber = missingStockVO.getSequenceNumber();

		this.missingStartRange = missingStockVO.getMissingStartRange();

		this.missingEndRange = missingStockVO.getMissingEndRange();

		this.asciiMissingStartRange = missingStockVO
				.getAsciiMissingStartRange();

		this.asciiMissingEndRange = missingStockVO.getAsciiMissingEndRange();

		this.numberOfDocs = missingStockVO.getNumberOfDocs();

		this.operationFlag = missingStockVO.getOperationFlag();
		
		this.missingRemarks=missingStockVO.getMissingRemarks();

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
	 * @return the numberOfDocs
	 */
	public String getNumberOfDocs() {
		return numberOfDocs;
	}

	/**
	 * @param numberOfDocs the numberOfDocs to set
	 */
	public void setNumberOfDocs(String numberOfDocs) {
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

}
