/*
 * MonitorStockForm.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

// TODO: Auto-generated Javadoc
/**
 * The Class MonitorStockForm.
 *
 * @author A-1952
 */
public class MonitorStockForm extends ScreenModel {

	/** The is manual. */
	private boolean isManual;

	/** The stock holder type. */
	private String stockHolderType;

	/** The level. */
	private String level;

	/** The doc type. */
	private String docType;

	/** The sub type. */
	private String subType;

	/** The stock holder code. */
	private String stockHolderCode;

	/** The check. */
	private String[] check;

	/** The reference no. */
	private String referenceNo;
	
	/** The flag. */
	private String flag;
		
	/** The code. */
	private String code;//to pass selected code to the dummy command(allocate button)
	
	// FOR PAGINATION
	/** The last page num. */
	private String lastPageNum = "";

	/** The display page. */
	private String displayPage = "1";


	/** The button status flag. */
	private String buttonStatusFlag;
	// The key attribute specified in struts_config.xml file.
	/** The Constant BUNDLE. */
	private static final String BUNDLE = "monitorstockresources";

	/** The bundle. */
	private String bundle;

	/** The mode. */
	private String mode = "";
	
	/** The disable butn. */
	private String disableButn = "";

	/** The selected. */
	private String selected = "";
	
	
	/** The list flag. */
	private String listFlag = "";

	/** The list int. */
	private String listInt = "";
	
	/** The from list monitor. */
	private String fromListMonitor="";
	
	/** The from monitor stock. */
	private String fromMonitorStock="";
	
	/** The check list. */
	private String checkList="";
	
	/** The list flag from list screen. */
	private String listFlagFromListScreen="";
	
	/** The disable butn from list screen. */
	private String disableButnFromListScreen="";
	
	/** The stock holder for print. */
	private String stockHolderForPrint;
	
	/** The report generate mode. */
	private String reportGenerateMode; // Added as part of ICRD-46860 to decide whether report should be generated or not
	
	/** The document range. */
	private String documentRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType
	
	private String deleteButtonPrivilege;
	
	private String partnerPrefix;
	public String getPartnerPrefix() {
		return partnerPrefix;
	}
	public void setPartnerPrefix(String partnerPrefix) {
		this.partnerPrefix = partnerPrefix;
	}
	/**
	 * Gets the document range.
	 *
	 * @return the document range
	 */
	public String getDocumentRange() {
		return documentRange;
	}
	
	/**
	 * Sets the document range.
	 *
	 * @param documentRange the new document range
	 */
	public void setDocumentRange(String documentRange) {
		this.documentRange = documentRange;
	}
	
	/**
	 * Gets the report generate mode.
	 *
	 * @return the report generate mode
	 */
	public String getReportGenerateMode() {
		return reportGenerateMode;
	}
	
	/**
	 * Sets the report generate mode.
	 *
	 * @param reportGenerateMode the new report generate mode
	 */
	public void setReportGenerateMode(String reportGenerateMode) {
		this.reportGenerateMode = reportGenerateMode;
	}
	
	/** Partner airline flag. @author A-2589 */
	private boolean partnerAirline;
	
	/** AWB Prefix to search with (is partner airline flag is set). @author A-2589 */
	private String awbPrefix;
	
	/** Airline name for the AWB Prefix selected. @author A-2589 */
	private String airlineName;
		


	/**
	 * Gets the from monitor stock.
	 *
	 * @return the from monitor stock
	 */
	public String getFromMonitorStock() {
		return fromMonitorStock;
	}
	
	/**
	 * Sets the from monitor stock.
	 *
	 * @param fromMonitorStock the new from monitor stock
	 */
	public void setFromMonitorStock(String fromMonitorStock) {
		this.fromMonitorStock = fromMonitorStock;
	}
	
	/**
	 * Gets the disable butn.
	 *
	 * @return the disable butn
	 */
	public String getDisableButn() {
		return disableButn;
	}
	
	/**
	 * Sets the disable butn.
	 *
	 * @param disableButn the new disable butn
	 */
	public void setDisableButn(String disableButn) {
		this.disableButn = disableButn;
	}
	
	/**
	 * Gets the list flag.
	 *
	 * @return the list flag
	 */
	public String getListFlag() {
		return listFlag;
	}
	
