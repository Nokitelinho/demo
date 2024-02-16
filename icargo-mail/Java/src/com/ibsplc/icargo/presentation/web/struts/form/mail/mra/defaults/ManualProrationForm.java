/*
 *  ManualProrationForm.java Created on June 19, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.currency.Money;

/**
 * @author A-3434
 *
 */
public class ManualProrationForm extends ScreenModel{

	private static final String BUNDLE = "manualprorationresources";

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.manualproration";
	private String companyCode;
	private String despatchSerialNumber;
	//pks
	private String billingBasis;
	private String consigneeDocumentNumber;
	private String consigneeSequenceNumber;
	private String poaCode;
	
	private String flightCarrierIdentifier;
	private String flightNumber;
	private String flightDate;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String postalAuthorityCode;
	private String postalAuthorityName;
	private String mailCategoryCode;
	private String mailSubclass;
	private String totalWeight;
	
	//For Primary Table
	private String[] carrierCodeForPri;
	private String[] weightForPri;
	private String[] numberOfPiecesForPri;
	private String[] prorationTypeForPri;
	private String[] prorationFactorForPri;
	private String[] prorationPercentageForPri;
	private String[] payableFlagForPri;
	private String[] prorationAmtInUsdForPri;
	private String[] prorationAmtInSdrForPri;
	private String[] prorationAmtInBaseCurrForPri;
	private String[] proratedAmtInCtrCurForPri;
	private String[] sectorStatusForPri;
	private String[] sectorFromForPri;
	private String[] sectorToForPri;
	
	//For Secondary Table
	private String[] carrierCodeForSec;
	private String[] weightForSec;
	private String[] numberOfPiecesForSec;
	private String[] prorationTypeForSec;
	private String[] prorationFactorForSec;
	private String[] prorationPercentageForSec;
	private String[] payableFlagForSec;
	private String[] prorationAmtInUsdForSec;
	private String[] prorationAmtInSdrForSec;
	private String[] prorationAmtInBaseCurrForSec;
	private String[] proratedAmtInCtrCurForSec;
	private String[] sectorStatusForSec;
	private String[] sectorFromForSec;
	private String[] sectorToForSec;
	
	//For Total Fields
	private String totalWtForPri;
	private String totalInUsdForPri;
	private String totalInBasForPri;
	private String totalInSdrForPri;
	private String totalInCurForPri;
	private String totalWtForSec;
	private String totalInUsdForSec;
	private String totalInBasForSec;
	private String totalInSdrForSec;
	private String totalInCurForSec;
	//hidden fields for total
	private String totalWtForPrimary;
	private String totalInUsdForPrimary;
	private String totalInBasForPrimary;
	private String totalInSdrForPrimary;
	private String totalInCurForPrimary;
	private String totalWtForSecon;
	private String totalInUsdForSecon;
	private String totalInBasForSecon;
	private String totalInSdrForSecon;
	private String totalInCurForSecon;
	//Flags
	private String prorateFlag;
	private String[] rowCountForPri;
	private String[] rowCountForSec;
	
	//For template row
	
	private String[] hiddenOpFlagForPri;
	private String[] hiddenOpFlagForSec;
	private String checkBox;
	private String selectedValues;
	
	//	 added for displaying dsn from lov
	private String dsn;
	
	//for weight comparison
	
	private String primaryWeight;
	
	private String secondaryWeight;
	
	private String prorated;
	
	private String fromScreen;
	
	private String closeFlag;
	private String validateFrom;
	
	
	
	//for money 
	private Money prorationAmtInUsdForPrimary;
	private Money prorationAmtInSdrForPrimary;
	private Money prorationAmtInBaseCurrForPrimary;
	private Money proratedAmtInCtrCurForPrimary;
	private Money prorationAmtInUsdForSecondary;
	private Money prorationAmtInSdrForSecondary;
	private Money prorationAmtInBaseCurrForSecondary;
	private Money proratedAmtInCtrCurForSecondary;
	//Added By Deepthi for DSN pop up
	private String showDsnPopUp;
	private Double oldWt;
	
	/**
	 * @return Returns the oldWt.
	 */
	public Double getOldWt() {
		return oldWt;
	}

