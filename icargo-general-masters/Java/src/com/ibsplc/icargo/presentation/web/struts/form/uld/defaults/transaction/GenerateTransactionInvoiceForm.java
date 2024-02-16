/*
 * GenerateTransactionInvoiceForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1496
 *
 */
public class GenerateTransactionInvoiceForm extends ScreenModel {


	private static final String BUNDLE = "generatetransactioninvoice";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.generatetransactioninvoice";


	private String bundle;

	private String invoiceId;
    private String invoicedToCode;
    private String name;
    private String invoicedDate = "";
    private String remarks;
    private String txnType;
    private String mainScreenstatus;
    private String uldNumbers;
    private String txnRefNos;
    private String generateStatus;
    private String txndates;
    private String statusFlag;

    private String uldNumber;
    private String partyTypeFlag;
    private String demAmt;
    private String waiver;
    private String hiddenDmgAmt;
    private String hiddenWaiver;
    private String originalDmgAmt;
    private String originalWaiver;
    private String totalDmgAmt;


    private int prevIndex;
    private String invoiceFlag;

    /**
	 * @return the invoiceFlag
	 */
	public String getInvoiceFlag() {
		return invoiceFlag;
	}

	/**
	 * @param invoiceFlag the invoiceFlag to set
	 */
	public void setInvoiceFlag(String invoiceFlag) {
		this.invoiceFlag = invoiceFlag;
	}


//  Added for BUG-20447--starts
    private String hiddenToPartyCode;
    //Added by A-2408---to store the party name
    private String hiddenToPtyName;

	/**
	 * @return the hiddenToPartyCode
	 */
	public String getHiddenToPartyCode() {
		return hiddenToPartyCode;
	}

	/**
	 * @param hiddenToPartyCode the hiddenToPartyCode to set
	 */
	public void setHiddenToPartyCode(String hiddenToPartyCode) {
		this.hiddenToPartyCode = hiddenToPartyCode;
	}
    //ends BUG-20447
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
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 *
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
     *
     */
    public String getProduct() {
        return PRODUCT;
    }

   /**
    *
    */
    public String getSubProduct() {
        return SUBPRODUCT;
    }

	/**
	 * @return Returns the invoicedDate.
	 */
	public String getInvoicedDate() {
		return invoicedDate;
	}

	/**
	 * @param invoicedDate The invoicedDate to set.
	 */
	public void setInvoicedDate(String invoicedDate) {
		this.invoicedDate = invoicedDate;
	}

	/**
	 * @return Returns the invoiceId.
	 */
	public String getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @param invoiceId The invoiceId to set.
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return Returns the mainScreenstatus.
	 */
	public String getMainScreenstatus() {
		return mainScreenstatus;
	}

	/**
	 * @param mainScreenstatus The mainScreenstatus to set.
	 */
	public void setMainScreenstatus(String mainScreenstatus) {
		this.mainScreenstatus = mainScreenstatus;
	}

	/**
	 * @return Returns the txnRefNos.
	 */
	public String getTxnRefNos() {
		return txnRefNos;
	}

	/**
	 * @param txnRefNos The txnRefNos to set.
	 */
	public void setTxnRefNos(String txnRefNos) {
		this.txnRefNos = txnRefNos;
	}

	/**
	 * @return Returns the txnType.
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType The txnType to set.
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return Returns the uldNumbers.
	 */
	public String getUldNumbers() {
		return uldNumbers;
	}

	/**
	 * @param uldNumbers The uldNumbers to set.
	 */
	public void setUldNumbers(String uldNumbers) {
		this.uldNumbers = uldNumbers;
	}

	/**
	 * @return Returns the generateStatus.
	 */
	public String getGenerateStatus() {
		return generateStatus;
	}

	/**
	 * @param generateStatus The generateStatus to set.
	 */
	public void setGenerateStatus(String generateStatus) {
		this.generateStatus = generateStatus;
	}

	/**
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
	 * @return Returns the txndates.
	 */
	public String getTxndates() {
		return txndates;
	}

	/**
	 * @param txndates The txndates to set.
	 */
	public void setTxndates(String txndates) {
		this.txndates = txndates;
	}

	/**
	 * @return Returns the partyTypeFlag.
	 */
	public String getPartyTypeFlag() {
		return partyTypeFlag;
	}

	/**
	 * @param partyTypeFlag The partyTypeFlag to set.
	 */
	public void setPartyTypeFlag(String partyTypeFlag) {
		this.partyTypeFlag = partyTypeFlag;
	}

	/**
	 * @return Returns the demAmt.
	 */
	public String getDemAmt() {
		return demAmt;
	}

	/**
	 * @param demAmt The demAmt to set.
	 */
	public void setDemAmt(String demAmt) {
		this.demAmt = demAmt;
	}

	/**
	 * @return Returns the waiver.
	 */
	public String getWaiver() {
		return waiver;
	}

	/**
	 * @param waiver The waiver to set.
	 */
	public void setWaiver(String waiver) {
		this.waiver = waiver;
	}

	/**
	 * @return Returns the hiddenDmgAmt.
	 */
	public String getHiddenDmgAmt() {
		return hiddenDmgAmt;
	}

	/**
	 * @param hiddenDmgAmt The hiddenDmgAmt to set.
	 */
	public void setHiddenDmgAmt(String hiddenDmgAmt) {
		this.hiddenDmgAmt = hiddenDmgAmt;
	}

	/**
	 * @return Returns the totalDmgAmt.
	 */
	public String getTotalDmgAmt() {
		return totalDmgAmt;
	}

	/**
	 * @param totalDmgAmt The totalDmgAmt to set.
	 */
	public void setTotalDmgAmt(String totalDmgAmt) {
		this.totalDmgAmt = totalDmgAmt;
	}

	/**
	 * @return Returns the hiddenWaiver.
	 */
	public String getHiddenWaiver() {
		return hiddenWaiver;
	}

	/**
	 * @param hiddenWaiver The hiddenWaiver to set.
	 */
	public void setHiddenWaiver(String hiddenWaiver) {
		this.hiddenWaiver = hiddenWaiver;
	}

	/**
	 * @return Returns the prevIndex.
	 */
	public int getPrevIndex() {
		return prevIndex;
	}

	/**
	 * @param prevIndex The prevIndex to set.
	 */
	public void setPrevIndex(int prevIndex) {
		this.prevIndex = prevIndex;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the originalDmgAmt.
	 */
	public String getOriginalDmgAmt() {
		return originalDmgAmt;
	}

	/**
	 * @param originalDmgAmt The originalDmgAmt to set.
	 */
	public void setOriginalDmgAmt(String originalDmgAmt) {
		this.originalDmgAmt = originalDmgAmt;
	}

	/**
	 * @return Returns the originalWaiver.
	 */
	public String getOriginalWaiver() {
		return originalWaiver;
	}

	/**
	 * @param originalWaiver The originalWaiver to set.
	 */
	public void setOriginalWaiver(String originalWaiver) {
		this.originalWaiver = originalWaiver;
	}

	/**
	 * @return the hiddenToPtyName
	 */
	public String getHiddenToPtyName() {
		return hiddenToPtyName;
	}

	/**
	 * @param hiddenToPtyName the hiddenToPtyName to set
	 */
	public void setHiddenToPtyName(String hiddenToPtyName) {
		this.hiddenToPtyName = hiddenToPtyName;
	}



}
