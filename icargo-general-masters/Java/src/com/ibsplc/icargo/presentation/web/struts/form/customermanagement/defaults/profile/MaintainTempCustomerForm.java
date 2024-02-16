/*
 * MaintainTempCustomerForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1496
 *
 */
public class MaintainTempCustomerForm extends ScreenModel {


	private static final String BUNDLE = "maintaintempcustomerform";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.maintaintempcustomerform";


	private String bundle;
	private String tempId;
	private String customerName;
	private String station;
	private String address;
	private String emailId;
	private String phoneNo;
	//added by Deepu for CR HA117 starts
	private String countryCode;
	private String cityCode;
	private String zipCode;
	private String addressTwo;
	private String mobileNumber;
	private String faxNumber;
	private String state;
	//added by Deepu for CR HA117 ends
	private String createdBy;
	private String active;
	private String remark;
	private String displayPage="1";
	private String lastPageNum="0";
	private String currentDialogId;
	private String currentDialogOption;
	private String[] rowId;
	private String pageURL = "";
	private String closeFlag;
	private boolean navigate;
	private boolean saveFlag;
	private String detailsFlag;
	private String operationMode;//to know the next operation is update or insert
	private String saveStatus;//to know whether save operation is success or not
	private String checkFlag;
	private String custCodeFlag;
	
	
	
	/**
	 * @return Returns the custCodeFlag.
	 */
	public String getCustCodeFlag() {
		return custCodeFlag;
	}
	/**
	 * @param custCodeFlag The custCodeFlag to set.
	 */
	public void setCustCodeFlag(String custCodeFlag) {
		this.custCodeFlag = custCodeFlag;
	}
	/**
	 * @return Returns the checkFlag.
	 */
	public String getCheckFlag() {
		return checkFlag;
	}
	/**
	 * @param checkFlag The checkFlag to set.
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	/**
	 * @return Returns the saveStatus.
	 */
	public String getSaveStatus() {
		return saveStatus;
	}
	/**
	 * @param saveStatus The saveStatus to set.
	 */
	public void setSaveStatus(String saveStatus) {
		this.saveStatus = saveStatus;
	}
	/**
	 * @return Returns the operationMode.
	 */
	public String getOperationMode() {
		return operationMode;
	}
	/**
	 * @param operationMode The operationMode to set.
	 */
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}
	/**
	 * 
	 * @return
	 */
	public String getDetailsFlag() {
		return detailsFlag;
	}
	/**
	 * 
	 * @param detailsFlag
	 */
	public void setDetailsFlag(String detailsFlag) {
		this.detailsFlag = detailsFlag;
	}
	/**
	 * 
	 * @return saveFlag
	 */
	public boolean getSaveFlag() {
		return saveFlag;
	}
	/**
	 * 
	 * @param saveFlag
	 */
	public void setSaveFlag(boolean saveFlag) {
		this.saveFlag = saveFlag;
	}
	/**
	 * 
	 * @return navigate
	 */
	public boolean isNavigate() {
		return navigate;
	}
	/**
	 * 
	 * @param navigate
	 */
	public void setNavigate(boolean navigate) {
		this.navigate = navigate;
	}
	/**
	 * 
	 * @return closeFlag
	 */
	public String getCloseFlag() {
		return closeFlag;
	}
	/**
	 * 
	 * @param closeFlag
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}
	/**
	 * 
	 * @return pageURL
	 */
	public String getPageURL() {
		return pageURL;
	}
	/**
	 * 
	 * @param pageURL
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	/**
	 * 
	 * @return rowId
	 */
	public String[] getRowId() {
		return rowId;
	}
	/**
	 * 
	 * @param rowId
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
	/**
	 * 
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 
	 * @return active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * 
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * 
	 * @return createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * 
	 * @param createdBy
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * 
	 * @return phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 * 
	 * @param phoneNo
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	/**
	 * 
	 * @return emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * 
	 * @param emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 
	 * @return station
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
	 * @return tempId
	 */
    public String getTempId() {
		return tempId;
	}
    /**
     * 
     * @param tempId
     */
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	/**
	 * 
	 * @return customerName 
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * 
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
/***
 * 
 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    /**
     * 
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
	 * @return displayPage
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
	 * @return lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * 
	 * @param lastPageNum
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 * 
	 * @return currentDialogId
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}
	/**
	 * 
	 * @param currentDialogId
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}
	/**
	 * 
	 * @return currentDialogOption
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}
	/**
	 * 
	 * @param currentDialogOption
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}
	/**
	 * @return the addressTwo
	 */
	public String getAddressTwo() {
		return addressTwo;
	}
	/**
	 * @param addressTwo the addressTwo to set
	 */
	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}
	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}
	/**
	 * @param faxNumber the faxNumber to set
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
    
    
}
