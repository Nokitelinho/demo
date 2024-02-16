/*
 * MailInvoicTransportationDtlVO.java created on Jul 19, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class MailInvoicTransportationDtlVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	private String receptacleIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	//original carrier
	private String scannedCarrierCode;
	
	private String scannedFlightNumber;
	
	private String originalOrigin;
	
	private String finalDestination;
	
	private LocalDate depatureDate;
	
	private LocalDate arrivalDate;
	
	private String possessionScanCarrier;
	
	private String possessionScanFlightNumber;
	
	private String possessionScanLocation;
	
	private LocalDate possesionScanDate;
	
	private String bookedScanCarrier;
	
	private String bookedScanFlightNumber;
	
	private String bookedScanLocation;
	
	private LocalDate bookedScanDate;
	
	private String loadScanCarrier;
	
	private String loadScanFlightNumber;
	
	private String loadScanLocation;
	
	private LocalDate loadScanDate;
	
	private String firstCarrierCode;
	
	private String secondFlightNumber;
	
	private String firstScanLocation;
	
	private LocalDate firstScanDate;
	
	private String secondScanCarrier;
	
	private String thirdFlightNumber;
	
	private String secondScanLocation;
	
	private LocalDate secondScanDate;
	
	private String thirdScanCarrier;
	
	private String fourthFlightNumber;
	
	private String thirdScanLocation;
	
	private LocalDate thirdScanDate;
	
	private String fifthScanCarrier;
	
	private String fifthFlightNumber;
	
	private String fifthScanLocation;
	
	private LocalDate fifthScanDate;
	
	private String deliveryScanCarrierCode;
	
	private String deliveryScanLocation;
	
	private LocalDate deliveryScanDate;

	/**
	 * @return Returns the arrivalDate.
	 */
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate The arrivalDate to set.
	 */
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return Returns the bookedScanCarrier.
	 */
	public String getBookedScanCarrier() {
		return bookedScanCarrier;
	}

	/**
	 * @param bookedScanCarrier The bookedScanCarrier to set.
	 */
	public void setBookedScanCarrier(String bookedScanCarrier) {
		this.bookedScanCarrier = bookedScanCarrier;
	}

	/**
	 * @return Returns the bookedScanDate.
	 */
	public LocalDate getBookedScanDate() {
		return bookedScanDate;
	}

	/**
	 * @param bookedScanDate The bookedScanDate to set.
	 */
	public void setBookedScanDate(LocalDate bookedScanDate) {
		this.bookedScanDate = bookedScanDate;
	}

	/**
	 * @return Returns the bookedScanFlightNumber.
	 */
	public String getBookedScanFlightNumber() {
		return bookedScanFlightNumber;
	}

	/**
	 * @param bookedScanFlightNumber The bookedScanFlightNumber to set.
	 */
	public void setBookedScanFlightNumber(String bookedScanFlightNumber) {
		this.bookedScanFlightNumber = bookedScanFlightNumber;
	}

	/**
	 * @return Returns the bookedScanLocation.
	 */
	public String getBookedScanLocation() {
		return bookedScanLocation;
	}

	/**
	 * @param bookedScanLocation The bookedScanLocation to set.
	 */
	public void setBookedScanLocation(String bookedScanLocation) {
		this.bookedScanLocation = bookedScanLocation;
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
	 * @return Returns the deliveryScanCarrierCode.
	 */
	public String getDeliveryScanCarrierCode() {
		return deliveryScanCarrierCode;
	}

	/**
	 * @param deliveryScanCarrierCode The deliveryScanCarrierCode to set.
	 */
	public void setDeliveryScanCarrierCode(String deliveryScanCarrierCode) {
		this.deliveryScanCarrierCode = deliveryScanCarrierCode;
	}

	/**
	 * @return Returns the deliveryScanDate.
	 */
	public LocalDate getDeliveryScanDate() {
		return deliveryScanDate;
	}

	/**
	 * @param deliveryScanDate The deliveryScanDate to set.
	 */
	public void setDeliveryScanDate(LocalDate deliveryScanDate) {
		this.deliveryScanDate = deliveryScanDate;
	}

	/**
	 * @return Returns the deliveryScanLocation.
	 */
	public String getDeliveryScanLocation() {
		return deliveryScanLocation;
	}

	/**
	 * @param deliveryScanLocation The deliveryScanLocation to set.
	 */
	public void setDeliveryScanLocation(String deliveryScanLocation) {
		this.deliveryScanLocation = deliveryScanLocation;
	}

	/**
	 * @return Returns the depatureDate.
	 */
	public LocalDate getDepatureDate() {
		return depatureDate;
	}

	/**
	 * @param depatureDate The depatureDate to set.
	 */
	public void setDepatureDate(LocalDate depatureDate) {
		this.depatureDate = depatureDate;
	}

	/**
	 * @return Returns the fifthFlightNumber.
	 */
	public String getFifthFlightNumber() {
		return fifthFlightNumber;
	}

	/**
	 * @param fifthFlightNumber The fifthFlightNumber to set.
	 */
	public void setFifthFlightNumber(String fifthFlightNumber) {
		this.fifthFlightNumber = fifthFlightNumber;
	}

	/**
	 * @return Returns the fifthScanCarrier.
	 */
	public String getFifthScanCarrier() {
		return fifthScanCarrier;
	}

	/**
	 * @param fifthScanCarrier The fifthScanCarrier to set.
	 */
	public void setFifthScanCarrier(String fifthScanCarrier) {
		this.fifthScanCarrier = fifthScanCarrier;
	}

	/**
	 * @return Returns the fifthScanDate.
	 */
	public LocalDate getFifthScanDate() {
		return fifthScanDate;
	}

	/**
	 * @param fifthScanDate The fifthScanDate to set.
	 */
	public void setFifthScanDate(LocalDate fifthScanDate) {
		this.fifthScanDate = fifthScanDate;
	}

	/**
	 * @return Returns the fifthScanLocation.
	 */
	public String getFifthScanLocation() {
		return fifthScanLocation;
	}

	/**
	 * @param fifthScanLocation The fifthScanLocation to set.
	 */
	public void setFifthScanLocation(String fifthScanLocation) {
		this.fifthScanLocation = fifthScanLocation;
	}

	/**
	 * @return Returns the finalDestination.
	 */
	public String getFinalDestination() {
		return finalDestination;
	}

	/**
	 * @param finalDestination The finalDestination to set.
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	/**
	 * @return Returns the firstCarrierCode.
	 */
	public String getFirstCarrierCode() {
		return firstCarrierCode;
	}

	/**
	 * @param firstCarrierCode The firstCarrierCode to set.
	 */
	public void setFirstCarrierCode(String firstCarrierCode) {
		this.firstCarrierCode = firstCarrierCode;
	}

	/**
	 * @return Returns the firstScanDate.
	 */
	public LocalDate getFirstScanDate() {
		return firstScanDate;
	}

	/**
	 * @param firstScanDate The firstScanDate to set.
	 */
	public void setFirstScanDate(LocalDate firstScanDate) {
		this.firstScanDate = firstScanDate;
	}

	/**
	 * @return Returns the firstScanLocation.
	 */
	public String getFirstScanLocation() {
		return firstScanLocation;
	}

	/**
	 * @param firstScanLocation The firstScanLocation to set.
	 */
	public void setFirstScanLocation(String firstScanLocation) {
		this.firstScanLocation = firstScanLocation;
	}

	/**
	 * @return Returns the fourthFlightNumber.
	 */
	public String getFourthFlightNumber() {
		return fourthFlightNumber;
	}

	/**
	 * @param fourthFlightNumber The fourthFlightNumber to set.
	 */
	public void setFourthFlightNumber(String fourthFlightNumber) {
		this.fourthFlightNumber = fourthFlightNumber;
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
	 * @return Returns the loadScanCarrier.
	 */
	public String getLoadScanCarrier() {
		return loadScanCarrier;
	}

	/**
	 * @param loadScanCarrier The loadScanCarrier to set.
	 */
	public void setLoadScanCarrier(String loadScanCarrier) {
		this.loadScanCarrier = loadScanCarrier;
	}

	/**
	 * @return Returns the loadScanDate.
	 */
	public LocalDate getLoadScanDate() {
		return loadScanDate;
	}

	/**
	 * @param loadScanDate The loadScanDate to set.
	 */
	public void setLoadScanDate(LocalDate loadScanDate) {
		this.loadScanDate = loadScanDate;
	}

	/**
	 * @return Returns the loadScanFlightNumber.
	 */
	public String getLoadScanFlightNumber() {
		return loadScanFlightNumber;
	}

	/**
	 * @param loadScanFlightNumber The loadScanFlightNumber to set.
	 */
	public void setLoadScanFlightNumber(String loadScanFlightNumber) {
		this.loadScanFlightNumber = loadScanFlightNumber;
	}

	/**
	 * @return Returns the loadScanLocation.
	 */
	public String getLoadScanLocation() {
		return loadScanLocation;
	}

	/**
	 * @param loadScanLocation The loadScanLocation to set.
	 */
	public void setLoadScanLocation(String loadScanLocation) {
		this.loadScanLocation = loadScanLocation;
	}

	/**
	 * @return Returns the originalOrigin.
	 */
	public String getOriginalOrigin() {
		return originalOrigin;
	}

	/**
	 * @param originalOrigin The originalOrigin to set.
	 */
	public void setOriginalOrigin(String originalOrigin) {
		this.originalOrigin = originalOrigin;
	}

	/**
	 * @return Returns the possesionScanDate.
	 */
	public LocalDate getPossesionScanDate() {
		return possesionScanDate;
	}

	/**
	 * @param possesionScanDate The possesionScanDate to set.
	 */
	public void setPossesionScanDate(LocalDate possesionScanDate) {
		this.possesionScanDate = possesionScanDate;
	}

	/**
	 * @return Returns the possessionScanCarrier.
	 */
	public String getPossessionScanCarrier() {
		return possessionScanCarrier;
	}

	/**
	 * @param possessionScanCarrier The possessionScanCarrier to set.
	 */
	public void setPossessionScanCarrier(String possessionScanCarrier) {
		this.possessionScanCarrier = possessionScanCarrier;
	}

	/**
	 * @return Returns the possessionScanFlightNumber.
	 */
	public String getPossessionScanFlightNumber() {
		return possessionScanFlightNumber;
	}

	/**
	 * @param possessionScanFlightNumber The possessionScanFlightNumber to set.
	 */
	public void setPossessionScanFlightNumber(String possessionScanFlightNumber) {
		this.possessionScanFlightNumber = possessionScanFlightNumber;
	}

	/**
	 * @return Returns the possessionScanLocation.
	 */
	public String getPossessionScanLocation() {
		return possessionScanLocation;
	}

	/**
	 * @param possessionScanLocation The possessionScanLocation to set.
	 */
	public void setPossessionScanLocation(String possessionScanLocation) {
		this.possessionScanLocation = possessionScanLocation;
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
	 * @return Returns the scannedCarrierCode.
	 */
	public String getScannedCarrierCode() {
		return scannedCarrierCode;
	}

	/**
	 * @param scannedCarrierCode The scannedCarrierCode to set.
	 */
	public void setScannedCarrierCode(String scannedCarrierCode) {
		this.scannedCarrierCode = scannedCarrierCode;
	}

	/**
	 * @return Returns the scannedFlightNumber.
	 */
	public String getScannedFlightNumber() {
		return scannedFlightNumber;
	}

	/**
	 * @param scannedFlightNumber The scannedFlightNumber to set.
	 */
	public void setScannedFlightNumber(String scannedFlightNumber) {
		this.scannedFlightNumber = scannedFlightNumber;
	}

	/**
	 * @return Returns the secondFlightNumber.
	 */
	public String getSecondFlightNumber() {
		return secondFlightNumber;
	}

	/**
	 * @param secondFlightNumber The secondFlightNumber to set.
	 */
	public void setSecondFlightNumber(String secondFlightNumber) {
		this.secondFlightNumber = secondFlightNumber;
	}

	/**
	 * @return Returns the secondScanCarrier.
	 */
	public String getSecondScanCarrier() {
		return secondScanCarrier;
	}

	/**
	 * @param secondScanCarrier The secondScanCarrier to set.
	 */
	public void setSecondScanCarrier(String secondScanCarrier) {
		this.secondScanCarrier = secondScanCarrier;
	}

	/**
	 * @return Returns the secondScanDate.
	 */
	public LocalDate getSecondScanDate() {
		return secondScanDate;
	}

	/**
	 * @param secondScanDate The secondScanDate to set.
	 */
	public void setSecondScanDate(LocalDate secondScanDate) {
		this.secondScanDate = secondScanDate;
	}

	/**
	 * @return Returns the secondScanLocation.
	 */
	public String getSecondScanLocation() {
		return secondScanLocation;
	}

	/**
	 * @param secondScanLocation The secondScanLocation to set.
	 */
	public void setSecondScanLocation(String secondScanLocation) {
		this.secondScanLocation = secondScanLocation;
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
	 * @return Returns the thirdFlightNumber.
	 */
	public String getThirdFlightNumber() {
		return thirdFlightNumber;
	}

	/**
	 * @param thirdFlightNumber The thirdFlightNumber to set.
	 */
	public void setThirdFlightNumber(String thirdFlightNumber) {
		this.thirdFlightNumber = thirdFlightNumber;
	}

	/**
	 * @return Returns the thirdScanCarrier.
	 */
	public String getThirdScanCarrier() {
		return thirdScanCarrier;
	}

	/**
	 * @param thirdScanCarrier The thirdScanCarrier to set.
	 */
	public void setThirdScanCarrier(String thirdScanCarrier) {
		this.thirdScanCarrier = thirdScanCarrier;
	}

	/**
	 * @return Returns the thirdScanDate.
	 */
	public LocalDate getThirdScanDate() {
		return thirdScanDate;
	}

	/**
	 * @param thirdScanDate The thirdScanDate to set.
	 */
	public void setThirdScanDate(LocalDate thirdScanDate) {
		this.thirdScanDate = thirdScanDate;
	}

	/**
	 * @return Returns the thirdScanLocation.
	 */
	public String getThirdScanLocation() {
		return thirdScanLocation;
	}

	/**
	 * @param thirdScanLocation The thirdScanLocation to set.
	 */
	public void setThirdScanLocation(String thirdScanLocation) {
		this.thirdScanLocation = thirdScanLocation;
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