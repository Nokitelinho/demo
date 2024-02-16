/*
 * AirlineBillingDetailVO.java Created on Feb 15,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;




import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;

/**
 * @author A-1946
 *
 */
public class AirlineBillingDetailVO extends AbstractVO {
	
	private String companycode;	
	
	private int sequenceNumber;
	
	private String billingBasis;
	
	private String interLineBillingType;
	
	private LocalDate invoiceDate;
	
	private String basisType;
	
	private String originOfficeOfExchange;
	
	
	private String destinationOfficeOfExchange;
	
	private String mailCategory;
	
	private String mailSubclass;
	
	private String receptacleSerialNumber;
	
	private String despatchSerialNumber;
	
	private String highestReceptacleNumber;
	
	private int year;
	
	private boolean registeredInsuredInd;
	
	private Money  billableAmount;
	
	private String billingCurrenyCode;
	
	private LocalDate receivedDate;
	
	private int receivedPieces;
	
	private double  receivedWeight;
	
	private double  applicablerate;
	
	private String billingStatus;
	
	private String invoicenumber;
	
	private String  consignmentDocumentNumber;
	
	private int consignmentSequenceNumber;
	
	private int flightCarrierIdentifier;

	private String flightNumber;

	private int flightSequencenumber;

	private int segmentSerialNumber;

	private String uldNumber;

	private String containerNumber;

	private String poaCode;

	private String billingRemarks;
	
	private String clearancePeriod;
	
	private String carrierCode;
	

	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the invoiceDate.
	 */
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate The invoiceDate to set.
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return Returns the interLineBillingType.
	 */
	public String getInterLineBillingType() {
		return interLineBillingType;
	}

	/**
	 * @param interLineBillingType The interLineBillingType to set.
	 */
	public void setInterLineBillingType(String interLineBillingType) {
		this.interLineBillingType = interLineBillingType;
	}

	/**
	 * @return Returns the applicablerate.
	 */
	public double getApplicablerate() {
		return applicablerate;
	}

	/**
	 * @param applicablerate The applicablerate to set.
	 */
	public void setApplicablerate(double applicablerate) {
		this.applicablerate = applicablerate;
	}

	/**
	 * @return Returns the basisType.
	 */
	public String getBasisType() {
		return basisType;
	}

	/**
	 * @param basisType The basisType to set.
	 */
	public void setBasisType(String basisType) {
		this.basisType = basisType;
	}

	

	/**
	 * @return the billableAmount
	 */
	public Money getBillableAmount() {
		return billableAmount;
	}

	/**
	 * @param billableAmount the billableAmount to set
	 */
	public void setBillableAmount(Money billableAmount) {
		this.billableAmount = billableAmount;
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
	 * @return Returns the billingCurrenyCode.
	 */
	public String getBillingCurrenyCode() {
		return billingCurrenyCode;
	}

	/**
	 * @param billingCurrenyCode The billingCurrenyCode to set.
	 */
	public void setBillingCurrenyCode(String billingCurrenyCode) {
		this.billingCurrenyCode = billingCurrenyCode;
	}

	/**
	 * @return Returns the billingRemarks.
	 */
	public String getBillingRemarks() {
		return billingRemarks;
	}

	/**
	 * @param billingRemarks The billingRemarks to set.
	 */
	public void setBillingRemarks(String billingRemarks) {
		this.billingRemarks = billingRemarks;
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
	 * @return Returns the companycode.
	 */
	public String getCompanycode() {
		return companycode;
	}

	/**
	 * @param companycode The companycode to set.
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	/**
	 * @return Returns the consignmentDocumentNumber.
	 */
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}

	/**
	 * @param consignmentDocumentNumber The consignmentDocumentNumber to set.
	 */
	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}

	/**
	 * @return Returns the consignmentSequenceNumber.
	 */
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}

	/**
	 * @param consignmentSequenceNumber The consignmentSequenceNumber to set.
	 */
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
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
	 * @return Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}

	/**
	 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightSequencenumber.
	 */
	public int getFlightSequencenumber() {
		return flightSequencenumber;
	}

	/**
	 * @param flightSequencenumber The flightSequencenumber to set.
	 */
	public void setFlightSequencenumber(int flightSequencenumber) {
		this.flightSequencenumber = flightSequencenumber;
	}

	/**
	 * @return Returns the highestReceptacleNumber.
	 */
	public String getHighestReceptacleNumber() {
		return highestReceptacleNumber;
	}

	/**
	 * @param highestReceptacleNumber The highestReceptacleNumber to set.
	 */
	public void setHighestReceptacleNumber(String highestReceptacleNumber) {
		this.highestReceptacleNumber = highestReceptacleNumber;
	}

	/**
	 * @return Returns the invoicenumber.
	 */
	public String getInvoicenumber() {
		return invoicenumber;
	}

	/**
	 * @param invoicenumber The invoicenumber to set.
	 */
	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
	}

	/**
	 * @return Returns the mailCategory.
	 */
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
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
	 * @return Returns the receivedDate.
	 */
	public LocalDate getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate The receivedDate to set.
	 */
	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return Returns the receivedPieces.
	 */
	public int getReceivedPieces() {
		return receivedPieces;
	}

	/**
	 * @param receivedPieces The receivedPieces to set.
	 */
	public void setReceivedPieces(int receivedPieces) {
		this.receivedPieces = receivedPieces;
	}

	/**
	 * @return Returns the receivedWeight.
	 */
	public double getReceivedWeight() {
		return receivedWeight;
	}

	/**
	 * @param receivedWeight The receivedWeight to set.
	 */
	public void setReceivedWeight(double receivedWeight) {
		this.receivedWeight = receivedWeight;
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
	 * @return Returns the registeredInsuredInd.
	 */
	public boolean isRegisteredInsuredInd() {
		return registeredInsuredInd;
	}

	/**
	 * @param registeredInsuredInd The registeredInsuredInd to set.
	 */
	public void setRegisteredInsuredInd(boolean registeredInsuredInd) {
		this.registeredInsuredInd = registeredInsuredInd;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	
}