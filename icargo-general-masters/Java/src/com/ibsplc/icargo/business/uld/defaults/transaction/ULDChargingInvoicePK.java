/*
 * ULDChargingInvoicePK.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction;

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
@Embeddable
@KeyTable(table = "ULDTXNINVNUMKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "ULDTXNINVNUMKEY", key = "MAX_SERNUM")

public class ULDChargingInvoicePK implements Serializable {
    /**
     * company code
     */

    private String companyCode;
    /**
     * invoice Reference Number
     */

    private String invoiceRefNumber;
    
    /**
     * Default constructor
     */
	public ULDChargingInvoicePK() {
	}

	/**
	 * Contructor
	 * 
	 * @param companyCode
	 * @param invoiceRefNumber
	 */
    public ULDChargingInvoicePK(
    	String companyCode, 
    	String invoiceRefNumber) {
    	this.companyCode = companyCode;
    	this.invoiceRefNumber = invoiceRefNumber;
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
				append(invoiceRefNumber).
				toString().hashCode();
	}




    



	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
    @KeyCondition(column = "CMPCOD")        
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setInvoiceRefNumber(java.lang.String invoiceRefNumber) {
		this.invoiceRefNumber=invoiceRefNumber;
	}
    @Key(generator = "ID_GEN", startAt = "1",prefix="INV")
	public java.lang.String getInvoiceRefNumber() {
		return this.invoiceRefNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:18 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(71);
		sbul.append("ULDChargingInvoicePK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', invoiceRefNumber '").append(this.invoiceRefNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
