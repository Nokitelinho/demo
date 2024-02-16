/*
 * ULDRepairFilterVO.java Created on Dec 21, 2005
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
public class ULDRepairFilterVO extends AbstractVO{
	
	private String companyCode;
	 private String uldNumber;
	private String repairHead;
    private String uldTypeCode;
    private String repairStation;
    private String uldStatus;
    private String repairStatus;
    private String currentStation;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int pageNumber;
    private int totalRecords;//Added By A-5214
    private String currency;

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
	//Added by A-5214 as part from the ICRD-22824 STARTS
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	//Added by A-5214 as part from the ICRD-22824 ENDS
	/**
	 * 
	 * 	Method		:	ULDRepairFilterVO.getCurrency
	 *	Added by 	:	A-7359 on 21-Jun-2018
	 * 	Used for 	:	ICRD-268766
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * 
	 * 	Method		:	ULDRepairFilterVO.setCurrency
	 *	Added by 	:	A-7359 on 21-Jun-2018
	 * 	Used for 	:	ICRD-268766
	 *	Parameters	:	@param currency 
	 *	Return type	: 	void
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
