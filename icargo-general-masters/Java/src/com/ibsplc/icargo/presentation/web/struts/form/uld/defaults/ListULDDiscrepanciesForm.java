/*
 * ListULDDiscrepanciesForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2052
 * 
 */
public class ListULDDiscrepanciesForm extends ScreenModel {

	private static final String BUNDLE = "listulddiscrepancies";

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.listulddiscrepancies";

	private String uldNo;

	private String airlineCode;

	private String reportingStation;

	private String ownerStation;

	private String listFlag;

	private String uldNoChild;

	private String discrepancyCode;

	private String discrepancyDate;

	private String reportingStationChild;

	private String remarks;

	private String discDisableStat = "";

	private String selectedRows;

	private String rowContents;

	private String displayPage = "1";

	private String lastPageNum = "0";

	private String totalRecords = "0";

	private String currentPageNum = "1";

	private String uldDisplayPage = "1";

	private String uldLastPageNum = "0";

	private String uldTotalRecords = "0";

	private String uldCurrentPageNum = "1";

	private String[] masterRowId;

	private String[] rowId;

	private String fromList;

	private String detailsFlag;

	private String closeFlag;

	private String bundle;

	private boolean navigate;

	private boolean saveFlag;

	private String pageURL = "";

	private String flag = "";

	private String listStatus = "";

	private String errorStatus = "";

	private String filterStatus = "";

	private String recordUldNumber = "";

	private String recordCode = "";

	private String recordDate = "";

	private String recordPOL = "";

	private String recordPOU = "";

	private String recordCurrentStation = "";

	private String buttonStatusFlag;

	private String saveOpFlag;

	private String checkList;
	
	private String discrepancyStatus ;
	//Added by A-5220 for ICRD-22824 starts
	public static final String NAV_MOD_LIST = "list";
	public static final String NAV_MOD_PAGINATION = "navigation";
	private String navigationMode;
	/**
	 * @return the navigationMode
	 */
	public String getNavigationMode() {
		return navigationMode;
	}
	/**
	 * @param navigationMode the navigationMode to set
	 */
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
	//Added by A-5220 for ICRD-22824 ends

	
	/**
	 * 
	 * @return
	 */
	public String getCheckList() {
		return checkList;
	}

