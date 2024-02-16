/*
 * MRAAirlineBillingDetailPK.java Created on Feb 15, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-1946
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1			  feb 14, 2007			    A-1946		Created	
 */
@Embeddable
public class MRAAirlineBillingDetailPK implements Serializable {
    /**
     * companycode
     */
    private String companyCode;

    /**
     * airlineIdentifier
     */
    private int airlineIdentifier;

    /**
     * sequenceNumber
     */
    private int sequenceNumber;
    
    /**
     * billingBasis
     */
    private String billingBasis;

    /**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
     *
     * @return
     */
    public int hashCode() {
        return new StringBuilder().append(companyCode).append(airlineIdentifier)
                .append(sequenceNumber).append(billingBasis).toString().hashCode();
    }

    /**
     *
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
    	boolean isEqual = false;
		if(obj != null ){
			isEqual = hashCode() == obj.hashCode();
		}
		return isEqual;		
    }

    /**
     * 
     * @param companyCode
     * @param airlineIdentifier
     * @param sequenceNumber
     * @param billingBasis
     */
    public MRAAirlineBillingDetailPK(String companyCode, int airlineIdentifier,
    		int sequenceNumber, String billingBasis) {
        this.companyCode = companyCode;
        this.airlineIdentifier = airlineIdentifier;
        this.sequenceNumber = sequenceNumber;
        this.billingBasis = billingBasis;
    }

    /**
     *
     */
    public MRAAirlineBillingDetailPK() {
    }

    /**
     * 
     * @param billingBasis
     */
	public void setBillingBasis(java.lang.String billingBasis) {
		this.billingBasis=billingBasis;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getBillingBasis() {
		return this.billingBasis;
	}

	/**
	 * 
	 * @param airlineIdentifier
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier=airlineIdentifier;
	}

	/**
	 * 
	 * @return
	 */
	public int getAirlineIdentifier() {
		return this.airlineIdentifier;
	}

	/**
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:53 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(127);
		sbul.append("MRAAirlineBillingDetailPK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', billingBasis '").append(this.billingBasis);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}