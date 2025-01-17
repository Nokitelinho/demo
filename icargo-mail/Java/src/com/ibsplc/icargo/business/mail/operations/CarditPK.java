/*
 * CarditPK.java Created on Jun 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * TODO Add the purpose of this class
 * 
 * @author A-1739
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 7, 2006 A-1739 First Draft
 *  		  July 16, 2007			A-1739		EJB3 Final changes
 */
@Embeddable
public class CarditPK implements Serializable {

	/**
	 * CompanyCode
	 */
	private String companyCode;

	/**
	 * Cardit Key
	 */
	private String carditKey;
    /**
     * @return
     */
	@Override
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(carditKey)
				.toString().hashCode();
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

	public void setCarditKey(java.lang.String carditKey) {
		this.carditKey=carditKey;
	}

	public java.lang.String getCarditKey() {
		return this.carditKey;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:52 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(52);
		sbul.append("CarditPK [ ");
		sbul.append("carditKey '").append(this.carditKey);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
