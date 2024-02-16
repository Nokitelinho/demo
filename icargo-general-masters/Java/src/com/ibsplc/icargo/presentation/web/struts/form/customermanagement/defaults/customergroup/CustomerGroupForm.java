/*
 * CustomerGroupForm.java Created on May 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup;



import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2122
 *
 */
public class CustomerGroupForm extends ScreenModel {
	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID ="customermanagement.defaults.customergroup";
	/**
	 * PRODUCT_NAME
	 */
	private static final String PRODUCT_NAME = "customermanagement";
	/**
	 * SUBPRODUCT_NAME
	 */
	private static final String SUBPRODUCT_NAME = "defaults";
	/**
	 * BUNDLE
	 */
	private static final String BUNDLE ="customergroupResources";

	private String companyCode;	
	private String customerGroupCode;
	private String groupName;
	private String station;
	//private String groupCode;
	//private String loyaltyCode;
	private String remarks;
	private String statusFlag;	
	private String detailsFlag;
	private String closeFlag;
	private String groupCodesSelected;
	private String displayPage="1";
	private String lastPageNum="0";
	private String currentPage = "1";
	private String totalRecords = "0";
	
    /* *** added for showing save message starts  **** */
    private String saveSuccessFlag;
    private String screenModifiedFlag;
    private String currentDialogOption;
    private String currentDialogId;
    /* *** added for showing save message ends  **** */
    
	/**
	 * bundle
	 */
	private String bundle;
	
	
	
	
	
	/**
	 *
	 * @return String
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
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/*/**
	 * @return Returns the groupCode.
	 */
/*	public String getGroupCode() {
		return groupCode;
	}*/
	/*/**
	 * @param groupCode The groupCode to set.
	 */
	/*public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}*/
	
	/**
	 * @return Returns the customerGroupCode.
	 */
	
	
	public String getCustomerGroupCode() {
		return customerGroupCode;
	}
	/**
	 * @param customerGroupCode The customerGroupCode to set.
	 */
	public void setCustomerGroupCode(String customerGroupCode) {
		this.customerGroupCode = customerGroupCode;
	}
	/**
	 * @return Returns the groupName.
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName The groupName to set.
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	/**
	 * @return Returns the station.
	 */
	public String getStation() {
		return station;
	}
	/**
	 * @param station The station to set.
	 */
	public void setStation(String station) {
		this.station = station;
	}
	
	
//	/**
//	 * @return Returns the loyaltyCode.
//	 */
//	public String getLoyaltyCode() {
//		return loyaltyCode;
//	}
//	/**
//	 * @param loyaltyCode The loyaltyCode to set.
//	 */
//	public void setLoyaltyCode(String loyaltyCode) {
//		this.loyaltyCode = loyaltyCode;
//	}
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
	 * @return Returns the detailsFlag.
	 */
	public String getDetailsFlag() {
		return detailsFlag;
	}
	/**
	 * @param detailsFlag The detailsFlag to set.
	 */
	public void setDetailsFlag(String detailsFlag) {
		this.detailsFlag = detailsFlag;
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
	 * @return Returns the groupCodesSelected.
	 */
	public String getGroupCodesSelected() {
		return groupCodesSelected;
	}
	/**
	 * @param groupCodesSelected The groupCodesSelected to set.
	 */
	public void setGroupCodesSelected(String groupCodesSelected) {
		this.groupCodesSelected = groupCodesSelected;
	}
	/**
	 * 
	 * @return Returns the displayPage.
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
	 * @return Returns the lastPageNum.
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
	 * @return Returns the currentPage.
	 */
	public String getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
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
     * 
     * @return
     */
    
    public String getSaveSuccessFlag() {
        return saveSuccessFlag;
    }
    /***
     * 
     * @param saveSuccessFlag
     */
    public void setSaveSuccessFlag(String saveSuccessFlag) {
        this.saveSuccessFlag = saveSuccessFlag;
    }
    /***
     * 
     * @return
     */
    public String getScreenModifiedFlag() {
        return screenModifiedFlag;
    }
    /**
     * 
     * @param screenModifiedFlag
     */
    public void setScreenModifiedFlag(String screenModifiedFlag) {
        this.screenModifiedFlag = screenModifiedFlag;
    }
    /**
     * 
     * @return
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
     * 
     * @return
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
	
    /* *** added for showing save message ends **** */
}
