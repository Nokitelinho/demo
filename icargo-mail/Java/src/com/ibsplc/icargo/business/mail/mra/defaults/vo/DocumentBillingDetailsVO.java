/*
 * DocumentBillingDetailsVO.java Created on Aug 7, 2008
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3434
 *
 */
public class DocumentBillingDetailsVO extends AbstractVO{
	private String companyCode;
	private Integer serialNumber ;
	private Integer csgSequenceNumber ;
	private String csgDocumentNumber ;
	private String billingBasis;
	private String  poaCode;
	private String airlineCode;
	private String origin;
	private String destination; 
	private String category;
	private String subClass;
	private String sectorFrom;
	private String sectorTo;
	private String year;
	private String dsn;
	private String noofMailbags;
	private Double weight;
	private String currency;
	private Money  amount;
	private String billingStatus;
	private String invoiceNumber;
	private String remarks;
	private LocalDate flightDate;
	private String reviewCheck;
	private String billingStatusDisplay;
	private String intblgType;
	private LocalDate lastUpdatedTime;
	private String gpaCode;
	private String flightNumber;
	private String memoFlag;
	private int airlineIdr;	
	//Added for ccadetails
	private String ccaType;
	private String ccaRefNumber;
	//added for cr ICRD-7370
	private Money serviceTax;
	private double tds;
	private Money netAmount;
	private Money grossAmount;
	private double applicableRate;
	private String rsn; 
	private Money valCharges;
	//Added by A-6991 for ICRD-213474
  	private double declaredValue;
	private String orgOfficeOfExchange;
	private Money mailChg;
	private Money surChg;
	private int invSerialNumber;	
//Added by A-6991 for ICRD-137019
	private String rateIdentifier;
	private String rateLineIdentifier;
	private String rateType;
	//Added by A-7531 for ICRD-132508 
    private String fromDate;
	private String toDate;
	private String screenID;
	private String prorateException;//added by a-7531 for icrd-132487
	
	//Added by A-7794 as part of MRA revamp
	private long mailSequenceNumber;
	
	private String consignmentSeqNumber;
	private String consignmentNumber;
	private String paBuilt ;
	private String containerID ;
	private	double actualWeight ; 
	private String actualWeightUnit ;
	private LocalDate dsnDate; //As part Of icrd-132548

	private String triggerPoint;
	private String lastUpdatedUser;

  	private String isUSPSPerformed; //Added by A-7871 for ICRD-232381
  	private String unitCode;// Added by A-8527 for IASCB-22915

  	private String upliftAirport;
  	private String dischargeAirport;
  	private String transferAirline;
  	private String transferPA;
  	private String originOE;
  	private String destinationOE;
  	private String originAirport;
  	private String destinationAirport;

  	
  	private String filterMode;


