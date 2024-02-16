/*
 * CCASurchargeDetailPK.java Created on Jul 13, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Column;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 * @author A-5255 
 * @version	0.1, Jul 13, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 13, 2015	     A-5255		First draft
 */

public class CCASurchargeDetailPK implements Serializable{
	private String companyCode;
	private long mailSequenceNumber;
	private String mcaRefNumber;
	private String chargeCode;
	/**
	 * @return the companyCode
	 */
	@KeyCondition(column="CMPCOD")
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
	 * @return the mailSequenceNumber
	 */
	@KeyCondition(column="MALSEQNUM")
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
	@KeyCondition(column="MCAREFNUM")
	public String getMcaRefNumber() {
		return mcaRefNumber;
	}
	/**
	 * @param mcaRefNumber the mcaRefNumber to set
	 */
	public void setMcaRefNumber(String mcaRefNumber) {
		this.mcaRefNumber = mcaRefNumber;
	}
	/**
	 * @return the chargeCode
	 */
	@Column(name = "CHGCOD")
	public String getChargeCode() {
		return chargeCode;
	}
	/**
	 * @param chargeCode the chargeCode to set
	 */
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}
	
}
