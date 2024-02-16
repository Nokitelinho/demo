/*
 * PreAdviceForm.java Created on July 03, 2006
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
public class PreAdviceForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.preadvice";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "preAdviceResources";
	
	private String bundle;

	
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

}
