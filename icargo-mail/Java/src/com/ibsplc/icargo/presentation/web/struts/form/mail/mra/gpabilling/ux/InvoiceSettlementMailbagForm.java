/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementMailbagForm.java
 *
 *	Created by	:	A-7531
 *	Created on	:	24-Apr-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementMailbagForm.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	24-Apr-2018	:	Draft
 */
public class InvoiceSettlementMailbagForm extends ScreenModel {
	
	
	private static final String BUNDLE ="invoicesettlementMailbagLevel";
	
	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";

	
	//filter section
	private String invoiceNumber;
	private String gpaCode;
	private String gpaName;
	private String gpaCodeFilter;
	private String invoiceStatus;
	private String billingfromDate;
	private String billingtoDate;
	private String chequeNumberFilter;
	
	//settlement details
	private String settlementReferenceNumber;
	private String settlementDate;
	private String settlementCurrency;
	private String totalBilledAmt;
	private String totalSettledAmt;
	private String mailbagId;
	private String[] mailbagIdtable;
	private String[] billingIndicator;
	private String[] billedAmt;
	private String[] internalMca;
	private String[] actualBilled;
	private String[] totalSettled;
	private String[] dueAmt;
	private String[] currentSettlingAmount;
	private String[] paymentStatus;
	private String[] remarks;
	private String[] caseClosed;
	
	//Settlement details capture
	private String[]chequeNumber;
	private String[]chequeDate;
	private String[]bankName;
	private String[]branchName;
	private String[]chequeAmount;
	private String[] isDelete;
	private String[]chequeRemarks;
	
	//Settlemenet history details
	private String mailbagidHistory;
	private String mcaNumber;
	private boolean popupFlag;
	
	private int pageNumber;
	private String displayPage;
	private String lastPageNum;
	private String actionFlag;
	private String selectedmail;
	
	

	private int selectedrow;
	private String []selectedIndex;
	private String [] stlOpFlag;
	private String[] settlemnetNumber;
	private String[]chequeNumberHistory;
	private String[]chequeDateHistory;
	private String[]chequeAmountHistory;
	private String[] settlementCurrencyHistory;
	private String[] settlementDateHistory;
	private String[] remarksHistory;
	private String[] check;
	private String defaultPageSize = "100";
	private int totalRecords;
	private boolean chequeFlag;
	private String flagFilter;
	private String deleteArray;//a-7871
	private String templateIndex;
	private String currentDialogOption;
	private String currentDialogId;
	private String saveBtnFlg;
	private String[] rowId;
	private String[] addButton;
	//added for IASCB-863
	private String selectedMailbag;
    private String overrideRounding;

	private String[] caseClosedArray;//Added by A-8399 as part of ICRD-305647
	
	
	public String[] getCaseClosedArray() {
		return caseClosedArray;
	}

	public void setCaseClosedArray(String[] caseClosedArray) {
		this.caseClosedArray = caseClosedArray;
	}

	/**
	 * @return the deleteArray
	 */
	public String getDeleteArray() {
		return deleteArray;
	}

	/**
	 * @param deleteArray the deleteArray to set
	 */
	public void setDeleteArray(String deleteArray) {
		this.deleteArray = deleteArray;
	}

