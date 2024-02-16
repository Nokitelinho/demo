/*
 * MaintainULDAgreementForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1496
 *
 */
public class MaintainULDAgreementForm extends ScreenModel {

	public MaintainULDAgreementForm() {
		setLastPageNumStr("0");
		setDisplayPageNumStr("1");
	}

	private static final String BUNDLE = "maintainuldagreement";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.maintainuldagreement";


	private String bundle;
	private String agreementNumber="";
	private String partyType="";
	private String partyName="";
	private String fromPartyType="";
	private String fromPartyName="";
	private String fromPartyCode="";
	private String typeOfParty="";
	private String transactionType="";
	private String partyCode="";
	private String agreementStatus="";
	private String agreementDate="";
	private int freeLoanPeriod;
	private String fromDate="";
	private String toDate="";
	private double demurrageRate;
	private String demurrageFrequency="";
	private double taxes;
	private String currencyCode="";
	private String remarks="";
	private String uldType="";
	private String station="";
	private String validFromDate="";
	private String validToDate="";
	private String freeLoanTime="";
	private String demurrageFrequencyDetails="";
	private String demurrageRateDetails="";
	private String currency="";
	private String remarkDetails="";
	private String taxDetails="";
	private String[] check;
	private String[] selectedChecks;
	private String lastPageNumber = "0";
	private String displayPage = "1";
	private String onload;
	private String preview;
	private String canClear;
	private String canClose;
	private String hasListed;
	private String closeStatus;
	private String listStatus;
	private String createFlag;
	private String comboFlag;
	private String toComboFlag;
	private String uldTypeFilter="";
	private String uldTypeFilterWarningFlag="false";
	
	public String getUldTypeFilterWarningFlag() {
		return uldTypeFilterWarningFlag;
	}
	public void setUldTypeFilterWarningFlag(String uldTypeFilterWarningFlag) {
		this.uldTypeFilterWarningFlag = uldTypeFilterWarningFlag;
	}
	public String getUldTypeFilter() {
		return uldTypeFilter;
	}
	public void setUldTypeFilter(String uldTypeFilter) {
		this.uldTypeFilter = uldTypeFilter;
	}
	public String getToComboFlag() {
		return toComboFlag;
	}
	public void setToComboFlag(String toComboFlag) {
		this.toComboFlag = toComboFlag;
	}
	private String isDataSaved;
	/*added by a-3278 for bug 25164 on 13Nov08
	 * for populating party name on tabout
	 * */
	private String errorStatusFlag;
	private String agrPartyCode;
	private String agrPartyName;
	
	private boolean isAllParties;
	private boolean isFromAllParties;
	
	private String displayPageNumStr = "1";
	private String lastPageNumStr = "0";
	//Added by A-8445
	public static final String PAGINATION_MODE_FROM_LIST = "LIST";
	public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "NAVIGATION";
	private String navigationMode;
	
