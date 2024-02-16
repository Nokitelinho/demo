/*
 * RelocateULDVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1347
 *
 */
public class RelocateULDVO extends AbstractVO{

    private String companyCode;

    private String uldNumber;

    private String currentStation;

    private String location;
    
    private String facilityType;
 
    //added by Sreekumar S for AirNZCR 421 on 03Apr08
    private String uldSuffix;

    private LocalDate txnFromDate;
    
    private LocalDate txnToDate;

	

	/**
	 * @return String Returns the facilityType.
	 */
	public String getFacilityType() {
		return this.facilityType;
	}

	/**
	 * @param facilityType The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
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
	 * @return Returns the location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
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
	 * 
	 * @return
	 */
	public String getUldSuffix() {
		return uldSuffix;
	}
	/**
	 * 
	 * @param uldSuffix
	 */
	public void setUldSuffix(String uldSuffix) {
		this.uldSuffix = uldSuffix;
	}

	/**
	 * @return Returns the txnFromDate.
	 */
	public LocalDate getTxnFromDate() {
		return txnFromDate;
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
		return txnToDate;
	}

	/**
	 * @param txnToDate The txnToDate to set.
	 */
	public void setTxnToDate(LocalDate txnToDate) {
		this.txnToDate = txnToDate;
	}
	
}
