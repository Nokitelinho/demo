/*
 * ListDamageReportForm.java Created on Feb 19,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-2052
 *
 */

public class ListDamageReportForm extends ScreenModel {

	/*
	 * The constant variable for product capacity
	 *
	 */
	private static final String PRODUCT = "uld";
	/*
	 * The constant for sub product routing
	 */
	private static final String SUBPRODUCT = "defaults";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "uld.defaults.listdamagereport";

    /**
	 * Constructor
	 */
	private static final String BUNDLE = "listDamageReportResources";
	
	 private String uldNo;
	 private String damageRefNo;
	 private String uldTypeCode;
	 private String uldStatus;
	 private String uldDamageStatus;
	 private String currentStn;
	 private String reportedStn;
	 private String repairedDateFrom;
	 private String repairedDateTo;
	 private String listDamagePicPresent="";
	 private String damageDisableStatus="";
	 
	 private String primaryKey;
	 private String seqNum;
	 private String selectedULDNum;
	 private String[] masterRowId;
	 private String[] rowId;
	 private String[] operationalFlag;
	 private String[] uldNoTable;
	 private String[] damageRefNoTable;
	 private String[] reportedStnTable;
	 private String[] currentStnTable;
	 private String[] reportedByTable;
	 private String[] repairStatusTable;
	 private String[] uldStatusTable;
	 private String[] remarksTable;
	 
	 private String bundle;
  
	 private String displayPage="1";
	 private String lastPageNum="0";
	 private String totalRecordsDmg;
	 private String currentPageNumDmg;
  
    private String pageURL="";
    private Page listPage;
	private String listStatus;
	
	//Added by Tarun for CRQ AirNZ418
	private String facilityType;
	private String location;
	private String partyType;
	private String party;
	private String currentStation;
	
	//added by A-5223 for ICRD-22824 starts
	public static final String PAGINATION_MODE_FROM_FILTER = "LIST";
	
	public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "LINK";
	
	private String paginationMode;
	
	//added by A-5223 for ICRD-22824 ends
	
