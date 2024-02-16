/*
 * ULDServiceabilityForm.java Created on Aug 26, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2052
 *
 */
public class ULDServiceabilityForm extends ScreenModel {

	private static final String BUNDLE = "uldserviceability";

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.uldserviceability";

	private String bundle;

	private String partyType;

	private String[] serviceCode;

	private String[] serviceDescription;

	private String beforeSave = "";

	private String[] selectedRows;

	private String afterList = "";

	private String[] rowId;

	private String[] operationFlag;

	private String[] sequenceNumber;

	private String chkBoxFlag;

	private String screenName;

	private String selectedRow_Type;

	/**
	 * @return the selectedRow_Type
	 */
	public String getSelectedRow_Type() {
		return selectedRow_Type;
	}

	/**
	 * @param selectedRow_Type the selectedRow_Type to set
	 */
	public void setSelectedRow_Type(String selectedRow_Type) {
		this.selectedRow_Type = selectedRow_Type;
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
	 * @return Returns the afterList.
	 */
	public String getAfterList() {
		return afterList;
	}

	/**
	 * @param afterList The afterList to set.
	 */
	public void setAfterList(String afterList) {
		this.afterList = afterList;
	}

	/**
	 * @return Returns the beforeSave.
	 */
	public String getBeforeSave() {
		return beforeSave;
	}

	/**
	 * @param beforeSave The beforeSave to set.
	 */
	public void setBeforeSave(String beforeSave) {
		this.beforeSave = beforeSave;
	}

	/**
	 * @return Returns the bUNDLE.
	 */
	public static String getBUNDLE() {
		return BUNDLE;
	}

	/**
	 * 
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * 
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * 
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the selectedRows.
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows The selectedRows to set.
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * 
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
	 * @return Returns the PartyType.
	 */
	public String getPartyType() {
		return partyType;
	}

	/**
	 * @param PartyType The PartyType to set.
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	/**
	 * @return Returns the chkBoxFlag.
	 */
	public String getChkBoxFlag() {
		return chkBoxFlag;
	}

	/**
	 * @param chkBoxFlag The chkBoxFlag to set.
	 */
	public void setChkBoxFlag(String chkBoxFlag) {
		this.chkBoxFlag = chkBoxFlag;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * 
	 * @param operationFlag
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * 
	 * @param sequenceNumber
	 */
	public void setSequenceNumber(String[] sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the serviceCode
	 */
	public String[] getServiceCode() {
		return serviceCode;
	}

	/**
	 * @param serviceCode the serviceCode to set
	 */
	public void setServiceCode(String[] serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * @return the serviceDescription
	 */
	public String[] getServiceDescription() {
		return serviceDescription;
	}

	/**
	 * @param serviceDescription the serviceDescription to set
	 */
	public void setServiceDescription(String[] serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
}
