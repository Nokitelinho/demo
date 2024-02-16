/*
 * GPAReportingFlightDetailsPK.java Created on Dec 28, 2006
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
 * 
 */
@KeyTable(table="MTKGPARPTFLTDTLKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="KEY_GEN",table="MTKGPARPTFLTDTLKEY",key="MTKGPARPTFLTKEY_SEQ")
@Embeddable
public class GPAReportingFlightDetailsPK implements Serializable{

	/**
	 * Revision History
	 *
	 * Version Date Author Description
	 *
	 * 0.1 A-1453 --Initial draft 
	 *0.2 A-3447----Updated Pk
	 *
	 */
	
	
	/**
	 * companyCode
	 */

	private String companyCode;

	/**
	 *  poaCode
	 *  
	 */

	private String poaCode;

	/**
	 * billingbasis
	 */

	private String billingBasis;

	/*/**
	 * 	 * @a-3447
	 *commented for Cr -175
	 * reportingFrom
	 *//*

	private String reportingFromString;

	***
	 * reportingTo
	 *//*

	private String reportingToString;
	  */
	/**
	 * sequenceNumber
	 */

	private int sequenceNumber;

	/**
	 * 
	 */

	private int flightSequenceNumber;

	/**
	 * 
	 *
	 */
	
	private String billingIdentifier;
	
	
	

	public GPAReportingFlightDetailsPK() {

	}

	/**
	 * 
	 * @param companyCode
	 * @param poaCode
	 * @param billingBasis
	 * @param reportingFrom
	 * @param reportingTo
	 * @param sequenceNumber
	 * @param flightSequenceNumber
	 */
	public GPAReportingFlightDetailsPK(String companyCode, String poaCode,
			String billingBasis, String billingIdentifier,int sequenceNumber, int flightSequenceNumber) {
		this.companyCode = companyCode;
		this.poaCode = poaCode;
		this.billingBasis = billingBasis;
		this.billingIdentifier = billingIdentifier;
		this.sequenceNumber = sequenceNumber;
		this.flightSequenceNumber = flightSequenceNumber;		
	}

	/**
	 * @return
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 * 
	 */
	public boolean equals(Object object) {
		boolean isEqual = false;
		if(object != null){
			isEqual = this.hashCode() == object.hashCode();
		}
		return isEqual;
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
	@KeyCondition(column =  "SERNUM")
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}

	/**
	 * 
	 * @param flightSequenceNumber
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber=flightSequenceNumber;
	}
	
	/**
	 * 
	 * @return
	 */
	@Key(generator="KEY_GEN",startAt="1")
	public int getFlightSequenceNumber() {
		return this.flightSequenceNumber;
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
		StringBuilder sbul = new StringBuilder(180);
		sbul.append("GPAReportingFlightDetailsPK [ ");
		sbul.append("billingBasis '").append(this.billingBasis);
		sbul.append("', billingIdentifier '").append(this.billingIdentifier);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', flightSequenceNumber '").append(
				this.flightSequenceNumber);
		sbul.append("', poaCode '").append(this.poaCode);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
