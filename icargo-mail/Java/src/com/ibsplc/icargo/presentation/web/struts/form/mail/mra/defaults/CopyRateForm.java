/*
 * CopyRateForm.java Created on Feb 8, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;



/**
 * @author A-2280
 *
 */
public class CopyRateForm extends ScreenModel{

private static final String BUNDLE = "copyrateresources";	

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.defaults.copyrate";

	private String rateCardId="";
	private String validFrom="";
	private String validTo="";
	private String screenMode="";
	private String screenFlag="";


	/**
	 * @return Returns the rateCardId.
	 */
	public String getRateCardId() {
		return rateCardId;
	}

	/**
	 * @param rateCardId The rateCardId to set.
	 */
	public void setRateCardId(String rateCardId) {
		this.rateCardId = rateCardId;
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
