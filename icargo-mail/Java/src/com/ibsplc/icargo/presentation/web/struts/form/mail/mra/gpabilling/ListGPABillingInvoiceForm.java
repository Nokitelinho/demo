/*
 * ListGPABillingInvoiceForm.java Created on June 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author a-3434
 *
 */
public class ListGPABillingInvoiceForm extends ScreenModel{

	private static final String BUNDLE = "listgpabillinginvoiceresources";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.gpabilling.listgpabillinginvoice";

	public static final String STATUS_LSTINVOICE_SUCCESS = "listinvoice_success";

	private String fromDate;

	private String toDate;

	private String gpacode;

	private String invoiceNo;
	
	private String	invoiceDate;
	
	private String	invoiceStatus;
	
	private String billedAmount;
	private String billingPeriod;
	private String failureFlag;
	private String gpaHKGPostConfirmFinalize;
	private String invoiceFinalizedStatus;
	private String fromScreen;
	private String[] invStatus;//Added by A-6991 for ICRD-211662
	/**
	 * Added for ICRD-189966
	 */
	private String overrideRounding;
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	/**
	 * 
	 * @author A-3447 for cr-162
	 */
	
	
	private String lastPageNumber = "0";

	/**
	 * @return the lastPageNumber
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}
	/**
	 * @param lastPageNumber the lastPageNumber to set
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	/**
	 * @return the displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	private String displayPage = "1";
	private String selectedRow;
	private String passFileName;
	private String periodNumber;
	private String checkPASS;
	/**
	 * 
	 * @author A-3447 for cr-162
	 */
	
	
	/**
	 * @return Returns the failureFlag.
	 */
	public String getFailureFlag() {
		return failureFlag;
	}

	/**
	 * @param failureFlag The failureFlag to set.
	 */
	public void setFailureFlag(String failureFlag) {
		this.failureFlag = failureFlag;
	}

	/**
	 * @return Returns the billingPeriod.
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}

	/**
	 * @param billingPeriod The billingPeriod to set.
	 */
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	/**
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="ListGPABillingInvoicesDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="ListGPABillingInvoicesDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * return the screen id
	 */
	public String getScreenId() {

		return SCREENID;
	}

	/**
	 * return the product
	 */
	public String getProduct() {

		return PRODUCT;
	}

	/**
	 * return the subproduct name
	 */
	public String getSubProduct() {

		return SUBPRODUCT;
	}
	/**
	 * @return Returns the billedAmount.
	 */
	public String getBilledAmount() {
		return billedAmount;
	}

	/**
	 * @param billedAmount The billedAmount to set.
	 */
	public void setBilledAmount(String billedAmount) {
		this.billedAmount = billedAmount;
	}

	
	/**
	 * @return Returns the gpacode.
	 */
	public String getGpacode() {
		return gpacode;
	}

	/**
	 * @param gpacode The gpacode to set.
	 */
	public void setGpacode(String gpacode) {
		this.gpacode = gpacode;
	}

	/**
	 * @return Returns the invoiceDate.
	 */
	public String getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate The invoiceDate to set.
	 */
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return Returns the invoiceNo.
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * @param invoiceNo The invoiceNo to set.
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * @return Returns the invoiceStatus.
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus The invoiceStatus to set.
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	public String getGpaHKGPostConfirmFinalize() {
		return gpaHKGPostConfirmFinalize;
	}

	public void setGpaHKGPostConfirmFinalize(String gpaHKGPostConfirmFinalize) {
		this.gpaHKGPostConfirmFinalize = gpaHKGPostConfirmFinalize;
	}

	public String getInvoiceFinalizedStatus() {
		return invoiceFinalizedStatus;
	}

	public void setInvoiceFinalizedStatus(String invoiceFinalizedStatus) {
		this.invoiceFinalizedStatus = invoiceFinalizedStatus;
	}
	/**
	 * @return the overrideRounding
	 */
	public String getOverrideRounding() {
		return overrideRounding;
	}
	/**
	 * @param overrideRounding the overrideRounding to set
	 */
	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}
	/**
	 * 	Getter for invStatus 
	 *	Added by : A-6991 on 22-Nov-2017
	 * 	Used for : ICRD-211662
	 */
	public String[] getInvStatus() {
		return invStatus;
	}
	/**
	 *  @param invStatus the invStatus to set
	 * 	Setter for invStatus 
	 *	Added by : A-6991 on 22-Nov-2017
	 * 	Used for : ICRD-211662
	 */
	public void setInvStatus(String[] invStatus) {
		this.invStatus = invStatus;
	}


	public String getPassFileName() {
		return passFileName;
	}

	public void setPassFileName(String passFileName) {
		this.passFileName = passFileName;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public String getCheckPASS() {
		return checkPASS;
	}

	public void setCheckPASS(String checkPASS) {
		this.checkPASS = checkPASS;
	}
}
