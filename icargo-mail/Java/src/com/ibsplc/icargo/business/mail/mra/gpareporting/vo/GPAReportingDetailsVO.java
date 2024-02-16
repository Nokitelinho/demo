/*
 * GPAReportingDetailsVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1453
 * 
 */

public class GPAReportingDetailsVO extends AbstractVO {

	/**
	 * Mail status - reported
	 */
	public static final String MAIL_STATUS_REPORTED = "R";
	
	public static final String MAIL_SUBCLASS_LIKE_U = "U";

	public static final String MAIL_SUBCLASS_LC = "LC";

	public static final String MAIL_SUBCLASS_CP = "CP";

	/**
	 * Added for displaying tool tip in captureGPAReport screen
	 */
	private String flightDetails;

	/**
	 * 
	 * companyCode
	 */
	private String companyCode;

	/**
	 * poaCode
	 */
	private String poaCode;

	/**
	 * countryCode
	 */
	private String countryCode;

	/**
	 * billingbasis
	 */
	private String billingBasis;

	/**
	 * reportingFrom
	 */
	private LocalDate reportingFrom;

	/**
	 * reportingTo
	 */
	private LocalDate reportingTo;

	/**
	 * sequenceNumber
	 */
	private int sequenceNumber;

	/**
	 * dsnDate
	 */
	private LocalDate dsnDate;

	/**
	 * basistype -if M - Mailbag then mailbag idr is billingbasis
	 */
	private String basistype;

	/**
	 * originOfficeExchange
	 */
	private String originOfficeExchange;

	/**
	 * destinationOfficeExchange
	 */
	private String destinationOfficeExchange;

	/**
	 * mailCategory
	 */
	private String mailCategory;

	/**
	 * mailSubClass group code. Possible values can be 'LC' or 'CP'
	 */
	private String mailSubClass;

	/**
	 * year
	 */
	private String year;

	/**
	 * dsnNumber
	 */
	private String dsnNumber;

	/**
	 * noOfMailBags
	 */
	private int noOfMailBags;

	/**
	 * weight
	 */
	private double weight;

	/**
	 * rate
	 */
	private double rate;

	/**
	 * amount
	 */
	private Money amount;

	/**
	 * tax
	 */
	private double tax;

	/**
	 * total
	 */
	private Money total;

	/**
	 * reportingStatus
	 */
	private String reportingStatus;

	private String lastUpdateUser;

	private LocalDate lastUpdateTime;

	/**
	 * gpaReportingFlightDetailsVOs
	 */
	private Collection<GPAReportingFlightDetailsVO> gpaReportingFlightDetailsVOs;

	/**
	 * operationFlag
	 */
	private String operationFlag;

	/**
	 * reportingFrom
	 */
	private String reportingFromString;

	/**
	 * reportingTo
	 */
	private String reportingToString;

	/**
	 * dsnDateForDisplay added for suggest combo of CaptureGPAReportScreen
	 */
	private String dsnDateForDisplay;

	/**
	 * RSN
	 */
	private String receptacleSerialNumber;

	/**
	 * RI
	 */
	private String registeredOrInsuredIndicator;

	/**
	 * HN
	 */
	private String highestNumberedReceptacle;

	/**
	 * Actual mail sub class
	 */
	private String actualMailSubClass;
	
	private String currencyCode;
	
	/**
	 * @author A-3447
	 */
	private String billingIdentifier;
	
	private int consignmentSeqNo;
	
	private String consignementDocNo;
	
	
	
	/**
	 * @return the consignementDocNo
	 */
	public String getConsignementDocNo() {
		return consignementDocNo;
	}

	/**
	 * @param consignementDocNo the consignementDocNo to set
	 */
	public void setConsignementDocNo(String consignementDocNo) {
		this.consignementDocNo = consignementDocNo;
	}

	/**
	 * @return the consignmentSeqNo
	 */
	public int getConsignmentSeqNo() {
		return consignmentSeqNo;
	}

	/**
	 * @param consignmentSeqNo the consignmentSeqNo to set
	 */
	public void setConsignmentSeqNo(int consignmentSeqNo) {
		this.consignmentSeqNo = consignmentSeqNo;
	}

	/**
	 * @return the billingIdentifier
	 */
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
	 * @return Returns the highestNumberedReceptacle.
	 */
	public String getHighestNumberedReceptacle() {
		return highestNumberedReceptacle;
	}

