/*
 * StockHolderVO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * The Class EmbargoGeographicLevelVO.
 *
 * @author A-1358
 */
public class EmbargoGeographicLevelVO extends AbstractVO {

	private String companyCode;
	
	private String embargoReferenceNumber;
	
	private int embargoVersion;
	
	

	/** The geographic level. */
	private String geographicLevel;
	
	/** The geographic level type. */
	private String geographicLevelType;
	
	/** The is included. */
	private String geographicLevelApplicableOn;
	
	/** The values. */
	private String geographicLevelValues;
	
	private String operationFlag;
	
	private String airportCode;
	
	private String countryCode;
	
	private String airportGroup;
	
	private String countryGroup;
	
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getEmbargoReferenceNumber() {
		return embargoReferenceNumber;
	}

	public void setEmbargoReferenceNumber(String embargoReferenceNumber) {
		this.embargoReferenceNumber = embargoReferenceNumber;
	}

	
	public int getEmbargoVersion() {
		return embargoVersion;
	}

	public void setEmbargoVersion(int embargoVersion) {
		this.embargoVersion = embargoVersion;
	}
	
	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	
	
	public String getGeographicLevelValues() {
		return geographicLevelValues;
	}

	public void setGeographicLevelValues(String geographicLevelValues) {
		this.geographicLevelValues = geographicLevelValues;
	}

	/**
	 * Gets the geographic level.
	 *
	 * @return the geographic level
	 */
	public String getGeographicLevel() {
		return geographicLevel;
	}
	
	/**
	 * Sets the geographic level.
	 *
	 * @param geographicLevel the new geographic level
	 */
	public void setGeographicLevel(String geographicLevel) {
		this.geographicLevel = geographicLevel;
	}
	
	/**
	 * Gets the geographic level type.
	 *
	 * @return the geographic level type
	 */
	public String getGeographicLevelType() {
		return geographicLevelType;
	}
	
	/**
	 * Sets the geographic level type.
	 *
	 * @param geographicLevelType the new geographic level type
	 */
	public void setGeographicLevelType(String geographicLevelType) {
		this.geographicLevelType = geographicLevelType;
	}
	

	
	public String getGeographicLevelApplicableOn() {
		return geographicLevelApplicableOn;
	}

	public void setGeographicLevelApplicableOn(String geographicLevelApplicableOn) {
		this.geographicLevelApplicableOn = geographicLevelApplicableOn;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAirportGroup() {
		return airportGroup;
	}

	public void setAirportGroup(String airportGroup) {
		this.airportGroup = airportGroup;
	}

	public String getCountryGroup() {
		return countryGroup;
	}

	public void setCountryGroup(String countryGroup) {
		this.countryGroup = countryGroup;
	}

	

}