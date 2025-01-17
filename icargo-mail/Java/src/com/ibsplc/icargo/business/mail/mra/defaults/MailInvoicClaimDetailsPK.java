/*
 * MailInvoicClaimDetailsPK.java Created on Aug 03, 2007
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
 * @author A-2270
 *
 */
@Embeddable
public class MailInvoicClaimDetailsPK implements Serializable {
	
	
	private String companyCode;
	
	private String receptacleIdentifier;
	
	private String sectorIdentifier;
	
	public MailInvoicClaimDetailsPK(){
	
	}
	 
  
/**
 * @param companyCode
 * @param receptacleIdentifier
 * @param sectorIdentifier
 */
public MailInvoicClaimDetailsPK(String companyCode, String receptacleIdentifier,String sectorIdentifier) {
       super();
       this.companyCode           = companyCode;
       this.receptacleIdentifier  = receptacleIdentifier;
       this.sectorIdentifier      = sectorIdentifier;
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
				append(receptacleIdentifier).
				append(sectorIdentifier).
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
	 * @return the receptacleIdentifier
	 */
	public String getReceptacleIdentifier() {
		return receptacleIdentifier;
	}

	/**
	 * @param receptacleIdentifier the receptacleIdentifier to set
	 */
	public void setReceptacleIdentifier(String receptacleIdentifier) {
		this.receptacleIdentifier = receptacleIdentifier;
	}

	/**
	 * @return the sectorIdentifier
	 */
	public String getSectorIdentifier() {
		return sectorIdentifier;
	}

	/**
	 * @param sectorIdentifier the sectorIdentifier to set
	 */
	public void setSectorIdentifier(String sectorIdentifier) {
		this.sectorIdentifier = sectorIdentifier;
	}


	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(107);
		sbul.append("MailInvoicClaimDetailsPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', receptacleIdentifier '").append(
				this.receptacleIdentifier);
		sbul.append("', sectorIdentifier '").append(this.sectorIdentifier);
		sbul.append("' ]");
		return sbul.toString();
	}
	
	

}
