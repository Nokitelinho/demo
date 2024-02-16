package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-7371
 *
 */
public class MailInvoicMessageDetailVO extends AbstractVO  {
	
	private String companyCode;
	
	private int serialNumber;
	
	private int sequenceNumber;
	
	private String invoiceNumber;
	
	private String consignmentNumber;
	
	private String poaCode;
	
	private String mailCategoryCode;
	
	private  String mailSubClassCode;
	
	private String mailProductCode;
	
	private String mailBagId;
	
	private String paymentLevel;
	
	private String lateIndicator;
	
	private String rateTypeIndicator;
	
	private String payCycleIndicator;
	
	private String adjustmentReasonCode;
	
	private LocalDate requiredDeliveryDate;
	
	private String containerNumber;
	
	private String claimAdjustmentCode;
	
	private String claimText;
	
	private String claimStatus;
	
	private String claimReasonCode;
	
	private Money baseTotalAmount;
	
	private Money adjustmentTotalAmount;
	
	private Money lineHaulCharge;
	
	private Money terminalHandlingCharge;
	
	private Money otherValuationCharge;
	
	private Money containerChargeAmount;
	
	private Money charterChargeAmount;
	
	private LocalDate possessionScanDate;
	
	private  double lineHaulDollarRate;
	
	private double lineHaulSDRRate;
	
	private double terminalHandlingDollarRate;
	
	private double terminalHandlingSDRRate;
	
	private double specialPerKiloDollarRate;
	
	private double specialPerKiloSDRRate;
	
	private double rangeDependentRate;
	
	private double activeIngredientRate;
	
	private double containerRate;
	
	private LocalDate sdrDate;
	
	private double sdrRate;
	
	private LocalDate consignmentCompletionDate;
	
	private LocalDate transportationWindowEndTime;
	
	private String weightUnit;
	
	private double grossWeight;
	
	private String containerWeightUnit;
	
	private double containerGrossWeight;
	
	private String orginAirport;
	
	private String destinationAirport;
	
	private String offlineOriginAirport;
	
	private String truckLocation;
	
	private String carrierFinalDestination;
	
	private String originalOriginAirport;
	
	private String finalDestination;
	
	private String deliveryScanCarrierCode;
	
	private String deleiveryScanLocation;
	
	private LocalDate deliveryDate;
	
	private String zeroPayReceptacleCode;
	
	private double terminalHandlingScanRate;
	
	private double sortRate;
	
	private double liveRate;
	
	private double  hubrelabelingRate;
	
	private  double additionalSeparationRate;

	private String adjustmentIndicator;
	
	private String misSentIndicator;
	
	private String scanPayIndicator;
	
	private String greatCircleMiles;
	
	private String greatCircleMilesUnit;
	
	private String payRateCode;
	
	private String contractType;
	
	private String carrierToPay;
	
	private String containerType;

	private LocalDate routeDepatureDate;
	
	private LocalDate assignedDate;
	
	
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
	
	private Money terminalHandlingScanningCharge;
	private Money sortCharge;
	private Money hubReLabelingCharge;
	private Money liveCharge;
	private Money additionalSeparationCharge;
	private Money adjustedTerminalHandlingCharge;
	private String sortScanCarrier;
	private String sortScanExpectedSite;
	private String sortScanActualSite;
	private LocalDate sortScanDate;
	private LocalDate sortScanReceiveDate;
	private String claimNotes;


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

	public LocalDate getRouteDepatureDate() {
		return routeDepatureDate;
	}

	public void setRouteDepatureDate(LocalDate routeDepatureDate) {
		this.routeDepatureDate = routeDepatureDate;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	public void setConsignmentNumber(String consignmentNUmber) {
		this.consignmentNumber = consignmentNUmber;
	}

	public String getPoaCode() {
		return poaCode;
	}

	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
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

	public String getMailBagId() {
		return mailBagId;
	}

	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}

	public String getPaymentLevel() {
		return paymentLevel;
	}

	public void setPaymentLevel(String paymentLevel) {
		this.paymentLevel = paymentLevel;
	}

	public String getLateIndicator() {
		return lateIndicator;
	}

	public void setLateIndicator(String lateIndicator) {
		this.lateIndicator = lateIndicator;
	}

	public String getRateTypeIndicator() {
		return rateTypeIndicator;
	}

	public void setRateTypeIndicator(String rateTypeIndicator) {
		this.rateTypeIndicator = rateTypeIndicator;
	}

	public String getPayCycleIndicator() {
		return payCycleIndicator;
	}

	public void setPayCycleIndicator(String payCycleIndicator) {
		this.payCycleIndicator = payCycleIndicator;
	}

	public String getAdjustmentReasonCode() {
		return adjustmentReasonCode;
	}

	public void setAdjustmentReasonCode(String adjustmentReasonCode) {
		this.adjustmentReasonCode = adjustmentReasonCode;
	}

