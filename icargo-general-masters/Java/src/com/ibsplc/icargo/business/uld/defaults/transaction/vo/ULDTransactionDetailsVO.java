/*
 * ULDTransactionDetailsVO.java Created on Jan 5, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDTransactionDetailsVO  extends AbstractVO implements Serializable{
	/**
	 * constant for the Status to be returned
	 */
	public static final String TO_BE_RETURNED ="T";
	/**
	 * constant for the Status to be invoiced
	 */
	public static final String TO_BE_INVOICED ="R";
	/**
	 * constant for the Status invoiced
	 */
	public static final String INVOICED  ="I";
	
	public static final String AGENT  ="G";
	public static final String AIRLINE  ="A";
	
	public static final String OTHERS  ="O";

    private String companyCode;
    private String uldNumber;
    private int transactionRefNumber;
//Transaction Type will be L-Loan/B-Borrow/R-Return
    private String transactionType;
    private String uldType;
//Transaction Status will be P-Permanent/T-Temporary
    private String transactionNature;
    private String partyType;
    private String fromPartyCode;
    private String toPartyCode;
    private int fromPartyIdentifier;
    private int toPartyIdentifier;

//Station at which Loan/Borrow
    private String transactionStationCode;
    private String transationPeriod;
    private LocalDate transactionDate;
    private LocalDate leaseEndDate;
    private String transactionStatus;
    private String transactionRemark;
//  This filed will only be there for Borrow Transaction
    private String capturedRefNumber;
    private String damageStatus;
    private LocalDate returnDate;
//  Station at which Return happens
    private String returnStationCode;
    private double demurrageAmount;
    private String currency;
    private String invoiceStatus;
    private double waived;
    private double taxes;
    private String returnedBy;
    private double otherCharges;
    private double total;
//Stamping and Tracking Invoice
    private String invoiceRefNumber;
    private String paymentStatus;
    private String returnRemark;
    private String operationalFlag;
    private LocalDate uldLastUpdateTime;
	private String facilityType;
	//added as part of ICRD-232684 by A-4393 starts 
    private String remainingDayToReturn;
  //added as part of ICRD-232684 by A-4393 ends
    /*
	 * added by a-3278 for ULD771 on 21Oct08
	 * for populating the base currency along with the total demmurage
	 */
    private double totalDemmurage;
    private String baseCurrency;
    //a-3278 ends

    private String controlReceiptNumber;
    
    /*
	 * added by a-3278 for 28897 on 05Jan09
	 * a new CRN is maintained to save the latest and the old CRN seperately
	 */
    private String returnCRN;
    // a-3278 ends
   
    private int numberMonths;
    private String uldNature;

    // to get selcted VO while modify
    private int selectNumber;
    // For setting invoice Number in Repair
    private int repairSeqNumber;
    private String strTxnDate;
    private String strTxnTime;
    private String strRetDate;
    private String strLseEndDate;
    
    //Sowmya starts for AirNZ309
    private String location;
    //Sowmya ends

    // the party who is loaning the uld
    private String returnPartyCode;

    // Identifier of the party who is loaning the uld
    private int returnPartyIdentifier;


    private String agreementNumber;
    /**
	 * partyName
	 */
    private String toPartyName;
    private String fromPartyName;
    
    /**
     * Added By Asharaf for QF1015 for 
    	doing online demurrage calculation
     */
    private double demurrageRate;

    /**
	 * currOwnerCode
	 */
    private int  currOwnerCode;

    /**
	 * operationalAirlineIdentifier
	 */
    private int operationalAirlineIdentifier;
    //added for LUC
    private String txStationCode;

    private LocalDate lastUpdateTime;
    
/**
	 * @return Returns the lastUpdateUser.
	 */
    private String lastUpdateUser;

    /**
     * For Report the owner code and serialNumber are to be shown separately
     */
    private String uldOwnerCode;

    private String uldSerialNumber;  

	// added by a-3278 for CR QF1015
	private String strRetTime;

	// added by a-3278 for CR QF1015 ends

    // Added by Preet starts
    private String uldConditionCode;
    

    private String controlReceiptNumberPrefix;
    private String crnToDisplay;
    // Added by Preet ends 
    //Added by nisha for QF1018 starts
    private String poolOwnerFlag;
    //ends
