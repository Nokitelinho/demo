/*
 * ULDRepairInvoiceDetailsVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1883
 *
 */
public class ULDRepairInvoiceDetailsVO extends AbstractVO 
				implements Serializable {

	private String companyCode;
	private String uldNumber;
	private String repairHead;
	private String repairStation;
	private double actualAmount;// repair amount
	private String repairRemarks;
	private double waivedAmount;
	private double invoicedAmount;
	private long repairSeqNumber;
	private String operationFlag; 
	private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	private Collection<Integer> damageRefNumbers;
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
	 * @return Returns the actualAmount.
	 */
	public double getActualAmount() {
		return actualAmount;
	}
	/**
	 * @param actualAmount The actualAmount to set.
	 */
	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
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
	 * @return Returns the repairRemarks.
	 */
	public String getRepairRemarks() {
		return repairRemarks;
	}
	/**
	 * @param repairRemarks The repairRemarks to set.
	 */
	public void setRepairRemarks(String repairRemarks) {
		this.repairRemarks = repairRemarks;
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
	 * @return Returns the waivedAmount.
	 */
	public double getWaivedAmount() {
		return waivedAmount;
	}
	/**
	 * @param waivedAmount The waivedAmount to set.
	 */
	public void setWaivedAmount(double waivedAmount) {
		this.waivedAmount = waivedAmount;
	}
	/**
	 * @return Returns the damageRefNumbers.
	 */
	public Collection<Integer> getDamageRefNumbers() {
		return damageRefNumbers;
	}
	/**
	 * @param damageRefNumbers The damageRefNumbers to set.
	 */
	public void setDamageRefNumbers(Collection<Integer> damageRefNumbers) {
		this.damageRefNumbers = damageRefNumbers;
	}
	/**
	 * @return Returns the repairSeqNumber.
	 */
	public long getRepairSeqNumber() {
		return repairSeqNumber;
	}
	/**
	 * @param repairSeqNumber The repairSeqNumber to set.
	 */
	public void setRepairSeqNumber(long repairSeqNumber) {
		this.repairSeqNumber = repairSeqNumber;
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
	 * @return Returns the invoicedAmount.
	 */
	public double getInvoicedAmount() {
		return invoicedAmount;
	}
	/**
	 * @param invoicedAmount The invoicedAmount to set.
	 */
	public void setInvoicedAmount(double invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	} 
	
}
