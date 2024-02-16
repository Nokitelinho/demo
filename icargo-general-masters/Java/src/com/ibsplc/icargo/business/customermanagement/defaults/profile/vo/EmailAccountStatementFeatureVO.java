/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.java
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.profile.vo;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.template.email.CustomerAccountStatementEmailTemplateVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.java
 *	Version	:	Name						 :	Date					:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-118899	 :	18-Aug-2021	:	Created
 */

public class EmailAccountStatementFeatureVO extends AbstractVO {

	private static final long serialVersionUID = -8405144611228443873L;

	public static final String MESSAGE_STANDARD = "IFCSTD";
	public static final String MESSAGE_TYPE = "CUSACCSMT";
	public static final String EMAIL_MODE = "M";
	public static final String MSG_VERSION = "1";
	public static final String FILE_NAME = "Statement of Account";
	public static final String FILE_FORMAT_PDF = ".pdf";
	public static final String FILE_FORMAT_XL = ".xlsx";
	public static final String TRIGGER_MANUAL = "MANUAL";
	public static final String TRIGGER_AUTO = "AUTO";

	private String companyCode;
	private String stationCode;
	private String customerCode;
	private String customerBillingEmailOne;
	private String customerBillingEmailTwo;
	private String customerBillingEmailThree;
	private String airlineContactEmail;
	private String countryCode;
	private String invoiceNumber;
	private LocalDate invoiceDate;
	private LocalDate billingEndDate;
	private Collection<String> invoiceDistributionMode;
	private Collection<List<CustomerInvoiceAWBDetailsVO>> customerInvoiceAWBDetailsVOs;
	private Collection<CustomerAccountStatementEmailTemplateVO> emailTemplateVOs;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerBillingEmailOne() {
		return customerBillingEmailOne;
	}

	public void setCustomerBillingEmailOne(String customerBillingEmailOne) {
		this.customerBillingEmailOne = customerBillingEmailOne;
	}

	public String getCustomerBillingEmailTwo() {
		return customerBillingEmailTwo;
	}

	public void setCustomerBillingEmailTwo(String customerBillingEmailTwo) {
		this.customerBillingEmailTwo = customerBillingEmailTwo;
	}

	public String getCustomerBillingEmailThree() {
		return customerBillingEmailThree;
	}

	public void setCustomerBillingEmailThree(String customerBillingEmailThree) {
		this.customerBillingEmailThree = customerBillingEmailThree;
	}

	public String getAirlineContactEmail() {
		return airlineContactEmail;
	}

	public void setAirlineContactEmail(String airlineContactEmail) {
		this.airlineContactEmail = airlineContactEmail;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public LocalDate getBillingEndDate() {
		return billingEndDate;
	}

	public void setBillingEndDate(LocalDate billingEndDate) {
		this.billingEndDate = billingEndDate;
	}

	public Collection<String> getInvoiceDistributionMode() {
		return invoiceDistributionMode;
	}

	public void setInvoiceDistributionMode(Collection<String> invoiceDistributionMode) {
		this.invoiceDistributionMode = invoiceDistributionMode;
	}

	public Collection<List<CustomerInvoiceAWBDetailsVO>> getCustomerInvoiceAWBDetailsVOs() {
		return customerInvoiceAWBDetailsVOs;
	}

	public void setCustomerInvoiceAWBDetailsVOs(
			Collection<List<CustomerInvoiceAWBDetailsVO>> customerInvoiceAWBDetailsVOs) {
		this.customerInvoiceAWBDetailsVOs = customerInvoiceAWBDetailsVOs;
	}

	public Collection<CustomerAccountStatementEmailTemplateVO> getEmailTemplateVOs() {
		return emailTemplateVOs;
	}

	public void setEmailTemplateVOs(Collection<CustomerAccountStatementEmailTemplateVO> emailTemplateVOs) {
		this.emailTemplateVOs = emailTemplateVOs;
	}

}
