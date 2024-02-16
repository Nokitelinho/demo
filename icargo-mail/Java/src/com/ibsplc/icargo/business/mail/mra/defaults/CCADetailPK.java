/*
 * CCADetailPK.java Created on July-15-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;
/*
 * *@author A-3447
 * 
 */
import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author a-3447
 */

@Embeddable
public class CCADetailPK implements Serializable {

	private String companyCode;

	private long mailSequenceNumber;

	private String mcaRefNumber;

	
	/**
	 * constructor
	 * 
	 */
	public CCADetailPK() {
	}

	
	/**
	 * @param companyCode
	 * @param mailSequenceNumber
	 * @param mcaRefNumber
	 */
	public CCADetailPK(String companyCode, long mailSequenceNumber,
			String mcaRefNumber) {
		super();
		this.companyCode = companyCode;
		this.mailSequenceNumber = mailSequenceNumber;
		this.mcaRefNumber = mcaRefNumber;
	}


	/**
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		if (other != null) {
			return hashCode() == other.hashCode();
		} else {
			return false;
		}

	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).append(mailSequenceNumber).append(
				mcaRefNumber).toString().hashCode();

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
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	/**
	 * @return the mcaRefNumber
	 */
	public String getMcaRefNumber() {
		return mcaRefNumber;
	}

	/**
	 * @param mcaRefNumber the mcaRefNumber to set
	 */
	public void setMcaRefNumber(String mcaRefNumber) {
		this.mcaRefNumber = mcaRefNumber;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CCADetailPK [companyCode=" + companyCode
				+ ", mailSequenceNumber=" + mailSequenceNumber
				+ ", mcaRefNumber=" + mcaRefNumber + "]";
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
