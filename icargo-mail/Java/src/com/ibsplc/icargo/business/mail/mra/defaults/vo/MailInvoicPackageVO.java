/*
 * MailInvoicPackageVO.java created on Jul 19, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class MailInvoicPackageVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	private String receptacleIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	private int packageCount;
	
	private String containerType;
	
	private String weightOfUnit;
	
	private double containerWeight;
	
	private double containerMinimumWeight;
	
	private String originOfficeOfExchange;
	
	private String destinationOfficeOfExchange;
	
	private String mailCategoryCode;
	
	private String mailSubclassCode;
	
	private String lastDigitOfYear;
	
	private String despatchSerialNumber;
	
	private String receptacleSerialNumber;
	
	private String highestReceptacleIndicator;
	
	private String registeredInsuredIndicator;
	
	private double receptacleWeight;
	
	private String reconciliationStatus;

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the containerMinimumWeight.
	 */
	public double getContainerMinimumWeight() {
		return containerMinimumWeight;
	}

	/**
	 * @param containerMinimumWeight The containerMinimumWeight to set.
	 */
	public void setContainerMinimumWeight(double containerMinimumWeight) {
		this.containerMinimumWeight = containerMinimumWeight;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the containerWeight.
	 */
	public double getContainerWeight() {
		return containerWeight;
	}

	/**
	 * @param containerWeight The containerWeight to set.
	 */
	public void setContainerWeight(double containerWeight) {
		this.containerWeight = containerWeight;
	}

	/**
	 * @return Returns the despatchSerialNumber.
	 */
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	/**
	 * @param despatchSerialNumber The despatchSerialNumber to set.
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	/**
	 * @return Returns the destinationOfficeOfExchange.
	 */
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	/**
	 * @param destinationOfficeOfExchange The destinationOfficeOfExchange to set.
	 */
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	/**
	 * @return Returns the highestReceptacleIndicator.
	 */
	public String getHighestReceptacleIndicator() {
		return highestReceptacleIndicator;
	}

	/**
	 * @param highestReceptacleIndicator The highestReceptacleIndicator to set.
	 */
	public void setHighestReceptacleIndicator(String highestReceptacleIndicator) {
		this.highestReceptacleIndicator = highestReceptacleIndicator;
	}

	/**
	 * @return Returns the invoiceKey.
	 */
	public String getInvoiceKey() {
		return invoiceKey;
	}

	/**
	 * @param invoiceKey The invoiceKey to set.
	 */
	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}

	/**
	 * @return Returns the lastDigitOfYear.
	 */
	public String getLastDigitOfYear() {
		return lastDigitOfYear;
	}

	/**
	 * @param lastDigitOfYear The lastDigitOfYear to set.
	 */
	public void setLastDigitOfYear(String lastDigitOfYear) {
		this.lastDigitOfYear = lastDigitOfYear;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubclassCode.
	 */
	public String getMailSubclassCode() {
		return mailSubclassCode;
	}

	/**
	 * @param mailSubclassCode The mailSubclassCode to set.
	 */
	public void setMailSubclassCode(String mailSubclassCode) {
		this.mailSubclassCode = mailSubclassCode;
	}

	/**
	 * @return Returns the originOfficeOfExchange.
	 */
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	/**
	 * @param originOfficeOfExchange The originOfficeOfExchange to set.
	 */
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	/**
	 * @return Returns the packageCount.
	 */
	public int getPackageCount() {
		return packageCount;
	}

	/**
	 * @param packageCount The packageCount to set.
	 */
	public void setPackageCount(int packageCount) {
		this.packageCount = packageCount;
	}

	/**
	 * @return Returns the receptacleIdentifier.
	 */
	public String getReceptacleIdentifier() {
		return receptacleIdentifier;
	}

	/**
	 * @param receptacleIdentifier The receptacleIdentifier to set.
	 */
	public void setReceptacleIdentifier(String receptacleIdentifier) {
		this.receptacleIdentifier = receptacleIdentifier;
	}

	/**
	 * @return Returns the receptacleSerialNumber.
	 */
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	/**
	 * @param receptacleSerialNumber The receptacleSerialNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	/**
	 * @return Returns the receptacleWeight.
	 */
	public double getReceptacleWeight() {
		return receptacleWeight;
	}

	/**
	 * @param receptacleWeight The receptacleWeight to set.
	 */
	public void setReceptacleWeight(double receptacleWeight) {
		this.receptacleWeight = receptacleWeight;
	}

	/**
	 * @return Returns the reconciliationStatus.
	 */
	public String getReconciliationStatus() {
		return reconciliationStatus;
	}

	/**
	 * @param reconciliationStatus The reconciliationStatus to set.
	 */
	public void setReconciliationStatus(String reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
	}

	/**
	 * @return Returns the registeredInsuredIndicator.
	 */
	public String getRegisteredInsuredIndicator() {
		return registeredInsuredIndicator;
	}

	/**
	 * @param registeredInsuredIndicator The registeredInsuredIndicator to set.
	 */
	public void setRegisteredInsuredIndicator(String registeredInsuredIndicator) {
		this.registeredInsuredIndicator = registeredInsuredIndicator;
	}

	/**
	 * @return Returns the sectorDestination.
	 */
	public String getSectorDestination() {
		return sectorDestination;
	}

	/**
	 * @param sectorDestination The sectorDestination to set.
	 */
	public void setSectorDestination(String sectorDestination) {
		this.sectorDestination = sectorDestination;
	}

	/**
	 * @return Returns the sectorOrigin.
	 */
	public String getSectorOrigin() {
		return sectorOrigin;
	}

	/**
	 * @param sectorOrigin The sectorOrigin to set.
	 */
	public void setSectorOrigin(String sectorOrigin) {
		this.sectorOrigin = sectorOrigin;
	}

	/**
	 * @return Returns the weightOfUnit.
	 */
	public String getWeightOfUnit() {
		return weightOfUnit;
	}

	/**
	 * @param weightOfUnit The weightOfUnit to set.
	 */
	public void setWeightOfUnit(String weightOfUnit) {
		this.weightOfUnit = weightOfUnit;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	
	
	
}