/*
 * MaintainLoyaltyForm.java Created on Mar 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1496
 *
 */
public class MaintainLoyaltyForm extends ScreenModel {


	private static final String BUNDLE = "maintainLoyaltyResources";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainloyalty";
	
	
	private String statusFlag="";
	
	

	private String loyaltyName="";
	private String loyaltyDesc="";
	private String entryPoints="";
	private String fromDate="";
	private String toDate="";
	private String saveFlag="";
	private String statusNew="";
	
	
	private String expiryPeriodValue="";
	private String expiryPeriod="";
	private String status="";
		
	
	
	private String[] selectedParameter;
	private String[] selectedValue;
	private String[] desc;
	
	private String[] parameter;
	private String[] value;
	private String[] parameterSequence;
	private String[] valueInLov;
	private String attribute="";
	private String unit="";
	private String points="";
	private String amount="";
	private String parameterIndex="";
	private String parameterInLOV="";
	private String[] hiddenOpFlag;

	
	private String description="";
	private String screenStatusValue="";
	private String[] checkFlag;
		
	private String bundle;    
	
	private String validateFlag = "";
	
	private String displayPage = "1";
	private String lastPageNum =  "0";
	private String totalRecords = "0";
	private String currentPageNum = "1";
	
	
	private String pageURL = "";

	private boolean dateChanged;
	
	private boolean closeWindow;
	
	private boolean saved;
	private boolean navigate;
	private String unitFlag="";

	

	/**
	 * @return Returns the unitFlag.
	 */
	public String getUnitFlag() {
		return unitFlag;
	}

	/**
	 * @param unitFlag The unitFlag to set.
	 */
	public void setUnitFlag(String unitFlag) {
		this.unitFlag = unitFlag;
	}

	/**
	 * @return Returns the pageURL.
	 */
	public String getPageURL() {
		return pageURL;
	}

	/**
	 * @param pageURL The pageURL to set.
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	/**
	 * @return Returns the currentPageNum.
	 */
	public String getCurrentPageNum() {
		return currentPageNum;
	}

	/**
	 * @param currentPageNum The currentPageNum to set.
	 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
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
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
     * Method to return the product the screen is associated with
     * 
     * @return String
     */
    public String getProduct() {
        return PRODUCT;
    }
    
