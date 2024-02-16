/*
 * InvoiceSettlementForm.java Created on Mar 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.currency.Money;


/**
 * @author A-2408
 *
 */
public class InvoiceSettlementForm extends ScreenModel {

	private static final String BUNDLE ="invoicesettlement";

	//private String bundle; 

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.gpabilling.invoicesettlement";

	
	private String gpaCodeFilter;
	
	private String invRefNumberFilter;
	
	private String fromDate;

	private String toDate;
	
	private String settlementStatusFilter;
	
	private String[] gpaCode;
	
	private String[] invRefNumber;
	
	private String[] totalBilledAmountContractCurr;
	
	private String[] contractCurrency;
	
	private String[] totalBilledAmountSettlementCurr;
	
	private String[] amountSettled;
	
	private String[] settlementCurrency;
	
	private String[] currentSettlingAmount;
	
	private Money[]currentSettlingAmountMoney;
	
	private String[] settlementStatus;
	
	private String[] check;
	
	private String gpaCodeHistory;
	
	private String invoiceNumberHistory;
	
	private String settlementCurrencyHistory;
	
	private String[] select;
	
	private String screenStatus;
	//Added for ICRD - 7316
	private String gpaNameFilter;
	private String invoiceStatusFilter;
	private String chequeNumberFilter;
	/* SettlementDetails*/
	private String settlementReferenceNumber;	
	private String settlementDate;
	private String settleCurrency;
	
	private String []gpaName;
	private String []billingPeriod;

	/*SettelmentCapture Details*/
	private String []chequeNumber;
	private String []chequeDate;
	private String []bankName;
	private String []branchName;
	private String []chequeAmount;
	private String [] isDelete;
	private String deleteArray;
	private String []chequeRemarks;
	
	/*Settlement history details*/
	private String []settlementReferenceNumberHistory;
	private String []chequeNumberHistory;
	private String []chequeDateHistory;
	private String []bankNameHistory;
	private String []branchNameHistory;
	private String []chequeAmountHistory;
	private String []deleteHistory;
	private String []chequeRemarksHistory;
	private String []settleCurrencyHistory;
	private String []settlementDateHistory;
	
	private String availableSettlement;
	private String frmPopUp;
	private String [] selectedRow;
	private String selectedInvoice;
	public String getSelectedInvoice() {
		return selectedInvoice;
	}
	public void setSelectedInvoice(String selectedInvoice) {
		this.selectedInvoice = selectedInvoice;
	}
	private String [] stlOpFlag;
	private String [] rowId;
	private String opFlag;
	private String checkFlag;
	//added for pagination
	private String displayPage;
	private String lastPageNum;
	private String listInvoiceFlag;
	private String listAccountingFlag;
	private String createFlag;
	private double totalAmount;
	private String[] settlementId;
	/**
	 * @return the totalAmount
	 */
	public double getTotalAmount() {
		return totalAmount;
	}
	
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return the createFlag
	 */
	public String getCreateFlag() {
		return createFlag;
	}
	/**
	 * @param createFlag the createFlag to set
	 */
	public void setCreateFlag(String createFlag) {
		this.createFlag = createFlag;
	}
	public String getListAccountingFlag() {
		return listAccountingFlag;
	}
	public void setListAccountingFlag(String listAccountingFlag) {
		this.listAccountingFlag = listAccountingFlag;
	}
	public String getListInvoiceFlag() {
		return listInvoiceFlag;
	}
	public void setListInvoiceFlag(String listInvoiceFlag) {
		this.listInvoiceFlag = listInvoiceFlag;
	}
	/**
	 * @return Returns the product.
	 */
	public String getPRODUCT() {
		return PRODUCT;
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
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		//this.bundle = bundle;
	}



	/**
	 * @param category The category to set.
	 */

	

	/**
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="InvoiceSettlementDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
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
	 * @return Returns the gpaCodeFilter.
	 */
	public String getGpaCodeFilter() {
		return gpaCodeFilter;
	}

	/**
	 * @param gpaCodeFilter The gpaCodeFilter to set.
	 */
	public void setGpaCodeFilter(String gpaCodeFilter) {
		this.gpaCodeFilter = gpaCodeFilter;
	}
	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="InvoiceSettlementDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
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
	 * @return Returns the amountSettled.
	 */
	public String[] getAmountSettled() {
		return amountSettled;
	}

	/**
	 * @param amountSettled The amountSettled to set.
	 */
	public void setAmountSettled(String[] amountSettled) {
		this.amountSettled = amountSettled;
	}

	/**
	 * @return Returns the contractCurrency.
	 */
	public String[] getContractCurrency() {
		return contractCurrency;
	}

