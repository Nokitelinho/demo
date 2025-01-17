/*
 * AirlineExceptionsPK.java Created on Jun 15, 2007
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
public class AirlineExceptionsPK implements Serializable {

	/**
	 * CompanyCode
	 */

	private String companyCode;

	/**
	 * airline Identifier
	 */

	private int airlineIdentifier;

	/**
	 * exception Code
	 */

	private String exceptionCode;

	/**
	 * serial Number
	 */

	private int serialNumber;
	/**
	 * invoice Number
	 */

	private String invoiceNumber;

	/**
	 * clearance period
	 */
	private String clearancePeriod;
	 /**
     * @param
     * @return int
     */
	@Override
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(airlineIdentifier)
				.append(exceptionCode).append(serialNumber).append(invoiceNumber)
				.append(clearancePeriod).toString().hashCode();
	}
    /**
     * @param other
     * @return
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
	    *
	    * @param companyCode
	    * @param airlineIdentifier
	    * @param exceptionCode
	    * @param serialNumber
	    * @param invoiceNumber
	    * @param clearancePeriod
	    * @return AirlineExceptionsPK
	    */
	   public AirlineExceptionsPK(String companyCode, int airlineIdentifier,
	                 String exceptionCode, int serialNumber, String invoiceNumber,String clearancePeriod) {
	       this.companyCode = companyCode;
	       this.airlineIdentifier = airlineIdentifier;
	       this.exceptionCode = exceptionCode;
	       this.serialNumber = serialNumber;
	       this.invoiceNumber = invoiceNumber;
	       this.clearancePeriod=clearancePeriod;
	   }

	   /**
	    *
	    */
	   public AirlineExceptionsPK() {
	   }

   /**
    * 
    * @param serialNumber
    */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber=serialNumber;
	}

	/**
	 * 
	 * @return
	 */
	public int getSerialNumber() {
		return this.serialNumber;
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
	 * @param exceptionCode
	 */
	public void setExceptionCode(java.lang.String exceptionCode) {
		this.exceptionCode=exceptionCode;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getExceptionCode() {
		return this.exceptionCode;
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
		StringBuilder sbul = new StringBuilder(172);
		sbul.append("AirlineExceptionsPK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', clearancePeriod '").append(this.clearancePeriod);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', exceptionCode '").append(this.exceptionCode);
		sbul.append("', invoiceNumber '").append(this.invoiceNumber);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
