/*
 * AttachLoyaltyProgrammePK.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-1496
 *
 *
 */
@Embeddable
@KeyTable(table = "CUSLTYPRGKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "CUSLTYPRGKEY", key = "MAX_SERNUM")
public class AttachLoyaltyProgrammePK implements Serializable{
	
	/**
	 * Company Code
	 */

	private String companyCode;
	/**
	 * Customer Code
	 */

	private String customerCode;
	/**
	 * Loyalty programme name
	 */

	private String loyaltyProgrammeCode;
	/**
	 * Sequence Number
	 */

	private String sequenceNumber;
	
	/**
	 * Default constructor
	 */
	public AttachLoyaltyProgrammePK() {
	}
	
/***
 * 
 * @param companyCode
 * @param customerCode
 * @param loyaltyProgrammeCode
 * @param sequenceNumber
 */
	public AttachLoyaltyProgrammePK(String companyCode, String customerCode,
			String loyaltyProgrammeCode , String sequenceNumber) {
		this.companyCode = companyCode;
		this.customerCode = customerCode;
		this.loyaltyProgrammeCode = loyaltyProgrammeCode;
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	/**
	 * @return int 
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
		append(customerCode).
		append(loyaltyProgrammeCode).
		append(sequenceNumber).
		toString().hashCode();
	}
	
	

	public void setSequenceNumber(java.lang.String sequenceNumber) {
		this.sequenceNumber=sequenceNumber;
	}
	@Key(generator = "ID_GEN", startAt = "1")
	public java.lang.String getSequenceNumber() {
		return this.sequenceNumber;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setCustomerCode(java.lang.String customerCode) {
		this.customerCode=customerCode;
	}
	@KeyCondition(column = "CUSCOD") 
	public java.lang.String getCustomerCode() {
		return this.customerCode;
	}

	public void setLoyaltyProgrammeCode(java.lang.String loyaltyProgrammeCode) {
		this.loyaltyProgrammeCode=loyaltyProgrammeCode;
	}
	@KeyCondition(column = "LTYPRGCOD") 
	public java.lang.String getLoyaltyProgrammeCode() {
		return this.loyaltyProgrammeCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:49 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(129);
		sbul.append("AttachLoyaltyProgrammePK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', customerCode '").append(this.customerCode);
		sbul.append("', loyaltyProgrammeCode '").append(
				this.loyaltyProgrammeCode);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}