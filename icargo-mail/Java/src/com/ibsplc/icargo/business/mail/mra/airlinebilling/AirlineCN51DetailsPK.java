/*
 * AirlineCN51DetailsPK.java Created on Feb 15, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-1946
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * feb 14, 2007 A-1946 Created
 */
@Embeddable
public class AirlineCN51DetailsPK implements Serializable {
	/**
	 * companycode
	 */
	private String companyCode;

	/**
	 * airlineidr
	 */
	private int airlineidr;

	/**
	 * interlinebillingtype
	 */
	private String interlinebillingtype;

	/**
	 * invoicenumber
	 */
	private String invoicenumber;

	/**
	 * clearancePeriod
	 */
	private String clearancePeriod;

	/**
	 * sequencenumber
	 */
	private int sequencenumber;

	/**
	 * 
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(airlineidr)
				.append(interlinebillingtype).append(invoicenumber).append(
						clearancePeriod).append(sequencenumber).toString()
				.hashCode();
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj) {
		if (obj != null) {
			return this.hashCode() == obj.hashCode();
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param airlineidr
	 * @param interlinebillingtype
	 * @param invoicenumber
	 * @param clearancePeriod
	 * @param sequencenumber
	 */
	public AirlineCN51DetailsPK(String companyCode, int airlineidr,
			String interlinebillingtype, String invoicenumber,
			String clearancePeriod, int sequencenumber) {
		this.companyCode = companyCode;
		this.airlineidr = airlineidr;
		this.interlinebillingtype = interlinebillingtype;
		this.invoicenumber = invoicenumber;
		this.clearancePeriod = clearancePeriod;
		this.sequencenumber = sequencenumber;
	}

	/**
	 * AirlineCN51DetailsPK Constructor
	 */
	public AirlineCN51DetailsPK() {
	}


	/**
	 * 
	 * @param interlinebillingtype
	 */
	public void setInterlinebillingtype(java.lang.String interlinebillingtype) {
		this.interlinebillingtype=interlinebillingtype;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getInterlinebillingtype() {
		return this.interlinebillingtype;
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
	 * @param airlineidr
	 */
	public void setAirlineidr(int airlineidr) {
		this.airlineidr=airlineidr;
	}

	/**
	 * 
	 * @return
	 */
	public int getAirlineidr() {
		return this.airlineidr;
	}

	/**
	 * 
	 * @param invoicenumber
	 */
	public void setInvoicenumber(java.lang.String invoicenumber) {
		this.invoicenumber=invoicenumber;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getInvoicenumber() {
		return this.invoicenumber;
	}

	/**
	 * 
	 * @param sequencenumber
	 */
	public void setSequencenumber(int sequencenumber) {
		this.sequencenumber=sequencenumber;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSequencenumber() {
		return this.sequencenumber;
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
		StringBuilder sbul = new StringBuilder(175);
		sbul.append("AirlineCN51DetailsPK [ ");
		sbul.append("airlineidr '").append(this.airlineidr);
		sbul.append("', clearancePeriod '").append(this.clearancePeriod);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', interlinebillingtype '").append(
				this.interlinebillingtype);
		sbul.append("', invoicenumber '").append(this.invoicenumber);
		sbul.append("', sequencenumber '").append(this.sequencenumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}