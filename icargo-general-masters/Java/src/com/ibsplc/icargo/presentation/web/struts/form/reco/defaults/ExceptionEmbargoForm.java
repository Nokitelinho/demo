package com.ibsplc.icargo.presentation.web.struts.form.reco.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-6843
 *
 */
public class ExceptionEmbargoForm extends ScreenModel{



	private static final String BUNDLE="exceptionembargoresources";
	
	private static final String PRODUCT = "reco";
	
	private static final String SUBPRODUCT = "defaults";
	
	private static final String SCREENID = "reco.defaults.exceptionembargo";
	private static final String EMPTYSPACE="";
	public static final String PAGINATION_MODE_FROM_FILTER="FILTER";
	public static final String PAGINATION_MODE_FROM_NAVIGATION="NAVIGATION";
	
	private String shipmentPrefixFilter;
	private String masterDocumentNumberFilter;
	private String startDateFilter;
	private String endDateFilter;

	private String productCode;
	private String[] shipmentPrefix;
	private String[] masterDocumentNumber;
	private String[] startDate;
	private String[] endDate;
	private String[] remarks;
	private String[] check;
	private String[] serialNumbers;
	
	private String applicableTransactionCodes;
	private String awbScribbledText = EMPTYSPACE;

	private String[] hiddenOpFlag;
	private String navigationMode;
	private String displayPage = "1";
	private String pageNumber = "0";

	public String getShipmentPrefixFilter() {
		return shipmentPrefixFilter;
	}

	public void setShipmentPrefixFilter(String shipmentPrefixFilter) {
		this.shipmentPrefixFilter = shipmentPrefixFilter;
	}

	public String getMasterDocumentNumberFilter() {
		return masterDocumentNumberFilter;
	}

	public void setMasterDocumentNumberFilter(String masterDocumentNumberFilter) {
		this.masterDocumentNumberFilter = masterDocumentNumberFilter;
	}

	public String getStartDateFilter() {
		return startDateFilter;
	}

	public void setStartDateFilter(String startDateFilter) {
		this.startDateFilter = startDateFilter;
	}

	public String getEndDateFilter() {
		return endDateFilter;
	}

	public void setEndDateFilter(String endDateFilter) {
		this.endDateFilter = endDateFilter;
	}

	public String[] getShipmentPrefix() {
		return shipmentPrefix;
	}

	public void setShipmentPrefix(String[] shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	
	public String[] getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	public void setMasterDocumentNumber(String[] masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	
	public String[] getStartDate() {
		return startDate;
	}

	public void setStartDate(String[] startDate) {
		this.startDate = startDate;
	}

	public String[] getEndDate() {
		return endDate;
	}

	public void setEndDate(String[] endDate) {
		this.endDate = endDate;
	}

	public String[] getRemarks() {
		return remarks;
	}

	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}

	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
	}
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Override
	public String getProduct() {
		return PRODUCT;
	}
	
	@Override
	public String getScreenId() {
		return SCREENID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	

	public String getApplicableTransactionCodes() {
		return applicableTransactionCodes;
	}

	public void setApplicableTransactionCodes(String applicableTransactionCodes) {
		this.applicableTransactionCodes = applicableTransactionCodes;
	}

	public String getAwbScribbledText() {
		return awbScribbledText;
	}

	public void setAwbScribbledText(String awbScribbledText) {
		this.awbScribbledText = awbScribbledText;
	}


	public String getNavigationMode() {
		return navigationMode;
	}

	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String[] getCheck() {
		return check;
	}

	public void setCheck(String[] check) {
		this.check = check;
	}

	public String[] getSerialNumbers() {
		return serialNumbers;
	}

	public void setSerialNumbers(String[] serialNumbers) {
		this.serialNumbers = serialNumbers;
	}


}