	/**
	 * @return the currentStation
	 */
	public String getCurrentStation() {
		return currentStation;
	}
	/**
	 * @param currentStation the currentStation to set
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
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
	 * @return the party
	 */
	public String getParty() {
		return party;
	}
	/**
	 * @param party the party to set
	 */
	public void setParty(String party) {
		this.party = party;
	}
	/**
	 * @return the partyType
	 */
	public String getPartyType() {
		return partyType;
	}
	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	public String getListStatus() {
		return listStatus;
	}
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
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
	 * @return Returns the currentPageNum.
	 */
	public String getCurrentPageNumDmg() {
		return currentPageNumDmg;
	}
	/**
	 * @param currentPageNumDmg
	 */
	public void setCurrentPageNumDmg(String currentPageNumDmg) {
		this.currentPageNumDmg = currentPageNumDmg;
	}
	/**
	 * @return Returns the currentStn.
	 */
	public String getCurrentStn() {
		return currentStn;
	}
	/**
	 * @param currentStn The currentStn to set.
	 */
	public void setCurrentStn(String currentStn) {
		this.currentStn = currentStn;
	}
	/**
	 * @return Returns the currentStnTable.
	 */
	public String[] getCurrentStnTable() {
		return currentStnTable;
	}
	/**
	 * @param currentStnTable The currentStnTable to set.
	 */
	public void setCurrentStnTable(String[] currentStnTable) {
		this.currentStnTable = currentStnTable;
	}
	/**
	 * @return Returns the damageRefNo.
	 */
	public String getDamageRefNo() {
		return damageRefNo;
	}
	/**
	 * @param damageRefNo The damageRefNo to set.
	 */
	public void setDamageRefNo(String damageRefNo) {
		this.damageRefNo = damageRefNo;
	}
	/**
	 * @return Returns the damageRefNoTable.
	 */
	public String[] getDamageRefNoTable() {
		return damageRefNoTable;
	}
	/**
	 * @param damageRefNoTable The damageRefNoTable to set.
	 */
	public void setDamageRefNoTable(String[] damageRefNoTable) {
		this.damageRefNoTable = damageRefNoTable;
	}
	//Modified by A-7426 for ICRD-200770 starts
	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	//Modified by A-7426 for ICRD-200770 starts
	/**
	 * @return Returns the listPage.
	 */
	public Page getListPage() {
		return listPage;
	}
	/**
	 * @param listPage
	 */
	public void setListPage(Page listPage) {
		this.listPage = listPage;
	}
	//Modified by A-7426 for ICRD-200770 starts
	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * @param lastPageNum
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	//Modified by A-7426 for ICRD-200770 ends
	/**
	 * @return Returns the masterRowId.
	 */
	public String[] getMasterRowId() {
		return masterRowId;
	}
	/**
	 * @param masterRowId
	 */
	public void setMasterRowId(String[] masterRowId) {
		this.masterRowId = masterRowId;
	}
	/**
	 * @return Returns the operationalFlag.
	 */
	public String[] getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * @param operationalFlag
	 */
	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
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
	 * @return Returns the remarksTable.
	 */
	public String[] getRemarksTable() {
		return remarksTable;
	}
	/**
	 * @param remarksTable The remarksTable to set.
	 */
	public void setRemarksTable(String[] remarksTable) {
		this.remarksTable = remarksTable;
	}
	/**
	 * @return Returns the repairedDateFrom.
	 */
	@DateFieldId(id="ListDamageReportsDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getRepairedDateFrom() {
		return repairedDateFrom;
	}
	/**
	 * @param repairedDateFrom The repairedDateFrom to set.
	 */
	public void setRepairedDateFrom(String repairedDateFrom) {
		this.repairedDateFrom = repairedDateFrom;
	}
	/**
	 * @return Returns the repairedDateTo.
	 */
	@DateFieldId(id="ListDamageReportsDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getRepairedDateTo() {
		return repairedDateTo;
	}
	/**
	 * @param repairedDateTo The repairedDateTo to set.
	 */
	public void setRepairedDateTo(String repairedDateTo) {
		this.repairedDateTo = repairedDateTo;
	}
	/**
	 * @return Returns the repairStatusTable.
	 */
	public String[] getRepairStatusTable() {
		return repairStatusTable;
	}
	/**
	 * @param repairStatusTable The repairStatusTable to set.
	 */
	public void setRepairStatusTable(String[] repairStatusTable) {
		this.repairStatusTable = repairStatusTable;
	}
	/**
	 * @return Returns the reportedByTable.
	 */
	public String[] getReportedByTable() {
		return reportedByTable;
	}
	/**
	 * @param reportedByTable The reportedByTable to set.
	 */
	public void setReportedByTable(String[] reportedByTable) {
		this.reportedByTable = reportedByTable;
	}
	/**
	 * @return Returns the reportedStn.
	 */
	public String getReportedStn() {
		return reportedStn;
	}
	/**
	 * @param reportedStn The reportedStn to set.
	 */
	public void setReportedStn(String reportedStn) {
		this.reportedStn = reportedStn;
	}
	/**
	 * @return Returns the reportedStnTable.
	 */
	public String[] getReportedStnTable() {
		return reportedStnTable;
	}
	/**
	 * @param reportedStnTable The reportedStnTable to set.
	 */
	public void setReportedStnTable(String[] reportedStnTable) {
		this.reportedStnTable = reportedStnTable;
	}
	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}
	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
	/**
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecordsDmg() {
		return totalRecordsDmg;
	}
	/**
	 * @param totalRecordsDmg
	 */
	public void setTotalRecordsDmg(String totalRecordsDmg) {
		this.totalRecordsDmg = totalRecordsDmg;
	}
	/**
	 * @return Returns the uldDamageStatus.
	 */
	public String getUldDamageStatus() {
		return uldDamageStatus;
	}
	/**
	 * @param uldDamageStatus
	 */
	public void setUldDamageStatus(String uldDamageStatus) {
		this.uldDamageStatus = uldDamageStatus;
	}
	/**
	 * @return Returns the uldNo.
	 */
	public String getUldNo() {
		return uldNo;
	}
	/**
	 * @param uldNo
	 */
	public void setUldNo(String uldNo) {
		this.uldNo = uldNo;
	}
	/**
	 * @return Returns the uldNoTable.
	 */
	public String[] getUldNoTable() {
		return uldNoTable;
	}
	/**
	 * @param uldNoTable
	 */
	public void setUldNoTable(String[] uldNoTable) {
		this.uldNoTable = uldNoTable;
	}
	/**
	 * @return Returns the uldStatus.
	 */
	public String getUldStatus() {
		return uldStatus;
	}
	/**
	 * @param uldStatus
	 */
	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
	}
	/**
	 * @return Returns the uldStatusTable.
	 */
	public String[] getUldStatusTable() {
		return uldStatusTable;
	}
	/**
	 * @param uldStatusTable
	 */
	public void setUldStatusTable(String[] uldStatusTable) {
		this.uldStatusTable = uldStatusTable;
	}
	/**
	 * @return Returns the uldTypeCode.
	 */
	public String getUldTypeCode() {
		return uldTypeCode;
	}
	/**
	 * @param uldTypeCode
	 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
	}
	/**
	 * 
	 */
	public String getScreenId() {
		// To be reviewed Auto-generated method stub
		return SCREENID;
	}
	/**
	 * 
	 */
	public String getProduct() {
		// To be reviewed Auto-generated method stub
		return PRODUCT;
	}
	/**
	 * 
	 */
	public String getSubProduct() {
		// To be reviewed Auto-generated method stub
		return SUBPRODUCT;
	}
	/**
	 * @return Returns the primaryKey.
	 */
	public String getPrimaryKey() {
		return primaryKey;
	}
	/**
	 * @param primaryKey The primaryKey to set.
	 */
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	/**
	 * @return Returns the listDamagePicPresent.
	 */
	public String getListDamagePicPresent() {
		return listDamagePicPresent;
	}
	/**
	 * @param listDamagePicPresent The listDamagePicPresent to set.
	 */
	public void setListDamagePicPresent(String listDamagePicPresent) {
		this.listDamagePicPresent = listDamagePicPresent;
	}
	/**
	 * @return Returns the damageDisableStatus.
	 */
	public String getDamageDisableStatus() {
		return damageDisableStatus;
	}
	/**
	 * @param damageDisableStatus The damageDisableStatus to set.
	 */
	public void setDamageDisableStatus(String damageDisableStatus) {
		this.damageDisableStatus = damageDisableStatus;
	}
	/**
	 * @return Returns the seqNum.
	 */
	public String getSeqNum() {
		return seqNum;
	}
	/**
	 * @param seqNum The seqNum to set.
	 */
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	/**
	 * @return Returns the selectedULDNum.
	 */
	public String getSelectedULDNum() {
		return selectedULDNum;
	}
	/**
	 * @param selectedULDNum The selectedULDNum to set.
	 */
	public void setSelectedULDNum(String selectedULDNum) {
		this.selectedULDNum = selectedULDNum;
	}
	/**
	 * 	Getter for paginationMode 
	 *	Added by : a-5223 on 07-Nov-2012
	 * 	Used for : getting total records
	 */
	public String getPaginationMode() {
		return paginationMode;
	}
	/**
	 *  @param paginationMode the paginationMode to set
	 * 	Setter for paginationMode 
	 *	Added by : a-5223 on 07-Nov-2012
	 * 	Used for : setting total records
	 */
	public void setPaginationMode(String paginationMode) {
		this.paginationMode = paginationMode;
	}
		
	 
}
