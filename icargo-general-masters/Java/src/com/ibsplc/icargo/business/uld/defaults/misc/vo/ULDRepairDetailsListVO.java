/*
 * ULDRepairDetailsListVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;


import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1950
 *
 */
public class ULDRepairDetailsListVO extends AbstractVO
                    implements Serializable{

    private String companyCode;
    private String repairHead;
    private String invoiceReferenceNumber;
    private long repairSequenceNumber;
    private String uldNumber;
    private String repairedStation;
    private String currentStation;
    private String damageStatus;
    private String overallStatus;
    private String remarks;
    private LocalDate reportedDate;
    private LocalDate repairDate;
    private String repairStatus;
    private long damageReferenceNumber;
    private String lastUpdateUser;
    private LocalDate lastUpdateTime;
    private double repairAmount;
    private double initialCost;
    
    //added by jisha for QF1022 starts
    
    private String damageCode;
    /*
     * position
     */
       private String position;
       
       /*
        * damage section
        */
       private String section;   
       
       /*
        * invoicedAmount
        */
       private double invoicedAmount;
       /*
        * damageDate
        */
       private LocalDate  damageDate; 
       
       /*
        * invoiceStatus
        */
       private String invoiceStatus;
       
      //added by jisha for QF1022 ends
      //Added by A-7359 for ICRD-248560
       private String currency;
    
	public double getInitialCost() {
		return initialCost;
	}
	public void setInitialCost(double initialCost) {
		this.initialCost = initialCost;
	}
	public double getRepairAmount() {
		return repairAmount;
	}
	public void setRepairAmount(double repairAmount) {
		this.repairAmount = repairAmount;
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
	 * @return Returns the repairStatus.
	 */
	public String getRepairStatus() {
		return repairStatus;
	}
	/**
	 * @param repairStatus The repairStatus to set.
	 */
	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
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
	 * @return Returns the currentStation.
	 */
	public String getCurrentStation() {
		return currentStation;
	}
	/**
	 * @param currentStation The currentStation to set.
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
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
	 * @return Returns the overallStatus.
	 */
	public String getOverallStatus() {
		return overallStatus;
	}
	/**
	 * @param overallStatus The overallStatus to set.
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
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
	 * @return Returns the repairedStation.
	 */
	public String getRepairedStation() {
		return repairedStation;
	}
	/**
	 * @param repairedStation The repairedStation to set.
	 */
	public void setRepairedStation(String repairedStation) {
		this.repairedStation = repairedStation;
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
	
	//@Column(name = "")
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
	 * @return Returns the reportedDate.
	 */
	public LocalDate getReportedDate() {
		return reportedDate;
	}
	/**
	 * @param reportedDate The reportedDate to set.
	 */
	public void setReportedDate(LocalDate reportedDate) {
		this.reportedDate = reportedDate;
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
	//@Column(name = "")
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
	 * @return Returns the damageCode.
	 */
	public String getDamageCode() {
		return damageCode;
	}
	/**
	 * @param damageCode The damageCode to set.
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	
	/**
	 * @return the damageDate
	 */
	public LocalDate getDamageDate() {
		return damageDate;
	}
	/**
	 * @param damageDate the damageDate to set
	 */
	public void setDamageDate(LocalDate damageDate) {
		this.damageDate = damageDate;
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
	 * @return Returns the position.
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return Returns the section.
	 */
	public String getSection() {
		return section;
	}
	/**
	 * @param section The section to set.
	 */
	public void setSection(String section) {
		this.section = section;
	}
	/**
	 * 
	 * 	Method		:	ULDRepairDetailsListVO.getCurrency
	 *	Added by 	:	A-7359 on 11-Jun-2018
	 * 	Used for 	:	ICRD-248560
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * 
	 * 	Method		:	ULDRepairDetailsListVO.setCurrency
	 *	Added by 	:	A-7359 on 11-Jun-2018
	 * 	Used for 	:	ICRD-248560
	 *	Parameters	:	@param currency 
	 *	Return type	: 	void
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
