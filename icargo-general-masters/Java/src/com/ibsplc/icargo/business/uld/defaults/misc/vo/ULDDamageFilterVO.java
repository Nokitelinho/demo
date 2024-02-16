/*
 * ULDDamageFilterVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDDamageFilterVO extends AbstractVO{
    
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
    
    private int pageNumber;
    
	//Added by Tarun for CRQ AirNZ418
	private String facilityType;
	private String location;
	private String partyType;
	private String party;
	//added by A-5223 for ICRD-22824 starts
	private int totalRecords;
	//added by A-5223 for ICRD-22824 ends
	private int damageSequenceNumber;
	
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
	 * @return the party
	 */
	public String getParty() {
		return party;
	}
	/**
	 * @param party the party to set
	 */
	public void setParty(String party) {
		this.party = party;
	}
	/**
	 * @return the partyType
	 */
	public String getPartyType() {
		return partyType;
	}
	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
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
	 * 	Getter for totalRecords 
	 *	Added by : a-5223 on 07-Nov-2012
	 * 	Used for : getting total records
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 *  @param totalRecords the totalRecords to set
	 * 	Setter for totalRecords 
	 *	Added by : a-5223 on 07-Nov-2012
	 * 	Used for : setting total records
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getDamageSequenceNumber() {
		return damageSequenceNumber;
	}
	public void setSequenceNumber(int damageSequenceNumber) {
		this.damageSequenceNumber = damageSequenceNumber;
	}
	
}
