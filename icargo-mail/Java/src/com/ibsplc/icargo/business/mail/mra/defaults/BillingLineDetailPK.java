/*
 * BillingLineDetailPK.java Created on Jun 24, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 * @author A-5255 
 * @version	0.1, Jun 24, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jun 24, 2015	     A-5255		First draft
 */

public class BillingLineDetailPK implements Serializable {

	private String companyCode;
	private String billingMatrixID;
	private int billingLineSequenceNumber;
	private String chargeType;
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
	 * @return the billingMatrixID
	 */
	@KeyCondition(column="BLGMTXCOD")
	public String getBillingMatrixID() {
		return billingMatrixID;
	}
	/**
	 * @param billingMatrixID the billingMatrixID to set
	 */
	public void setBillingMatrixID(String billingMatrixID) {
		this.billingMatrixID = billingMatrixID;
	}
	/**
	 * @return the billingLineSequenceNumber
	 */
	@KeyCondition(column="BLGLINSEQNUM")
	public int getBillingLineSequenceNumber() {
		return billingLineSequenceNumber;
	}
	/**
	 * @param billingLineSequenceNumber the billingLineSequenceNumber to set
	 */
	public void setBillingLineSequenceNumber(int billingLineSequenceNumber) {
		this.billingLineSequenceNumber = billingLineSequenceNumber;
	}
	/**
	 * @return the chargeType
	 */
	@KeyCondition(column="CHGTYP")
	public String getChargeType() {
		return chargeType;
	}
	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	
	
}
