/*
 * CaptureCN66Form.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.currency.Money;

/**
 * @author A-2408
 * 
 */
public class CaptureCN66Form extends ScreenModel {
	private static final String BUNDLE = "capturecn66";

	// private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn66";

	private String clearancePeriod;

	private String airlineCode;

	private String cn66Period;

	private String invoiceRefNo;

	private String carriageFromFilter;

	private String carriageToFilter;

	private String category;

	private String despatchStatusFilter;

	private int airlineIdentifier;

	private String billingType; //BillingMode

	private String[] check;

	private String[] carriageFrom;

	private String[] carriageTo;

	private String[] flightNumber;

	private String[] carrierCode;

	private String[] mailCategoryCode;

	private String[] flightDate;

	private String[] origin;

	private String[] destination;

	private String[] despatchSerialNo;

	/* changed from double to money for CR ID AirNZ164 by indu */
	private String[] totalWeight;

	// private Money[] totalWeightMoney;

	private String[] despatchStatus;

	private String[] despatchDate;

	private String[] mailSubClass;

	private String screenStatus;

	private String fromScreenStatus;

	private String cn51Status;
	
	private String fromScreenFlag;
	/**
	 * 
	 */
	private String netChargeMoneyDisp;

	/* ***************** added for ViewCN51s Screen starts ************* */

	private static final String SCREENID_VIEWCN51 = "mailtracking.mra.defaults.listCN51s";

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	private static final String SCREEN_FLAG_VIEWCN51 = "viewCN51";

	/* ***************** added for ViewCN51s Screen ends ************* */

	private Money[] amountMoney;

	private String[] rate;

	private String[] amount;

	private Money netAmountMoney;

	private String netAmount;

	private String netLCWeight;

	private String netCPWeight;

	private String netSalWeight;

	private String netUldWeight;

	private String netSVWeight;

	private String netEMSWeight;
	// private Money netWeightMoney;
	private String netSummaryWeight;

	private Money netChargeMoney;

	private String netSummaryCharge;

	private String blgCurCode;

	private String rowCounter;
	
	private Money netSummaryAmount;
	//Added By A-3434 for Pagination
	private String displayPage = "1";
	private String lastPageNum= "0";
	
	private String linkStatus;
	
	//Added by A-7929 as part of ICRD-265471
	private String typeBilling;

	public String getTypeBilling() {
		return typeBilling;
	}

	public void setTypeBilling(String typeBilling) {
		this.typeBilling = typeBilling;
	}

	

	/**
	 * @return Returns the netSummaryWeight.
	 */
	public String getNetSummaryWeight() {
		return netSummaryWeight;
	}

	/**
	 * @param netSummaryWeight The netSummaryWeight to set.
	 */
	public void setNetSummaryWeight(String netSummaryWeight) {
		this.netSummaryWeight = netSummaryWeight;
	}

	/**
	 * @return Returns the netCPWeight.
	 */
	public String getNetCPWeight() {
		return netCPWeight;
	}

	/**
	 * @param netCPWeight The netCPWeight to set.
	 */
	public void setNetCPWeight(String netCPWeight) {
		this.netCPWeight = netCPWeight;
	}

	/**
	 * @return Returns the netLCWeight.
	 */
	public String getNetLCWeight() {
		return netLCWeight;
	}

	/**
	 * @param netLCWeight The netLCWeight to set.
	 */
	public void setNetLCWeight(String netLCWeight) {
		this.netLCWeight = netLCWeight;
	}

	/**
	 * @return Returns the netSalWeight.
	 */
	public String getNetSalWeight() {
		return netSalWeight;
	}

	/**
	 * @param netSalWeight The netSalWeight to set.
	 */
	public void setNetSalWeight(String netSalWeight) {
		this.netSalWeight = netSalWeight;
	}

	/**
	 * @return Returns the netSummaryCharge.
	 */
	public String getNetSummaryCharge() {
		return netSummaryCharge;
	}

	/**
	 * @param netSummaryCharge The netSummaryCharge to set.
	 */
	public void setNetSummaryCharge(String netSummaryCharge) {
		this.netSummaryCharge = netSummaryCharge;
	}

	/**
	 * @return Returns the netSVWeight.
	 */
	public String getNetSVWeight() {
		return netSVWeight;
	}

	/**
	 * @param netSVWeight The netSVWeight to set.
	 */
	public void setNetSVWeight(String netSVWeight) {
		this.netSVWeight = netSVWeight;
	}

	/**
	 * @return Returns the netUldWeight.
	 */
	public String getNetUldWeight() {
		return netUldWeight;
	}

