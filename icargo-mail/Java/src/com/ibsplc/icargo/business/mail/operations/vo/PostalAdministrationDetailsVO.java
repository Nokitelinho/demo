/*
 * PostalAdministrationDetailsVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;



import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-3109
 *
 */
public class PostalAdministrationDetailsVO extends AbstractVO {

	private String companyCode;
	private String poaCode;
	private String parCode;
	private String sernum;
	private String billingSource;
	private String billingFrequency;
	private String profInv;
	private String settlementCurrencyCode;
	private LocalDate validFrom;
	private LocalDate validTo;
	private String operationFlag;
	//added for CR ICRD-7298
	private String parameterValue;
	private String partyIdentifier;
	
	private String detailedRemarks;
	private String parameterType;
	private String passValue;


	/**
	 * @return the parameterType
	 */
	public String getParameterType() {
		return parameterType;
	}
	/**
	 * @param parameterType the parameterType to set
	 */
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getBillingFrequency() {
		return billingFrequency;
	}
	public void setBillingFrequency(String billingFrequency) {
		this.billingFrequency = billingFrequency;
	}
	public String getBillingSource() {
		return billingSource;
	}
	public void setBillingSource(String billingSource) {
		this.billingSource = billingSource;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getParCode() {
		return parCode;
	}
	public void setParCode(String parCode) {
		this.parCode = parCode;
	}
	public String getPoaCode() {
		return poaCode;
	}
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	public String getProfInv() {
		return profInv;
	}
	public void setProfInv(String profInv) {
		this.profInv = profInv;
	}
	public String getSernum() {
		return sernum;
	}
	public void setSernum(String sernum) {
		this.sernum = sernum;
	}
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}
	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}
	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}
	public LocalDate getValidFrom() {
		return validFrom;
	}
	public LocalDate getValidTo() {
		return validTo;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return the parameterValue
	 */
	public String getParameterValue() {
		return parameterValue;
	}
	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	/**
	 * @return the detailedRemarks
	 */
	public String getDetailedRemarks() {
		return detailedRemarks;
	}
	/**
	 * @param detailedRemarks the detailedRemarks to set
	 */
	public void setDetailedRemarks(String detailedRemarks) {
		this.detailedRemarks = detailedRemarks;
	}
	
	public String getPartyIdentifier() {
		return partyIdentifier;
	}
	public void setPartyIdentifier(String partyIdentifier) {
		this.partyIdentifier = partyIdentifier;
	}
	public String getPassValue() {
		return passValue;
	}
	public void setPassValue(String passValue) {
		this.passValue = passValue;
	}

}
