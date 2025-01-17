/*
 * ULDPK.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;


/**
 * This class is used to represent the primary key of the ULD.
 * 
 * @author A-1347
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Embeddable
public class ULDPK implements Serializable {
    /**
     * 
     */
    private String companyCode;
    /**
     * 
     */
    private String uldNumber;

    
	/**
	 * Constructor
	 */
	public ULDPK() {
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 */	
	public ULDPK(String companyCode, String uldNumber){
		this.companyCode=companyCode;
		this.uldNumber=uldNumber;
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
		return new StringBuilder(companyCode)
		.append(uldNumber)
		.toString().hashCode();
	 }

	public void setUldNumber(java.lang.String uldNumber) {
		this.uldNumber=uldNumber;
	}

	public java.lang.String getUldNumber() {
		return this.uldNumber;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:18 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(49);
		sbul.append("ULDPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', uldNumber '").append(this.uldNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
