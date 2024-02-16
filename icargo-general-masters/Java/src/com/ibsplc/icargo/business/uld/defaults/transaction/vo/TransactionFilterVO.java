/*
 * TransactionFilterVO.java Created on Jan 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class TransactionFilterVO   extends AbstractVO implements Serializable {
    
	/**
     * Transaction Status To be Returned
	 */
    public static final String TRANSACTION_TO_BE_RETURNED_STATUS = "T";
    /**
     * Transaction Status To be Invoiced
     */
    public static final String TRANSACTION_TO_BE_INVOICED_STATUS = "R";
    /**
     * Transaction Status Invoiced
     */
    public static final String TRANSACTION_INVOICED_STATUS = "I";
    
    public static final String TRANSACTION_TYPE_ALL = "ALL";
    private String companyCode;
    private String uldNumber;
    private String accessoryCode;
    private String uldTypeCode;
//Transaction Types are Loan/Borrow    
    private String transactionType;
//Transaction Status are To be Returned/Returned and not Invoiced/Returned and Invoiced    
    private String transactionStatus;
    private String partyType;
    //private String partyCode;
    private String fromPartyCode;
    private String toPartyCode;
//Station at which Loan/Borrow happened    
    private String transactionStationCode;
//  Station at which Return happened    
    private String returnedStationCode;
//For Loan/Borrow  and also for Return 
    private String strTxnFromDate;
    private String strTxnToDate;
    private String strReturnFromDate;
    private String strReturnToDate;
    private LocalDate txnFromDate;
    private LocalDate txnToDate;
    private LocalDate returnFromDate;
    private LocalDate returnToDate;
    //for time fields
    private String strTxnFrmTime;
    private String strTxnToTime;
    private String strRetFrmTime;
    private String strRetToTime;
    //Added by Sowmya for AirNZ309
    private String location;
    private String isAgreementListingRequired;
    //Sowmya ends
    
    //This is for Server Use to fill collection of ULDs
    private Collection<String> uldNumbers;
    
    private int pageNumber;
      
    private String transactionRefNumber;
    private  Collection<Integer> transactionRefNumbers;
    private String isLoanUcrPrint;
    
