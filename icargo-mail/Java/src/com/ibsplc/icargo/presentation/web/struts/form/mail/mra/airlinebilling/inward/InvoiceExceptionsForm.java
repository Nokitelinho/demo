/*
 * InvoiceExceptionsForm.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward;




import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author Sreekanth.V.G
 * Form for InvoiceExceptions  screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 20, 2007   Sreekanth.V.G   			Initial draft
 *  
 */

public class InvoiceExceptionsForm extends ScreenModel {

    private static final String BUNDLE = "invoiceexceptionsresources";

	//private String bundle;

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.invoiceexceptions";
	
	private String airlineCode;
	
	private String invoiceNumber;
	
	private String clearencePeriod;
	 
	private String exceptionStatus;
	
	private String rejectionMemoNumber;
	
	private String memoStatus;
	
	private String contractCurrency;
	
	private String closeFlag;
	
	private String rowId[];
	
	private String operationFlag;
	
	private String fromScreenFlag;
	
	private String cn66CloseFlag;
	
	private String[] invNumber;
	
	private String[] memoNumber;
	
	private String[] expStatus;
	
/*Added by Indu for AcceptInvoice PopUp*/
	
	private String[] remarks;
	
	private String popupRemarks;
	
	private String screenStatus;
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
	 * @return Returns the popupRemarks.
	 */
	public String getPopupRemarks() {
		return popupRemarks;
	}

	/**
	 * @param popupRemarks The popupRemarks to set.
	 */
	public void setPopupRemarks(String popupRemarks) {
		this.popupRemarks = popupRemarks;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
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
	 * @return Returns the invNumber.
	 */
	public String[] getInvNumber() {
		return invNumber;
	}

	/**
	 * @param invNumber The invNumber to set.
	 */
	public void setInvNumber(String[] invNumber) {
		this.invNumber = invNumber;
	}

	/**
	 * @return Returns the fromScreenFlag.
	 */
	public String getFromScreenFlag() {
		return fromScreenFlag;
	}

	/**
	 * @param fromScreenFlag The fromScreenFlag to set.
	 */
	public void setFromScreenFlag(String fromScreenFlag) {
		this.fromScreenFlag = fromScreenFlag;
	}

	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return closeFlag;
	}

	/**
	 * @param closeFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return Returns the contractCurrency.
	 */
	public String getContractCurrency() {
		return contractCurrency;
	}

	/**
	 * @param contractCurrency The contractCurrency to set.
	 */
	public void setContractCurrency(String contractCurrency) {
		this.contractCurrency = contractCurrency;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the clearencePeriod.
	 */
	public String getClearencePeriod() {
		return clearencePeriod;
	}

	/**
	 * @param clearencePeriod The clearencePeriod to set.
	 */
	public void setClearencePeriod(String clearencePeriod) {
		this.clearencePeriod = clearencePeriod;
	}

	/**
	 * @return Returns the exceptionStatus.
	 */
	public String getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * @param exceptionStatus The exceptionStatus to set.
	 */
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return Returns the memoStatus.
	 */
	public String getMemoStatus() {
		return memoStatus;
	}

	/**
	 * @param memoStatus The memoStatus to set.
	 */
	public void setMemoStatus(String memoStatus) {
		this.memoStatus = memoStatus;
	}

	/**
	 * @return Returns the rejectionMemoNumber.
	 */
	public String getRejectionMemoNumber() {
		return rejectionMemoNumber;
	}

	/**
	 * @param rejectionMemoNumber The rejectionMemoNumber to set.
	 */
	public void setRejectionMemoNumber(String rejectionMemoNumber) {
		this.rejectionMemoNumber = rejectionMemoNumber;
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
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		//this.bundle = bundle;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	public String getCn66CloseFlag() {
		return cn66CloseFlag;
	}

	public void setCn66CloseFlag(String cn66CloseFlag) {
		this.cn66CloseFlag = cn66CloseFlag;
	}

	

	/**
	 * @return Returns the memoNumber.
	 */
	public String[] getMemoNumber() {
		return memoNumber;
	}

	/**
	 * @param memoNumber The memoNumber to set.
	 */
	public void setMemoNumber(String[] memoNumber) {
		this.memoNumber = memoNumber;
	}

	/**
	 * @return Returns the expStatus.
	 */
	public String[] getExpStatus() {
		return expStatus;
	}

	/**
	 * @param expStatus The expStatus to set.
	 */
	public void setExpStatus(String[] expStatus) {
		this.expStatus = expStatus;
	}


	
}