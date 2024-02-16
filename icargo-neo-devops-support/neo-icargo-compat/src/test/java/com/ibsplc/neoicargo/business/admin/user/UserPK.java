/*
 * UserPK.java Created on Jun 5, 2005
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
 * This class represents the primary key elements of the User entity.
 * 
 * @author A-1417
 */
@Embeddable
public class UserPK implements Serializable {

	/**
	 * The company code corresponding to the user
	 */
	private String companyCode;

	/**
	 * The user identifier
	 */
	private String userCode;

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
		return new StringBuffer(companyCode).append(userCode).toString()
				.hashCode();
	}


	public void setUserCode(String userCode) {
		this.userCode=userCode;
	}

	public String getUserCode() {
		return this.userCode;
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
		StringBuilder sbul = new StringBuilder(49);
		sbul.append("UserPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', userCode '").append(this.userCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