	/**
	 * @param netUldWeight The netUldWeight to set.
	 */
	public void setNetUldWeight(String netUldWeight) {
		this.netUldWeight = netUldWeight;
	}

	/**
	 * @return the rowCounter
	 */
	public String getRowCounter() {
		return rowCounter;
	}

	/**
	 * @param rowCounter
	 *            the rowCounter to set
	 */
	public void setRowCounter(String rowCounter) {
		this.rowCounter = rowCounter;
	}
	/**
	 * 	Getter for netEMSWeight 
	 *	Added by : A-4809 on 17-Feb-2014
	 * 	Used for :
	 */
	public String getNetEMSWeight() {
		return netEMSWeight;
	}
	/**
	 *  @param netEMSWeight the netEMSWeight to set
	 * 	Setter for netEMSWeight 
	 *	Added by : A-4809 on 17-Feb-2014
	 * 	Used for :
	 */
	public void setNetEMSWeight(String netEMSWeight) {
		this.netEMSWeight = netEMSWeight;
	}

	/**
	 * @return Returns the blgCurCode.
	 */
	public String getBlgCurCode() {
		return blgCurCode;
	}

	/**
	 * @param blgCurCode
	 *            The blgCurCode to set.
	 */
	public void setBlgCurCode(String blgCurCode) {
		this.blgCurCode = blgCurCode;
	}

