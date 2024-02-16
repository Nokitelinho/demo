/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryDetails.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Dec 17, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Dec 17, 2018	:	Draft
 */
public class GPABillingEntryDetails {
	
	private String companyCode;
	private String mailbagID;
	private String origin;
	private String destination;	
  	private String ratedSector;
  	private String gpaCode;
	private double weight;
	private String currency;	
	private double applicableRate;
	private double netAmount;
	private double serviceTax;
	private double  amount;
	private String rateType;
	private String csgDocumentNumber ;
	private String hni; 
	private double declaredValue;
	private String orgOfficeOfExchange;
	private String regInd; 
	private String invoiceNumber;
	private String year;
	private double surChg;
	private String ccaRefNumber;
	private String destOfficeOfExchange;
	private double grossAmount;
	private String dsn;
	private String rateIdentifier;
	private String rateLineIdentifier;	
	private String category;
	private String rsn; 
	private double valCharges;
	private String subClass;
	private String isUSPSPerformed; 
	private String billingStatus;
	private String mcaIndicator;
	private String mailType;
	private String mailWeight;
	private long mailSequenceNumber;
	private int sequenceNumber;
	private int invoiceSerialNumber;
	private int csgSequenceNumber;
	private int airlineIdentifier;
	private String remarks;
	private String wgt;
	private String paBuilt;
	private double actualWeight ;
	
	private String containerID;
	private String actualWeightUnit ;
	  //Added by A-8527 for IASCB-22915
	private String wgtunitcod;
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public String getActualWeightUnit() {
		return actualWeightUnit;
	}
	public void setActualWeightUnit(String actualWeightUnit) {
		this.actualWeightUnit = actualWeightUnit;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public int getInvoiceSerialNumber() {
		return invoiceSerialNumber;
	}
	public void setInvoiceSerialNumber(int invoiceSerialNumber) {
		this.invoiceSerialNumber = invoiceSerialNumber;
	}
	public int getCsgSequenceNumber() {
		return csgSequenceNumber;
	}
	public void setCsgSequenceNumber(int csgSequenceNumber) {
		this.csgSequenceNumber = csgSequenceNumber;
	}
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCsgDocumentNumber() {
		return csgDocumentNumber;
	}
	public void setCsgDocumentNumber(String csgDocumentNumber) {
		this.csgDocumentNumber = csgDocumentNumber;
	}
	public String getMailbagID() {
		return mailbagID;
	}
	public void setMailbagID(String mailbagID) {
		this.mailbagID = mailbagID;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	public String getCcaRefNumber() {
		return ccaRefNumber;
	}
	public void setCcaRefNumber(String ccaRefNumber) {
		this.ccaRefNumber = ccaRefNumber;
	}
	public double getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}
	public double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	public double getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}
	public double getApplicableRate() {
		return applicableRate;
	}
	public void setApplicableRate(double applicableRate) {
		this.applicableRate = applicableRate;
	}
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	public double getValCharges() {
		return valCharges;
	}
	public void setValCharges(double valCharges) {
		this.valCharges = valCharges;
	}
	public double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getOrgOfficeOfExchange() {
		return orgOfficeOfExchange;
	}
	public void setOrgOfficeOfExchange(String orgOfficeOfExchange) {
		this.orgOfficeOfExchange = orgOfficeOfExchange;
	}
	public double getSurChg() {
		return surChg;
	}
	public void setSurChg(double surChg) {
		this.surChg = surChg;
	}
	public String getRateIdentifier() {
		return rateIdentifier;
	}
	public void setRateIdentifier(String rateIdentifier) {
		this.rateIdentifier = rateIdentifier;
	}
	public String getRateLineIdentifier() {
		return rateLineIdentifier;
	}
	public void setRateLineIdentifier(String rateLineIdentifier) {
		this.rateLineIdentifier = rateLineIdentifier;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getIsUSPSPerformed() {
		return isUSPSPerformed;
	}
	public void setIsUSPSPerformed(String isUSPSPerformed) {
		this.isUSPSPerformed = isUSPSPerformed;
	}
	public String getRatedSector() {
		return ratedSector;
	}
	public void setRatedSector(String ratedSector) {
		this.ratedSector = ratedSector;
	}
	public String getHni() {
		return hni;
	}
	public void setHni(String hni) {
		this.hni = hni;
	}
	public String getRegInd() {
		return regInd;
	}
	public void setRegInd(String regInd) {
		this.regInd = regInd;
	}
	public String getDestOfficeOfExchange() {
		return destOfficeOfExchange;
	}
	public void setDestOfficeOfExchange(String destOfficeOfExchange) {
		this.destOfficeOfExchange = destOfficeOfExchange;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public String getMcaIndicator() {
		return mcaIndicator;
	}
	public void setMcaIndicator(String mcaIndicator) {
		this.mcaIndicator = mcaIndicator;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getMailWeight() {
		return mailWeight;
	}
	public void setMailWeight(String mailWeight) {
		this.mailWeight = mailWeight;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	
	
	// added by 8331
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getWgtunitcod() {
		return wgtunitcod;
	}
	public void setWgtunitcod(String wgtunitcod) {
		this.wgtunitcod = wgtunitcod;
	}
	public String getWgt() {
		return wgt;
	}
	public void setWgt(String wgt) {
		this.wgt = wgt;
	}
	public String getPaBuilt() {
		return paBuilt;
	}
	public void setPaBuilt(String paBuilt) {
		this.paBuilt = paBuilt;
	}
	public double getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}
	public String getContainerID() {
		return containerID;
	}
	public void setContainerID(String containerID) {
		this.containerID = containerID;
	}
	
	
}