	/**
	 * 
	 * @param checkList
	 */
	public void setCheckList(String checkList) {
		this.checkList = checkList;
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
	 * 
	 */
	public String getBundle() {
		return BUNDLE;
	}
	//Added by Sreekumar S as a part of AirNZ434 
	private String facilityType;
	private String defaultComboValue;
	private Page<ULDDiscrepancyVO> pageLocationLov = null;
	private String facilityCode;
	private String[] numChecked;
	private String location;
	private String saveStatusFlag="";
	private String oldFacilityType;
	private String saveStatusPopup;
	private String index;
	private String locationName;
//	Added by Sreekumar S as a part of AirNZ434 ends
	
	
	

	

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getSaveStatusPopup() {
		return saveStatusPopup;
	}

	public void setSaveStatusPopup(String saveStatusPopup) {
		this.saveStatusPopup = saveStatusPopup;
	}

	public String getOldFacilityType() {
		return oldFacilityType;
	}

	public void setOldFacilityType(String oldFacilityType) {
		this.oldFacilityType = oldFacilityType;
	}

	public String getSaveStatusFlag() {
		return saveStatusFlag;
	}

	public void setSaveStatusFlag(String saveStatusFlag) {
		this.saveStatusFlag = saveStatusFlag;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String[] getNumChecked() {
		return numChecked;
	}

	public void setNumChecked(String[] numChecked) {
		this.numChecked = numChecked;
	}

	public String getFacilityCode() {
		return facilityCode;
	}

	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}


	/**
	 * @return Returns the rowContents.
	 */
	public String getRowContents() {
		return rowContents;
	}

	/**
	 * @param rowContents
	 *            The rowContents to set.
	 */
	public void setRowContents(String rowContents) {
		this.rowContents = rowContents;
	}

	/**
	 * @return Returns the selectedRows.
	 */
	public String getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows
	 *            The selectedRows to set.
	 */
	public void setSelectedRows(String selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * @return Returns the flag.
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            The flag to set.
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return Returns the masterRowId.
	 */
	public String[] getMasterRowId() {
		return masterRowId;
	}

	/**
	 * @param masterRowId
	 *            The masterRowId to set.
	 */
	public void setMasterRowId(String[] masterRowId) {
		this.masterRowId = masterRowId;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId
	 *            The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the currentPageNum.
	 */
	public String getCurrentPageNum() {
		return currentPageNum;
	}

	/**
	 * @param currentPageNum
	 *            The currentPageNum to set.
	 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	/**
	 * @return Returns the discrepancyCode.
	 */
	public String getDiscrepancyCode() {
		return discrepancyCode;
	}

	/**
	 * @param discrepancyCode
	 *            The discrepancyCode to set.
	 */
	public void setDiscrepancyCode(String discrepancyCode) {
		this.discrepancyCode = discrepancyCode;
	}

	/**
	 * @return Returns the discrepancyDate.
	 */
	public String getDiscrepancyDate() {
		return discrepancyDate;
	}

	/**
	 * @param discrepancyDate
	 *            The discrepancyDate to set.
	 */
	public void setDiscrepancyDate(String discrepancyDate) {
		this.discrepancyDate = discrepancyDate;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage
	 *            The displayPage to set.
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
	 * @param lastPageNum
	 *            The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the listFlag.
	 */
	public String getListFlag() {
		return listFlag;
	}

	/**
	 * @param listFlag
	 *            The listFlag to set.
	 */
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

	/**
	 * @return Returns the ownerStation.
	 */
	public String getOwnerStation() {
		return ownerStation;
	}

	/**
	 * @param ownerStation
	 *            The ownerStation to set.
	 */
	public void setOwnerStation(String ownerStation) {
		this.ownerStation = ownerStation;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the reportingStation.
	 */
	public String getReportingStation() {
		return reportingStation;
	}

	/**
	 * @param reportingStation
	 *            The reportingStation to set.
	 */
	public void setReportingStation(String reportingStation) {
		this.reportingStation = reportingStation;
	}

	/**
	 * @return Returns the reportingStationChild.
	 */
	public String getReportingStationChild() {
		return reportingStationChild;
	}

	/**
	 * @param reportingStationChild
	 *            The reportingStationChild to set.
	 */
	public void setReportingStationChild(String reportingStationChild) {
		this.reportingStationChild = reportingStationChild;
	}

	/**
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords
	 *            The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return Returns the uldNo.
	 */
	public String getUldNo() {
		return uldNo;
	}

	/**
	 * @param uldNo
	 *            The uldNo to set.
	 */
	public void setUldNo(String uldNo) {
		this.uldNo = uldNo;
	}

	/**
	 * @return Returns the uldNoChild.
	 */
	public String getUldNoChild() {
		return uldNoChild;
	}

	/**
	 * @param uldNoChild
	 *            The uldNoChild to set.
	 */
	public void setUldNoChild(String uldNoChild) {
		this.uldNoChild = uldNoChild;
	}

	/**
	 * @return Returns the fromList.
	 */
	public String getFromList() {
		return fromList;
	}

	/**
	 * @param fromList
	 *            The fromList to set.
	 */
	public void setFromList(String fromList) {
		this.fromList = fromList;
	}

	/**
	 * @return Returns the uldCurrentPageNum.
	 */
	public String getUldCurrentPageNum() {
		return uldCurrentPageNum;
	}

	/**
	 * @param uldCurrentPageNum
	 *            The uldCurrentPageNum to set.
	 */
	public void setUldCurrentPageNum(String uldCurrentPageNum) {
		this.uldCurrentPageNum = uldCurrentPageNum;
	}

	/**
	 * @return Returns the uldDisplayPage.
	 */
	public String getUldDisplayPage() {
		return uldDisplayPage;
	}

	/**
	 * @param uldDisplayPage
	 *            The uldDisplayPage to set.
	 */
	public void setUldDisplayPage(String uldDisplayPage) {
		this.uldDisplayPage = uldDisplayPage;
	}

	/**
	 * @return Returns the uldLastPageNum.
	 */
	public String getUldLastPageNum() {
		return uldLastPageNum;
	}

	/**
	 * @param uldLastPageNum
	 *            The uldLastPageNum to set.
	 */
	public void setUldLastPageNum(String uldLastPageNum) {
		this.uldLastPageNum = uldLastPageNum;
	}

	/**
	 * @return Returns the uldTotalRecords.
	 */
	public String getUldTotalRecords() {
		return uldTotalRecords;
	}

	/**
	 * @param uldTotalRecords
	 *            The uldTotalRecords to set.
	 */
	public void setUldTotalRecords(String uldTotalRecords) {
		this.uldTotalRecords = uldTotalRecords;
	}

	/**
	 * @return Returns the detailsFlag.
	 */
	public String getDetailsFlag() {
		return detailsFlag;
	}

	/**
	 * @param detailsFlag
	 *            The detailsFlag to set.
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
	 * @param closeFlag
	 *            The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return Returns the buttonStatusFlag.
	 */
	public String getButtonStatusFlag() {
		return buttonStatusFlag;
	}

	/**
	 * @param buttonStatusFlag
	 *            The buttonStatusFlag to set.
	 */
	public void setButtonStatusFlag(String buttonStatusFlag) {
		this.buttonStatusFlag = buttonStatusFlag;
	}

	/**
	 * @return Returns the saveOpFlag.
	 */
	public String getSaveOpFlag() {
		return saveOpFlag;
	}

	/**
	 * @param saveOpFlag
	 *            The saveOpFlag to set.
	 */
	public void setSaveOpFlag(String saveOpFlag) {
		this.saveOpFlag = saveOpFlag;
	}

	/**
	 * 
	 * 
	 */
	public void resetForm() {
		uldNo = "";
		airlineCode = "";
		reportingStation = "";
		ownerStation = "";
		buttonStatusFlag = "";
		saveOpFlag = "";
	}

	/**
	 * @return Returns the navigate.
	 */
	public boolean isNavigate() {
		return navigate;
	}

	/**
	 * @param navigate
	 *            The navigate to set.
	 */
	public void setNavigate(boolean navigate) {
		this.navigate = navigate;
	}

	/**
	 * @return Returns the saveFlag.
	 */
	public boolean isSaveFlag() {
		return saveFlag;
	}

	/**
	 * @param saveFlag
	 *            The saveFlag to set.
	 */
	public void setSaveFlag(boolean saveFlag) {
		this.saveFlag = saveFlag;
	}

	/**
	 * @return Returns the pageURL.
	 */
	public String getPageURL() {
		return pageURL;
	}

	/**
	 * @param pageURL
	 *            The pageURL to set.
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	/**
	 * @return Returns the listStatus.
	 */
	public String getListStatus() {
		return listStatus;
	}

	/**
	 * @param listStatus
	 *            The listStatus to set.
	 */
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	/**
	 * @return Returns the errorStatus.
	 */
	public String getErrorStatus() {
		return errorStatus;
	}

	/**
	 * @param errorStatus
	 *            The errorStatus to set.
	 */
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}

	/**
	 * @return Returns the filterStatus.
	 */
	public String getFilterStatus() {
		return filterStatus;
	}

	/**
	 * @param filterStatus
	 *            The filterStatus to set.
	 */
	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}

	/**
	 * @return Returns the recordCode.
	 */
	public String getRecordCode() {
		return recordCode;
	}

	/**
	 * @param recordCode
	 *            The recordCode to set.
	 */
	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	/**
	 * @return Returns the recordCurrentStation.
	 */
	public String getRecordCurrentStation() {
		return recordCurrentStation;
	}

	/**
	 * @param recordCurrentStation
	 *            The recordCurrentStation to set.
	 */
	public void setRecordCurrentStation(String recordCurrentStation) {
		this.recordCurrentStation = recordCurrentStation;
	}

	/**
	 * @return Returns the recordDate.
	 */
	public String getRecordDate() {
		return recordDate;
	}

	/**
	 * @param recordDate
	 *            The recordDate to set.
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	/**
	 * @return Returns the recordPOL.
	 */
	public String getRecordPOL() {
		return recordPOL;
	}

	/**
	 * @param recordPOL
	 *            The recordPOL to set.
	 */
	public void setRecordPOL(String recordPOL) {
		this.recordPOL = recordPOL;
	}

	/**
	 * @return Returns the recordPOU.
	 */
	public String getRecordPOU() {
		return recordPOU;
	}

	/**
	 * @param recordPOU
	 *            The recordPOU to set.
	 */
	public void setRecordPOU(String recordPOU) {
		this.recordPOU = recordPOU;
	}

	/**
	 * @return Returns the recordUldNumber.
	 */
	public String getRecordUldNumber() {
		return recordUldNumber;
	}

	/**
	 * @param recordUldNumber
	 *            The recordUldNumber to set.
	 */
	public void setRecordUldNumber(String recordUldNumber) {
		this.recordUldNumber = recordUldNumber;
	}

	/**
	 * @return Returns the discDisableStat.
	 */
	public String getDiscDisableStat() {
		return discDisableStat;
	}

	/**
	 * @param discDisableStat
	 *            The discDisableStat to set.
	 */
	public void setDiscDisableStat(String discDisableStat) {
		this.discDisableStat = discDisableStat;
	}
	/**
	 * 
	 * @return
	 */
	public String getFacilityType() {
		return facilityType;
	}
	/**
	 * 
	 * @param facilityType
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * 
	 * @return
	 */
	public String getDefaultComboValue() {
		return defaultComboValue;
	}
	/**
	 * 
	 * @param defaultComboValue
	 */
	public void setDefaultComboValue(String defaultComboValue) {
		this.defaultComboValue = defaultComboValue;
	}

	public Page<ULDDiscrepancyVO> getPageLocationLov() {
		return pageLocationLov;
	}

	public void setPageLocationLov(Page<ULDDiscrepancyVO> pageLocationLov) {
		this.pageLocationLov = pageLocationLov;
	}
	public String getDiscrepancyStatus() {
		return discrepancyStatus;
	}
	public void setDiscrepancyStatus(String discrepancyStatus) {
		this.discrepancyStatus = discrepancyStatus;
	}

}
