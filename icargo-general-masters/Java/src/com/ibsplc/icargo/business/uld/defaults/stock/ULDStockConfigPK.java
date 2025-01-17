/*
 * ULDStockConfigPK.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.stock;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * 
 * 
 * @author A-1496
 *
 */
@Embeddable
public class ULDStockConfigPK implements Serializable {
    /**
     * Company code
     */
    private String companyCode;
    /**
     * Airline Identifier
     */
    private int airlineIdentifier;
    /**
     * Station Code
     */
    private String stationCode;
    /**
     * Uld Type Code
     */
    private String uldTypeCode;
    
    /**
     * 
     */
    private String uldNature;
    
    /**
     * Default constructor
     */
	public ULDStockConfigPK() {
	}

	/**
	 * Contructor
	 * 
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param stationCode
	 * @param uldTypeCode
	 */
    public ULDStockConfigPK(
    	String companyCode, 
    	int airlineIdentifier,
    	String stationCode,
    	String uldTypeCode ,
    	String uldNature) {
    	this.companyCode = companyCode;
    	this.airlineIdentifier = airlineIdentifier;
    	this.stationCode = stationCode;
    	this.uldTypeCode = uldTypeCode;
    	this.uldNature = uldNature;
	}
    /**
     * @param other
     * @return boolean
     */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
/**
 * @return int
 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(airlineIdentifier).
				append(stationCode).
				append(uldTypeCode).
				append(uldNature).
				toString().hashCode();
	}




	public void setUldTypeCode(java.lang.String uldTypeCode) {
		this.uldTypeCode=uldTypeCode;
	}

	public java.lang.String getUldTypeCode() {
		return this.uldTypeCode;
	}

	public void setUldNature(java.lang.String uldNature) {
		this.uldNature=uldNature;
	}

	public java.lang.String getUldNature() {
		return this.uldNature;
	}

	public void setStationCode(java.lang.String stationCode) {
		this.stationCode=stationCode;
	}

	public java.lang.String getStationCode() {
		return this.stationCode;
	}

	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier=airlineIdentifier;
	}

	public int getAirlineIdentifier() {
		return this.airlineIdentifier;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:18 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(135);
		sbul.append("ULDStockConfigPK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', stationCode '").append(this.stationCode);
		sbul.append("', uldNature '").append(this.uldNature);
		sbul.append("', uldTypeCode '").append(this.uldTypeCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
