/*
 * BillingLineForm.java Created on Feb 28, 2007
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2398
 *
 */
public class BillingLineForm extends ScreenModel{

private static final String BUNDLE = "billingline";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID =
		"mailtracking.mra.defaults.maintainbillingmatrix";

	
	
	private String orgRegInc;
	
	private String orgRegExc;
	
	private String orgCntInc;
	
	private String orgCntExc;
	
	private String orgCtyInc;
	
	private String orgCtyExc;
	
	private String uplCntInc;
	
	private String uplCntExc;
	
	private String uplCtyInc;
	
	private String uplCtyExc;
	
	private String fltNumInc;
	
	private String fltNumExc;
	
	private String disCntInc;
	
	private String disCntExc;
	
	private String disCtyInc;
	
	private String disCtyExc;
	
	private String desRegInc;
	
	private String desRegExc;
	
	private String desCtyInc;
	
	private String desCtyExc;
	
	private String desCntInc;
	
	private String desCntExc;
	
	private String catInc;
	
	private String catExc;
	
	private String classInc;
	
	private String classExc;
	
	private String subClsInc;
	
	private String subClsExc;
	
	private String uldTypInc;
	
	private String uldTypExc;
	
	private String transferedByInc;
	
	private String transferedByExc;
	
	private String revExp;
	
	private String billBy;
	
	private String billTo;
	
	private String billedSector;
	
	//private String rate;
	
	private String currency;
	
	private String blgLineValidFrom;
	
	private String blgLineValidTo;
	
	private String selectedIndex;
	
	private String actionType;
	
	private String canClose;
	
	private String isModified;
	
	private String billingParty;
	
	private String blgPtyType;
	
	private String frmDate;
	
	private String toDate;
	
	private String reFlag;
	
	private String disableButton;
	
	private String subClassInc;
	
	private String subClassExc;
	
	private String transferedPAInc;
	
	private String transferedPAExc;
	//Added for CRQ 12578
	private String unitCode;
	
	//Added by A-4809 for BUG ICRD-18562
	private String carrierCodeInc;
	private String flightNumberInc;
	private String carriercodeExc;
	private String flightNumberExc;
	private String restrictionFlag;
	private String mailCompanyInc;
	private String mailCompanyExc;
	private String specificFlag;
	
	private String chargeHead;
	private String chargeTypeMail;
	private String[] wbIndexMail;
	private String[] wbFrmWgtMail;
	private String[] wbApplicableRateMail;
	private String minimumChargeOther;
	private String normalRateOther;
	private String[] wbIndexOther;
	private String[] wbFrmWgtOther;
	private String[] wbApplicableRateOther;
	private String flatRateMail;
	private String flatRateOther;
	private String flatChargeMail;
	private String flatChargeOther;
	private String ratingBasisMail;
	private String chargeTypeOther;
	private String ratingBasisOther;
	private String minimumChargeMail;
	private String normalRateMail;
	private String prevoiusChargeHead;
	private String billingBasisMail;
	private String billingBasisOther;
	private String billingBasis;
	private String[] hiddenRateOpFlag;
	private String rateAction;
	//added by a-7531 for icrd-224979
	private String agentCodeInc;
	private String agentCodeExc;
		
	private String overrideRounding;//added by a-7871 for ICRD-214766
	
	private String roundingValue;//added by a-7871 for ICRD-214766

	//Added by A-7540 for ICRD-232319
		private String orgAirportInc;
		private String orgAirportExc;
		private String desAirportInc;
		private String desAirportExc;
		private String viaPointInc;
		private String viaPointExc;
		private String mailServiceExc;
		private String mailServiceInc;
		private String paBuilt;	
		private Double conDiscount;
		private Double uspsRateOne=0.0;
		private Double uspsRateTwo=0.0;
		private Double uspsRateThr=0.0;
		private Double uspsRateFour=0.0;
		private Double uspsTot=0.0;
		
		private String uplArpInc;
		private String uplArpExc;
		private String disArpInc;
		private String disArpExc;
		
		private String flownCarrierInc;
		private String flownCarrierExc;
		
		
	/**
	 * @return the roundingValue
	 */
	public String getRoundingValue() {
		return roundingValue;
	}

