/*
 * PostalAdministrationAuditPK.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * 
 * @author A-3109
 * 
 */
@KeyTable(table = "MALPOAAUDKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "AUDSEQ_GEN", table = "MALPOAAUDKEY", key = "POAAUD_SEQNUM")
@Embeddable
public class PostalAdministrationAuditPK implements Serializable {

	/**
	 * companyCode
	 */
	private String companyCode;

	/**
	 * poaCode
	 */
	private String poaCode;

	/**
	 * serialNumber
	 */
	private int serialNumber;

	public PostalAdministrationAuditPK() {

	}

	/**
	 * 
	 * @param companyCode
	 * @param poaCode
	 */
	public PostalAdministrationAuditPK(String companyCode, String poaCode) {
		this.companyCode = companyCode;
		this.poaCode = poaCode;
	}

	/**
	 * @param other
	 * @return
	 */

	public boolean equals(Object other) {
		boolean isEqual = false;
		if (other != null) {
			isEqual = hashCode() == other.hashCode();
		}
		return isEqual;
	}

	/**
	 * @return
	 */
	public int hashCode() {

		return new StringBuffer(companyCode).append(poaCode)
				.append(serialNumber).toString().hashCode();
	}

	/**
	 * @return Returns the companyCode.
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the poaCode.
	 */
	@KeyCondition(column = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode
	 *            The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the serialNumber.
	 */
	@Key(generator = "AUDSEQ_GEN", startAt = "1")
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber
	 *            The serialNumber to set.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(93);
		sbul.append("PostalAdministrationAuditPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', poaCode '").append(this.poaCode);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

}