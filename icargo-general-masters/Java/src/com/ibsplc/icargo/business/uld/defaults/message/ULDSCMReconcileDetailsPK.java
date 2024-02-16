/*
* ULDSCMReconcileDetailsPK.java Created on AUG 01, 2006
*
* Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to license terms.
*/

package com.ibsplc.icargo.business.uld.defaults.message;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-2048
 */
@Embeddable
public class ULDSCMReconcileDetailsPK implements Serializable {
    
	   
	   /**
	    * 
	    */
	    private String airportCode;
	    /**
	     * 
	     */
	    private String companyCode;
	    /**
	     * 
	     */
	    private int airlineIdentifier;
	    /**
	     * 
	     */
	    private String sequenceNumber;
	    /**
	     * 
	     */
	    private String uldNumber;
	 
    /**
     *
     */
    public ULDSCMReconcileDetailsPK() {
    }
   /**
    * 
    * @param airportCode
    * @param companyCode
    * @param sequenceNumber
    * @param airlineIdentifier
    * @param uldNumber
    */
    public ULDSCMReconcileDetailsPK(
            String airportCode,
            String companyCode,
            String sequenceNumber,
            int  airlineIdentifier,
            String uldNumber) {
    	
    	
    	this.airportCode = airportCode;
    	this.companyCode = companyCode;
    	
    	this.sequenceNumber = sequenceNumber;
    	this.airlineIdentifier =airlineIdentifier;
    	this.uldNumber = uldNumber;
    }

    /**
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        return (other != null) && (hashCode() == other.hashCode());
    }

    /**
     * @return
     */
    public int hashCode() {
    	return new StringBuilder().append(airportCode)
        .append(companyCode)
        .append(sequenceNumber)
        .append(airlineIdentifier)
        .append(uldNumber)
        .toString().hashCode();
    }
    /**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(java.lang.String airportCode) {
		this.airportCode=airportCode;
	}
	/**
	 * @return Returns the airportCode.
	 */
	public java.lang.String getAirportCode() {
		return this.airportCode;
	}
	 /**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(java.lang.String uldNumber) {
		this.uldNumber=uldNumber;
	}
	/**
	 * @return Returns the uldNumber.
	 */
	public java.lang.String getUldNumber() {
		return this.uldNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(java.lang.String sequenceNumber) {
		this.sequenceNumber=sequenceNumber;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	public java.lang.String getSequenceNumber() {
		return this.sequenceNumber;
	}
	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier=airlineIdentifier;
	}
	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return this.airlineIdentifier;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:17 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(146);
		sbul.append("ULDSCMReconcileDetailsPK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', airportCode '").append(this.airportCode);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("', uldNumber '").append(this.uldNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
