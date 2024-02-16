/*
 * BillingMatrixForm.java Created on Feb 28, 2007
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
public class BillingMatrixForm extends ScreenModel{

private static final String BUNDLE = "billingmatrix";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID =
		"mailtracking.mra.defaults.maintainbillingmatrix";

	private String operationFlag;

	private String blgMatrixID;

	private String gpaCode;

	private String airlineCode;

	private String description;

	private String status;

	private String id;

	private String validFrom;

	private String validTo;

	private String idValue;

	private String[] parameters;

	private String [] checkboxes;

	private String [] orgCountry;

	private String [] orgCity;

	private String [] orgRegion;

	private String [] destCountry;

	private String [] destRegion;

	private String [] destCity;

	private String [] upliftCountry;

	private String [] upliftCity;

	private String [] dischargeCountry;

	private String [] dischargeCity;

	private String [] billingCategory;

	private String [] billingClass;

	private String [] billingSubClass;

	private String [] billingUldType;

	private String [] billingFlightNo;

	private String [] transferedBy;
	
	private String [] transferedPA;

	private String [] mailCompanyCode;
	private String lastPageNum="0";

	private String displayPage="1";

	private String isModified;

	private String formStatusFlag;

	private String selectedIndex;

	private String billingParty;

	private String [] statusValue;

	private String airlineIdentfier;

	private String changedStatus;

	private String fromPage;

	private String popupStatus;

	private String canClose;

	private String copyFlag;

	private String actionType;

	private String selectedIndexes;

	private String validateStatus;

	private String [] revExpFlag;

	private String [] billingBasis;
	private String disableButton;
	private String disableActive;
	private String notSave;
	private String fromDateFlag;
	
	private String[] subClass;
	 private String[] agentCode;//added by a-7531
	 private String overrideRounding;//added by a-7871 for ICRD-214766
	 //Added by A-7540 
	 private String[] orgAirport;
	 private String[] desAirport;
	 private String[] viaPoint;
	 private String[] mailService;
	 private String[]  paBuilt;
	 
	 private String[] uplAirport;
	 private String[] disAirport;
	 
	 private String[] flownCarrier;
	 
	 
	 
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
	 * @return Returns the disableActive.
	 */
	public String getDisableActive() {
		return disableActive;
	}

	/**
	 * @param disableActive The disableActive to set.
	 */
	public void setDisableActive(String disableActive) {
		this.disableActive = disableActive;
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
	* @return Returns the gpaCode.
	*/
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	* @param gpaCode The gpaCode to set.
	*/
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return Returns the transferedBy.
	 */
	public String[] getTransferedBy() {
		return transferedBy;
	}

	/**
	 * @param transferedBy The transferedBy to set.
	 */
	public void setTransferedBy(String[] transferedBy) {
		this.transferedBy = transferedBy;
	}

	/**
	 * @return Returns the revExpFlag.
	 */
	public String[] getRevExpFlag() {
		return revExpFlag;
	}

	/**
	 * @param revExpFlag The revExpFlag to set.
	 */
	public void setRevExpFlag(String[] revExpFlag) {
		this.revExpFlag = revExpFlag;
	}

	/**
	 * @return Returns the validateStatus.
	 */
	public String getValidateStatus() {
		return validateStatus;
	}

	/**
	 * @param validateStatus The validateStatus to set.
	 */
	public void setValidateStatus(String validateStatus) {
		this.validateStatus = validateStatus;
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
	 * @return Returns the selectedIndexes.
	 */
	public String getSelectedIndexes() {
		return selectedIndexes;
	}

	/**
	 * @param selectedIndexes The selectedIndexes to set.
	 */
	public void setSelectedIndexes(String selectedIndexes) {
		this.selectedIndexes = selectedIndexes;
	}

	/**
	 * @return Returns the copyFlag.
	 */
	public String getCopyFlag() {
		return copyFlag;
	}

	/**
	 * @param copyFlag The copyFlag to set.
	 */
	public void setCopyFlag(String copyFlag) {
		this.copyFlag = copyFlag;
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
	 * @return Returns the changedStatus.
	 */
	public String getChangedStatus() {
		return changedStatus;
	}

	/**
	 * @param changedStatus The changedStatus to set.
	 */
	public void setChangedStatus(String changedStatus) {
		this.changedStatus = changedStatus;
	}

	/**
	 * @return Returns the fromPage.
	 */
	public String getFromPage() {
		return fromPage;
	}

	/**
	 * @param fromPage The fromPage to set.
	 */
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	/**
	 * @return Returns the popupStatus.
	 */
	public String getPopupStatus() {
		return popupStatus;
	}

	/**
	 * @param popupStatus The popupStatus to set.
	 */
	public void setPopupStatus(String popupStatus) {
		this.popupStatus = popupStatus;
	}

	/**
	 * @return Returns the statusValue.
	 */
	public String[] getStatusValue() {
		return statusValue;
	}

	/**
	 * @param statusValue The statusValue to set.
	 */
	public void setStatusValue(String[] statusValue) {
		this.statusValue = statusValue;
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
	 * @return Returns the formStatusFlag.
	 */
	public String getFormStatusFlag() {
		return formStatusFlag;
	}

	/**
	 * @param formStatusFlag The formStatusFlag to set.
	 */
	public void setFormStatusFlag(String formStatusFlag) {
		this.formStatusFlag = formStatusFlag;
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
	 * @return Returns the blgMatrixID.
	 */
	public String getBlgMatrixID() {
		return blgMatrixID;
	}

	/**
	 * @param blgMatrixID The blgMatrixID to set.
	 */
	public void setBlgMatrixID(String blgMatrixID) {
		this.blgMatrixID = blgMatrixID;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the validFrom.
	 */
	public String getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom The validFrom to set.
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return Returns the validTo.
	 */
	public String getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo The validTo to set.
	 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
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
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the checkboxes.
	 */
	public String[] getCheckboxes() {
		return checkboxes;
	}

	/**
	 * @param checkboxes The checkboxes to set.
	 */
	public void setCheckboxes(String[] checkboxes) {
		this.checkboxes = checkboxes;
	}

	/**
	 * @return Returns the destCity.
	 */
	public String[] getDestCity() {
		return destCity;
	}

	/**
	 * @param destCity The destCity to set.
	 */
	public void setDestCity(String[] destCity) {
		this.destCity = destCity;
	}

	/**
	 * @return Returns the destCountry.
	 */
	public String[] getDestCountry() {
		return destCountry;
	}

	/**
	 * @param destCountry The destCountry to set.
	 */
	public void setDestCountry(String[] destCountry) {
		this.destCountry = destCountry;
	}

	/**
	 * @return Returns the destRegion.
	 */
	public String[] getDestRegion() {
		return destRegion;
	}

	/**
	 * @param destRegion The destRegion to set.
	 */
	public void setDestRegion(String[] destRegion) {
		this.destRegion = destRegion;
	}

	/**
	 * @return Returns the dischargeCity.
	 */
	public String[] getDischargeCity() {
		return dischargeCity;
	}

	/**
	 * @param dischargeCity The dischargeCity to set.
	 */
	public void setDischargeCity(String[] dischargeCity) {
		this.dischargeCity = dischargeCity;
	}

	/**
	 * @return Returns the dischargeCountry.
	 */
	public String[] getDischargeCountry() {
		return dischargeCountry;
	}

	/**
	 * @param dischargeCountry The dischargeCountry to set.
	 */
	public void setDischargeCountry(String[] dischargeCountry) {
		this.dischargeCountry = dischargeCountry;
	}

	/**
	 * @return Returns the orgCity.
	 */
	public String[] getOrgCity() {
		return orgCity;
	}

	/**
	 * @param orgCity The orgCity to set.
	 */
	public void setOrgCity(String[] orgCity) {
		this.orgCity = orgCity;
	}

	/**
	 * @return Returns the orgCountry.
	 */
	public String[] getOrgCountry() {
		return orgCountry;
	}

	/**
	 * @param orgCountry The orgCountry to set.
	 */
	public void setOrgCountry(String[] orgCountry) {
		this.orgCountry = orgCountry;
	}

	/**
	 * @return Returns the orgRegion.
	 */
	public String[] getOrgRegion() {
		return orgRegion;
	}

	/**
	 * @param orgRegion The orgRegion to set.
	 */
	public void setOrgRegion(String[] orgRegion) {
		this.orgRegion = orgRegion;
	}

	/**
	 * @return Returns the upliftCity.
	 */
	public String[] getUpliftCity() {
		return upliftCity;
	}

	/**
	 * @param upliftCity The upliftCity to set.
	 */
	public void setUpliftCity(String[] upliftCity) {
		this.upliftCity = upliftCity;
	}

	/**
	 * @return Returns the upliftCountry.
	 */
	public String[] getUpliftCountry() {
		return upliftCountry;
	}

	/**
	 * @param upliftCountry The upliftCountry to set.
	 */
	public void setUpliftCountry(String[] upliftCountry) {
		this.upliftCountry = upliftCountry;
	}

	/**
	 * @return Returns the billingCategory.
	 */
	public String[] getBillingCategory() {
		return billingCategory;
	}

	/**
	 * @param billingCategory The billingCategory to set.
	 */
	public void setBillingCategory(String[] billingCategory) {
		this.billingCategory = billingCategory;
	}

	/**
	 * @return Returns the billingClass.
	 */
	public String[] getBillingClass() {
		return billingClass;
	}

	/**
	 * @param billingClass The billingClass to set.
	 */
	public void setBillingClass(String[] billingClass) {
		this.billingClass = billingClass;
	}

	/**
	 * @return Returns the billingFlightNo.
	 */
	public String[] getBillingFlightNo() {
		return billingFlightNo;
	}

	/**
	 * @param billingFlightNo The billingFlightNo to set.
	 */
	public void setBillingFlightNo(String[] billingFlightNo) {
		this.billingFlightNo = billingFlightNo;
	}

	/**
	 * @return Returns the billingSubClass.
	 */
	public String[] getBillingSubClass() {
		return billingSubClass;
	}

	/**
	 * @param billingSubClass The billingSubClass to set.
	 */
	public void setBillingSubClass(String[] billingSubClass) {
		this.billingSubClass = billingSubClass;
	}

	/**
	 * @return Returns the billingUldType.
	 */
	public String[] getBillingUldType() {
		return billingUldType;
	}

	/**
	 * @param billingUldType The billingUldType to set.
	 */
	public void setBillingUldType(String[] billingUldType) {
		this.billingUldType = billingUldType;
	}

	/**
	 * @return Returns the airlineIdentfier.
	 */
	public String getAirlineIdentfier() {
		return airlineIdentfier;
	}

	/**
	 * @param airlineIdentfier The airlineIdentfier to set.
	 */
	public void setAirlineIdentfier(String airlineIdentfier) {
		this.airlineIdentfier = airlineIdentfier;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the idValue.
	 */
	public String getIdValue() {
		return idValue;
	}

	/**
	 * @param idValue The idValue to set.
	 */
	public void setIdValue(String idValue) {
		this.idValue = idValue;
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
	 * @return Returns the billingBasis.
	 */
	public String[] getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis The billingBasis to set.
	 */
	public void setBillingBasis(String[] billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return Returns the parameters.
	 */
	public String[] getParameters() {
		return parameters;
	}

	/**
	 * @param parameters The parameters to set.
	 */
	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	public String getNotSave() {
		return notSave;
	}

	public void setNotSave(String notSave) {
		this.notSave = notSave;
	}

	public String getFromDateFlag() {
		return fromDateFlag;
	}

	public void setFromDateFlag(String fromDateFlag) {
		this.fromDateFlag = fromDateFlag;
	}

	/**
	 * @return the subClass
	 */
	public String[] getSubClass() {
		return subClass;
	}

	/**
	 * @param subClass the subClass to set
	 */
	public void setSubClass(String[] subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return the transferedPA
	 */
	public String[] getTransferedPA() {
		return transferedPA;
	}

	/**
	 * @param transferedPA the transferedPA to set
	 */
	public void setTransferedPA(String[] transferedPA) {
		this.transferedPA = transferedPA;
	}
	/**
	 * @return the mailCompanyCode
	 */
	public String[] getMailCompanyCode() {
		return mailCompanyCode;
	}
	/**
	 * @param mailCompanyCode the mailCompanyCode to set
	 */
	public void setMailCompanyCode(String[] mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}

	public String[] getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String[] agentCode) {
		this.agentCode = agentCode;
	}

	public String[] getOrgAirport() {
		return orgAirport;
	}

	public void setOrgAirport(String[] orgAirport) {
		this.orgAirport = orgAirport;
	}

	public String[] getDesAirport() {
		return desAirport;
	}

	public void setDesAirport(String[] desAirport) {
		this.desAirport = desAirport;
	}

	public String[] getViaPoint() {
		return viaPoint;
	}

	public void setViaPoint(String[] viaPoint) {
		this.viaPoint = viaPoint;
	}

	public String[] getMailService() {
		return mailService;
	}

	public void setMailService(String[] mailService) {
		this.mailService = mailService;
	}

	public String[] getPaBuilt() {
		return paBuilt;
	}

	public void setPaBuilt(String[] paBuilt) {
		this.paBuilt = paBuilt;
	}

	public String[] getUplAirport() {
		return uplAirport;
	}

	public void setUplAirport(String[] uplAirport) {
		this.uplAirport = uplAirport;
	}

	public String[] getDisAirport() {
		return disAirport;
	}

	public void setDisAirport(String[] disAirport) {
		this.disAirport = disAirport;
	}

	public String[] getFlownCarrier() {
		return flownCarrier;
	}

	public void setFlownCarrier(String[] flownCarrier) {
		this.flownCarrier = flownCarrier;
	}


	
}
