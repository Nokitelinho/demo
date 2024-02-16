/*
 * AirlineCN66DetailsVO.java Created on Jun 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

//import java.util.Calendar;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 *
 * @author A-2407
 *
 */
public class AirlineCN66DetailsVO extends AbstractVO {

	private String companyCode;

	private int airlineIdentifier;

	private String invoiceNumber;

	private String interlineBillingType;

	private int sequenceNumber;

	private String clearancePeriod;

	private String carriageFrom;

	private String carriageTo;

	private String carrierCode;

	private String flightNumber;

	private LocalDate flightDate;

	private String origin;

	private String destination;

	private String originExchangeOffice;

	private String destinationExchangeOffice;

	private String mailCategoryCode;

	private String mailSubClass;

	private String despatchSerialNo;

	private String receptacleSerialNo;

	private int year;

	private String despatchStatus;
	private String rsn;
	
	
	//Added as part of ICRD-265471
	private String dsnIdr;
	private long malSeqNum;
	public String getDsnIdr() {
		return dsnIdr;
	}

	public void setDsnIdr(String dsnIdr) {
		this.dsnIdr = dsnIdr;
	}

	/*public String getMalSeqNum() {
		return malSeqNum;
	}

	public void setMalSeqNum(String malSeqNum) {
		this.malSeqNum = malSeqNum;
	}*/

	
	
	public String getRsn() {
		return rsn;
	}

	public long getMalSeqNum() {
		return malSeqNum;
	}

