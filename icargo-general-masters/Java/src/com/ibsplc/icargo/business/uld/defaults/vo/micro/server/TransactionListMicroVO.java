/*
 * TransactionListVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;


/**
 * @author A-2052
 *
 */
public class TransactionListMicroVO extends AbstractVO {

		private String companyCode;

		private String uldNumber;

		private String transactionType;

		private String operationalFlag;

		private String returnStationCode;

		private String currency;

		private double demurrageAmount;

		private String damageStatus;

		private String transactionStatus;

		private int operationalAirlineIdentifier;

		private int transactionRefNumber;

		private String transactionDate;
		private String returnDate;

		private String partyType;
		private String uldType;
	//Transaction Status will be P-Permanent/T-Temporary
		private String transactionNature;
		private String fromPartyCode;
		private String toPartyCode;
		private int fromPartyIdentifier;
		private int toPartyIdentifier;

	//Station at which Loan/Borrow
		private String transactionStationCode;
		private String transationPeriod;
		private String transactionRemark;
	//  This filed will only be there for Borrow Transaction
		private String capturedRefNumber;
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

		private String controlReceiptNumber;

		private int numberMonths;

		// to get selcted VO while modify
		private int selectNumber;
		// For setting invoice Number in Repair
		private int repairSeqNumber;
		private String strTxnDate;
		private String strRetDate;

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
		 * currOwnerCode
		 */
		private int  currOwnerCode;


		private String txStationCode;


		private String lastUpdateUser;
		/**
		 * For optimistic locking
		 */
    	private String lastUpdateTime;

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
		 *
		 * @param operationalFlag The operationalFlag to be set.
		 */
		public void setOperationalFlag(String operationalFlag){
			this.operationalFlag=operationalFlag;
		}
		/**
		 *
		 * @return Returns the OperationalFlag
		 */
		public String getOperationalFlag(){
			return operationalFlag;
		}
		/**
		 * @return Returns the returnStationCode.
		 */
		public String getReturnStationCode() {
			return this.returnStationCode;
		}

		/**
		 * @param returnStationCode The returnStationCode to set.
		 */
		public void setReturnStationCode(String returnStationCode) {
			this.returnStationCode = returnStationCode;
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
		 * @return Returns the transactionDate
		 */
		public String getTransactionDate() {
			return transactionDate;
		}

		/**
		 * @param transactionDate The transactionDate to set.
		 */
		public void setTransactionDate(String transactionDate) {
			this.transactionDate = transactionDate;
		}
		/**
		 * @return Returns the ReturnDate
		 */
		public String getReturnDate() {
			return transactionDate;
		}

		/**
		 * @param ReturnDate The ReturnDate to set.
		 */
		public void setReturnDate(String returnDate) {
			this.returnDate = returnDate;
		}

		/**
		 * @return Returns the lastUpdateTime.
		 */
		public String getLastUpdateTime() {
			return lastUpdateTime;
		}
		/**
		 * @param lastUpdateTime The lastUpdateTime to set.
		 */
		public void setLastUpdateTime(String lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}
	}
