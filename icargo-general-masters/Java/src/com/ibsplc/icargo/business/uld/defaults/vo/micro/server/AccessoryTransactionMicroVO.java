/*
 * AccessoryTransactionVO.java Created on Jan 5, 2006
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
public class AccessoryTransactionMicroVO extends AbstractVO{

    private String companyCode;
    private String accessoryCode;
    private int transactionRefNumber;
//  Transaction Type will be L-Loan/B-Borrow/R-Return
    private String transactionType;
//  Transaction Status will be P-Permanent/T-Temporary
    private String transactionNature;
    private String partyType;
  //  private String partyName;
  //  private String partyCode;
//  Current Owner Identifier
    private int  currOwnerCode;
//  Station at which Loan/Borrow happens
    private String transactionStationCode;
    private String transationPeriod;
    private String transactionDate;
    private String transactionRemark;
    private int quantity;
    private int operationalAirlineIdentifier;
    private String operationalFlag;

   private String lastUpdateUser;
    private String returnStation;
    private String strTxnDate;

    private String toPartyCode;
    private String fromPartyCode;
    private String toPartyName;
    private String fromPartyName;

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
	 * @return Returns the operationalAirlineIdentifier.
	 */
	public int getOperationalAirlineIdentifier() {
		return operationalAirlineIdentifier;
	}
	/**
	 * @param operationalAirlineIdentifier The operationalAirlineIdentifier to set.
	 */
	public void setOperationalAirlineIdentifier(int operationalAirlineIdentifier) {
		this.operationalAirlineIdentifier = operationalAirlineIdentifier;
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
	 * @return Returns the currOwnerCode.
	 */
	public int getCurrOwnerCode() {
		return currOwnerCode;
	}
	/**
	 * @param currOwnerCode The currOwnerCode to set.
	 */
	public void setCurrOwnerCode(int currOwnerCode) {
		this.currOwnerCode = currOwnerCode;
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
	 * @return Returns the quantity.
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return Returns the transactionDate.
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
	 * @return Returns the returnStation.
	 */
	public String getReturnStation() {
		return returnStation;
	}
	/**
	 * @param returnStation The returnStation to set.
	 */
	public void setReturnStation(String returnStation) {
		this.returnStation = returnStation;
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







}
