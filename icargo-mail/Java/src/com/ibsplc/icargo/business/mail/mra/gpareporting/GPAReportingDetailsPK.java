/*
 * GPAReportingDetailsPK.java Created on Dec 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;


/**
 * @author A-1453
 *
 */
@KeyTable(table="MTKGPARPTDTLKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="KEY_GEN",table="MTKGPARPTDTLKEY",key="MTKGPARPTDTLKEY_SEQ")
@Embeddable
public class GPAReportingDetailsPK implements Serializable {

	/**
	 * companyCode
	 */

	private String companyCode;

	/**
	 *  poaCode
	 */

	private String poaCode;

	/**
	 * billingbasis
	 */

	private String billingBasis;

	/** Removed from Pk as part of AirNZ 165
	 * reportingFrom
	 *//*

	private String reportingFromString;

	*//**
	 * reportingTo
	 *//*

	private String reportingToString;
	  */
	/**
	 * sequenceNumber
	 */

	private int sequenceNumber;

	private String billingIdentifier;
	
	
	

	/**
	 * Default constructor
	 *
	 */
	public GPAReportingDetailsPK() {
	}

	/**
	 * 
	 * @param companyCode
	 * @param poaCode
	 * @param billingBasis
	 * @param reportingFrom
	 * @param reportingTo
	 * @param sequenceNumber
	 */
	public GPAReportingDetailsPK(String companyCode, String poaCode,
			String billingBasis, String billingIdentifier,
			int sequenceNumber) {
		this.companyCode = companyCode;
		this.poaCode = poaCode;
		this.billingBasis = billingBasis;
		this.billingIdentifier=billingIdentifier;
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		boolean isEqual = false;
		if(other != null){
			isEqual = this.hashCode() == other.hashCode();
		}
		return isEqual;
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return this.toString().hashCode();
	}

	/**
	 * 
	 * @param billingBasis
	 */
	public void setBillingBasis(java.lang.String billingBasis) {
		this.billingBasis=billingBasis;
	}
	/**
	 * 
	 * @return
	 */
	@KeyCondition(column =  "BLGBAS")
	public java.lang.String getBillingBasis() {
		return this.billingBasis;
	}

	/**
	 * 
	 * @param sequenceNumber
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber=sequenceNumber;
	}
	/**
	 * 
	 * @return
	 */
	@Key(generator="KEY_GEN",startAt="1")
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}


	/**
	 * 
	 * @param poaCode
	 */
	public void setPoaCode(java.lang.String poaCode) {
		this.poaCode=poaCode;
	}
	/**
	 * 
	 * @return
	 */
	@KeyCondition(column =  "GPACOD")
	public java.lang.String getPoaCode() {
		return this.poaCode;
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
	@KeyCondition(column =  "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}
	
	/**
	 * @return the billingIdentifier
	 */
	@KeyCondition(column =  "BILIDR")
	public String getBillingIdentifier() {
		return billingIdentifier;
	}

	
	/**
	 * @param billingIdentifier the billingIdentifier to set
	 */
	public void setBillingIdentifier(String billingIdentifier) {
		this.billingIdentifier = billingIdentifier;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(142);
		sbul.append("GPAReportingDetailsPK [ ");
		sbul.append("billingBasis '").append(this.billingBasis);
		sbul.append("', billingIdentifier '").append(this.billingIdentifier);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', poaCode '").append(this.poaCode);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
