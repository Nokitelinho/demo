package com.ibsplc.icargo.presentation.web.struts.form.reco.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

public class RegulatoryComposeMessageForm extends ScreenModel{

    
	/** The Constant BUNDLE. */
	private static final String BUNDLE = "regulatorycomposemessage";  

	/** The Constant PRODUCT. */
	private static final String PRODUCT = "reco";

	/** The Constant SUBPRODUCT. */
	private static final String SUBPRODUCT = "defaults";

	/** The Constant SCREENID. */
	private static final String SCREENID = "reco.defaults.maintainregulatorycompliance";

	/** The Constant PAGINATION_MODE_FROM_FILTER. */
	public static final String PAGINATION_MODE_FROM_FILTER="FILTER";

	/** The Constant PAGINATION_MODE_FROM_NAVIGATION. */
	public static final String PAGINATION_MODE_FROM_NAVIGATION="NAVIGATION";



	private String roleGroup;
	
	private String startDate;
	
	private String endDate;

	private String[] roleGroups;
	
	private String[] messages; 
	
	private String[] startDates;
	
	private String[] endDates;
	
	private String[] serialNumbers;

	/** The mst chexk. */
	private String[] mstChexk;

	/** The is unsaved present. */
	private String isUnsavedPresent;

	/* For Pagination */	
	/** The display page. */
	private String displayPage = "1";

	/** The last page num. */
	private String lastPageNum = "0";

	/** The absolute index. */
	private String absoluteIndex="0";

	/** The navigation mode. */
	private String navigationMode;

	//hidden fields
	/** The save flag. */
	private String saveFlag="";

	/** The delete flag. */
	private String deleteFlag="";


	/** The hidden op flag. */
	private String[] hiddenOpFlag;

	/**
	 * Method to return the product the screen is associated with.
	 *
	 * @return String
	 */
	public String getProduct() {
	    return PRODUCT;
	}

	/**
	 * Method to return the sub product the screen is associated with.
	 *
	 * @return String
	 */
	public String getSubProduct() {
	    return SUBPRODUCT;
	}

	/**
	 * Method to return the id the screen is associated with.
	 *
	 * @return String
	 */
	public String getScreenId() {
	    return SCREENID;
	}

	/**
	 * Gets the bundle.
	 *
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	public String getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}

	public String[] getRoleGroups() {
		return roleGroups;
	}

	public void setRoleGroups(String[] roleGroups) {
		this.roleGroups = roleGroups;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public String[] getSerialNumbers() {
		return serialNumbers;
	}

	public void setSerialNumbers(String[] serialNumbers) {
		this.serialNumbers = serialNumbers;
	}

	public String[] getMstChexk() {
		return mstChexk;
	}

	public void setMstChexk(String[] mstChexk) {
		this.mstChexk = mstChexk;
	}

	public String getIsUnsavedPresent() {
		return isUnsavedPresent;
	}

	public void setIsUnsavedPresent(String isUnsavedPresent) {
		this.isUnsavedPresent = isUnsavedPresent;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public String getAbsoluteIndex() {
		return absoluteIndex;
	}

	public void setAbsoluteIndex(String absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	public String getNavigationMode() {
		return navigationMode;
	}

	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}

	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String[] getStartDates() {
		return startDates;
	}

	public void setStartDates(String[] startDates) {
		this.startDates = startDates;
	}

	public String[] getEndDates() {
		return endDates;
	}

	public void setEndDates(String[] endDates) {
		this.endDates = endDates;
	}

}
