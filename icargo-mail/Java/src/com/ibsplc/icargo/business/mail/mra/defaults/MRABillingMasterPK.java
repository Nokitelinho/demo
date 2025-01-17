/*
 * MRABillingMasterPK.java Created on Apr 2, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 * @author a-1747
 *
 */

@Embeddable
public class MRABillingMasterPK implements Serializable {

	private String companyCode;
	private long mailSeqNumber;
	
    

    /**
	 * @param companyCode
	 * @param mailSeqNumber
	 * @param mailIdentifier
	 * @param consignmentDocNumber
	 * @param consignmentSeqNumber
	 * @param postalAuthorityCode
	 */
	public MRABillingMasterPK(String companyCode, long mailSeqNumber) {
		super();
		this.companyCode = companyCode;
		this.mailSeqNumber = mailSeqNumber;
	}

	/**
	 * 
	 */
	public MRABillingMasterPK() {
		super();
	}

	/**
     * @param other
     * @return boolean
     */
	public boolean equals(Object other) {
		if(other != null){
			return hashCode() == other.hashCode();
		}
		else {
			return false;
		}

	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(mailSeqNumber).toString().hashCode();
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
	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}
	
	/**
	 * @return the mailSeqNumber
	 */
	@KeyCondition(column = "MALSEQNUM")
	public long getMailSeqNumber() {
		return mailSeqNumber;
	}

	/**
	 * @param mailSeqNumber the mailSeqNumber to set
	 */
	public void setMailSeqNumber(long mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}

	
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(149);
		sbul.append("MRABillingMasterPK [ ");
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', mailSeqNumber '").append(this.mailSeqNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
