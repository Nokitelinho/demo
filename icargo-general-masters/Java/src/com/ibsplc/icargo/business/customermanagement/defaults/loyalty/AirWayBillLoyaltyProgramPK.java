/*
 * AirWayBillLoyaltyProgramPK.java
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-1883
 *
 */
@Embeddable
public class AirWayBillLoyaltyProgramPK implements Serializable{
	/**
	 * Company Code
	 */
	private String companyCode;
	/**
	 * Customer Code
	 */
	private String customerCode;
	/**
	 * Master Awb Number
	 */
	private String masterAwbNumber;
	/**
	 * Document Owner Identifier
	 */
	private int documentOwnerIdentifier;
	/**
	 * Duplicate Number
	 */
    private int duplicateNumber;
    /**
     * Sequence Number
     */
    private int sequenceNumber;
    /**
     * Loyalty Program Code
     */
    private String loyaltyProgrammeCode;
    /**
     * Loyalty Attribute
     */
    private String loyaltyAttribute;
    /**
     * Loyalty Attribute Unit
     */
    private String loyaltyAttributeUnit;
    /**
	 * @return Returns the loyaltyAttributeUnit.
	 */
	public String getLoyaltyAttributeUnit() {
		return loyaltyAttributeUnit;
	}

	/**
	 * @param loyaltyAttributeUnit The loyaltyAttributeUnit to set.
	 */
	public void setLoyaltyAttributeUnit(String loyaltyAttributeUnit) {
		this.loyaltyAttributeUnit = loyaltyAttributeUnit;
	}

	/**
     * Default Constructor
     */
    public AirWayBillLoyaltyProgramPK(){
    	
    }
    
    /**
     * Constructor
     * @param companyCode
     * @param customerCode
     * @param masterAwbNumber
     * @param documentOwnerIdentifier
     * @param duplicateNumber
     * @param sequenceNumber
     * @param loyaltyProgrammeCode
     * @param loyaltyAttribute
     * @param loyaltyAttributeUnit
     */
	public AirWayBillLoyaltyProgramPK(String companyCode, String customerCode,
			String masterAwbNumber,int documentOwnerIdentifier,
			int duplicateNumber,int sequenceNumber,String loyaltyProgrammeCode
			,String loyaltyAttribute,String loyaltyAttributeUnit) {
		this.companyCode = companyCode;
		this.customerCode = customerCode;
		this.masterAwbNumber = masterAwbNumber;
		this.documentOwnerIdentifier = documentOwnerIdentifier;
		this.duplicateNumber = duplicateNumber; 
		this.sequenceNumber = sequenceNumber;
		this.loyaltyProgrammeCode = loyaltyProgrammeCode;
		this.loyaltyAttribute = loyaltyAttribute; 
		this.loyaltyAttributeUnit = loyaltyAttributeUnit;
	}
	/**
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	/**
	 * @return int 
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
		append(customerCode).
		append(masterAwbNumber).
		append(documentOwnerIdentifier).
		append(duplicateNumber).
		append(sequenceNumber).
		append(loyaltyProgrammeCode).
		append(loyaltyAttribute).
		append(loyaltyAttributeUnit).
		toString().hashCode();
	}

	public void setLoyaltyAttribute(java.lang.String loyaltyAttribute) {
		this.loyaltyAttribute=loyaltyAttribute;
	}

	public java.lang.String getLoyaltyAttribute() {
		return this.loyaltyAttribute;
	}

	public void setLoyaltyProgrammeCode(java.lang.String loyaltyProgrammeCode) {
		this.loyaltyProgrammeCode=loyaltyProgrammeCode;
	}

	public java.lang.String getLoyaltyProgrammeCode() {
		return this.loyaltyProgrammeCode;
	}

	public void setCustomerCode(java.lang.String customerCode) {
		this.customerCode=customerCode;
	}

	public java.lang.String getCustomerCode() {
		return this.customerCode;
	}

	public void setMasterAwbNumber(java.lang.String masterAwbNumber) {
		this.masterAwbNumber=masterAwbNumber;
	}

	public java.lang.String getMasterAwbNumber() {
		return this.masterAwbNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber=sequenceNumber;
	}

	public int getSequenceNumber() {
		return this.sequenceNumber;
	}

	public void setDocumentOwnerIdentifier(int documentOwnerIdentifier) {
		this.documentOwnerIdentifier=documentOwnerIdentifier;
	}

	public int getDocumentOwnerIdentifier() {
		return this.documentOwnerIdentifier;
	}

	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber=duplicateNumber;
	}

	public int getDuplicateNumber() {
		return this.duplicateNumber;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:49 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(280);
		sbul.append("AirWayBillLoyaltyProgramPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', customerCode '").append(this.customerCode);
		sbul.append("', documentOwnerIdentifier '").append(
				this.documentOwnerIdentifier);
		sbul.append("', duplicateNumber '").append(this.duplicateNumber);
		sbul.append("', loyaltyAttribute '").append(this.loyaltyAttribute);
		sbul.append("', loyaltyAttributeUnit '").append(
				this.loyaltyAttributeUnit);
		sbul.append("', loyaltyProgrammeCode '").append(
				this.loyaltyProgrammeCode);
		sbul.append("', masterAwbNumber '").append(this.masterAwbNumber);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}