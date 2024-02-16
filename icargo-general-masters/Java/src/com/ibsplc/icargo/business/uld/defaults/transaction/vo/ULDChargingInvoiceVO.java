/*
 * ULDChargingInvoiceVO.java Created on Jan 10, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction.vo;



import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDChargingInvoiceVO   extends AbstractVO implements Serializable{

	/**
	 *
	 */
	public static final String MODULE ="uld";
	/**
	 *
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 *
	 */
	public static final String ENTITY ="uld.defaults.transaction.ULDChargingInvoice";
	/**
	 *
	 */
	public static final String REPAIR_TXN_TYPE = "P";

    private String companyCode;
    private String invoiceRefNumber;
    private String transactionType;
    private LocalDate invoicedDate;
    private String invoicedToCode;
    private String invoicedToCodeName;
    private String invoiceRemark;
    private String paymentStatus;
    private String currencyCode;
    private LocalDate lastUpdatedTime;
    private String lastUpdatedUser;
    //Use the following field for List Transaction Invoice
    private double demurrageAmount;
    // Flag to identifying whether Repair or Transaction Invoice
    private boolean isRepairInvoice;
    //Added by Deepthi on 16Apr08 for AirNZ417
    /*
     *
     */
    private String partyType;

    /*
     * This is the total demurrage amount
     */
    private double  totalAmount;
    /*
     * This is the amounr after deducting waiverAmount
     */
    private double netAmount;
    private double waiverAmount;
    //Deepthi ends

	private String uldNumber;
	private String location;
	private String interfacedFlag;
	private String uldType;
	private LocalDate date1;
	private LocalDate date2;
	private String controlRecieptNumber;
	/**
	 * @return Returns the date2.
	 */
	public LocalDate getDate2() {
		return date2;
	}
	/**
	 * @param date2
	 */
	public void setDate2(LocalDate date2) {
		this.date2  = date2;
	}

	/**
	 * @return Returns the date1.
	 */
	public LocalDate getDate1() {
		return date1;
	}
	/**
	 * @param date1
	 */
	public void setDate1(LocalDate date1) {
		this.date1  = date1;
	}

	/**
	 * @return Returns the controlRecieptNumber.
	 */
	public String getControlRecieptNumber() {
		return controlRecieptNumber;
	}
	/**
	 * @param controlRecieptNumber
	 */
	public void setControlRecieptNumber(String controlRecieptNumber) {
		this.controlRecieptNumber  = controlRecieptNumber;
	}

	/**
	 * @return Returns the uldType.
	 */
	public String getUldType() {
		return uldType;
	}
	/**
	 * @param uldType
	 */
	public void setUldType(String uldType) {
		this.uldType  = uldType;
	}
	/**
	 * @return Returns the interfacedFlag.
	 */
	public String getInterfacedFlag() {
		return interfacedFlag;
	}
	/**
	 * @param interfacedFlag
	 */
	public void setInterfacedFlag(String interfacedFlag) {
		this.interfacedFlag  = interfacedFlag;
	}


	/**
	 * @return Returns the UldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
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
	 * @return Returns the currencyCode.
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * @return Returns the demurrageAmount.
	 */
	public double getDemurrageAmount() {
		return demurrageAmount;
	}
	/**
	 * @param demurrageAmount The demurrageAmount to set.
	 */
	public void setDemurrageAmount(double demurrageAmount) {
		this.demurrageAmount = demurrageAmount;
	}
	/**
	 * @return Returns the invoicedDate.
	 */
	public LocalDate getInvoicedDate() {
		return invoicedDate;
	}
	/**
	 * @param invoicedDate The invoicedDate to set.
	 */
	public void setInvoicedDate(LocalDate invoicedDate) {
		this.invoicedDate = invoicedDate;
	}
	/**
	 * @return Returns the invoicedToCode.
	 */
	public String getInvoicedToCode() {
		return invoicedToCode;
	}
	/**
	 * @param invoicedToCode The invoicedToCode to set.
	 */
	public void setInvoicedToCode(String invoicedToCode) {
		this.invoicedToCode = invoicedToCode;
	}
	/**
	 * @return Returns the invoicedToCodeName.
	 */
	public String getInvoicedToCodeName() {
		return invoicedToCodeName;
	}
	/**
	 * @param invoicedToCodeName The invoicedToCodeName to set.
	 */
	public void setInvoicedToCodeName(String invoicedToCodeName) {
		this.invoicedToCodeName = invoicedToCodeName;
	}
	/**
	 * @return Returns the invoiceRefNumber.
	 */
	public String getInvoiceRefNumber() {
		return invoiceRefNumber;
	}
	/**
	 * @param invoiceRefNumber The invoiceRefNumber to set.
	 */
	public void setInvoiceRefNumber(String invoiceRefNumber) {
		this.invoiceRefNumber = invoiceRefNumber;
	}
	/**
	 * @return Returns the invoiceRemark.
	 */
	public String getInvoiceRemark() {
		return invoiceRemark;
	}
	/**
	 * @param invoiceRemark The invoiceRemark to set.
	 */
	public void setInvoiceRemark(String invoiceRemark) {
		this.invoiceRemark = invoiceRemark;
	}
	/**
	 * @return Returns the paymentStatus.
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}
	/**
	 * @param paymentStatus The paymentStatus to set.
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	/**
	 * @return Returns the transactionType.
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
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
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return Returns the isRepairInvoice.
	 */
	public boolean isRepairInvoice() {
		return isRepairInvoice;
	}
	/**
	 * @param isRepairInvoice The isRepairInvoice to set.
	 */
	public void setRepairInvoice(boolean isRepairInvoice) {
		this.isRepairInvoice = isRepairInvoice;
	}
	/**
	 *
	 * @return
	 */
	public double getNetAmount() {
		return netAmount;
	}
	/**
	 *
	 * @param netAmount
	 */
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 *
	 * @return
	 */
	public double getTotalAmount() {
		return totalAmount;
	}
	/**
	 *
	 * @param totalAmount
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 *
	 * @return
	 */
	public double getWaiverAmount() {
		return waiverAmount;
	}
	/**
	 *
	 * @param waiverAmount
	 */
	public void setWaiverAmount(double waiverAmount) {
		this.waiverAmount = waiverAmount;
	}
	/**
	 * @return the partyType
	 */
	public String getPartyType() {
		return partyType;
	}
	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
}
