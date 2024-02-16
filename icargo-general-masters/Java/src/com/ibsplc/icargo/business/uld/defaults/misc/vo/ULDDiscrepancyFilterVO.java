/*
 * ULDDiscrepancyFilterVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;


import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDDiscrepancyFilterVO extends AbstractVO{
    
    private String uldNumber;
    private String reportingStation;    
    private String OwnerStation;
    private int uldOwnerIdentifier;
    private String companyCode;
    private String airlineCode;
    private  int pageNumber; 
    private String agentCode;
    private String agentLocation;
    private String location;
    private String facilityType;
    private String discrepancyStatus ;
    //added by a-3045 for CR AirNZ265 starts
    private LocalDate fromDate;
    private LocalDate todate;
    private Collection<String> uldNumbers;
    //added by a_3045 for CR AirNZ265 ends
    
  //Added by A-5220 for ICRD-22824 starts
    private int totalRecordsCount;
      /**
     * @return the totalRecordsCount
     */
    public int getTotalRecordsCount() {
    	return totalRecordsCount;
    }
    /**
     * @param totalRecordsCount the totalRecordsCount to set
     */
    public void setTotalRecordsCount(int totalRecordsCount) {
    	this.totalRecordsCount = totalRecordsCount;
    }

    //Added by A-5220 for ICRD-22824 ends
    
    /**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the todate
	 */
	public LocalDate getTodate() {
		return todate;
	}
	/**
	 * @param todate the todate to set
	 */
	public void setTodate(LocalDate todate) {
		this.todate = todate;
	}
	/**
     * 
     * @return
     */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * 
	 * @param agentCode
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getAgentLocation() {
		return agentLocation;
	}
	/**
	 * 
	 * @param agentLocation
	 */
	public void setAgentLocation(String agentLocation) {
		this.agentLocation = agentLocation;
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
	 * @return Returns the uldOwnerIdentifier.
	 */
	public int getUldOwnerIdentifier() {
		return uldOwnerIdentifier;
	}
	/**
	 * @param uldOwnerIdentifier The uldOwnerIdentifier to set.
	 */
	public void setUldOwnerIdentifier(int uldOwnerIdentifier) {
		this.uldOwnerIdentifier = uldOwnerIdentifier;
	}
	/**
	 * @return Returns the ownerStation.
	 */
	public String getOwnerStation() {
		return OwnerStation;
	}
	/**
	 * @param ownerStation The ownerStation to set.
	 */
	public void setOwnerStation(String ownerStation) {
		OwnerStation = ownerStation;
	}
	/**
	 * @return Returns the reportingStation.
	 */
	public String getReportingStation() {
		return reportingStation;
	}
	/**
	 * @param reportingStation The reportingStation to set.
	 */
	public void setReportingStation(String reportingStation) {
		this.reportingStation = reportingStation;
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
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getFacilityType() {
		return facilityType;
	}
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDiscrepancyStatus() {
		return discrepancyStatus;
	}
	public void setDiscrepancyStatus(String discrepancyStatus) {
		this.discrepancyStatus = discrepancyStatus;
	}
	public Collection<String> getUldNumbers() {
		return uldNumbers;
	}
	public void setUldNumbers(Collection<String> uldNumbers) {
		this.uldNumbers = uldNumbers;
	}    
}
