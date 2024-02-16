/*
 * StockRequestFilterVO.java Created on Aug 25, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.List;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-1366
 *
 */
public class StockRequestFilterVO extends AbstractVO{
	/**
	 * Flag ALL
	 */
	public static final String FLAG_ALL="ALL";
	
	/**
	 * Company Code
	 */
	private String companyCode;
	/**
	 * Request Reference number
	 */
	private String requestRefNumber;
	/**
	 * Request status
	 */
	private String status;
	/**
	 * Stock holder code
	 */
	private String stockHolderCode;
	/**
	 * Stock holder Type
	 */
	private String stockHolderType;
	/**
	 * Stock Control For
	 */
	private String stockControlFor;
	
	/**
	 * Document type
	 */
	private String documentType;
	/**
	 * Document sub type
	 */
	private String documentSubType;
	/**
	 * Flag for indicating manual stock for physical document
	 */
	private boolean isManual;
	/**
	 * From date
	 */
	private LocalDate fromDate;
	/**
	 * To date
	 */
	private LocalDate toDate;
	/**
	 * Approver Code
	 */
	private String approver;
	/**
	 * Flag for indicating whether the call is from allocate screen or not
	 */
	private boolean isAllocateCall;
	/**
	 * For #102543
	 */
	private String airlineIdentifier;
	
	/**
	 * operation
	 */
	private String operation;
	
	/* added by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-20959 and ScreenId: STK016
	 */
	private int totalRecords;
	
	 //Added as part of ICRD-105821
    private String privilegeLevelType;

    private String privilegeLevelValue;
    
    private String privilegeRule;
    private int pageSize;
	
    private List<String> requestCreatedBy;
	/**
	 * @return Returns the isAllocateCall.
	 */
	public boolean isAllocateCall() {
		return isAllocateCall;
	}
	/**
	 * @param isAllocateCall The isAllocateCall to set.
	 */
	public void setAllocateCall(boolean isAllocateCall) {
		this.isAllocateCall = isAllocateCall;
	}
	/**
	 * @return Returns the approver.
	 */
	public String getApprover() {
		return approver;
	}
	/**
	 * @param approver The approver to set.
	 */
	public void setApprover(String approver) {
		this.approver = approver;
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
	 * Default Constructor
	 *
	 */   
	public StockRequestFilterVO(){
		
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
	 * @return Returns the requestRefNumber.
	 */
	public String getRequestRefNumber() {
		return requestRefNumber;
	}
	/**
	 * @param requestRefNumber The requestRefNumber to set.
	 */
	public void setRequestRefNumber(String requestRefNumber) {
		this.requestRefNumber = requestRefNumber;
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
	 * @return Returns the stockControlFor.
	 */
	public String getStockControlFor() {
		return stockControlFor;
	}
	/**
	 * @param stockControlFor The stockControlFor to set.
	 */
	public void setStockControlFor(String stockControlFor) {
		this.stockControlFor = stockControlFor;
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
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	
	//added by A-5216
	/**
	 * @param totalRecords.
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	/**
	 * @return Returns the totalRecords.
	 */
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
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	
	public void setRequestCreatedBy(List<String> requestCreatedBy) {
		this.requestCreatedBy = requestCreatedBy;
	}
	public List<String> getRequestCreatedBy() {
		return requestCreatedBy;
	}
	
}