	/**
	 * @param roundingValue the roundingValue to set
	 */
	public void setRoundingValue(String roundingValue) {
		this.roundingValue = roundingValue;
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
	 * @return the carrierCodeInc
	 */
	public String getCarrierCodeInc() {
		return carrierCodeInc;
	}

	/**
	 * @param carrierCodeInc the carrierCodeInc to set
	 */
	public void setCarrierCodeInc(String carrierCodeInc) {
		this.carrierCodeInc = carrierCodeInc;
	}

	/**
	 * @return the flightNumberInc
	 */
	public String getFlightNumberInc() {
		return flightNumberInc;
	}

	/**
	 * @param flightNumberInc the flightNumberInc to set
	 */
	public void setFlightNumberInc(String flightNumberInc) {
		this.flightNumberInc = flightNumberInc;
	}

	/**
	 * @return the carriercodeExc
	 */
	public String getCarriercodeExc() {
		return carriercodeExc;
	}

	/**
	 * @param carriercodeExc the carriercodeExc to set
	 */
	public void setCarriercodeExc(String carriercodeExc) {
		this.carriercodeExc = carriercodeExc;
	}

	/**
	 * @return the flightNumberExc
	 */
	public String getFlightNumberExc() {
		return flightNumberExc;
	}

	/**
	 * @param flightNumberExc the flightNumberExc to set
	 */
	public void setFlightNumberExc(String flightNumberExc) {
		this.flightNumberExc = flightNumberExc;
	}

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	private String 	isTaxIncludedInRateFlag;
	
		
	/**
	 * @return the isTaxIncludedInRateFlag
	 */
	public String getIsTaxIncludedInRateFlag() {
		return isTaxIncludedInRateFlag;
	}

	/**
	 * @param isTaxIncludedInRateFlag the isTaxIncludedInRateFlag to set
	 */
	public void setIsTaxIncludedInRateFlag(String isTaxIncludedInRateFlag) {
		this.isTaxIncludedInRateFlag = isTaxIncludedInRateFlag;
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
	 * @return Returns the transferedByExc.
	 */
	public String getTransferedByExc() {
		return transferedByExc;
	}

	/**
	 * @param transferedByExc The transferedByExc to set.
	 */
	public void setTransferedByExc(String transferedByExc) {
		this.transferedByExc = transferedByExc;
	}

	/**
	 * @return Returns the transferedByInc.
	 */
	public String getTransferedByInc() {
		return transferedByInc;
	}

	/**
	 * @param transferedByInc The transferedByInc to set.
	 */
	public void setTransferedByInc(String transferedByInc) {
		this.transferedByInc = transferedByInc;
	}

	/**
	 * @return Returns the reFlag.
	 */
	public String getReFlag() {
		return reFlag;
	}

	/**
	 * @param reFlag The reFlag to set.
	 */
	public void setReFlag(String reFlag) {
		this.reFlag = reFlag;
	}

	/**
	 * @return Returns the blgPtyType.
	 */
	public String getBlgPtyType() {
		return blgPtyType;
	}

	/**
	 * @param blgPtyType The blgPtyType to set.
	 */
	public void setBlgPtyType(String blgPtyType) {
		this.blgPtyType = blgPtyType;
	}

	/**
	 * @return Returns the billingParty.
	 */
	public String getBillingParty() {
		return billingParty;
	}

	/**
	 * @param billingParty The billingParty to set.
	 */
	public void setBillingParty(String billingParty) {
		this.billingParty = billingParty;
	}

	/**
	 * @return Returns the isModified.
	 */
	public String getIsModified() {
		return isModified;
	}

	/**
	 * @param isModified The isModified to set.
	 */
	public void setIsModified(String isModified) {
		this.isModified = isModified;
	}

	/**
	 * @return Returns the canClose.
	 */
	public String getCanClose() {
		return canClose;
	}

	/**
	 * @param canClose The canClose to set.
	 */
	public void setCanClose(String canClose) {
		this.canClose = canClose;
	}

	
	/**
	 * @return Returns the actionType.
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * @param actionType The actionType to set.
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return Returns the selectedIndex.
	 */
	public String getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * @param selectedIndex The selectedIndex to set.
	 */
	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	/**
	 * @return Returns the billBy.
	 */
	public String getBillBy() {
		return billBy;
	}

	/**
	 * @param billBy The billBy to set.
	 */
	public void setBillBy(String billBy) {
		this.billBy = billBy;
	}

	/**
	 * @return Returns the billedSector.
	 */
	public String getBilledSector() {
		return billedSector;
	}

	/**
	 * @param billedSector The billedSector to set.
	 */
	public void setBilledSector(String billedSector) {
		this.billedSector = billedSector;
	}

	/**
	 * @return Returns the billTo.
	 */
	public String getBillTo() {
		return billTo;
	}

	/**
	 * @param billTo The billTo to set.
	 */
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	/**
	 * @return Returns the blgLineValidFrom.
	 */
	public String getBlgLineValidFrom() {
		return blgLineValidFrom;
	}

	/**
	 * @param blgLineValidFrom The blgLineValidFrom to set.
	 */
	public void setBlgLineValidFrom(String blgLineValidFrom) {
		this.blgLineValidFrom = blgLineValidFrom;
	}

	/**
	 * @return Returns the blgLineValidTo.
	 */
	public String getBlgLineValidTo() {
		return blgLineValidTo;
	}

	/**
	 * @param blgLineValidTo The blgLineValidTo to set.
	 */
	public void setBlgLineValidTo(String blgLineValidTo) {
		this.blgLineValidTo = blgLineValidTo;
	}

	/**
	 * @return Returns the catExc.
	 */
	public String getCatExc() {
		return catExc;
	}

	/**
	 * @param catExc The catExc to set.
	 */
	public void setCatExc(String catExc) {
		this.catExc = catExc;
	}

	/**
	 * @return Returns the catInc.
	 */
	public String getCatInc() {
		return catInc;
	}

	/**
	 * @param catInc The catInc to set.
	 */
	public void setCatInc(String catInc) {
		this.catInc = catInc;
	}

	/**
	 * @return Returns the classExc.
	 */
	public String getClassExc() {
		return classExc;
	}

	/**
	 * @param classExc The classExc to set.
	 */
	public void setClassExc(String classExc) {
		this.classExc = classExc;
	}

	/**
	 * @return Returns the classInc.
	 */
	public String getClassInc() {
		return classInc;
	}

	/**
	 * @param classInc The classInc to set.
	 */
	public void setClassInc(String classInc) {
		this.classInc = classInc;
	}

	/**
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return Returns the rate.
	 *//*
	public String getRate() {
		return rate;
	}

	*//**
	 * @param rate The rate to set.
	 *//*
	public void setRate(String rate) {
		this.rate = rate;
	}
*/
	/**
	 * @return Returns the revExp.
	 */
	public String getRevExp() {
		return revExp;
	}

	/**
	 * @param revExp The revExp to set.
	 */
	public void setRevExp(String revExp) {
		this.revExp = revExp;
	}

	/**
	 * @return Returns the subClsExc.
	 */
	public String getSubClsExc() {
		return subClsExc;
	}

	/**
	 * @param subClsExc The subClsExc to set.
	 */
	public void setSubClsExc(String subClsExc) {
		this.subClsExc = subClsExc;
	}

	/**
	 * @return Returns the subClsInc.
	 */
	public String getSubClsInc() {
		return subClsInc;
	}

	/**
	 * @param subClsInc The subClsInc to set.
	 */
	public void setSubClsInc(String subClsInc) {
		this.subClsInc = subClsInc;
	}

	/**
	 * @return Returns the uldTypExc.
	 */
	public String getUldTypExc() {
		return uldTypExc;
	}

	/**
	 * @param uldTypExc The uldTypExc to set.
	 */
	public void setUldTypExc(String uldTypExc) {
		this.uldTypExc = uldTypExc;
	}

	/**
	 * @return Returns the uldTypInc.
	 */
	public String getUldTypInc() {
		return uldTypInc;
	}

	/**
	 * @param uldTypInc The uldTypInc to set.
	 */
	public void setUldTypInc(String uldTypInc) {
		this.uldTypInc = uldTypInc;
	}

	/**
	 *
	 */
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 *
	 */
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 *
	 */
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	

	/**
	 * @return Returns the desCtyExc.
	 */
	public String getDesCtyExc() {
		return desCtyExc;
	}

	/**
	 * @param desCtyExc The desCtyExc to set.
	 */
	public void setDesCtyExc(String desCtyExc) {
		this.desCtyExc = desCtyExc;
	}

	/**
	 * @return Returns the desCtyInc.
	 */
	public String getDesCtyInc() {
		return desCtyInc;
	}

	/**
	 * @param desCtyInc The desCtyInc to set.
	 */
	public void setDesCtyInc(String desCtyInc) {
		this.desCtyInc = desCtyInc;
	}

	/**
	 * @return Returns the desRegExc.
	 */
	public String getDesRegExc() {
		return desRegExc;
	}

	/**
	 * @param desRegExc The desRegExc to set.
	 */
	public void setDesRegExc(String desRegExc) {
		this.desRegExc = desRegExc;
	}

	/**
	 * @return Returns the desRegInc.
	 */
	public String getDesRegInc() {
		return desRegInc;
	}

	/**
	 * @param desRegInc The desRegInc to set.
	 */
	public void setDesRegInc(String desRegInc) {
		this.desRegInc = desRegInc;
	}

	/**
	 * @return Returns the disCntExc.
	 */
	public String getDisCntExc() {
		return disCntExc;
	}

	/**
	 * @param disCntExc The disCntExc to set.
	 */
	public void setDisCntExc(String disCntExc) {
		this.disCntExc = disCntExc;
	}

	/**
	 * @return Returns the disCntInc.
	 */
	public String getDisCntInc() {
		return disCntInc;
	}

	/**
	 * @param disCntInc The disCntInc to set.
	 */
	public void setDisCntInc(String disCntInc) {
		this.disCntInc = disCntInc;
	}

	/**
	 * @return Returns the disCtyExc.
	 */
	public String getDisCtyExc() {
		return disCtyExc;
	}

	/**
	 * @param disCtyExc The disCtyExc to set.
	 */
	public void setDisCtyExc(String disCtyExc) {
		this.disCtyExc = disCtyExc;
	}

	/**
	 * @return Returns the disCtyInc.
	 */
	public String getDisCtyInc() {
		return disCtyInc;
	}

	/**
	 * @param disCtyInc The disCtyInc to set.
	 */
	public void setDisCtyInc(String disCtyInc) {
		this.disCtyInc = disCtyInc;
	}

	/**
	 * @return Returns the fltNumExc.
	 */
	public String getFltNumExc() {
		return fltNumExc;
	}

	/**
	 * @param fltNumExc The fltNumExc to set.
	 */
	public void setFltNumExc(String fltNumExc) {
		this.fltNumExc = fltNumExc;
	}

	/**
	 * @return Returns the fltNumInc.
	 */
	public String getFltNumInc() {
		return fltNumInc;
	}

	/**
	 * @param fltNumInc The fltNumInc to set.
	 */
	public void setFltNumInc(String fltNumInc) {
		this.fltNumInc = fltNumInc;
	}

	/**
	 * @return Returns the orgCntExc.
	 */
	public String getOrgCntExc() {
		return orgCntExc;
	}

	/**
	 * @param orgCntExc The orgCntExc to set.
	 */
	public void setOrgCntExc(String orgCntExc) {
		this.orgCntExc = orgCntExc;
	}

	/**
	 * @return Returns the orgCntInc.
	 */
	public String getOrgCntInc() {
		return orgCntInc;
	}

	/**
	 * @param orgCntInc The orgCntInc to set.
	 */
	public void setOrgCntInc(String orgCntInc) {
		this.orgCntInc = orgCntInc;
	}

	/**
	 * @return Returns the orgCtyExc.
	 */
	public String getOrgCtyExc() {
		return orgCtyExc;
	}

	/**
	 * @param orgCtyExc The orgCtyExc to set.
	 */
	public void setOrgCtyExc(String orgCtyExc) {
		this.orgCtyExc = orgCtyExc;
	}

	/**
	 * @return Returns the orgCtyInc.
	 */
	public String getOrgCtyInc() {
		return orgCtyInc;
	}

	/**
	 * @param orgCtyInc The orgCtyInc to set.
	 */
	public void setOrgCtyInc(String orgCtyInc) {
		this.orgCtyInc = orgCtyInc;
	}

	/**
	 * @return Returns the orgRegExc.
	 */
	public String getOrgRegExc() {
		return orgRegExc;
	}

	/**
	 * @param orgRegExc The orgRegExc to set.
	 */
	public void setOrgRegExc(String orgRegExc) {
		this.orgRegExc = orgRegExc;
	}

	/**
	 * @return Returns the orgRegInc.
	 */
	public String getOrgRegInc() {
		return orgRegInc;
	}

	/**
	 * @param orgRegInc The orgRegInc to set.
	 */
	public void setOrgRegInc(String orgRegInc) {
		this.orgRegInc = orgRegInc;
	}

	/**
	 * @return Returns the uplCntExc.
	 */
	public String getUplCntExc() {
		return uplCntExc;
	}

	/**
	 * @param uplCntExc The uplCntExc to set.
	 */
	public void setUplCntExc(String uplCntExc) {
		this.uplCntExc = uplCntExc;
	}

	/**
	 * @return Returns the uplCntInc.
	 */
	public String getUplCntInc() {
		return uplCntInc;
	}

	/**
	 * @param uplCntInc The uplCntInc to set.
	 */
	public void setUplCntInc(String uplCntInc) {
		this.uplCntInc = uplCntInc;
	}

	/**
	 * @return Returns the uplCtyExc.
	 */
	public String getUplCtyExc() {
		return uplCtyExc;
	}

	/**
	 * @param uplCtyExc The uplCtyExc to set.
	 */
	public void setUplCtyExc(String uplCtyExc) {
		this.uplCtyExc = uplCtyExc;
	}

	/**
	 * @return Returns the uplCtyInc.
	 */
	public String getUplCtyInc() {
		return uplCtyInc;
	}

	/**
	 * @param uplCtyInc The uplCtyInc to set.
	 */
	public void setUplCtyInc(String uplCtyInc) {
		this.uplCtyInc = uplCtyInc;
	}

	/**
	 * @return Returns the desCntExc.
	 */
	public String getDesCntExc() {
		return desCntExc;
	}

	/**
	 * @param desCntExc The desCntExc to set.
	 */
	public void setDesCntExc(String desCntExc) {
		this.desCntExc = desCntExc;
	}

	/**
	 * @return Returns the desCntInc.
	 */
	public String getDesCntInc() {
		return desCntInc;
	}

	/**
	 * @param desCntInc The desCntInc to set.
	 */
	public void setDesCntInc(String desCntInc) {
		this.desCntInc = desCntInc;
	}

	/**
	 * @return Returns the frmDate.
	 */
	public String getFrmDate() {
		return frmDate;
	}

	/**
	 * @param frmDate The frmDate to set.
	 */
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}

	/**
	 * @return Returns the toDate.
	 */
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
	 * @return the subClassExc
	 */
	public String getSubClassExc() {
		return subClassExc;
	}

	/**
	 * @param subClassExc the subClassExc to set
	 */
	public void setSubClassExc(String subClassExc) {
		this.subClassExc = subClassExc;
	}

	/**
	 * @return the subClassInc
	 */
	public String getSubClassInc() {
		return subClassInc;
	}

	/**
	 * @param subClassInc the subClassInc to set
	 */
	public void setSubClassInc(String subClassInc) {
		this.subClassInc = subClassInc;
	}

	/**
	 * @return the transferedPAExc
	 */
	public String getTransferedPAExc() {
		return transferedPAExc;
	}

	/**
	 * @param transferedPAExc the transferedPAExc to set
	 */
	public void setTransferedPAExc(String transferedPAExc) {
		this.transferedPAExc = transferedPAExc;
	}

	/**
	 * @return the transferedPAInc
	 */
	public String getTransferedPAInc() {
		return transferedPAInc;
	}

	/**
	 * @param transferedPAInc the transferedPAInc to set
	 */
	public void setTransferedPAInc(String transferedPAInc) {
		this.transferedPAInc = transferedPAInc;
	}
	/**
	 * @param restrictionFlag the restrictionFlag to set
	 */
	public void setRestrictionFlag(String restrictionFlag) {
		this.restrictionFlag = restrictionFlag;
	}
	/**
	 * @return the restrictionFlag
	 */
	public String getRestrictionFlag() {
		return restrictionFlag;
	}
	/**
	 * @param mailCompanyInc the mailCompanyInc to set
	 */
	public void setMailCompanyInc(String mailCompanyInc) {
		this.mailCompanyInc = mailCompanyInc;
	}
	/**
	 * @return the mailCompanyInc
	 */
	public String getMailCompanyInc() {
		return mailCompanyInc;
	}
	/**
	 * @param mailCompanyExc the mailCompanyExc to set
	 */
	public void setMailCompanyExc(String mailCompanyExc) {
		this.mailCompanyExc = mailCompanyExc;
	}
	/**
	 * @return the mailCompanyExc
	 */
	public String getMailCompanyExc() {
		return mailCompanyExc;
	}

	/**
	 * 	Getter for specificFlag 
	 *	Added by : A-5219 on 06-Apr-2014
	 * 	Used for :
	 */
	public String getSpecificFlag() {
		return specificFlag;
	}

	/**
	 *  @param specificFlag the specificFlag to set
	 * 	Setter for specificFlag 
	 *	Added by : A-5219 on 06-Apr-2014
	 * 	Used for :
	 */
	public void setSpecificFlag(String specificFlag) {
		this.specificFlag = specificFlag;
	}

	/**
	 * @return the chargeHead
	 */
	public String getChargeHead() {
		return chargeHead;
	}

	/**
	 * @param chargeHead the chargeHead to set
	 */
	public void setChargeHead(String chargeHead) {
		this.chargeHead = chargeHead;
	}

	/**
	 * @return the chargeTypeMail
	 */
	public String getChargeTypeMail() {
		return chargeTypeMail;
	}

	/**
	 * @param chargeTypeMail the chargeTypeMail to set
	 */
	public void setChargeTypeMail(String chargeTypeMail) {
		this.chargeTypeMail = chargeTypeMail;
	}

	/**
	 * @return the wbIndexMail
	 */
	public String[] getWbIndexMail() {
		return wbIndexMail;
	}

	/**
	 * @param wbIndexMail the wbIndexMail to set
	 */
	public void setWbIndexMail(String[] wbIndexMail) {
		this.wbIndexMail = wbIndexMail;
	}

	/**
	 * @return the wbFrmWgtMail
	 */
	public String[] getWbFrmWgtMail() {
		return wbFrmWgtMail;
	}

	/**
	 * @param wbFrmWgtMail the wbFrmWgtMail to set
	 */
	public void setWbFrmWgtMail(String[] wbFrmWgtMail) {
		this.wbFrmWgtMail = wbFrmWgtMail;
	}

	/**
	 * @return the wbApplicableRateMail
	 */
	public String[] getWbApplicableRateMail() {
		return wbApplicableRateMail;
	}

	/**
	 * @param wbApplicableRateMail the wbApplicableRateMail to set
	 */
	public void setWbApplicableRateMail(String[] wbApplicableRateMail) {
		this.wbApplicableRateMail = wbApplicableRateMail;
	}

	/**
	 * @return the minimumChargeOther
	 */
	public String getMinimumChargeOther() {
		return minimumChargeOther;
	}

	/**
	 * @param minimumChargeOther the minimumChargeOther to set
	 */
	public void setMinimumChargeOther(String minimumChargeOther) {
		this.minimumChargeOther = minimumChargeOther;
	}

	/**
	 * @return the normalRateOther
	 */
	public String getNormalRateOther() {
		return normalRateOther;
	}

	/**
	 * @param normalRateOther the normalRateOther to set
	 */
	public void setNormalRateOther(String normalRateOther) {
		this.normalRateOther = normalRateOther;
	}

	/**
	 * @return the wbIndexOther
	 */
	public String[] getWbIndexOther() {
		return wbIndexOther;
	}

	/**
	 * @param wbIndexOther the wbIndexOther to set
	 */
	public void setWbIndexOther(String[] wbIndexOther) {
		this.wbIndexOther = wbIndexOther;
	}

	/**
	 * @return the wbFrmWgtOther
	 */
	public String[] getWbFrmWgtOther() {
		return wbFrmWgtOther;
	}

	/**
	 * @param wbFrmWgtOther the wbFrmWgtOther to set
	 */
	public void setWbFrmWgtOther(String[] wbFrmWgtOther) {
		this.wbFrmWgtOther = wbFrmWgtOther;
	}

	/**
	 * @return the wbApplicableRateOther
	 */
	public String[] getWbApplicableRateOther() {
		return wbApplicableRateOther;
	}

	/**
	 * @param wbApplicableRateOther the wbApplicableRateOther to set
	 */
	public void setWbApplicableRateOther(String[] wbApplicableRateOther) {
		this.wbApplicableRateOther = wbApplicableRateOther;
	}

	/**
	 * @return the flatRateMail
	 */
	public String getFlatRateMail() {
		return flatRateMail;
	}

	/**
	 * @param flatRateMail the flatRateMail to set
	 */
	public void setFlatRateMail(String flatRateMail) {
		this.flatRateMail = flatRateMail;
	}

	/**
	 * @return the flatRateOther
	 */
	public String getFlatRateOther() {
		return flatRateOther;
	}

	/**
	 * @param flatRateOther the flatRateOther to set
	 */
	public void setFlatRateOther(String flatRateOther) {
		this.flatRateOther = flatRateOther;
	}

	/**
	 * @return the flatChargeMail
	 */
	public String getFlatChargeMail() {
		return flatChargeMail;
	}

	/**
	 * @param flatChargeMail the flatChargeMail to set
	 */
	public void setFlatChargeMail(String flatChargeMail) {
		this.flatChargeMail = flatChargeMail;
	}

	/**
	 * @return the flatChargeOther
	 */
	public String getFlatChargeOther() {
		return flatChargeOther;
	}

	/**
	 * @param flatChargeOther the flatChargeOther to set
	 */
	public void setFlatChargeOther(String flatChargeOther) {
		this.flatChargeOther = flatChargeOther;
	}

	/**
	 * @return the ratingBasisMail
	 */
	public String getRatingBasisMail() {
		return ratingBasisMail;
	}

	/**
	 * @param ratingBasisMail the ratingBasisMail to set
	 */
	public void setRatingBasisMail(String ratingBasisMail) {
		this.ratingBasisMail = ratingBasisMail;
	}

	/**
	 * @return the chargeTypeOther
	 */
	public String getChargeTypeOther() {
		return chargeTypeOther;
	}

	/**
	 * @param chargeTypeOther the chargeTypeOther to set
	 */
	public void setChargeTypeOther(String chargeTypeOther) {
		this.chargeTypeOther = chargeTypeOther;
	}

	/**
	 * @return the ratingBasisOther
	 */
	public String getRatingBasisOther() {
		return ratingBasisOther;
	}

	/**
	 * @param ratingBasisOther the ratingBasisOther to set
	 */
	public void setRatingBasisOther(String ratingBasisOther) {
		this.ratingBasisOther = ratingBasisOther;
	}

	/**
	 * @return the minimumChargeMail
	 */
	public String getMinimumChargeMail() {
		return minimumChargeMail;
	}

	/**
	 * @param minimumChargeMail the minimumChargeMail to set
	 */
	public void setMinimumChargeMail(String minimumChargeMail) {
		this.minimumChargeMail = minimumChargeMail;
	}

	/**
	 * @return the subproduct
	 */
	public static String getSubproduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return the screenid
	 */
	public static String getScreenid() {
		return SCREENID;
	}

	/**
	 * @return the normalRateMail
	 */
	public String getNormalRateMail() {
		return normalRateMail;
	}

	/**
	 * @param normalRateMail the normalRateMail to set
	 */
	public void setNormalRateMail(String normalRateMail) {
		this.normalRateMail = normalRateMail;
	}

	/**
	 * @return the prevoiusChargeHead
	 */
	public String getPrevoiusChargeHead() {
		return prevoiusChargeHead;
	}

	/**
	 * @param prevoiusChargeHead the prevoiusChargeHead to set
	 */
	public void setPrevoiusChargeHead(String prevoiusChargeHead) {
		this.prevoiusChargeHead = prevoiusChargeHead;
	}

	/**
	 * @return the billingBasisMail
	 */
	public String getBillingBasisMail() {
		return billingBasisMail;
	}

	/**
	 * @param billingBasisMail the billingBasisMail to set
	 */
	public void setBillingBasisMail(String billingBasisMail) {
		this.billingBasisMail = billingBasisMail;
	}

	/**
	 * @return the billingBasisOther
	 */
	public String getBillingBasisOther() {
		return billingBasisOther;
	}

	/**
	 * @param billingBasisOther the billingBasisOther to set
	 */
	public void setBillingBasisOther(String billingBasisOther) {
		this.billingBasisOther = billingBasisOther;
	}

	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return the hiddenRateOpFlag
	 */
	public String[] getHiddenRateOpFlag() {
		return hiddenRateOpFlag;
	}

	/**
	 * @param hiddenRateOpFlag the hiddenRateOpFlag to set
	 */
	public void setHiddenRateOpFlag(String[] hiddenRateOpFlag) {
		this.hiddenRateOpFlag = hiddenRateOpFlag;
	}

	/**
	 * @return the rateAction
	 */
	public String getRateAction() {
		return rateAction;
	}

	/**
	 * @param rateAction the rateAction to set
	 */
	public void setRateAction(String rateAction) {
		this.rateAction = rateAction;
	}

	public String getAgentCodeInc() {
		return agentCodeInc;
	}

	public void setAgentCodeInc(String agentCodeInc) {
		this.agentCodeInc = agentCodeInc;
	}

	public String getAgentCodeExc() {
		return agentCodeExc;
	}

	public void setAgentCodeExc(String agentCodeExc) {
		this.agentCodeExc = agentCodeExc;
	}

	public String getOrgAirportInc() {
		return orgAirportInc;
	}

	public void setOrgAirportInc(String orgAirportInc) {
		this.orgAirportInc = orgAirportInc;
	}

	public String getOrgAirportExc() {
		return orgAirportExc;
	}

	public void setOrgAirportExc(String orgAirportExc) {
		this.orgAirportExc = orgAirportExc;
	}

	public String getDesAirportInc() {
		return desAirportInc;
	}

	public void setDesAirportInc(String desAirportInc) {
		this.desAirportInc = desAirportInc;
	}

	public String getDesAirportExc() {
		return desAirportExc;
	}

	public void setDesAirportExc(String desAirportExc) {
		this.desAirportExc = desAirportExc;
	}

	public String getViaPointInc() {
		return viaPointInc;
	}

	public void setViaPointInc(String viaPointInc) {
		this.viaPointInc = viaPointInc;
	}

	public String getViaPointExc() {
		return viaPointExc;
	}

	public void setViaPointExc(String viaPointExc) {
		this.viaPointExc = viaPointExc;
	}

	public String getMailServiceExc() {
		return mailServiceExc;
	}

	public void setMailServiceExc(String mailServiceExc) {
		this.mailServiceExc = mailServiceExc;
	}

	public String getMailServiceInc() {
		return mailServiceInc;
	}

	public void setMailServiceInc(String mailServiceInc) {
		this.mailServiceInc = mailServiceInc;
	}
 
	public String getPaBuilt() {
		return paBuilt;
	}

	public void setPaBuilt(String paBuilt) {
		this.paBuilt = paBuilt;
	}

	public Double getConDiscount() {
		return conDiscount;
	}

	public void setConDiscount(Double conDiscount) {
		this.conDiscount = conDiscount;
	}

	public Double getUspsRateOne() {
		return uspsRateOne;
	}

	public void setUspsRateOne(Double uspsRateOne) {
		this.uspsRateOne = uspsRateOne;
	}

	public Double getUspsRateTwo() {
		return uspsRateTwo;
	}

	public void setUspsRateTwo(Double uspsRateTwo) {
		this.uspsRateTwo = uspsRateTwo;
	}

	public Double getUspsRateThr() {
		return uspsRateThr;
	}

	public void setUspsRateThr(Double uspsRateThr) {
		this.uspsRateThr = uspsRateThr;
	}

	public Double getUspsRateFour() {
		return uspsRateFour;
	}

	public void setUspsRateFour(Double uspsRateFour) {
		this.uspsRateFour = uspsRateFour;
	}

	public Double getUspsTot() {
		return uspsTot;
	}

	public void setUspsTot(Double uspsTot) {
		this.uspsTot = uspsTot;
	}

	public String getUplArpInc() {
		return uplArpInc;
	}

	public void setUplArpInc(String uplArpInc) {
		this.uplArpInc = uplArpInc;
	}

	public String getUplArpExc() {
		return uplArpExc;
	}

	public void setUplArpExc(String uplArpExc) {
		this.uplArpExc = uplArpExc;
	}

	public String getDisArpInc() {
		return disArpInc;
	}

	public void setDisArpInc(String disArpInc) {
		this.disArpInc = disArpInc;
	}

	public String getDisArpExc() {
		return disArpExc;
	}

	public void setDisArpExc(String disArpExc) {
		this.disArpExc = disArpExc;
	}

	public String getFlownCarrierInc() {
		return flownCarrierInc;
	}

	public void setFlownCarrierInc(String flownCarrierInc) {
		this.flownCarrierInc = flownCarrierInc;
	}

	public String getFlownCarrierExc() {
		return flownCarrierExc;
	} 

	public void setFlownCarrierExc(String flownCarrierExc) {
		this.flownCarrierExc = flownCarrierExc;
	}

	  

	
} 
