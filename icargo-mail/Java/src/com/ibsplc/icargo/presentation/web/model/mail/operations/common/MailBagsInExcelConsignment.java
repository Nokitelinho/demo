package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import com.ibsplc.icargo.framework.util.time.LocalDate;

public class MailBagsInExcelConsignment {
	
	private String mailId;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailCategoryCode;
	private String mailClass;
	private String mailSubclass;
	private int year;
	private String dsn;
	private String receptacleSerialNumber;
	private int statedBags;
	private String highestNumberedReceptacle;
	private String registeredOrInsuredIndicator;
	private String statedWeight;
	private String uldNumber;
	private long mailSequenceNumber;
	private int consignmentSequenceNumber;
	private double declaredValue;
	private String currencyCode;
	private LocalDate transWindowEndTime;
	private LocalDate reqDeliveryTime;
	private String requiredDeliveryTime;
	private String mailOrigin;
	private String mailDestination;
	private String mailServiceLevel;
	public String getMailServiceLevel() {
		return mailServiceLevel;
	}
	public void setMailServiceLevel(String mailServiceLevel) {
		this.mailServiceLevel = mailServiceLevel;
	}
	public LocalDate getTransWindowEndTime() {
		return transWindowEndTime;
	}
	public void setTransWindowEndTime(LocalDate transWindowEndTime) {
		this.transWindowEndTime = transWindowEndTime;
	}
	
	
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	public String getRequiredDeliveryTime() {
		return requiredDeliveryTime;
	}
	public void setRequiredDeliveryTime(String requiredDeliveryTime) {
		this.requiredDeliveryTime = requiredDeliveryTime;
	}
	public String getMailOrigin() {
		return mailOrigin;
	}
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}
	public String getMailDestination() {
		return mailDestination;
	}
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	public String getMailSubclass() {
		return mailSubclass;
	}
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}
	public int getStatedBags() {
		return statedBags;
	}
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}
	public String getUldNumber() {
		return uldNumber;
	}
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	public String getHighestNumberedReceptacle() {
		return highestNumberedReceptacle;
	}
	public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
		this.highestNumberedReceptacle = highestNumberedReceptacle;
	}
	public String getRegisteredOrInsuredIndicator() {
		return registeredOrInsuredIndicator;
	}
	public void setRegisteredOrInsuredIndicator(String registeredOrInsuredIndicator) {
		this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
	}
	public String getStatedWeight() {
		return statedWeight;
	}
	public void setStatedWeight(String statedWeight) {
		this.statedWeight = statedWeight;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}
	/**
	 * @return the declaredValue
	 */
	public double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	}
