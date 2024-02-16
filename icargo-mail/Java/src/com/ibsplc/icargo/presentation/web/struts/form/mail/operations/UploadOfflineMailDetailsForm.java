/*
 * UploadOfflineMailDetailsForm.java Created on Oct 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-6385
 * Added for ICRD-84459
 *
 */
public class UploadOfflineMailDetailsForm extends ScreenModel{
	
	private static final String BUNDLE = "uploadMailDetailsResources";

	private static final String SCREENID = "mailtracking.defaults.offlinemailupload";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "operations";
	
	
	private String[] selectedMails;
	
	private String scanningPort;
		
	
	/**
	 * @return the scanningPort
	 */
	public String getScanningPort() {
		return scanningPort;
	}

	/**
	 * @param scanningPort the scanningPort to set
	 */
	public void setScanningPort(String scanningPort) {
		this.scanningPort = scanningPort;
	}

	/**
	 * @return the selectedMails
	 */
	public String[] getSelectedMails() {
		return selectedMails;
	}

	/**
	 * @param selectedMails the selectedMails to set
	 */
	public void setSelectedMails(String[] selectedMails) {
		this.selectedMails = selectedMails;
	}

	public String getBundle() {
		return BUNDLE;
	}
	
	public String getProduct() {
		return PRODUCT;
	}
	
	public String getScreenId() {
		return SCREENID;
	}
	public String getSubProduct() {
		return SUBPRODUCT;
	}

}