//  added by a-3045 for CR QF1013 starts
    //added for MUC Tracking
    private String mucReferenceNumber;
    private LocalDate mucDate;
    private String strMucDate;
    private String mucIataStatus;
    //added by a-3045 for CR QF1013 ends
    
    //added by a-3045 for CR QF1142 ends
    private String mucStatus;
    //added by a-3045 for CR QF11142 ends
    
    // added by paulson for transaction details report
    private String NoOfMovements;
    private String NoOfLoanTxns;
    private String NoOfTimesDmgd;
    private String NoOfTimesRepaired;
    
    //added by a-3278 for bug 18347 on 05Oct08
    private String desStation;
    //a-3278 ends
    
    //added by a-3045 for bug 26528 starts
    private String prefixControlReceiptNo;
    private String midControlReceiptNo;
    private String controlReceiptNo;
    //added by a-3045 for bug 26528 ends
    private int totalRecord;
    private String supportInfo;
    private String toPartyName;
    private String canUseReturnCRNForUCR;
    private String leaseOrReturn;
    
    
    
	/**
	 * @return the totalRecord
	 */
	public int getTotalRecord() {
		return totalRecord;
	}
	/**
	 * @param totalRecord the totalRecord to set
	 */
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	/**
	 * @return Returns the isLoanUcrPrint.
	 */
	public String getIsLoanUcrPrint() {
		return isLoanUcrPrint;
	}
	/**
	 * @param isLoanUcrPrint The isLoanUcrPrint to set.
	 */
	public void setIsLoanUcrPrint(String isLoanUcrPrint) {
		this.isLoanUcrPrint = isLoanUcrPrint;
	}
	/**
	 * @return Returns the transactionRefNumbers.
	 */
	public Collection<Integer> getTransactionRefNumbers() {
		return transactionRefNumbers;
	}
	/**
	 * @param transactionRefNumbers The transactionRefNumbers to set.
	 */
	public void setTransactionRefNumbers(Collection<Integer> transactionRefNumbers) {
		this.transactionRefNumbers = transactionRefNumbers;
	}
	public String getTransactionRefNumber() {
		return transactionRefNumber;
	}
	public void setTransactionRefNumber(String transactionRefNumber) {
		this.transactionRefNumber = transactionRefNumber;
	}
	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return this.pageNumber;
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
	public Collection<String> getUldNumbers() {
		return uldNumbers;
	}
	/**
	 * 
	 * @param uldNumbers
	 */
	public void setUldNumbers(Collection<String> uldNumbers) {
		this.uldNumbers = uldNumbers;
	}
	/**
	 * @return Returns the accessoryCode.
	 */
	public String getAccessoryCode() {
		return accessoryCode;
	}
	/**
	 * @param accessoryCode The accessoryCode to set.
	 */
	public void setAccessoryCode(String accessoryCode) {
		this.accessoryCode = accessoryCode;
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
	 * @return Returns the partyType.
	 */
	public String getPartyType() {
		return partyType;
	}
	/**
	 * @param partyType The partyType to set.
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	/**
	 * @return Returns the returnedStationCode.
	 */
	public String getReturnedStationCode() {
		return returnedStationCode;
	}
	/**
	 * @param returnedStationCode The returnedStationCode to set.
	 */
	public void setReturnedStationCode(String returnedStationCode) {
		this.returnedStationCode = returnedStationCode;
	}
	
	/**
	 * @return Returns the transactionStationCode.
	 */
	public String getTransactionStationCode() {
		return transactionStationCode;
	}
	/**
	 * @param transactionStationCode The transactionStationCode to set.
	 */
	public void setTransactionStationCode(String transactionStationCode) {
		this.transactionStationCode = transactionStationCode;
	}
	/**
	 * @return Returns the transactionStatus.
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * @param transactionStatus The transactionStatus to set.
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	/**
	 * @return Returns the transactionType.
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return Returns the uldTypeCode.
	 */
	public String getUldTypeCode() {
		return uldTypeCode;
	}
	/**
	 * @param uldTypeCode The uldTypeCode to set.
	 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
	}
	/**
	 * @return Returns the returnFromDate.
	 */
	public LocalDate getReturnFromDate() {
		return this.returnFromDate;
	}
	/**
	 * @param returnFromDate The returnFromDate to set.
	 */
	public void setReturnFromDate(LocalDate returnFromDate) {
		this.returnFromDate = returnFromDate;
	}
	/**
	 * @return Returns the returnToDate.
	 */
	public LocalDate getReturnToDate() {
		return this.returnToDate;
	}
	/**
	 * @param returnToDate The returnToDate to set.
	 */
	public void setReturnToDate(LocalDate returnToDate) {
		this.returnToDate = returnToDate;
	}
	/**
	 * @return Returns the strReturnFromDate.
	 */
	public String getStrReturnFromDate() {
		return this.strReturnFromDate;
	}
	/**
	 * @param strReturnFromDate The strReturnFromDate to set.
	 */
	public void setStrReturnFromDate(String strReturnFromDate) {
		this.strReturnFromDate = strReturnFromDate;
	}
	/**
	 * @return Returns the strReturnToDate.
	 */
	public String getStrReturnToDate() {
		return this.strReturnToDate;
	}
	/**
	 * @param strReturnToDate The strReturnToDate to set.
	 */
	public void setStrReturnToDate(String strReturnToDate) {
		this.strReturnToDate = strReturnToDate;
	}
	/**
	 * @return Returns the strTxnFromDate.
	 */
	public String getStrTxnFromDate() {
		return this.strTxnFromDate;
	}
	/**
	 * @param strTxnFromDate The strTxnFromDate to set.
	 */
	public void setStrTxnFromDate(String strTxnFromDate) {
		this.strTxnFromDate = strTxnFromDate;
	}
	/**
	 * @return Returns the strTxnToDate.
	 */
	public String getStrTxnToDate() {
		return this.strTxnToDate;
	}
	/**
	 * @param strTxnToDate The strTxnToDate to set.
	 */
	public void setStrTxnToDate(String strTxnToDate) {
		this.strTxnToDate = strTxnToDate;
	}
	/**
	 * @return Returns the txnFromDate.
	 */
	public LocalDate getTxnFromDate() {
		return this.txnFromDate;
	}
	/**
	 * @param txnFromDate The txnFromDate to set.
	 */
	public void setTxnFromDate(LocalDate txnFromDate) {
		this.txnFromDate = txnFromDate;
	}
	/**
	 * @return Returns the txnToDate.
	 */
	public LocalDate getTxnToDate() {
		return this.txnToDate;
	}
	/**
	 * @param txnToDate The txnToDate to set.
	 */
	public void setTxnToDate(LocalDate txnToDate) {
		this.txnToDate = txnToDate;
	}
	
	//@Column(name = "")
	/**
	 * @return Returns the fromPartyCode.
	 */
	public String getFromPartyCode() {
		return fromPartyCode;
	}
	/**
	 * @param fromPartyCode The fromPartyCode to set.
	 */
	public void setFromPartyCode(String fromPartyCode) {
		this.fromPartyCode = fromPartyCode;
	}
	/**
	 * @return Returns the toPartyCode.
	 */
	public String getToPartyCode() {
		return toPartyCode;
	}
	/**
	 * @param toPartyCode The toPartyCode to set.
	 */
	public void setToPartyCode(String toPartyCode) {
		this.toPartyCode = toPartyCode;
	}
	/**
	 * 
	 * @return strRetFrmTime
	 */
	public String getStrRetFrmTime() {
		return strRetFrmTime;
	}
	/**
	 * 
	 * @param strRetFrmTime
	 */
	public void setStrRetFrmTime(String strRetFrmTime) {
		this.strRetFrmTime = strRetFrmTime;
	}
	/**
	 * 
	 * @return
	 */
	public String getStrRetToTime() {
		return strRetToTime;
	}
	/**
	 * 
	 * @param strRetToTime
	 */
	public void setStrRetToTime(String strRetToTime) {
		this.strRetToTime = strRetToTime;
	}
	/**
	 * 
	 * @return
	 */
	public String getStrTxnFrmTime() {
		return strTxnFrmTime;
	}
	/**
	 * 
	 * @param strTxnFrmTime
	 */
	public void setStrTxnFrmTime(String strTxnFrmTime) {
		this.strTxnFrmTime = strTxnFrmTime;
	}
	/**
	 * 
	 * @return
	 */
	public String getStrTxnToTime() {
		return strTxnToTime;
	}
	/**
	 * 
	 * @param strTxnToTime
	 */
	public void setStrTxnToTime(String strTxnToTime) {
		this.strTxnToTime = strTxnToTime;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the mucDate
	 */
	public LocalDate getMucDate() {
		return mucDate;
	}
	/**
	 * @param mucDate the mucDate to set
	 */
	public void setMucDate(LocalDate mucDate) {
		this.mucDate = mucDate;
	}
	/**
	 * @return the mucIataStatus
	 */
	public String getMucIataStatus() {
		return mucIataStatus;
	}
	/**
	 * @param mucIataStatus the mucIataStatus to set
	 */
	public void setMucIataStatus(String mucIataStatus) {
		this.mucIataStatus = mucIataStatus;
	}
	/**
	 * @return the mucReferenceNumber
	 */
	public String getMucReferenceNumber() {
		return mucReferenceNumber;
	}
	/**
	 * @param mucReferenceNumber the mucReferenceNumber to set
	 */
	public void setMucReferenceNumber(String mucReferenceNumber) {
		this.mucReferenceNumber = mucReferenceNumber;
	}
	/**
	 * @return the strMucDate
	 */
	public String getStrMucDate() {
		return strMucDate;
	}
	/**
	 * @param strMucDate the strMucDate to set
	 */
	public void setStrMucDate(String strMucDate) {
		this.strMucDate = strMucDate;
	}
	/**
	 * @return the mucStatus
	 */
	public String getMucStatus() {
		return mucStatus;
	}
	/**
	 * @param mucStatus the mucStatus to set
	 */
	public void setMucStatus(String mucStatus) {
		this.mucStatus = mucStatus;
	}
	public String getNoOfLoanTxns() {
		return NoOfLoanTxns;
	}
	public void setNoOfLoanTxns(String noOfLoanTxns) {
		NoOfLoanTxns = noOfLoanTxns;
	}
	public String getNoOfMovements() {
		return NoOfMovements;
	}
	public void setNoOfMovements(String noOfMovements) {
		NoOfMovements = noOfMovements;
	}
	public String getNoOfTimesDmgd() {
		return NoOfTimesDmgd;
	}
	public void setNoOfTimesDmgd(String noOfTimesDmgd) {
		NoOfTimesDmgd = noOfTimesDmgd;
	}
	public String getNoOfTimesRepaired() {
		return NoOfTimesRepaired;
	}
	public void setNoOfTimesRepaired(String noOfTimesRepaired) {
		NoOfTimesRepaired = noOfTimesRepaired;
	}
	/**
	 * @return the desStation
	 */
	public String getDesStation() {
		return desStation;
	}
	/**
	 * @param desStation the desStation to set
	 */
	public void setDesStation(String desStation) {
		this.desStation = desStation;
	}
	/**
	 * @return the controlReceiptNo
	 */
	public String getControlReceiptNo() {
		return controlReceiptNo;
	}
	/**
	 * @param controlReceiptNo the controlReceiptNo to set
	 */
	public void setControlReceiptNo(String controlReceiptNo) {
		this.controlReceiptNo = controlReceiptNo;
	}
	/**
	 * @return the midControlReceiptNo
	 */
	public String getMidControlReceiptNo() {
		return midControlReceiptNo;
	}
	/**
	 * @param midControlReceiptNo the midControlReceiptNo to set
	 */
	public void setMidControlReceiptNo(String midControlReceiptNo) {
		this.midControlReceiptNo = midControlReceiptNo;
	}
	/**
	 * @return the prefixControlReceiptNo
	 */
	public String getPrefixControlReceiptNo() {
		return prefixControlReceiptNo;
	}
	/**
	 * @param prefixControlReceiptNo the prefixControlReceiptNo to set
	 */
	public void setPrefixControlReceiptNo(String prefixControlReceiptNo) {
		this.prefixControlReceiptNo = prefixControlReceiptNo;
	}
	/**
	 * @return the supportInfo
	 */
	public String getSupportInfo() {
		return supportInfo;
	}
	/**
	 * @param supportInfo the supportInfo to set
	 */
	public void setSupportInfo(String supportInfo) {
		this.supportInfo = supportInfo;
	}
	/**
	 * @return the toPartyName
	 */
	public String getToPartyName() {
		return toPartyName;
	}
	/**
	 * @param toPartyName the toPartyName to set
	 */
	public void setToPartyName(String toPartyName) {
		this.toPartyName = toPartyName;
	}
	public String getCanUseReturnCRNForUCR() {
		return canUseReturnCRNForUCR;
	}
	public void setCanUseReturnCRNForUCR(String canUseReturnCRNForUCR) {
		this.canUseReturnCRNForUCR = canUseReturnCRNForUCR;
	}
	/**
	 * 
	 * 	Method		:	TransactionFilterVO.getLeaseOrReturn
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	getting the flag which shows if its lease or return as filter
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getLeaseOrReturn() {
		return leaseOrReturn;
	}
	/**
	 * 
	 * 	Method		:	TransactionFilterVO.setLeaseOrReturn
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	setting the flag which shows if its lease or return as filter
	 *	Parameters	:	@param leaseOrReturn 
	 *	Return type	: 	void
	 */
	public void setLeaseOrReturn(String leaseOrReturn) {
		this.leaseOrReturn = leaseOrReturn;
	}
	public String getIsAgreementListingRequired() {
		return isAgreementListingRequired;
	}
	public void setIsAgreementListingRequired(String isAgreementListingRequired) {
		this.isAgreementListingRequired = isAgreementListingRequired;
	}
	
  
}
