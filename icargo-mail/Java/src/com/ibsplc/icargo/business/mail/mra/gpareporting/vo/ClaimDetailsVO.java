/**
 * ClaimDetailsVO.java Created on March 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-8257
 *
 */
public class ClaimDetailsVO extends AbstractVO{
	
 //this is the filter needed for input for list query in sql	    
	private String gpaCode;
	private LocalDate fromDate;
	private LocalDate toDate;
	private int noOfMailbags;
    private int pageNumber;
    private String currency;
	private int totalRecords;
	private int defaultPageSize;
	private String cmpcod;
	private double claimAmount;
	private long sernum;
	private String claimGenerateFlag;
	private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	private String mailBagId;
	private long mailSeqNumber;
	private int seqNumber;
	private String claimType;
	private String claimStatus;
	private LocalDate receivedDate;
	private LocalDate claimSubDate;
	private  String claimRefNumber;
	private String invoicRefNumber;
	private int versnNumber;
	
	private String companyCode;
	private String controlNumber;
	private String regionCode;
	private String referenceVersionNumber;
	private String truckingLocation;
	private LocalDate routeArrivalDate;
	private String originTripStagQualifier;
	private String originTripFlightNumber;
	private String originTripCarrierCode;
	private String possessionScanStagQualifier;
	private String possessionScanCarrierCode;
	private String possessionScanExpectedSite;
	private String loadScanCarrierCode;
	private String loadScanStagQualifier;
	private String loadScanFlightNumber;
	private String loadScanExpectedSite;
	private LocalDate loadScanDate;
	private String firstTransferScanCarrier;
	private String firstTransferStagQualifier;
	private String firstTransferFlightNumber;
	private String firstTransferExpectedSite;
	private LocalDate firstTransferDate;
	private String secondTransferScanCarrier;
	private String secondTransferStagQualifier;
	private String secondTransferFlightNumber;
	private String secondTransferExpectedSite;
	private LocalDate secondTransferDate;
	private String thirdTransferScanCarrier;
	private String thirdTransferStagQualifier;
	private String thirdTransferFlightNumber;
	private String thirdTransferExpectedSite;
	private LocalDate thirdTransferDate;
	private String fourthTransferScanCarrier;
	private String fourthTransferStagQualifier;
	private String fourthTransferFlightNumber;
	private String fourthTransferExpectedSite;
	private LocalDate fourthTransferDate;
	private String deliverTransferStagQualifier;
	private String deliverScanActualSite;
	private String carrierFinalDestination;
	private String rateTypeIndicator;
	private String greatCircleMiles;
	private String consignmentNumber;
	private String mailCategoryCode;
	private  String mailSubClassCode;
	private String mailProductCode;
	private String payRateCode;
	private  double lineHaulDollarRate;
	private double lineHaulSDRRate;
	private double terminalHandlingDollarRate;
	private double terminalHandlingSDRRate;
	private double specialPerKiloDollarRate;
	private double specialPerKiloSDRRate;
	private double containerRate;
	private String containerType;
	private String weightUnit;
	private String containerWeightUnit;
	private double containerGrossWeight;
	private LocalDate consignmentCompletionDate;
	private String contractType;
	private double grossWeight;
	private String carrierToPay;
	private String orginAirport;
	private String destinationAirport;
	private String offlineOriginAirport;
	private String deliveryScanCarrierCode;
	private String originalOriginAirport;
	private String finalDestination;
	private LocalDate routeDepatureDate;
	private LocalDate possessionScanDate;
	private LocalDate deliveryDate;
	private Money baseTotalAmount;
	private String deliveryScanExpectedSite;
	private String messageText;
	private String sortScanCarrier;
	private String sortScanExpectedSite;
	private String sortScanActualSite;
	private LocalDate sortScanActualDate;
	private LocalDate sortScanRecDate;
	private String mailServiceLevel;

