/*
 * ReservationListingForm.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;

/**
 * @author A-1619
 *
 */
public class ReservationListingForm extends ScreenModel {
	/*
	 * The constant variable for product
	 *
	 */
	private static final String PRODUCT = "stockcontrol";
	/*
	 * The constant for sub product
	 */
	private static final String SUBPRODUCT = "defaults";
	/*
	 * The constant for screen id
	 */
	private static final String SCREEN_ID = "stockcontrol.defaults.reservationlisting";

	/**
	 * Constructor
	 */
	private static final String BUNDLE = "reservationListingResources";

    private String[] rowId;
	private String[] operationFlag;
	private String reservationFilterFromDate;
	private String reservationFilterToDate;
	private String expiryFilterFromDate;
	private String expiryFilterToDate;
	private String airlineFilterCode;
	private String customerFilterCode;
	private String documentFilterType;

    private String airportCode;
    private int airlineIdentifier;
    private String airlineCode;
    private String[] documentNumber;
    private String[] customerCode;
    private String[] documentType;
    private String[] shipmentPrefix;
    private String[] reservationDate;
    private String[] expiryDate;
    private String documentStatus;
    private String[] reservationRemarks;
    private String popDocumentnumber;
    private String popShipmentPrefix;
    private String cancelRemarks;
    private String extendexpiryDate;
    private String afterReload="N";
   	private String bundle;
    private String popupRowId;
    private String displayPage = "1";
	private String totalRecords = "0";
	private String lastPageNum = "0";
	private String currentPageNum = "1";
	private String errorStatus;
	private String listScreen;
	private String preview;
	private String enableBtn = "N";  

