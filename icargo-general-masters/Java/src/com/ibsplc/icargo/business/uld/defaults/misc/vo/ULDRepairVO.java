/*
 * ULDRepairVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**
 * @author A-1347
 *
 */
public class ULDRepairVO extends AbstractVO{
    /**
     * 
     */
	public static final String MODULE ="uld";
	/**
	 * 
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * 
	 */
	public static final String ENTITY ="uld.defaults.misc.ULDRepair";
	
	private long damageReferenceNumber;
	private String lastUpdateUser;
    private String repairHead;
    private String repairStation;
    private double amount;    
    private String currency;
    private String remarks;
    private long repairSequenceNumber;
    private LocalDate repairDate;
    private String operationFlag;
    private double displayAmount;
    private int airlineIdentifier;
    private String invoiceReferenceNumber;
	private LocalDate lastUpdateTime;
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
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	/**
	 * @return Returns the repairHead.
	 */
	public String getRepairHead() {
		return repairHead;
	}
	/**
	 * @param repairHead The repairHead to set.
	 */
	public void setRepairHead(String repairHead) {
		this.repairHead = repairHead;
	}

	
	/**
	 * @return Returns the repairStation.
	 */
	public String getRepairStation() {
		return repairStation;
	}
	/**
	 * @param repairStation The repairStation to set.
	 */
	public void setRepairStation(String repairStation) {
		this.repairStation = repairStation;
	}

	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return Returns the repairSequenceNumber.
	 */
	public long getRepairSequenceNumber() {
		return repairSequenceNumber;
	}
	/**
	 * @param repairSequenceNumber The repairSequenceNumber to set.
	 */
	public void setRepairSequenceNumber(long repairSequenceNumber) {
		this.repairSequenceNumber = repairSequenceNumber;
	}
	
	/**
	 * @return Returns the repairDate.
	 */
	public LocalDate getRepairDate() {
		return repairDate;
	}
	/**
	 * @param repairDate The repairDate to set.
	 */
	public void setRepairDate(LocalDate repairDate) {
		this.repairDate = repairDate;
	}
	
	/**
	 * @return Returns the damageReferenceNumber.
	 */
	public long getDamageReferenceNumber() {
		return damageReferenceNumber;
	}
	/**
	 * @param damageReferenceNumber The damageReferenceNumber to set.
	 */
	public void setDamageReferenceNumber(long damageReferenceNumber) {
		this.damageReferenceNumber = damageReferenceNumber;
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
	* This method generates the hashcode of an instance
	 * @return int - returns the hashcode of the instance
	 */
	//@Column(name = "")
	/**
	 * @return Returns the displayAmount.
	 */
	public double getDisplayAmount() {
		return displayAmount;
	}
	/**
	 * @param displayAmount The displayAmount to set.
	 */
	public void setDisplayAmount(double displayAmount) {
		this.displayAmount = displayAmount;
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
	//@Column(name = "")
	/**
	 * @return Returns the invoiceReferenceNumber.
	 */
	public String getInvoiceReferenceNumber() {
		return invoiceReferenceNumber;
	}
	/**
	 * @param invoiceReferenceNumber The invoiceReferenceNumber to set.
	 */
	public void setInvoiceReferenceNumber(String invoiceReferenceNumber) {
		this.invoiceReferenceNumber = invoiceReferenceNumber;
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
}