	private String resditSendFlag;
	private String mailClass;
	private String claimFilename;
	
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getCmpcod() {
		return cmpcod;
	}
	public void setCmpcod(String cmpcod) {
		this.cmpcod = cmpcod;
	}
	public int getNoOfMailbags() {
		return noOfMailbags;
	}
	public void setNoOfMailbags(int noOfMailbags) {
		this.noOfMailbags = noOfMailbags;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}
	public long getSernum() {
		return sernum;
	}
	public void setSernum(long sernum) {
		this.sernum = sernum;
	}
	public String getClaimGenerateFlag() {
		return claimGenerateFlag;
	}
	public void setClaimGenerateFlag(String claimGenerateFlag) {
		this.claimGenerateFlag = claimGenerateFlag;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public String getMailBagId() {
		return mailBagId;
	}
	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}
	public long getMailSeqNumber() {
		return mailSeqNumber;
	}
	public void setMailSeqNumber(long mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}
	public int getSeqNumber() {
		return seqNumber;
	}
	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public LocalDate getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}
	public LocalDate getClaimSubDate() {
		return claimSubDate;
	}
	public void setClaimSubDate(LocalDate claimSubDate) {
		this.claimSubDate = claimSubDate;
	}
	public String getClaimRefNumber() {
		return claimRefNumber;
	}
	public void setClaimRefNumber(String claimRefNumber) {
		this.claimRefNumber = claimRefNumber;
	}
	public String getInvoicRefNumber() {
		return invoicRefNumber;
	}
	public void setInvoicRefNumber(String invoicRefNumber) {
		this.invoicRefNumber = invoicRefNumber;
	}
	public int getVersnNumber() {
		return versnNumber;
	}
	public void setVersnNumber(int versnNumber) {
		this.versnNumber = versnNumber;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getControlNumber() {
		return controlNumber;
	}
	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getReferenceVersionNumber() {
		return referenceVersionNumber;
	}
	public void setReferenceVersionNumber(String referenceVersionNumber) {
		this.referenceVersionNumber = referenceVersionNumber;
	}
	public String getTruckingLocation() {
		return truckingLocation;
	}
	public void setTruckingLocation(String truckingLocation) {
		this.truckingLocation = truckingLocation;
	}
	public LocalDate getRouteArrivalDate() {
		return routeArrivalDate;
	}
	public void setRouteArrivalDate(LocalDate routeArrivalDate) {
		this.routeArrivalDate = routeArrivalDate;
	}
	public String getOriginTripStagQualifier() {
		return originTripStagQualifier;
	}
	public void setOriginTripStagQualifier(String originTripStagQualifier) {
		this.originTripStagQualifier = originTripStagQualifier;
	}
	public String getOriginTripFlightNumber() {
		return originTripFlightNumber;
	}
	public void setOriginTripFlightNumber(String originTripFlightNumber) {
		this.originTripFlightNumber = originTripFlightNumber;
	}
	public String getOriginTripCarrierCode() {
		return originTripCarrierCode;
	}
	public void setOriginTripCarrierCode(String originTripCarrierCode) {
		this.originTripCarrierCode = originTripCarrierCode;
	}
	public String getPossessionScanStagQualifier() {
		return possessionScanStagQualifier;
	}
	public void setPossessionScanStagQualifier(String possessionScanStagQualifier) {
		this.possessionScanStagQualifier = possessionScanStagQualifier;
	}
	public String getPossessionScanCarrierCode() {
		return possessionScanCarrierCode;
	}
	public void setPossessionScanCarrierCode(String possessionScanCarrierCode) {
		this.possessionScanCarrierCode = possessionScanCarrierCode;
	}
	public String getPossessionScanExpectedSite() {
		return possessionScanExpectedSite;
	}
	public void setPossessionScanExpectedSite(String possessionScanExpectedSite) {
		this.possessionScanExpectedSite = possessionScanExpectedSite;
	}
	public String getLoadScanCarrierCode() {
		return loadScanCarrierCode;
	}
	public void setLoadScanCarrierCode(String loadScanCarrierCode) {
		this.loadScanCarrierCode = loadScanCarrierCode;
	}
	public String getLoadScanStagQualifier() {
		return loadScanStagQualifier;
	}
	public void setLoadScanStagQualifier(String loadScanStagQualifier) {
		this.loadScanStagQualifier = loadScanStagQualifier;
	}
	public String getLoadScanFlightNumber() {
		return loadScanFlightNumber;
	}
	public void setLoadScanFlightNumber(String loadScanFlightNumber) {
		this.loadScanFlightNumber = loadScanFlightNumber;
	}
	public String getLoadScanExpectedSite() {
		return loadScanExpectedSite;
	}
	public void setLoadScanExpectedSite(String loadScanExpectedSite) {
		this.loadScanExpectedSite = loadScanExpectedSite;
	}
	public LocalDate getLoadScanDate() {
		return loadScanDate;
	}
	public void setLoadScanDate(LocalDate loadScanDate) {
		this.loadScanDate = loadScanDate;
	}
	public String getFirstTransferScanCarrier() {
		return firstTransferScanCarrier;
	}
	public void setFirstTransferScanCarrier(String firstTransferScanCarrier) {
		this.firstTransferScanCarrier = firstTransferScanCarrier;
	}
	public String getFirstTransferStagQualifier() {
		return firstTransferStagQualifier;
	}
	public void setFirstTransferStagQualifier(String firstTransferStagQualifier) {
		this.firstTransferStagQualifier = firstTransferStagQualifier;
	}
	public String getFirstTransferFlightNumber() {
		return firstTransferFlightNumber;
	}
	public void setFirstTransferFlightNumber(String firstTransferFlightNumber) {
		this.firstTransferFlightNumber = firstTransferFlightNumber;
	}
	public String getFirstTransferExpectedSite() {
		return firstTransferExpectedSite;
	}
	public void setFirstTransferExpectedSite(String firstTransferExpectedSite) {
		this.firstTransferExpectedSite = firstTransferExpectedSite;
	}
	public LocalDate getFirstTransferDate() {
		return firstTransferDate;
	}
	public void setFirstTransferDate(LocalDate firstTransferDate) {
		this.firstTransferDate = firstTransferDate;
	}
	public String getSecondTransferScanCarrier() {
		return secondTransferScanCarrier;
	}
	public void setSecondTransferScanCarrier(String secondTransferScanCarrier) {
		this.secondTransferScanCarrier = secondTransferScanCarrier;
	}
	public String getSecondTransferStagQualifier() {
		return secondTransferStagQualifier;
	}
	public void setSecondTransferStagQualifier(String secondTransferStagQualifier) {
		this.secondTransferStagQualifier = secondTransferStagQualifier;
	}
	public String getSecondTransferFlightNumber() {
		return secondTransferFlightNumber;
	}
	public void setSecondTransferFlightNumber(String secondTransferFlightNumber) {
		this.secondTransferFlightNumber = secondTransferFlightNumber;
	}
	public String getSecondTransferExpectedSite() {
		return secondTransferExpectedSite;
	}
	public void setSecondTransferExpectedSite(String secondTransferExpectedSite) {
		this.secondTransferExpectedSite = secondTransferExpectedSite;
	}
	public LocalDate getSecondTransferDate() {
		return secondTransferDate;
	}
	public void setSecondTransferDate(LocalDate secondTransferDate) {
		this.secondTransferDate = secondTransferDate;
	}
	public String getThirdTransferScanCarrier() {
		return thirdTransferScanCarrier;
	}
	public void setThirdTransferScanCarrier(String thirdTransferScanCarrier) {
		this.thirdTransferScanCarrier = thirdTransferScanCarrier;
	}
	public String getThirdTransferStagQualifier() {
		return thirdTransferStagQualifier;
	}
	public void setThirdTransferStagQualifier(String thirdTransferStagQualifier) {
		this.thirdTransferStagQualifier = thirdTransferStagQualifier;
	}
	public String getThirdTransferFlightNumber() {
		return thirdTransferFlightNumber;
	}
	public void setThirdTransferFlightNumber(String thirdTransferFlightNumber) {
		this.thirdTransferFlightNumber = thirdTransferFlightNumber;
	}
	public String getThirdTransferExpectedSite() {
		return thirdTransferExpectedSite;
	}
	public void setThirdTransferExpectedSite(String thirdTransferExpectedSite) {
		this.thirdTransferExpectedSite = thirdTransferExpectedSite;
	}
	public LocalDate getThirdTransferDate() {
		return thirdTransferDate;
	}
	public void setThirdTransferDate(LocalDate thirdTransferDate) {
		this.thirdTransferDate = thirdTransferDate;
	}
	public String getFourthTransferScanCarrier() {
		return fourthTransferScanCarrier;
	}
	public void setFourthTransferScanCarrier(String fourthTransferScanCarrier) {
		this.fourthTransferScanCarrier = fourthTransferScanCarrier;
	}
	public String getFourthTransferStagQualifier() {
		return fourthTransferStagQualifier;
	}
	public void setFourthTransferStagQualifier(String fourthTransferStagQualifier) {
		this.fourthTransferStagQualifier = fourthTransferStagQualifier;
	}
	public String getFourthTransferFlightNumber() {
		return fourthTransferFlightNumber;
	}
	public void setFourthTransferFlightNumber(String fourthTransferFlightNumber) {
		this.fourthTransferFlightNumber = fourthTransferFlightNumber;
	}
	public String getFourthTransferExpectedSite() {
		return fourthTransferExpectedSite;
	}
	public void setFourthTransferExpectedSite(String fourthTransferExpectedSite) {
		this.fourthTransferExpectedSite = fourthTransferExpectedSite;
	}
	public LocalDate getFourthTransferDate() {
		return fourthTransferDate;
	}
	public void setFourthTransferDate(LocalDate fourthTransferDate) {
		this.fourthTransferDate = fourthTransferDate;
	}
	public String getDeliverTransferStagQualifier() {
		return deliverTransferStagQualifier;
	}
	public void setDeliverTransferStagQualifier(String deliverTransferStagQualifier) {
		this.deliverTransferStagQualifier = deliverTransferStagQualifier;
	}
	public String getDeliverScanActualSite() {
		return deliverScanActualSite;
	}
	public void setDeliverScanActualSite(String deliverScanActualSite) {
		this.deliverScanActualSite = deliverScanActualSite;
	}
	public String getCarrierFinalDestination() {
		return carrierFinalDestination;
	}
	public void setCarrierFinalDestination(String carrierFinalDestination) {
		this.carrierFinalDestination = carrierFinalDestination;
	}
	public String getRateTypeIndicator() {
		return rateTypeIndicator;
	}
	public void setRateTypeIndicator(String rateTypeIndicator) {
		this.rateTypeIndicator = rateTypeIndicator;
	}
	public String getGreatCircleMiles() {
		return greatCircleMiles;
	}
	public void setGreatCircleMiles(String greatCircleMiles) {
		this.greatCircleMiles = greatCircleMiles;
	}
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	public String getMailSubClassCode() {
		return mailSubClassCode;
	}
	public void setMailSubClassCode(String mailSubClassCode) {
		this.mailSubClassCode = mailSubClassCode;
	}
	public String getMailProductCode() {
		return mailProductCode;
	}
	public void setMailProductCode(String mailProductCode) {
		this.mailProductCode = mailProductCode;
	}
	public String getPayRateCode() {
		return payRateCode;
	}
	public void setPayRateCode(String payRateCode) {
		this.payRateCode = payRateCode;
	}
	public double getLineHaulDollarRate() {
		return lineHaulDollarRate;
	}
	public void setLineHaulDollarRate(double lineHaulDollarRate) {
		this.lineHaulDollarRate = lineHaulDollarRate;
	}
	public double getLineHaulSDRRate() {
		return lineHaulSDRRate;
	}
	public void setLineHaulSDRRate(double lineHaulSDRRate) {
		this.lineHaulSDRRate = lineHaulSDRRate;
	}
	public double getTerminalHandlingDollarRate() {
		return terminalHandlingDollarRate;
	}
	public void setTerminalHandlingDollarRate(double terminalHandlingDollarRate) {
		this.terminalHandlingDollarRate = terminalHandlingDollarRate;
	}
	public double getTerminalHandlingSDRRate() {
		return terminalHandlingSDRRate;
	}
	public void setTerminalHandlingSDRRate(double terminalHandlingSDRRate) {
		this.terminalHandlingSDRRate = terminalHandlingSDRRate;
	}
	public double getSpecialPerKiloDollarRate() {
		return specialPerKiloDollarRate;
	}
	public void setSpecialPerKiloDollarRate(double specialPerKiloDollarRate) {
		this.specialPerKiloDollarRate = specialPerKiloDollarRate;
	}
	public double getSpecialPerKiloSDRRate() {
		return specialPerKiloSDRRate;
	}
	public void setSpecialPerKiloSDRRate(double specialPerKiloSDRRate) {
		this.specialPerKiloSDRRate = specialPerKiloSDRRate;
	}
	public double getContainerRate() {
		return containerRate;
	}
	public void setContainerRate(double containerRate) {
		this.containerRate = containerRate;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public String getContainerWeightUnit() {
		return containerWeightUnit;
	}
	public void setContainerWeightUnit(String containerWeightUnit) {
		this.containerWeightUnit = containerWeightUnit;
	}
	public double getContainerGrossWeight() {
		return containerGrossWeight;
	}
	public void setContainerGrossWeight(double containerGrossWeight) {
		this.containerGrossWeight = containerGrossWeight;
	}
	public LocalDate getConsignmentCompletionDate() {
		return consignmentCompletionDate;
	}
	public void setConsignmentCompletionDate(LocalDate consignmentCompletionDate) {
		this.consignmentCompletionDate = consignmentCompletionDate;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public double getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getCarrierToPay() {
		return carrierToPay;
	}
	public void setCarrierToPay(String carrierToPay) {
		this.carrierToPay = carrierToPay;
	}
	public String getOrginAirport() {
		return orginAirport;
	}
	public void setOrginAirport(String orginAirport) {
		this.orginAirport = orginAirport;
	}
	public String getDestinationAirport() {
		return destinationAirport;
	}
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	public String getOfflineOriginAirport() {
		return offlineOriginAirport;
	}
	public void setOfflineOriginAirport(String offlineOriginAirport) {
		this.offlineOriginAirport = offlineOriginAirport;
	}
	public String getDeliveryScanCarrierCode() {
		return deliveryScanCarrierCode;
	}
	public void setDeliveryScanCarrierCode(String deliveryScanCarrierCode) {
		this.deliveryScanCarrierCode = deliveryScanCarrierCode;
	}
	public String getOriginalOriginAirport() {
		return originalOriginAirport;
	}
	public void setOriginalOriginAirport(String originalOriginAirport) {
		this.originalOriginAirport = originalOriginAirport;
	}
	public String getFinalDestination() {
		return finalDestination;
	}
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}
	public LocalDate getRouteDepatureDate() {
		return routeDepatureDate;
	}
	public void setRouteDepatureDate(LocalDate routeDepatureDate) {
		this.routeDepatureDate = routeDepatureDate;
	}
	public LocalDate getPossessionScanDate() {
		return possessionScanDate;
	}
	public void setPossessionScanDate(LocalDate possessionScanDate) {
		this.possessionScanDate = possessionScanDate;
	}
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Money getBaseTotalAmount() {
		return baseTotalAmount;
	}
	public void setBaseTotalAmount(Money baseTotalAmount) {
		this.baseTotalAmount = baseTotalAmount;
	}
	public String getDeliveryScanExpectedSite() {
		return deliveryScanExpectedSite;
	}
	public void setDeliveryScanExpectedSite(String deliveryScanExpectedSite) {
		this.deliveryScanExpectedSite = deliveryScanExpectedSite;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public String getSortScanCarrier() {
		return sortScanCarrier;
	}
	public void setSortScanCarrier(String sortScanCarrier) {
		this.sortScanCarrier = sortScanCarrier;
	}
	public String getSortScanExpectedSite() {
		return sortScanExpectedSite;
	}
	public void setSortScanExpectedSite(String sortScanExpectedSite) {
		this.sortScanExpectedSite = sortScanExpectedSite;
	}
	public String getSortScanActualSite() {
		return sortScanActualSite;
	}
	public void setSortScanActualSite(String sortScanActualSite) {
		this.sortScanActualSite = sortScanActualSite;
	}
	public LocalDate getSortScanActualDate() {
		return sortScanActualDate;
	}
	public void setSortScanActualDate(LocalDate sortScanActualDate) {
		this.sortScanActualDate = sortScanActualDate;
	}
	public LocalDate getSortScanRecDate() {
		return sortScanRecDate;
	}
	public void setSortScanRecDate(LocalDate sortScanRecDate) {
		this.sortScanRecDate = sortScanRecDate;
	}

	/**
	 * 	Getter for resditSendFlag 
	 *	Added by : A-8061 on 27-Jun-2019
	 * 	Used for :
	 */
	public String getResditSendFlag() {
		return resditSendFlag;
	}
	/**
	 *  @param resditSendFlag the resditSendFlag to set
	 * 	Setter for resditSendFlag 
	 *	Added by : A-8061 on 27-Jun-2019
	 * 	Used for :
	 */
	public void setResditSendFlag(String resditSendFlag) {
		this.resditSendFlag = resditSendFlag;
	}
	
	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	public String getMailServiceLevel() {
		return mailServiceLevel;
	}
	public void setMailServiceLevel(String mailServiceLevel) {
		this.mailServiceLevel = mailServiceLevel;
	}
	public String getClaimFilename() {
		return claimFilename;
	}
	public void setClaimFilename(String claimFilename) {
		this.claimFilename = claimFilename;
	}
}
