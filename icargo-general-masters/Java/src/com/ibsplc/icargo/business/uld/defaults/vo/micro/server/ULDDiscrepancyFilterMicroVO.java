/*
 * ULDDiscrepancyFilterMicroVO.java Created on Dec 21, 2005
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
public class ULDDiscrepancyFilterMicroVO extends AbstractVO {

    private String uldNumber;
    private String reportingStation;
    private String ownerStation;
    private int uldOwnerIdentifier;
    private String companyCode;
    private String airlineCode;
    private  int pageNumber;
    private String agentCode;
    private String agentLocation;
    private String facilityType;

    /**
	 * @return Returns the facilityType.
	 */
	public String getFacilityType() {
		return facilityType;
	}
	/**
	 * @param facilityType The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
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
		return ownerStation;
	}
	/**
	 * @param ownerStation The ownerStation to set.
	 */
	public void setOwnerStation(String ownerStation) {
		this.ownerStation = ownerStation;
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
}