	/**
	 * Sets the list flag.
	 *
	 * @param listFlag the new list flag
	 */
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}
	
	/**
	 * Gets the list int.
	 *
	 * @return the list int
	 */
	public String getListInt() {
		return listInt;
	}
	
	/**
	 * Sets the list int.
	 *
	 * @param listInt the new list int
	 */
	public void setListInt(String listInt) {
		this.listInt = listInt;
	}
	
	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	
	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * Gets the selected.
	 *
	 * @return the selected
	 */
	public String getSelected() {
		return selected;
	}
	
	/**
	 * Sets the selected.
	 *
	 * @param selected the new selected
	 */
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	/**
	 * Gets the bundle.
	 *
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	
	/**
	 * Sets the bundle.
	 *
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * Gets the reference no.
	 *
	 * @return Returns the referenceNo.
	 */
	public String getReferenceNo() {
		return referenceNo;
	}

	/**
	 * Sets the reference no.
	 *
	 * @param referenceNo The referenceNo to set.
	 */
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	/**
	 * Gets the stock holder type.
	 *
	 * @return Returns the stockHolderType.
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}

	/**
	 * Sets the stock holder type.
	 *
	 * @param stockHolderType The stockHolderType to set.
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}

	/**
	 * Gets the check.
	 *
	 * @return Returns the check.
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * Sets the check.
	 *
	 * @param check The check to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * Sets the manual.
	 *
	 * @param isManual The check to set.
	 */
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}

	/**
	 * Checks if is manual.
	 *
	 * @return Returns the isManual.
	 */
	public boolean isManual() {

		return isManual;
	}

	/**
	 * Gets the stock holder code.
	 *
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * Sets the stock holder code.
	 *
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * Gets the level.
	 *
	 * @return Returns the level.
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 *
	 * @param level The level to set.
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Gets the sub type.
	 *
	 * @return Returns the subType.
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * Sets the sub type.
	 *
	 * @param subType The subType to set.
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * Gets the doc type.
	 *
	 * @return Returns the docType.
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * Sets the doc type.
	 *
	 * @param docType The docType to set.
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 */
	public String getScreenId() {
		return "stockcontrol.defaults.monitorstock";
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 */
	public String getProduct() {
		return "stockcontrol";
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 */
	public String getSubProduct() {
		return "defaults";
	}
	
	/**
	 * Gets the flag.
	 *
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	
	/**
	 * Sets the flag.
	 *
	 * @param flag the new flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Gets the button status flag.
	 *
	 * @return Returns the buttonStatusFlag.
	 */
	public String getButtonStatusFlag() {
		return buttonStatusFlag;
	}
	
	/**
	 * Sets the button status flag.
	 *
	 * @param buttonStatusFlag The buttonStatusFlag to set.
	 */
	public void setButtonStatusFlag(String buttonStatusFlag) {
		this.buttonStatusFlag = buttonStatusFlag;
	}
	
	/**
	 * Gets the display page.
	 *
	 * @return the display page
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	
	/**
	 * Sets the display page.
	 *
	 * @param displayPage the new display page
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	
	/**
	 * Gets the last page num.
	 *
	 * @return the last page num
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	
	/**
	 * Sets the last page num.
	 *
	 * @param lastPageNum the new last page num
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	
	/**
	 * Gets the from list monitor.
	 *
	 * @return the from list monitor
	 */
	public String getFromListMonitor() {
		return fromListMonitor;
	}
	
	/**
	 * Sets the from list monitor.
	 *
	 * @param fromListMonitor the new from list monitor
	 */
	public void setFromListMonitor(String fromListMonitor) {
		this.fromListMonitor = fromListMonitor;
	}
	
	/**
	 * Gets the check list.
	 *
	 * @return the check list
	 */
	public String getCheckList() {
		return checkList;
	}
	
	/**
	 * Sets the check list.
	 *
	 * @param checkList the new check list
	 */
	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}
	
	/**
	 * Gets the disable butn from list screen.
	 *
	 * @return the disable butn from list screen
	 */
	public String getDisableButnFromListScreen() {
		return disableButnFromListScreen;
	}
	
	/**
	 * Sets the disable butn from list screen.
	 *
	 * @param disableButnFromListScreen the new disable butn from list screen
	 */
	public void setDisableButnFromListScreen(String disableButnFromListScreen) {
		this.disableButnFromListScreen = disableButnFromListScreen;
	}
	
	/**
	 * Gets the list flag from list screen.
	 *
	 * @return the list flag from list screen
	 */
	public String getListFlagFromListScreen() {
		return listFlagFromListScreen;
	}
	
	/**
	 * Sets the list flag from list screen.
	 *
	 * @param listFlagFromListScreen the new list flag from list screen
	 */
	public void setListFlagFromListScreen(String listFlagFromListScreen) {
		this.listFlagFromListScreen = listFlagFromListScreen;
	}
	
	/**
	 * Gets the stock holder for print.
	 *
	 * @return the stockHolderForPrint
	 */
	public String getStockHolderForPrint() {
		return stockHolderForPrint;
	}
	
	/**
	 * Sets the stock holder for print.
	 *
	 * @param stockHolderForPrint the stockHolderForPrint to set
	 */
	public void setStockHolderForPrint(String stockHolderForPrint) {
		this.stockHolderForPrint = stockHolderForPrint;
	}	
	
	/**
	 * Checks if is partner airline.
	 *
	 * @return the partnerAirline
	 */
	public boolean isPartnerAirline() {
		return partnerAirline;
	}
	
	/**
	 * Sets the partner airline.
	 *
	 * @param partnerAirline the partnerAirline to set
	 */
	public void setPartnerAirline(boolean partnerAirline) {
		this.partnerAirline = partnerAirline;
	}
	
	/**
	 * Gets the awb prefix.
	 *
	 * @return the awbPrefix
	 */
	public String getAwbPrefix() {
		return awbPrefix;
	}
	
	/**
	 * Sets the awb prefix.
	 *
	 * @param awbPrefix the awbPrefix to set
	 */
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	
	/**
	 * Gets the airline name.
	 *
	 * @return the airlineName
	 */
	public String getAirlineName() {
		return airlineName;
	}
	
	/**
	 * Sets the airline name.
	 *
	 * @param airlineName the airlineName to set
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getDeleteButtonPrivilege() {
		return deleteButtonPrivilege;
	}
	public void setDeleteButtonPrivilege(String deleteButtonPrivilege) {
		this.deleteButtonPrivilege = deleteButtonPrivilege;
	}
}