	/**
	 * @param highestNumberedReceptacle
	 *            The highestNumberedReceptacle to set.
	 */
	public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
		this.highestNumberedReceptacle = highestNumberedReceptacle;
	}

	/**
	 * @return Returns the receptacleSerialNumber.
	 */
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	/**
	 * @param receptacleSerialNumber
	 *            The receptacleSerialNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	/**
	 * @return Returns the registeredOrInsuredIndicator.
	 */
	public String getRegisteredOrInsuredIndicator() {
		return registeredOrInsuredIndicator;
	}

	/**
	 * @param registeredOrInsuredIndicator
	 *            The registeredOrInsuredIndicator to set.
	 */
	public void setRegisteredOrInsuredIndicator(
			String registeredOrInsuredIndicator) {
		this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
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
	 * @return Returns the billingbasis.
	 */

	/**
	 * @return Returns the amount.
	 */
	public Money getAmount() {
		return amount;
	}

	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(Money amount) {
		this.amount = amount;
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
	 * @return Returns the destinationOfficeExchange.
	 */
	public String getDestinationOfficeExchange() {
		return destinationOfficeExchange;
	}

	/**
	 * @param destinationOfficeExchange
	 *            The destinationOfficeExchange to set.
	 */
	public void setDestinationOfficeExchange(String destinationOfficeExchange) {
		this.destinationOfficeExchange = destinationOfficeExchange;
	}

	/**
	 * @return Returns the dsnDate.
	 */
	public LocalDate getDsnDate() {
		return dsnDate;
	}

	/**
	 * @param dsnDate
	 *            The dsnDate to set.
	 */
	public void setDsnDate(LocalDate dsnDate) {
		this.dsnDate = dsnDate;
	}

	/**
	 * @return Returns the dsnNumber.
	 */
	public String getDsnNumber() {
		return dsnNumber;
	}

	/**
	 * @param dsnNumber
	 *            The dsnNumber to set.
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}

	/**
	 * @return Returns the gpaReportingFlightDetailsVOs.
	 */
	public Collection<GPAReportingFlightDetailsVO> getGpaReportingFlightDetailsVOs() {
		return gpaReportingFlightDetailsVOs;
	}

	/**
	 * @param flightDetails
	 *            The flightDetails to set.
	 */
	public void setGpaReportingFlightDetailsVOs(
			Collection<GPAReportingFlightDetailsVO> flightDetails) {
		this.gpaReportingFlightDetailsVOs = flightDetails;
	}

	/**
	 * @return Returns the basistype.
	 */
	public String getBasistype() {
		return basistype;
	}

	/**
	 * @param basistype
	 *            The basistype to set.
	 */
	public void setBasistype(String basistype) {
		this.basistype = basistype;
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
	 * @return Returns the mailCategory.
	 */
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory
	 *            The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the mailSubClass.
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass
	 *            The mailSubClass to set.
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return Returns the noOfMailBags.
	 */
	public int getNoOfMailBags() {
		return noOfMailBags;
	}

	/**
	 * @param noOfMailBags
	 *            The noOfMailBags to set.
	 */
	public void setNoOfMailBags(int noOfMailBags) {
		this.noOfMailBags = noOfMailBags;
	}

	/**
	 * @return Returns the originOfficeExchange.
	 */
	public String getOriginOfficeExchange() {
		return originOfficeExchange;
	}

	/**
	 * @param originOfficeExchange
	 *            The originOfficeExchange to set.
	 */
	public void setOriginOfficeExchange(String originOfficeExchange) {
		this.originOfficeExchange = originOfficeExchange;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode
	 *            The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the rate.
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            The rate to set.
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return Returns the reportingFrom.
	 */
	public LocalDate getReportingFrom() {
		return reportingFrom;
	}

	/**
	 * @param reportingFrom
	 *            The reportingFrom to set.
	 */
	public void setReportingFrom(LocalDate reportingFrom) {
		this.reportingFrom = reportingFrom;
	}

	/**
	 * @return Returns the reportingStatus.
	 */
	public String getReportingStatus() {
		return reportingStatus;
	}

	/**
	 * @param reportingStatus
	 *            The reportingStatus to set.
	 */
	public void setReportingStatus(String reportingStatus) {
		this.reportingStatus = reportingStatus;
	}

	/**
	 * @return Returns the reportingTo.
	 */
	public LocalDate getReportingTo() {
		return reportingTo;
	}

	/**
	 * @param reportingTo
	 *            The reportingTo to set.
	 */
	public void setReportingTo(LocalDate reportingTo) {
		this.reportingTo = reportingTo;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the tax.
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax
	 *            The tax to set.
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	

	/**
	 * @return Returns the total.
	 */
	public Money getTotal() {
		return total;
	}

	/**
	 * @param total The total to set.
	 */
	public void setTotal(Money total) {
		this.total = total;
	}

	/**
	 * @return Returns the weight.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            The weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis
	 *            The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the reportingFromString.
	 */
	public String getReportingFromString() {
		return reportingFromString;
	}

	/**
	 * @param reportingFromString
	 *            The reportingFromString to set.
	 */
	public void setReportingFromString(String reportingFromString) {
		this.reportingFromString = reportingFromString;
	}

	/**
	 * @return Returns the reportingToString.
	 */
	public String getReportingToString() {
		return reportingToString;
	}

	/**
	 * @param reportingToString
	 *            The reportingToString to set.
	 */
	public void setReportingToString(String reportingToString) {
		this.reportingToString = reportingToString;
	}

	/**
	 * @return Returns the flightDetails.
	 */
	public String getFlightDetails() {
		return flightDetails;
	}

	/**
	 * @param flightDetails
	 *            The flightDetails to set.
	 */
	public void setFlightDetails(String flightDetails) {
		this.flightDetails = flightDetails;
	}

	/**
	 * @return Returns the dsnDateForDisplay.
	 */
	public String getDsnDateForDisplay() {
		return dsnDateForDisplay;
	}

	/**
	 * @param dsnDateForDisplay
	 *            The dsnDateForDisplay to set.
	 */
	public void setDsnDateForDisplay(String dsnDateForDisplay) {
		this.dsnDateForDisplay = dsnDateForDisplay;
	}

	/**
	 * @return Returns the actualMailSubClass.
	 */
	public String getActualMailSubClass() {
		return actualMailSubClass;
	}

	/**
	 * @param actualMailSubClass
	 *            The actualMailSubClass to set.
	 */
	public void setActualMailSubClass(String actualMailSubClass) {
		this.actualMailSubClass = actualMailSubClass;
	}

	/**
	 * @return Returns the currencyCode.
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
}
