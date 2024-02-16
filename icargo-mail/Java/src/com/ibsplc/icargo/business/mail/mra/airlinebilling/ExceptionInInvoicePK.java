/*
 * ExceptionInInvoicePK.java Created on Feb 20, 2007
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
 * @author A-2391 
 */
@Embeddable
public class ExceptionInInvoicePK implements Serializable  {
	/**
	 * CompanyCode
	 */
	private String companyCode;
	/**
	 * airline Identifier
	 */
	private int airlineIdentifier;
	/**
	 * invoicer Number
	 */
	private String invoiceNumber;
	/**
	 * clearance period
	 */
	private String clearancePeriod;
	/**
     * Default constructor
     *
     */
    public ExceptionInInvoicePK(){
    }
	/**
	 *
	 * @param companyCode
	 * @param airlineCode
	 * @param invoiceNumber
	 * @param clearancePeriod
	 */
    public ExceptionInInvoicePK(String companyCode,
            int airlineCode,String invoiceNumber,String clearancePeriod) {
    }

    /**
     * @param other
     * @return boolean
     */
    @Override
	public boolean equals(Object other) {
    	boolean isEqual = false;
		if(other != null ){
			isEqual = hashCode() == other.hashCode();
		}
		return isEqual;		
	}

	/**
	 * @return int
	 */
	@Override
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(airlineIdentifier).
				append(invoiceNumber).
				append(clearancePeriod).
				toString().hashCode();
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
		StringBuilder sbul = new StringBuilder(124);
		sbul.append("ExceptionInInvoicePK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', clearancePeriod '").append(this.clearancePeriod);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', invoiceNumber '").append(this.invoiceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
