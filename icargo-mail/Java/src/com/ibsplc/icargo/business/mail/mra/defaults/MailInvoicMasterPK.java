/*
 * MailInvoicMasterPK.java Created on Jul 19, 2007
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
public class MailInvoicMasterPK implements Serializable {
	
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	public MailInvoicMasterPK(){
	
	}
	 
   /**
 * @param companyCode
 * @param invoiceKey
 * @param poaCode
 */
public MailInvoicMasterPK(String companyCode, String invoiceKey,String poaCode) {
       super();
       this.companyCode = companyCode;
       this.invoiceKey = invoiceKey;
       this.poaCode=poaCode;
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
		StringBuilder sbul = new StringBuilder(82);
		sbul.append("MailInvoicMasterPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', invoiceKey '").append(this.invoiceKey);
		sbul.append("', poaCode '").append(this.poaCode);
		sbul.append("' ]");
		return sbul.toString();
	}
	

}
