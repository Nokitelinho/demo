/*
 * SCMValidationVO.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3459
 *
 */
public class SCMValidationVO extends AbstractVO{
	
	private String companyCode;
	private String airportCode;
	private String facilityType;
	private String location;
	private String uldNumber;
	private String scmFlag;
	private LocalDate scmdate;
	private int pageNumber;
	private String notSighted;
	private String total;
	private String prevMissingFlag;
	//Added by A-6841 for 213319 starts
	private String flightDetails;
	private String flightSegment;
	private String remarks;
	
	private boolean missingDiscrepancyCaptured;
	public String getFlightDetails() {
		return flightDetails;
	}
	public void setFlightDetails(String flightDetails) {
		this.flightDetails = flightDetails;
	}
	public String getFlightSegment() {
		return flightSegment;
	}
	public void setFlightSegment(String flightSegment) {
		this.flightSegment = flightSegment;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	//Added by A-6841 for 213319 ends
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
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
	 * @return the scmdate
	 */
	public LocalDate getScmdate() {
		return scmdate;
	}
	/**
	 * @param scmdate the scmdate to set
	 */
	public void setScmdate(LocalDate scmdate) {
		this.scmdate = scmdate;
	}
	/**
	 * @return the scmFlag
	 */
	public String getScmFlag() {
		return scmFlag;
	}
	/**
	 * @param scmFlag the scmFlag to set
	 */
	public void setScmFlag(String scmFlag) {
		this.scmFlag = scmFlag;
	}
	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return the notSighted
	 */
	public String getNotSighted() {
		return notSighted;
	}
	/**
	 * @param notSighted the notSighted to set
	 */
	public void setNotSighted(String notSighted) {
		this.notSighted = notSighted;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the prevMissingFlag
	 */
	public String getPrevMissingFlag() {
		return prevMissingFlag;
	}
	/**
	 * @param prevMissingFlag the prevMissingFlag to set
	 */
	public void setPrevMissingFlag(String prevMissingFlag) {
		this.prevMissingFlag = prevMissingFlag;
	}
	public boolean isMissingDiscrepancyCaptured() {
		return missingDiscrepancyCaptured;
	}
	public void setMissingDiscrepancyCaptured(boolean missingDiscrepancyCaptured) {
		this.missingDiscrepancyCaptured = missingDiscrepancyCaptured;
	}
	
	
}
