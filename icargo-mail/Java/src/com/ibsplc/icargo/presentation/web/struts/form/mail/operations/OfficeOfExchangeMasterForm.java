/*
 * OfficeOfExchangeMasterForm.java Created on June 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2047
 *
 */
public class OfficeOfExchangeMasterForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.masters.officeofexchange";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "officeOfExchangeResources";

	private String bundle;
	private String officeOfExchange;
	private String[] rowId;
	private String operationFlag;
	private String countryCode;
	private String cityCode;
	private String officeCode;
	private String codeDescription;
	private String poaCode;
	private String active;
	private String status;
	private String popUpStatus;
	
	
	private String ooeInfo;
	
	private String lastPageNum;
	private String displayPage;
	
	//Added by A-5945 for ICRD-71956 starts
    private String airportCode;
    //a-5945 ends
    //Added as part of CRQ ICRD-111886 by A-5526
    private String mailboxId;
	/**
	 * This property refers Exchange Office while Adding new OOE
	 */
	private String code;
	//Added by A-8527 for bug IASCB-30982 
	private String ooexchfltrval;
	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
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
	 * @return Returns the officeOfExchange.
	 */
	public String getOfficeOfExchange() {
		return officeOfExchange;
	}

	/**
	 * @param officeOfExchange The officeOfExchange to set.
	 */
	public void setOfficeOfExchange(String officeOfExchange) {
		this.officeOfExchange = officeOfExchange;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the cityCode.
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode The cityCode to set.
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return Returns the officeCode.
	 */
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * @param officeCode The officeCode to set.
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	/**
	 * @return Returns the codeDescription.
	 */
	public String getCodeDescription() {
		return codeDescription;
	}

	/**
	 * @param codeDescription The codeDescription to set.
	 */
	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	//Added by A-5945 for ICRD-71956 starts
	/**
	 * @return airportCode
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
	//A-5945 ends
	/**
	 * @return Returns the active.
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active The active to set.
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the popUpStatus.
	 */
	public String getPopUpStatus() {
		return popUpStatus;
	}

	/**
	 * @param popUpStatus The popUpStatus to set.
	 */
	public void setPopUpStatus(String popUpStatus) {
		this.popUpStatus = popUpStatus;
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

	public String getOoeInfo() {
		return ooeInfo;
	}

	public void setOoeInfo(String ooeInfo) {
		this.ooeInfo = ooeInfo;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}	
	
	public String getMailboxId() {
		return mailboxId;
	}
	public String getOoexchfltrval() {
		return ooexchfltrval;
	}
	public void setOoexchfltrval(String ooexchfltrval) {
		this.ooexchfltrval = ooexchfltrval;
	}

	public void setMailboxId(String mailboxId) {
		this.mailboxId = mailboxId;
	}

}
