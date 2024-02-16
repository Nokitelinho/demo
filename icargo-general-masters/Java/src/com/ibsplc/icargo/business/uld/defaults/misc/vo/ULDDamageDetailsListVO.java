/*
 * ULDDamageDetailsListVO.java Created on Dec 21, 2005
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
public class ULDDamageDetailsListVO extends AbstractVO
                    implements Serializable{

    private String companyCode;
    private String uldNumber;
    private String reportedStation;
    private String currentStation;
    private String damageStatus;
    private String overallStatus;
    private String remarks;
    private LocalDate reportedDate;
    private LocalDate repairDate;
    private long damageReferenceNumber;
    private long sequenceNumber;
    private boolean isPicurePresent;
    private String party;
    private String partyType;
    private String location;
    //added by a-3045 for bug20387 starts
    private String section;
	private String damageDescription;
    //added by a-3045 for bug20387 starts
    
    //Added by A-7636 as part of ICRD-245031
	private String imageCount;
    
	public String getImageCount() {
		return imageCount;
	}
	public void setImageCount(String imageCount) {
		this.imageCount = imageCount;
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
	 * @return Returns the sequenceNumber.
	 */
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
	 * @return Returns the isPicurePresent.
	 */
	public boolean isPicurePresent() {
		return this.isPicurePresent;
	}
	/**
	 * @param isPicurePresent The isPicurePresent to set.
	 */
	public void setPicurePresent(boolean isPicurePresent) {
		this.isPicurePresent = isPicurePresent;
	}
	/**
	 * @return the damageDescription
	 */
	public String getDamageDescription() {
		return damageDescription;
	}
	/**
	 * @param damageDescription the damageDescription to set
	 */
	public void setDamageDescription(String damageDescription) {
		this.damageDescription = damageDescription;
	}
	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}
}
