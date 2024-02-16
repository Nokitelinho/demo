/*
 * ULDTransactionPK.java Created on Jan 6, 2006
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
 * @author A-1496
 *
 */
@Embeddable
@KeyTable(table = "ULDTXNKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "ULDTXNKEY", key = "MAX_SERNUM") 
public class ULDTransactionPK  implements Serializable {
	/**
	 * 
	 */

    private String companyCode;
     /**
      * 
      */

     private String uldNumber;
    /**
     * 
     */

    private int transactionRefNumber;
    
    /**
     * Default constructor
     */
	public ULDTransactionPK() {
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param transactionRefNumber
	 */
    public ULDTransactionPK(
    	String companyCode, 
    	String uldNumber,
    	int transactionRefNumber) {
    	this.companyCode = companyCode;
    	this.uldNumber = uldNumber;
    	this.transactionRefNumber = transactionRefNumber; 
	}
    /**
     * This method tests for equality of one instance of this class with
     * the other.
     * @param other - another object to test for equality
     * @return boolean - returns true if equal
     */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	/**
	 * This method generates the hashcode of an instance
	 * @return int - returns the hashcode of the instance
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(uldNumber).
				append(transactionRefNumber).
				toString().hashCode();
	}



	public void setUldNumber(java.lang.String uldNumber) {
		this.uldNumber=uldNumber;
	}
     @KeyCondition(column = "ULDNUM")    
	public java.lang.String getUldNumber() {
		return this.uldNumber;
	}

	public void setTransactionRefNumber(int transactionRefNumber) {
		this.transactionRefNumber=transactionRefNumber;
	}
     @Key(generator = "ID_GEN", startAt = "1")    
	public int getTransactionRefNumber() {
		return this.transactionRefNumber;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
     @KeyCondition(column = "CMPCOD")    
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:18 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(92);
		sbul.append("ULDTransactionPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', transactionRefNumber '").append(
				this.transactionRefNumber);
		sbul.append("', uldNumber '").append(this.uldNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
