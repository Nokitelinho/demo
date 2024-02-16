/*
 * ReservationPK.java Created on Jan 8, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.reservation;

import java.io.Serializable;


import javax.persistence.Embeddable;

/**
 * @author A-1619
 *
 */
@Embeddable
public class ReservationPK implements Serializable {

    /**
     * 
     */
    private String companyCode;
    
    /**
     * 
     */
    private String airportCode;
    
    /**
     * 
     */
    private int airlineIdentifier;
    
    /**
     * 
     */
    private String documentNumber;
    
    /**
     * @param other
     * @return
     */
    public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

	/**
	 * @return
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).append(airportCode).
		append(airlineIdentifier).append(documentNumber).	
			toString().hashCode();
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
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
	 * @return Returns the documentNumber.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber The documentNumber to set.
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:15 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(114);
		sbul.append("ReservationPK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', airportCode '").append(this.airportCode);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', documentNumber '").append(this.documentNumber);
		sbul.append("' ]");
		return sbul.toString();
	}   
    
}