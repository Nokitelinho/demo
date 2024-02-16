package com.ibsplc.icargo.presentation.web.model.mail.operations.common;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignerDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10383	:	23-May-2021		:	Draft
 */

public class ConsignerDetails{
	
	private String agenttype;
	private String agentId;
	private String isoCountryCode;
	private String expiryDate;
	private String consignmentNumber;
	private String consignmentSequenceNumber;
	private String paCode;
	private String securityReasonCode;
	private String opFlag;
	private String serialnum;
	 
	public String getSerialnum() {
		return serialnum;
	}
	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}
	public String getAgenttype() {
		return agenttype;
	}
	public void setAgenttype(String agenttype) {
		this.agenttype = agenttype;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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
	public String getOpFlag() {
		return opFlag;
	}
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}
	

}
