/*
 * CarditReferencePK.java Created on JAN 04, 2009
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
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		 Author			   Description
 * -------------------------------------------------------------------------
 *  0.1			JAN 04, 2009	    	A-3227 RENO K ABRAHAM	First Draft
 */
@KeyTable(table = "MALCDTREFKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "MALCDTREFKEY", key = "RFF_SER_NUM")
@Embeddable 
public class CarditReferencePK implements Serializable{
	
	private String companyCode;	
	private String carditKey;
	private int referrenceSerialNumber;
	

	/**
	 * The Hashcode Implementation
	 * @return
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(carditKey)
				.append(referrenceSerialNumber).toString().hashCode();
	}

	/**
	 * The Equals Method
	 * @param other
	 * @return 
	 */
	public boolean equals(Object other) {
		boolean isEqual = false;
		if(other != null ){
			isEqual = hashCode() == other.hashCode();
		}
		return isEqual;		
	}

	/**
	 * @return the carditKey
	 */
	@KeyCondition(column = "CDTKEY")
	public String getCarditKey() {
		return carditKey;
	}
	/**
	 * @param carditKey the carditKey to set
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}
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
	 * @return the referrenceSerialNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1")
	public int getReferrenceSerialNumber() {
		return referrenceSerialNumber;
	}

	/**
	 * @param referrenceSerialNumber the referrenceSerialNumber to set
	 */
	public void setReferrenceSerialNumber(int referrenceSerialNumber) {
		this.referrenceSerialNumber = referrenceSerialNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:52 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(95);
		sbul.append("CarditReferencePK [ ");
		sbul.append("carditKey '").append(this.carditKey);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', referrenceSerialNumber '").append(
				this.referrenceSerialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

}
