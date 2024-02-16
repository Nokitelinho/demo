/*
 * MailIncentiveDetailPK.java Created on SEP 10, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 * @author A-6986
 *
 */

@Embeddable
public class MailIncentiveDetailPK implements Serializable {

	private String companyCode;

	private int incentiveSerialNumber;

	private int sequenceNumber;


	/**
	 * @return the companyCode
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the incentiveSerialNumber
	 */
	@KeyCondition(column = "INSSERNUM")
	public int getIncentiveSerialNumber() {
		return incentiveSerialNumber;
	}

	/**
	 * @param incentiveSerialNumber the incentiveSerialNumber to set
	 */
	public void setIncentiveSerialNumber(int incentiveSerialNumber) {
		this.incentiveSerialNumber = incentiveSerialNumber;
	}

	/**
	 * @return the sequenceNumber
	 */
	@KeyCondition(column = "SEQNUM")
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
     *@param other
     *@return
     *
     */
    public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
  /**
   * @return
   */
	public int hashCode() {

		return new StringBuffer(companyCode).
				append(incentiveSerialNumber).

				append(sequenceNumber).
				toString().hashCode();
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(106);
		sbul.append("MailIncentiveDetailPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', incentiveSerialNumber '").append(this.incentiveSerialNumber);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}


}