//  added by a-3045 for CR QF1013 starts
    //added for MUC Tracking
    private String mucReferenceNumber;
    private LocalDate mucDate;
    private String mucIataStatus;
    //added by a-3045 for CR QF1013 ends
    private String awbNumber;
    private String emptyStatus;
    //Added by A-2408 for bugfix
    //This flag is used in client side to indicate 
    //whether a return need to be saved....
    private boolean isReturn;
    //Added by A-3415 for ICRD-114538
    private String thirdPartyFlag; 
	private String sysRtnFlag;
	private String source;
	//Added by A-4072 for CR ICRD-192300 starts
    private String originatorName;
    private String damageFlagFromScreen;
    private String damageRemark;
    private String odlnCode;
    
    private String remainingDaysToEndLease;
  //Added by A-4072 for CR ICRD-192300 end
    private String nonCarrierId;
    
    private boolean isLUCMessageRequired;
    
    private String demurrageFrequency;
    private int freeLoanPeriod;
    
	public boolean isLUCMessageRequired() {
		return isLUCMessageRequired;
	}
	public void setLUCMessageRequired(boolean isLUCMessageRequired) {
		this.isLUCMessageRequired = isLUCMessageRequired;
	}
	public String getNonCarrierId() {
		return nonCarrierId;
	}
	public void setNonCarrierId(String nonCarrierId) {
		this.nonCarrierId = nonCarrierId;
	}
	public String getUldConditionCode() {
		return uldConditionCode;
	}
	public void setUldConditionCode(String uldConditionCode) {
		this.uldConditionCode = uldConditionCode;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
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
	 * @return Returns the borrowRefNumber.
	 */
	public String getCapturedRefNumber() {
		return capturedRefNumber;
	}
	/**
	 * @param capturedRefNumber The capturedRefNumber to set.
	 */
	public void setCapturedRefNumber(String capturedRefNumber) {
		this.capturedRefNumber = capturedRefNumber;
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
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return Returns the damageStatus.
	 */
	public String getDamageStatus() {
		return damageStatus;
	}
	/**
	 * @param damageStatus The damageStatus to set.
	 */
	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}
	/**
	 * @return Returns the demurrageAmount.
	 */
	public double getDemurrageAmount() {
		return demurrageAmount;
	}
	/**
	 * @param demurrageAmount The demurrageAmount to set.
	 */
	public void setDemurrageAmount(double demurrageAmount) {
		this.demurrageAmount = demurrageAmount;
	}
	/**
	 * @return Returns the invoiceRefNumber.
	 */
	public String getInvoiceRefNumber() {
		return invoiceRefNumber;
	}
	/**
	 * @param invoiceRefNumber The invoiceRefNumber to set.
	 */
	public void setInvoiceRefNumber(String invoiceRefNumber) {
		this.invoiceRefNumber = invoiceRefNumber;
	}
	/**
	 * @return Returns the invoiceStatus.
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	/**
	 * @param invoiceStatus The invoiceStatus to set.
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	/**
	 * @return Returns the otherCharges.
	 */
	public double getOtherCharges() {
		return otherCharges;
	}
	/**
	 * @param otherCharges The otherCharges to set.
	 */
	public void setOtherCharges(double otherCharges) {
		this.otherCharges = otherCharges;
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
	 * @return Returns the paymentStatus.
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}
	/**
	 * @param paymentStatus The paymentStatus to set.
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	/**
	 * @return Returns the returnDate.
	 */
	public LocalDate getReturnDate() {
		return returnDate;
	}
	/**
	 * @param returnDate The returnDate to set.
	 */
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	/**
	 * @return Returns the returnedBy.
	 */
	public String getReturnedBy() {
		return returnedBy;
	}
	/**
	 * @param returnedBy The returnedBy to set.
	 */
	public void setReturnedBy(String returnedBy) {
		this.returnedBy = returnedBy;
	}
	/**
	 * @return Returns the returnStationCode.
	 */
	public String getReturnStationCode() {
		return returnStationCode;
	}
	/**
	 * @param returnStationCode The returnStationCode to set.
	 */
	public void setReturnStationCode(String returnStationCode) {
		this.returnStationCode = returnStationCode;
	}
	/**
	 * @return Returns the taxes.
	 */
	public double getTaxes() {
		return taxes;
	}
	/**
	 * @param taxes The taxes to set.
	 */
	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}
	/**
	 * @return Returns the total.
	 */
	public double getTotal() {
		return total;
	}
	/**
	 * @param total The total to set.
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	/**
	 * @return Returns the transactionDate.
	 */
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return Returns the transactionNature.
	 */
	public String getTransactionNature() {
		return transactionNature;
	}
	/**
	 * @param transactionNature The transactionNature to set.
	 */
	public void setTransactionNature(String transactionNature) {
		this.transactionNature = transactionNature;
	}
	/**
	 * @return Returns the transactionRefNumber.
	 */
	public int getTransactionRefNumber() {
		return transactionRefNumber;
	}
	/**
	 * @param transactionRefNumber The transactionRefNumber to set.
	 */
	public void setTransactionRefNumber(int transactionRefNumber) {
		this.transactionRefNumber = transactionRefNumber;
	}
	/**
	 * @return Returns the transactionRemark.
	 */
	public String getTransactionRemark() {
		return transactionRemark;
	}
	/**
	 * @param transactionRemark The transactionRemark to set.
	 */
	public void setTransactionRemark(String transactionRemark) {
		this.transactionRemark = transactionRemark;
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
	 * @return Returns the transationPeriod.
	 */
	public String getTransationPeriod() {
		return transationPeriod;
	}
	/**
	 * @param transationPeriod The transationPeriod to set.
	 */
	public void setTransationPeriod(String transationPeriod) {
		this.transationPeriod = transationPeriod;
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
	 * @return Returns the uldType.
	 */
	public String getUldType() {
		return uldType;
	}
	/**
	 * @param uldType The uldType to set.
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}
	/**
	 * @return Returns the waived.
	 */
	public double getWaived() {
		return waived;
	}
	/**
	 * @param waived The waived to set.
	 */
	public void setWaived(double waived) {
		this.waived = waived;
	}
	/**
	 * @return Returns the operationalFlag.
	 */
	public String getOperationalFlag() {
		return this.operationalFlag;
	}
	/**
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
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
	 * @return Returns the currOwnerCode.
	 */
	public int getCurrOwnerCode() {
		return this.currOwnerCode;
	}
	/**
	 * @param currOwnerCode The currOwnerCode to set.
	 */
	public void setCurrOwnerCode(int currOwnerCode) {
		this.currOwnerCode = currOwnerCode;
	}
	/**
	 * @return Returns the operationalAirlineIdentifier.
	 */
	public int getOperationalAirlineIdentifier() {
		return this.operationalAirlineIdentifier;
	}
	/**
	 * @param operationalAirlineIdentifier The operationalAirlineIdentifier to set.
	 */
	public void setOperationalAirlineIdentifier(int operationalAirlineIdentifier) {
		this.operationalAirlineIdentifier = operationalAirlineIdentifier;
	}

	/**
	 * @return Returns the repairSeqNumber.
	 */
	public int getRepairSeqNumber() {
		return repairSeqNumber;
	}
	/**
	 * @param repairSeqNumber The repairSeqNumber to set.
	 */
	public void setRepairSeqNumber(int repairSeqNumber) {
		this.repairSeqNumber = repairSeqNumber;
	}
	/**
	 * @return Returns the strRetDate.
	 */
	public String getStrRetDate() {
		return strRetDate;
	}
	/**
	 * @param strRetDate The strRetDate to set.
	 */
	public void setStrRetDate(String strRetDate) {
		this.strRetDate = strRetDate;
	}
	/**
	 * @return Returns the strTxnDate.
	 */
	public String getStrTxnDate() {
		return strTxnDate;
	}
	/**
	 * @param strTxnDate The strTxnDate to set.
	 */
	public void setStrTxnDate(String strTxnDate) {
		this.strTxnDate = strTxnDate;
	}
	/**
	 * @return Returns the selectNumber.
	 */
	public int getSelectNumber() {
		return this.selectNumber;
	}
	/**
	 * @param selectNumber The selectNumber to set.
	 */
	public void setSelectNumber(int selectNumber) {
		this.selectNumber = selectNumber;
	}
	/**
	 * @return Returns the returnRemark.
	 */
	public String getReturnRemark() {
		return this.returnRemark;
	}
	/**
	 * @param returnRemark The returnRemark to set.
	 */
	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}
	/**
	 * @return Returns the numberMonths.
	 */
	public int getNumberMonths() {
		return this.numberMonths;
	}
	/**
	 * @param numberMonths The numberMonths to set.
	 */
	public void setNumberMonths(int numberMonths) {
		this.numberMonths = numberMonths;
	}
	/**
	 * @return Returns the agreementNumber.
	 */
	public String getAgreementNumber() {
		return agreementNumber;
	}
	/**
	 * @param agreementNumber The agreementNumber to set.
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}
	public String getTxStationCode() {
		return txStationCode;
	}
	public void setTxStationCode(String txStationCode) {
		this.txStationCode = txStationCode;
	}

	//@Column(name = "")
	/**
	 * @return Returns the returnPartyIdentifier.
	 */
	public int getReturnPartyIdentifier() {
		return returnPartyIdentifier;
	}
	/**
	 * @param returnPartyIdentifier The returnPartyIdentifier to set.
	 */
	public void setReturnPartyIdentifier(int returnPartyIdentifier) {
		this.returnPartyIdentifier = returnPartyIdentifier;
	}
	/**
	 * @return Returns the returnPartyCode.
	 */
	public String getReturnPartyCode() {
		return returnPartyCode;
	}
	/**
	 * @param returnPartyCode The returnPartyCode to set.
	 */
	public void setReturnPartyCode(String returnPartyCode) {
		this.returnPartyCode = returnPartyCode;
	}
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
	 * @return Returns the fromPartyName.
	 */
	public String getFromPartyName() {
		return fromPartyName;
	}
	/**
	 * @param fromPartyName The fromPartyName to set.
	 */
	public void setFromPartyName(String fromPartyName) {
		this.fromPartyName = fromPartyName;
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
	 * @return Returns the toPartyName.
	 */
	public String getToPartyName() {
		return toPartyName;
	}
	/**
	 * @param toPartyName The toPartyName to set.
	 */
	public void setToPartyName(String toPartyName) {
		this.toPartyName = toPartyName;
	}
	/**
	 * @return Returns the toPartyIdentifier.
	 */
	public int getToPartyIdentifier() {
		return toPartyIdentifier;
	}
	/**
	 * @param toPartyIdentifier The toPartyIdentifier to set.
	 */
	public void setToPartyIdentifier(int toPartyIdentifier) {
		this.toPartyIdentifier = toPartyIdentifier;
	}
	/**
	 * @return Returns the fromPartyIdentifier.
	 */
	public int getFromPartyIdentifier() {
		return fromPartyIdentifier;
	}
	/**
	 * @param fromPartyIdentifier The fromPartyIdentifier to set.
	 */
	public void setFromPartyIdentifier(int fromPartyIdentifier) {
		this.fromPartyIdentifier = fromPartyIdentifier;
	}
	//@Column(name = "")
	/**
	 * @return Returns the controlReceiptNumber.
	 */
	public String getControlReceiptNumber() {
		return controlReceiptNumber;
	}
	/**
	 * @param controlReceiptNumber The controlReceiptNumber to set.
	 */
	public void setControlReceiptNumber(String controlReceiptNumber) {
		this.controlReceiptNumber = controlReceiptNumber;
	}
	/**
	 * @return Returns the uldNature.
	 */
	public String getUldNature() {
		return this.uldNature;
	}
	/**
	 * @param uldNature The uldNature to set.
	 */
	public void setUldNature(String uldNature) {
		this.uldNature = uldNature;
	}
	/**
	 * @return Returns the uldSerialNumber.
	 */
	public String getUldSerialNumber() {
		return this.uldSerialNumber;
	}
	/**
	 * @param uldSerialNumber The uldSerialNumber to set.
	 */
	public void setUldSerialNumber(String uldSerialNumber) {
		this.uldSerialNumber = uldSerialNumber;
	}
	/**
	 * @return Returns the uldOwnerCode.
	 */
	public String getUldOwnerCode() {
		return this.uldOwnerCode;
	}
	/**
	 * @param uldOwnerCode The uldOwnerCode to set.
	 */
	public void setUldOwnerCode(String uldOwnerCode) {
		this.uldOwnerCode = uldOwnerCode;
	}
	
	public LocalDate getUldLastUpdateTime() {
		return uldLastUpdateTime;
	}
	public void setUldLastUpdateTime(LocalDate uldLastUpdateTime) {
		this.uldLastUpdateTime = uldLastUpdateTime;
	}	
	public String getControlReceiptNumberPrefix() {
		return controlReceiptNumberPrefix;
	}
	public void setControlReceiptNumberPrefix(String controlReceiptNumberPrefix) {
		this.controlReceiptNumberPrefix = controlReceiptNumberPrefix;
	}
	public String getCrnToDisplay() {
		return crnToDisplay;
	}
	public void setCrnToDisplay(String crnToDisplay) {
		this.crnToDisplay = crnToDisplay;
	}
	public String getStrTxnTime() {
		return strTxnTime;
	}
	public void setStrTxnTime(String strTxnTime) {
		this.strTxnTime = strTxnTime;
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
	 * @return the poolOwnerFlag
	 */
	public String getPoolOwnerFlag() {
		return poolOwnerFlag;
	}
	/**
	 * @param poolOwnerFlag the poolOwnerFlag to set
	 */
	public void setPoolOwnerFlag(String poolOwnerFlag) {
		this.poolOwnerFlag = poolOwnerFlag;
	}
	/**
	 * @return the strRetTime
	 */
	public String getStrRetTime() {
		return strRetTime;
	}
	/**
	 * @param strRetTime the strRetTime to set
	 */
	public void setStrRetTime(String strRetTime) {
		this.strRetTime = strRetTime;
	}
	/**
	 * 
	 * @return
	 */
	public double getDemurrageRate() {
		return demurrageRate;
	}
	/**
	 * 
	 * @param demurrageRate
	 */
	public void setDemurrageRate(double demurrageRate) {
		this.demurrageRate = demurrageRate;
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
	 * @return the awbNumber
	 */
	public String getAwbNumber() {
		return awbNumber;
	}
	/**
	 * @param awbNumber the awbNumber to set
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	/**
	 * @return the emptyStatus
	 */
	public String getEmptyStatus() {
		return emptyStatus;
	}
	/**
	 * @param emptyStatus the emptyStatus to set
	 */
	public void setEmptyStatus(String emptyStatus) {
		this.emptyStatus = emptyStatus;
	}
	/**
	 * @return the isReturn
	 */
	public boolean isReturn() {
		return isReturn;
	}
	/**
	 * @param isReturn the isReturn to set
	 */
	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}
	/**
	 * @return the baseCurrency
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}
	/**
	 * @param baseCurrency the baseCurrency to set
	 */
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	/**
	 * @return the totalDemmurage
	 */
	public double getTotalDemmurage() {
		return totalDemmurage;
	}
	/**
	 * @param totalDemmurage the totalDemmurage to set
	 */
	public void setTotalDemmurage(double totalDemmurage) {
		this.totalDemmurage = totalDemmurage;
	}
	/**
	 * @return the returnCRN
	 */
	public String getReturnCRN() {
		return returnCRN;
	}
	/**
	 * @param returnCRN the returnCRN to set
	 */
	public void setReturnCRN(String returnCRN) {
		this.returnCRN = returnCRN;
	}
	/**
	 * @return the facilityType
	 */
	public String getFacilityType() {
		return facilityType;
	}
	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	//Added by A-3415 for ICRD-114538 -- Starts
	/**
	 * @return the thirdPartyFlag
	 */
	public String getThirdPartyFlag() {
		return thirdPartyFlag;
	}
	/**
	 * @param thirdPartyFlag the thirdPartyFlag to set
	 */
	public void setThirdPartyFlag(String thirdPartyFlag) {
		this.thirdPartyFlag = thirdPartyFlag;
	}
	/**
	 * @return the sysRtnFlag
	 */
	public String getSysRtnFlag() {
		return sysRtnFlag;
	}
	/**
	 * @param sysRtnFlag the sysRtnFlag to set
	 */
	public void setSysRtnFlag(String sysRtnFlag) {
		this.sysRtnFlag = sysRtnFlag;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	//Added by A-3415 for ICRD-114538 -- Ends
	/**
	 * @return the originatorName
	 */
	public String getOriginatorName() {
		return originatorName;
	}
	/**
	 * @param originatorName the originatorName to set
	 */
	public void setOriginatorName(String originatorName) {
		this.originatorName = originatorName;
	}
	
	/**
	 * @return the damageRemark
	 */
	public String getDamageRemark() {
		return damageRemark;
	}
	/**
	 * @param damageRemark the damageRemark to set
	 */
	public void setDamageRemark(String damageRemark) {
		this.damageRemark = damageRemark;
	}
	/**
	 * @return the odlnCode
	 */
	public String getOdlnCode() {
		return odlnCode;
	}
	/**
	 * @param odlnCode the odlnCode to set
	 */
	public void setOdlnCode(String odlnCode) {
		this.odlnCode = odlnCode;
	}
	
	/**
	 * @return the damageFlagFromScreen
	 */
	public String getDamageFlagFromScreen() {
		return damageFlagFromScreen;
	}
	/**
	 * @param damageFlagFromScreen the damageFlagFromScreen to set
	 */
	public void setDamageFlagFromScreen(String damageFlagFromScreen) {
		this.damageFlagFromScreen = damageFlagFromScreen;
	}
	public String getRemainingDayToReturn() {
		return remainingDayToReturn;
	}
	public void setRemainingDayToReturn(String remainingDayToReturn) {
		this.remainingDayToReturn = remainingDayToReturn;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.getLeaseEndDate
	 *	Added on 	:	23-Dec-2021
	 * 	Used for 	:	getting the lease end date
	 *	Parameters	:	@return 
	 *	Return type	: 	LocalDate
	 */
	public LocalDate getLeaseEndDate() {
		return leaseEndDate;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.setLeaseEndDate
	 *	Added on 	:	26-Dec-2021
	 * 	Used for 	:	setting lease end date
	 *	Parameters	:	@param leaseEndDate 
	 *	Return type	: 	void
	 */
	public void setLeaseEndDate(LocalDate leaseEndDate) {
		this.leaseEndDate = leaseEndDate;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.getStrLseEndDate
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	getting Lease end date as String
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getStrLseEndDate() {
		return strLseEndDate;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.setStrLseEndDate
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	setting Lease end date as String
	 *	Parameters	:	@param strLseEndDate 
	 *	Return type	: 	void
	 */
	public void setStrLseEndDate(String strLseEndDate) {
		this.strLseEndDate = strLseEndDate;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.getRemainingDaysToEndLease
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	getting Remaining days to End Lease Date
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getRemainingDaysToEndLease() {
		return remainingDaysToEndLease;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.setRemainingDaysToEndLease
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	setting Remaining days to End Lease Date
	 *	Parameters	:	@param remainingDaysToEndLease 
	 *	Return type	: 	void
	 */
	public void setRemainingDaysToEndLease(String remainingDaysToEndLease) {
		this.remainingDaysToEndLease = remainingDaysToEndLease;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.getDemurrageFrequency
	 *	Added on 	:	16-Apr-2023
	 * 	Used for 	:	getting Demurrage frequency
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getDemurrageFrequency() {
		return demurrageFrequency;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.setDemurrageFrequency
	 *	Added on 	:	16-Apr-2023
	 * 	Used for 	:	setting Demurrage frequency
	 *	Parameters	:	@param demurrageFrequency 
	 *	Return type	: 	void
	 */
	public void setDemurrageFrequency(String demurrageFrequency) {
		this.demurrageFrequency = demurrageFrequency;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.getFreeLoanPeriod
	 *	Added on 	:	16-Apr-2023
	 * 	Used for 	:	getting free loan period
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public int getFreeLoanPeriod() {
		return freeLoanPeriod;
	}
	/**
	 * 
	 * 	Method		:	ULDTransactionDetailsVO.setFreeLoanPeriod
	 *	Added on 	:	16-Apr-2023
	 * 	Used for 	:	setting free Loan period
	 *	Parameters	:	@param freeLoanPeriod 
	 *	Return type	: 	void
	 */
	public void setFreeLoanPeriod(int freeLoanPeriod) {
		this.freeLoanPeriod = freeLoanPeriod;
	}
	
	
	
	
	
	
}
