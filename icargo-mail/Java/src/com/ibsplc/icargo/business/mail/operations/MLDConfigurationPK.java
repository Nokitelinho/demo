/*
 * MLDConfigurationPK Created on Dec 17, 2015
 *
 * Copyright 2015 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 * 
 * @author A-5526
 * 
 */
@Embeddable
public class MLDConfigurationPK implements Serializable {
	private String companyCode;

	private String airportCode;

	private int carrierIdentifier;

	/**
	 * @param other
	 * @return
	 */
	public boolean equals(Object other) {
		return (other != null) && ((this.hashCode() == other.hashCode()));
	}

	/**
	 * @return
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(airportCode)
				.append(carrierIdentifier).hashCode();
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode = companyCode;
	}

	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	@KeyCondition(column = "ARPCOD")
	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	@KeyCondition(column = "CARIDR")
	public int getCarrierIdentifier() {
		return carrierIdentifier;
	}

	public void setCarrierIdentifier(int carrierIdentifier) {
		this.carrierIdentifier = carrierIdentifier;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:53 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(100);
		sbul.append("MLDConfigurationPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', airportCode '").append(this.airportCode);
		sbul.append("', carrierIdentifier '").append(this.carrierIdentifier);
		sbul.append("' ]");
		return sbul.toString();
	}

}
