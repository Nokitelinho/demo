/*
 * MailContractBillingDetailPK.java Created on March 30, 2007
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
 * 
 * @author a-2518
 * 
 */
@Embeddable
public class MailContractBillingDetailPK implements Serializable {

	/**
	 * Company code
	 */
	private String companyCode;

	/**
	 * Mail contract reference number
	 */
	private String contractReferenceNumber;

	/**
	 * Billing matrix code
	 */
	private String billingMatrixCode;

	/**
	 * Default constructor
	 */
	public MailContractBillingDetailPK() {
	}

	/**
	 * 
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param billingMatrixCode
	 */
	public MailContractBillingDetailPK(String companyCode,
			String contractReferenceNumber, String billingMatrixCode) {

		this.companyCode = companyCode;
		this.contractReferenceNumber = contractReferenceNumber;
		this.billingMatrixCode = billingMatrixCode;
	}

	/**
	 * @param object
	 * @return boolean
	 */
	public boolean equals(Object object) {
		boolean isEqual = false;
		if (object != null) {
			isEqual = this.hashCode() == object.hashCode();
		}
		return isEqual;
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).append(contractReferenceNumber)
				.append(billingMatrixCode).toString().hashCode();
	}

	/**
	 * 
	 * @param contractReferenceNumber
	 */
	public void setContractReferenceNumber(java.lang.String contractReferenceNumber) {
		this.contractReferenceNumber=contractReferenceNumber;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getContractReferenceNumber() {
		return this.contractReferenceNumber;
	}

	/**
	 * 
	 * @param billingMatrixCode
	 */
	public void setBillingMatrixCode(java.lang.String billingMatrixCode) {
		this.billingMatrixCode=billingMatrixCode;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getBillingMatrixCode() {
		return this.billingMatrixCode;
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
		StringBuilder sbul = new StringBuilder(114);
		sbul.append("MailContractBillingDetailPK [ ");
		sbul.append("billingMatrixCode '").append(this.billingMatrixCode);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', contractReferenceNumber '").append(
				this.contractReferenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
