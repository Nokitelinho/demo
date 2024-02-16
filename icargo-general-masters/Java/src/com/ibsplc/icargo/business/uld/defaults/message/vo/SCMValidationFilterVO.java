/*
 * SCMValidationFilterVO.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message.vo;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3459
 *
 */
public class SCMValidationFilterVO extends AbstractVO{
	
	private String companyCode;
	private String airportCode;
	private String facilityType;
	private String location;
	private int pageNumber;
	private String uldTypeCode;
	private String uldNumber;
	private String scmStatus;
	private String total; 
	private String notSighted; 
	private String missing;
	private int totalRecords;
	//added by a-3278 for bug 34185 on 21Jan09
	private int airlineIdentifier;
	//a-3278 ends
	/**
	 * @return the missing
	 */
	public String getMissing() {
		return missing;
	}
	/**
	 * @param missing the missing to set
	 */
	public void setMissing(String missing) {
		this.missing = missing;
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
	 * @return the scmStatus
	 */
	public String getScmStatus() {
		return scmStatus;
	}
	/**
	 * @param scmStatus the scmStatus to set
	 */
	public void setScmStatus(String scmStatus) {
		this.scmStatus = scmStatus;
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
	 * @return the uldTypeCode
	 */
	public String getUldTypeCode() {
		return uldTypeCode;
	}
	/**
	 * @param uldTypeCode the uldTypeCode to set
	 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
	}
	/**
	 * @return the airlineIdentifier
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	/**
	 * @param airlineIdentifier the airlineIdentifier to set
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
}
