/*
 * ListRepairReportForm.java Created on Feb 19,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-2046
 *
 */

public class ListRepairReportForm extends ScreenModel {

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
	private static final String SCREENID = "uld.defaults.listrepairreport";

    /**
	 * Constructor
	 */
	private static final String BUNDLE = "listRepairReportResources";
	
	 private String uldNo;
	 private String repairHead;
	 private String uldTypeCode;
	 private String uldStatus;
	 private String repairStatus;
	 private String currentStn;
	 private String repairedStn;
	 private String repairedDateFrom;
	 private String repairedDateTo;
	 private String invoiceId;
	 private String repairDisableStatus="";
	 
	 private String[] masterRowId;
	 private String[] rowId;
	 private String[] operationalFlag;
	 private String[] uldNoTable;
	 
	 private String[] repairHeadTable;
	 private String[] repairStnTable;
	 private String[] currentStnTable;
	 private String[] repairDateTable;
	 private String[] uldStatusTable;
	 private String[] remarksTable;
	 
	 private String bundle;
  
	 private String displayPage="1";
	 private String lastPageNumber="0";
	 private String totalRecords;
	 private String currentPageNum;
  
    private String pageURL="";
    private Page listPage;
	private String listStatus;
	private String currencyValue;
	private String countTotalFlag = "";//Added by A-5214 as part from the ICRD-22824
	
	
	/**
	 * @return the currencyValue
	 */
	public String getCurrencyValue() {
		return currencyValue;
	}
	/**
	 * @param currencyValue the currencyValue to set
	 */
	public void setCurrencyValue(String currencyValue) {
		this.currencyValue = currencyValue;
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
	public String getLastPageNumber() {
		return lastPageNumber;
	}
	/**
	 * @param lastPageNumber
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
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
	 * @param pageURL
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
	 * @param remarksTable
	 */
	public void setRemarksTable(String[] remarksTable) {
		this.remarksTable = remarksTable;
	}
	/**
	 * @return Returns the repairedDateFrom.
	 */
	@DateFieldId(id="ListRepairReportsDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getRepairedDateFrom() {
		return repairedDateFrom;
	}
	/**
	 * @param repairedDateFrom
	 */
	public void setRepairedDateFrom(String repairedDateFrom) {
		this.repairedDateFrom = repairedDateFrom;
	}
	/**
	 * @return Returns the repairedDateTo.
	 */
	@DateFieldId(id="ListRepairReportsDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getRepairedDateTo() {
		return repairedDateTo;
	}
	/**
	 * @param repairedDateTo
	 */
	public void setRepairedDateTo(String repairedDateTo) {
		this.repairedDateTo = repairedDateTo;
	}
	
	/**
	 * 
	 * @return
	 */
	public String[] getRepairDateTable() {
		return repairDateTable;
	}
	/**
	 * 
	 * @param repairDateTable
	 */
	public void setRepairDateTable(String[] repairDateTable) {
		this.repairDateTable = repairDateTable;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getRepairHeadTable() {
		return repairHeadTable;
	}
	/**
	 * 
	 * @param repairHeadTable
	 */
	public void setRepairHeadTable(String[] repairHeadTable) {
		this.repairHeadTable = repairHeadTable;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getRepairStnTable() {
		return repairStnTable;
	}
	/**
	 * 
	 * @param repairStnTable
	 */
	public void setRepairStnTable(String[] repairStnTable) {
		this.repairStnTable = repairStnTable;
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
	 * @return Returns the uldNo.
	 */
	public String getUldNo() {
		return uldNo;
	}
	/**
	 * @param uldNo The uldNo to set.
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
	 * @param uldNoTable The uldNoTable to set.
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
	 * @param uldStatus The uldStatus to set.
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
	 * @param uldStatusTable The uldStatusTable to set.
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
	 * @param uldTypeCode The uldTypeCode to set.
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
	 * 
	 * @return
	 */
	public String getRepairedStn() {
		return repairedStn;
	}
	/**
	 * 
	 * @param repairedStn
	 */
	public void setRepairedStn(String repairedStn) {
		this.repairedStn = repairedStn;
	}
	/**
	 * 
	 * @return
	 */
	public String getRepairHead() {
		return repairHead;
	}
	/**
	 * 
	 * @param repairHead
	 */
	public void setRepairHead(String repairHead) {
		this.repairHead = repairHead;
	}
	/**
	 * 
	 * @return
	 */
	public String getRepairStatus() {
		return repairStatus;
	}
	/**
	 * 
	 * @param repairStatus
	 */
	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}
	/**
	 * @return Returns the invoiceId.
	 */
	public String getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId The invoiceId to set.
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return Returns the repairDisableStatus.
	 */
	public String getRepairDisableStatus() {
		return repairDisableStatus;
	}
	/**
	 * @param repairDisableStatus The repairDisableStatus to set.
	 */
	public void setRepairDisableStatus(String repairDisableStatus) {
		this.repairDisableStatus = repairDisableStatus;
	}
	//Added by A-5214 as part from the ICRD-22824 STARTS
	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}
	public String getCountTotalFlag() {
		return countTotalFlag;
	}
	//Added by A-5214 as part from the ICRD-22824 ENDS	 
}
