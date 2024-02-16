/*
 * CopyBlgLineForm.java Created on Mar 22, 2007
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-2391
 *
 */
public class CopyBlgLineForm extends ScreenModel{
	private static final String BUNDLE = "copyblglineresources";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.defaults.copyrate";

	private String blgMatrixId="";
	private String validFrom="";
	private String validTo="";
	private String screenMode="";
	private String screenFlag="";
    	private String opFlag="";
	private String closeFlag="";
	
	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return closeFlag;
	}

	/**
	 * @param opFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}
	/**
	 * @return Returns the opFlag.
	 */
	public String getOpFlag() {
		return opFlag;
	}

	/**
	 * @param opFlag The opFlag to set.
	 */
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}

	/**
	 * @return Returns the blgMatrixId.
	 */
	public String getBlgMatrixId() {
		return blgMatrixId;
	}

	/**
	 * @param blgMatrixId The blgMatrixId to set.
	 */
	public void setBlgMatrixId(String blgMatrixId) {
		this.blgMatrixId = blgMatrixId;
	}

	/**
	 * @return Returns the screenMode.
	 */
	public String getScreenMode() {
		return screenMode;
	}

	/**
	 * @param screenMode The screenMode to set.
	 */
	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
	}

	/**
	 * @return Returns the validFrom.
	 */
	public String getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom The validFrom to set.
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return Returns the validTo.
	 */
	public String getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo The validTo to set.
	 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	/**
	 *
	 */
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 *
	 */
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 *
	 */
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
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
		//this.bundle = bundle;
	}

	/**
	 * @return Returns the screenFlag.
	 */
	public String getScreenFlag() {
		return screenFlag;
	}

	/**
	 * @param screenFlag The screenFlag to set.
	 */
	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}



}
