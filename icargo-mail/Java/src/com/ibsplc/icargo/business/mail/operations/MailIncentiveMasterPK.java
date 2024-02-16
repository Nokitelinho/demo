/*
 * MailIncentiveMasterPK.java Created on SEP 10, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 * @author A-6986
 *
 */
@SequenceKeyGenerator(name="SEQ_GEN",sequence="MALMRAINCCFG_SEQ")
@Embeddable
public class MailIncentiveMasterPK implements Serializable{

	private String companyCode;

	private int incentiveSerialNumber;

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
	@Key(generator = "SEQ_GEN", startAt = "1")
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
				toString().hashCode();
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(106);
		sbul.append("MailIncentiveMasterPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', incentiveSerialNumber '").append(this.incentiveSerialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}




}
