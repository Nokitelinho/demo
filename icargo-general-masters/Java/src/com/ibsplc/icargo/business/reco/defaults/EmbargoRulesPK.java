/*
 * EmbargoPK.java Created on Jul 13, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults;

import java.io.Serializable;


import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * Primary key class of Embargo
 * 
 * @author A-1358
 * 
 */


@KeyTable(table="RECMSTKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN",table="RECMSTKEY",key="EMBVER_SEQ")
@Embeddable
public class EmbargoRulesPK implements Serializable {


	/**
	 * Comapny Code
	 */
	private String companyCode;


	/**
	 * EmbargoReferenceNumber
	 */
	private String embargoReferenceNumber;
	
	private int embargoVersion;

	

	/**
     * Checks whether to objects are equal 
     *@param other
     *@return boolean 
     *
     */
 
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

	@Key(generator = "ID_GEN", startAt = "1")
	public int getEmbargoVersion() {
		return embargoVersion;
	}

	public void setEmbargoVersion(int embargoVersion) {
		this.embargoVersion = embargoVersion;
	}

	/**
     * Returns HashCode 
     * @return int
     */
	public int hashCode() {
		return new StringBuffer(companyCode).append(embargoReferenceNumber).append(embargoVersion)
				.toString().hashCode();
	}

	public void setEmbargoReferenceNumber(java.lang.String embargoReferenceNumber) {
		this.embargoReferenceNumber=embargoReferenceNumber;
	}
	/* @Key(generator = "ID_GEN", startAt = "1") */
	@KeyCondition(column = "REFNUM")
	public java.lang.String getEmbargoReferenceNumber() {
		return this.embargoReferenceNumber;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:12 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(97);
		sbul.append("EmbargoRulesPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', embargoReferenceNumber '").append(
				this.embargoReferenceNumber);
		sbul.append("', embargoVersion '").append(this.embargoVersion);
		sbul.append("' ]");
		return sbul.toString();
	}
}
