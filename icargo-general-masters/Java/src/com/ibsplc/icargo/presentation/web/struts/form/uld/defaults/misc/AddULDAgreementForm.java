/*
 * AddULDAgreementForm.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * 
 * @author A-1862
 *
 */
public class AddULDAgreementForm extends ScreenModel {

	private static final String BUNDLE = "maintainuldagreement";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.maintainuldagreement";

	private String uldType;
	private String station;
	private String validFrom="";
	private String validTo="";
	private double demurrageRate;
	private int freeLoanPeriod;
	private String demurrageFrequency;
	private double taxes;
	private String remarks;
	private String[] selectedChecks;
	private String currencyCode;
	private String canClose;
	private String fromDate="";
	private String toDate="";
	private String bundle;
	private String lastPageNumber = "0";
	private String displayPage = "1";
	private String totalRecords="0";
	private String currentPage="1";
	private String actionStatus;
	private String flag;
	private String saveStatusFlag;
	private int sequenceNumber;
	private String agreementNumber;
/**
 * 
 * @return
 */
    public String getAgreementNumber() {
		return agreementNumber;
	}
/**
 * 
 * @param agreementNumber
 */

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

/**
 * 
 * @return
 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

/**
 * 
 * @param sequenceNumber
 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

/**
 * 
 * @return
 */
	public String getSaveStatusFlag() {
		return saveStatusFlag;
	}

/**
 * 
 * @param saveStatusFlag
 */
	public void setSaveStatusFlag(String saveStatusFlag) {
		this.saveStatusFlag = saveStatusFlag;
	}

/**
 * 
 * @return
 */
	public String getFlag() {
		return flag;
	}

/**
 * 
 * @param flag
 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

/**
 * 
 * @return
 */
	public String getActionStatus() {
		return actionStatus;
	}

/**
 * 
 * @param actionStatus
 */
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
/**
 * 
 * @return
 */
	public String getCurrentPage() {
		return currentPage;
	}
/**
 * 
 * @param currentPage
 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
/**
 * 
 * @return
 */
	public String getDisplayPage() {
		return displayPage;
	}
/**
 * 
 * @param displayPage
 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
/**
 * 
 * @return
 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}
/**
 * 
 * @param lastPageNumber
 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
/**
 * 
 * @return
 */
	public String getTotalRecords() {
		return totalRecords;
	}
/**
 * 
 * @param totalRecords
 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
/**
 * 
 * @return
 */
	public String getFromDate() {
		return fromDate;
	}
/**
 * 
 * @param fromDate
 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
/**
 * 
 * @return
 */
	public String getToDate() {
		return toDate;
	}
/**
 * 
 * @param toDate
 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
/**
 * 
 * @return
 */
	public String getCanClose() {
		return canClose;
	}
/**
 * 
 * @param canClose
 */
	public void setCanClose(String canClose) {
		this.canClose = canClose;
	}
/**
 * 
 * @return
 */
	public String getCurrencyCode() {
		return currencyCode;
	}
/**
 * 
 * @param currencyCode
 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
/**
 * 
 * @return
 */
	public String[] getSelectedChecks() {
		return selectedChecks;
	}
/**
 * 
 * @param selectedChecks
 */
	public void setSelectedChecks(String[] selectedChecks) {
		this.selectedChecks = selectedChecks;
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
 * 
 */
	public  String getBundle() {
		return BUNDLE;
	}
/**
 * 
 * @return
 */
	public String getDemurrageFrequency() {
		return demurrageFrequency;
	}
/**
 * 
 * @param demurrageFrequency
 */
	public void setDemurrageFrequency(String demurrageFrequency) {
		this.demurrageFrequency = demurrageFrequency;
	}
/**
 * 
 * @return
 */
	public double getDemurrageRate() {
		return demurrageRate;
	}
/**
 * 
 * @param demurrageRate
 */
	public void setDemurrageRate(double demurrageRate) {
		this.demurrageRate = demurrageRate;
	}
/**
 * 
 * @return
 */
	public int getFreeLoanPeriod() {
		return freeLoanPeriod;
	}
/**
 * 
 * @param freeLoanPeriod
 */
	public void setFreeLoanPeriod(int freeLoanPeriod) {
		this.freeLoanPeriod = freeLoanPeriod;
	}
/**
 * 
 * @return
 */
	public String getPRODUCT() {
		return PRODUCT;
	}
/**
 * 
 * @return
 */
	public String getRemarks() {
		return remarks;
	}
/**
 * 
 * @param remarks
 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
/**
 * 
 * @return
 */
	public String getSCREENID() {
		return SCREENID;
	}
/**
 * 
 * @return
 */
	public String getStation() {
		return station;
	}
/**
 * 
 * @param station
 */
	public void setStation(String station) {
		this.station = station;
	}
/**
 * 
 * @return
 */
	public String getSUBPRODUCT() {
		return SUBPRODUCT;
	}
/**
 * 
 * @return
 */
	public double getTaxes() {
		return taxes;
	}
/**
 * 
 * @param taxes
 */
	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}
/**
 * 
 * @return
 */
	public String getUldType() {
		return uldType;
	}
/**
 * 
 * @param uldType
 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}
/**
 * 
 * @return
 */
	public String getValidFrom() {
		return validFrom;
	}
/**
 * 
 * @param validFrom
 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
/**
 * 
 * @return
 */
	public String getValidTo() {
		return validTo;
	}
/**
 * 
 * @param validTo
 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
/**
 * 
 * @param bundle
 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

}
