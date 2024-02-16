/*
 * MailSLAMasterPK.java Created on Apr 2, 2007
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
 * @author a-2524
 *
 */

@Embeddable
public class MailSLAMasterPK implements Serializable {

	/**
	 * companyCode
	 */
    private String companyCode;
    /**
     * slaId ID
     */
    private String slaId;
    /**
     * 
     */
    public MailSLAMasterPK() {  
    }
    /**
     * 
     * @param companyCode
     * @param slaId
     */
    public MailSLAMasterPK(String companyCode, String slaId) {
        
        this.companyCode = companyCode;
        this.slaId = slaId;
    }
    
    /**
     * @param other
     * @return boolean
     */
	public boolean equals(Object other) {
		if(other != null){
			return hashCode() == other.hashCode();
		}
		else {
			return false;
		}
		
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(slaId).
				toString().hashCode();
	}	
		

	/**
	 * 
	 * @param slaId
	 */
	public void setSlaId(java.lang.String slaId) {
		this.slaId=slaId;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getSlaId() {
		return this.slaId;
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
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(55);
		sbul.append("MailSLAMasterPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', slaId '").append(this.slaId);
		sbul.append("' ]");
		return sbul.toString();
	}
}