	public LocalDate getDsnDate() {
		return dsnDate;
	}
	public void setDsnDate(LocalDate dsnDate) {
		this.dsnDate = dsnDate;
	}
	/**
	 * 
	 * 	Method		:	DocumentBillingDetailsVO.getFromDate
	 *	Added by 	:	A-7531 
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getFromDate() {
		return fromDate;
	}
	public String getActualWeightUnit() {
		return actualWeightUnit;
	}
	public void setActualWeightUnit(String actualWeightUnit) {
		this.actualWeightUnit = actualWeightUnit;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * 
	 * 	Method		:	DocumentBillingDetailsVO.getToDate
	 *	Added by 	:	A-7531 
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * 	Getter for rateIdentifier 
	 *	Added by : A-6991 on 05-Apr-2017
	 * 	Used for : ICRD-137019
	 */
	public String getRateIdentifier() {
		return rateIdentifier;
	}
	/**
	 *  @param rateIdentifier the rateIdentifier to set
	 * 	Setter for rateIdentifier 
	 *	Added by : A-6991 on 05-Apr-2017
	 * 	Used for : ICRD-137019
	 */
	public void setRateIdentifier(String rateIdentifier) {
		this.rateIdentifier = rateIdentifier;
	}
	/**
	 * 	Getter for rateType 
	 *	Added by : A-6991 on 05-Apr-2017
	 * 	Used for : ICRD-137019
	 */
	public String getRateType() {
		return rateType;
	}
	/**
	 *  @param rateType the rateType to set
	 * 	Setter for rateType 
	 *	Added by : A-6991 on 05-Apr-2017
	 * 	Used for : ICRD-137019
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getOrgOfficeOfExchange() {
		return orgOfficeOfExchange;
	}
	public void setOrgOfficeOfExchange(String orgOfficeOfExchange) {
		this.orgOfficeOfExchange = orgOfficeOfExchange;
	}
	public String getDestOfficeOfExchange() {
		return destOfficeOfExchange;
	}
	public void setDestOfficeOfExchange(String destOfficeOfExchange) {
		this.destOfficeOfExchange = destOfficeOfExchange;
	}
	private String destOfficeOfExchange;
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
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
	private String hni; 
	private String regInd; 

	
	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return Returns the ccaRefNumber.
	 */
	public String getCcaRefNumber() {
		return ccaRefNumber;
	}
	/**
	 * @param ccaRefNumber The ccaRefNumber to set.
	 */
	public void setCcaRefNumber(String ccaRefNumber) {
		this.ccaRefNumber = ccaRefNumber;
	}
	/**
	 * @return Returns the ccaType.
	 */
	public String getCcaType() {
		return ccaType;
	}
	/**
	 * @param ccaType The ccaType to set.
	 */
	public void setCcaType(String ccaType) {
		this.ccaType = ccaType;
	}
	/**
	 * @return Returns the intblgType.
	 */
	public String getIntblgType() {
		return intblgType;
	}
	/**
	 * @param intblgType The intblgType to set.
	 */
	public void setIntblgType(String intblgType) {
		this.intblgType = intblgType;
	}
	/**
	 * @return Returns the billingStatusDisplay.
	 */
	public String getBillingStatusDisplay() {
		return billingStatusDisplay;
	}
	/**
	 * @param billingStatusDisplay The billingStatusDisplay to set.
	 */
	public void setBillingStatusDisplay(String billingStatusDisplay) {
		this.billingStatusDisplay = billingStatusDisplay;
	}
	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return Returns the reviewCheck.
	 */
	public String getReviewCheck() {
		return reviewCheck;
	}
	/**
	 * @param reviewCheck The reviewCheck to set.
	 */
	public void setReviewCheck(String reviewCheck) {
		this.reviewCheck = reviewCheck;
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
	
	/**
	 * @return Returns the serialNumber.
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}
	/**
	 * @param billingBasis The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	/**
	 * @return Returns the csgDocumentNumber.
	 */
	public String getCsgDocumentNumber() {
		return csgDocumentNumber;
	}
	/**
	 * @param csgDocumentNumber The csgDocumentNumber to set.
	 */
	public void setCsgDocumentNumber(String csgDocumentNumber) {
		this.csgDocumentNumber = csgDocumentNumber;
	}
	/**
	 * @return Returns the csgSequenceNumber.
	 */
	public Integer getCsgSequenceNumber() {
		return csgSequenceNumber;
	}
	/**
	 * @param csgSequenceNumber The csgSequenceNumber to set.
	 */
	public void setCsgSequenceNumber(Integer csgSequenceNumber) {
		this.csgSequenceNumber = csgSequenceNumber;
	}
	
	
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
	 * @return Returns the billingStatus.
	 */
	public String getBillingStatus() {
		return billingStatus;
	}
	/**
	 * @param billingStatus The billingStatus to set.
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * @return Returns the noofMailbags.
	 */
	public String getNoofMailbags() {
		return noofMailbags;
	}
	/**
	 * @param noofMailbags The noofMailbags to set.
	 */
	public void setNoofMailbags(String noofMailbags) {
		this.noofMailbags = noofMailbags;
	}
	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	/**
	 * @return Returns the sectorFrom.
	 */
	public String getSectorFrom() {
		return sectorFrom;
	}
	/**
	 * @param sectorFrom The sectorFrom to set.
	 */
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}
	/**
	 * @return Returns the sectorTo.
	 */
	public String getSectorTo() {
		return sectorTo;
	}
	/**
	 * @param sectorTo The sectorTo to set.
	 */
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}
	/**
	 * @return Returns the subClass.
	 */
	public String getSubClass() {
		return subClass;
	}
	/**
	 * @param subClass The subClass to set.
	 */
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	/**
	 * @return Returns the weight.
	 */
	public Double getWeight() {
		return weight;
	}
	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the airlineIdr
	 */
	public int getAirlineIdr() {
		return airlineIdr;
	}
	/**
	 * @param airlineIdr the airlineIdr to set
	 */
	public void setAirlineIdr(int airlineIdr) {
		this.airlineIdr = airlineIdr;
	}
	/**
	 * @return the memoFlag
	 */
	public String getMemoFlag() {
		return memoFlag;
	}
	/**
	 * @param memoFlag the memoFlag to set
	 */
	public void setMemoFlag(String memoFlag) {
		this.memoFlag = memoFlag;
	}
	/**
	 * @return the serviceTax
	 */
	public Money getServiceTax() {
		return serviceTax;
	}
	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(Money serviceTax) {
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
	public Money getNetAmount() {
		return netAmount;
	}
	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(Money netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 * @return the grossAmount
	 */
	public Money getGrossAmount() {
		return grossAmount;
	}
	/**
	 * @param grossAmount the grossAmount to set
	 */
	public void setGrossAmount(Money grossAmount) {
		this.grossAmount = grossAmount;
	}
	/**
	 * @return the applicableRate
	 */
	public double getApplicableRate() {
		return applicableRate;
	}
	/**
	 * @param applicableRate the applicableRate to set
	 */
	public void setApplicableRate(double applicableRate) {
		this.applicableRate = applicableRate;
	}
	/**
	 * @param valCharges the valCharges to set
	 */
	public void setValCharges(Money valCharges) {
		this.valCharges = valCharges;
	}
	/**
	 * @return the valCharges
	 */
	public Money getValCharges() {
		return valCharges;
	}
	/**
	 * 	Getter for declaredValue 
	 *	Added by : A-6991 on 24-Aug-2017
	 * 	Used for :
	 */
	public double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 *  @param declaredValue the declaredValue to set
	 * 	Setter for declaredValue 
	 *	Added by : A-6991 on 24-Aug-2017
	 * 	Used for :
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the mailChg
	 */
	public Money getMailChg() {
		return mailChg;
	}
	/**
	 * @param mailChg the mailChg to set
	 */
	public void setMailChg(Money mailChg) {
		this.mailChg = mailChg;
	}
	/**
	 * @return the surChg
	 */
	public Money getSurChg() {
		return surChg;
	}
	/**
	 * @param surChg the surChg to set
	 */
	public void setSurChg(Money surChg) {
		this.surChg = surChg;
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
	public String getScreenID() {
		return screenID;
	}
	public void setScreenID(String screenID) {
		this.screenID = screenID;
	}
	/**
	 * @return the rateLineIdentifier
	 */
	public String getRateLineIdentifier() {
		return rateLineIdentifier;
	}
	/**
	 * @param rateLineIdentifier the rateLineIdentifier to set
	 */
	public void setRateLineIdentifier(String rateLineIdentifier) {
		this.rateLineIdentifier = rateLineIdentifier;
	}
	/**
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public String getProrateException() {
		return prorateException;
	}
	public void setProrateException(String prorateException) {
		this.prorateException = prorateException;
	}
	/**
	 * @return the triggerPoint
	 */
	public String getTriggerPoint() {
		return triggerPoint;
	}
	/**
	 * @param triggerPoint the triggerPoint to set
	 */
	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	
	public String getIsUSPSPerformed() {
		return isUSPSPerformed;
	}
	public void setIsUSPSPerformed(String isUSPSPerformed) {
		this.isUSPSPerformed = isUSPSPerformed;
	}
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	public String getConsignmentSeqNumber() {
		return consignmentSeqNumber;
	}
	public void setConsignmentSeqNumber(String consignmentSeqNumber) {
		this.consignmentSeqNumber = consignmentSeqNumber;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getPaBuilt() {
		return paBuilt;
	}
	public void setPaBuilt(String paBuilt) {
		this.paBuilt = paBuilt;
	}
	public String getContainerID() {
		return containerID;
	}
	public void setContainerID(String containerID) {
		this.containerID = containerID;
	}
	
	public double getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}
	/**
<<<<<<< HEAD
	 * 	Getter for upliftAirport 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public String getUpliftAirport() {
		return upliftAirport;
	}
	/**
	 *  @param upliftAirport the upliftAirport to set
	 * 	Setter for upliftAirport 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public void setUpliftAirport(String upliftAirport) {
		this.upliftAirport = upliftAirport;
	}
	/**
	 * 	Getter for dischargeAirport 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public String getDischargeAirport() {
		return dischargeAirport;
	}
	/**
	 *  @param dischargeAirport the dischargeAirport to set
	 * 	Setter for dischargeAirport 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public void setDischargeAirport(String dischargeAirport) {
		this.dischargeAirport = dischargeAirport;
	}
	/**
	 * 	Getter for transferAirline 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public String getTransferAirline() {
		return transferAirline;
	}
	/**
	 *  @param transferAirline the transferAirline to set
	 * 	Setter for transferAirline 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public void setTransferAirline(String transferAirline) {
		this.transferAirline = transferAirline;
	}
	/**
	 * 	Getter for transferPA 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public String getTransferPA() {
		return transferPA;
	}
	/**
	 *  @param transferPA the transferPA to set
	 * 	Setter for transferPA 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public void setTransferPA(String transferPA) {
		this.transferPA = transferPA;
	}
	/**
	 * 	Getter for originOE 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public String getOriginOE() {
		return originOE;
	}
	/**
	 *  @param originOE the originOE to set
	 * 	Setter for originOE 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}
	/**
	 * 	Getter for destinationOE 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public String getDestinationOE() {
		return destinationOE;
	}
	/**
	 *  @param destinationOE the destinationOE to set
	 * 	Setter for destinationOE 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}
	/**
	 * 	Getter for originAirport 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public String getOriginAirport() {
		return originAirport;
	}
	/**
	 *  @param originAirport the originAirport to set
	 * 	Setter for originAirport 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}
	/**
	 * 	Getter for destinationAirport 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}
	/**
	 *  @param destinationAirport the destinationAirport to set
	 * 	Setter for destinationAirport 
	 *	Added by : A-8061 on 19-Feb-2021
	 * 	Used for :
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	 /* 	Getter for filterMode 
	 *	Added by : A-8061 on 23-Oct-2020
	 * 	Used for :
	 */
	public String getFilterMode() {
		return filterMode;
	}
	/**
	 *  @param filterMode the filterMode to set
	 * 	Setter for filterMode 
	 *	Added by : A-8061 on 23-Oct-2020
	 * 	Used for :
	 */
	public void setFilterMode(String filterMode) {
		this.filterMode = filterMode;

	}	
	
	
}
