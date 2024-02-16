/*
 * MailInvoicEnquiryDetailsVO.java created on Jul 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2270
 *
 */
public class MailInvoicEnquiryDetailsVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	private String receptacleIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	private String rateTypeIndicator;
	
	private String gcm;
	
	private String consignmentDocumentNumber;
	
	private Money baseTotalAmount;
	
	private double LHDollarRate;
	
	private double THDollarRate;
	
	private String contractType;
	
	private double containerWeight;
	
    private String carrierCode;
	
	private String carrierName;
	
	private LocalDate scheduledInvoiceDate;
	
	private String controlValue;
	
	private Money totalAdjustmentAmount;
	
	private String reconcilStatus;
	
	private String paymentType;
	// for LocationTransportation Details
	private String payCarrier;
	private String originAirport;
	private String destinationAirport;
	private String carrierFinalDest;
	private String carrierAssigned;
	private String scanCarrier;
	private String scanLocation;
	private LocalDate scanDate;
	private LocalDate departureDate;
	private LocalDate arrivalDate;
	private String possessionCarrier;
	private String possessionLocation;
	private LocalDate possessionDate;
    private String dLVScanCarrier;
    private String dLVScanLocation;
    private LocalDate dLVScanDate;
    
    
    // for price payment details
    private Money adjustmentTotalAmount;
	private double lineHaulCharge;
	private double terminalHandlingCharge;
	private double lineHaulDollarRate;
	private double lineHaulSDRRate;
	//private double tHDollarRate;
	private double tHSDRRate;
	private double containerRate;
	private double  sDRRate;
	private LocalDate sDRDate;
	
	/**
	 * @return the consignmentDocumentNumber
	 */
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}

	/**
	 * @param consignmentDocumentNumber the consignmentDocumentNumber to set
	 */
	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}

	/**
	 * @return the containerWeight
	 */
	public double getContainerWeight() {
		return containerWeight;
	}

	/**
	 * @param containerWeight the containerWeight to set
	 */
	public void setContainerWeight(double containerWeight) {
		this.containerWeight = containerWeight;
	}

	/**
	 * @return the contractType
	 */
	public String getContractType() {
		return contractType;
	}

	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	/**
	 * @return the gcm
	 */
	public String getGcm() {
		return gcm;
	}

	/**
	 * @param gcm the gcm to set
	 */
	public void setGcm(String gcm) {
		this.gcm = gcm;
	}

	/**
	 * @return the lHDollarRate
	 */
	public double getLHDollarRate() {
		return LHDollarRate;
	}

	/**
	 * @param dollarRate the lHDollarRate to set
	 */
	public void setLHDollarRate(double dollarRate) {
		LHDollarRate = dollarRate;
	}

	/**
	 * @return the rateTypeIndicator
	 */
	public String getRateTypeIndicator() {
		return rateTypeIndicator;
	}

	/**
	 * @param rateTypeIndicator the rateTypeIndicator to set
	 */
	public void setRateTypeIndicator(String rateTypeIndicator) {
		this.rateTypeIndicator = rateTypeIndicator;
	}

	/**
	 * @return the receptacleIdentifier
	 */
	public String getReceptacleIdentifier() {
		return receptacleIdentifier;
	}

	/**
	 * @param receptacleIdentifier the receptacleIdentifier to set
	 */
	public void setReceptacleIdentifier(String receptacleIdentifier) {
		this.receptacleIdentifier = receptacleIdentifier;
	}

	/**
	 * @return the sectorDestination
	 */
	public String getSectorDestination() {
		return sectorDestination;
	}

	/**
	 * @param sectorDestination the sectorDestination to set
	 */
	public void setSectorDestination(String sectorDestination) {
		this.sectorDestination = sectorDestination;
	}

	/**
	 * @return the sectorOrigin
	 */
	public String getSectorOrigin() {
		return sectorOrigin;
	}

	/**
	 * @param sectorOrigin the sectorOrigin to set
	 */
	public void setSectorOrigin(String sectorOrigin) {
		this.sectorOrigin = sectorOrigin;
	}

	/**
	 * @return the tHDollarRate
	 */
	public double getTHDollarRate() {
		return THDollarRate;
	}

	/**
	 * @param dollarRate the tHDollarRate to set
	 */
	public void setTHDollarRate(double dollarRate) {
		THDollarRate = dollarRate;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the invoiceKey
	 */
	public String getInvoiceKey() {
		return invoiceKey;
	}

	/**
	 * @param invoiceKey the invoiceKey to set
	 */
	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}

	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the carrierName
	 */
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName the carrierName to set
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	/**
	 * @return the controlValue
	 */
	public String getControlValue() {
		return controlValue;
	}

	/**
	 * @param controlValue the controlValue to set
	 */
	public void setControlValue(String controlValue) {
		this.controlValue = controlValue;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the reconcilStatus
	 */
	public String getReconcilStatus() {
		return reconcilStatus;
	}

	/**
	 * @param reconcilStatus the reconcilStatus to set
	 */
	public void setReconcilStatus(String reconcilStatus) {
		this.reconcilStatus = reconcilStatus;
	}

	/**
	 * @return the scheduledInvoiceDate
	 */
	public LocalDate getScheduledInvoiceDate() {
		return scheduledInvoiceDate;
	}

	/**
	 * @param scheduledInvoiceDate the scheduledInvoiceDate to set
	 */
	public void setScheduledInvoiceDate(LocalDate scheduledInvoiceDate) {
		this.scheduledInvoiceDate = scheduledInvoiceDate;
	}

	
	/**
	 * @return the arrivalDate
	 */
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the carrierAssigned
	 */
	public String getCarrierAssigned() {
		return carrierAssigned;
	}

	/**
	 * @param carrierAssigned the carrierAssigned to set
	 */
	public void setCarrierAssigned(String carrierAssigned) {
		this.carrierAssigned = carrierAssigned;
	}

	/**
	 * @return the carrierFinalDest
	 */
	public String getCarrierFinalDest() {
		return carrierFinalDest;
	}

	/**
	 * @param carrierFinalDest the carrierFinalDest to set
	 */
	public void setCarrierFinalDest(String carrierFinalDest) {
		this.carrierFinalDest = carrierFinalDest;
	}

	/**
	 * @return the containerRate
	 */
	public double getContainerRate() {
		return containerRate;
	}

	/**
	 * @param containerRate the containerRate to set
	 */
	public void setContainerRate(double containerRate) {
		this.containerRate = containerRate;
	}

	/**
	 * @return the departureDate
	 */
	public LocalDate getDepartureDate() {
		return departureDate;
	}

	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * @return the destinationAirport
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}

	/**
	 * @param destinationAirport the destinationAirport to set
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	/**
	 * @return the dLVScanCarrier
	 */
	public String getDLVScanCarrier() {
		return dLVScanCarrier;
	}

	/**
	 * @param scanCarrier the dLVScanCarrier to set
	 */
	public void setDLVScanCarrier(String scanCarrier) {
		dLVScanCarrier = scanCarrier;
	}

	/**
	 * @return the dLVScanDate
	 */
	public LocalDate getDLVScanDate() {
		return dLVScanDate;
	}

	/**
	 * @param scanDate the dLVScanDate to set
	 */
	public void setDLVScanDate(LocalDate scanDate) {
		dLVScanDate = scanDate;
	}

	/**
	 * @return the dLVScanLocation
	 */
	public String getDLVScanLocation() {
		return dLVScanLocation;
	}

	/**
	 * @param scanLocation the dLVScanLocation to set
	 */
	public void setDLVScanLocation(String scanLocation) {
		dLVScanLocation = scanLocation;
	}

	
	/**
	 * @return the lineHaulCharge
	 */
	public double getLineHaulCharge() {
		return lineHaulCharge;
	}

	/**
	 * @param lineHaulCharge the lineHaulCharge to set
	 */
	public void setLineHaulCharge(double lineHaulCharge) {
		this.lineHaulCharge = lineHaulCharge;
	}

	/**
	 * @return the lineHaulDollarRate
	 */
	public double getLineHaulDollarRate() {
		return lineHaulDollarRate;
	}

	/**
	 * @param lineHaulDollarRate the lineHaulDollarRate to set
	 */
	public void setLineHaulDollarRate(double lineHaulDollarRate) {
		this.lineHaulDollarRate = lineHaulDollarRate;
	}

	/**
	 * @return the lineHaulSDRRate
	 */
	public double getLineHaulSDRRate() {
		return lineHaulSDRRate;
	}

	/**
	 * @param lineHaulSDRRate the lineHaulSDRRate to set
	 */
	public void setLineHaulSDRRate(double lineHaulSDRRate) {
		this.lineHaulSDRRate = lineHaulSDRRate;
	}

	/**
	 * @return the originAirport
	 */
	public String getOriginAirport() {
		return originAirport;
	}

	/**
	 * @param originAirport the originAirport to set
	 */
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}

	/**
	 * @return the payCarrier
	 */
	public String getPayCarrier() {
		return payCarrier;
	}

	/**
	 * @param payCarrier the payCarrier to set
	 */
	public void setPayCarrier(String payCarrier) {
		this.payCarrier = payCarrier;
	}

	/**
	 * @return the possessionCarrier
	 */
	public String getPossessionCarrier() {
		return possessionCarrier;
	}

	/**
	 * @param possessionCarrier the possessionCarrier to set
	 */
	public void setPossessionCarrier(String possessionCarrier) {
		this.possessionCarrier = possessionCarrier;
	}

	/**
	 * @return the possessionDate
	 */
	public LocalDate getPossessionDate() {
		return possessionDate;
	}

	/**
	 * @param possessionDate the possessionDate to set
	 */
	public void setPossessionDate(LocalDate possessionDate) {
		this.possessionDate = possessionDate;
	}

	/**
	 * @return the possessionLocation
	 */
	public String getPossessionLocation() {
		return possessionLocation;
	}

	/**
	 * @param possessionLocation the possessionLocation to set
	 */
	public void setPossessionLocation(String possessionLocation) {
		this.possessionLocation = possessionLocation;
	}

	/**
	 * @return the scanCarrier
	 */
	public String getScanCarrier() {
		return scanCarrier;
	}

	/**
	 * @param scanCarrier the scanCarrier to set
	 */
	public void setScanCarrier(String scanCarrier) {
		this.scanCarrier = scanCarrier;
	}

	/**
	 * @return the scanDate
	 */
	public LocalDate getScanDate() {
		return scanDate;
	}

	/**
	 * @param scanDate the scanDate to set
	 */
	public void setScanDate(LocalDate scanDate) {
		this.scanDate = scanDate;
	}

	/**
	 * @return the scanLocation
	 */
	public String getScanLocation() {
		return scanLocation;
	}

	/**
	 * @param scanLocation the scanLocation to set
	 */
	public void setScanLocation(String scanLocation) {
		this.scanLocation = scanLocation;
	}

	/**
	 * @return the sDRDate
	 */
	public LocalDate getSDRDate() {
		return sDRDate;
	}

	/**
	 * @param date the sDRDate to set
	 */
	public void setSDRDate(LocalDate date) {
		sDRDate = date;
	}

	/**
	 * @return the sDRRate
	 */
	public double getSDRRate() {
		return sDRRate;
	}

	/**
	 * @param rate the sDRRate to set
	 */
	public void setSDRRate(double rate) {
		sDRRate = rate;
	}

	/**
	 * @return the terminalHandlingCharge
	 */
	public double getTerminalHandlingCharge() {
		return terminalHandlingCharge;
	}

	/**
	 * @param terminalHandlingCharge the terminalHandlingCharge to set
	 */
	public void setTerminalHandlingCharge(double terminalHandlingCharge) {
		this.terminalHandlingCharge = terminalHandlingCharge;
	}

	/**
	 * @return the tHSDRRate
	 */
	public double getTHSDRRate() {
		return tHSDRRate;
	}

	/**
	 * @param rate the tHSDRRate to set
	 */
	public void setTHSDRRate(double rate) {
		tHSDRRate = rate;
	}

	/**
	 * @return Returns the adjustmentTotalAmount.
	 */
	public Money getAdjustmentTotalAmount() {
		return adjustmentTotalAmount;
	}

	/**
	 * @param adjustmentTotalAmount The adjustmentTotalAmount to set.
	 */
	public void setAdjustmentTotalAmount(Money adjustmentTotalAmount) {
		this.adjustmentTotalAmount = adjustmentTotalAmount;
	}

	/**
	 * @return Returns the baseTotalAmount.
	 */
	public Money getBaseTotalAmount() {
		return baseTotalAmount;
	}

	/**
	 * @param baseTotalAmount The baseTotalAmount to set.
	 */
	public void setBaseTotalAmount(Money baseTotalAmount) {
		this.baseTotalAmount = baseTotalAmount;
	}

	/**
	 * @return Returns the totalAdjustmentAmount.
	 */
	public Money getTotalAdjustmentAmount() {
		return totalAdjustmentAmount;
	}

	/**
	 * @param totalAdjustmentAmount The totalAdjustmentAmount to set.
	 */
	public void setTotalAdjustmentAmount(Money totalAdjustmentAmount) {
		this.totalAdjustmentAmount = totalAdjustmentAmount;
	}
	
		
	
}