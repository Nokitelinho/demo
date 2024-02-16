/*
 * UserAllowableOfficesPK.java Created on Jun 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.business.admin.user;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * 
 * This class denotes the primary key elements associated with user office
 * details entity
 * 
 * @author A-1417
 */
@Embeddable
public class UserAllowableOfficesPK implements Serializable {

	/**
	 * The field is used for denoting the company
	 */
	private String companyCode;

	/**
	 * The field is used for identifying the user
	 */
	private String userCode;

	/**
	 * The offices associated with the user
	 */
	private String officeCode;

	/**
	 * to check equals
	 * 
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

	/**
	 * to create HashCode
	 * 
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).append(userCode)
				.append(officeCode).toString().hashCode();
	}

	public void setUserCode(String userCode) {
		this.userCode=userCode;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode=officeCode;
	}

	public String getOfficeCode() {
		return this.officeCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode=companyCode;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:36 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(87);
		sbul.append("UserAllowableOfficesPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', officeCode '").append(this.officeCode);
		sbul.append("', userCode '").append(this.userCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}