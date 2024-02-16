/*
 * GPABillingMasterPK.java Created on Dec 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-1556
 *
 */
@Embeddable
public class GPABillingMasterPK implements Serializable {


	/**
	 * The company Code
	 */
    private String companyCode;

    /**
     * The GPA Code
     */
    private String gpaCode;

    /**
     * The billing basis
     * The value of this field may be a despatch number or
     * a mail bag number
     */
    private String billingBasis;

    /**
     * Default constructor
     *
     */
    public GPABillingMasterPK(){
    }
	/**
	 *
	 * @param companyCode
	 * @param billingBasis
	 * @param gpaCode
	 */
    public GPABillingMasterPK(String companyCode,
            String gpaCode,String billingBasis ) {
    }

    /**
     * @param other
     * @return boolean
     */
	public boolean equals(Object other) {
		boolean isEqual = false;
		if(other != null){
			isEqual = this.hashCode() == other.hashCode();
		}
		return isEqual;
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(gpaCode).
				append(billingBasis).
				toString().hashCode();
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
	 * @param gpaCode
	 */
	public void setGpaCode(java.lang.String gpaCode) {
		this.gpaCode=gpaCode;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getGpaCode() {
		return this.gpaCode;
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
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(84);
		sbul.append("GPABillingMasterPK [ ");
		sbul.append("billingBasis '").append(this.billingBasis);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', gpaCode '").append(this.gpaCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
