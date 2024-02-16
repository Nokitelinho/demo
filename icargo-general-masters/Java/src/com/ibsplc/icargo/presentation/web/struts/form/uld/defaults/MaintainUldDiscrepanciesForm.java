/*
 * MaintainUldDiscrepanciesForm.java Created on Dec 01, 2011
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
 * 
 * @author a-4823
 *
 */
public class MaintainUldDiscrepanciesForm extends ScreenModel {

	private static final String BUNDLE = "maintainulddiscrepancies";

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.maintainulddiscrepancies";

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

	private String discrepancymode;
	/**
	 * @return the discrepancymode
	 */
	public String getDiscrepancymode() { 
		return discrepancymode;
	}
	/**
	 * @param discrepancymode the discrepancymode to set
	 */
	public void setDiscrepancymode(String discrepancymode) {
		this.discrepancymode = discrepancymode;
	}
	/**
	 * @return the uldNo
	 */
	public String getUldNo() {
		return uldNo;
	}
	/**
	 * @param uldNo the uldNo to set
	 */
	public void setUldNo(String uldNo) {
		this.uldNo = uldNo;
	}
	/**
	 * @return the airlineCode
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return the reportingStation
	 */
	public String getReportingStation() {
		return reportingStation;
	}
	/**
	 * @param reportingStation the reportingStation to set
	 */
	public void setReportingStation(String reportingStation) {
		this.reportingStation = reportingStation;
	}
	/**
	 * @return the ownerStation
	 */
	public String getOwnerStation() {
		return ownerStation;
	}
	/**
	 * @param ownerStation the ownerStation to set
	 */
	public void setOwnerStation(String ownerStation) {
		this.ownerStation = ownerStation;
	}
	/**
	 * @return the listFlag
	 */
	public String getListFlag() {
		return listFlag;
	}
	/**
	 * @param listFlag the listFlag to set
	 */
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}
	/**
	 * @return the uldNoChild
	 */
	public String getUldNoChild() {
		return uldNoChild;
	}
	/**
	 * @param uldNoChild the uldNoChild to set
	 */
	public void setUldNoChild(String uldNoChild) {
		this.uldNoChild = uldNoChild;
	}
	/**
	 * @return the discrepancyCode
	 */
	public String getDiscrepancyCode() {
		return discrepancyCode;
	}
	/**
	 * @param discrepancyCode the discrepancyCode to set
	 */
	public void setDiscrepancyCode(String discrepancyCode) {
		this.discrepancyCode = discrepancyCode;
	}
	/**
	 * @return the discrepancyDate
	 */
	public String getDiscrepancyDate() {
		return discrepancyDate;
	}
	/**
	 * @param discrepancyDate the discrepancyDate to set
	 */
	public void setDiscrepancyDate(String discrepancyDate) {
		this.discrepancyDate = discrepancyDate;
	}
	/**
	 * @return the reportingStationChild
	 */
	public String getReportingStationChild() {
		return reportingStationChild;
	}
	/**
	 * @param reportingStationChild the reportingStationChild to set
	 */
	public void setReportingStationChild(String reportingStationChild) {
		this.reportingStationChild = reportingStationChild;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the discDisableStat
	 */
	public String getDiscDisableStat() {
		return discDisableStat;
	}
	/**
	 * @param discDisableStat the discDisableStat to set
	 */
	public void setDiscDisableStat(String discDisableStat) {
		this.discDisableStat = discDisableStat;
	}
	/**
	 * @return the selectedRows
	 */
	public String getSelectedRows() {
		return selectedRows;
	}
	/**
	 * @param selectedRows the selectedRows to set
	 */
	public void setSelectedRows(String selectedRows) {
		this.selectedRows = selectedRows;
	}
	/**
	 * @return the rowContents
	 */
	public String getRowContents() {
		return rowContents;
	}
	/**
	 * @param rowContents the rowContents to set
	 */
	public void setRowContents(String rowContents) {
		this.rowContents = rowContents;
	}
	/**
	 * @return the displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * @return the lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * @param lastPageNum the lastPageNum to set
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 * @return the totalRecords
	 */
	public String getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * @return the currentPageNum
	 */
	public String getCurrentPageNum() {
		return currentPageNum;
	}
	/**
	 * @param currentPageNum the currentPageNum to set
	 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	/**
	 * @return the uldDisplayPage
	 */
	public String getUldDisplayPage() {
		return uldDisplayPage;
	}
	/**
	 * @param uldDisplayPage the uldDisplayPage to set
	 */
	public void setUldDisplayPage(String uldDisplayPage) {
		this.uldDisplayPage = uldDisplayPage;
	}
	/**
	 * @return the uldLastPageNum
	 */
	public String getUldLastPageNum() {
		return uldLastPageNum;
	}
	/**
	 * @param uldLastPageNum the uldLastPageNum to set
	 */
	public void setUldLastPageNum(String uldLastPageNum) {
		this.uldLastPageNum = uldLastPageNum;
	}
	/**
	 * @return the uldTotalRecords
	 */
	public String getUldTotalRecords() {
		return uldTotalRecords;
	}
	/**
	 * @param uldTotalRecords the uldTotalRecords to set
	 */
	public void setUldTotalRecords(String uldTotalRecords) {
		this.uldTotalRecords = uldTotalRecords;
	}
	/**
	 * @return the uldCurrentPageNum
	 */
	public String getUldCurrentPageNum() {
		return uldCurrentPageNum;
	}
	/**
	 * @param uldCurrentPageNum the uldCurrentPageNum to set
	 */
	public void setUldCurrentPageNum(String uldCurrentPageNum) {
		this.uldCurrentPageNum = uldCurrentPageNum;
	}
	/**
	 * @return the masterRowId
	 */
	public String[] getMasterRowId() {
		return masterRowId;
	}
	/**
	 * @param masterRowId the masterRowId to set
	 */
	public void setMasterRowId(String[] masterRowId) {
		this.masterRowId = masterRowId;
	}
	/**
	 * @return the rowId
	 */
	public String[] getRowId() {
		return rowId;
	}
	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
	/**
	 * @return the fromList
	 */
	public String getFromList() {
		return fromList;
	}
	/**
	 * @param fromList the fromList to set
	 */
	public void setFromList(String fromList) {
		this.fromList = fromList;
	}
	/**
	 * @return the detailsFlag
	 */
	public String getDetailsFlag() {
		return detailsFlag;
	}
	/**
	 * @param detailsFlag the detailsFlag to set
	 */
	public void setDetailsFlag(String detailsFlag) {
		this.detailsFlag = detailsFlag;
	}
	/**
	 * @return the closeFlag
	 */
	public String getCloseFlag() {
		return closeFlag;
	}
	/**
	 * @param closeFlag the closeFlag to set
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}
	
	
	/**
	 * @return the navigate
	 */
	public boolean isNavigate() {
		return navigate;
	}
	/**
	 * @param navigate the navigate to set
	 */
	public void setNavigate(boolean navigate) {
		this.navigate = navigate;
	}
	/**
	 * @return the saveFlag
	 */
	public boolean isSaveFlag() {
		return saveFlag;
	}
	/**
	 * @param saveFlag the saveFlag to set
	 */
	public void setSaveFlag(boolean saveFlag) {
		this.saveFlag = saveFlag;
	}
	/**
	 * @return the pageURL
	 */
	public String getPageURL() {
		return pageURL;
	}
	/**
	 * @param pageURL the pageURL to set
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @return the listStatus
	 */
	public String getListStatus() {
		return listStatus;
	}
	/**
	 * @param listStatus the listStatus to set
	 */
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
	/**
	 * @return the errorStatus
	 */
	public String getErrorStatus() {
		return errorStatus;
	}
	/**
	 * @param errorStatus the errorStatus to set
	 */
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}
	/**
	 * @return the filterStatus
	 */
	public String getFilterStatus() {
		return filterStatus;
	}
	/**
	 * @param filterStatus the filterStatus to set
	 */
	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}
	/**
	 * @return the recordUldNumber
	 */
	public String getRecordUldNumber() {
		return recordUldNumber;
	}
	/**
	 * @param recordUldNumber the recordUldNumber to set
	 */
	public void setRecordUldNumber(String recordUldNumber) {
		this.recordUldNumber = recordUldNumber;
	}
	/**
	 * @return the recordCode
	 */
	public String getRecordCode() {
		return recordCode;
	}
	/**
	 * @param recordCode the recordCode to set
	 */
	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	/**
	 * @return the recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}
	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	/**
	 * @return the recordPOL
	 */
	public String getRecordPOL() {
		return recordPOL;
	}
	/**
	 * @param recordPOL the recordPOL to set
	 */
	public void setRecordPOL(String recordPOL) {
		this.recordPOL = recordPOL;
	}
	/**
	 * @return the recordPOU
	 */
	public String getRecordPOU() {
		return recordPOU;
	}
	/**
	 * @param recordPOU the recordPOU to set
	 */
	public void setRecordPOU(String recordPOU) {
		this.recordPOU = recordPOU;
	}
	/**
	 * @return the recordCurrentStation
	 */
	public String getRecordCurrentStation() {
		return recordCurrentStation;
	}
	/**
	 * @param recordCurrentStation the recordCurrentStation to set
	 */
	public void setRecordCurrentStation(String recordCurrentStation) {
		this.recordCurrentStation = recordCurrentStation;
	}
	/**
	 * @return the buttonStatusFlag
	 */
	public String getButtonStatusFlag() {
		return buttonStatusFlag;
	}
	/**
	 * @param buttonStatusFlag the buttonStatusFlag to set
	 */
	public void setButtonStatusFlag(String buttonStatusFlag) {
		this.buttonStatusFlag = buttonStatusFlag;
	}
	/**
	 * @return the saveOpFlag
	 */
	public String getSaveOpFlag() {
		return saveOpFlag;
	}
	/**
	 * @param saveOpFlag the saveOpFlag to set
	 */
	public void setSaveOpFlag(String saveOpFlag) {
		this.saveOpFlag = saveOpFlag;
	}
	/**
	 * @return the checkList
	 */
	public String getCheckList() {
		return checkList;
	}
	/**
	 * @param checkList the checkList to set
	 */
	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}
	/**
	 * @return the facilityType
	 */
	public String getFacilityType() {
		return facilityType;
	}
	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return the defaultComboValue
	 */
	public String getDefaultComboValue() {
		return defaultComboValue;
	}
	/**
	 * @param defaultComboValue the defaultComboValue to set
	 */
	public void setDefaultComboValue(String defaultComboValue) {
		this.defaultComboValue = defaultComboValue;
	}
	/**
	 * @return the pageLocationLov
	 */
	public Page<ULDDiscrepancyVO> getPageLocationLov() {
		return pageLocationLov;
	}
	/**
	 * @param pageLocationLov the pageLocationLov to set
	 */
	public void setPageLocationLov(Page<ULDDiscrepancyVO> pageLocationLov) {
		this.pageLocationLov = pageLocationLov;
	}
	/**
	 * @return the facilityCode
	 */
	public String getFacilityCode() {
		return facilityCode;
	}
	/**
	 * @param facilityCode the facilityCode to set
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	/**
	 * @return the numChecked
	 */
	public String[] getNumChecked() {
		return numChecked;
	}
	/**
	 * @param numChecked the numChecked to set
	 */
	public void setNumChecked(String[] numChecked) {
		this.numChecked = numChecked;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the saveStatusFlag
	 */
	public String getSaveStatusFlag() {
		return saveStatusFlag;
	}
	/**
	 * @param saveStatusFlag the saveStatusFlag to set
	 */
	public void setSaveStatusFlag(String saveStatusFlag) {
		this.saveStatusFlag = saveStatusFlag;
	}
	/**
	 * @return the oldFacilityType
	 */
	public String getOldFacilityType() {
		return oldFacilityType;
	}
	/**
	 * @param oldFacilityType the oldFacilityType to set
	 */
	public void setOldFacilityType(String oldFacilityType) {
		this.oldFacilityType = oldFacilityType;
	}
	/**
	 * @return the saveStatusPopup
	 */
	public String getSaveStatusPopup() {
		return saveStatusPopup;
	}
	/**
	 * @param saveStatusPopup the saveStatusPopup to set
	 */
	public void setSaveStatusPopup(String saveStatusPopup) {
		this.saveStatusPopup = saveStatusPopup;
	}
	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}
	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}
	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
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
	/**
	 * Method to return the product the screen is associated with
	 * 
	 * @return String
	 */
	public String getProduct() {		
		return PRODUCT;
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
	 * Method to return the sub product the screen is associated with
	 * 
	 * @return String
	 */
	public String getSubProduct() {		
		return SUBPRODUCT;
	}
	
	public String getBundle() {
		return BUNDLE;
	}

	
}
