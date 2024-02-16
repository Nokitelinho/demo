/*
 * MRASisSupportingDocumentDetailsForm.java Created on Oct 10, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-8061
 *
 */
public class MRASisSupportingDocumentDetailsForm extends ScreenModel{

	private String uploadFileName;
	private FormFile fileData = null;
	private String actionStatus;
	private String fromScreen;
	private String selectedRow;
	private String billingType;
	
	

	private static final String BUNDLE = "rejectionmemobundle";

	private static final String MODULE = "mail";

	private static final String SUBMODULE = "mra";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";
	

	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 *  @return Returns the product
	 */
	public String getProduct() {
		return MODULE;
	}

	/**
	 *  @return Returns the subProduct
	 */
	public String getSubProduct() {
		return SUBMODULE;
	}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public FormFile getFileData() {
		return fileData;
	}

	public void setFileData(FormFile fileData) {
		this.fileData = fileData;
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
	 public String getFromScreen() {
			return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	 public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public String getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}




}