	public void setMalSeqNum(long malSeqNum) {
		this.malSeqNum = malSeqNum;
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

	private int totalPieces;

/*changed from double to money for CR ID AirNZ164 by indu*/
	private double totalWeight;

	private LocalDate despatchDate;

	private String operationFlag;

	private int flightCarrierIdentifier;

	private String cn51Status;

	private double weightLC;// added for reports by a-2458

	private double weightCP;// added for reports by a-2458
	private double weightULD;// added for reports by a-3447
	private double weightSV;// added for reports by a-3447
	private double weightEMS;//Added for EMS
	private double bldamt;// added for reports by a-3447
	private String carCode;
	private String flightCarrierCode;

	private LocalDate lastUpdatedTime;

	private String lastUpdatedUser;

	/*Added two fiels  for CR ID AirNZ164 by indu*/
	private double rate;
	private Money amount;
	private String curCod;
	private String despachDate;
	
	private String csgdocnum;
	
	private String poaCode;
	
	private int csgseqnum;
	
	private String billingBasis;
	
	private Money summaryAmount;
	
	private String listingCurrencyCode;
	
	private double totalSummaryWeight;
	/***
	 * Added by @author a-3447 for List acc entries 
	 * Integration
	 */
	
	public static final String FUNCTIONPOINT_INWARDBILLING="MI";
	
	public static final String FUNCTIONPOINT_OUTWARDBILLING="MO";
	
	public static final String MRA_SUBSYSTEM="M";

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
	 * @return Returns the csgdocnum.
	 */
	public String getCsgdocnum() {
		return csgdocnum;
	}

	/**
	 * @param csgdocnum The csgdocnum to set.
	 */
	public void setCsgdocnum(String csgdocnum) {
		this.csgdocnum = csgdocnum;
	}

	/**
	 * @return Returns the csgseqnum.
	 */
	public int getCsgseqnum() {
		return csgseqnum;
	}

	/**
	 * @param csgseqnum The csgseqnum to set.
	 */
	public void setCsgseqnum(int csgseqnum) {
		this.csgseqnum = csgseqnum;
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
	 * @return Returns the curCod.
	 */
	public String getCurCod() {
		return curCod;
	}

	/**
	 * @param curCod The curCod to set.
	 */
	public void setCurCod(String curCod) {
		this.curCod = curCod;
	}
	/**
	 * @return the weightEMS
	 */
	public double getWeightEMS() {
		return weightEMS;
	}
	/**
	 * @param weightEMS the weightEMS to set
	 */
	public void setWeightEMS(double weightEMS) {
		this.weightEMS = weightEMS;
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
	 * @return Returns the rate.
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate The rate to set.
	 */
	public void setRate(double rate) {
		this.rate = rate;
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
	 * @return the lsatUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lsatUpdatedTime the lsatUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the carriageFrom.
	 */
	public String getCarriageFrom() {
		return carriageFrom;
	}

	/**
	 * @param carriageFrom
	 *            The carriageFrom to set.
	 */
	public void setCarriageFrom(String carriageFrom) {
		this.carriageFrom = carriageFrom;
	}

	/**
	 * @return Returns the carriageTo.
	 */
	public String getCarriageTo() {
		return carriageTo;
	}

	/**
	 * @param carriageTo
	 *            The carriageTo to set.
	 */
	public void setCarriageTo(String carriageTo) {
		this.carriageTo = carriageTo;
	}

	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod
	 *            The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the despatchDate.
	 */
	public LocalDate getDespatchDate() {
		return despatchDate;
	}

	/**
	 * @param despatchDate
	 *            The despatchDate to set.
	 */
	public void setDespatchDate(LocalDate despatchDate) {
		this.despatchDate = despatchDate;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the despatchStatus.
	 */
	public String getDespatchStatus() {
		return despatchStatus;
	}

	/**
	 * @param despatchStatus
	 *            The despatchStatus to set.
	 */
	public void setDespatchStatus(String despatchStatus) {
		this.despatchStatus = despatchStatus;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubClass.
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass
	 *            The mailSubClass to set.
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the totalPieces.
	 */
	public int getTotalPieces() {
		return totalPieces;
	}

	/**
	 * @param totalPieces
	 *            The totalPieces to set.
	 */
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}

	/**
	 * @return Returns the totalWeight.
	 */
	public double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight
	 *            The totalWeight to set.
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return Returns the destinationExchangeOffice.
	 */
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}

	/**
	 * @param destinationExchangeOffice
	 *            The destinationExchangeOffice to set.
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}

	/**
	 * @return Returns the originExchangeOffice.
	 */
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}

	/**
	 * @param originExchangeOffice
	 *            The originExchangeOffice to set.
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	/**
	 * @return Returns the interlineBillingType.
	 */
	public String getInterlineBillingType() {
		return interlineBillingType;
	}

	/**
	 * @param interlineBillingType
	 *            The interlineBillingType to set.
	 */
	public void setInterlineBillingType(String interlineBillingType) {
		this.interlineBillingType = interlineBillingType;
	}

	/**
	 * @return Returns the despatchSerialNo.
	 */
	public String getDespatchSerialNo() {
		return despatchSerialNo;
	}

	/**
	 * @param despatchSerialNo
	 *            The despatchSerialNo to set.
	 */
	public void setDespatchSerialNo(String despatchSerialNo) {
		this.despatchSerialNo = despatchSerialNo;
	}

	/**
	 * @return Returns the receptacleSerialNo.
	 */
	public String getReceptacleSerialNo() {
		return receptacleSerialNo;
	}

	/**
	 * @param receptacleSerialNo
	 *            The receptacleSerialNo to set.
	 */
	public void setReceptacleSerialNo(String receptacleSerialNo) {
		this.receptacleSerialNo = receptacleSerialNo;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}

	/**
	 * @param flightCarrierIdentifier
	 *            The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}

	/**
	 * @return Returns the cn51Status.
	 */
	public String getCn51Status() {
		return cn51Status;
	}

	/**
	 * @param cn51Status
	 *            The cn51Status to set.
	 */
	public void setCn51Status(String cn51Status) {
		this.cn51Status = cn51Status;
	}

	public void setWeightCP(double weightCP) {
		this.weightCP = weightCP;
	}

	/**
	 * @param weightCP
	 *            The weightCP to set.
	 */
	public double getWeightCP() {
		return weightCP;
	}

	/**
	 * @return Returns the weightCP.
	 */
	public double getWeightLC() {
		return weightLC;
	}

	/**
	 * @return Returns the weightLC.
	 */

	public void setWeightLC(double weightLC) {
		this.weightLC = weightLC;
	}

	/**
	 * @param weightLC
	 *            The weightLC to set.
	 */

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode
	 *            The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return the despachDate
	 */
	public String getDespachDate() {
		return despachDate;
	}

	/**
	 * @param despachDate the despachDate to set
	 */
	public void setDespachDate(String despachDate) {
		this.despachDate = despachDate;
	}

	/**
	 * @return the weightSV
	 */
	public double getWeightSV() {
		return weightSV;
	}

	/**
	 * @param weightSV the weightSV to set
	 */
	public void setWeightSV(double weightSV) {
		this.weightSV = weightSV;
	}

	/**
	 * @return the weightULD
	 */
	public double getWeightULD() {
		return weightULD;
	}

	/**
	 * @param weightULD the weightULD to set
	 */
	public void setWeightULD(double weightULD) {
		this.weightULD = weightULD;
	}

	/**
	 * @return the bldamt
	 */
	public double getBldamt() {
		return bldamt;
	}

	/**
	 * @param bldamt the bldamt to set
	 */
	public void setBldamt(double bldamt) {
		this.bldamt = bldamt;
	}

	/**
	 * @return the carCode
	 */
	public String getCarCode() {
		return carCode;
	}

	/**
	 * @param carCode the carCode to set
	 */
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public Money getSummaryAmount() {
		return summaryAmount;
	}

	public void setSummaryAmount(Money summaryAmount) {
		this.summaryAmount = summaryAmount;
	}

	public String getListingCurrencyCode() {
		return listingCurrencyCode;
	}

	public void setListingCurrencyCode(String listingCurrencyCode) {
		this.listingCurrencyCode = listingCurrencyCode;
	}

	public double getTotalSummaryWeight() {
		return totalSummaryWeight;
	}

	public void setTotalSummaryWeight(double totalSummaryWeight) {
		this.totalSummaryWeight = totalSummaryWeight;
	}

}
