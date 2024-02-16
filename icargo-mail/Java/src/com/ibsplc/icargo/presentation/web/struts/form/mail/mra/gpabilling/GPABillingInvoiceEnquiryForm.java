/*
 * GPABillingInvoiceEnquiryForm.java Created on July 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;
import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-3456
 *
 */
public class GPABillingInvoiceEnquiryForm extends ScreenModel{
	private static final String BUNDLE ="gpabillinginvoiceenquiry";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";
	private String invoiceNo;
	private String gpaCode;
	private String gpaName;
	private String bundle;
	private String frmDate;
	private String toDate;
	private String invoiceDate;
	private String invoiceStatus;
	private String currency;
	private Double totalAmount;
	private String showPopup;
	private String counter;
	private String remarks;
	private String status;
	private String dsn;
	private String dsDate;
	private String selectedRow;
	private String disableButton;
	private String closePopup;
	private String invokingScreen;
	private String[] ccaReferenceNumber;
	private String loadEInvoice;
	private String eInvoiceMessage;
	private String txtInvoiceMsg;
	private String invoiceFinalizedStatus;
	private String[] invStatus;
	
	
	/**
	 * @return Returns the ccaReferenceNumber.
	 */
	public String[] getCcaReferenceNumber() {
		return ccaReferenceNumber;
	}
	/**
	 * @param ccaReferenceNumber The ccaReferenceNumber to set.
	 */
	public void setCcaReferenceNumber(String[] ccaReferenceNumber) {
		this.ccaReferenceNumber = ccaReferenceNumber;
	}
	/**
	 * @return the invokingScreen
	 */
	public String getInvokingScreen() {
		return invokingScreen;
	}
	/**
	 * @param invokingScreen the invokingScreen to set
	 */
	public void setInvokingScreen(String invokingScreen) {
		this.invokingScreen = invokingScreen;
	}
	/**
	 * @return Returns the closePopup.
	 */
	public String getClosePopup() {
		return closePopup;
	}
	/**
	 * @param closePopup The closePopup to set.
	 */
	public void setClosePopup(String closePopup) {
		this.closePopup = closePopup;
	}
	/**
	 * @return Returns the totalAmount.
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount The totalAmount to set.
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return Returns the disableButton.
	 */
	public String getDisableButton() {
		return disableButton;
	}
	/**
	 * @param disableButton The disableButton to set.
	 */
	public void setDisableButton(String disableButton) {
		this.disableButton = disableButton;
	}
	/**
	 * @return Returns the dsDate.
	 */
	public String getDsDate() {
		return dsDate;
	}
	/**
	 * @param dsDate The dsDate to set.
	 */
	public void setDsDate(String dsDate) {
		this.dsDate = dsDate;
	}
	/**
	 * @return Returns the selectedRow.
	 */
	public String getSelectedRow() {
		return selectedRow;
	}
	/**
	 * @param selectedRow The selectedRow to set.
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}
	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the counter.
	 */
	public String getCounter() {
		return counter;
	}
	/**
	 * @param counter The counter to set.
	 */
	public void setCounter(String counter) {
		this.counter = counter;
	}
	/**
	 * @return Returns the showPopup.
	 */
	public String getShowPopup() {
		return showPopup;
	}
	/**
	 * @param showPopup The showPopup to set.
	 */
	public void setShowPopup(String showPopup) {
		this.showPopup = showPopup;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	public String getGpaName() {
		return gpaName;
	}
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	/**
	 * @return Returns the SUBPRODUCT.
	 */
	public String getSUBPRODUCT() {
		return SUBPRODUCT;
	}
	/**
	 * @return Returns screenId.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the product.
	 */
    public String getProduct() {
        return PRODUCT;
    }
    /**
	 * @return Returns the subproduct.
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
		this.bundle = bundle;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getFrmDate() {
		return frmDate;
	}
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getLoadEInvoice() {
		return loadEInvoice;
	}
	public void setLoadEInvoice(String loadEInvoice) {
		this.loadEInvoice = loadEInvoice;
	}
	public String getEInvoiceMessage() {
		return eInvoiceMessage;
	}
	public void setEInvoiceMessage(String invoiceMessage) {
		eInvoiceMessage = invoiceMessage;
	}
	public String getTxtInvoiceMsg() {
		return txtInvoiceMsg;
	}
	public void setTxtInvoiceMsg(String txtInvoiceMsg) {
		this.txtInvoiceMsg = txtInvoiceMsg;
	}
	public String getInvoiceFinalizedStatus() {
		return invoiceFinalizedStatus;
	}
	public void setInvoiceFinalizedStatus(String invoiceFinalizedStatus) {
		this.invoiceFinalizedStatus = invoiceFinalizedStatus;
	}
	/**
	 * @return the invStatus
	 */
	public String[] getInvStatus() {
		return invStatus;
	}
	/**
	 * @param invStatus the invStatus to set
	 */
	public void setInvStatus(String[] invStatus) {
		this.invStatus = invStatus;
	}

    }