	/**
	 * 	Getter for invoiceNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 *  @param invoiceNumber the invoiceNumber to set
	 * 	Setter for invoiceNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * 	Getter for gpaCode 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 *  @param gpaCode the gpaCode to set
	 * 	Setter for gpaCode 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * 	Getter for gpaName 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 *  @param gpaName the gpaName to set
	 * 	Setter for gpaName 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * 	Getter for gpaCodeFilter 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getGpaCodeFilter() {
		return gpaCodeFilter;
	}

	/**
	 *  @param gpaCodeFilter the gpaCodeFilter to set
	 * 	Setter for gpaCodeFilter 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setGpaCodeFilter(String gpaCodeFilter) {
		this.gpaCodeFilter = gpaCodeFilter;
	}

	/**
	 * 	Getter for invoiceStatus 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 *  @param invoiceStatus the invoiceStatus to set
	 * 	Setter for invoiceStatus 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * 	Getter for billingfromDate 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getBillingfromDate() {
		return billingfromDate;
	}

	/**
	 *  @param billingfromDate the billingfromDate to set
	 * 	Setter for billingfromDate 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setBillingfromDate(String billingfromDate) {
		this.billingfromDate = billingfromDate;
	}

	/**
	 * 	Getter for billingtoDate 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getBillingtoDate() {
		return billingtoDate;
	}

	/**
	 *  @param billingtoDate the billingtoDate to set
	 * 	Setter for billingtoDate 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setBillingtoDate(String billingtoDate) {
		this.billingtoDate = billingtoDate;
	}

	/**
	 * 	Getter for chequeNumberFilter 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getChequeNumberFilter() {
		return chequeNumberFilter;
	}

	/**
	 *  @param chequeNumberFilter the chequeNumberFilter to set
	 * 	Setter for chequeNumberFilter 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setChequeNumberFilter(String chequeNumberFilter) {
		this.chequeNumberFilter = chequeNumberFilter;
	}

	/**
	 * 	Getter for settlementReferenceNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getSettlementReferenceNumber() {
		return settlementReferenceNumber;
	}

	/**
	 *  @param settlementReferenceNumber the settlementReferenceNumber to set
	 * 	Setter for settlementReferenceNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setSettlementReferenceNumber(String settlementReferenceNumber) {
		this.settlementReferenceNumber = settlementReferenceNumber;
	}

	/**
	 * 	Getter for settlementDate 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getSettlementDate() {
		return settlementDate;
	}

	/**
	 *  @param settlementDate the settlementDate to set
	 * 	Setter for settlementDate 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * 	Getter for settlementCurrency 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	/**
	 *  @param settlementCurrency the settlementCurrency to set
	 * 	Setter for settlementCurrency 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	/**
	 * 	Getter for totalBilledAmt 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getTotalBilledAmt() {
		return totalBilledAmt;
	}

	/**
	 *  @param totalBilledAmt the totalBilledAmt to set
	 * 	Setter for totalBilledAmt 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setTotalBilledAmt(String totalBilledAmt) {
		this.totalBilledAmt = totalBilledAmt;
	}

	/**
	 * 	Getter for totalSettledAmt 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getTotalSettledAmt() {
		return totalSettledAmt;
	}

	/**
	 *  @param totalSettledAmt the totalSettledAmt to set
	 * 	Setter for totalSettledAmt 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setTotalSettledAmt(String totalSettledAmt) {
		this.totalSettledAmt = totalSettledAmt;
	}

	/**
	 * 	Getter for mailbagId 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	/**
	 * 	Getter for mailbagIdtable 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getMailbagIdtable() {
		return mailbagIdtable;
	}

	/**
	 *  @param mailbagIdtable the mailbagIdtable to set
	 * 	Setter for mailbagIdtable 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setMailbagIdtable(String[] mailbagIdtable) {
		this.mailbagIdtable = mailbagIdtable;
	}

	/**
	 * 	Getter for billingIndicator 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getBillingIndicator() {
		return billingIndicator;
	}

	/**
	 *  @param billingIndicator the billingIndicator to set
	 * 	Setter for billingIndicator 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setBillingIndicator(String[] billingIndicator) {
		this.billingIndicator = billingIndicator;
	}

	/**
	 * 	Getter for billedAmt 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getBilledAmt() {
		return billedAmt;
	}

	/**
	 *  @param billedAmt the billedAmt to set
	 * 	Setter for billedAmt 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setBilledAmt(String[] billedAmt) {
		this.billedAmt = billedAmt;
	}

	/**
	 * 	Getter for internalMca 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getInternalMca() {
		return internalMca;
	}

	/**
	 *  @param internalMca the internalMca to set
	 * 	Setter for internalMca 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setInternalMca(String[] internalMca) {
		this.internalMca = internalMca;
	}

	/**
	 * 	Getter for actualBilled 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getActualBilled() {
		return actualBilled;
	}

	/**
	 *  @param actualBilled the actualBilled to set
	 * 	Setter for actualBilled 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setActualBilled(String[] actualBilled) {
		this.actualBilled = actualBilled;
	}

	/**
	 * 	Getter for totalSettled 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getTotalSettled() {
		return totalSettled;
	}

	/**
	 *  @param totalSettled the totalSettled to set
	 * 	Setter for totalSettled 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setTotalSettled(String[] totalSettled) {
		this.totalSettled = totalSettled;
	}

	/**
	 * 	Getter for dueAmt 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getDueAmt() {
		return dueAmt;
	}

	/**
	 *  @param dueAmt the dueAmt to set
	 * 	Setter for dueAmt 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setDueAmt(String[] dueAmt) {
		this.dueAmt = dueAmt;
	}

	
	/**
	 * @return the currentSettlingAmount
	 */
	public String[] getCurrentSettlingAmount() {
		return currentSettlingAmount;
	}

	/**
	 * @param currentSettlingAmount the currentSettlingAmount to set
	 */
	public void setCurrentSettlingAmount(String[] currentSettlingAmount) {
		this.currentSettlingAmount = currentSettlingAmount;
	}

