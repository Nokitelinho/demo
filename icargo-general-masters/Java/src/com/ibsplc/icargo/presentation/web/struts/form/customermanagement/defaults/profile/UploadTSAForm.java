/*
 * UploadTSAForm.java Created on Jun 22, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3045
 *
 */
public class UploadTSAForm extends ScreenModel {
	
	/*
	 * The constant variable for product customermanagement
	 * 
	 */
	private static final String PRODUCT = "customermanagement";
	/*
	 * The constant for sub product defaults
	 */
	private static final String SUBPRODUCT = "defaults";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "customermanagement.defaults.customerlisting";
	/*
	 * The constant for bundle
	 */
	private static final String BUNDLE = "listcustomerform"; 
	
	private String bundle;
	private String fileType;
	private FormFile selectedFile=null;
	/**
	 * @return the bundle
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * @param bundle the bundle to set
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	/**
	 * @return the selectedFile
	 */
	public FormFile getSelectedFile() {
		return selectedFile;
	}
	/**
	 * @param selectedFile the selectedFile to set
	 */
	public void setSelectedFile(FormFile selectedFile) {
		this.selectedFile = selectedFile;
	}
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 */
	public String getProduct() {
		// To be reviewed Auto-generated method stub
		return PRODUCT;
	}
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 */
	public String getScreenId() {
		// To be reviewed Auto-generated method stub
		return SCREENID;
	}
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 */
	public String getSubProduct() {
		// To be reviewed Auto-generated method stub
		return SUBPRODUCT;
	}
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
