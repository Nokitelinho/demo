/*
 * MaintainEmbargoRulesForm.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.reco.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * Form bean for maintainembargoform
 * */
public class MaintainComplianceInfoForm extends ScreenModel {

	private static final String BUNDLE
		= "embargorulesresources";
	private String bundle;
	private String refNumber;
	private String status;
	private String startDate;
	private String endDate;
	private boolean isSuspended;
	private String[] operationalFlag;
	private String[] rowId;
	private String embargoDesc;
	private String remarks;
	private String mode;
	private String canSave;
	private String primaryKey;
	private String isScreenload ="true";
	private String isDisplayDetails="false";
	private String isSaved="";
	private String isButtonClicked;
	private String fromList;
	private String refNumberFlag="false";
	
	private String[] geographicLevel;
	private String[] geographicLevelType;
	private String[] geographicLevelApplicableOn;
	private String[] geographicLevelValues;
	private String[] glOperationFlag;
	private String[] index;
	private String  masterCheckbox;
	private String category;
	private String isApproveFlag;
	private String parameterCode;
	private String parameterValue;
	
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getIsApproveFlag() {
		return isApproveFlag;
	}
	public void setIsApproveFlag(String isApproveFlag) {
		this.isApproveFlag = isApproveFlag;
	}
	private String scc;
	private String sccGroup;
	
	private String embargoVersion;
	
	private String isPrivilegedUser;
	
	public String getIsPrivilegedUser() {
		return isPrivilegedUser;
	}
	public void setIsPrivilegedUser(String isPrivilegedUser) {
		this.isPrivilegedUser = isPrivilegedUser;
	}
	
	public String getEmbargoVersion() {
		return embargoVersion;
	}
	public void setEmbargoVersion(String embargoVersion) {
		this.embargoVersion = embargoVersion;
	}
	public String getScc() {
		return scc;
	}
	public void setScc(String scc) {
		this.scc = scc;
	}
	public String getSccGroup() {
		return sccGroup;
	}
	public void setSccGroup(String sccGroup) {
		this.sccGroup = sccGroup;
	}
	public String[] getRowId() {
		return rowId;
	}
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
	public String[] getIndex() {
		return index;
	}
	public void setIndex(String[] index) {
		this.index = index;
	}
	public String getMasterCheckbox() {
		return masterCheckbox;
	}
	public void setMasterCheckbox(String masterCheckbox) {
		this.masterCheckbox = masterCheckbox;
	}
	
	public String[] getGlOperationFlag() {
		return glOperationFlag;
	}
	public void setGlOperationFlag(String[] glOperationFlag) {
		this.glOperationFlag = glOperationFlag;
	}

	public String[] getGeographicLevelValues() {
		return geographicLevelValues;
	}
	public void setGeographicLevelValues(String[] geographicLevelValues) {
		this.geographicLevelValues = geographicLevelValues;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String[] getGeographicLevel() {
		return geographicLevel;
	}
	public void setGeographicLevel(String[] geographicLevel) {
		this.geographicLevel = geographicLevel;
	}
	public String[] getGeographicLevelType() {
		return geographicLevelType;
	}
	public void setGeographicLevelType(String[] geographicLevelType) {
		this.geographicLevelType = geographicLevelType;
	}
	public String[] getGeographicLevelApplicableOn() {
		return geographicLevelApplicableOn;
	}
	public void setGeographicLevelApplicableOn(String[] geographicLevelApplicableOn) {
		this.geographicLevelApplicableOn = geographicLevelApplicableOn;
	}
	
	/**
	 * @return Returns the refNumberFlag.
	 */
	public String getRefNumberFlag() {
		return refNumberFlag;
	}
	/**
	 * @param refNumberFlag The refNumberFlag to set.
	 */
	public void setRefNumberFlag(String refNumberFlag) {
		this.refNumberFlag = refNumberFlag;
	}
	public String getFromList() {
		return fromList;
	}
	public void setFromList(String fromList) {
		this.fromList = fromList;
	}
	/**
	 * @return Returns the isButtonClicked.
	 */
	public String getIsButtonClicked() {
		return isButtonClicked;
	}
	/**
	 * @param isButtonClicked The isButtonClicked to set.
	 */
	public void setIsButtonClicked(String isButtonClicked) {
		this.isButtonClicked = isButtonClicked;
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
	 * @return Returns the isSaved.
	 */
	public String getIsSaved() {
		return isSaved;
	}
	/**
	 * @param isSaved The isSaved to set.
	 */
	public void setIsSaved(String isSaved) {
		this.isSaved = isSaved;
	}
	/**
	 * @return Returns the isDisplayDetails.
	 */
	public String getIsDisplayDetails() {
		return isDisplayDetails;
	}
	/**
	 * @param isDisplayDetails The isDisplayDetails to set.
	 */
	public void setIsDisplayDetails(String isDisplayDetails) {
		this.isDisplayDetails = isDisplayDetails;
	}
	/**
	 * @return Returns the isScreenload.
	 */
	public String getIsScreenload() {
		return isScreenload;
	}
	/**
	 * @param isScreenload The isScreenload to set.
	 */
	public void setIsScreenload(String isScreenload) {
		this.isScreenload = isScreenload;
	}
	/**
	 * Getter method for canSave
	 * @return Returns the canSave.
	 */
	public String getCanSave() {
		return canSave;
	}
	/**
	 * Setter method for canSave
	 * @param canSave The canSave to set.
	 */
	public void setCanSave(String canSave) {
		this.canSave = canSave;
	}
	/**
	 * Getter method for mode
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * Setter method for mode
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * Getter method for embargoDesc.
	 * @return Returns the embargoDesc.
	 */
	public String getEmbargoDesc() {
		return embargoDesc;
	}
	/**
	 * Setter method for embargoDesc
	 * @param embargoDesc The embargoDesc to set.
	 */
	public void setEmbargoDesc(String embargoDesc) {
		this.embargoDesc = embargoDesc;
	}
	/**
	 * Getter method for remarks
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * Setter method for remarks
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * Getter method for status.
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Setter method for status.
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Getter method for startDate.
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return startDate;
	}
	
	/**
	 * Setter method for startDate
	 * @param startDate The startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * Getter method for endDate.
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * Setter method for endDate.
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * Getter method for isSuspended.
	 * @return Returns the isSuspended.
	 */
	public boolean getIsSuspended() {
		return isSuspended;
	}
	/**
	 * Setter method for isSuspended.
	 * @param isSuspended The isSuspended to set.
	 */
	public void setIsSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}
	
	/**
	 * Getter method for refNumber.
	 * @return Returns the refNumber.
	 */
	public String getRefNumber() {
		return refNumber;
	}
	/**
	 * Setter method for refNumber.
	 * @param refNumber The refNumber to set.
	 */
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	/**
	 * Getter method for screenid
	 * @return screenid
	 */
		public String getScreenId() {
			return "reco.defaults.maintaincomplianceinfo";
		}
	 /**
	  * Getter method for product
	  * @return product
	  */
		public String getProduct() {
			return "reco";
		}
	/**
	 * Getter method for subproduct
	 *  @return SubProduct
	 */
		public String getSubProduct() {
			return "defaults";
		}
	/**
	 * Getter method for operationalFlag
	 * @return Returns the operationalFlag.
	 */
	public String[] getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * Setter method for operationalFlag
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 * Getter method for primaryKey.
	 * @return Returns the primaryKey.
	 */
	public String getPrimaryKey() {
		return primaryKey;
	}
	/**
	 * Setter method for primaryKey.
	 * @param primaryKey The primaryKey to set.
	 */
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}	
}
