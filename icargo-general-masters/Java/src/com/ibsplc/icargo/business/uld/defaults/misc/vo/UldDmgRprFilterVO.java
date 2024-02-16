/*
 * UldDmgRprFilterVO.java Created on May 15,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author a-3093
 *
 */

public class UldDmgRprFilterVO extends AbstractVO{
    private String companyCode;
    private String uldNumber;
    private long damageReferenceNumber;
    private String uldTypeCode;
    private String uldStatus;
    private String damageStatus;
    private String currentStation;
    private String reportedStation;
    private String fromDate;
    private String ToDate;
	private String repairHead;
    private String repairStation;
    private String repairStatus;
    private int pageNumber;
    private int noOfMovements;
    private int noOfLoanTxns;
    private int noOfTimesDmged;
    private int noOfTimesRepaired;
    
    
    /**
	 * @return the noOfLoanTxns
	 */
	public int getNoOfLoanTxns() {
		return noOfLoanTxns;
	}
	/**
	 * @param noOfLoanTxns the noOfLoanTxns to set
	 */
	public void setNoOfLoanTxns(int noOfLoanTxns) {
		this.noOfLoanTxns = noOfLoanTxns;
	}
	/**
	 * @return the noOfMovements
	 */
	public int getNoOfMovements() {
		return noOfMovements;
	}
	/**
	 * @param noOfMovements the noOfMovements to set
	 */
	public void setNoOfMovements(int noOfMovements) {
		this.noOfMovements = noOfMovements;
	}
	/**
	 * @return the noOfTimesDmged
	 */
	public int getNoOfTimesDmged() {
		return noOfTimesDmged;
	}
	/**
	 * @param noOfTimesDmged the noOfTimesDmged to set
	 */
	public void setNoOfTimesDmged(int noOfTimesDmged) {
		this.noOfTimesDmged = noOfTimesDmged;
	}
	/**
	 * @return the noOfTimesRepaired
	 */
	public int getNoOfTimesRepaired() {
		return noOfTimesRepaired;
	}
	/**
	 * @param noOfTimesRepaired the noOfTimesRepaired to set
	 */
	public void setNoOfTimesRepaired(int noOfTimesRepaired) {
		this.noOfTimesRepaired = noOfTimesRepaired;
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
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the reportedStation.
	 */
	public String getReportedStation() {
		return reportedStation;
	}
	/**
	 * @param reportedStation The reportedStation to set.
	 */
	public void setReportedStation(String reportedStation) {
		this.reportedStation = reportedStation;
	}
	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return ToDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		ToDate = toDate;
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
	 * @return Returns the uldStatus.
	 */
	public String getUldStatus() {
		return uldStatus;
	}
	/**
	 * @param uldStatus The uldStatus to set.
	 */
	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
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
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
	
}
    