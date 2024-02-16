package com.ibsplc.icargo.presentation.web.model.mail.operations.common;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignerDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10383	:	06-June-2021		:	Draft
 */

public class ApplicableRegulation{

	private String applicableRegTransportDirection;
	private String applicableRegBorderAgencyAuthority;
	private String applicableRegReferenceID;
	private String applicableRegFlag;
	private String consignmentNumber;
	private String consignmentSequenceNumber;
	private String paCode;
	private String securityReasonCode;
	private String serialnum;
	private String opFlag;
	
	public String getApplicableRegTransportDirection() {
		return applicableRegTransportDirection;
	}
	public void setApplicableRegTransportDirection(String applicableRegTransportDirection) {
		this.applicableRegTransportDirection = applicableRegTransportDirection;
	}
	public String getApplicableRegBorderAgencyAuthority() {
		return applicableRegBorderAgencyAuthority;
	}
	public void setApplicableRegBorderAgencyAuthority(String applicableRegBorderAgencyAuthority) {
		this.applicableRegBorderAgencyAuthority = applicableRegBorderAgencyAuthority;
	}
	public String getApplicableRegReferenceID() {
		return applicableRegReferenceID;
	}
	public void setApplicableRegReferenceID(String applicableRegReferenceID) {
		this.applicableRegReferenceID = applicableRegReferenceID;
	}
	public String getApplicableRegFlag() {
		return applicableRegFlag;
	}
	public void setApplicableRegFlag(String applicableRegFlag) {
		this.applicableRegFlag = applicableRegFlag;
	}
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	public String getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}
	public void setConsignmentSequenceNumber(String consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getSecurityReasonCode() {
		return securityReasonCode;
	}
	public void setSecurityReasonCode(String securityReasonCode) {
		this.securityReasonCode = securityReasonCode;
	}
	public String getSerialnum() {
		return serialnum;
	}
	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}
	public String getOpFlag() {
		return opFlag;
	}
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}
}