	/**
	 * @param oldWt The oldWt to set.
	 */
	public void setOldWt(Double oldWt) {
		this.oldWt = oldWt;
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
	 * @return Returns the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the consigneeDocumentNumber
	 */
	public String getConsigneeDocumentNumber() {
		return consigneeDocumentNumber;
	}

	/**
	 * @param consigneeDocumentNumber the consigneeDocumentNumber to set.
	 */
	public void setConsigneeDocumentNumber(String consigneeDocumentNumber) {
		this.consigneeDocumentNumber = consigneeDocumentNumber;
	}

	/**
	 * @return Returns the despatchSerialNumber
	 */
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	/**
	 * @param despatchSerialNumber the despatchSerialNumber to set.
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	/**
	 * @return Returns the destinationExchangeOffice
	 */
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}
	/**
	 * @param destinationExchangeOffice the destinationExchangeOffice to set.
	 */

	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}

	/**
	 * @return Returns the flightCarrierIdentifier
	 */
	public String getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}
	/**
	 * @param flightCarrierIdentifier the flightCarrierIdentifier to set.
	 */

	public void setFlightCarrierIdentifier(String flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}

	/**
	 * @return Returns the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set.
	 */

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set.
	 */

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}



	/**
	 * @return Returns the mailCategoryCode
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode the mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubclass
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}
	/**
	 * @param mailSubclass the mailSubclass to set.
	 */

	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}



	/**
	 * @return Returns the originExchangeOffice
	 */
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}
	/**
	 * @param originExchangeOffice the originExchangeOffice to set.
	 */

	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	

	/**
	 * @return Returns the postalAuthorityCode
	 */
	public String getPostalAuthorityCode() {
		return postalAuthorityCode;
	}

	/**
	 * @param postalAuthorityCode the postalAuthorityCode to set.
	 */
	public void setPostalAuthorityCode(String postalAuthorityCode) {
		this.postalAuthorityCode = postalAuthorityCode;
	}

	/**
	 * @return Returns the postalAuthorityName
	 */
	public String getPostalAuthorityName() {
		return postalAuthorityName;
	}
	/**
	 * @param postalAuthorityName the postalAuthorityName to set.
	 */

	public void setPostalAuthorityName(String postalAuthorityName) {
		this.postalAuthorityName = postalAuthorityName;
	}


	/**
	 * @return Returns the prorateFlag
	 */

	public String getProrateFlag() {
		return prorateFlag;
	}
	
	/**
	 * @param prorateFlag the prorateFlag to set.
	 */

	public void setProrateFlag(String prorateFlag) {
		this.prorateFlag = prorateFlag;
	}

	



	/**
	 * @return Returns the checkBox
	 */
	public String getCheckBox() {
		return checkBox;
	}

	/**
	 * @param checkBox the checkBox to set.
	 */
	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}
	/**
	 * @return Returns the carrierCode
	 */

	public String getSelectedValues() {
		return selectedValues;
	}

	/**
	 * @param selectedValues the selectedValues to set.
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}
	/**
	 * @return Returns the totalWeight
	 */

	public String  getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight the totalWeight to set.
	 */
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	

	/**
	 * @return Returns the hiddenOpFlagForPri
	 */
	public String[] getHiddenOpFlagForPri() {
		return hiddenOpFlagForPri;
	}

	/**
	 * @param hiddenOpFlagForPri the hiddenOpFlagForPri to set.
	 */
	public void setHiddenOpFlagForPri(String[] hiddenOpFlagForPri) {
		this.hiddenOpFlagForPri = hiddenOpFlagForPri;
	}
	/**
	 * @return Returns the hiddenOpFlagForSec
	 */

	public String[] getHiddenOpFlagForSec() {
		return hiddenOpFlagForSec;
	}
	/**
	 * @param hiddenOpFlagForSec the hiddenOpFlagForSec to set.
	 */

	public void setHiddenOpFlagForSec(String[] hiddenOpFlagForSec) {
		this.hiddenOpFlagForSec = hiddenOpFlagForSec;
	}

	/**
	 * @return Returns the proratedAmtInCtrCurForPri
	 */
	public String[] getProratedAmtInCtrCurForPri() {
		return proratedAmtInCtrCurForPri;
	}

	/**
	 * @param proratedAmtInCtrCurForPri the proratedAmtInCtrCurForPri to set.
	 */
	public void setProratedAmtInCtrCurForPri(String[] proratedAmtInCtrCurForPri) {
		this.proratedAmtInCtrCurForPri = proratedAmtInCtrCurForPri;
	}
	/**
	 * @return Returns the proratedAmtInCtrCurForSec
	 */
	
	public String[] getProratedAmtInCtrCurForSec() {
		return proratedAmtInCtrCurForSec;
	}

	/**
	 * @param proratedAmtInCtrCurForSec the proratedAmtInCtrCurForSec to set.
	 */
	public void setProratedAmtInCtrCurForSec(String[] proratedAmtInCtrCurForSec) {
		this.proratedAmtInCtrCurForSec = proratedAmtInCtrCurForSec;
	}

	/**
	 * @return Returns the prorationAmtInBaseCurrForPri
	 */
	public String[] getProrationAmtInBaseCurrForPri() {
		return prorationAmtInBaseCurrForPri;
	}

	/**
	 * @param prorationAmtInBaseCurrForPri the prorationAmtInBaseCurrForPri to set.
	 */
	public void setProrationAmtInBaseCurrForPri(
			String[] prorationAmtInBaseCurrForPri) {
		this.prorationAmtInBaseCurrForPri = prorationAmtInBaseCurrForPri;
	}
	/**
	 * @return Returns the prorationAmtInBaseCurrForSec
	 */
	
	public String[] getProrationAmtInBaseCurrForSec() {
		return prorationAmtInBaseCurrForSec;
	}

	/**
	 * @param prorationAmtInBaseCurrForSec the prorationAmtInBaseCurrForSec to set.
	 */
	public void setProrationAmtInBaseCurrForSec(
			String[] prorationAmtInBaseCurrForSec) {
		this.prorationAmtInBaseCurrForSec = prorationAmtInBaseCurrForSec;
	}
	/**
	 * @return Returns the prorationAmtInSdrForPri
	 */
	
	public String[] getProrationAmtInSdrForPri() {
		return prorationAmtInSdrForPri;
	}

	/**
	 * @param prorationAmtInSdrForPri the prorationAmtInSdrForPri to set.
	 */
	public void setProrationAmtInSdrForPri(String[] prorationAmtInSdrForPri) {
		this.prorationAmtInSdrForPri = prorationAmtInSdrForPri;
	}

	/**
	 * @return Returns the prorationAmtInSdrForSec
	 */
	public String[] getProrationAmtInSdrForSec() {
		return prorationAmtInSdrForSec;
	}

	/**
	 * @param prorationAmtInSdrForSec the prorationAmtInSdrForSec to set.
	 */
	public void setProrationAmtInSdrForSec(String[] prorationAmtInSdrForSec) {
		this.prorationAmtInSdrForSec = prorationAmtInSdrForSec;
	}
	
	/**
	 * @return Returns the prorationAmtInUsdForPri
	 */

	public String[] getProrationAmtInUsdForPri() {
		return prorationAmtInUsdForPri;
	}

	/**
	 * @param prorationAmtInUsdForPri the prorationAmtInUsdForPri to set.
	 */
	public void setProrationAmtInUsdForPri(String[] prorationAmtInUsdForPri) {
		this.prorationAmtInUsdForPri = prorationAmtInUsdForPri;
	}

	/**
	 * @return Returns the prorationAmtInUsdForSec
	 */
	public String[] getProrationAmtInUsdForSec() {
		return prorationAmtInUsdForSec;
	}

	/**
	 * @param prorationAmtInUsdForSec the prorationAmtInUsdForSec to set.
	 */
	public void setProrationAmtInUsdForSec(String[] prorationAmtInUsdForSec) {
		this.prorationAmtInUsdForSec = prorationAmtInUsdForSec;
	}

	/**
	 * @return Returns the prorationAmtInUsdForSec
	 */
	public String[] getProrationFactorForPri() {
		return prorationFactorForPri;
	}

	/**
	 * @param hiddenOpFlagForSec the hiddenOpFlagForSec to set.
	 */
	public void setProrationFactorForPri(String[] prorationFactorForPri) {
		this.prorationFactorForPri = prorationFactorForPri;
	}
	/**
	 * @return Returns the prorationAmtInUsdForSec
	 */
	public String[] getProrationFactorForSec() {
		return prorationFactorForSec;
	}

	/**
	 * @param hiddenOpFlagForSec the hiddenOpFlagForSec to set.
	 */
	public void setProrationFactorForSec(String[] prorationFactorForSec) {
		this.prorationFactorForSec = prorationFactorForSec;
	}
	/**
	 * @return Returns the prorationAmtInUsdForSec
	 */
	public String[] getProrationPercentageForPri() {
		return prorationPercentageForPri;
	}

	/**
	 * @param hiddenOpFlagForSec the hiddenOpFlagForSec to set.
	 */
	public void setProrationPercentageForPri(String[] prorationPercentageForPri) {
		this.prorationPercentageForPri = prorationPercentageForPri;
	}
	/**
	 * @return Returns the prorationPercentageForSec
	 */
	public String[] getProrationPercentageForSec() {
		return prorationPercentageForSec;
	}
	/**
	 * @param prorationPercentageForSec the prorationPercentageForSec to set.
	 */

	public void setProrationPercentageForSec(String[] prorationPercentageForSec) {
		this.prorationPercentageForSec = prorationPercentageForSec;
	}
	/**
	 * @return Returns the prorationTypeForPri
	 */
	public String[] getProrationTypeForPri() {
		return prorationTypeForPri;
	}

	/**
	 * @param prorationTypeForPri the prorationTypeForPri to set.
	 */
	public void setProrationTypeForPri(String[] prorationTypeForPri) {
		this.prorationTypeForPri = prorationTypeForPri;
	}
	/**
	 * @return Returns the prorationTypeForSec
	 */
	public String[] getProrationTypeForSec() {
		return prorationTypeForSec;
	}
	
	/**
	 * @param prorationTypeForSec the prorationTypeForSec to set.
	 */

	public void setProrationTypeForSec(String[] prorationTypeForSec) {
		this.prorationTypeForSec = prorationTypeForSec;
	}
	/**
	 * @return Returns the sectorFromForPri
	 */
	public String[] getSectorFromForPri() {
		return sectorFromForPri;
	}
	
	/**
	 * @param sectorFromForPri the sectorFromForPris to set.
	 */

	public void setSectorFromForPri(String[] sectorFromForPri) {
		this.sectorFromForPri = sectorFromForPri;
	}
	/**
	 * @return Returns the sectorFromForSec
	 */
	public String[] getSectorFromForSec() {
		return sectorFromForSec;
	}

	/**
	 * @param sectorFromForSec the sectorFromForSec to set.
	 */
	public void setSectorFromForSec(String[] sectorFromForSec) {
		this.sectorFromForSec = sectorFromForSec;
	}
	/**
	 * @return Returns the sectorStatusForPri
	 */
	public String[] getSectorStatusForPri() {
		return sectorStatusForPri;
	}

	/**
	 * @param sectorStatusForPri the sectorStatusForPri to set.
	 */
	public void setSectorStatusForPri(String[] sectorStatusForPri) {
		this.sectorStatusForPri = sectorStatusForPri;
	}
	/**
	 * @return Returns the sectorStatusForSec
	 */
	public String[] getSectorStatusForSec() {
		return sectorStatusForSec;
	}

	/**
	 * @param sectorStatusForSec the sectorStatusForSec to set.
	 */
	public void setSectorStatusForSec(String[] sectorStatusForSec) {
		this.sectorStatusForSec = sectorStatusForSec;
	}
	/**
	 * @return Returns the sectorToForPri
	 */
	public String[] getSectorToForPri() {
		return sectorToForPri;
	}

	/**
	 * @param sectorToForPri the sectorToForPri to set.
	 */
	public void setSectorToForPri(String[] sectorToForPri) {
		this.sectorToForPri = sectorToForPri;
	}
	/**
	 * @return Returns the sectorToForSec
	 */
	public String[] getSectorToForSec() {
		return sectorToForSec;
	}

	/**
	 * @param sectorToForSec the sectorToForSec to set.
	 */
	public void setSectorToForSec(String[] sectorToForSec) {
		this.sectorToForSec = sectorToForSec;
	}
	/**
	 * @return Returns the weightForPri
	 */
	public String[] getWeightForPri() {
		return weightForPri;
	}

	/**
	 * @param weightForPri the weightForPri to set.
	 */
	public void setWeightForPri(String[] weightForPri) {
		this.weightForPri = weightForPri;
	}
	/**
	 * @return Returns the weightForSec
	 */
	public String[] getWeightForSec() {
		return weightForSec;
	}
	/**
	 * @param weightForSec the weightForSec to set.
	 */

	public void setWeightForSec(String[] weightForSec) {
		this.weightForSec = weightForSec;
	}
	/**
	 * @return Returns the carrierCodeForPri
	 */
	public String[] getCarrierCodeForPri() {
		return carrierCodeForPri;
	}

	/**
	 * @param carrierCodeForPri the carrierCodeForPri to set.
	 */
	public void setCarrierCodeForPri(String[] carrierCodeForPri) {
		this.carrierCodeForPri = carrierCodeForPri;
	}
	/**
	 * @return Returns the carrierCodeForSec
	 */
	public String[] getCarrierCodeForSec() {
		return carrierCodeForSec;
	}

	/**
	 * @param carrierCodeForSec the carrierCodeForSec to set.
	 */
	public void setCarrierCodeForSec(String[] carrierCodeForSec) {
		this.carrierCodeForSec = carrierCodeForSec;
	}
	/**
	 * @return Returns the numberOfPiecesForPri
	 */
	public String[] getNumberOfPiecesForPri() {
		return numberOfPiecesForPri;
	}

	/**
	 * @param numberOfPiecesForPri the numberOfPiecesForPri to set.
	 */
	public void setNumberOfPiecesForPri(String[] numberOfPiecesForPri) {
		this.numberOfPiecesForPri = numberOfPiecesForPri;
	}
	/**
	 * @return Returns the numberOfPiecesForPri
	 */
	public String[] getNumberOfPiecesForSec() {
		return numberOfPiecesForSec;
	}

	/**
	 * @param numberOfPiecesForSec the numberOfPiecesForSec to set.
	 */
	public void setNumberOfPiecesForSec(String[] numberOfPiecesForSec) {
		this.numberOfPiecesForSec = numberOfPiecesForSec;
	}
	/**
	 * @return Returns the payableFlagForPri
	 */
	public String[] getPayableFlagForPri() {
		return payableFlagForPri;
	}

	/**
	 * @param payableFlagForPri the payableFlagForPri to set.
	 */
	public void setPayableFlagForPri(String[] payableFlagForPri) {
		this.payableFlagForPri = payableFlagForPri;
	}
	/**
	 * @return Returns the payableFlagForSec
	 */
	public String[] getPayableFlagForSec() {
		return payableFlagForSec;
	}

	/**
	 * @param payableFlagForSec the payableFlagForSec to set.
	 */
	public void setPayableFlagForSec(String[] payableFlagForSec) {
		this.payableFlagForSec = payableFlagForSec;
	}
	/**
	 * @return Returns the totalInBasForPri
	 */
	public String getTotalInBasForPri() {
		return totalInBasForPri;
	}

	/**
	 * @param totalInBasForPri the totalInBasForPri to set.
	 */
	public void setTotalInBasForPri(String totalInBasForPri) {
		this.totalInBasForPri = totalInBasForPri;
	}
	/**
	 * @return Returns the totalInBasForSec
	 */
	public String getTotalInBasForSec() {
		return totalInBasForSec;
	}

	/**
	 * @param totalInBasForSec the totalInBasForSec to set.
	 */
	public void setTotalInBasForSec(String totalInBasForSec) {
		this.totalInBasForSec = totalInBasForSec;
	}
	/**
	 * @return Returns the totalInCurForPri
	 */
	public String getTotalInCurForPri() {
		return totalInCurForPri;
	}
	/**
	 * @param totalInCurForPri the totalInCurForPri to set.
	 */

	public void setTotalInCurForPri(String totalInCurForPri) {
		this.totalInCurForPri = totalInCurForPri;
	}
	/**
	 * @return Returns the totalInCurForSec
	 */
	public String getTotalInCurForSec() {
		return totalInCurForSec;
	}

	/**
	 * @param totalInCurForSec the totalInCurForSec to set.
	 */
	public void setTotalInCurForSec(String totalInCurForSec) {
		this.totalInCurForSec = totalInCurForSec;
	}
	/**
	 * @return Returns the totalInSdrForPri
	 */
	public String getTotalInSdrForPri() {
		return totalInSdrForPri;
	}

	/**
	 * @param totalInSdrForPri the totalInSdrForPri to set.
	 */
	public void setTotalInSdrForPri(String totalInSdrForPri) {
		this.totalInSdrForPri = totalInSdrForPri;
	}
	/**
	 * @return Returns the totalInSdrForSec
	 */
	public String getTotalInSdrForSec() {
		return totalInSdrForSec;
	}

	/**
	 * @param totalInSdrForSec the totalInSdrForSec to set.
	 */
	public void setTotalInSdrForSec(String totalInSdrForSec) {
		this.totalInSdrForSec = totalInSdrForSec;
	}
	/**
	 * @return Returns the totalInUsdForPri
	 */
	public String getTotalInUsdForPri() {
		return totalInUsdForPri;
	}

	/**
	 * @param totalInUsdForPri the totalInUsdForPri to set.
	 */
	public void setTotalInUsdForPri(String totalInUsdForPri) {
		this.totalInUsdForPri = totalInUsdForPri;
	}
	/**
	 * @return Returns the totalInUsdForSec
	 */
	public String getTotalInUsdForSec() {
		return totalInUsdForSec;
	}

	/**
	 * @param totalInUsdForSec the totalInUsdForSec to set.
	 */
	public void setTotalInUsdForSec(String totalInUsdForSec) {
		this.totalInUsdForSec = totalInUsdForSec;
	}
	/**
	 * @return Returns the totalWtForPri
	 */
	public String getTotalWtForPri() {
		return totalWtForPri;
	}

	/**
	 * @param totalWtForPri the totalWtForPri to set.
	 */
	public void setTotalWtForPri(String totalWtForPri) {
		this.totalWtForPri = totalWtForPri;
	}
	/**
	 * @return Returns the totalWtForSec
	 */
	public String getTotalWtForSec() {
		return totalWtForSec;
	}

	/**
	 * @param totalWtForSec the totalWtForSec to set.
	 */
	public void setTotalWtForSec(String totalWtForSec) {
		this.totalWtForSec = totalWtForSec;
	}
	


	/**
	 * @return Returns the rowCountForPri
	 */
	public String[] getRowCountForPri() {
		return rowCountForPri;
	}
	
	/**
	 * @param rowCountForPri the rowCountForPri to set.
	 */

	public void setRowCountForPri(String[] rowCountForPri) {
		this.rowCountForPri = rowCountForPri;
	}
	/**
	 * @return Returns the rowCountForSec
	 */
	public String[] getRowCountForSec() {
		return rowCountForSec;
	}

	/**
	 * @param rowCountForSec the rowCountForSec to set.
	 */
	public void setRowCountForSec(String[] rowCountForSec) {
		this.rowCountForSec = rowCountForSec;
	}
	/**
	 * @return Returns the totalInBasForPrimary
	 */
	public String getTotalInBasForPrimary() {
		return totalInBasForPrimary;
	}

	/**
	 * @param totalInBasForPrimary the totalInBasForPrimary to set.
	 */
	public void setTotalInBasForPrimary(String totalInBasForPrimary) {
		this.totalInBasForPrimary = totalInBasForPrimary;
	}
	/**
	 * @return Returns the totalInCurForPrimary
	 */
	public String getTotalInCurForPrimary() {
		return totalInCurForPrimary;
	}
	/**
	 * @param totalInCurForPrimary the totalInCurForPrimary to set.
	 */

	public void setTotalInCurForPrimary(String totalInCurForPrimary) {
		this.totalInCurForPrimary = totalInCurForPrimary;
	}
	/**
	 * @return Returns the totalInSdrForPrimary
	 */
	public String getTotalInSdrForPrimary() {
		return totalInSdrForPrimary;
	}

	/**
	 * @param totalInSdrForPrimary the totalInSdrForPrimary to set.
	 */
	public void setTotalInSdrForPrimary(String totalInSdrForPrimary) {
		this.totalInSdrForPrimary = totalInSdrForPrimary;
	}
	/**
	 * @return Returns the totalInUsdForPrimary
	 */
	public String getTotalInUsdForPrimary() {
		return totalInUsdForPrimary;
	}
	/**
	 * @param totalInUsdForPrimary the totalInUsdForPrimary to set.
	 */

	public void setTotalInUsdForPrimary(String totalInUsdForPrimary) {
		this.totalInUsdForPrimary = totalInUsdForPrimary;
	}
	/**
	 * @return Returns the totalWtForPrimary
	 */
	public String getTotalWtForPrimary() {
		return totalWtForPrimary;
	}
	/**
	 * @param totalWtForPrimary the totalWtForPrimary to set.
	 */

	public void setTotalWtForPrimary(String totalWtForPrimary) {
		this.totalWtForPrimary = totalWtForPrimary;
	}
	/**
	 * @return Returns the totalInBasForSecon
	 */
	public String getTotalInBasForSecon() {
		return totalInBasForSecon;
	}

	/**
	 * @param totalInBasForSecon the totalInBasForSecon to set.
	 */
	public void setTotalInBasForSecon(String totalInBasForSecon) {
		this.totalInBasForSecon = totalInBasForSecon;
	}
	/**
	 * @return Returns the totalInCurForSecon
	 */
	public String getTotalInCurForSecon() {
		return totalInCurForSecon;
	}

	/**
	 * @param totalInCurForSecon the totalInCurForSecon to set.
	 */
	public void setTotalInCurForSecon(String totalInCurForSecon) {
		this.totalInCurForSecon = totalInCurForSecon;
	}
	/**
	 * @return Returns the totalInSdrForSecon
	 */
	public String getTotalInSdrForSecon() {
		return totalInSdrForSecon;
	}

	/**
	 * @param totalInSdrForSecon the totalInSdrForSecon to set.
	 */
	public void setTotalInSdrForSecon(String totalInSdrForSecon) {
		this.totalInSdrForSecon = totalInSdrForSecon;
	}
	/**
	 * @return Returns the totalInUsdForSecon
	 */
	public String getTotalInUsdForSecon() {
		return totalInUsdForSecon;
	}

	/**
	 * @param totalInUsdForSecon the totalInUsdForSecon to set.
	 */
	public void setTotalInUsdForSecon(String totalInUsdForSecon) {
		this.totalInUsdForSecon = totalInUsdForSecon;
	}
	/**
	 * @return Returns the totalWtForSecon
	 */
	public String getTotalWtForSecon() {
		return totalWtForSecon;
	}

	/**
	 * @param totalWtForSecon the totalWtForSecon to set.
	 */
	public void setTotalWtForSecon(String totalWtForSecon) {
		this.totalWtForSecon = totalWtForSecon;
	}

	/**
	 * @return Returns the dsn
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn the dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the dsn
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis the billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return Returns the consigneeSequenceNumber
	 */
	public String getConsigneeSequenceNumber() {
		return consigneeSequenceNumber;
	}
	/**
	 * @param consigneeSequenceNumber the consigneeSequenceNumber to set.
	 */

	public void setConsigneeSequenceNumber(String consigneeSequenceNumber) {
		this.consigneeSequenceNumber = consigneeSequenceNumber;
	}

	/**
	 * @return Returns the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}
	/**
	 * @param poaCode the poaCode to set.
	 */

	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the primaryWeight
	 */
	public String getPrimaryWeight() {
		return primaryWeight;
	}

	/**
	 * @param primaryWeight the primaryWeight to set.
	 */
	public void setPrimaryWeight(String primaryWeight) {
		this.primaryWeight = primaryWeight;
	}

	/**
	 * @return Returns the secondaryWeight
	 */
	public String getSecondaryWeight() {
		return secondaryWeight;
	}

	/**
	 * @param secondaryWeight the secondaryWeight to set.
	 */
	public void setSecondaryWeight(String secondaryWeight) {
		this.secondaryWeight = secondaryWeight;
	}

	/**
	 * @return the prorated
	 */
	public String getProrated() {
		return prorated;
	}

	/**
	 * @param prorated the prorated to set
	 */
	public void setProrated(String prorated) {
		this.prorated = prorated;
	}

	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return the validateFrom
	 */
	public String getValidateFrom() {
		return validateFrom;
	}

	/**
	 * @param validateFrom the validateFrom to set
	 */
	public void setValidateFrom(String validateFrom) {
		this.validateFrom = validateFrom;
	}

	/**
	 * @return the proratedAmtInCtrCurForPrimary
	 */
	public Money getProratedAmtInCtrCurForPrimary() {
		return proratedAmtInCtrCurForPrimary;
	}

	/**
	 * @param proratedAmtInCtrCurForPrimary the proratedAmtInCtrCurForPrimary to set
	 */
	public void setProratedAmtInCtrCurForPrimary(Money proratedAmtInCtrCurForPrimary) {
		this.proratedAmtInCtrCurForPrimary = proratedAmtInCtrCurForPrimary;
	}

	/**
	 * @return the proratedAmtInCtrCurForSecondary
	 */
	public Money getProratedAmtInCtrCurForSecondary() {
		return proratedAmtInCtrCurForSecondary;
	}

	/**
	 * @param proratedAmtInCtrCurForSecondary the proratedAmtInCtrCurForSecondary to set
	 */
	public void setProratedAmtInCtrCurForSecondary(
			Money proratedAmtInCtrCurForSecondary) {
		this.proratedAmtInCtrCurForSecondary = proratedAmtInCtrCurForSecondary;
	}

	/**
	 * @return the prorationAmtInBaseCurrForPrimary
	 */
	public Money getProrationAmtInBaseCurrForPrimary() {
		return prorationAmtInBaseCurrForPrimary;
	}
	/**
	 * @param prorationAmtInBaseCurrForPrimary the prorationAmtInBaseCurrForPrimary to set
	 */

	public void setProrationAmtInBaseCurrForPrimary(
			Money prorationAmtInBaseCurrForPrimary) {
		this.prorationAmtInBaseCurrForPrimary = prorationAmtInBaseCurrForPrimary;
	}

	/**
	 * @return the prorationAmtInBaseCurrForSecondary
	 */
	public Money getProrationAmtInBaseCurrForSecondary() {
		return prorationAmtInBaseCurrForSecondary;
	}

	/**
	 * @param prorationAmtInBaseCurrForSecondary the prorationAmtInBaseCurrForSecondary to set
	 */
	public void setProrationAmtInBaseCurrForSecondary(
			Money prorationAmtInBaseCurrForSecondary) {
		this.prorationAmtInBaseCurrForSecondary = prorationAmtInBaseCurrForSecondary;
	}

	/**
	 * @return the prorationAmtInSdrForPrimary
	 */
	public Money getProrationAmtInSdrForPrimary() {
		return prorationAmtInSdrForPrimary;
	}

	/**
	 * @param prorationAmtInSdrForPrimary the prorationAmtInSdrForPrimary to set
	 */
	public void setProrationAmtInSdrForPrimary(Money prorationAmtInSdrForPrimary) {
		this.prorationAmtInSdrForPrimary = prorationAmtInSdrForPrimary;
	}

	/**
	 * @return the prorationAmtInSdrForSecondary
	 */
	public Money getProrationAmtInSdrForSecondary() {
		return prorationAmtInSdrForSecondary;
	}

	/**
	 * @param prorationAmtInSdrForSecondary the prorationAmtInSdrForSecondary to set
	 */
	public void setProrationAmtInSdrForSecondary(Money prorationAmtInSdrForSecondary) {
		this.prorationAmtInSdrForSecondary = prorationAmtInSdrForSecondary;
	}
	/**
	 * @return the prorationAmtInUsdForPrimary
	 */
	public Money getProrationAmtInUsdForPrimary() {
		return prorationAmtInUsdForPrimary;
	}

	/**
	 * @param prorationAmtInUsdForPrimary the prorationAmtInUsdForPrimary to set
	 */
	public void setProrationAmtInUsdForPrimary(Money prorationAmtInUsdForPrimary) {
		this.prorationAmtInUsdForPrimary = prorationAmtInUsdForPrimary;
	}
	/**
	 * @return the prorationAmtInUsdForSecondary
	 */

	public Money getProrationAmtInUsdForSecondary() {
		return prorationAmtInUsdForSecondary;
	}

	/**
	 * @param prorationAmtInUsdForSecondary the prorationAmtInUsdForSecondary to set
	 */
	public void setProrationAmtInUsdForSecondary(Money prorationAmtInUsdForSecondary) {
		this.prorationAmtInUsdForSecondary = prorationAmtInUsdForSecondary;
	}

	/**
	 * @return the showDsnPopUp
	 */
	public String getShowDsnPopUp() {
		return showDsnPopUp;
	}

	/**
	 * @param showDsnPopUp the showDsnPopUp to set
	 */
	public void setShowDsnPopUp(String showDsnPopUp) {
		this.showDsnPopUp = showDsnPopUp;
	}

	

	}
