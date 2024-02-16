/*
 * MailInvoicClaimsEnquiryVO.java created on Aug 02, 2007
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
 * @author A-2270
 *
 */
public class MailInvoicClaimsEnquiryVO extends AbstractVO {

	/**
	 * Claim status - Success
	 */
	public static final String SUCCESS = "S";

	private String companyCode;

	private String poaCode;

	private String receptacleIdentifier;

	private String sectorIdentifier;

	private String sector;

	private String sectorOrigin;

	private String sectorDestination;

	private LocalDate flightDate;

	private String flightDetails;

	private int flightNumber;

	private String carrierCode;

	private String calimType;

	private LocalDate claimDate;

	private String claimStatus;

	private LocalDate transferDate;

	private double claimAmount;

	private String claimCode;

	private String claimKey;

	private String invoiceKey;

	private LocalDate scanDate;
	
	private String sequenceNumber;

	private String lastUpdatedUser;

	private LocalDate lastUpdatedTime;

	private double actualValue;

	private double invoicValue;

	/*
	 * ¿WXXX¿=Weight amount claim, where ¿xxx¿ equals weight claimed
	 */
	public static final String WXXX = "WXX";

	/*
	 * ¿NPR¿ = No Pay Record claim
	 */
	public static final String NPR = "NPR";

	/*
	 * ¿LHR¿ = Line Haul rate claim
	 */
	public static final String LHR = "LHR";
	/*
	 * ¿THR¿ = Line Haul rate claim
	 */
	public static final String THR = "THR";


	/**
	 * @return the actualValue
	 */
	public double getActualValue() {
		return actualValue;
	}

	/**
	 * @param actualValue the actualValue to set
	 */
	public void setActualValue(double actualValue) {
		this.actualValue = actualValue;
	}

	/**
	 * @return the invoicValue
	 */
	public double getInvoicValue() {
		return invoicValue;
	}

	/**
	 * @param invoicValue the invoicValue to set
	 */
	public void setInvoicValue(double invoicValue) {
		this.invoicValue = invoicValue;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
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

	/**
	 * @return the sequenceNumber
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
	 * @return the claimKey
	 */
	public String getClaimKey() {
		return claimKey;
	}

	/**
	 * @param claimKey the claimKey to set
	 */
	public void setClaimKey(String claimKey) {
		this.claimKey = claimKey;
	}

	/**
	 * @return the claimCode
	 */
	public String getClaimCode() {
		return claimCode;
	}

	/**
	 * @param claimCode the claimCode to set
	 */
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}

	/**
	 * @return the claimAmount
	 */
	public double getClaimAmount() {
		return claimAmount;
	}

	/**
	 * @param claimAmount the claimAmount to set
	 */
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	/**
	 * @return the calimType
	 */
	public String getCalimType() {
		return calimType;
	}

	/**
	 * @param calimType the calimType to set
	 */
	public void setCalimType(String calimType) {
		this.calimType = calimType;
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
	 * @return the claimDate
	 */
	public LocalDate getClaimDate() {
		return claimDate;
	}

	/**
	 * @param claimDate the claimDate to set
	 */
	public void setClaimDate(LocalDate claimDate) {
		this.claimDate = claimDate;
	}

	/**
	 * @return the claimStatus
	 */
	public String getClaimStatus() {
		return claimStatus;
	}

	/**
	 * @param claimStatus the claimStatus to set
	 */
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
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
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
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
	 * @return the transferDate
	 */
	public LocalDate getTransferDate() {
		return transferDate;
	}

	/**
	 * @param transferDate the transferDate to set
	 */
	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}

	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * @return the flightNumber
	 */
	public int getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the flightDetails
	 */
	public String getFlightDetails() {
		return flightDetails;
	}

	/**
	 * @param flightDetails the flightDetails to set
	 */
	public void setFlightDetails(String flightDetails) {
		this.flightDetails = flightDetails;
	}

	/**
	 * @return the sectorIdentifier
	 */
	public String getSectorIdentifier() {
		return sectorIdentifier;
	}

	/**
	 * @param sectorIdentifier the sectorIdentifier to set
	 */
	public void setSectorIdentifier(String sectorIdentifier) {
		this.sectorIdentifier = sectorIdentifier;
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

}