/*
 * MailInvoicProductDtlPK.java Created on Jul 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-2408
 *
 */
@Embeddable
public class MailInvoicProductDtlPK implements Serializable {
	
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	private String receptacleIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	public MailInvoicProductDtlPK(){
	
	}
	 
   /**
 * @param companyCode
 * @param invoiceKey
 * @param poaCode
 * @param receptacleIdentifier
 * @param sectorOrigin
 * @param sectorDestination
 */
public MailInvoicProductDtlPK(String companyCode, String invoiceKey,String poaCode,String receptacleIdentifier,
		   String sectorOrigin,String sectorDestination) {
       super();
       this.companyCode = companyCode;
       this.invoiceKey = invoiceKey;
       this.poaCode=poaCode;
       this.receptacleIdentifier=receptacleIdentifier;
       this.sectorOrigin=sectorOrigin;
       this.sectorDestination=sectorDestination;
   }

   /**
    * @param other
    * @return boolean
    */
	public boolean equals(Object other) {
		if(other != null ){
			return (hashCode() == other.hashCode());
		}else {
			return false;
		}
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(invoiceKey).
				append(poaCode).
				append(receptacleIdentifier).
				append(sectorOrigin).
				append(sectorDestination).
				toString().hashCode();
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	/**
	 * @return Returns the invoiceKey.
	 */
	public String getInvoiceKey() {
		return invoiceKey;
	}
	/**
	 * @param invoiceKey The invoiceKey to set.
	 */
	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}
	/**
	 * @return Returns the receptacleIdentifier.
	 */
	public String getReceptacleIdentifier() {
		return receptacleIdentifier;
	}
	/**
	 * @param receptacleIdentifier The receptacleIdentifier to set.
	 */
	public void setReceptacleIdentifier(String receptacleIdentifier) {
		this.receptacleIdentifier = receptacleIdentifier;
	}
	/**
	 * @return Returns the sectorDestination.
	 */
	public String getSectorDestination() {
		return sectorDestination;
	}
	/**
	 * @param sectorDestination The sectorDestination to set.
	 */
	public void setSectorDestination(String sectorDestination) {
		this.sectorDestination = sectorDestination;
	}
	/**
	 * @return Returns the sectorOrigin.
	 */
	public String getSectorOrigin() {
		return sectorOrigin;
	}
	/**
	 * @param sectorOrigin The sectorOrigin to set.
	 */
	public void setSectorOrigin(String sectorOrigin) {
		this.sectorOrigin = sectorOrigin;
	}
	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}
	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(171);
		sbul.append("MailInvoicProductDtlPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', invoiceKey '").append(this.invoiceKey);
		sbul.append("', poaCode '").append(this.poaCode);
		sbul.append("', receptacleIdentifier '").append(
				this.receptacleIdentifier);
		sbul.append("', sectorDestination '").append(this.sectorDestination);
		sbul.append("', sectorOrigin '").append(this.sectorOrigin);
		sbul.append("' ]");
		return sbul.toString();
	}
	
	

}
