/*
 * CaptureMailInvoiceForm.java Created on June 11, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3434
 * Form for CaptureInvoice screen.
 * 
 * Revision History
 * 
 * Version Date Author
 * 
 * 0.1 June 11 A-3434
 */

public class CaptureMailInvoiceForm extends ScreenModel {
	private static final String BUNDLE = "captureInvoicebundle";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	private String clearancePeriod;

	private String airlineCode;

	private String airlineNo;

	private String invoiceRefNo;

	private String invoiceDate;

	private String listingCurrency;

	private String netAmount;

	private String exchangeRate;

	private String amountInusd;

	private String invoiceReceiptDate;

	private String totalWeight;

	private String buttonFlag;

	private String invoiceStatus;

	private String invoiceFormOneStatus;

	private String invSatusCheckFlag;

	private String invForm1SatusCheckFlag;

	private String invokingScreen;

	private String selectedRow;

	private String ichFlag;

	private String noFormOneCaptured;
	
	private String fromScreenFlg;

	/**
	 * 
	 */
	private String exgRate;

	/**
	 * @return the exgRate
	 */
	public String getExgRate() {
		return exgRate;
	}

	/**
	 * @param exgRate
	 *            the exgRate to set
	 */
	public void setExgRate(String exgRate) {
		this.exgRate = exgRate;
	}

	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow
	 *            the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return the invokingScreen
	 */
	public String getInvokingScreen() {
		return invokingScreen;
	}

	/**
	 * @param invokingScreen
	 *            the invokingScreen to set
	 */
	public void setInvokingScreen(String invokingScreen) {
		this.invokingScreen = invokingScreen;
	}

	/**
	 * @return the invForm1SatusCheckFlag
	 */
	public String getInvForm1SatusCheckFlag() {
		return invForm1SatusCheckFlag;
	}

	/**
	 * @param invForm1SatusCheckFlag
	 *            the invForm1SatusCheckFlag to set
	 */
	public void setInvForm1SatusCheckFlag(String invForm1SatusCheckFlag) {
		this.invForm1SatusCheckFlag = invForm1SatusCheckFlag;
	}

	/**
	 * @return the invSatusCheckFlag
	 */
	public String getInvSatusCheckFlag() {
		return invSatusCheckFlag;
	}

	/**
	 * @param invSatusCheckFlag
	 *            the invSatusCheckFlag to set
	 */
	public void setInvSatusCheckFlag(String invSatusCheckFlag) {
		this.invSatusCheckFlag = invSatusCheckFlag;
	}

	/**
	 * @return the buttonFlag
	 */
	public String getButtonFlag() {
		return buttonFlag;
	}

	/**
	 * @param buttonFlag
	 *            the buttonFlag to set
	 */
	public void setButtonFlag(String buttonFlag) {
		this.buttonFlag = buttonFlag;
	}

	/**
	 * @return Returns the airlineNo.
	 */
	public String getAirlineNo() {
		return airlineNo;
	}

	/**
	 * @param airlineNo
	 *            The airlineNo to set.
	 */
	public void setAirlineNo(String airlineNo) {
		this.airlineNo = airlineNo;
	}

	/**
	 * @return the amountInusd
	 */
	public String getAmountInusd() {
		return amountInusd;
	}

	/**
	 * @param amountInusd
	 *            the amountInusd to set
	 */
	public void setAmountInusd(String amountInusd) {
		this.amountInusd = amountInusd;
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
	 * @return Returns the exchangeRate.
	 */
	public String getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param exchangeRate
	 *            The exchangeRate to set.
	 */
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * @return Returns the invoiceDate.
	 */
	public String getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate
	 *            The invoiceDate to set.
	 */
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return Returns the invoiceReceiptDate.
	 */
	public String getInvoiceReceiptDate() {
		return invoiceReceiptDate;
	}

	/**
	 * @param invoiceReceiptDate
	 *            The invoiceReceiptDate to set.
	 */
	public void setInvoiceReceiptDate(String invoiceReceiptDate) {
		this.invoiceReceiptDate = invoiceReceiptDate;
	}

	/**
	 * @return Returns the listingCurrency.
	 */
	public String getListingCurrency() {
		return listingCurrency;
	}

	/**
	 * @param listingCurrency
	 *            The listingCurrency to set.
	 */
	public void setListingCurrency(String listingCurrency) {
		this.listingCurrency = listingCurrency;
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
	 * @return Returns the totalWeight.
	 */
	public String getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight
	 *            The totalWeight to set.
	 */
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return Returns the SCREENID.
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return Returns the PRODUCT.
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SUBPRODUCT.
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
	 * @return the invoiceFormOneStatus
	 */
	public String getInvoiceFormOneStatus() {
		return invoiceFormOneStatus;
	}

	/**
	 * @param invoiceFormOneStatus
	 *            the invoiceFormOneStatus to set
	 */
	public void setInvoiceFormOneStatus(String invoiceFormOneStatus) {
		this.invoiceFormOneStatus = invoiceFormOneStatus;
	}

	/**
	 * @return the invoiceStatus
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus
	 *            the invoiceStatus to set
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * @return the ichFlag
	 */
	public String getIchFlag() {
		return ichFlag;
	}

	/**
	 * @param ichFlag
	 *            the ichFlag to set
	 */
	public void setIchFlag(String ichFlag) {
		this.ichFlag = ichFlag;
	}

	/**
	 * @return the noFormOneCaptured
	 */
	public String getNoFormOneCaptured() {
		return noFormOneCaptured;
	}

	/**
	 * @param noFormOneCaptured
	 *            the noFormOneCaptured to set
	 */
	public void setNoFormOneCaptured(String noFormOneCaptured) {
		this.noFormOneCaptured = noFormOneCaptured;
	}

	/**
	 * @return the fromScreenFlg
	 */
	public String getFromScreenFlg() {
		return fromScreenFlg;
	}

	/**
	 * @param fromScreenFlg the fromScreenFlg to set
	 */
	public void setFromScreenFlg(String fromScreenFlg) {
		this.fromScreenFlg = fromScreenFlg;
	}

}