	/**
	 * 	Getter for paymentStatus 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 *  @param paymentStatus the paymentStatus to set
	 * 	Setter for paymentStatus 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setPaymentStatus(String[] paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * 	Getter for remarks 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 *  @param remarks the remarks to set
	 * 	Setter for remarks 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	/**
	 * 	Getter for caseClosed 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getCaseClosed() {
		return caseClosed;
	}

	/**
	 *  @param caseClosed the caseClosed to set
	 * 	Setter for caseClosed 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setCaseClosed(String[] caseClosed) {
		this.caseClosed = caseClosed;
	}

	/**
	 * 	Getter for chequeNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getChequeNumber() {
		return chequeNumber;
	}

	/**
	 *  @param chequeNumber the chequeNumber to set
	 * 	Setter for chequeNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setChequeNumber(String[] chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * 	Getter for chequeDate 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getChequeDate() {
		return chequeDate;
	}

	/**
	 *  @param chequeDate the chequeDate to set
	 * 	Setter for chequeDate 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setChequeDate(String[] chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * 	Getter for bankName 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getBankName() {
		return bankName;
	}

	/**
	 *  @param bankName the bankName to set
	 * 	Setter for bankName 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setBankName(String[] bankName) {
		this.bankName = bankName;
	}

	/**
	 * 	Getter for branchName 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getBranchName() {
		return branchName;
	}

	/**
	 *  @param branchName the branchName to set
	 * 	Setter for branchName 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setBranchName(String[] branchName) {
		this.branchName = branchName;
	}

	/**
	 * 	Getter for chequeAmount 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getChequeAmount() {
		return chequeAmount;
	}

	/**
	 *  @param chequeAmount the chequeAmount to set
	 * 	Setter for chequeAmount 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setChequeAmount(String[] chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	/**
	 * 	Getter for isDelete 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getIsDelete() {
		return isDelete;
	}

	/**
	 *  @param isDelete the isDelete to set
	 * 	Setter for isDelete 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setIsDelete(String[] isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * 	Getter for chequeRemarks 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getChequeRemarks() {
		return chequeRemarks;
	}

	/**
	 *  @param chequeRemarks the chequeRemarks to set
	 * 	Setter for chequeRemarks 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setChequeRemarks(String[] chequeRemarks) {
		this.chequeRemarks = chequeRemarks;
	}

	/**
	 * 	Getter for mailbagidHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getMailbagidHistory() {
		return mailbagidHistory;
	}

	/**
	 *  @param mailbagidHistory the mailbagidHistory to set
	 * 	Setter for mailbagidHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setMailbagidHistory(String mailbagidHistory) {
		this.mailbagidHistory = mailbagidHistory;
	}

	/**
	 * 	Getter for mcaNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String getMcaNumber() {
		return mcaNumber;
	}

	/**
	 *  @param mcaNumber the mcaNumber to set
	 * 	Setter for mcaNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setMcaNumber(String mcaNumber) {
		this.mcaNumber = mcaNumber;
	}

	/**
	 * 	Getter for settlemnetNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getSettlemnetNumber() {
		return settlemnetNumber;
	}

	/**
	 *  @param settlemnetNumber the settlemnetNumber to set
	 * 	Setter for settlemnetNumber 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setSettlemnetNumber(String[] settlemnetNumber) {
		this.settlemnetNumber = settlemnetNumber;
	}

	/**
	 * 	Getter for chequeNumberHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getChequeNumberHistory() {
		return chequeNumberHistory;
	}

	/**
	 *  @param chequeNumberHistory the chequeNumberHistory to set
	 * 	Setter for chequeNumberHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setChequeNumberHistory(String[] chequeNumberHistory) {
		this.chequeNumberHistory = chequeNumberHistory;
	}

	/**
	 * 	Getter for chequeDateHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getChequeDateHistory() {
		return chequeDateHistory;
	}

	/**
	 *  @param chequeDateHistory the chequeDateHistory to set
	 * 	Setter for chequeDateHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setChequeDateHistory(String[] chequeDateHistory) {
		this.chequeDateHistory = chequeDateHistory;
	}

	/**
	 * 	Getter for chequeAmountHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getChequeAmountHistory() {
		return chequeAmountHistory;
	}

	/**
	 *  @param chequeAmountHistory the chequeAmountHistory to set
	 * 	Setter for chequeAmountHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setChequeAmountHistory(String[] chequeAmountHistory) {
		this.chequeAmountHistory = chequeAmountHistory;
	}

	/**
	 * 	Getter for settlementCurrencyHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getSettlementCurrencyHistory() {
		return settlementCurrencyHistory;
	}

	/**
	 *  @param settlementCurrencyHistory the settlementCurrencyHistory to set
	 * 	Setter for settlementCurrencyHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setSettlementCurrencyHistory(String[] settlementCurrencyHistory) {
		this.settlementCurrencyHistory = settlementCurrencyHistory;
	}

	/**
	 * 	Getter for settlementDateHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getSettlementDateHistory() {
		return settlementDateHistory;
	}

	/**
	 *  @param settlementDateHistory the settlementDateHistory to set
	 * 	Setter for settlementDateHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setSettlementDateHistory(String[] settlementDateHistory) {
		this.settlementDateHistory = settlementDateHistory;
	}

	/**
	 * 	Getter for remarksHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public String[] getRemarksHistory() {
		return remarksHistory;
	}

	/**
	 *  @param remarksHistory the remarksHistory to set
	 * 	Setter for remarksHistory 
	 *	Added by : A-7531 on 24-Apr-2018
	 * 	Used for :
	 */
	public void setRemarksHistory(String[] remarksHistory) {
		this.remarksHistory = remarksHistory;
	}

	
	
	
	
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 *	Added by 			: A-7531 on 24-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 *	Added by 			: A-7531 on 24-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 *	Added by 			: A-7531 on 24-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}

	public String getBundle() {
		return BUNDLE;
	}
	
	public void setBundle(String bundle) {
		//this.bundle = bundle;
	}



	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

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

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}


	public String getSelectedmail() {
		return selectedmail;
	}

	public void setSelectedmail(String selectedmail) {
		this.selectedmail = selectedmail;
	}

	public String [] getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(String [] selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public String [] getStlOpFlag() {
		return stlOpFlag;
	}

	public void setStlOpFlag(String [] stlOpFlag) {
		this.stlOpFlag = stlOpFlag;
	}

	public String[] getCheck() {
		return check;
	}

	public void setCheck(String[] check) {
		this.check = check;
	}

	public String getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(String defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public boolean isPopupFlag() {
		return popupFlag;
	}

	public void setPopupFlag(boolean popupFlag) {
		this.popupFlag = popupFlag;
	}

	public boolean isChequeFlag() {
		return chequeFlag;
	}

	public void setChequeFlag(boolean chequeFlag) {
		this.chequeFlag = chequeFlag;
	}

	public String getFlagFilter() {
		return flagFilter;
	}

	public void setFlagFilter(String flagFilter) {
		this.flagFilter = flagFilter;
	}

	public String getTemplateIndex() {
		return templateIndex;
	}

	public void setTemplateIndex(String templateIndex) {
		this.templateIndex = templateIndex;
	}

	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	public String getCurrentDialogId() {
		return currentDialogId;
	}

	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	public String getSaveBtnFlg() {
		return saveBtnFlg;
	}

	public void setSaveBtnFlg(String saveBtnFlg) {
		this.saveBtnFlg = saveBtnFlg;
	}

	/**
	 * 	Getter for rowId 
	 *	Added by : A-7531 on 29-Nov-2018
	 * 	Used for :
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 *  @param rowId the rowId to set
	 * 	Setter for rowId 
	 *	Added by : A-7531 on 29-Nov-2018
	 * 	Used for :
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
public int getSelectedrow() {
		return selectedrow;
	}   
public void setSelectedrow(int selectedrow) {
		this.selectedrow = selectedrow;
	}

	 /**
	 * 	Getter for addButton 
	 *	Added by : A-7531 on 26-Dec-2018
	 * 	Used for :
	 */
	public String[] getAddButton() {
		return addButton;
	}

	/**
	 *  @param addButton the addButton to set
	 * 	Setter for addButton 
	 *	Added by : A-7531 on 26-Dec-2018
	 * 	Used for :
	 */
	public void setAddButton(String[] addButton) {
		this.addButton = addButton;
	}

	/**
	 * 	Getter for selectedMailbag 
	 *	Added by : A-7531 on 13-Mar-2019
	 * 	Used for :
	 */
	public String getSelectedMailbag() {
		return selectedMailbag;
	}

	/**
	 *  @param selectedMailbag the selectedMailbag to set
	 * 	Setter for selectedMailbag 
	 *	Added by : A-7531 on 13-Mar-2019
	 * 	Used for :
	 */
	public void setSelectedMailbag(String selectedMailbag) {
		this.selectedMailbag = selectedMailbag;
	}
	
	public String getOverrideRounding() {
		return overrideRounding;
	}

	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}

	private boolean createButtonFlag;
	public boolean isCreateButtonFlag() {
		return createButtonFlag;
	}
	public void setCreateButtonFlag(boolean createButtonFlag) {
		this.createButtonFlag = createButtonFlag;
	}
}
