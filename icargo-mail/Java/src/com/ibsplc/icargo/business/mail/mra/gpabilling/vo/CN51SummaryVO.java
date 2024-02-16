/*
 * CN51SummaryVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class CN51SummaryVO extends AbstractVO {

	public static final String INVOICE_STATUS_FINALISED = "F";

	public static final String INVOICE_STATUS_GENERATED = "G";

	public static final String INVOICE_STATUS_PERFORMAGENERATED = "P";

	public static final String INVOICE_STATUS_DIRECTFINALISED = "D";

	/**
	 * ONE TIMES FOR BILLING STATUS STARTS
	 */
	public static final String BILLABLE = "BB";

	public static final String DIRECT_BILLABLE = "DB";

	public static final String DIRECT_BILLED = "DD";

	public static final String ONHOLD = "OH";

	public static final String PROFORMA_BILLABLE = "PB";

	public static final String PROFORMA_BILLED = "PD";

	public static final String PROFORMA_ONHOLD = "PO";

	public static final String WITHDRAWN_DIRECT = "WD";

	public static final String WITHDRAWN_PROFORMA = "WP";

	/**
	 * ONE TIMES FOR BILLING STATUS ENDS
	 */

	public static final String MRA_SUB_SYSTEM = "M";

	public static final String MRA_FUNCTION_POINT = "GB";

	public static final String POSTAL_AUTHORITY_TYPE_PASS="PA";

	private String companyCode;

	private String billingPeriod;

	private String billingPeriodFrom;

	private String billingPeriodTo;

	private String invoiceNumber;

	private String gpaCode;

	private Money totalAmountInContractCurrency;

	private Money totalAmountInBillingCurrency;

	private String billingCurrencyCode;

	private String contractCurrencyCode;

	private LocalDate billedDate;

	private String invoiceStatus;

	private double totalAmount;

	private Collection<CN66DetailsVO> cn66details;

	private Collection<CN51DetailsVO> cn51details;

	private String operationFlag;

	private int invSerialNumber;

	private String periodNumber;
	private String passFileName;
	private boolean isPASS;

	/**
	 * added by
	 *
	 * @author A-3447 starts
	 */
	private String lastUpdateduser;

	private LocalDate lastUpdatedTime;

	private String billingStatus;

	private String billingBasis;

	private int sequenceNo;

	private String lstupdatedUser;

	private LocalDate lstUpdatedTime;

	private String invoiceStatusDisplay;

	/**
	 * added by
	 *
	 * @author A-3447 ends
	 */
	private String functionPoint;

	private String countryCode;

	private LocalDate fromDate;

	private LocalDate toDate; 

	private String ccaReference;

	private String dueAirline;

	private String gpaName;
	//added for cr ICRD-7370
	private double serviceTax;
	private double tds;
	private double netAmount;
	private double grossAmount;
	private String overrideRounding;  //Added by A-8164 for ICRD-267499
	//Added By A-8527 for ICRD-234294 Starts
	private Collection<CN51SummaryVO> rebillInvoiceDetails; 
	private String rebillInvoiceNumber;
	private int rebillRound;
	//Added By A-8527 for ICRD-234294 Ends
	public String getOverrideRounding() {
		return overrideRounding;
	}

	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}

	public Collection<CN66DetailsVO> getCn66details() {
		return cn66details;
	}

	/**
	 * @param cn66details
	 *            The cn66details to set.
	 */
	public void setCn66details(Collection<CN66DetailsVO> cn66details) {
		this.cn66details = cn66details;
	}

	/**
	 * @return Returns the totalAmount.
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            The totalAmount to set.
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return Returns the billingPeriodFrom.
	 */
	public String getBillingPeriodFrom() {
		return billingPeriodFrom;
	}

	/**
	 * @param billingPeriodFrom
	 *            The billingPeriodFrom to set.
	 */
	public void setBillingPeriodFrom(String billingPeriodFrom) {
		this.billingPeriodFrom = billingPeriodFrom;
	}

	/**
	 * @return Returns the billingPeriodTo.
	 */
	public String getBillingPeriodTo() {
		return billingPeriodTo;
	}

	/**
	 * @param billingPeriodTo
	 *            The billingPeriodTo to set.
	 */
	public void setBillingPeriodTo(String billingPeriodTo) {
		this.billingPeriodTo = billingPeriodTo;
	}

	/**
	 * @return Returns the invoiceStatus.
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus
	 *            The invoiceStatus to set.
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
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
	 * @return Returns the billedDate.
	 */
	public LocalDate getBilledDate() {
		return billedDate;
	}

	/**
	 * @param billedDate
	 *            The billedDate to set.
	 */
	public void setBilledDate(LocalDate billedDate) {
		this.billedDate = billedDate;
	}

	/**
	 * @return Returns the billingCurrencyCode.
	 */
	public String getBillingCurrencyCode() {
		return billingCurrencyCode;
	}

	/**
	 * @param billingCurrencyCode
	 *            The billingCurrencyCode to set.
	 */
	public void setBillingCurrencyCode(String billingCurrencyCode) {
		this.billingCurrencyCode = billingCurrencyCode;
	}

	/**
	 * @return Returns the billingPeriod.
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}

	/**
	 * @param billingPeriod
	 *            The billingPeriod to set.
	 */
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	/**
	 * @return Returns the cn51details.
	 */
	public Collection<CN51DetailsVO> getCn51details() {
		return cn51details;
	}

	/**
	 * @param cn51details
	 *            The cn51details to set.
	 */
	public void setCn51details(Collection<CN51DetailsVO> cn51details) {
		this.cn51details = cn51details;
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
	 * @return Returns the contractCurrencyCode.
	 */
	public String getContractCurrencyCode() {
		return contractCurrencyCode;
	}

	/**
	 * @param contractCurrencyCode
	 *            The contractCurrencyCode to set.
	 */
	public void setContractCurrencyCode(String contractCurrencyCode) {
		this.contractCurrencyCode = contractCurrencyCode;
	}

	/**
	 * @return Returns the totalAmountInBillingCurrency.
	 */
	public Money getTotalAmountInBillingCurrency() {
		return totalAmountInBillingCurrency;
	}

	/**
	 * @param totalAmountInBillingCurrency
	 *            The totalAmountInBillingCurrency to set.
	 */
	public void setTotalAmountInBillingCurrency(
			Money totalAmountInBillingCurrency) {
		this.totalAmountInBillingCurrency = totalAmountInBillingCurrency;
	}

	/**
	 * @return Returns the totalAmountInContractCurrency.
	 */
	public Money getTotalAmountInContractCurrency() {
		return totalAmountInContractCurrency;
	}

	/**
	 * @param totalAmountInContractCurrency
	 *            The totalAmountInContractCurrency to set.
	 */
	public void setTotalAmountInContractCurrency(
			Money totalAmountInContractCurrency) {
		this.totalAmountInContractCurrency = totalAmountInContractCurrency;
	}

	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis
	 *            the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return the billingStatus
	 */
	public String getBillingStatus() {
		return billingStatus;
	}

	/**
	 * @param billingStatus
	 *            the billingStatus to set
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	/**
	 * @return the invoiceStatusDisplay
	 */
	public String getInvoiceStatusDisplay() {
		return invoiceStatusDisplay;
	}

	/**
	 * @param invoiceStatusDisplay
	 *            the invoiceStatusDisplay to set
	 */
	public void setInvoiceStatusDisplay(String invoiceStatusDisplay) {
		this.invoiceStatusDisplay = invoiceStatusDisplay;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUpdateduser
	 */
	public String getLastUpdateduser() {
		return lastUpdateduser;
	}

	/**
	 * @param lastUpdateduser
	 *            the lastUpdateduser to set
	 */
	public void setLastUpdateduser(String lastUpdateduser) {
		this.lastUpdateduser = lastUpdateduser;
	}

	/**
	 * @return the lstUpdatedTime
	 */
	public LocalDate getLstUpdatedTime() {
		return lstUpdatedTime;
	}

	/**
	 * @param lstUpdatedTime
	 *            the lstUpdatedTime to set
	 */
	public void setLstUpdatedTime(LocalDate lstUpdatedTime) {
		this.lstUpdatedTime = lstUpdatedTime;
	}

	/**
	 * @return the lstupdatedUser
	 */
	public String getLstupdatedUser() {
		return lstupdatedUser;
	}

	/**
	 * @param lstupdatedUser
	 *            the lstupdatedUser to set
	 */
	public void setLstupdatedUser(String lstupdatedUser) {
		this.lstupdatedUser = lstupdatedUser;
	}

	/**
	 * @return the sequenceNo
	 */
	public int getSequenceNo() {
		return sequenceNo;
	}

	/**
	 * @param sequenceNo
	 *            the sequenceNo to set
	 */
	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the functionPoint.
	 */
	public String getFunctionPoint() {
		return functionPoint;
	}

	/**
	 * @param functionPoint
	 *            The functionPoint to set.
	 */
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the dueAirline.
	 */
	public String getDueAirline() {
		return dueAirline;
	}

	/**
	 * @param dueAirline
	 *            The dueAirline to set.
	 */
	public void setDueAirline(String dueAirline) {
		this.dueAirline = dueAirline;
	}

	/**
	 * @return Returns the ccaReference.
	 */
	public String getCcaReference() {
		return ccaReference;
	}

	/**
	 * @param ccaReference
	 *            The ccaReference to set.
	 */
	public void setCcaReference(String ccaReference) {
		this.ccaReference = ccaReference;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName
	 *            The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return the serviceTax
	 */
	public double getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @return the tds
	 */
	public double getTds() {
		return tds;
	}

	/**
	 * @param tds the tds to set
	 */
	public void setTds(double tds) {
		this.tds = tds;
	}

	/**
	 * @return the netAmount
	 */
	public double getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	/**
	 * @return the grossAmount
	 */
	public double getGrossAmount() {
		return grossAmount;
	}

	/**
	 * @param grossAmount the grossAmount to set
	 */
	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	/**
	 * 	Getter for invSerialNumber 
	 *	Added by : A-6991 on 22-Sep-2017
	 * 	Used for :
	 */
	public int getInvSerialNumber() {
		return invSerialNumber;
	}

	/**
	 *  @param invSerialNumber the invSerialNumber to set
	 * 	Setter for invSerialNumber 
	 *	Added by : A-6991 on 22-Sep-2017
	 * 	Used for :
	 */
	public void setInvSerialNumber(int invSerialNumber) {
		this.invSerialNumber = invSerialNumber;
	}
	public Collection<CN51SummaryVO> getRebillInvoiceDetails() {
		return rebillInvoiceDetails;
	}
	public void setRebillInvoiceDetails(
			Collection<CN51SummaryVO> rebillInvoiceDetails) {
		this.rebillInvoiceDetails = rebillInvoiceDetails;
	}
	public String getRebillInvoiceNumber() {
		return rebillInvoiceNumber;
	}
	public void setRebillInvoiceNumber(String rebillInvoiceNumber) {
		this.rebillInvoiceNumber = rebillInvoiceNumber;
	}
	public int getRebillRound() {
		return rebillRound;
	}
	public void setRebillRound(int rebillRound) {
		this.rebillRound = rebillRound;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public String getPassFileName() {
		return passFileName;
	}

	public void setPassFileName(String passFileName) {
		this.passFileName = passFileName;
	}

	public boolean isPASS() {
		return isPASS;
	}

	public void setPASS(boolean pass) {
		isPASS = pass;
	}
}
