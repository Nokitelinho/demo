/*
 * MRAMemoInInvoicePK.java Created on Jun 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;


import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * TODO Add the purpose of this class
 * 
 * @author A-2407
 * 
 */

@Embeddable
public class MRAMemoInInvoicePK implements Serializable {

	/**
	 * CompanyCode
	 */
	
	private String companyCode;

	/**
	 * airline Identifier
	 */
	
	private int airlineIdentifier;
	
	/**
	 * interline Billing Type 
	 */
	
	private String interlineBlgType;
	
	/**
	 * invoicer Number
	 */
	
	private String invoiceNumber;
	
	/**
	 * memo Number
	 */
	
	private String memoCode;
	
	/**
	 * clearance Period
	 */
	private String clearancePeriod;
    /**
     * @return int
     */
	@Override
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(airlineIdentifier)
				.append(interlineBlgType).append(invoiceNumber)
				.append(memoCode).append(clearancePeriod).toString().hashCode();
	}
    /**
     * @param other
     * @return boolean
     */
	@Override
	public boolean equals(Object other) {
		boolean isEqual = false;
		if(other != null){
			isEqual = this.hashCode() == other.hashCode();
		}
		return isEqual;
	}
	
	/**
    * 
    * @param companyCode
    * @param airlineIdentifier
    * @param memoCode
    * @param invoiceNumber
    * @param interlineBlgType
    * @param clearancePeriod
    * @return MRAMemoInInvoicePK
    */
   public MRAMemoInInvoicePK(String companyCode, int airlineIdentifier,
                 String memoCode, String invoiceNumber,
                 String interlineBlgType , String clearancePeriod) {
       this.companyCode = companyCode;
       this.airlineIdentifier = airlineIdentifier;
       this.memoCode = memoCode;
       this.invoiceNumber = invoiceNumber;
       this.interlineBlgType = interlineBlgType;
       this.clearancePeriod = clearancePeriod;
   }

   /**
    *
    */
   public MRAMemoInInvoicePK() {
   }

   /**
    * 
    * @param invoiceNumber
    */
	public void setInvoiceNumber(java.lang.String invoiceNumber) {
		this.invoiceNumber=invoiceNumber;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	/**
	 * 
	 * @param clearancePeriod
	 */
	public void setClearancePeriod(java.lang.String clearancePeriod) {
		this.clearancePeriod=clearancePeriod;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getClearancePeriod() {
		return this.clearancePeriod;
	}

	/**
	 * 
	 * @param interlineBlgType
	 */
	public void setInterlineBlgType(java.lang.String interlineBlgType) {
		this.interlineBlgType=interlineBlgType;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getInterlineBlgType() {
		return this.interlineBlgType;
	}

	/**
	 * 
	 * @param memoCode
	 */
	public void setMemoCode(java.lang.String memoCode) {
		this.memoCode=memoCode;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getMemoCode() {
		return this.memoCode;
	}

	/**
	 * 
	 * @param airlineIdentifier
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier=airlineIdentifier;
	}

	/**
	 * 
	 * @return
	 */
	public int getAirlineIdentifier() {
		return this.airlineIdentifier;
	}

	/**
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:53 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(170);
		sbul.append("MRAMemoInInvoicePK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', clearancePeriod '").append(this.clearancePeriod);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', interlineBlgType '").append(this.interlineBlgType);
		sbul.append("', invoiceNumber '").append(this.invoiceNumber);
		sbul.append("', memoCode '").append(this.memoCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