	public String getNavigationMode() {
		return navigationMode;
	}
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isAllParties() {
		return isAllParties;
	}
	/**
	 * 
	 * @param createFlag
	 */
	public void setAllParties(boolean isAllParties) {
		this.isAllParties = isAllParties;
	}
	/**
	 * 
	 * @return
	 */
	public String getCreateFlag() {
		return createFlag;
	}
	/**
	 * 
	 * @param createFlag
	 */
	public void setCreateFlag(String createFlag) {
		this.createFlag = createFlag;
	}
	/**
	 *
	 * @return
	 */
	public String getListStatus() {
		return listStatus;
	}
	/**
	 *
	 * @param listStatus
	 */
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	/**
	 *
	 * @return
	 */
	public String getCloseStatus(){
		return closeStatus;
		}
/**
 * 
 * @param closeStatus
 */
	public void setCloseStatus(String closeStatus){
		this.closeStatus=closeStatus;

			}
	/**
	 *
	 * @return
	 */
	public String getHasListed() {
		return hasListed;
	}
	/**
	 *
	 * @param hasListed
	 */
	public void setHasListed(String hasListed) {
		this.hasListed = hasListed;
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
	public String getCanClear() {
		return canClear;
	}
	/**
	 *
	 * @param canClear
	 */
	public void setCanClear(String canClear) {
		this.canClear = canClear;
	}
	/**
	 *
	 * @return
	 */
	public String getOnload() {
		return onload;
	}
	/**
	 *
	 * @param onload
	 */
	public void setOnload(String onload) {
		this.onload = onload;
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
	 * @return
	 */
	public String[] getCheck() {
		return check;
	}
	/**
	 *
	 * @param check
	 */
	public void setCheck(String[] check) {
		this.check = check;
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
     * @return agreementDate
     */
	public String getAgreementDate() {
		return agreementDate;
	}
	/**
	 *
	 * @param agreementDate
	 */
	public void setAgreementDate(String agreementDate) {
		this.agreementDate = agreementDate;
	}
	/**
	 *
	 * @return agreementNumber
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
	 * @return agreementStatus
	 */
	public String getAgreementStatus() {
		return agreementStatus;
	}

	/**
	 *
	 * @param agreementStatus
	 */
	public void setAgreementStatus(String agreementStatus) {
		this.agreementStatus = agreementStatus;
	}
	/**
	 *
	 * @return
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 *
	 * @param bundle
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	/**
	 *
	 * @return
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 *
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
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
	public String getDemurrageFrequencyDetails() {
		return demurrageFrequencyDetails;
	}
	/**
	 *
	 * @param demurrageFrequencyDetails
	 */
	public void setDemurrageFrequencyDetails(String demurrageFrequencyDetails) {
		this.demurrageFrequencyDetails = demurrageFrequencyDetails;
	}


	/**
	 *
	 * @return
	 */
	public String getDemurrageRateDetails() {
		return demurrageRateDetails;
	}
	/**
	 *
	 * @param demurrageRateDetails
	 */
	public void setDemurrageRateDetails(String demurrageRateDetails) {
		this.demurrageRateDetails = demurrageRateDetails;
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
	public String getFreeLoanTime() {
		return freeLoanTime;
	}
	/**
	 *
	 * @param freeLoanTime
	 */
	public void setFreeLoanTime(String freeLoanTime) {
		this.freeLoanTime = freeLoanTime;
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
	public String getPartyCode() {
		return partyCode;
	}
	/**
	 *
	 * @param partyCode
	 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}
	/**
	 *
	 * @return
	 */
	public String getPartyType() {
		return partyType;
	}
	/**
	 *
	 * @param partyType
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
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
	public String getRemarkDetails() {
		return remarkDetails;
	}
	/**
	 *
	 * @param remarkDetails
	 */
	public void setRemarkDetails(String remarkDetails) {
		this.remarkDetails = remarkDetails;
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
	public String getTaxDetails() {
		return taxDetails;
	}
	/**
	 *
	 * @param taxDetails
	 */
	public void setTaxDetails(String taxDetails) {
		this.taxDetails = taxDetails;
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
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 *
	 * @param transactionType
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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
	public String getValidFromDate() {
		return validFromDate;
	}
	/**
	 *
	 * @param validFromDate
	 */
	public void setValidFromDate(String validFromDate) {
		this.validFromDate = validFromDate;
	}
	/**
	 *
	 * @return
	 */
	public String getValidToDate() {
		return validToDate;
	}
	/**
	 *
	 * @param validToDate
	 */
	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	}
	/**
	 *
	 * @return
	 */
	public String getPartyName() {
		return partyName;
	}
	/**
	 *
	 * @param partyName
	 */
	public void setPartyName(String partyName) {
		this.partyName= partyName;
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
	public double getDemurrageRate() {
		return demurrageRate;
	}
	/**
	 *
	 * @return
	 */
	public String getPreview() {
		return preview;
	}
	/**
	 *
	 * @param preview
	 */
	public void setPreview(String preview) {
		this.preview = preview;
	}
	/**
	 * @return Returns the comboFlag.
	 */
	public String getComboFlag() {
		return comboFlag;
	}
	/**
	 * @param comboFlag The comboFlag to set.
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}
	/**
	 * @return the isDataSaved
	 */
	public String getIsDataSaved() {
		return isDataSaved;
	}
	/**
	 * @param isDataSaved the isDataSaved to set
	 */
	public void setIsDataSaved(String isDataSaved) {
		this.isDataSaved = isDataSaved;
	}
	/**
	 * @return the agrPartyCode
	 */
	public String getAgrPartyCode() {
		return agrPartyCode;
	}
	/**
	 * @param agrPartyCode the agrPartyCode to set
	 */
	public void setAgrPartyCode(String agrPartyCode) {
		this.agrPartyCode = agrPartyCode;
	}
	/**
	 * @return the agrPartyName
	 */
	public String getAgrPartyName() {
		return agrPartyName;
	}
	/**
	 * @param agrPartyName the agrPartyName to set
	 */
	public void setAgrPartyName(String agrPartyName) {
		this.agrPartyName = agrPartyName;
	}
	/**
	 * @return the errorStatusFlag
	 */
	public String getErrorStatusFlag() {
		return errorStatusFlag;
	}
	/**
	 * @param errorStatusFlag the errorStatusFlag to set
	 */
	public void setErrorStatusFlag(String errorStatusFlag) {
		this.errorStatusFlag = errorStatusFlag;
	}
	public String getFromPartyType() {
		return fromPartyType;
	}
	public void setFromPartyType(String fromPartyType) {
		this.fromPartyType = fromPartyType;
	}
	public String getFromPartyName() {
		return fromPartyName;
	}
	public void setFromPartyName(String fromPartyName) {
		this.fromPartyName = fromPartyName;
	}
	public String getFromPartyCode() {
		return fromPartyCode;
	}
	public void setFromPartyCode(String fromPartyCode) {
		this.fromPartyCode = fromPartyCode;
	}
	public boolean isFromAllParties() {
		return isFromAllParties;
	}
	public void setFromAllParties(boolean isFromAllParties) {
		this.isFromAllParties = isFromAllParties;
	}
	public String getTypeOfParty() {
		return typeOfParty;
	}
	public void setTypeOfParty(String typeOfParty) {
		this.typeOfParty = typeOfParty;
	}
	public String getDisplayPageNumStr() {
		return displayPageNumStr;
	}
	public void setDisplayPageNumStr(String displayPageNumStr) {
		this.displayPageNumStr = displayPageNumStr;
	}
	public String getLastPageNumStr() {
		return lastPageNumStr;
	}
	public void setLastPageNumStr(String lastPageNumStr) {
		this.lastPageNumStr = lastPageNumStr;
	}
}
