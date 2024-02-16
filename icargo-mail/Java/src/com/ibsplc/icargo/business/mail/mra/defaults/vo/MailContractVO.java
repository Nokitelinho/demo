/*
 * MailContractVO.java created on Mar 30, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2518
 * 
 */
public class MailContractVO extends AbstractVO {
	/**
	 * Company Code
	 */
	private String companyCode;

	/**
	 * Mail contract reference number
	 */
	private String contractReferenceNumber;

	/**
	 * Mail contract version number
	 */
	private String versionNumber;

	/**
	 * Airline identifier
	 */
	private int airlineIdentifier;

	/**
	 * Airline code
	 */
	private String airlineCode;

	/**
	 * General Postal Authority code
	 */
	private String gpaCode;

	/**
	 * Mail contract description
	 */
	private String contractDescription;

	/**
	 * Agreement status - Possible values can be
	 * <li>D - Draft, C - Cancelled, A - Active</li>
	 */
	private String agreementStatus;

	/**
	 * Agreement type - Possible values can be
	 * <li>B - Bid, C- Contract</li>
	 */
	private String agreementType;

	/**
	 * Mail contract creation date
	 */
	private LocalDate creationDate;

	/**
	 * Mail contract valid from date
	 */
	private LocalDate validFromDate;

	/**
	 * Mail contract valid to date
	 */
	private LocalDate validToDate;

	/**
	 * Operation flag
	 */
	private String operationFlag;

	/**
	 * Mail contract details
	 */
	private Collection<MailContractDetailsVO> mailContractDetailsVos;

	/**
	 * Mail contract billing matrix details
	 */
	private Collection<String> billingDetails;

	/**
	 * This field will be set to true if the billing details are modified
	 */
	private boolean isBillingMatrixModified;

	/**
	 * @return Returns the agreementStatus.
	 */
	public String getAgreementStatus() {
		return agreementStatus;
	}

	/**
	 * @param agreementStatus
	 *            The agreementStatus to set.
	 */
	public void setAgreementStatus(String agreementStatus) {
		this.agreementStatus = agreementStatus;
	}

	/**
	 * @return Returns the agreementType.
	 */
	public String getAgreementType() {
		return agreementType;
	}

	/**
	 * @param agreementType
	 *            The agreementType to set.
	 */
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the companyCode.
	 */
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
	 * @return Returns the contractDescription.
	 */
	public String getContractDescription() {
		return contractDescription;
	}

	/**
	 * @param contractDescription
	 *            The contractDescription to set.
	 */
	public void setContractDescription(String contractDescription) {
		this.contractDescription = contractDescription;
	}

	/**
	 * @return Returns the contractReferenceNumber.
	 */
	public String getContractReferenceNumber() {
		return contractReferenceNumber;
	}

	/**
	 * @param contractReferenceNumber
	 *            The contractReferenceNumber to set.
	 */
	public void setContractReferenceNumber(String contractReferenceNumber) {
		this.contractReferenceNumber = contractReferenceNumber;
	}

	/**
	 * @return Returns the creationDate.
	 */
	public LocalDate getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            The creationDate to set.
	 */
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the billingDetails.
	 */
	public Collection<String> getBillingDetails() {
		return billingDetails;
	}

	/**
	 * @param billingDetails
	 *            The billingDetails to set.
	 */
	public void setBillingDetails(Collection<String> billingDetails) {
		this.billingDetails = billingDetails;
	}

	/**
	 * @return Returns the mailContractDetailsVos.
	 */
	public Collection<MailContractDetailsVO> getMailContractDetailsVos() {
		return mailContractDetailsVos;
	}

	/**
	 * @param mailContractDetailsVos
	 *            The mailContractDetailsVos to set.
	 */
	public void setMailContractDetailsVos(
			Collection<MailContractDetailsVO> mailContractDetailsVos) {
		this.mailContractDetailsVos = mailContractDetailsVos;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the validFromDate.
	 */
	public LocalDate getValidFromDate() {
		return validFromDate;
	}

	/**
	 * @param validFromDate
	 *            The validFromDate to set.
	 */
	public void setValidFromDate(LocalDate validFromDate) {
		this.validFromDate = validFromDate;
	}

	/**
	 * @return Returns the validToDate.
	 */
	public LocalDate getValidToDate() {
		return validToDate;
	}

	/**
	 * @param validToDate
	 *            The validToDate to set.
	 */
	public void setValidToDate(LocalDate validToDate) {
		this.validToDate = validToDate;
	}

	/**
	 * @return Returns the versionNumber.
	 */
	public String getVersionNumber() {
		return versionNumber;
	}

	/**
	 * @param versionNumber
	 *            The versionNumber to set.
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * @return Returns the isBillingMatrixModified.
	 */
	public boolean isBillingMatrixModified() {
		return isBillingMatrixModified;
	}

	/**
	 * @param isBillingMatrixModified
	 *            The isBillingMatrixModified to set.
	 */
	public void setBillingMatrixModified(boolean isBillingMatrixModified) {
		this.isBillingMatrixModified = isBillingMatrixModified;
	}

}