	/**
	 * @return Returns the preview.
	 */
	public String getPreview() {
		return this.preview;
	}
	/**
	 * @param preview The preview to set.
	 */
	public void setPreview(String preview) {
		this.preview = preview;
	}
	/**
     * @return Returns the bundle.
	 */
	/**
     * @return String
     * @param 
     * */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
     */
	/**
     * @return String
     * @param 
     * */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
     */
    /**
     * @return String
     * @param 
     * */
    public String getProduct() {
        return PRODUCT;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
     */
    /**
     * @return String
     * @param 
     * */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return Returns the airlineFilterCode.
	 */
	public String getAirlineFilterCode() {
		return airlineFilterCode;
	}
	/**
	 * @param airlineFilterCode The airlineFilterCode to set.
	 */
	public void setAirlineFilterCode(String airlineFilterCode) {
		this.airlineFilterCode = airlineFilterCode;
	}
	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return Returns the aWBFilterType.
	 */
	public String getDocumentFilterType() {
		return documentFilterType;
	}
	/**
	 * @param filterType The aWBFilterType to set.
	 */
	/**
     * @return 
     * @param documentFilterType
     * */
	public void setDocumentFilterType(String documentFilterType) {
		this.documentFilterType = documentFilterType;
	}
	/**
	 * @return Returns the customerCode.
	 */
	public String[] getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String[] customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return Returns the customerFilterCode.
	 */
	public String getCustomerFilterCode() {
		return customerFilterCode;
	}
	/**
	 * @param customerFilterCode The customerFilterCode to set.
	 */
	public void setCustomerFilterCode(String customerFilterCode) {
		this.customerFilterCode = customerFilterCode;
	}
	/**
	 * @return Returns the documentNumber.
	 */
	public String[] getDocumentNumber() {
		return documentNumber;
	}
	/**
	 * @param documentNumber The documentNumber to set.
	 */
	public void setDocumentNumber(String[] documentNumber) {
		this.documentNumber = documentNumber;
	}
	/**
	 * @return Returns the documentStatus.
	 */
	public String getDocumentStatus() {
		return documentStatus;
	}
	/**
	 * @param documentStatus The documentStatus to set.
	 */
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	/**
	 * @return Returns the documentType.
	 */
	public String[] getDocumentType() {
		return documentType;
	}
	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String[] documentType) {
		this.documentType = documentType;
	}
	/**
	 * @return Returns the expiryDate.
	 */
	public String[] getExpiryDate() {
		return expiryDate;
	}
	/**
	 * @param expiryDate The expiryDate to set.
	 */
	public void setExpiryDate(String[] expiryDate) {
		this.expiryDate = expiryDate;
	}
	/**
	 * @return Returns the expiryFilterFromDate.
	 */
	@DateFieldId(id="ReservationListingExpiryDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getExpiryFilterFromDate() {
		return expiryFilterFromDate;
	}
	/**
	 * @param expiryFilterFromDate The expiryFilterFromDate to set.
	 */
	public void setExpiryFilterFromDate(String expiryFilterFromDate) {
		this.expiryFilterFromDate = expiryFilterFromDate;
	}
	/**
	 * @return Returns the expiryFilterToDate.
	 */
	@DateFieldId(id="ReservationListingExpiryDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getExpiryFilterToDate() {
		return expiryFilterToDate;
	}
	/**
	 * @param expiryFilterToDate The expiryFilterToDate to set.
	 */
	public void setExpiryFilterToDate(String expiryFilterToDate) {
		this.expiryFilterToDate = expiryFilterToDate;
	}
	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return Returns the reservationDate.
	 */
	public String[] getReservationDate() {
		return reservationDate;
	}
	/**
	 * @param reservationDate The reservationDate to set.
	 */
	public void setReservationDate(String[] reservationDate) {
		this.reservationDate = reservationDate;
	}
	/**
	 * @return Returns the reservationFilterFromDate.
	 */
	@DateFieldId(id="ReservationListingDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getReservationFilterFromDate() {
		return reservationFilterFromDate;
	}
	/**
	 * @param reservationFilterFromDate The reservationFilterFromDate to set.
	 */
	public void setReservationFilterFromDate(String reservationFilterFromDate) {
		this.reservationFilterFromDate = reservationFilterFromDate;
	}
	/**
	 * @return Returns the reservationFilterToDate.
	 */
	@DateFieldId(id="ReservationListingDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getReservationFilterToDate() {
		return reservationFilterToDate;
	}
	/**
	 * @param reservationFilterToDate The reservationFilterToDate to set.
	 */
	public void setReservationFilterToDate(String reservationFilterToDate) {
		this.reservationFilterToDate = reservationFilterToDate;
	}
	/**
	 * @return Returns the reservationRemarks.
	 */
	public String[] getReservationRemarks() {
		return reservationRemarks;
	}
	/**
	 * @param reservationRemarks The reservationRemarks to set.
	 */
	public void setReservationRemarks(String[] reservationRemarks) {
		this.reservationRemarks = reservationRemarks;
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
	 * @return Returns the shipmentPrefix.
	 */
	public String[] getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 * @param shipmentPrefix The shipmentPrefix to set.
	 */
	public void setShipmentPrefix(String[] shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	/**
	 * @return Returns the cancelRemarks.
	 */
	public String getCancelRemarks() {
		return cancelRemarks;
	}
	/**
	 * @param cancelRemarks The cancelRemarks to set.
	 */
	public void setCancelRemarks(String cancelRemarks) {
		this.cancelRemarks = cancelRemarks;
	}
	/**
	 * @return Returns the cancelshipmentPrefix.
	 */
	public String getPopShipmentPrefix() {
		return popShipmentPrefix;
	}
	/**
	 * @param cancelshipmentPrefix The cancelshipmentPrefix to set.
	 */
	/**
     * @return 
     * @param popShipmentPrefix
     * */
	public void setPopShipmentPrefix(String popShipmentPrefix) {
		this.popShipmentPrefix = popShipmentPrefix;
	}
	/**
	 * @return Returns the documentnumber.
	 */
	public String getPopDocumentnumber() {
		return popDocumentnumber;
	}
	/**
	 * @param documentnumber The documentnumber to set.
	 */
	/**
     * @return 
     * @param popDocumentnumber
     * */
	public void setPopDocumentnumber(String popDocumentnumber) {
		this.popDocumentnumber = popDocumentnumber;
	}
	/**
	 * @return Returns the extendexpiryDate.
	 */
	public String getExtendexpiryDate() {
		return extendexpiryDate;
	}
	/**
	 * @param extendexpiryDate The extendexpiryDate to set.
	 */
	public void setExtendexpiryDate(String extendexpiryDate) {
		this.extendexpiryDate = extendexpiryDate;
	}
	/**
	 * @return Returns the afterReload.
	 */
	public String getAfterReload() {
		return afterReload;
	}
	/**
	 * @param afterReload The afterReload to set.
	 */
	public void setAfterReload(String afterReload) {
		this.afterReload = afterReload;
	}
	/**
	 * @return Returns the popupRowId.
	 */
	public String getPopupRowId() {
		return popupRowId;
	}
	/**
	 * @param popupRowId The popupRowId to set.
	 */
	public void setPopupRowId(String popupRowId) {
		this.popupRowId = popupRowId;
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
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
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
	 * @return Returns the errorStatus.
	 */
	public String getErrorStatus() {
		return errorStatus;
	}
	/**
	 * @param errorStatus The errorStatus to set.
	 */
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}
	/**
	 * @return Returns the listScreen.
	 */
	public String getListScreen() {
		return listScreen;
	}
	/**
	 * @param listScreen The listScreen to set.
	 */
	public void setListScreen(String listScreen) {
		this.listScreen = listScreen;
	}
	/**
	 * @return Returns the enableBtn.
	 */
	public String getEnableBtn() {
		return enableBtn;
	}
	/**
	 * @param enableBtn The enableBtn to set.
	 */
	public void setEnableBtn(String enableBtn) {
		this.enableBtn = enableBtn;
	}
	
	

}
