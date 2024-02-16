/*
 * InvoiceSettlementVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;



import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class InvoiceSettlementVO extends AbstractVO {

    private String companyCode;
    private String gpaCode;
    private String invoiceNumber; 
    private Money amountInContractCurrency;
    private String contractCurrencyCode;
    private Money amountInSettlementCurrency;
    private String settlementCurrencyCode;
    private Money amountAlreadySettled;
    private Money currentSettlingAmount;
    private String settlementStatus;
    private String operationFlag;
    private LocalDate lastUpdatedTime;
    private String lastUpdatedUser;
    //added for CR ICRD 7316
    private String gpaName;
    private String billingPeriod;
    private Money dueAmount;
    private String billingCurrencyCode;
    private int settlementSequenceNumber;
	private int serialNumber;
	private String settlementId;
	private String remarks;
	private Boolean isDeleted;
    private LocalDate billingPeriodTo;
    private int invSerialNumber;
    //a-7531
    private String mailbagID;
    private long mailsequenceNum;
    private double tolerancePercentage;
    private double settlementValue;
    private double settlementMaxValue;
    private String settlementLevel;
    private String caseClosed;
    private String invoiceStatus;
    private Money mcaNumber;
    private LocalDate settlementDate;
    private String chequeNo;
    private Money actualBilled;
    private Money totalBlgamt;
    private Money totalSettledamt;
    private Money settlemetAmt;
    private String mcaRefnum;
    private Money amountSettled;
    private int index;
    private long batchSeqNumber;
    private double totalBatchAmountApplied;
    private boolean fromBatchSettlementJob;
    //added by a-7531 for icrd-235819
    private String destnCode;
    private String settlementFileName;
    private String settlementFileType;
    private String flownSector;
    private double  mailRate;
    private Money mailCharge;
    private Money netAmount;
    private String orgCode;
    private Money surCharge;
    private Measure wgt;
    private Money tax;
    private String errorCode;
    private String processIdentifier;
    
    private String summaryGpa;
    private String summaryInvoiceNumber;
    
    private String fromScreen;
    
    private String settlementReferenceId ;
    private boolean invoiceNotPresent;
    private boolean gpaMismatch;
    private String batchID;
    
    
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
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
	 * @return Returns the contractCurrencyCode.
	 */
	public String getContractCurrencyCode() {
		return contractCurrencyCode;
	}
	/**
	 * @param contractCurrencyCode The contractCurrencyCode to set.
	 */
	public void setContractCurrencyCode(String contractCurrencyCode) {
		this.contractCurrencyCode = contractCurrencyCode;
	}
	
	/**
	 * @return Returns the amountAlreadySettled.
	 */
	public Money getAmountAlreadySettled() {
		return amountAlreadySettled;
	}
	/**
	 * @param amountAlreadySettled The amountAlreadySettled to set.
	 */
	public void setAmountAlreadySettled(Money amountAlreadySettled) {
		this.amountAlreadySettled = amountAlreadySettled;
	}
	/**
	 * @return Returns the amountInContractCurrency.
	 */
	public Money getAmountInContractCurrency() {
		return amountInContractCurrency;
	}
	/**
	 * @param amountInContractCurrency The amountInContractCurrency to set.
	 */
	public void setAmountInContractCurrency(Money amountInContractCurrency) {
		this.amountInContractCurrency = amountInContractCurrency;
	}
	/**
	 * @return Returns the amountInSettlementCurrency.
	 */
	public Money getAmountInSettlementCurrency() {
		return amountInSettlementCurrency;
	}
	/**
	 * @param amountInSettlementCurrency The amountInSettlementCurrency to set.
	 */
	public void setAmountInSettlementCurrency(Money amountInSettlementCurrency) {
		this.amountInSettlementCurrency = amountInSettlementCurrency;
	}
	/**
	 * @return Returns the currentSettlingAmount.
	 */
	public Money getCurrentSettlingAmount() {
		return currentSettlingAmount;
	}
	/**
	 * @param currentSettlingAmount The currentSettlingAmount to set.
	 */
	public void setCurrentSettlingAmount(Money currentSettlingAmount) {
		this.currentSettlingAmount = currentSettlingAmount;
	}
	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * @return Returns the settlementCurrencyCode.
	 */
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}
	/**
	 * @param settlementCurrencyCode The settlementCurrencyCode to set.
	 */
	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}
	/**
	 * @return Returns the settlementStatus.
	 */
	public String getSettlementStatus() {
		return settlementStatus;
	}
	/**
	 * @param settlementStatus The settlementStatus to set.
	 */
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	/**
	 * @return the gpaName
	 */
	public String getGpaName() {
		return gpaName;
	}
	/**
	 * @param gpaName the gpaName to set
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}
	/**
	 * @return the billingPeriod
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}
	/**
	 * @param billingPeriod the billingPeriod to set
	 */
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	/**
	 * @return the dueAmount
	 */
	public Money getDueAmount() {
		return dueAmount;
	}
	/**
	 * @param dueAmount the dueAmount to set
	 */
	public void setDueAmount(Money dueAmount) {
		this.dueAmount = dueAmount;
	}
	/**
	 * @return the billingCurrencyCode
	 */
	public String getBillingCurrencyCode() {
		return billingCurrencyCode;
	}
	/**
	 * @param billingCurrencyCode the billingCurrencyCode to set
	 */
	public void setBillingCurrencyCode(String billingCurrencyCode) {
		this.billingCurrencyCode = billingCurrencyCode;
	}
	/**
	 * @return the settlementSequenceNumber
	 */
	public int getSettlementSequenceNumber() {
		return settlementSequenceNumber;
	}
	/**
	 * @param settlementSequenceNumber the settlementSequenceNumber to set
	 */
	public void setSettlementSequenceNumber(int settlementSequenceNumber) {
		this.settlementSequenceNumber = settlementSequenceNumber;
	}
	/**
	 * @return the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the settlementId
	 */
	public String getSettlementId() {
		return settlementId;
	}
	/**
	 * @param settlementId the settlementId to set
	 */
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
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
	 * @return the isDeleted
	 */
	public Boolean isDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * @return the billingPeriodFrm
	 */
	public LocalDate getBillingPeriodTo() {
		return billingPeriodTo;
	}
	/**
	 * @param billingPeriodFrm the billingPeriodFrm to set
	 */
	public void setBillingPeriodTo(LocalDate billingPeriodTo) {
		this.billingPeriodTo = billingPeriodTo;
	}
	/**
	 * 	Getter for invSerialNumber 
	 *	Added by : A-6991 on 22-Sep-2017
	 * 	Used for :
	 */
	public int getInvSerialNumber() {
		return invSerialNumber;
	}
	/**
	 *  @param invSerialNumber the invSerialNumber to set
	 * 	Setter for invSerialNumber 
	 *	Added by : A-6991 on 22-Sep-2017
	 * 	Used for :
	 */
	public void setInvSerialNumber(int invSerialNumber) {
		this.invSerialNumber = invSerialNumber;
	}
	public String getMailbagID() {
		return mailbagID;
	}
	public void setMailbagID(String mailbagID) {
		this.mailbagID = mailbagID;
	}
	public long getMailsequenceNum() {
		return mailsequenceNum;
	}
	public void setMailsequenceNum(long mailsequenceNum) {
		this.mailsequenceNum = mailsequenceNum;
	}
	public double getTolerancePercentage() {
		return tolerancePercentage;
	}
	public void setTolerancePercentage(double tolerancePercentage) {
		this.tolerancePercentage = tolerancePercentage;
	}
	public double getSettlementValue() {
		return settlementValue;
	}
	public void setSettlementValue(double settlementValue) {
		this.settlementValue = settlementValue;
	}
	public double getSettlementMaxValue() {
		return settlementMaxValue;
	}
	public void setSettlementMaxValue(double settlementMaxValue) {
		this.settlementMaxValue = settlementMaxValue;
	}
	
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public Money getActualBilled() {
		return actualBilled;
	}
	public void setActualBilled(Money actualBilled) {
		this.actualBilled = actualBilled;
	}
	public Money getMcaNumber() {
		return mcaNumber;
	}
	public void setMcaNumber(Money mcaNumber) {
		this.mcaNumber = mcaNumber;
	}
	public Money getTotalBlgamt() {
		return totalBlgamt;
	}
	public void setTotalBlgamt(Money totalBlgamt) {
		this.totalBlgamt = totalBlgamt;
	}
	public Money getTotalSettledamt() {
		return totalSettledamt;
	}
	public void setTotalSettledamt(Money totalSettledamt) {
		this.totalSettledamt = totalSettledamt;
	}
	public Money getSettlemetAmt() {
		return settlemetAmt;
	}
	public void setSettlemetAmt(Money settlemetAmt) {
		this.settlemetAmt = settlemetAmt;
	}
	public String getMcaRefnum() {
		return mcaRefnum;
	}
	public void setMcaRefnum(String mcaRefnum) {
		this.mcaRefnum = mcaRefnum;
	}
	public String getCaseClosed() {
		return caseClosed;
	}
	public void setCaseClosed(String caseClosed) {
		this.caseClosed = caseClosed;
	}
	public Money getAmountSettled() {
		return amountSettled;
	}
	public void setAmountSettled(Money amountSettled) {
		this.amountSettled = amountSettled;
	}
	public String getSettlementLevel() {
		return settlementLevel;
	}
	public void setSettlementLevel(String settlementLevel) {
		this.settlementLevel = settlementLevel;
	}
	/**
	 * 	Getter for destnCode 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public String getDestnCode() {
		return destnCode;
	}
	/**
	 *  @param destnCode the destnCode to set
	 * 	Setter for destnCode 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setDestnCode(String destnCode) {
		this.destnCode = destnCode;
	}
	/**
	 * 	Getter for tax 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public Money getTax() {
		return tax;
	}
	/**
	 *  @param tax the tax to set
	 * 	Setter for tax 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setTax(Money tax) {
		this.tax = tax;
	}
	
	/**
	 * 	Getter for surCharge 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public Money getSurCharge() {
		return surCharge;
	}
	/**
	 *  @param surCharge the surCharge to set
	 * 	Setter for surCharge 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setSurCharge(Money surCharge) {
		this.surCharge = surCharge;
	}
	/**
	 * 	Getter for orgCode 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 *  @param orgCode the orgCode to set
	 * 	Setter for orgCode 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * 	Getter for netAmount 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public Money getNetAmount() {
		return netAmount;
	}
	/**
	 *  @param netAmount the netAmount to set
	 * 	Setter for netAmount 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setNetAmount(Money netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 * 	Getter for mailCharge 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public Money getMailCharge() {
		return mailCharge;
	}
	/**
	 *  @param mailCharge the mailCharge to set
	 * 	Setter for mailCharge 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setMailCharge(Money mailCharge) {
		this.mailCharge = mailCharge;
	}
	/**
	 * 	Getter for mailRate 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public double getMailRate() {
		return mailRate;
	}
	/**
	 *  @param mailRate the mailRate to set
	 * 	Setter for mailRate 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setMailRate(double mailRate) {
		this.mailRate = mailRate;
	}
	/**
	 * 	Getter for flownSector 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public String getFlownSector() {
		return flownSector;
	}
	/**
	 *  @param flownSector the flownSector to set
	 * 	Setter for flownSector 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setFlownSector(String flownSector) {
		this.flownSector = flownSector;
	}
	/**
	 * 	Getter for settlementFileName 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public String getSettlementFileName() {
		return settlementFileName;
	}
	/**
	 *  @param settlementFileName the settlementFileName to set
	 * 	Setter for settlementFileName 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setSettlementFileName(String settlementFileName) {
		this.settlementFileName = settlementFileName;
	}
	/**
	 * 	Getter for settlementFileType 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public String getSettlementFileType() {
		return settlementFileType;
	}
	/**
	 *  @param settlementFileType the settlementFileType to set
	 * 	Setter for settlementFileType 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setSettlementFileType(String settlementFileType) {
		this.settlementFileType = settlementFileType;
	}
	/**
	 * 	Getter for wgt 
	 *	Added by : A-7531 on 15-Jan-2019
	 * 	Used for :
	 */
	public Measure getWgt() {
		return wgt;
	}
	/**
	 *  @param wgt the wgt to set
	 * 	Setter for wgt 
	 *	Added by : A-7531 on 15-Jan-2019
	 * 	Used for :
	 */
	public void setWgt(Measure wgt) {
		this.wgt = wgt;
	}
	/**
	 * 	Getter for settlementFileType 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 *  @param settlementFileType the settlementFileType to set
	 * 	Setter for settlementFileType 
	 *	Added by : A-7531 on 14-Jan-2019
	 * 	Used for :
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * 	Getter for processIdentifier 
	 *	Added by : A-7531 on 21-Jan-2019
	 * 	Used for :
	 */
	public String getProcessIdentifier() {
		return processIdentifier;
	}
	/**
	 *  @param processIdentifier the processIdentifier to set
	 * 	Setter for processIdentifier 
	 *	Added by : A-7531 on 21-Jan-2019
	 * 	Used for :
	 */
	public void setProcessIdentifier(String processIdentifier) {
		this.processIdentifier = processIdentifier;
	}
	/**
	 * 	Getter for summaryGpa 
	 *	Added by : A-7531 on 22-Jan-2019
	 * 	Used for :
	 */
	public String getSummaryGpa() {
		return summaryGpa;
	}
	/**
	 *  @param summaryGpa the summaryGpa to set
	 * 	Setter for summaryGpa 
	 *	Added by : A-7531 on 22-Jan-2019
	 * 	Used for :
	 */
	public void setSummaryGpa(String summaryGpa) {
		this.summaryGpa = summaryGpa;
	}

	/**
	 * 	Getter for summaryInvoiceNumber 
	 *	Added by : A-7531 on 22-Jan-2019
	 * 	Used for :
	 */
	public String getSummaryInvoiceNumber() {
		return summaryInvoiceNumber;
	}
	/**
	 *  @param summaryInvoiceNumber the summaryInvoiceNumber to set
	 * 	Setter for summaryInvoiceNumber 
	 *	Added by : A-7531 on 22-Jan-2019
	 * 	Used for :
	 */
	public void setSummaryInvoiceNumber(String summaryInvoiceNumber) {
		this.summaryInvoiceNumber = summaryInvoiceNumber;
	}
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	public String getSettlementReferenceId() {
		return settlementReferenceId;
	}
	public void setSettlementReferenceId(String settlementReferenceId) {
		this.settlementReferenceId = settlementReferenceId;
	}
	
	public long getBatchSeqNumber() {
		return batchSeqNumber;
	}
	public void setBatchSeqNumber(long batchSeqNumber) {
		this.batchSeqNumber = batchSeqNumber;
	}
	public double getTotalBatchAmountApplied() {
		return totalBatchAmountApplied;
	}
	public void setTotalBatchAmountApplied(double totalBatchAmountApplied) {
		this.totalBatchAmountApplied = totalBatchAmountApplied;
	}
	public boolean isFromBatchSettlementJob() {
		return fromBatchSettlementJob;
	}
	public void setFromBatchSettlementJob(boolean fromBatchSettlementJob) {
		this.fromBatchSettlementJob = fromBatchSettlementJob;
	}
	public boolean isInvoiceNotPresent() {
		return invoiceNotPresent;
	}
	public void setInvoiceNotPresent(boolean invoiceNotPresent) {
		this.invoiceNotPresent = invoiceNotPresent;
	}
	public boolean isGpaMismatch() {
		return gpaMismatch;
	}
	public void setGpaMismatch(boolean gpaMismatch) {
		this.gpaMismatch = gpaMismatch;
	}
	public String isBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
    
}
