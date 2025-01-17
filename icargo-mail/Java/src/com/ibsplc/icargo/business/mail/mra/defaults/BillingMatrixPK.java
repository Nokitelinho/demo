/*
 * BillingMatrixPK.java Created on Feb 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author a-1556
 * 
 */
@Embeddable
public class BillingMatrixPK implements Serializable {

	/**
	 * companyCode
	 */
	private String companyCode;

	/**
	 * billingMatrix ID
	 */
	private String billingMatrixID;

	/**
	 * 
	 */
	public BillingMatrixPK() {
	}

	/**
	 * 
	 * @param companyCode
	 * @param billingMatrixID
	 */
	public BillingMatrixPK(String companyCode, String billingMatrixID) {

		this.companyCode = companyCode;
		this.billingMatrixID = billingMatrixID;
	}

	/**
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		boolean isEqual = false;
		if (other != null) {
			isEqual = this.hashCode() == other.hashCode();
		}
		return isEqual;
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).append(billingMatrixID).toString()
				.hashCode();
	}

	/**
	 * 
	 * @param billingMatrixID
	 */
	public void setBillingMatrixID(java.lang.String billingMatrixID) {
		this.billingMatrixID = billingMatrixID;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getBillingMatrixID() {
		return this.billingMatrixID;
	}

	/**
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode = companyCode;
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
		StringBuilder sbul = new StringBuilder(65);
		sbul.append("BillingMatrixPK [ ");
		sbul.append("billingMatrixID '").append(this.billingMatrixID);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
