/*
 * ULDAgreementPK.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * 
 * 
 * @author A-1496
 *
 */
@KeyTable(table = "ULDAGRMNTNUMKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "ULDAGRMNTNUMKEY", key = "MAX_SERNUM")
@Embeddable
public class ULDAgreementPK implements Serializable {
    
	/**
	 * 
	 */

    private String companyCode;
	/**
	 * 
	 */

    private String agreementNumber;
    
    /**
     * Default constructor
     */
	public ULDAgreementPK() {
	}

	/**
	 * 
	 * @param companyCode
	 * @param agreementNumber
	 */
    public ULDAgreementPK(
    	String companyCode, String agreementNumber) {
    	this.companyCode = companyCode;
    	this.agreementNumber = agreementNumber;
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
		return new StringBuilder().append(companyCode).
				append(agreementNumber).
				toString().hashCode();
	}  

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	@KeyCondition(column = "CMPCOD")        
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setAgreementNumber(java.lang.String agreementNumber) {
		this.agreementNumber=agreementNumber;
	}
	@Key(generator = "ID_GEN", startAt = "1",prefix="AGN")
	public java.lang.String getAgreementNumber() {
		return this.agreementNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:17 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(64);
		sbul.append("ULDAgreementPK [ ");
		sbul.append("agreementNumber '").append(this.agreementNumber);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
