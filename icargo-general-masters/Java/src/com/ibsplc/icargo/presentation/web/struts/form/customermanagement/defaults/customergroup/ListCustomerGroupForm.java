/*
 * ListCustomerGroupForm.java Created on May 8, 2006
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
public class ListCustomerGroupForm extends ScreenModel {
	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID ="customermanagement.defaults.listcustomergroup";
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
	private static final String BUNDLE ="listcustomergroupResources";

	private String companyCode;
	private String groupCode;
	private String groupName;
	private String[] groupCodes;
	private String[] groupNames;
	private String[] station;
	private String[] loyaltyCode;
	private int[] loyaltyPoints;
	private String[] remarks;
	private String[] selectAll;
	private String[] select;	
	private String[] groupCodesSelected;
	private String createFlag;
	private String displayPage="1";
	private String lastPageNum="0";
	private String groupCodeAttach ="";
 	private String groupNameAttach ="";
 	private String[] loyaltyPgm;
	private String[] pgmFromDate;
	private String[] pgmToDate;
	private String[] attachFromDate;
	private String[] attachToDate;
	private String[] operationalFlag;
	private String statusFlag ="";
	private String[] selectedRows;
    
    /* ****** added by kiran ****** */
    private String absIdx;
	
	/**
	 * bundle
	 */
	private String bundle;
	
	
	// Added by A-5183 for < ICRD-22065 > Starts
	
	public static final String PAGINATION_MODE_FROM_LIST = "LIST";
	public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "NAVIGATION";
	private String navigationMode;
	
	public String getNavigationMode() {
		return navigationMode;
	}

	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
		
	// Added by A-5183 for < ICRD-22065 > Ends
	
	
	
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

	/**
	 * @return Returns the groupCode.
	 */
	public String getGroupCode() {
		return groupCode;
	}
	/**
	 * @param groupCode The groupCode to set.
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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
	 * @return Returns the groupCodes.
	 */
	public String[] getGroupCodes() {
		return groupCodes;
	}
	/**
	 * @param groupCodes The groupCodes to set.
	 */
	public void setGroupCodes(String[] groupCodes) {
		this.groupCodes = groupCodes;
	}
	/**
	 * @return Returns the groupNames.
	 */
	public String[] getGroupNames() {
		return groupNames;
	}
	/**
	 * @param groupNames The groupNames to set.
	 */
	public void setGroupNames(String[] groupNames) {
		this.groupNames = groupNames;
	}
	/**
	 * @return Returns the loyaltyCode.
	 */
	public String[] getLoyaltyCode() {
		return loyaltyCode;
	}
	/**
	 * @param loyaltyCode The loyaltyCode to set.
	 */
	public void setLoyaltyCode(String[] loyaltyCode) {
		this.loyaltyCode = loyaltyCode;
	}
	/**
	 * @return Returns the loyaltyPoints.
	 */
	public int[] getLoyaltyPoints() {
		return loyaltyPoints;
	}
	/**
	 * @param loyaltyPoints The loyaltyPoints to set.
	 */
	public void setLoyaltyPoints(int[] loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String[] getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the station.
	 */
	public String[] getStation() {
		return station;
	}
	/**
	 * @param station The station to set.
	 */
	public void setStation(String[] station) {
		this.station = station;
	}
	/**
	 * @return Returns the selectAll.
	 */
	public String[] getSelectAll() {
		return selectAll;
	}
	/**
	 * @param selectAll The selectAll to set.
	 */
	public void setSelectAll(String[] selectAll) {
		this.selectAll = selectAll;
	}

	/**
	 * @return Returns the select.
	 */
	public String[] getSelect() {
		return select;
	}
	/**
	 * @param select The select to set.
	 */
	public void setSelect(String[] select) {
		this.select = select;
	}
	
	/**
	 * @return Returns the groupCodesSelected.
	 */
	public String[] getGroupCodesSelected() {
		return groupCodesSelected;
	}
	/**
	 * @param groupCodesSelected The groupCodesSelected to set.
	 */
	public void setGroupCodesSelected(String[] groupCodesSelected) {
		this.groupCodesSelected = groupCodesSelected;
	}
	/**
	 * @return Returns the createFlag.
	 */
	public String getCreateFlag() {
		return createFlag;
	}
	/**
	 * @param createFlag The createFlag to set.
	 */
	public void setCreateFlag(String createFlag) {
		this.createFlag = createFlag;
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
	 * 
	 * @return
	 */
	public String getGroupCodeAttach() {
		return groupCodeAttach;
	}
	/**
	 * 
	 * @param groupCodeAttach
	 */
	public void setGroupCodeAttach(String groupCodeAttach) {
		this.groupCodeAttach = groupCodeAttach;
	}
	/**
	 * 
	 * @return
	 */
	public String getGroupNameAttach() {
		return groupNameAttach;
	}
	/**
	 * 
	 * @param groupNameAttach
	 */
	public void setGroupNameAttach(String groupNameAttach) {
		this.groupNameAttach = groupNameAttach;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getAttachFromDate() {
		return attachFromDate;
	}
	/**
	 * 
	 * @param attachFromDate
	 */
	public void setAttachFromDate(String[] attachFromDate) {
		this.attachFromDate = attachFromDate;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getAttachToDate() {
		return attachToDate;
	}
	/**
	 * 
	 * @param attachToDate
	 */
	public void setAttachToDate(String[] attachToDate) {
		this.attachToDate = attachToDate;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getLoyaltyPgm() {
		return loyaltyPgm;
	}
	/**
	 * 
	 * @param loyaltyPgm
	 */
	public void setLoyaltyPgm(String[] loyaltyPgm) {
		this.loyaltyPgm = loyaltyPgm;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * 
	 * @param operationalFlag
	 */
	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getPgmFromDate() {
		return pgmFromDate;
	}
	/**
	 * 
	 * @param pgmFromDate
	 */
	public void setPgmFromDate(String[] pgmFromDate) {
		this.pgmFromDate = pgmFromDate;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getPgmToDate() {
		return pgmToDate;
	}
	/**
	 * 
	 * @param pgmToDate
	 */
	public void setPgmToDate(String[] pgmToDate) {
		this.pgmToDate = pgmToDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getStatusFlag() {
		return statusFlag;
	}
	/**
	 * 
	 * @param statusFlag
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}
	/**
	 * 
	 * @param selectedRows
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}
    /**
     * 
     * @return
     */
    public String getAbsIdx() {
        return absIdx;
    }
    /**
     * 
     * @param absIdx
     */
    public void setAbsIdx(String absIdx) {
        this.absIdx = absIdx;
    }
    
    
    
	
}
