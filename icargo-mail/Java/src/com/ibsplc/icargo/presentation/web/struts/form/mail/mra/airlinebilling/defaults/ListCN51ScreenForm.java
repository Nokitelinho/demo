/*
 * ListCN51ScreenForm.java Created on Mar 14,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author a-2049
 *
 */
public class ListCN51ScreenForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";
	
	private static final String PRODUCT = "mail";
	
	private static final String SUBPRODUCT = "defaults";
	
	private static final String BUNDLE = "listCN51ScreenBundle";
	
	private String blgFromDateStr;
	
	private String blgToDateStr;
	
	private String airlineCode;
	
	private String interlineBlgType;   //Billing mode
	
	private String[] tableRowId;
	
	private String[] invoiceNumber;
	private String checkButton; 
	//Added by A-7929 as part of ICRD-265471
	private String billingType;
	private String invoiceNo;
	
	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * @return the checkButton
	 */
	public String getCheckButton() {
		return checkButton;
	}

	/**
	 * @param checkButton the checkButton to set
	 */
	public void setCheckButton(String checkButton) {
		this.checkButton = checkButton;
	}

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
	/**
	 * hidden variable inside form- possible values 
	 * are "Y" or any other value 
	 */
	private String closeFrmCN51Flag;
	/**
	 * hidden variableinside form - possible values 
	 * are "Y" or any other value
	 */
	private String closeFrmCN66Flag;
	
	private String screenStatus;
	
	private String closeAccEntryFlag;
	
	private String accEntryFlag;
	
	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	@ DateFieldId(id="ListCN51DateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/
	public String getBlgFromDateStr() {
		return blgFromDateStr;
	}

	public void setBlgFromDateStr(String blgFromDateStr) {
		this.blgFromDateStr = blgFromDateStr;
	}
	@ DateFieldId(id="ListCN51DateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/
	public String getBlgToDateStr() {
		return blgToDateStr;
	}

	public void setBlgToDateStr(String blgToDateStr) {
		this.blgToDateStr = blgToDateStr;
	}

	public String getInterlineBlgType() {
		return interlineBlgType;
	}

	public void setInterlineBlgType(String interlineBlgType) {
		this.interlineBlgType = interlineBlgType;
	}

	public String[] getTableRowId() {
		return tableRowId;
	}

	public void setTableRowId(String[] tableRowId) {
		this.tableRowId = tableRowId;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public String getBundle() {
		return BUNDLE;
	}

	public String getCloseFrmCN51Flag() {
		return closeFrmCN51Flag;
	}

	public void setCloseFrmCN51Flag(String closeFrmCN51Flag) {
		this.closeFrmCN51Flag = closeFrmCN51Flag;
	}

	public String getCloseFrmCN66Flag() {
		return closeFrmCN66Flag;
	}

	public void setCloseFrmCN66Flag(String closeFrmCN66Flag) {
		this.closeFrmCN66Flag = closeFrmCN66Flag;
	}

	/**
	 * @return Returns the screenStatus.
	 */
	public String getScreenStatus() {
		return screenStatus;
	}

	/**
	 * @param screenStatus The screenStatus to set.
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}

	/**
	 * @return Returns the invoiceNumber.
	 */
	public String[] getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String[] invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return Returns the closeAccEntryFlag.
	 */
	public String getCloseAccEntryFlag() {
		return closeAccEntryFlag;
	}

	/**
	 * @param closeAccEntryFlag The closeAccEntryFlag to set.
	 */
	public void setCloseAccEntryFlag(String closeAccEntryFlag) {
		this.closeAccEntryFlag = closeAccEntryFlag;
	}

	public String getAccEntryFlag() {
		return accEntryFlag;
	}

	public void setAccEntryFlag(String accEntryFlag) {
		this.accEntryFlag = accEntryFlag;
	}
	
	

}