	/**
	 * @param contractCurrency The contractCurrency to set.
	 */
	public void setContractCurrency(String[] contractCurrency) {
		this.contractCurrency = contractCurrency;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String[] getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String[] gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the invRefNumber.
	 */
	public String[] getInvRefNumber() {
		return invRefNumber;
	}

	/**
	 * @param invRefNumber The invRefNumber to set.
	 */
	public void setInvRefNumber(String[] invRefNumber) {
		this.invRefNumber = invRefNumber;
	}

	/**
	 * @return Returns the invRefNumberFilter.
	 */
	public String getInvRefNumberFilter() {
		return invRefNumberFilter;
	}

	/**
	 * @param invRefNumberFilter The invRefNumberFilter to set.
	 */
	public void setInvRefNumberFilter(String invRefNumberFilter) {
		this.invRefNumberFilter = invRefNumberFilter;
	}

	/**
	 * @return Returns the settlementCurrency.
	 */
	public String[] getSettlementCurrency() {
		return settlementCurrency;
	}

	/**
	 * @param settlementCurrency The settlementCurrency to set.
	 */
	public void setSettlementCurrency(String[] settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	/**
	 * @return Returns the settlementStatusFilter.
	 */
	public String getSettlementStatusFilter() {
		return settlementStatusFilter;
	}

	/**
	 * @param settlementStatusFilter The settlementStatusFilter to set.
	 */
	public void setSettlementStatusFilter(String settlementStatusFilter) {
		this.settlementStatusFilter = settlementStatusFilter;
	}

	

	/**
	 * @return Returns the currentSettlingAmount.
	 */
	public String[] getCurrentSettlingAmount() {
		return currentSettlingAmount;
	}

	/**
	 * @param currentSettlingAmount The currentSettlingAmount to set.
	 */
	public void setCurrentSettlingAmount(String[] currentSettlingAmount) {
		this.currentSettlingAmount = currentSettlingAmount;
	}

	/**
	 * @return Returns the totalBilledAmountContractCurr.
	 */
	public String[] getTotalBilledAmountContractCurr() {
		return totalBilledAmountContractCurr;
	}

	/**
	 * @param totalBilledAmountContractCurr The totalBilledAmountContractCurr to set.
	 */
	public void setTotalBilledAmountContractCurr(
			String[] totalBilledAmountContractCurr) {
		this.totalBilledAmountContractCurr = totalBilledAmountContractCurr;
	}

	/**
	 * @return Returns the totalBilledAmountSettlementCurr.
	 */
	public String[] getTotalBilledAmountSettlementCurr() {
		return totalBilledAmountSettlementCurr;
	}

	/**
	 * @param totalBilledAmountSettlementCurr The totalBilledAmountSettlementCurr to set.
	 */
	public void setTotalBilledAmountSettlementCurr(
			String[] totalBilledAmountSettlementCurr) {
		this.totalBilledAmountSettlementCurr = totalBilledAmountSettlementCurr;
	}

	/**
	 * @return Returns the settlementStatus.
	 */
	public String[] getSettlementStatus() {
		return settlementStatus;
	}

	/**
	 * @param settlementStatus The settlementStatus to set.
	 */
	public void setSettlementStatus(String[] settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	/**
	 * @return Returns the check.
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * @param check The check to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * @return Returns the gpaCodeHistory.
	 */
	public String getGpaCodeHistory() {
		return gpaCodeHistory;
	}

	/**
	 * @param gpaCodeHistory The gpaCodeHistory to set.
	 */
	public void setGpaCodeHistory(String gpaCodeHistory) {
		this.gpaCodeHistory = gpaCodeHistory;
	}

	/**
	 * @return Returns the invoiceNumberHistory.
	 */
	public String getInvoiceNumberHistory() {
		return invoiceNumberHistory;
	}

	/**
	 * @param invoiceNumberHistory The invoiceNumberHistory to set.
	 */
	public void setInvoiceNumberHistory(String invoiceNumberHistory) {
		this.invoiceNumberHistory = invoiceNumberHistory;
	}

	/**
	 * @return Returns the settlementCurrencyHistory.
	 */
	public String getSettlementCurrencyHistory() {
		return settlementCurrencyHistory;
	}

	/**
	 * @param settlementCurrencyHistory The settlementCurrencyHistory to set.
	 */
	public void setSettlementCurrencyHistory(String settlementCurrencyHistory) {
		this.settlementCurrencyHistory = settlementCurrencyHistory;
	}

	/**
	 * @return Returns the select.
	 */
	public String[] getSelect() {
		return select;
	}

	/**
	 * @param select The select to set.
	 */
	public void setSelect(String[] select) {
		this.select = select;
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
	 * @return Returns the currentSettlingAmountMoney.
	 */
	public Money[] getCurrentSettlingAmountMoney() {
		return currentSettlingAmountMoney;
	}

	/**
	 * @param currentSettlingAmountMoney The currentSettlingAmountMoney to set.
	 */
	public void setCurrentSettlingAmountMoney(Money[] currentSettlingAmountMoney) {
		this.currentSettlingAmountMoney = currentSettlingAmountMoney;
	}

	/**
	 * @return the gpaNameFilter
	 */
	public String getGpaNameFilter() {
		return gpaNameFilter;
	}

	/**
	 * @param gpaNameFilter the gpaNameFilter to set
	 */
	public void setGpaNameFilter(String gpaNameFilter) {
		this.gpaNameFilter = gpaNameFilter;
	}

	/**
	 * @return the invoiceStatusFilter
	 */
	public String getInvoiceStatusFilter() {
		return invoiceStatusFilter;
	}

	/**
	 * @param invoiceStatusFilter the invoiceStatusFilter to set
	 */
	public void setInvoiceStatusFilter(String invoiceStatusFilter) {
		this.invoiceStatusFilter = invoiceStatusFilter;
	}

	/**
	 * @return the chequeNumberFilter
	 */
	public String getChequeNumberFilter() {
		return chequeNumberFilter;
	}

	/**
	 * @param chequeNumberFilter the chequeNumberFilter to set
	 */
	public void setChequeNumberFilter(String chequeNumberFilter) {
		this.chequeNumberFilter = chequeNumberFilter;
	}

	/**
	 * @return the settlementReferenceNumber
	 */
	public String getSettlementReferenceNumber() {
		return settlementReferenceNumber;
	}

	/**
	 * @param settlementReferenceNumber the settlementReferenceNumber to set
	 */
	public void setSettlementReferenceNumber(String settlementReferenceNumber) {
		this.settlementReferenceNumber = settlementReferenceNumber;
	}

	/**
	 * @return the settlementDate
	 */
	public String getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param settlementDate the settlementDate to set
	 */
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * @return the settleCurrency
	 */
	public String getSettleCurrency() {
		return settleCurrency;
	}

	/**
	 * @param settleCurrency the settleCurrency to set
	 */
	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

	/**
	 * @return the gpaName
	 */
	public String[] getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName the gpaName to set
	 */
	public void setGpaName(String[] gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return the billingPeriod
	 */
	public String[] getBillingPeriod() {
		return billingPeriod;
	}

	/**
	 * @param billingPeriod the billingPeriod to set
	 */
	public void setBillingPeriod(String[] billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	/**
	 * @return the chequeNumber
	 */
	public String[] getChequeNumber() {
		return chequeNumber;
	}

	/**
	 * @param chequeNumber the chequeNumber to set
	 */
	public void setChequeNumber(String[] chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @return the chequeDate
	 */
	public String[] getChequeDate() {
		return chequeDate;
	}

	/**
	 * @param chequeDate the chequeDate to set
	 */
	public void setChequeDate(String[] chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * @return the bankName
	 */
	public String[] getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String[] bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the branchName
	 */
	public String[] getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String[] branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the chequeAmount
	 */
	public String[] getChequeAmount() {
		return chequeAmount;
	}

	/**
	 * @param chequeAmount the chequeAmount to set
	 */
	public void setChequeAmount(String[] chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	

	/**
	 * @return the chequeRemarks
	 */
	public String[] getChequeRemarks() {
		return chequeRemarks;
	}

	/**
	 * @param chequeRemarks the chequeRemarks to set
	 */
	public void setChequeRemarks(String[] chequeRemarks) {
		this.chequeRemarks = chequeRemarks;
	}

	/**
	 * @return the settlementReferenceNumberHistory
	 */
	public String[] getSettlementReferenceNumberHistory() {
		return settlementReferenceNumberHistory;
	}

	/**
	 * @param settlementReferenceNumberHistory the settlementReferenceNumberHistory to set
	 */
	public void setSettlementReferenceNumberHistory(
			String[] settlementReferenceNumberHistory) {
		this.settlementReferenceNumberHistory = settlementReferenceNumberHistory;
	}

	/**
	 * @return the chequeNumberHistory
	 */
	public String[] getChequeNumberHistory() {
		return chequeNumberHistory;
	}

	/**
	 * @param chequeNumberHistory the chequeNumberHistory to set
	 */
	public void setChequeNumberHistory(String[] chequeNumberHistory) {
		this.chequeNumberHistory = chequeNumberHistory;
	}

	/**
	 * @return the chequeDateHistory
	 */
	public String[] getChequeDateHistory() {
		return chequeDateHistory;
	}

	/**
	 * @param chequeDateHistory the chequeDateHistory to set
	 */
	public void setChequeDateHistory(String[] chequeDateHistory) {
		this.chequeDateHistory = chequeDateHistory;
	}

	/**
	 * @return the bankNameHistory
	 */
	public String[] getBankNameHistory() {
		return bankNameHistory;
	}

	/**
	 * @param bankNameHistory the bankNameHistory to set
	 */
	public void setBankNameHistory(String[] bankNameHistory) {
		this.bankNameHistory = bankNameHistory;
	}

	/**
	 * @return the branchNameHistory
	 */
	public String[] getBranchNameHistory() {
		return branchNameHistory;
	}

	/**
	 * @param branchNameHistory the branchNameHistory to set
	 */
	public void setBranchNameHistory(String[] branchNameHistory) {
		this.branchNameHistory = branchNameHistory;
	}

	/**
	 * @return the chequeAmountHistory
	 */
	public String[] getChequeAmountHistory() {
		return chequeAmountHistory;
	}

	/**
	 * @param chequeAmountHistory the chequeAmountHistory to set
	 */
	public void setChequeAmountHistory(String[] chequeAmountHistory) {
		this.chequeAmountHistory = chequeAmountHistory;
	}

	/**
	 * @return the deleteHistory
	 */
	public String[] getDeleteHistory() {
		return deleteHistory;
	}

	/**
	 * @param deleteHistory the deleteHistory to set
	 */
	public void setDeleteHistory(String[] deleteHistory) {
		this.deleteHistory = deleteHistory;
	}

	/**
	 * @return the chequeRemarksHistory
	 */
	public String[] getChequeRemarksHistory() {
		return chequeRemarksHistory;
	}

	/**
	 * @param chequeRemarksHistory the chequeRemarksHistory to set
	 */
	public void setChequeRemarksHistory(String[] chequeRemarksHistory) {
		this.chequeRemarksHistory = chequeRemarksHistory;
	}

	/**
	 * @return the settleCurrencyHistory
	 */
	public String[] getSettleCurrencyHistory() {
		return settleCurrencyHistory;
	}

	/**
	 * @param settleCurrencyHistory the settleCurrencyHistory to set
	 */
	public void setSettleCurrencyHistory(String[] settleCurrencyHistory) {
		this.settleCurrencyHistory = settleCurrencyHistory;
	}

	/**
	 * @return the settlementDateHistory
	 */
	public String[] getSettlementDateHistory() {
		return settlementDateHistory;
	}

	/**
	 * @param settlementDateHistory the settlementDateHistory to set
	 */
	public void setSettlementDateHistory(String[] settlementDateHistory) {
		this.settlementDateHistory = settlementDateHistory;
	}

	/**
	 * @return the stlOpFlag
	 */
	public String[] getStlOpFlag() {
		return stlOpFlag;
	}

	/**
	 * @param stlOpFlag the stlOpFlag to set
	 */
	public void setStlOpFlag(String[] stlOpFlag) {
		this.stlOpFlag = stlOpFlag;
	}

	/**
	 * @return the availableSettlement
	 */
	public String getAvailableSettlement() {
		return availableSettlement;
	}

	/**
	 * @param availableSettlement the availableSettlement to set
	 */
	public void setAvailableSettlement(String availableSettlement) {
		this.availableSettlement = availableSettlement;
	}

	/**
	 * @return the selectedRow
	 */
	public String[] getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}

	

	/**
	 * @return the frmPopUp
	 */
	public String getFrmPopUp() {
		return frmPopUp;
	}

	/**
	 * @param frmPopUp the frmPopUp to set
	 */
	public void setFrmPopUp(String frmPopUp) {
		this.frmPopUp = frmPopUp;
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

	/**
	 * @return the lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum the lastPageNum to set
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	

	/**
	 * @return the opFlag
	 */
	public String getOpFlag() {
		return opFlag;
	}

	/**
	 * @param opFlag the opFlag to set
	 */
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}
	

	/**
	 * @return the checkFlag
	 */
	public String getCheckFlag() {
		return checkFlag;
	}

	/**
	 * @param checkFlag the checkFlag to set
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**
	 * @return the rowId
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(String[] isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * 
	 * @return
	 */
	public String[] isDelete(){
		return isDelete;
	}

	/**
	 * @return the delete
	 */
	public String getDeleteArray() {
		return deleteArray;
	}

	/**
	 * @param delete the delete to set
	 */
	public void setDeleteArray(String deleteArray) {
		this.deleteArray = deleteArray;
	}
	public String[] getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(String[] settlementId) {
		this.settlementId = settlementId;
	}


}