    /**
     * Method to return the sub product the screen is associated with
     * 
     * @return String
     */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    
    /**
     * Method to return the id the screen is associated with
     * 
     * @return String
     */
    public String getScreenId() {
        return SCREENID;
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
	 * @return Returns the saveFlag.
	 */
	public String getSaveFlag() {
		return saveFlag;
	}

	/**
	 * @param saveFlag The saveFlag to set.
	 */
	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
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
	 * @return Returns the entryPoints.
	 */
	public String getEntryPoints() {
		return entryPoints;
	}

	/**
	 * @param entryPoints The entryPoints to set.
	 */
	public void setEntryPoints(String entryPoints) {
		this.entryPoints = entryPoints;
	}

	/**
	 * @return Returns the expiryPeriod.
	 */
	public String getExpiryPeriod() {
		return expiryPeriod;
	}

	/**
	 * @param expiryPeriod The expiryPeriod to set.
	 */
	public void setExpiryPeriod(String expiryPeriod) {
		this.expiryPeriod = expiryPeriod;
	}

	/**
	 * @return Returns the expiryPeriodValue.
	 */
	public String getExpiryPeriodValue() {
		return expiryPeriodValue;
	}

	/**
	 * @param expiryPeriodValue The expiryPeriodValue to set.
	 */
	public void setExpiryPeriodValue(String expiryPeriodValue) {
		this.expiryPeriodValue = expiryPeriodValue;
	}

	/**
	 * @return Returns the fromDate.
	 */
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
	 * @return Returns the loyaltyDesc.
	 */
	public String getLoyaltyDesc() {
		return loyaltyDesc;
	}

	/**
	 * @param loyaltyDesc The loyaltyDesc to set.
	 */
	public void setLoyaltyDesc(String loyaltyDesc) {
		this.loyaltyDesc = loyaltyDesc;
	}

	/**
	 * @return Returns the loyaltyName.
	 */
	public String getLoyaltyName() {
		return loyaltyName;
	}

	/**
	 * @param loyaltyName The loyaltyName to set.
	 */
	public void setLoyaltyName(String loyaltyName) {
		this.loyaltyName = loyaltyName;
	}

	/**
	 * @return Returns the parameter.
	 */
	public String[] getParameter() {
		return parameter;
	}

	/**
	 * @param parameter The parameter to set.
	 */
	public void setParameter(String[] parameter) {
		this.parameter = parameter;
	}



	/**
	 * @return Returns the selectedParameter.
	 */
	public String[] getSelectedParameter() {
		return selectedParameter;
	}

	/**
	 * @param selectedParameter The selectedParameter to set.
	 */
	public void setSelectedParameter(String[] selectedParameter) {
		this.selectedParameter = selectedParameter;
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
	 * @return Returns the value.
	 */
	public String[] getValue() {
		return value;
	}

	/**
	 * @param value The value to set.
	 */
	public void setValue(String[] value) {
		this.value = value;
	}

	/**
	 * @return Returns the amount.
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return Returns the attribute.
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute The attribute to set.
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return Returns the points.
	 */
	public String getPoints() {
		return points;
	}

	/**
	 * @param points The points to set.
	 */
	public void setPoints(String points) {
		this.points = points;
	}

	/**
	 * @return Returns the units.
	 */
	public String getUnit() {
		return unit;
	}

/**
 * 
 * @param unit
 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return Returns the dateChanged.
	 */
	public boolean isDateChanged() {
		return dateChanged;
	}

	/**
	 * @param dateChanged The dateChanged to set.
	 */
	public void setDateChanged(boolean dateChanged) {
		this.dateChanged = dateChanged;
	}

	/**
	 * @return Returns the parameterIndex.
	 */
	public String getParameterIndex() {
		return parameterIndex;
	}

	/**
	 * @param parameterIndex The parameterIndex to set.
	 */
	public void setParameterIndex(String parameterIndex) {
		this.parameterIndex = parameterIndex;
	}

	/**
	 * @return Returns the parameterSequence.
	 */
	public String[] getParameterSequence() {
		return parameterSequence;
	}

	/**
	 * @param parameterSequence The parameterSequence to set.
	 */
	public void setParameterSequence(String[] parameterSequence) {
		this.parameterSequence = parameterSequence;
	}

	/**
	 * @return Returns the valueInLov.
	 */
	public String[] getValueInLov() {
		return valueInLov;
	}

	/**
	 * @param valueInLov The valueInLov to set.
	 */
	public void setValueInLov(String[] valueInLov) {
		this.valueInLov = valueInLov;
	}

	/**
	 * @return Returns the parameterInLOV.
	 */
	public String getParameterInLOV() {
		return parameterInLOV;
	}

	/**
	 * @param parameterInLOV The parameterInLOV to set.
	 */
	public void setParameterInLOV(String parameterInLOV) {
		this.parameterInLOV = parameterInLOV;
	}

	/**
	 * @return Returns the selectedValue.
	 */
	public String[] getSelectedValue() {
		return selectedValue;
	}

	/**
	 * @param selectedValue The selectedValue to set.
	 */
	public void setSelectedValue(String[] selectedValue) {
		this.selectedValue = selectedValue;
	}

	/**
	 * @return Returns the closeWindow.
	 */
	public boolean isCloseWindow() {
		return closeWindow;
	}

	/**
	 * @param closeWindow The closeWindow to set.
	 */
	public void setCloseWindow(boolean closeWindow) {
		this.closeWindow = closeWindow;
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
	 * @return Returns the desc.
	 */
	public String[] getDesc() {
		return desc;
	}

	/**
	 * @param desc The desc to set.
	 */
	public void setDesc(String[] desc) {
		this.desc = desc;
	}

	/**
	 * @return Returns the navigate.
	 */
	public boolean isNavigate() {
		return navigate;
	}

	/**
	 * @param navigate The navigate to set.
	 */
	public void setNavigate(boolean navigate) {
		this.navigate = navigate;
	}

	/**
	 * @return Returns the saved.
	 */
	public boolean isSaved() {
		return saved;
	}

	/**
	 * @param saved The saved to set.
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	/**
	 * @return Returns the checkFlag.
	 */
	public String[] getCheckFlag() {
		return checkFlag;
	}

	/**
	 * @param checkFlag The checkFlag to set.
	 */
	public void setCheckFlag(String[] checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**
	 * @return Returns the validateFlag.
	 */
	public String getValidateFlag() {
		return validateFlag;
	}

	/**
	 * @param validateFlag The validateFlag to set.
	 */
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	public String getStatusNew() {
		return statusNew;
	}

	public void setStatusNew(String statusNew) {
		this.statusNew = statusNew;
	}

	public String getScreenStatusValue() {
		return screenStatusValue;
	}

	public void setScreenStatusValue(String screenStatusValue) {
		this.screenStatusValue = screenStatusValue;
	}
	public String[] getHiddenOpFlag() {
			return hiddenOpFlag;
		}

	public void setHiddenOpFlag(String[] hiddenOpFlag) {
			this.hiddenOpFlag = hiddenOpFlag;
	}
	
}