	public LocalDate getRequiredDeliveryDate() {
		return requiredDeliveryDate;
	}

	public void setRequiredDeliveryDate(LocalDate requiredDeliveryDate) {
		this.requiredDeliveryDate = requiredDeliveryDate;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getClaimAdjustmentCode() {
		return claimAdjustmentCode;
	}

	public void setClaimAdjustmentCode(String claimAdjustmentCode) {
		this.claimAdjustmentCode = claimAdjustmentCode;
	}

	public String getClaimText() {
		return claimText;
	}

	public void setClaimText(String claimText) {
		this.claimText = claimText;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	public Money getBaseTotalAmount() {
		return baseTotalAmount;
	}

	public void setBaseTotalAmount(Money baseTotalAmount) {
		this.baseTotalAmount = baseTotalAmount;
	}

	public Money getAdjustmentTotalAmount() {
		return adjustmentTotalAmount;
	}

	public void setAdjustmentTotalAmount(Money adjustmentTotalAmount) {
		this.adjustmentTotalAmount = adjustmentTotalAmount;
	}

	public Money getLineHaulCharge() {
		return lineHaulCharge;
	}

	public void setLineHaulCharge(Money lineHaulCharge) {
		this.lineHaulCharge = lineHaulCharge;
	}

	public Money getTerminalHandlingCharge() {
		return terminalHandlingCharge;
	}

	public void setTerminalHandlingCharge(Money terminalHandlingCharge) {
		this.terminalHandlingCharge = terminalHandlingCharge;
	}

	public Money getOtherValuationCharge() {
		return otherValuationCharge;
	}

	public void setOtherValuationCharge(Money otherValuationCharge) {
		this.otherValuationCharge = otherValuationCharge;
	}

	public Money getContainerChargeAmount() {
		return containerChargeAmount;
	}

	public void setContainerChargeAmount(Money containerChargeAmount) {
		this.containerChargeAmount = containerChargeAmount;
	}

	public Money getCharterChargeAmount() {
		return charterChargeAmount;
	}

	public void setCharterChargeAmount(Money charterChargeAmount) {
		this.charterChargeAmount = charterChargeAmount;
	}

	public LocalDate getPossessionScanDate() {
		return possessionScanDate;
	}

	public void setPossessionScanDate(LocalDate possessionScanDate) {
		this.possessionScanDate = possessionScanDate;
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

	public LocalDate getSdrDate() {
		return sdrDate;
	}

	public void setSdrDate(LocalDate sdrDate) {
		this.sdrDate = sdrDate;
	}

	public double getSdrRate() {
		return sdrRate;
	}

	public void setSdrRate(double sdrRate) {
		this.sdrRate = sdrRate;
	}

	public LocalDate getConsignmentCompletionDate() {
		return consignmentCompletionDate;
	}

	public void setConsignmentCompletionDate(LocalDate consignmentCompletionDate) {
		this.consignmentCompletionDate = consignmentCompletionDate;
	}

	public LocalDate getTransportationWindowEndTime() {
		return transportationWindowEndTime;
	}

	public void setTransportationWindowEndTime(LocalDate transportationWindowEndTime) {
		this.transportationWindowEndTime = transportationWindowEndTime;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
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

	public String getTruckLocation() {
		return truckLocation;
	}

	public void setTruckLocation(String truckLocation) {
		this.truckLocation = truckLocation;
	}

	public String getCarrierFinalDestination() {
		return carrierFinalDestination;
	}

	public void setCarrierFinalDestination(String carrierFinalDestination) {
		this.carrierFinalDestination = carrierFinalDestination;
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

	public String getDeliveryScanCarrierCode() {
		return deliveryScanCarrierCode;
	}

	public void setDeliveryScanCarrierCode(String deliveryScanCarrierCode) {
		this.deliveryScanCarrierCode = deliveryScanCarrierCode;
	}

	public String getDeleiveryScanLocation() {
		return deleiveryScanLocation;
	}

	public void setDeleiveryScanLocation(String deleiveryScanLocation) {
		this.deleiveryScanLocation = deleiveryScanLocation;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getZeroPayReceptacleCode() {
		return zeroPayReceptacleCode;
	}

	public void setZeroPayReceptacleCode(String zeroPayReceptacleCode) {
		this.zeroPayReceptacleCode = zeroPayReceptacleCode;
	}

	public double getTerminalHandlingScanRate() {
		return terminalHandlingScanRate;
	}

	public void setTerminalHandlingScanRate(double terminalHandlingScanRate) {
		this.terminalHandlingScanRate = terminalHandlingScanRate;
	}

	public double getSortRate() {
		return sortRate;
	}

	public void setSortRate(double sortRate) {
		this.sortRate = sortRate;
	}

	public double getLiveRate() {
		return liveRate;
	}

	public void setLiveRate(double liveRate) {
		this.liveRate = liveRate;
	}

	public double getHubrelabelingRate() {
		return hubrelabelingRate;
	}

	public void setHubrelabelingRate(double hubrelabelingRate) {
		this.hubrelabelingRate = hubrelabelingRate;
	}

	public double getAdditionalSeparationRate() {
		return additionalSeparationRate;
	}

	public void setAdditionalSeparationRate(double additionalSeparationRate) {
		this.additionalSeparationRate = additionalSeparationRate;
	}

	public String getClaimReasonCode() {
		return claimReasonCode;
	}

	public void setClaimReasonCode(String claimReasonCode) {
		this.claimReasonCode = claimReasonCode;
	}

	public double getTerminalHandlingSDRRate() {
		return terminalHandlingSDRRate;
	}

	public void setTerminalHandlingSDRRate(double terminalHandlingSDRRate) {
		this.terminalHandlingSDRRate = terminalHandlingSDRRate;
	}

	public double getRangeDependentRate() {
		return rangeDependentRate;
	}

	public void setRangeDependentRate(double rangeDependentRate) {
		this.rangeDependentRate = rangeDependentRate;
	}

	public double getActiveIngredientRate() {
		return activeIngredientRate;
	}

	public void setActiveIngredientRate(double activeIngredientRate) {
		this.activeIngredientRate = activeIngredientRate;
	}

	public String getAdjustmentIndicator() {
		return adjustmentIndicator;
	}

	public void setAdjustmentIndicator(String adjustmentIndicator) {
		this.adjustmentIndicator = adjustmentIndicator;
	}

	public String getMisSentIndicator() {
		return misSentIndicator;
	}

	public void setMisSentIndicator(String misSentIndicator) {
		this.misSentIndicator = misSentIndicator;
	}

	public String getScanPayIndicator() {
		return scanPayIndicator;
	}

	public void setScanPayIndicator(String scanPayIndicator) {
		this.scanPayIndicator = scanPayIndicator;
	}

	public String getGreatCircleMiles() {
		return greatCircleMiles;
	}

	public void setGreatCircleMiles(String greatCircleMiles) {
		this.greatCircleMiles = greatCircleMiles;
	}

	public String getGreatCircleMilesUnit() {
		return greatCircleMilesUnit;
	}

	public void setGreatCircleMilesUnit(String greatCircleMilesUnit) {
		this.greatCircleMilesUnit = greatCircleMilesUnit;
	}

	public String getPayRateCode() {
		return payRateCode;
	}

	public void setPayRateCode(String payRateCode) {
		this.payRateCode = payRateCode;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getCarrierToPay() {
		return carrierToPay;
	}

	public void setCarrierToPay(String carrierToPay) {
		this.carrierToPay = carrierToPay;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public Money getTerminalHandlingScanningCharge() {
		return terminalHandlingScanningCharge;
	}

	public void setTerminalHandlingScanningCharge(Money terminalHandlingScanningCharge) {
		this.terminalHandlingScanningCharge = terminalHandlingScanningCharge;
	}

	public Money getSortCharge() {
		return sortCharge;
	}

	public void setSortCharge(Money sortCharge) {
		this.sortCharge = sortCharge;
	}

	public Money getHubReLabelingCharge() {
		return hubReLabelingCharge;
	}

	public void setHubReLabelingCharge(Money hubReLabelingCharge) {
		this.hubReLabelingCharge = hubReLabelingCharge;
	}

	public Money getLiveCharge() {
		return liveCharge;
	}

	public void setLiveCharge(Money liveCharge) {
		this.liveCharge = liveCharge;
	}

	public Money getAdditionalSeparationCharge() {
		return additionalSeparationCharge;
	}

	public void setAdditionalSeparationCharge(Money additionalSeparationCharge) {
		this.additionalSeparationCharge = additionalSeparationCharge;
	}

	public Money getAdjustedTerminalHandlingCharge() {
		return adjustedTerminalHandlingCharge;
	}

	public void setAdjustedTerminalHandlingCharge(Money adjustedTerminalHandlingCharge) {
		this.adjustedTerminalHandlingCharge = adjustedTerminalHandlingCharge;
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

	public LocalDate getSortScanDate() {
		return sortScanDate;
	}

	public void setSortScanDate(LocalDate sortScanDate) {
		this.sortScanDate = sortScanDate;
	}

	public LocalDate getSortScanReceiveDate() {
		return sortScanReceiveDate;
	}

	public void setSortScanReceiveDate(LocalDate sortScanReceiveDate) {
		this.sortScanReceiveDate = sortScanReceiveDate;
	}
	public String getClaimNotes() {
		return claimNotes;
	}
	public void setClaimNotes(String claimNotes) {
		this.claimNotes = claimNotes;
	}
	
	
	
	

}
