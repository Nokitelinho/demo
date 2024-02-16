/*
 * MailContractFilterVO.java created on Apr 02, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2518
 * 
 */
public class MailContractFilterVO extends AbstractVO {
	/**
	 * Company Code
	 */
	private String companyCode;

	/**
	 * Mail contract reference number
	 */
	private String contractReferenceNumber;

	/**
	 * Version number
	 */
	private String versionNumber;
	/**
	 * contractDate
	 */
	private LocalDate contractDate;
	/**
	 * paCode
	 */
	private String paCode ;
	/**
	 * airlineCode
	 */
	private String airlineCode;
	/**
	 * agreementType
	 */
	private String agreementType;
	/**
	 * agreementStatus
	 */
	private String agreementStatus ;
	/**
	 * contractValidityFrom
	 */
	private LocalDate contractValidityFrom;
	/**
	 * contractValidityTo
	 */
	private LocalDate contractValidityTo;

	/**
	 * @return Returns the agreementStatus.
	 */
	public String getAgreementStatus() {
		return agreementStatus;
	}

	/**
	 * @param agreementStatus The agreementStatus to set.
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
	 * @param agreementType The agreementType to set.
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
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the contractDate.
	 */
	public LocalDate getContractDate() {
		return contractDate;
	}

	/**
	 * @param contractDate The contractDate to set.
	 */
	public void setContractDate(LocalDate contractDate) {
		this.contractDate = contractDate;
	}

	/**
	 * @return Returns the contractValidityFrom.
	 */
	public LocalDate getContractValidityFrom() {
		return contractValidityFrom;
	}

	/**
	 * @param contractValidityFrom The contractValidityFrom to set.
	 */
	public void setContractValidityFrom(LocalDate contractValidityFrom) {
		this.contractValidityFrom = contractValidityFrom;
	}

	/**
	 * @return Returns the contractValidityTo.
	 */
	public LocalDate getContractValidityTo() {
		return contractValidityTo;
	}

	/**
	 * @param contractValidityTo The contractValidityTo to set.
	 */
	public void setContractValidityTo(LocalDate contractValidityTo) {
		this.contractValidityTo = contractValidityTo;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
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

}