	/**
	 * @return screenid.
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return product.
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return subproduct.
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle
	 *            The bundle to set.
	 */
	public void setBundle(String bundle) {
		// this.bundle = bundle;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
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
	 * @return Returns the cn66Date.
	 */
	public String getCn66Period() {
		return cn66Period;
	}

	/**
	 * @param cn66Period
	 *            The cn66Period to set.
	 */
	public void setCn66Period(String cn66Period) {
		this.cn66Period = cn66Period;
	}

	/**
	 * @return Returns the invoiceRefNo.
	 */
	public String getInvoiceRefNo() {
		return invoiceRefNo;
	}

	/**
	 * @param invoiceRefNo
	 *            The invoiceRefNo to set.
	 */
	public void setInvoiceRefNo(String invoiceRefNo) {
		this.invoiceRefNo = invoiceRefNo;
	}

	/**
	 * @return Returns the billingType.
	 */
	public String getBillingType() {
		return billingType;
	}

	/**
	 * @param billingType
	 *            The billingType to set.
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return Returns the carriageFrom.
	 */
	public String[] getCarriageFrom() {
		return carriageFrom;
	}

	/**
	 * @param carriageFrom
	 *            The carriageFrom to set.
	 */
	public void setCarriageFrom(String[] carriageFrom) {
		this.carriageFrom = carriageFrom;
	}

	/**
	 * @return Returns the carriageFromFilter.
	 */
	public String getCarriageFromFilter() {
		return carriageFromFilter;
	}

	/**
	 * @param carriageFromFilter
	 *            The carriageFromFilter to set.
	 */
	public void setCarriageFromFilter(String carriageFromFilter) {
		this.carriageFromFilter = carriageFromFilter;
	}

	/**
	 * @return Returns the carriageTo.
	 */
	public String[] getCarriageTo() {
		return carriageTo;
	}

	/**
	 * @param carriageTo
	 *            The carriageTo to set.
	 */
	public void setCarriageTo(String[] carriageTo) {
		this.carriageTo = carriageTo;
	}

	/**
	 * @return Returns the carriageToFilter.
	 */
	public String getCarriageToFilter() {
		return carriageToFilter;
	}

	/**
	 * @param carriageToFilter
	 *            The carriageToFilter to set.
	 */
	public void setCarriageToFilter(String carriageToFilter) {
		this.carriageToFilter = carriageToFilter;
	}

	/**
	 * @return Returns the despatchSerialNo.
	 */
	public String[] getDespatchSerialNo() {
		return despatchSerialNo;
	}

	/**
	 * @param despatchSerialNo
	 *            The despatchSerialNo to set.
	 */
	public void setDespatchSerialNo(String[] despatchSerialNo) {
		this.despatchSerialNo = despatchSerialNo;
	}

	/**
	 * @return Returns the despatchStatus.
	 */
	public String[] getDespatchStatus() {
		return despatchStatus;
	}

	/**
	 * @param despatchStatus
	 *            The despatchStatus to set.
	 */
	public void setDespatchStatus(String[] despatchStatus) {
		this.despatchStatus = despatchStatus;
	}

	/**
	 * @return Returns the despatchStatusFilter.
	 */
	public String getDespatchStatusFilter() {
		return despatchStatusFilter;
	}

	/**
	 * @param despatchStatusFilter
	 *            The despatchStatusFilter to set.
	 */
	public void setDespatchStatusFilter(String despatchStatusFilter) {
		this.despatchStatusFilter = despatchStatusFilter;
	}

	/**
	 * @return Returns the destination.
	 */
	public String[] getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            The destination to set.
	 */
	public void setDestination(String[] destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String[] getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String[] mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the origin.
	 */
	public String[] getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            The origin to set.
	 */
	public void setOrigin(String[] origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the totalWeight.
	 */
	public String[] getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight
	 *            The totalWeight to set.
	 */
	public void setTotalWeight(String[] totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return Returns the check.
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * @param check
	 *            The check to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * @return Returns the screenStatus.
	 */
	public String getScreenStatus() {
		return screenStatus;
	}

	/**
	 * @param screenStatus
	 *            The screenStatus to set.
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String[] getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the despatchDate.
	 */
	public String[] getDespatchDate() {
		return despatchDate;
	}

	/**
	 * @param despatchDate
	 *            The despatchDate to set.
	 */
	public void setDespatchDate(String[] despatchDate) {
		this.despatchDate = despatchDate;
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
	 * @return Returns the mailSubClass.
	 */
	public String[] getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass
	 *            The mailSubClass to set.
	 */
	public void setMailSubClass(String[] mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String[] getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String[] carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String[] getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(String[] flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the fromScreenStatus.
	 */
	public String getFromScreenStatus() {
		return fromScreenStatus;
	}

	/**
	 * @param fromScreenStatus
	 *            The fromScreenStatus to set.
	 */
	public void setFromScreenStatus(String fromScreenStatus) {
		this.fromScreenStatus = fromScreenStatus;
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

	/**
	 * @return Returns the amount.
	 */
	public String[] getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            The amount to set.
	 */
	public void setAmount(String[] amount) {
		this.amount = amount;
	}

	/**
	 * @return Returns the amountMoney.
	 */
	public Money[] getAmountMoney() {
		return amountMoney;
	}

	/**
	 * @param amountMoney
	 *            The amountMoney to set.
	 */
	public void setAmountMoney(Money[] amountMoney) {
		this.amountMoney = amountMoney;
	}

	/**
	 * @return Returns the netAmount.
	 */
	public String getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount
	 *            The netAmount to set.
	 */
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	/**
	 * @return Returns the netAmountMoney.
	 */
	public Money getNetAmountMoney() {
		return netAmountMoney;
	}

	/**
	 * @param netAmountMoney
	 *            The netAmountMoney to set.
	 */
	public void setNetAmountMoney(Money netAmountMoney) {
		this.netAmountMoney = netAmountMoney;
	}

	/**
	 * @return Returns the rate.
	 */
	public String[] getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            The rate to set.
	 */
	public void setRate(String[] rate) {
		this.rate = rate;
	}

	

	/**
	 * @return Returns the netChargeMoney.
	 */
	public Money getNetChargeMoney() {
		return netChargeMoney;
	}

	/**
	 * @param netChargeMoney
	 *            The netChargeMoney to set.
	 */
	public void setNetChargeMoney(Money netChargeMoney) {
		this.netChargeMoney = netChargeMoney;
	}

	public Money getNetSummaryAmount() {
		return netSummaryAmount;
	}

	public void setNetSummaryAmount(Money netSummaryAmount) {
		this.netSummaryAmount = netSummaryAmount;
	}

	/**
	 * @return the netChargeMoneyDisp
	 */
	public String getNetChargeMoneyDisp() {
		return netChargeMoneyDisp;
	}

	/**
	 * @param netChargeMoneyDisp the netChargeMoneyDisp to set
	 */
	public void setNetChargeMoneyDisp(String netChargeMoneyDisp) {
		this.netChargeMoneyDisp = netChargeMoneyDisp;
	}

	/**
	 * @return the linkStatus
	 */
	public String getLinkStatus() {
		return linkStatus;
	}

	/**
	 * @param linkStatus the linkStatus to set
	 */
	public void setLinkStatus(String linkStatus) {
		this.linkStatus = linkStatus;
	}
	
	/**
	 * @return the fromScreenFlag
	 */
	public String getFromScreenFlag() {
		return fromScreenFlag;
	}

	/**
	 * @param fromScreenFlag the fromScreenFlag to set
	 */
	public void setFromScreenFlag(String fromScreenFlag) {
		this.fromScreenFlag = fromScreenFlag;
	}

	
	
}
